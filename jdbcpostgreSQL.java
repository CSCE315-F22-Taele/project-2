import java.sql.*;
import java.io.BufferedReader;  
import java.io.FileReader;  
import java.io.IOException;  

/*
CSCE 315
9-27-2021 Lab
 */
public class jdbcpostgreSQL {

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
     int order_number = 0;
     int customer_number = 0;
     String prev_name = "";
     try{
       //create a statement object
       Statement stmt = conn.createStatement();

       //Running a query
       //TODO: update the sql command here s
      
      BufferedReader br = new BufferedReader(new FileReader("order_entries.csv"));  

      while ((line = br.readLine()) != null)   {  

        String[] order_entry = line.split(splitBy);    // use comma as separator  
        String sqlStatement = "INSERT INTO order_entries (order_number, customer, base, protein, guacamole, queso, chips_salsa, chips_queso, chips_guac, brownie, cookie, drink_16oz, drink_22oz, cost, date) VALUES (" + order_number + ", " + customer_number + ", \'" + order_entry[1] + "\', \'" + order_entry[2] + "\', \'" + order_entry[3] + "\', \'" + order_entry[4] + "\', \'" + order_entry[5] + "\', \'" + order_entry[6] + "\', \'" + order_entry[7] + "\', \'" + order_entry[8] + "\', \'" + order_entry[9] + "\', \'" + order_entry[10] + "\', \'" + order_entry[11] + "\', " + order_entry[12] + ", \'" + order_entry[13] + "\')";

        System.out.println(sqlStatement);
        //System.out.println(order_entry[0] +  " " + customer_number);
        //order_entry[0] == name

        stmt.executeQuery(sqlStatement); //add to data base 
        order_number++;

        if ( prev_name.equals( order_entry[0] )) {
          //System.out.println("same name");
        } else {
          customer_number++;
        }

        prev_name = order_entry[0];
        //send statement to DBMS
        //This executeQuery command is useful for data retrieval

        //ResultSet result = stmt.executeQuery(sqlStatement);

        //OUTPUT
        //You will need to output the results differently depeninding on which function you use
        //System.out.println("--------------------Query Results--------------------");
        //System.out.println(result);

      }

      br.close();
   } catch (Exception e){
       e.printStackTrace();
       System.err.println(e.getClass().getName()+": "+e.getMessage());
       System.exit(0);
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
