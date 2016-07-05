package System;

import Internet.Address;


public class Report {

    String message;
    String reportDate;
    String reason;
    String user;
    Address address;
    
    public Report( String inputMessage , String inputReason , String inputUser , Address inputAddress ){
        
        reportDate = Time.getDateTime();
        message = inputMessage;
        reason = inputReason;
        user = inputUser;
        Address address = inputAddress;
        
    }
    
    public Report( String inputMessage , String inputReason ){

        this( inputMessage , inputReason , System.getProperty( "user.name" ) , new Address() );
        
    }
    
    public Report( String inputMessage ){
    
        this( inputMessage , "Unknown" );
        
    }
    
    public Report( String inputMessage , Exception inputReason ){
        
        this( inputMessage , inputReason.getMessage() );
        
    }

}
