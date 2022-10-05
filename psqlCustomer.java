import java.sql.*;
import java.io.BufferedReader;  
import java.io.FileReader;  
import java.io.IOException;  

/*
CSCE 315
9-27-2021 Lab
 */
public class psqlCustomer {

  //Commands to run this script
  //This will compile all java files in this directory
  //javac *.java
  //This command tells the file where to find the postgres jar which it needs to execute postgres commands, then executes the code
  //Windows: java -cp ".;postgresql-42.2.8.jar" jdbcpostgreSQL
  //Mac/Linux: java -cp ".:postgresql-42.2.8.jar" jdbcpostgreSQL

  /*
  javac *java && java -cp ".:postgresql-42.2.8.jar" jdbcpostgreSQL 
  */


  //MAKE SURE YOU ARE ON VPN or TAMU WIFI TO ACCESS DATABASE
  public static void main(String args[]) {

    //Building the connection with your credentials
    Connection conn = null;
    String teamNumber = "22";
    String sectionNumber = "913";
    String dbName = "csce315_" + sectionNumber + "_" + teamNumber ;
    String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
    dbSetup myCredentials = new dbSetup(); 

    //Connecting to the database
    try {
        conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
     } catch (Exception e) {
        e.printStackTrace();
        System.err.println(e.getClass().getName()+": "+e.getMessage());
        System.exit(0);
     }

     System.out.println("Opened database successfully");
     String line = "";  
     String splitBy = ",";  
     int customer_number = 0;
     String prev_name = "";
     float total_cost = 0;
     int i = 0;
     boolean new_name = true;


     
     try{
       //create a statement object
       Statement stmt = conn.createStatement();

       //Running a query
       //TODO: update the sql command here s
      
      BufferedReader br = new BufferedReader(new FileReader("order_entries.csv"));  
      while ((line = br.readLine()) != null)   {  

        String[] order_entry = line.split(splitBy);    // use comma as separator  

       // System.out.println(order_entry[12]);
        //System.out.println(sqlStatement);

        //System.out.println("before execute");
        //stmt.executeUpdate(sqlStatement); //executeUpdate to get arround execption
        //System.out.println("after execute");

        //base case
        if (new_name) {
            if (customer_number != 0) {
                String sqlStatement = "INSERT INTO customer (customer_id, total_cost) VALUES (" + customer_number + ", " + total_cost + ")";
                System.out.println(sqlStatement);
                customer_number++;
                total_cost = 0;
                new_name = true;
                i++;
            } else {
                total_cost = Float.parseFloat(order_entry[12]);
            }
        }

        if ( prev_name.equals( order_entry[0] )) {
            total_cost += Float.parseFloat(order_entry[12]); //adding total cost 
            new_name = false;
        } else {

          if (i == 3) {break;}
        }

        prev_name = order_entry[0];

      }

      br.close();
   } catch (Exception e){
       e.printStackTrace();
       System.err.println(e.getClass().getName()+": "+e.getMessage());
       //System.exit(0);
   }

   
    //closing the connection
    try {
      conn.close();
      System.out.println("Connection Closed.");
    } catch(Exception e) {
      System.out.println("Connection NOT Closed.");
    }//end try catch
  }//end main
}//end Class
