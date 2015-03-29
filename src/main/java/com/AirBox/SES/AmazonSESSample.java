package com.AirBox.SES;
import java.io.IOException;




//import com.AirBox.rest.MyConfig;
import com.amazonaws.services.simpleemail.*;
import com.amazonaws.services.simpleemail.model.*;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.regions.*;

public class AmazonSESSample {
 
    String FROM = "sumantmurke@gmail.com";  // Replace with your "From" address. This address must be verified.
    String TO = null; // Replace with a "To" address. If you have not yet requested
                                                      // production access, this address must be verified.
    String BODY = null;
    String SUBJECT = null;
  
    ConcreteMessage message ;
    ConcreteUserInfo userInfo ;
    
    //private final MyConfig conf = new MyConfig();
    
    public void setConnec(ConcreteUserInfo userInfo, ConcreteMessage message){
    	
    	AWSCredentials myCredentials = new BasicAWSCredentials(MyConfig2.getMyAccessId(), MyConfig2.getMySecretId());
    	this.message = message;
    	this.userInfo = userInfo;
    	
    	
    	TO = userInfo.receiversEmail;
    	BODY = message.msgBody;
    	SUBJECT = message.msgSubject;
    	
    	Destination destination = new Destination().withToAddresses(new String[]{TO});
    	Content subject = new Content().withData(SUBJECT);
        Content textBody = new Content().withData(BODY); 
        Body body = new Body().withText(textBody);
        Message msg = new Message().withSubject(subject).withBody(body);
        
        SendEmailRequest request = new SendEmailRequest().withSource(FROM).withDestination(destination).withMessage(msg);
        
        try
        {        
            System.out.println("Attempting to send an email through Amazon SES ");
        
            // Instantiate an Amazon SES client, which will make the service call with the supplied AWS credentials.
            AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient(myCredentials);
            
            System.out.println("after connecting to ses client and before defining REGION variable as US_WEST_2");
    
            Region REGION = Region.getRegion(Regions.US_WEST_2);
            
            System.out.println("before setting the region");
            client.setRegion(REGION);
            
            System.out.println("before sending the email");
       
            // Send the email.
            client.sendEmail(request);  
            System.out.println("Email sent!");
        }
        catch (Exception ex) 
        {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
    }
    
    

   /* public static void main(String[] args) throws IOException {
    	
        // Your AWS credentials are stored in the AwsCredentials.properties file within the project.
        // You entered these AWS credentials when you created a new AWS Java project in Eclipse.
        PropertiesCredentials credentials = new PropertiesCredentials(
        		AmazonSESSample.class
                        .getResourceAsStream("AwsCredentials.properties"));
    	AWSCredentials myCredentials = new BasicAWSCredentials(
				MyConfig.getMyAccessId(), MyConfig.getMySecretId());
        
        // Retrieve the AWS Access Key ID and Secret Key from AwsCredentials.properties.
        credentials.getAWSAccessKeyId();
        credentials.getAWSSecretKey();
    
        // Construct an object to contain the recipient address.
        Destination destination = new Destination().withToAddresses(new String[]{TO});
        
        // Create the subject and body of the message.
        Content subject = new Content().withData(SUBJECT);
        Content textBody = new Content().withData(BODY); 
        Body body = new Body().withText(textBody);
        
        // Create a message with the specified subject and body.
        Message message = new Message().withSubject(subject).withBody(body);
        
        // Assemble the email.
        SendEmailRequest request = new SendEmailRequest().withSource(FROM).withDestination(destination).withMessage(message);
        
        try
        {        
            System.out.println("Attempting to send an email through Amazon SES ");
        
            // Instantiate an Amazon SES client, which will make the service call with the supplied AWS credentials.
            AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient(myCredentials);
               
    
            Region REGION = Region.getRegion(Regions.US_WEST_2);
            client.setRegion(REGION);
       
            // Send the email.
            client.sendEmail(request);  
            System.out.println("Email sent!");
        }
        catch (Exception ex) 
        {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
    }*/
}