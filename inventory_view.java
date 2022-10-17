
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
//import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
/* Inventory_view displays the inventory using java swing elements and jdbc queries to the database.
 * @author Asger Schelde Larsen
 * 
 */
public class inventory_view implements ActionListener{

    static String[][] input_data;
    static String[][] data;
    static String columns[] = {"ID", "Name", "Current Count", "Maximum Count", "Sell Price", "Is Menu Item"};
    static JFrame f = new JFrame("inventory GUI");
    static JTable table;

    public static void main(String[] args) {
        // create a new frame
        
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.getContentPane().setLayout(new BoxLayout(f.getContentPane(), BoxLayout.Y_AXIS));


        data = getData();

        table = new JTable(data,columns);
        table.setBounds(30,40,200,300);          
        JScrollPane sp=new JScrollPane(table);    
        f.getContentPane().add(sp);

        JPanel p = new JPanel(new BorderLayout());

        input_data = new String[1][6];
        JTable input = new JTable(input_data,columns);
        input.setBounds(30,40,200,300);
        p.add(input, BorderLayout.PAGE_START);

        JPanel pButtons = new JPanel(new FlowLayout());
        JButton addItem = new JButton("Add Item");
        JButton updateItem = new JButton("Update Item");
        JButton deleteItem = new JButton("Delete Item");

        inventory_view iv = new inventory_view();
        addItem.addActionListener(iv);
        updateItem.addActionListener(iv);
        deleteItem.addActionListener(iv);

        pButtons.add(addItem);
        pButtons.add(updateItem);
        pButtons.add(deleteItem);

        p.add(pButtons, BorderLayout.CENTER);
        
        f.getContentPane().add(p);
        f.setSize(300,400);
        f.pack();
        f.setVisible(true);       
        f.setBounds(100, 100, 768, 768);
        
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

        //System.out.println("Opened database successfully");

        try{
            //create a statement object
            Statement stmt = conn.createStatement();

            String sqlStatement = "SELECT count(food_id) AS count FROM inventory;";

            //send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);
            result.next();
            int length = Integer.parseInt(result.getString("count"));
            data = new String[length][6];

            sqlStatement = "SELECT * FROM inventory ORDER BY food_id ASC";
            //send statement to DBMS
            result = stmt.executeQuery(sqlStatement);
            
            int entry_nr = 0;
            while (result.next()) {
                data[entry_nr][0] = result.getString("food_id")+"\n";
                data[entry_nr][1] = result.getString("food_name")+"\n";
                data[entry_nr][2] = result.getString("current_count")+"\n";
                data[entry_nr][3] = result.getString("max_count")+"\n";
                data[entry_nr][4] = result.getString("sell_price")+"\n";
                data[entry_nr][5] = result.getString("is_menu_item")+"\n";
                entry_nr++;
            }
            conn.close();
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error accessing Database.");
        }
        return data;
    }

    // if button is pressed
    public void actionPerformed(ActionEvent e)
    {
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
        } catch (Exception exception) {
            exception.printStackTrace();
            System.err.println(exception.getClass().getName()+": "+exception.getMessage());
            System.exit(0);
        }
        
        String s = e.getActionCommand();
        if (s.equals("Add Item")) {
            try {
                Statement stmt = conn.createStatement();

                String sqlStatement = "INSERT INTO inventory VALUES (" + input_data[0][0] + ",'" + input_data[0][1] + "'," + input_data[0][2] + "," + input_data[0][3] + "," + input_data[0][4] + "," + input_data[0][5] + ");";
                //System.out.println(sqlStatement);
                //send statement to DBMS
                stmt.executeUpdate(sqlStatement);

                updateTable();

            } catch (Exception exception) {
                exception.printStackTrace();
                System.err.println(exception.getClass().getName()+": "+exception.getMessage());
                System.exit(0);
            }
        } else if (s.equals("Update Item")) {
            try {
                Statement stmt = conn.createStatement();
                
                //checkInputTypeOk();
                
                String sqlStatement = makeQuery();
                System.out.println(sqlStatement);

                //Test if given food_id exists in inventory
                if (checkIdExists(stmt) <= 0) {
                    JOptionPane.showMessageDialog(null,"ID does not exist");

                //if any input was entered.
                } else if (!sqlStatement.isEmpty()) {
                    //send statement to DBMS
                    stmt.executeUpdate(sqlStatement);
                    updateTable();
                } else {
                    // No updates where given in fields
                    JOptionPane.showMessageDialog(null,"No fields to update.");
                }

            } catch (Exception exception) {
                exception.printStackTrace();
                System.err.println(exception.getClass().getName()+": "+exception.getMessage());
                System.exit(0);
            }
        } else if ((s.equals("Delete Item"))) {
            try {
                Statement stmt = conn.createStatement();

                String sqlStatement = "DELETE FROM inventory WHERE food_id = " + input_data[0][0] +";";
                stmt.executeUpdate(sqlStatement);
                updateTable();

            } catch (Exception exception) {
                exception.printStackTrace();
                System.err.println(exception.getClass().getName()+": "+exception.getMessage());
                System.exit(0);
            }
        }
    }

    public void updateTable() {
        String[][] data = getData();
        DefaultTableModel tableModel = new DefaultTableModel(data,columns);
        table.setModel(tableModel);
        tableModel.fireTableDataChanged();     
    }

    public String makeQuery() {
        //Make query using input data
        boolean needForComma = false;
        String sqlStatement = "UPDATE inventory SET ";
        if (input_data[0][1] != null && !input_data[0][1].isEmpty()) {
            sqlStatement += "food_name = '" + input_data[0][1]+"'";
            needForComma = true;
        }
        if (input_data[0][2] != null && !input_data[0][2].isEmpty()) {
            if (needForComma) {
                sqlStatement += ", ";
            }
            sqlStatement += "current_count = " + input_data[0][2];
            needForComma = true;
        }
        if (input_data[0][3] != null && !input_data[0][3].isEmpty()) {
            if (needForComma) {
                sqlStatement += ", ";
            }
            sqlStatement += "max_count = " + input_data[0][3];
            needForComma = true;
        }
        if (input_data[0][4] != null && !input_data[0][4].isEmpty()) {
            if (needForComma) {
                sqlStatement += ", ";
            }
            sqlStatement += "sell_price = " + input_data[0][4];
            needForComma = true;
        }
        if (input_data[0][5] != null && !input_data[0][5].isEmpty()) {
            if (needForComma) {
                sqlStatement += ", ";
            }
            sqlStatement += "is_menu_item = '" + input_data[0][5] + "'";
            needForComma = true;
        }
        sqlStatement += " WHERE food_id = " + input_data[0][0] + ";";

        //return statement if any input was entered
        if (needForComma) {
            return sqlStatement;
        } else {
            return "";
        }
    }

    public int checkIdExists(Statement stmt) {
        String checkIdExistsStmt = "SELECT count(*) FROM inventory WHERE food_id = " + input_data[0][0];
            try {
                ResultSet result = stmt.executeQuery(checkIdExistsStmt);
                result.next();
                return Integer.parseInt(result.getString("count"));
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName()+": "+e.getMessage());
                System.exit(0);
            }
            return -1;
    }

    public boolean checkInputTypeOk() {
        for (int i = 0; i < input_data[0].length; i++) {
            //Check ID is not null or empty
            //
            if (input_data[0][i] == null ) {

            }
        }

        return false;
    }
}