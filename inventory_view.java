
import java.sql.*;
import java.util.ArrayList;
//import java.awt.event.*;
import javax.swing.*;

/* Inventory_view displays the inventory using java swing elements and jdbc queries to the database.
 * @author Asger Schelde Larsen
 * 
 */
public class inventory_view extends JFrame{

    public static void main(String[] args) {
        // create a new frame
        JFrame f = new JFrame("inventory GUI");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[][] data = getData();
        
        String columns[] = {"food_id", "food_name", "current_count", "max_count", "sell_price"};

        JTable table = new JTable(data,columns);
        table.setBounds(30,40,200,300);          
        JScrollPane sp=new JScrollPane(table);    
        f.add(sp);

        f.setSize(300,400);    
        f.setVisible(true);       
        
    }

/* getData() sends a jdbc query to the database to get the inventory data and return it
 * @author Asger Schelde Larsen
 * @param none
 * @return {ArrayList<String[]>} data
 */

    static String[][] getData() {
        String[][] data = new String[0][0];
        Connection conn = null;
        String teamNumber = "22";
        String sectionNumber = "913";
        String dbName = "csce315_" + sectionNumber + "_" + teamNumber ;
        String dbConnectionString = "jdbc.;postgresql://csce-315-db.engr.tamu.edu/" + dbName;

        //Connecting to the database
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315_913_22",
               "csce315_913_larsen", "633001563");
          } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
          }

        System.out.println("Opened database successfully");

        try{
            //create a statement object
            Statement stmt = conn.createStatement();

            String sqlStatement = "SELECT count(food_id) AS count FROM inventory;";

            //send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);
            result.next();
            int length = Integer.parseInt(result.getString("count"));
            data = new String[length][5];

            sqlStatement = "SELECT * FROM inventory;";
            //send statement to DBMS
            result = stmt.executeQuery(sqlStatement);
            
            int entry_nr = 0;
            while (result.next()) {
                data[entry_nr][0] = result.getString("food_id")+"\n";
                data[entry_nr][1] = result.getString("food_name")+"\n";
                data[entry_nr][2] = result.getString("current_count")+"\n";
                data[entry_nr][3] = result.getString("max_count")+"\n";
                data[entry_nr][4] = result.getString("sell_price")+"\n";
                entry_nr++;
                System.out.println(data[0]+","+data[1]+","+data[2]+","+data[3]+","+data[4]);
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error accessing Database.");
        }
        return data;
    }
}