import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/* manager_view displays the total daily sales, total sales to date,
 * to view current transactions, and view the charts and graphs of our
 * most sold items and revenue growth.
 *
 * @author Jonathan Kutsch
 *
 */


public class manager_view extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JLabel introLbl, lbl1, lbl2, txtfld1, txtfld2;
//    private JTextField txtfld1, txtfld2, txtfld3;
    private JButton btn1;
    private JTextArea txtArea1;


    public static void main(String[] args) {

        // make window object
        manager_view GUI = new manager_view();
        GUI.init();

        // set window object size
        GUI.setSize(760, 768);
        GUI.setTitle("MANAGER VIEW");
        GUI.setVisible(true);
        GUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void init() {

        // create the pane
        Container pane = this.getContentPane();
        pane.setLayout(null);

        // title of screen
        introLbl = new JLabel();
        introLbl.setBounds(10, 10, 500, 10);
        introLbl.setText("INVENTORY MANAGEMENT SYSTEM");

        // first label
        lbl1 = new JLabel("TOTAL DAILY SALES:");
        lbl1.setBounds(35, 50, 150, 20);
//        txtfld1 = new JTextField("$50.11");
        txtfld1 = new JLabel();
        txtfld1.setText("$50.11");

        txtfld1.setBounds(250, 45, 100, 30);

        // second label
        lbl2 = new JLabel("TOTAL SALES TO DATE:");
        lbl2.setBounds(35, 90, 150, 20);
        txtfld2 = new JLabel();
        txtfld2.setText("$142.91");

        txtfld2.setBounds(250, 85, 100, 30);

        // create button for charts and analysis
        btn1 = new JButton("VIEW CURRENT ITEMS");
        btn1.setBounds(35, 200, 350, 30);
        btn1.addActionListener(this);

        // add to pane
        pane.add(introLbl);
        pane.add(lbl1);
        pane.add(lbl2);

        pane.add(txtfld1);
        pane.add(txtfld2);

        pane.add(btn1);

    }


    // prompts user that the analysis is completed

    @Override
    public void actionPerformed(ActionEvent e) {

//        JOptionPane.showMessageDialog(this, "ANALYSIS COMPLETED");

        // frame
        JFrame f;
        // Table
        JTable j;

        f = new JFrame();

        // Frame Title
        f.setTitle("TODAY'S TRANSACTIONS: " + java.time.LocalDate.now());

        // NEED TO LEARN HOW TO PULL FROM DB
        String[][] data = {
                { "0", "Chicken", "135", "250", "7.29"},
                { "1", "Steak", "163", "200", "8.29"},
                { "4", "Ground_Beef", "157", "300", "8.19"},
                { "5", "Roasted_Vegetables", "142", "200", "7.29"},
                { "8", "Guacamole", "1344", "2000", "2.00"},
                { "9", "Queso", "1211", "2000", "2.00"},
                { "13", "Cookie", "121", "300", "1.99"},
                { "14", "Brownie", "211", "300", "1.99"},
                { "15", "Chips_Salsa", "125", "150", "2.19"},
                { "16", "Chips_Queso", "113", "150", "3.64"},
                { "17", "Chips_Quac", "141", "150", "3.69"},
                { "18", "16oz_Fountain", "315", "500", "2.25"},
                { "19", "22oz_Fountain", "182", "500", "2.75"},



        }; // hard coding for now -- connect to table in database later

        // Column Names
        String[] columnNames = { "food_id", "food_name", "current_count", "max_count", "sell_price" };


        //Building the connection with your credentials

//        Connection conn = null;
//        String teamNumber = "22";
//        String sectionNumber = "913";
//        String dbName = "csce315_" + sectionNumber + "_" + teamNumber;
//        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbname;
////        dbSetup myCredentials = new dbSetup();
////        "csce315_913_kutsch", "830002561";
//
//        //Connecting to the database
//        try {
//            Class.forName("org.postgresql.Driver");
//            conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315_913_22", "csce315_913_kutsch", "830002561");
//        } catch (Exception e2) {
//            e2.printStackTrace();
//            System.err.println(e2.getClass().getName()+": "+e2.getMessage());
//            System.exit(0);
//        }
//
//        System.out.println("Opened database successfully");
//
//        try{
//            //create a statement object
//            Statement stmt = conn.createStatement();
//
//            // count the number of rows in the inventory
//            String sqlStatement = "SELECT count(food_id) AS count FROM inventory;";
//
//            //send statement to DBMS
//            ResultSet result = stmt.executeQuery(sqlStatement);
//            result.next();
//            int length = Integer.parseInt(result.getString("count"));
//            data = new String[length][6];
//
//            sqlStatement = "SELECT * FROM inventory WHERE is_menu_item=1;";
//            //send statement to DBMS
//            result = stmt.executeQuery(sqlStatement);
//
//            int entry_nr = 0;
//            while (result.next()) {
//                data[entry_nr][0] = result.getString("food_id")+"\n";
//                data[entry_nr][1] = result.getString("food_name")+"\n";
//                data[entry_nr][2] = result.getString("current_count")+"\n";
//                data[entry_nr][3] = result.getString("max_count")+"\n";
//                data[entry_nr][4] = result.getString("sell_price")+"\n";
//                data[entry_nr][5] = result.getString("is_menu_item")+"\n";
//                entry_nr++;
//            }
//            conn.close();
//        } catch(Exception e3){
//            JOptionPane.showMessageDialog(null,"Error accessing Database.");
//        }
//        return data;



        // Initializing the JTable
        j = new JTable(data, columnNames);
        j.setBounds(30, 40, 200, 500);

        // adding it to JScrollPane
        JScrollPane sp = new JScrollPane(j);
        f.add(sp);
        // Frame Size
        f.setSize(760, 375);
        f.setVisible(true);

    }


}