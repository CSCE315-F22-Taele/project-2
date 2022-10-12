
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

        ArrayList<String[]> data = getData();
        String[][] data_final;
        data_final = data.stream().toArray(String[][]::new);
        
        String columns[] = {"order_number", "customer", "base", "protein", "guacamole",  "queso", "chips_salsa", "chips_queso", "chips_guac", "brownie", "cookie", "drink_16oz", "drink_22oz", "cost date", "food_id", "food_name", "current_count", "max_count", "sell_price"};

        JTable table = new JTable(data_final,columns);
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

    static ArrayList<String[]> getData() {
        ArrayList<String[]> data;
        data = new ArrayList<String[]>();
        String[] entry = new String[20];
        Connection conn = null;
        String teamNumber = "22";
        String sectionNumber = "913";
        String dbName = "csce315_" + sectionNumber + "_" + teamNumber ;
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;

        //Connecting to the database
        try {
            conn = DriverManager.getConnection(dbConnectionString, "csce315_913_larsen", "633001563");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        System.out.println("Opened database successfully");

        try{
            //create a statement object
            Statement stmt = conn.createStatement();
            String sqlStatement = "SELECT * FROM inventory;";
            //send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);
            
            while (result.next()) {
                entry[0] = result.getString("food_id")+"\n";
                entry[1] = result.getString("food_name")+"\n";
                entry[2] = result.getString("current_count")+"\n";
                entry[3] = result.getString("max_count")+"\n";
                entry[4] = result.getString("sell_price")+"\n";
                data.add(entry);
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error accessing Database.");
        }
        return data;
    }
}