package com.AirBox.rest;

import java.util.HashMap;
import java.util.Map;

import com.AirBox.SES.MyConfig2;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.util.Tables;





public class dynamo {
	
	public static  String createConnection(){

		AWSCredentials Credentials = new BasicAWSCredentials(MyConfig2.getMyAccessId(), MyConfig2.getMySecretId());
		System.out.println("Credentials taken...");
		AmazonDynamoDBClient dynamoDB = new AmazonDynamoDBClient(Credentials);
		Region usWest2 = Region.getRegion(Regions.US_WEST_1);
		dynamoDB.setRegion(usWest2);
		System.out.println("dyan connected...");

		String tableName = "User";
		DescribeTableRequest describeTableRequest = new DescribeTableRequest().withTableName(tableName);
		TableDescription tableDescription = dynamoDB.describeTable(describeTableRequest).getTable();
		System.out.println("Table Description: " + tableDescription);
		
		String output ="successfully dyna connected";
		
	
		
	     
		
		
		return output;
		  
	}  
	  
	public Object addItem(String firstname, String lastname, String username, String password){
		String firstName = firstname;
		String lastName = lastname;
		String userName = username;
		String Password = password;

		
		AWSCredentials Credentials = new BasicAWSCredentials(MyConfig2.getMyAccessId(), MyConfig2.getMySecretId());
		System.out.println("Credentials taken...");
		AmazonDynamoDBClient dynamoDB = new AmazonDynamoDBClient(Credentials);
		Region usWest2 = Region.getRegion(Regions.US_WEST_1);
		dynamoDB.setRegion(usWest2);
		System.out.println("dyan connected...");
		
		String tableName = "User";
		
		Map<String, AttributeValue> item = newItem(firstName, lastName, userName, Password);
        PutItemRequest putItemRequest = new PutItemRequest(tableName, item);
        PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
        System.out.println("Result: " + putItemResult);
		
		return null;
	}
	

	
	 private static Map<String, AttributeValue> newItem(String firstname, String lastname, String username, String password) {
	        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
	        item.put("firstname", new AttributeValue(firstname));
	        item.put("lastname", new AttributeValue().withS(lastname));
	        item.put("username", new AttributeValue().withS(username));
	        item.put("password", new AttributeValue().withS(password));
	        
	        System.out.println("object "+item.toString());
	        return item;
	    }
	
}
