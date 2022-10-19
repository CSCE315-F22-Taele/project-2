import javax.swing.*;
import java.awt.*;
import java.lang.*;
import java.sql.*;
import javax.swing.table.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.*;
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

    private enum Actions {
        BTN2,
        GOODBYE
    }

    private static final long serialVersionUID = 1L;

    private JLabel introLbl, lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7, lbl8, lbl9, txtfld1, txtfld2, txtfld4, txtfld5, txtfld6, txtfld7, txtfld8, txtfld9;
    private JTextField txtfld3;
    private JButton btn1;
    private JButton btn2;
    private JTextArea txtArea1;


        manager_view() {

        // make window object
//        manager_view GUI = new manager_view();
//        GUI.init();
        this.init();

        // set window object size
//        GUI.setSize(760, 768);
//        GUI.setTitle("MANAGER VIEW");
//        GUI.setVisible(true);
//        GUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(760, 768);
        this.setTitle("MANAGER VIEW");
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
        txtfld1 = new JLabel();
        txtfld1.setText("$" + sumToday());

        txtfld1.setBounds(250, 45, 100, 30);

        // second label
        lbl2 = new JLabel("TOTAL SALES TO DATE:");
        lbl2.setBounds(35, 90, 150, 20);
        txtfld2 = new JLabel();
        txtfld2.setText("$" + getTotalSum());

        txtfld2.setBounds(250, 85, 500, 30);

        // create button for all current menu items in the inventory
        btn1 = new JButton("VIEW CURRENT ITEMS");
        btn1.setBounds(35, 150, 350, 30);
        btn1.addActionListener(this);

        String[] items = {
                "Chicken",
                "Steak",
                "Beef",
                "Roasted_Vegetables",
                "chips_queso",
                "chips_quac",
                "chips_salsa",
                "16oz_drink",
                "22oz_drink",
                "cookie",
                "brownie"
        };


        // third label
        lbl3 = new JLabel("Itemized revenue up to today's date:");
        lbl3.setBounds(35, 285, 300, 20);
        txtfld3 = new JTextField();
        LocalDate dt = java.time.LocalDate.now();
        txtfld3.setText(dt.toString());
        txtfld3.setBounds(260, 285, 100, 20);


        // create button for specific item and specific date
//        btn2 = new JButton("Sum of Chicken");
//        btn2.setBounds(35, 360, 350, 30);
//        btn2.setActionCommand(Actions.BTN2.name());
//        btn2.addActionListener(this);
//        btn2.addActionListener(this);
        // "SELECT * FROM order_entries WHERE date BETWEEN 'start' AND 'end';"

        lbl4 = new JLabel("CHICKEN BOWLS SOLD IN CURRENT DATE:");
        lbl4.setBounds(35, 360, 300, 10);
        txtfld4 = new JLabel();
        txtfld4.setText("$" + getChickenBowlTotal());
        txtfld4.setBounds(425, 360, 100, 10);

        lbl5 = new JLabel("CHICKEN BURRITOS SOLD IN CURRENT DATE:");
        lbl5.setBounds(35, 390, 300, 10);
        txtfld5 = new JLabel();
        txtfld5.setText("$" + getChickenBurritoTotal());
        txtfld5.setBounds(425, 390, 100, 10);


        lbl6 = new JLabel("STEAK BOWLS SOLD IN CURRENT DATE:");
        lbl6.setBounds(35, 420, 300, 10);
        txtfld6 = new JLabel();
        txtfld6.setText("$" + getSteakBowlTotal());
        txtfld6.setBounds(425, 420, 100, 10);

        lbl7 = new JLabel("STEAK BURRITOS SOLD IN CURRENT DATE:");
        lbl7.setBounds(35, 450, 300, 10);
        txtfld7 = new JLabel();
        txtfld7.setText("$" + getSteakBurritoTotal());
        txtfld7.setBounds(425, 450, 100, 10);


        lbl8 = new JLabel("BEEF BOWLS SOLD IN CURRENT DATE:");
        lbl8.setBounds(35, 480, 300, 10);
        txtfld8 = new JLabel();
        txtfld8.setText("$" + getBeefBowlTotal());
        txtfld8.setBounds(425, 480, 100, 10);

        lbl9 = new JLabel("BEEF BURRITOS SOLD IN CURRENT DATE:");
        lbl9.setBounds(35, 510, 300, 10);
        txtfld9 = new JLabel();
        txtfld9.setText("$" + getBeefBurritoTotal());
        txtfld9.setBounds(425, 510, 100, 10);

        // add to pane
        pane.add(introLbl);
        pane.add(lbl1);
        pane.add(lbl2);
        pane.add(lbl3);
        pane.add(lbl4);
        pane.add(lbl5);
        pane.add(lbl6);
        pane.add(lbl7);
        pane.add(lbl8);
        pane.add(lbl9);

        pane.add(txtfld1);
        pane.add(txtfld2);
        pane.add(txtfld3);
        pane.add(txtfld4);
        pane.add(txtfld5);
        pane.add(txtfld6);
        pane.add(txtfld7);
        pane.add(txtfld8);
        pane.add(txtfld9);



        pane.add(btn1);
//        pane.add(btn2);

    }


    // prompts user that the analysis is completed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() != Actions.BTN2.name()) {
            // frame
            JFrame f;
            // Table
            JTable j;

            f = new JFrame();

            // Frame Title
            f.setTitle("TODAY'S TRANSACTIONS: " + java.time.LocalDate.now());

            // pull all the menu items from the inventory to be displayed
            String[][] inv = getInventory();

            // Column Names
            String[] columnNames = {"food_id", "food_name", "current_count", "max_count", "sell_price" };


            // Initializing the JTable
            j = new JTable(inv, columnNames);
            j.setBounds(30, 40, 200, 500);

            // adding it to JScrollPane
            JScrollPane sp = new JScrollPane(j);
            f.add(sp);
            // Frame Size
            f.setSize(760, 375);
            f.setVisible(true);
        }
    }

    // helper function to get the inventory
    static String[][] getInventory() {
        // create array for inventory items
        String[][] inv = new String[0][0];
        // establish database setup information
        Connection conn = null;
        String teamNumber = "22";
        String sectionNumber = "913";
        String dbName = "csce315_" + sectionNumber + "_" + teamNumber ;
        String dbConnectionString = "jdbc.;postgresql://csce-315-db.engr.tamu.edu/" + dbName;

        // Connecting to the database
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315_913_22",
                    "csce315_913_kutsch", "830002561");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

