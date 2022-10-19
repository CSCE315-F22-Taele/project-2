
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
//import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

/** Inventory_view displays the inventory using java swing elements and jdbc queries to the database.
 * @author Asger Schelde Larsen
 * 
 */
public class inventory_view implements ActionListener{

    private static String[][] input_data;
    private static String[][] data;
    private static String columns[] = {"ID", "Name", "Current Count", "Maximum Count", "Sell Price", "Is Menu Item"};
    private static JFrame f = new JFrame("inventory GUI");
    private static JTable table;
    private static JTable input;
    private static JPanel p;

    public static void main(String[] args) {
        new inventory_view();
    }

    /** inventory_view() is the class constructor which initializes all java swing elements and sets the frame visible.
    * @author Asger Schelde Larsen
    * @param none
    * @return {String} sqlStatement
    */
    public inventory_view() {
        // create a new frame
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setLayout(new BoxLayout(f.getContentPane(), BoxLayout.Y_AXIS));

        // Setup the main JPanel for the inventory screen
        p = new JPanel(new BorderLayout(10,10));
        JPanel table_panel = new JPanel(new BorderLayout(10,10));

        // Create JPanel for table and title
        JLabel title = new JLabel("INVENTORY", SwingConstants.CENTER);
        title.setFont(new Font(title.getFont().toString(), Font.PLAIN, 25));
        table_panel.add(title,BorderLayout.PAGE_START);

        // setup table with data from database
        data = getData();
        table = new JTable(data,columns);
        table.setBounds(30,40,200,300);          
        JScrollPane sp=new JScrollPane(table);    
        table_panel.add(sp, BorderLayout.CENTER);
        p.add(table_panel, BorderLayout.PAGE_START);

        // Create JPanel for inputs
        JPanel input_panel = new JPanel(new BorderLayout(10,10));

        JLabel input_title = new JLabel("Edit inventory:", SwingConstants.CENTER);
        input_title.setFont(new Font(title.getFont().toString(), Font.PLAIN, 15));
        input_panel.add(input_title,BorderLayout.PAGE_START);

        // Setup table for input data and add to JPanel
        input_data = new String[1][6];
        input = new JTable(input_data,columns);
        input.setBounds(30,40,200,300);
        input_panel.add(input, BorderLayout.CENTER);

        p.add(input_panel, BorderLayout.CENTER);

        // Create buttons and actionlisteners
        JPanel pButtons = new JPanel(new FlowLayout());
        JButton addItem = new JButton("Add Item");
        JButton updateItem = new JButton("Update Item");
        JButton deleteItem = new JButton("Delete Item");
        JButton back = new JButton("Back");
        JButton stockReport = new JButton("Show Stock Report");

        addItem.addActionListener(this);
        updateItem.addActionListener(this);
        deleteItem.addActionListener(this);
        back.addActionListener(this);
        stockReport.addActionListener(this);

        // Add buttons to
        pButtons.add(addItem);
        pButtons.add(updateItem);
        pButtons.add(deleteItem);
        pButtons.add(stockReport);
        pButtons.add(back);

        p.add(pButtons, BorderLayout.PAGE_END);
        
        f.getContentPane().add(p);
        f.setSize(300,400);
        f.pack();
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setVisible(true);       
    }

