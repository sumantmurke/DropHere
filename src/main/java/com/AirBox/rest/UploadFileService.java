package com.AirBox.rest;

import com.AirBox.SES.AmazonSESSample;
import com.AirBox.SES.ConcreteMessage;
import com.AirBox.SES.ConcreteUserInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.AirBox.Dao.DbConnection;
import com.AirBox.Domain.UploadObject;
import com.AirBox.Domain.User;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;



@Path("/file")
public class UploadFileService {
	
	public ConcreteMessage cm;
	public ConcreteUserInfo ui;
	public AmazonSESSample as;
	
	AWSCredentials myCredentials = new BasicAWSCredentials(
			S3Config.getMyAccessId(), S3Config.getMySecretId());
	AmazonS3 newc = new AmazonS3Client(myCredentials);        
	Region usWest1 = Region.getRegion(Regions.US_WEST_1);
	
	
/*
 * Rest API for handling between AWS and files  
 */
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			 @FormDataParam("file") File fileobject,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader,@Context HttpServletRequest req) {
		String username;
		String bucketname;
		
		HttpSession session= req.getSession(true);
		username=(String) session.getAttribute("username");
		
		DbConnection dbnewcon = new DbConnection();
		bucketname = dbnewcon.getBucketName(username);
		
		String invalidFile = "Invalid File";
		AWSFacade awsFacade=new AWSFacade();
		String output = "";
		//If File Uploaded successfully then send EMail to user about the upload
		
		if(output.equalsIgnoreCase("success")){
			
			String msgBody ="File upload successful";
			String msgHeader = "AWS S3 file upload success";
			
			ui = new ConcreteUserInfo(username);
			cm = new ConcreteMessage(msgBody, msgHeader);
			as = new AmazonSESSample();
			/*ui.setReEmail("chetan.burande7@gmail.com");
			cm.setMsgBody(msgBody);
			cm.setMsgSubject(msgHeader);*/
			as.setConnec(ui, cm);
		}
		
		UploadObject uploadobject = new UploadObject();
		uploadobject.setFileName(contentDispositionHeader.getFileName());
		uploadobject.setSize(fileobject.length());
		uploadobject.setUsername(username); 
		
		/*
		 * dynamo
		 */
		//dynamo d = new dynamo();
		//d.setFileDetails();
		
		System.out.println("upload object user is "+ uploadobject.getUsername());
		System.out.println("uploaded object date created is "+uploadobject.getDateCreated());
		System.out.println("upload object size is "+ fileobject.length());//new
		//System.out.println("uploaded object date created is "+ dateFormat.format(date));//new
		DbConnection dbcon = new DbConnection();
		dbcon.getTotalSize(username);
		if(dbcon.insertFiledata(uploadobject, username)) 
		{
			System.out.println("file added to S3 after db insertion");
			output=awsFacade.addS3BucketObjects(fileobject,contentDispositionHeader.getFileName(),bucketname);
			return Response.status(200).entity(output).build();
			}
			
			else
				System.out.println("sorry...Not enough space ...");
				return Response.status(400).entity(invalidFile).build();
			

	}
			
			
			
		

	@GET
    @Path("/download")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response downloadObjects(@Context HttpServletRequest req){
    	
    	String output="Files downloaded at location: C:/Users/Rohit/Desktop/AirBoxRepo/";
    	String downloadLocation = "C:/Users/Bhagyashree/Desktop/AirBoxRepo/";
    	AWSFacade awsFacade=new AWSFacade();
    	HttpSession session= req.getSession(true);
		String username = (String) session.getAttribute("username");
		DbConnection dbnewcon = new DbConnection();
		String bucketname = dbnewcon.getBucketName(username);
    	
    	output=awsFacade.downloadAllS3BucketObjects(downloadLocation,bucketname);
    	return Response.status(200).entity(output).build();
    }  
	
	@GET
    @Path("/download/{objectKey}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response downloadOneObject(@PathParam("objectKey") String key, @Context HttpServletRequest req){
    	
    	/*String output="Files downloaded at location: C:/Users/Rohit/Desktop/AirBoxRepo/";
    	String downloadLocation = "/Users/sumantmurke/Desktop/download/";*/
		String username;
		String bucketname;
    	AWSFacade awsFacade=new AWSFacade();
    	HttpSession session= req.getSession(true);
		username=(String) session.getAttribute("username");
    	DbConnection dbnewcon = new DbConnection();
		bucketname = dbnewcon.getBucketName(username);
		String fileLink = awsFacade.getShareLink(bucketname,key);
		
    	//output=awsFacade.downloadS3BucketObject(downloadLocation, key);
    	return Response.status(200).entity(fileLink).build();
    } 

	@DELETE
    @Path("/delete/{objectkey}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response deleteObject(
    		@PathParam("objectkey") String key, @Context HttpServletRequest req){
    	AWSFacade awsFacade=new AWSFacade();
    	HttpSession session= req.getSession(true);
    	System.out.println("key::::::: "+key);
    	String username=(String) session.getAttribute("username");
    	//session.getAttribute("filename")
    	DbConnection dbnewcon = new DbConnection();
		String bucketname = dbnewcon.getBucketName(username);
    //	DbConnection dbcon = new DbConnection();
    //	dbcon.deleteFile(key);
    	String output=awsFacade.deleteS3BucketObjects(key,bucketname);
    	dbnewcon.deleteFile(username, key);
    	return Response.status(200).entity(output).build();
    }
	
	/*
	 * REST API for accessing of user data 
	 */
	
	@POST
	@Path("/signup")
	public Response userprofile(@FormParam("fname") String fname,
			@FormParam("lname") String lname,
			@FormParam("email") String email, 
			@FormParam("password") String password,
			@Context HttpServletRequest req) {
			System.out.println("name is: "+fname);
			User user = new User();
			user.setFirstName(fname);
			user.setLastName(lname);
			user.setUserName(email);
			user.setPassword(password);
			AWSFacade fact= new AWSFacade();
			System.out.println("surname of the user is"+user.getLastName());
			String output = "Thankyou for registring with us you will recieve email shortly "+ user.getFirstName();
			DbConnection dbcon = new DbConnection();
			
			HttpSession session= req.getSession(true);
			session.setAttribute("username", email);
			session.setAttribute("sessionId", session.getId());
			session.setAttribute("usersfirstname", fname);
			session.setAttribute("userslastname", lname);
			
			/*
			 * Dynamo
			 */
			System.out.println("before dynamo");
			
			dynamo d = new dynamo();
			d.addItem(user.getFirstName(), user.getLastName(), user.getUserName(), user.getPassword());
			
			
			System.out.println("after dynamo");
			
			// dynamo end
			
			System.out.println("User added");
			
			Bucket bucket = fact.makeNewBucket(user);
			System.out.println("UploadFileService: After adding new bucket - "+user.getBucketname());
			dbcon.insertUser(user);
			
			String msgBody ="Your account was created on AirBox.";
			String msgHeader = "Registration Confirmation";
			
			ui = new ConcreteUserInfo(email);
			as = new AmazonSESSample();
			cm = new ConcreteMessage(msgBody, msgHeader);
			//ui.setReEmail(email);
			//cm.setMsgBody(msgBody);
			//cm.setMsgSubject(msgHeader);
			as.setConnec(ui, cm);
		return Response.status(200).entity(output).build();

	}

	@POST
	@Path("/login")
	public Response userLogin(@FormParam("email") String email, 
			@FormParam("password") String password, 
			@Context HttpServletRequest req) {
			String output = "";
			String invalidUser = "Invalid User";
			System.out.println("Username is: "+email);
			DbConnection dbcon = new DbConnection();
			//List<String> userDetails = new ArrayList<String>();
			
			User userDetailObject = new User();
			//List<UploadObject> fileDetails = new ArrayList<UploadObject>();
			List<UploadObject> shareperc = new ArrayList<UploadObject>();
			List<UploadObject> sharefiledetails = new ArrayList<UploadObject>();
			
			
			
			if(dbcon.loginCheck(email, password))
			{
			output = "Login Successful for "+ email;
			System.out.println("User Validated");
			HttpSession session= req.getSession(true);
			session.setAttribute("username", email);
			session.setAttribute("sessionId", session.getId());

			
			User user =	dbcon.getUserDetails(email);
			user.getFirstName();
			System.out.println("users firsname"+user.getFirstName());
			
			session.setAttribute("usersfirstname", user.getFirstName());
			session.setAttribute("userslastname", user.getLastName());
			//session.setAttribute("userspassword", user.getPassword());
			

			//String bucketname = dbcon.getBucketName(email);			
			//userDetailObject = dbcon.getUserDetails(email);
			//fileDetails = dbcon.getFileDetails(email);

			/*			
			shareperc= dbcon.getSharePerc(email);
		
			for(int l=0; l<shareperc.size();l++){
				System.out.println("shareperc sharename "+shareperc.get(l).getUsername());
				System.out.println("shareperc filesize "+shareperc.get(l).getSize());
				
			}
			
			session.setAttribute("shareperc", shareperc);

			
										
			//for(int i=0; i<fileDetails.size();i++){
			//System.out.println("first object upload object "+fileDetails.get(i).getFileName());
				
			//}
		//	HttpSession session= req.getSession(true);
			//session.setAttribute("fileDetails", fileDetails);
	
			//System.out.println("total percentage "+dbcon.getTotalSize(email));
			//session.setAttribute("filePercentage", dbcon.getTotalSize(email));
			
			//Added by Sumant
			if (dbcon.shareCheck(email))
			{
									
			shareperc= dbcon.getSharePerc(email);
			sharefiledetails=dbcon.getShareFileDetails(email);
			
			}
			else
				
			{
				shareperc=null;
				sharefiledetails= null;
			
			}
			for(int j=0;j<sharefiledetails.size();j++){
				System.out.println("share name "+sharefiledetails.get(j).getUsername());
				System.out.println("file name "+sharefiledetails.get(j).getFileName());
			}
			
			session.setAttribute("history", sharefiledetails);
*/
			
			
			return Response.status(200).entity(output).build();
			}
			
			else
				System.out.println("User Invalid");
				return Response.status(400).entity(invalidUser).build();	

	}
	@GET
	@Path("/refresh")
	public Response pageRefresh(@Context HttpServletRequest req) {
		User userDetailObject = new User();
		List<UploadObject> fileDetails = new ArrayList<UploadObject>();
		List<UploadObject> sharefiledetails = new ArrayList<UploadObject>();
		List<UploadObject> shareperc = new ArrayList<UploadObject>();
		DbConnection dbcon = new DbConnection();
		HttpSession session= req.getSession(true);
		String email=(String)session.getAttribute("username");
		
		userDetailObject = dbcon.getUserDetails(email);
		fileDetails = dbcon.getFileDetails(email);

		shareperc= dbcon.getSharePerc(email);
		
		for(int l=0; l<shareperc.size();l++){
			System.out.println("shareperc sharename "+shareperc.get(l).getUsername());
			System.out.println("shareperc filesize "+shareperc.get(l).getSize());
			
		}
		
		session.setAttribute("shareperc", shareperc);

		
									
		//for(int i=0; i<fileDetails.size();i++){
		//System.out.println("first object upload object "+fileDetails.get(i).getFileName());
			
		//}
	//	HttpSession session= req.getSession(true);
		//session.setAttribute("fileDetails", fileDetails);

		//System.out.println("total percentage "+dbcon.getTotalSize(email));
		//session.setAttribute("filePercentage", dbcon.getTotalSize(email));
		
		//Added by Sumant
		if (dbcon.shareCheck(email))
		{
								
		shareperc= dbcon.getSharePerc(email);
		sharefiledetails=dbcon.getShareFileDetails(email);
		
		}
		else
			
		{
			shareperc=null;
			sharefiledetails= null;
		
		}
		if(sharefiledetails != null){
		for(int j=0;j<sharefiledetails.size();j++){
			System.out.println("share name "+sharefiledetails.get(j).getUsername());
			System.out.println("file name "+sharefiledetails.get(j).getFileName());
		}
		}
		
		session.setAttribute("history", sharefiledetails);
		if(fileDetails != null){
		for(int i=0; i<fileDetails.size();i++){
			System.out.println("first object upload object "+fileDetails.get(i).getFileName());
			
		}}		
		session.setAttribute("fileDetails", fileDetails);
		//sharefiledetails = dbcon.getShareFileDetails(email);
		System.out.println("total percentage "+dbcon.getTotalSize(email));
		session.setAttribute("filePercentage", dbcon.getTotalSize(email));
		return Response.status(200).entity("success").build();

	}


	@POST
	@Path("/sharelink")
	public Response shareEmail(@FormParam("shareemail") String shareemail, 
			@FormParam("filename") String filename, 
			@Context HttpServletRequest req) {
			String username;
			String bucketname;
			String shareFileName = "";
			String owneremail = "";
			String output = "success";
			shareFileName = filename;
			System.out.println("Link will be shared with : "+shareemail);
			System.out.println("File name from controller - "+shareFileName);
			HttpSession session= req.getSession(true);
			owneremail=(String) session.getAttribute("username");
			System.out.println("owneremail from session is -" +owneremail);
			DbConnection dbcon = new DbConnection();
			
			dbcon.shareFile(owneremail, shareFileName, shareemail);
			// Sending email to user
			String ownermsgBody ="You have shared a file - "+shareFileName+" with - "+shareemail;
			String ownermsgHeader = "File sharing successful";
			String fileLink = "";
			ui = new ConcreteUserInfo(owneremail);
			cm = new ConcreteMessage(ownermsgBody, ownermsgHeader);
			as = new AmazonSESSample();
			as.setConnec(ui, cm);
			// Sending email notification to file receiver
			
			AWSFacade awsFacade=new AWSFacade();
			DbConnection dbnewcon = new DbConnection();
			bucketname = dbnewcon.getBucketName(owneremail);
			fileLink = awsFacade.getShareLink(bucketname,shareFileName);
			System.out.println("Link genetated - "+fileLink);
			String receivermsgBody =owneremail+ " has shared a file - "+shareFileName+" with you. The link for the file is - "+fileLink;
			String receivermsgHeader = "File sharing successful";
			
			ui = new ConcreteUserInfo(shareemail);
			cm = new ConcreteMessage(receivermsgBody, receivermsgHeader);
			as = new AmazonSESSample();
			as.setConnec(ui, cm);
			return Response.status(200).entity(output).build();

	}

	
	@GET
	@Path("/logout")
	public Response logout(@Context HttpServletRequest req) {
		HttpSession session= req.getSession(false);
		session.invalidate();
		System.out.println("User has logged out !");
		String output = "User has succesfully logged out";
		
		 return Response.status(200).entity(output).build();
		
	}
	
}