//        System.out.println("Opened database successfully");

        try{
            // create a statement object
            Statement stmt = conn.createStatement();

            // SQL line that finds the number of items in inventory by the number of id's
            String sqlStatement = "SELECT count(food_id) AS count FROM inventory;";

            // send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);
            result.next();
            int sz = Integer.parseInt(result.getString("count"));
            inv = new String[sz][5];

            // SQL line that pulls all the menu items from the inventory sorted by their food id
            sqlStatement = "SELECT * FROM inventory WHERE is_menu_item=true ORDER BY food_id;";
            // send statement to DBMS
            result = stmt.executeQuery(sqlStatement);

            // pull into table
            int entry_nr = 0;
            while (result.next()) {
                inv[entry_nr][0] = result.getString("food_id")+"\n";
                inv[entry_nr][1] = result.getString("food_name")+"\n";
                inv[entry_nr][2] = result.getString("current_count")+"\n";
                inv[entry_nr][3] = result.getString("max_count")+"\n";
                inv[entry_nr][4] = result.getString("sell_price")+"\n";
                entry_nr++;
                System.out.println(inv[0]+","+inv[1]+","+inv[2]+","+inv[3]+","+inv[4]);
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error accessing Database.");
        }
        return inv;
    }



    // helper function to get the total sum of entire business
    static double getTotalSum() {
        double totalSum = 0.0;
        // establish database setup information
        Connection conn = null;
        String teamNumber = "22";
        String sectionNumber = "913";
        String dbName = "csce315_" + sectionNumber + "_" + teamNumber ;
        String dbConnectionString = "jdbc.;postgresql://csce-315-db.engr.tamu.edu/" + dbName;

        // Connecting to the database
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315_913_22",
                    "csce315_913_kutsch", "830002561");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

