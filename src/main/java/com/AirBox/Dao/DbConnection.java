package com.AirBox.Dao;

import com.AirBox.Domain.UploadObject;
import com.AirBox.Domain.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DbConnection {
	
	//private static String connectionString = "jdbc:mysql://airbox.cnnmp1jopl3g.us-west-1.rds.amazonaws.com:3306/airbox";
	private static String connectionString="jdbc:mysql://localhost:3306/airbox";
	private static String dbUsername = "root";
	//private static String dbPassword = "airboxroot";
	private static String dbPassword = "root";
	
public boolean loginCheck(String username, String password){
    String query;
    boolean login = false;

    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
        Statement stmt = (Statement) con.createStatement();
        query = "SELECT username, password FROM user_details WHERE username='" + username + "' AND password='" + password + "';";
        System.out.println("username"+username+"pwd"+password);
        stmt.executeQuery(query);
        ResultSet rs = stmt.getResultSet();
        login = rs.first(); //rs.first();
        con.close();
    } catch (InstantiationException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return login;
}


public String getBucketName (String username){
	
	String query;
	String bname = "nouser";
    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
        Statement stmt = (Statement) con.createStatement();
        //String uname = user.getUserName();
        query = "SELECT bucketname FROM user_details WHERE username ='"+username+"';";
        //query = "INSERT into user_details (first_name, last_name, username, password,bucketname) values ('"+user.getFirstName()+"','"+user.getLastName()+"','"+user.getUserName()+"','"+user.getPassword()+"','"+user.getBucketname()+"')";
        ResultSet rs = stmt.executeQuery(query);
        //System.out.println("Bucketname for User "+user.getUserName()+" is "+);
        if(rs.next()){
        	bname = rs.getString("bucketname");
        	//bname = (String) rs.getObject(1);
        	
        }else
        	System.out.println("No user found for username = "+username+"  ");
        System.out.println("Inside DbConnection - testing bucket name: " +bname);
       con.close();
    } catch (InstantiationException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return bname;
	
}

public User getUserDetails (String uname){
	
	String query;
	//String bname = "nouser";
	//List<String> nl = new ArrayList<String>();
	String first_name, last_name,username, password, bucketname;
	User userD = new User();
    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
        Statement stmt = (Statement) con.createStatement();
        //String uname = user.getUserName();
        query = "SELECT * FROM user_details WHERE username ='"+uname+"';";
        //query = "INSERT into user_details (first_name, last_name, username, password,bucketname) values ('"+user.getFirstName()+"','"+user.getLastName()+"','"+user.getUserName()+"','"+user.getPassword()+"','"+user.getBucketname()+"')";
        ResultSet rs = stmt.executeQuery(query);
        //System.out.println("Bucketname for User "+user.getUserName()+" is "+);
        /*if(rs.next()){
        	bname = rs.getString("bucketname");
        	//bname = (String) rs.getObject(1);
        	
        }else
        	System.out.println("No user found for username = "+username+"  ");
        System.out.println("Inside DbConnection - testing bucket name: " +bname);*/
        //int numColumns = rs.getMetaData().getColumnCount();
        
        if ( rs.next() ) {
            
        	 first_name = rs.getString("first_name");
        	 last_name = rs.getString("last_name");
        	 username = rs.getString("username");
        	 password = rs.getString("password");
        	 bucketname = rs.getString("bucketname");
             userD.setFirstName(first_name);
             userD.setLastName(last_name);
             userD.setUserName(username);
             userD.setPassword(password);
             userD.setBucketname(bucketname);
        } else{
        	return null;
        }
        con.close();
       
    } catch (InstantiationException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    //return bname;
    return userD;
	
}



public List<UploadObject> getFileDetails (String uname){
	
	
	String query;
	//String bname = "nouser";
	String  username, filename;
	long filesize;
	Date date_created;
	//List<String> nl = new ArrayList<String>();
	List<UploadObject> fileDetailsList = new ArrayList<UploadObject>();	

	UploadObject fileDetailObject ;

	

    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
        Statement stmt = (Statement) con.createStatement();
        //String uname = user.getUserName();
        query = "SELECT * FROM file_details WHERE username ='"+uname+"';";
        //query = "INSERT into user_details (first_name, last_name, username, password,bucketname) values ('"+user.getFirstName()+"','"+user.getLastName()+"','"+user.getUserName()+"','"+user.getPassword()+"','"+user.getBucketname()+"')";
        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Bucketname for User ");
        /*if(rs.next()){
        	bname = rs.getString("bucketname");
        	//bname = (String) rs.getObject(1);
        	
        }else
        	System.out.println("No user found for username = "+username+"  ");
        System.out.println("Inside DbConnection - testing bucket name: " +bname);*/
        
        /*while ( rs.next() ) {
            int numColumns = rs.getMetaData().getColumnCount();
            for ( int i = 1 ; i <= numColumns ; i++ ) {
              
               
               nl.add((String)rs.getObject(i));
               System.out.println( "COLUMN " + i + " = " + rs.getObject(i) );
            }
        }*/
        
        //int numColumns = rs.getMetaData().getColumnCount();
        /*rs.last();
        int rowcount = 0;
        rowcount = rs.getRow();

        System.out.println("row count "+rowcount);
        rs.beforeFirst();*/
        
       // DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //Date date = new Date();
        //System.out.println(dateFormat.format(date));
        /*
        	

        rs.beforeFirst();*/
        

        	while(rs.next()){
        		
        		
        		username = rs.getString("username");
        		System.out.println("username "+username);
        		filename = rs.getString("filename");
        		System.out.println("filename "+filename);
        		filesize = rs.getLong("filesize");
        		System.out.println("filesize "+filesize);
        		date_created =rs.getDate("date_created");

        		System.out.println("date "+date_created);

        		fileDetailObject = new UploadObject();
        		fileDetailObject.setUsername(username);
        		fileDetailObject.setFileName(filename);
        		long convfileSize = filesize;
        		fileDetailObject.setSize(convfileSize);
        		fileDetailObject.setDateCreated(date_created);
        		
        		fileDetailsList.add(fileDetailObject);
        	}
        
        	con.close();
       
    } catch (InstantiationException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    //return bname;
    //return nl;
    
    return fileDetailsList;
	
}


public List<UploadObject> getShareFileDetails (String username){
	
	
	String query;
	String filename;
	String shareuser;
	Date shareddate;
	List<UploadObject> fileDetailsList = new ArrayList<UploadObject>();	
	UploadObject fileDetailObject;
    try {
    	
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
        Statement stmt = (Statement) con.createStatement();
        query = "SELECT * FROM share_details WHERE ownername ='"+username+"';";
        ResultSet rs = stmt.executeQuery(query);
       
        
       while(rs.next()){
        		
        		username = rs.getString("ownername");
        		shareuser = rs.getString("shareuser");
        		filename = rs.getString("filename");
        		shareddate =rs.getDate("shareddate");
        		fileDetailObject = new UploadObject();
        		fileDetailObject.setFileName(filename);
        		fileDetailObject.setUsername(shareuser);
        		fileDetailObject.setDateCreated(shareddate);
        		
        		fileDetailsList.add(fileDetailObject);
        	}
              	
       con.close();
       
    } catch (InstantiationException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    
    return fileDetailsList;
	
}


public String getTotalSize (String username){
	
	String query;
	String totalSize=null ;
    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
        Statement stmt = (Statement) con.createStatement();

        query = "select * from (SELECT (sum(filesize)/1048576) *100 as num FROM airbox.file_details where username='"+ username +"' UNION SELECT 1048576 as num) t where t.num is not null limit 1";

       System.out.println("sharefile"); 
        ResultSet rs = stmt.executeQuery(query);
        
        while(rs.next()){
        	totalSize = (rs.getString(1));
        	
        }
       
        System.out.println("The total size of bucket of username = "+username+" is" + totalSize  );
        con.close();
       
    } catch (InstantiationException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return totalSize;
	
}




public boolean shareCheck(String username){
    String query;
    boolean share = false;

    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
        Statement stmt = (Statement) con.createStatement();
        query = "SELECT * FROM share_details WHERE ownername='" + username + "';";
        
        stmt.executeQuery(query);
        ResultSet rs = stmt.getResultSet();
        share = rs.first(); //rs.first();
        con.close();
        
    } catch (InstantiationException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return share;
}



public List<UploadObject> getSharePerc (String username){
	
	
	String query;
	String shareuser;
	Long perc;
	List<UploadObject> fileDetailsList = new ArrayList<UploadObject>();	
	UploadObject fileDetailObject;
    try {
    	
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
        Statement stmt = (Statement) con.createStatement();

        query ="SELECT t.shareuser as shareuser, ROUND((t.num/t.total)*100,0) AS perc FROM ( SELECT shareuser, count(*) num,(select count(*) from airbox.share_details where ownername ='"+ username +"' ) as total FROM airbox.share_details where ownername ='"+ username +"' group by shareuser )t;";
        
        ResultSet rs = stmt.executeQuery(query);
       
        
       while(rs.next()){
        		
        		shareuser = rs.getString("shareuser");
        		perc =rs.getLong("perc");
        		fileDetailObject = new UploadObject();
        		fileDetailObject.setUsername(shareuser);
        		fileDetailObject.setSize(perc);
        		System.out.println("perc"+ perc);
        		fileDetailsList.add(fileDetailObject);
        	}
        
       con.close();
        
        
       
    } catch (InstantiationException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    
    return fileDetailsList;
	
}



public void insertUser(User user){
    String query;
    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
        Statement stmt = (Statement) con.createStatement();
        query = "INSERT into user_details (first_name, last_name, username, password, bucketname) values ('"+user.getFirstName()+"','"+user.getLastName()+"','"+user.getUserName()+"','"+user.getPassword()+"','"+user.getBucketname()+"')";
        stmt.executeUpdate(query);
        System.out.println("User Inserted successfully");
        con.close();
    } catch (InstantiationException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public boolean insertFiledata(UploadObject file, String username){
    String query1;
    String query2;
    boolean fileinsert = false;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date date = new Date();
	long filesize= file.getSize()/1024;
	long allowedSize =0;
    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
        Statement stmt = (Statement) con.createStatement();
        
        query1="SELECT * FROM (SELECT 1048576-sum(filesize) as num FROM airbox.file_details WHERE username='"+ username +"' UNION SELECT 1048576 as num) t where t.num is not null limit 1";
        
        query2 = "INSERT into file_details (username, filename, filesize, date_created) values ('"+username+"','"+file.getFileName()+"','"+filesize+"','"+dateFormat.format(date)+"')";
          
        
        ResultSet rs = stmt.executeQuery(query1);
        while(rs.next()){
        allowedSize = Long.parseLong(rs.getString(1));
        System.out.println("allowedSize" + allowedSize);
        
        }
        if (filesize< allowedSize)
        {
        	stmt.executeUpdate(query2);
        	System.out.println("File Inserted");
        	fileinsert=true;
        }
        else
        {
        System.out.println("file can not be inserted");}
        con.close();
        
    } catch (InstantiationException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return fileinsert;
    
}

public void shareFile(String username, String filename, String shareemail){
    String query;
    String uname = username;
    String fname = filename;
    String smail = shareemail;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date date = new Date();
    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
        Statement stmt = (Statement) con.createStatement();
        query = "INSERT into share_details (ownername, shareuser, filename, shareddate) values ('"+uname+"','"+smail+"','"+fname+"','"+dateFormat.format(date)+"')";
        System.out.println("file inserted in db");
        stmt.executeUpdate(query);
        con.close();
    } catch (InstantiationException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public void deleteFile(String username, String filename){
	String query;
    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
        Statement stmt = (Statement) con.createStatement();
        query = "delete from file_details WHERE username='" + username + "' AND filename='" + filename + "'";
        stmt.executeUpdate(query);
        con.close();
    } catch (InstantiationException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}




}