    /** getData() sends a jdbc query to the database to get the inventory data and return it.
     * @author Asger Schelde Larsen
     * @param none
     * @return {String[][]} data
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

    /** actionPerformed() handles actions to beformed when a button is clicked. 
     *  When 'back' is clicked returns to manager_view.
     *  When 'Add Item' is clicked the input is added to the database.
     *  When 'Update Item' is clicked the changed inputs field are updated in the database.
     *  When 'Delete is clicked the item with the input ID is deleted in the database.'
     *  When 'Show Restock Report' is clicked a window showing the restock report is shown.
    * @author Asger Schelde Larsen
    * @param none
    * @return none
    */
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
        if (s.equals("Back")) {
            new manager_view();
            f.getContentPane().removeAll();
            f.repaint();
            f.dispose();
            p = null;
        } else if (s.equals("Add Item")) {
            try {
                Statement stmt = conn.createStatement();
                if (checkInputTypeOk()) {
                    String sqlStatement = makeInsert();
                    //Test if given food_id exists in inventory
                    if (checkIdExists(stmt) > 0) {
                        JOptionPane.showMessageDialog(null,"ID already exists.");
    
                    //if any input was entered.
                    } else if (!sqlStatement.isEmpty()) {
                        //send statement to DBMS
                        stmt.executeUpdate(sqlStatement);
                        updateTable();
                    } else {
                        // No updates where given in fields
                        JOptionPane.showMessageDialog(null,"Not enough info typed.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null,"Wrong type used in field.");
                }

            } catch (Exception exception) {
                exception.printStackTrace();
                System.err.println(exception.getClass().getName()+": "+exception.getMessage());
                System.exit(0);
            }
        } else if (s.equals("Update Item")) {
            try {
                Statement stmt = conn.createStatement();
                
                if (checkInputTypeOk()) {
                    String sqlStatement = makeQuery();
    
                    //Test if given food_id exists in inventory
                    if (checkIdExists(stmt) <= 0) {
                        JOptionPane.showMessageDialog(null,"ID does not exist.");
    
                    //if any input was entered.
                    } else if (!sqlStatement.isEmpty()) {
                        //send statement to DBMS
                        stmt.executeUpdate(sqlStatement);
                        updateTable();
                    } else {
                        // No updates where given in fields
                        JOptionPane.showMessageDialog(null,"No fields to update.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null,"Wrong type used in field.");
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                System.err.println(exception.getClass().getName()+": "+exception.getMessage());
                System.exit(0);
            }
        } else if ((s.equals("Delete Item"))) {
            try {
                Statement stmt = conn.createStatement();
                try {
                    // Check that ID inout can parse to integer.
                    Integer.parseInt(input_data[0][0]);

                    String sqlStatement = "DELETE FROM inventory WHERE food_id = " + input_data[0][0];

                    // Execute statement if ID does not already exist
                    if (checkIdExists(stmt) >= 0) {
                        stmt.executeUpdate(sqlStatement);
                        updateTable();
                    } else {
                        JOptionPane.showMessageDialog(null,"ID does not exist.");
                    }
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(null,"ID must be a number");
                }

            } catch (Exception exception) {
                exception.printStackTrace();
                System.err.println(exception.getClass().getName()+": "+exception.getMessage());
                System.exit(0);
            }
        } else if (s.equals("Show Stock Report")) {
            // frame
            JFrame frame;
            // Table
            JTable j;

            frame = new JFrame();

            // Frame Title
            frame.setTitle("Restock Report");

            // pull all the menu items from the inventory to be displayed
            String[][] inv = getRestockReport();

            // Initializing the JTable
            j = new JTable(inv, columns);
            j.setBounds(30, 40, 200, 500);

            // adding it to JScrollPane
            JScrollPane sp = new JScrollPane(j);
            frame.add(sp);
            // Frame Size
            frame.setSize(760, 375);
            frame.setVisible(true);
        }
    }

    /** getRestockReport() returns a 2D-array with all inventory items with a current_count lower or equal to 20% of max_count
    * @author Asger Schelde Larsen
    * @param none
    * @return {String[][]} data
    */
    private String[][] getRestockReport() {
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

        try{
            //create a statement object
            Statement stmt = conn.createStatement();

            String sqlStatement = "SELECT count(food_id) AS count FROM inventory WHERE current_count < (max_count/5)";

            //send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);
            result.next();
            int length = Integer.parseInt(result.getString("count"));
            data = new String[length][6];

            sqlStatement = "SELECT * FROM inventory WHERE current_count < (max_count/5) ORDER BY food_id ASC";
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

    /** updateTable() updates the Model for the table with newest data from the database.
    * @author Asger Schelde Larsen
    * @param none
    * @return none
    */
    public void updateTable() {
        String[][] data = getData();
        DefaultTableModel tableModel = new DefaultTableModel(data,columns);
        table.setModel(tableModel);
        tableModel.fireTableDataChanged();
        for (int i = 0; i < input_data[0].length; i++) {
            input_data[0][i] = null;
        }
    }

    /** makeQuery() generates the query statement from the input data given by the user.
    * @author Asger Schelde Larsen
    * @param none
    * @return {String} sqlStatement
    */
    public String makeQuery() {
        //Make query using input data
        boolean needForComma = false;
        String sqlStatement = "UPDATE inventory SET ";

        // Checking if Name has an input.
        if (input_data[0][1] != null && !input_data[0][1].isEmpty()) {
            sqlStatement += "food_name = '" + input_data[0][1]+"'";
            needForComma = true;
        }
        // Checking if current_count has an input.
        if (input_data[0][2] != null && !input_data[0][2].isEmpty()) {
            if (needForComma) {
                sqlStatement += ", ";
            }
            sqlStatement += "current_count = " + input_data[0][2];
            needForComma = true;
        }
        // Checking if max_count has an input.
        if (input_data[0][3] != null && !input_data[0][3].isEmpty()) {
            if (needForComma) {
                sqlStatement += ", ";
            }
            sqlStatement += "max_count = " + input_data[0][3];
            needForComma = true;
        }
        // Checking if price has an input.
        if (input_data[0][4] != null && !input_data[0][4].isEmpty()) {
            if (needForComma) {
                sqlStatement += ", ";
            }
            sqlStatement += "sell_price = " + input_data[0][4];
            needForComma = true;
        }
        // Checking if is_menu_item has an input.
        if (input_data[0][5] != null && !input_data[0][5].isEmpty()) {
            if (needForComma) {
                sqlStatement += ", ";
            }
            sqlStatement += "is_menu_item = '" + input_data[0][5] + "'";
            needForComma = true;
        }
        sqlStatement += " WHERE food_id = " + input_data[0][0];

        //return statement if any input was entered
        if (needForComma) {
            return sqlStatement;
        } else {
            return "";
        }
    }

    /** makeInsert() genreates an insert statement from the input data given by the user
    * @author Asger Schelde Larsen
    * @param none
    * @return {String} sqlStatement
    */
    public String makeInsert() {
        //Make query using input data
        boolean needForComma = false;
        String sqlStatement = "INSERT INTO inventory VALUES (";

        // Checking if ID has an input
        if (input_data[0][0] != null && !input_data[0][0].isEmpty()) {
            sqlStatement += input_data[0][0];
            needForComma = true;
        }
        // Checking if Name has an input. Otherwise replace with 'null'
        if (input_data[0][1] != null && !input_data[0][1].isEmpty()) {
            if (needForComma) {
                sqlStatement += ", ";
            }
            sqlStatement += "'" + input_data[0][1]+"'";
            needForComma = true;
        } else {
            if (needForComma) {
                sqlStatement += ", ";
            }
            sqlStatement += "null";
            needForComma = true;
        }
        // Checking if current_count has an input. Otherwise set to 0
        if (input_data[0][2] != null && !input_data[0][2].isEmpty()) {
            if (needForComma) {
                sqlStatement += ", ";
            }
            sqlStatement += input_data[0][2];
            needForComma = true;
        } else {
            if (needForComma) {
                sqlStatement += ", ";
            }
            sqlStatement += 0;
            needForComma = true;
        }
        // Checking if max_count has an input. Otherwise set to 0
        if (input_data[0][3] != null && !input_data[0][3].isEmpty()) {
            if (needForComma) {
                sqlStatement += ", ";
            }
            sqlStatement += input_data[0][3];
            needForComma = true;
        } else {
            if (needForComma) {
                sqlStatement += ", ";
            }
            sqlStatement += 0;
            needForComma = true;
        }
        // Checking if Price has an input. Otherwise set to 'null'
        if (input_data[0][4] != null && !input_data[0][4].isEmpty()) {
            if (needForComma) {
                sqlStatement += ", ";
            }
            sqlStatement += input_data[0][4];
            needForComma = true;
        } else {
            if (needForComma) {
                sqlStatement += ", ";
            }
            sqlStatement += "null";
            needForComma = true;
        }
        //Check if Is_menu_item has an input. Otherwise set to 'f'
        if (input_data[0][5] != null && !input_data[0][5].isEmpty()) {
            if (needForComma) {
                sqlStatement += ", ";
            }
            sqlStatement += "'" + input_data[0][5] + "'";
            needForComma = true;
        } else {
            if (needForComma) {
                sqlStatement += ", ";
            }
            sqlStatement += "'f'";
            needForComma = true;
        }
        sqlStatement += ")";

        //return statement if any input was entered
        if (needForComma) {
            return sqlStatement;
        } else {
            return "";
        }
    }

    /** checkIdExists() executes a query to check if the given ID exists in the inventory table. 
     *  Returns -1 if ID does not exist and 1 if the ID exists.
    * @author Asger Schelde Larsen
    * @param {Statement} stmt 
    * @return {int}
    */
    public int checkIdExists(Statement stmt) {
        String checkIdExistsStmt = "SELECT count(*) FROM inventory WHERE food_id = " + input_data[0][0];
            try {
                ResultSet result = stmt.executeQuery(checkIdExistsStmt);
                result.next();
                // Check the result can parse to integer
                return Integer.parseInt(result.getString("count"));
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName()+": "+e.getMessage());
                System.exit(0);
            }
            return -1;
    }

    /** checkInputTypeOk() checks that all user inputs can be parsed from String to the desired type.
    * @author Asger Schelde Larsen
    * @param none
    * @return {Boolean}
    */
    public boolean checkInputTypeOk() {
        //Check ID is not null or empty
        if (input_data[0][0] == null || input_data[0][0].isEmpty()) {
            return false;
        }
        //Check ID, current_count and max_count can convert to int
        try {
            Integer.parseInt(input_data[0][0]);
        } catch(Exception e) {
            return false;
        }
        if (input_data[0][2] != null && !input_data[0][2].isEmpty()) {
            try {
                Integer.parseInt(input_data[0][2]);
            } catch(Exception e) {
                return false;
            }
        }
        if (input_data[0][3] != null && !input_data[0][3].isEmpty()) {
            try {
                Integer.parseInt(input_data[0][3]);
            } catch(Exception e) {
                return false;
            }
        }
        //Check sell_price is float
        if (input_data[0][4] != null && !input_data[0][4].isEmpty()) {
            try {
                Float.valueOf(input_data[0][4]);
            } catch(Exception e) {
                return false;
            }
        }
        //Check is_menu_item is t or f
        if (input_data[0][5] != null && !input_data[0][5].isEmpty()) {
            if (input_data[0][5] == "t" || input_data[0][5] == "f") {
            } else {
                return false;
            }
        }
        return true;
    }
}