//        System.out.println("Opened database successfully");

        try{
            // create a statement object
            Statement stmt = conn.createStatement();

            ResultSet res = stmt.executeQuery("SELECT SUM(cost) FROM order_entries;");

            // pull into table
            while (res.next()) {
                double tmp = res.getFloat(1);
                totalSum = totalSum + tmp;
            }
            totalSum = Math.round(totalSum*100.0)/100.0;
//            System.out.println(sumToday);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error accessing Database.");
        }
        return totalSum;
    }

    // helper function to get the sum of profits in today's operating hours
    static double getChickenBurritoTotal() {
        double sumChickenBurrito = 0.0;
        // establish database setup information
        Connection conn = null;
        String teamNumber = "22";
        String sectionNumber = "913";
        String dbName = "csce315_" + sectionNumber + "_" + teamNumber ;
        String dbConnectionString = "jdbc.;postgresql://csce-315-db.engr.tamu.edu/" + dbName;

        // Connecting to the database
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315_913_22",
                    "csce315_913_kutsch", "830002561");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

//        System.out.println("Opened database successfully");

        try{
            // create a statement object
            Statement stmt = conn.createStatement();

            // will not work if there are no orders in today's date
            // SO YOU MUST INSERT AN ORDER ENTRY BEFORE RUNNING THE MANAGER VIEW PAGE
            ResultSet res = stmt.executeQuery("SELECT SUM(cost) FROM order_entries WHERE protein='Chicken' AND base='Burrito';");

            // pull into table
            while (res.next()) {
                double tmp = res.getFloat(1);
                sumChickenBurrito = sumChickenBurrito + tmp;
            }
            sumChickenBurrito = Math.round(sumChickenBurrito*100.0)/100.0;
//            System.out.println(sumToday);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error accessing Database.");
        }
        return sumChickenBurrito;
    }


    // helper function to get the sum of profits in today's operating hours
    static double getChickenBowlTotal() {
        double sumChickenBowl = 0.0;
        // establish database setup information
        Connection conn = null;
        String teamNumber = "22";
        String sectionNumber = "913";
        String dbName = "csce315_" + sectionNumber + "_" + teamNumber ;
        String dbConnectionString = "jdbc.;postgresql://csce-315-db.engr.tamu.edu/" + dbName;

        // Connecting to the database
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315_913_22",
                    "csce315_913_kutsch", "830002561");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

//        System.out.println("Opened database successfully");

        try{
            // create a statement object
            Statement stmt = conn.createStatement();

            // will not work if there are no orders in today's date
            // SO YOU MUST INSERT AN ORDER ENTRY BEFORE RUNNING THE MANAGER VIEW PAGE
            ResultSet res = stmt.executeQuery("SELECT SUM(cost) FROM order_entries WHERE protein='Chicken' AND base='Bowl';");

            // pull into table
            while (res.next()) {
                double tmp = res.getFloat(1);
                sumChickenBowl = sumChickenBowl + tmp;
            }
            sumChickenBowl = Math.round(sumChickenBowl*100.0)/100.0;
//            System.out.println(sumToday);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error accessing Database.");
        }
        return sumChickenBowl;
    }

    // helper function to get the sum of profits in today's operating hours
    static double getSteakBurritoTotal() {
        double sumSteakBurrito = 0.0;
        // establish database setup information
        Connection conn = null;
        String teamNumber = "22";
        String sectionNumber = "913";
        String dbName = "csce315_" + sectionNumber + "_" + teamNumber ;
        String dbConnectionString = "jdbc.;postgresql://csce-315-db.engr.tamu.edu/" + dbName;

        // Connecting to the database
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315_913_22",
                    "csce315_913_kutsch", "830002561");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

