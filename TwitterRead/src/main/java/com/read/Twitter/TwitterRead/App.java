package com.read.Twitter.TwitterRead;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import org.bson.Document;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws TwitterException, JSONException, JsonParseException, JsonMappingException, JsonGenerationException, IOException
    {
    	
    	ConfigurationBuilder cb = new ConfigurationBuilder();
    	cb.setDebugEnabled(true)
    	  .setOAuthConsumerKey("")
    	  .setOAuthConsumerSecret("")
    	  .setOAuthAccessToken("")
    	  .setOAuthAccessTokenSecret("");
    	TwitterFactory tf = new TwitterFactory(cb.build());
    	Twitter twitter = tf.getInstance();
    	
    	
    	
    	// The factory instance is re-useable and thread safe.
        List<Status> statuses = twitter.getHomeTimeline();
        System.out.println("Showing home timeline.");
        List<Object> binaryStatus = new ArrayList<Object>();

        Mongo mongo = new Mongo("localhost", 27017);
		DB db = mongo.getDB("twitter");
		DBCollection collection = db.getCollection("twitterJava");
        for (Status status : statuses) {
            System.out.println(status.getUser().getName() + ":" +
                               status.getText());
            System.out.println("---------- size = "+ status.getMediaEntities().length+ " --------------------------------------");
            ObjectMapper objectMapper = new ObjectMapper();
            collection.insert((DBObject)JSON.parse((String) objectMapper.writeValueAsString(status)));
            binaryStatus.add(objectMapper.writeValueAsString(status));
            
        }
    
        
        
    
    }
    
    
    
}
