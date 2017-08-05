package caprica.main;

import caprica.datatypes.Num;
import caprica.datatypes.SystemFile;
import caprica.system.Output;
import caprica.system.SystemInformation;
import java.util.ArrayList;

public class Banker {

    public static void main( String[] arguments ){
        
        double rent = 580.14 * 2;
        double pay = 1095 * 2;
        double postRent = pay - rent;
        
        Output.print( "It's bankin time" );
        Output.print( "Reading bank data" );
        
        SystemFile bankFolder = new SystemFile( SystemInformation.getAppData() + "bank/" );
        
        String rawData = "";
        
        for ( SystemFile bankFile : bankFolder.listContents() ){
            
            rawData += bankFile.toString() + "\n";
            
        }
        
        Output.print( "Processing raw bank data" );
        
        ArrayList< Transaction > transactions = new ArrayList<>();
        
        String date, place;
        double amount;
        
        for ( String bankLine : rawData.split( "\n" ) ){
            
            if ( bankLine.length() > 5 ){
                
                String[] data = bankLine.split( ";" );
                
                date = data[ 0 ];
                place = data[ 1 ];
                amount = Double.parseDouble( data[ 2 ] );
                
                boolean alreadyHas = false;
                
                for ( Transaction transaction : transactions ){
                    
                    if ( transaction.place.equals( place ) && transaction.date.equals( date ) && transaction.amount == amount ){
                        
                        alreadyHas = true;
                        break;
                        
                    }
                    
                }
                
                if ( !alreadyHas ){
                    
                    transactions.add( new Transaction( date , place, amount ) );
                    
                }
                
            }
            
        }
        
        Output.print( "Processed " + transactions.size() + " transactions" );
        
        String[] months = new String[]{ "05" , "06" , "07" };
        String[] monthNames = new String[]{ "May" , "June" , "July" };
        
        String[] foodIdent = new String[]{ "WAL-MART" , "HORTONS" , "MENU" , "MARCELLO" };
        
        double totalSpend, foodSpend;
        int longestDay;
        
        for ( int i = 0 ; i < months.length ; i++ ){
            
            Output.print( "[" + monthNames[ i ] + "]" );
            
            totalSpend = 0;
            foodSpend = 0;
            longestDay = 0;
            
            for ( Transaction transaction : transactions ){
                
                if ( transaction.date.split( "-" )[ 1 ].equals( months[ i ] ) ){
                    
                    if ( Integer.parseInt( transaction.date.split( "-" )[ 0 ] ) > longestDay ){
                        
                        longestDay = Integer.parseInt( transaction.date.split( "-" )[ 0 ] );
                        
                    }
                    
                    totalSpend += transaction.amount;
                    
                    for ( String foodName : foodIdent ){
                        
                        if ( transaction.place.contains( foodName ) ){
                            
                            foodSpend += transaction.amount;
                            break;
                            
                        }
                        
                    }
                    
                }
                
            }
            
            double leftOver = postRent - totalSpend;
            
            Output.print( "Total spend:" + new Num( totalSpend ).toNiceString() );
            Output.print( "Food spend:" + new Num( foodSpend ).toNiceString() );
            Output.print( "Leftover:" + new Num( leftOver ).toNiceString() );
        
            if ( i == months.length - 1 ){
                
                int daysRemaining = 31 - longestDay;
                double dailySpend = totalSpend / longestDay;
                double expectedSpend = dailySpend * daysRemaining;
                
                Output.print( "Expected leftover after month: " + new Num( expectedSpend ).toNiceString() );
                
            }
            
        }
        
    }
    
    public static class Transaction {
    
        String date, place;
        double amount;
        
        public Transaction( String date , String place , double amount ){
            
            this.date = date;
            this.place = place;
            this.amount = amount;
            
        }
       
    }
    
}