//        System.out.println("Opened database successfully");

        try{
            // create a statement object
            Statement stmt = conn.createStatement();

            // will not work if there are no orders in today's date
            // SO YOU MUST INSERT AN ORDER ENTRY BEFORE RUNNING THE MANAGER VIEW PAGE
            ResultSet res = stmt.executeQuery("SELECT SUM(cost) FROM order_entries WHERE protein='Steak' AND base='Burrito';");

            // pull into table
            while (res.next()) {
                double tmp = res.getFloat(1);
                sumSteakBurrito = sumSteakBurrito + tmp;
            }
            sumSteakBurrito = Math.round(sumSteakBurrito*100.0)/100.0;
//            System.out.println(sumToday);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error accessing Database.");
        }
        return sumSteakBurrito;
    }


    // helper function to get the sum of profits in today's operating hours
    static double getSteakBowlTotal() {
        double sumSteakBowl = 0.0;
        // establish database setup information
        Connection conn = null;
        String teamNumber = "22";
        String sectionNumber = "913";
        String dbName = "csce315_" + sectionNumber + "_" + teamNumber ;
        String dbConnectionString = "jdbc.;postgresql://csce-315-db.engr.tamu.edu/" + dbName;

        // Connecting to the database
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315_913_22",
                    "csce315_913_kutsch", "830002561");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

//        System.out.println("Opened database successfully");

        try{
            // create a statement object
            Statement stmt = conn.createStatement();

            // will not work if there are no orders in today's date
            // SO YOU MUST INSERT AN ORDER ENTRY BEFORE RUNNING THE MANAGER VIEW PAGE
            ResultSet res = stmt.executeQuery("SELECT SUM(cost) FROM order_entries WHERE protein='Steak' AND base='Bowl';");

            // pull into table
            while (res.next()) {
                double tmp = res.getFloat(1);
                sumSteakBowl = sumSteakBowl + tmp;
            }
            sumSteakBowl = Math.round(sumSteakBowl*100.0)/100.0;
//            System.out.println(sumToday);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error accessing Database.");
        }
        return sumSteakBowl;
    }


    // helper function to get the sum of profits in today's operating hours
    static double getBeefBurritoTotal() {
        double sumBeefBurrito = 0.0;
        // establish database setup information
        Connection conn = null;
        String teamNumber = "22";
        String sectionNumber = "913";
        String dbName = "csce315_" + sectionNumber + "_" + teamNumber ;
        String dbConnectionString = "jdbc.;postgresql://csce-315-db.engr.tamu.edu/" + dbName;

        // Connecting to the database
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315_913_22",
                    "csce315_913_kutsch", "830002561");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

//        System.out.println("Opened database successfully");

        try{
            // create a statement object
            Statement stmt = conn.createStatement();

            // will not work if there are no orders in today's date
            // SO YOU MUST INSERT AN ORDER ENTRY BEFORE RUNNING THE MANAGER VIEW PAGE
            ResultSet res = stmt.executeQuery("SELECT SUM(cost) FROM order_entries WHERE protein='Beef' AND base='Burrito';");

            // pull into table
            while (res.next()) {
                double tmp = res.getFloat(1);
                sumBeefBurrito = sumBeefBurrito + tmp;
            }
            sumBeefBurrito = Math.round(sumBeefBurrito*100.0)/100.0;
//            System.out.println(sumToday);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error accessing Database.");
        }
        return sumBeefBurrito;
    }


    // helper function to get the sum of profits in today's operating hours
    static double getBeefBowlTotal() {
        double sumBeefBowl = 0.0;
        // establish database setup information
        Connection conn = null;
        String teamNumber = "22";
        String sectionNumber = "913";
        String dbName = "csce315_" + sectionNumber + "_" + teamNumber ;
        String dbConnectionString = "jdbc.;postgresql://csce-315-db.engr.tamu.edu/" + dbName;

        // Connecting to the database
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315_913_22",
                    "csce315_913_kutsch", "830002561");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

//        System.out.println("Opened database successfully");

        try{
            // create a statement object
            Statement stmt = conn.createStatement();

            // will not work if there are no orders in today's date
            // SO YOU MUST INSERT AN ORDER ENTRY BEFORE RUNNING THE MANAGER VIEW PAGE
            ResultSet res = stmt.executeQuery("SELECT SUM(cost) FROM order_entries WHERE protein='Beef' AND base='Bowl';");

            // pull into table
            while (res.next()) {
                double tmp = res.getFloat(1);
                sumBeefBowl = sumBeefBowl + tmp;
            }
            sumBeefBowl = Math.round(sumBeefBowl*100.0)/100.0;
//            System.out.println(sumToday);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error accessing Database.");
        }
        return sumBeefBowl;
    }


    // helper function to get the sum of profits in today's operating hours
    static double sumToday() {
        double sumToday = 0.0;
        // establish database setup information
        Connection conn = null;
        String teamNumber = "22";
        String sectionNumber = "913";
        String dbName = "csce315_" + sectionNumber + "_" + teamNumber ;
        String dbConnectionString = "jdbc.;postgresql://csce-315-db.engr.tamu.edu/" + dbName;

        // Connecting to the database
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315_913_22",
                    "csce315_913_kutsch", "830002561");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

//        System.out.println("Opened database successfully");

        try{
            // create a statement object
            Statement stmt = conn.createStatement();

            // will not work if there are no orders in today's date
            // SO YOU MUST INSERT AN ORDER ENTRY BEFORE RUNNING THE MANAGER VIEW PAGE
            ResultSet res = stmt.executeQuery("SELECT SUM(cost) FROM order_entries WHERE date BETWEEN NOW() - INTERVAL '24 HOURS' AND NOW();");

            // pull into table
            while (res.next()) {
                double tmp = res.getFloat(1);
                sumToday = sumToday + tmp;
            }
            sumToday = Math.round(sumToday*100.0)/100.0;
//            System.out.println(sumToday);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error accessing Database.");
        }
        return sumToday;
    }



    // helper function to pull the revenues for specific item on specific date
    static double querySpecificItem() {
        double sumToday = 0.0;
        // establish database setup information
        Connection conn = null;
        String teamNumber = "22";
        String sectionNumber = "913";
        String dbName = "csce315_" + sectionNumber + "_" + teamNumber ;
        String dbConnectionString = "jdbc.;postgresql://csce-315-db.engr.tamu.edu/" + dbName;

        // Connecting to the database
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315_913_22",
                    "csce315_913_kutsch", "830002561");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

//        System.out.println("Opened database successfully");

        try{
            // create a statement object
            Statement stmt = conn.createStatement();

            // will not work if there are no orders in today's date
            // SO YOU MUST INSERT AN ORDER ENTRY BEFORE RUNNING THE MANAGER VIEW PAGE
            ResultSet res = stmt.executeQuery("SELECT SUM(cost) FROM order_entries WHERE date BETWEEN NOW() - INTERVAL '24 HOURS' AND NOW();");

            // pull into table
            while (res.next()) {
                double tmp = res.getFloat(1);
                sumToday = sumToday + tmp;
            }
            sumToday = Math.round(sumToday*100.0)/100.0;
//            System.out.println(sumToday);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error accessing Database.");
        }
        return sumToday;
    }

    public static void main(String[] args) {
        new manager_view();
    }

}
