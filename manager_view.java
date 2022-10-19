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

/** manager_view displays the total daily sales, total sales to date,
 * to view current items in the inventory, and view most sold items.
 *
 * @author Jonathan Kutsch
 *
 */
public class manager_view extends JFrame implements ActionListener {

    private enum Actions {
        BTN2,
        BTN3,
        BTN4,
        BTN5
    }

    private static final long serialVersionUID = 1L;

    private JLabel introLbl, lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7, lbl8, lbl9, lbl10, lbl11, lbl12, txtfld1, txtfld2, txtfld4, txtfld5, txtfld6, txtfld7, txtfld8, txtfld9, txtfld10, txtfld11, txtfld12;
    private JTextField txtfld3;
    private JTextField txtfldTimeStamp;
    private JButton btn1, btn3, btn4, btn5;
//    private JButton btn2;
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
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    /** init() function initializes the GUI by creating the pane, labels, buttons, textfields, and boundaries.
     *
     * @param N/A
     * @return void function
     * @throws N/A
     * @see pane created on compilation
     *
     */
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
        btn1.setBounds(35, 150, 300, 30);
        btn1.addActionListener(this);


        // create button for signing out
        btn3 = new JButton("Sign Out");
        btn3.setBounds(300, 0, 150, 20);
        btn3.addActionListener(this);

        // create button for checking inventory
        btn4 = new JButton("INVENTORY");
        btn4.setBounds(350, 150, 300, 30);
        btn4.addActionListener(this);

        // create button for checking excess report
        btn5 = new JButton("EXCESS REPORT");
        btn5.setBounds(665, 150, 300, 30);
        btn5.addActionListener(this);

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
        lbl3 = new JLabel("Itemized Sales Today:");
        lbl3.setBounds(35, 285, 300, 20);
        txtfld3 = new JTextField();
        LocalDate dt = java.time.LocalDate.now();
        txtfld3.setText(dt.toString());
        txtfld3.setBounds(260, 285, 100, 20);


        lbl4 = new JLabel("CHICKEN BOWLS SOLD IN CURRENT DATE:");
        lbl4.setBounds(35, 360, 300, 10);
        txtfld4 = new JLabel();
        txtfld4.setText(String.valueOf(getChickenBowlTotal()));
        txtfld4.setBounds(425, 360, 100, 10);

        lbl5 = new JLabel("CHICKEN BURRITOS SOLD IN CURRENT DATE:");
        lbl5.setBounds(35, 390, 300, 10);
        txtfld5 = new JLabel();
        txtfld5.setText(String.valueOf(getChickenBurritoTotal()));
        txtfld5.setBounds(425, 390, 100, 10);


        lbl6 = new JLabel("STEAK BOWLS SOLD IN CURRENT DATE:");
        lbl6.setBounds(35, 420, 300, 10);
        txtfld6 = new JLabel();
        txtfld6.setText(String.valueOf(getSteakBowlTotal()));
        txtfld6.setBounds(425, 420, 100, 10);

        lbl7 = new JLabel("STEAK BURRITOS SOLD IN CURRENT DATE:");
        lbl7.setBounds(35, 450, 300, 10);
        txtfld7 = new JLabel();
        txtfld7.setText(String.valueOf(getSteakBurritoTotal()));
        txtfld7.setBounds(425, 450, 100, 10);


        lbl8 = new JLabel("BEEF BOWLS SOLD IN CURRENT DATE:");
        lbl8.setBounds(35, 480, 300, 10);
        txtfld8 = new JLabel();
        txtfld8.setText(String.valueOf(getBeefBowlTotal()));
        txtfld8.setBounds(425, 480, 100, 10);

        lbl9 = new JLabel("BEEF BURRITOS SOLD IN CURRENT DATE:");
        lbl9.setBounds(35, 510, 300, 10);
        txtfld9 = new JLabel();
        txtfld9.setText(String.valueOf(getBeefBurritoTotal()));
        txtfld9.setBounds(425, 510, 100, 10);


        lbl10 = new JLabel("COOKIES SOLD IN CURRENT DATE:");
        lbl10.setBounds(35, 540, 300, 10);
        txtfld10 = new JLabel();
        txtfld10.setText(String.valueOf(getCookieTotal()));
        txtfld10.setBounds(425, 540, 100, 10);

        lbl11 = new JLabel("BROWNIES SOLD IN CURRENT DATE:");
        lbl11.setBounds(35, 570, 300, 10);
        txtfld11 = new JLabel();
        txtfld11.setText(String.valueOf(getBrownieTotal()));
        txtfld11.setBounds(425, 570, 100, 10);

        lbl12 = new JLabel("TOTAL ORDERS PLACED:");
        lbl12.setBounds(35, 640, 300, 10);
        txtfld12 = new JLabel();
        txtfld12.setText(String.valueOf(getTotalOrders()));
        txtfld12.setBounds(425, 640, 100, 10);

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
        pane.add(lbl10);
        pane.add(lbl11);
        pane.add(lbl12);

        pane.add(txtfld1);
        pane.add(txtfld2);
        pane.add(txtfld3);
        pane.add(txtfld4);
        pane.add(txtfld5);
        pane.add(txtfld6);
        pane.add(txtfld7);
        pane.add(txtfld8);
        pane.add(txtfld9);
        pane.add(txtfld10);
        pane.add(txtfld11);
        pane.add(txtfld12);

        pane.add(btn1);
//        pane.add(btn2);
        pane.add(btn3);
        pane.add(btn4);
        pane.add(btn5);
    }

    /** actionPerformed(ActionEvent e) takes care of the creation of the "Today's Transactions" Table
     *
     * @param ActionEvent e -- 'this' in use case
     * @return void function
     * @throws N/A
     * @see TODAY'S TRANSACTIONS TABLE CREATED WHEN BUTTON IS PRESSED
     *
     */
    // prompts user that the analysis is completed
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btn3){
            new login_view();
            dispose();
            return;
        }

        if(e.getSource() == btn4){
            new inventory_view();
            dispose();
            return;
        }
        if(e.getSource() == btn5){
            String Date = JOptionPane.showInputDialog("Timestamp(MM/DD/YYYY)");
            JFrame f;
            // Table
            JTable j;

            f = new JFrame();

            // Frame Title
            f.setTitle("EXCESS REPORT: " + java.time.LocalDate.now());

            // pull all the menu items from the inventory to be displayed
            String[][] inv = getInventory();

            // Column Names
            String[] columnNames = {"food_id", "food_name"};


            // Initializing the JTable
            j = new JTable(inv, columnNames);
            j.setBounds(30, 40, 200, 500);

            // adding it to JScrollPane
            JScrollPane sp = new JScrollPane(j);
            f.add(sp);
            // Frame Size
            f.setExtendedState(JFrame.MAXIMIZED_BOTH);
            f.setVisible(true);
            return;
        
        }
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
            f.setExtendedState(JFrame.MAXIMIZED_BOTH);
            f.setVisible(true);
        }
    }

    /** getInventory() function connects to the JDBC and pulls all the current inventory marked as is_menu_item
     *
     * @param N/A
     * @return static 2D array to display current inventory as table
     * @throws catch exception if database connection fails
     * @see TODAY'S TRANSACTIONS TABLE CREATED WHEN BUTTON IS PRESSED
     *
     */
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


    /** getTotalSum() function connects to the JDBC and pulls the total sum of the entire business revenue
     *
     * @param N/A
     * @return static double to present total currency generated by business
     * @throws catch exception if database connection fails
     * @see TODAY'S TRANSACTIONS TABLE CREATED WHEN BUTTON IS PRESSED
     *
     */
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

    /** getChickenBurritoTotal() function connects to the JDBC and pulls total number of chicken burritos sold.
     *
     * @param N/A
     * @return static int to present total chicken burritos generated by business
     * @throws catch exception if database connection fails
     * @see table at bottom of GUI
     *
     */
    static int getChickenBurritoTotal() {
        int sumChickenBurrito = 0;
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
                int tmp = res.getInt(1);
                sumChickenBurrito = sumChickenBurrito + tmp;
            }
//            sumChickenBurrito = Math.round(sumChickenBurrito*100.0)/100.0;
//            System.out.println(sumToday);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error accessing Database.");
        }
        return sumChickenBurrito;
    }


    /** getChickenBowlTotal() function connects to the JDBC and pulls total number of chicken bowls sold.
     *
     * @param N/A
     * @return static int to present total chicken bowls generated by business
     * @throws catch exception if database connection fails
     * @see table at bottom of GUI
     *
     */
    static int getChickenBowlTotal() {
        int sumChickenBowl = 0;
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
            ResultSet res = stmt.executeQuery("SELECT count(protein) FROM order_entries WHERE protein='Chicken' AND base='Bowl';");

            // pull into table
            while (res.next()) {
                int tmp = res.getInt(1);
                sumChickenBowl = sumChickenBowl + tmp;
            }
//            sumChickenBowl = Math.round(sumChickenBowl*100.0)/100.0;
//            System.out.println(sumToday);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error accessing Database.");
        }
        return sumChickenBowl;
    }
    /** getSteakBurritoTotal() function connects to the JDBC and pulls total number of steak burritos sold.
     *
     * @param N/A
     * @return static int to present total steak burritos generated by business
     * @throws catch exception if database connection fails
     * @see table at bottom of GUI
     *
     */
    static int getSteakBurritoTotal() {
        int sumSteakBurrito = 0;
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
            ResultSet res = stmt.executeQuery("SELECT count(protein) FROM order_entries WHERE protein='Steak' AND base='Burrito';");

            // pull into table
            while (res.next()) {
                int tmp = res.getInt(1);
                sumSteakBurrito = sumSteakBurrito + tmp;
            }
//            sumSteakBurrito = Math.round(sumSteakBurrito*100.0)/100.0;
//            System.out.println(sumToday);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error accessing Database.");
        }
        return sumSteakBurrito;
    }


    /** getSteakBowlTotal() function connects to the JDBC and pulls total number of steak bowls sold.
     *
     * @param N/A
     * @return static int to present total static bowls generated by business
     * @throws catch exception if database connection fails
     * @see table at bottom of GUI
     *
     */
    static int getSteakBowlTotal() {
        int sumSteakBowl = 0;
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
            ResultSet res = stmt.executeQuery("SELECT count(protein) FROM order_entries WHERE protein='Steak' AND base='Bowl';");

            // pull into table
            while (res.next()) {
                int tmp = res.getInt(1);
                sumSteakBowl = sumSteakBowl + tmp;
            }
//            sumSteakBowl = Math.round(sumSteakBowl*100.0)/100.0;
//            System.out.println(sumToday);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error accessing Database.");
        }
        return sumSteakBowl;
    }


    /** getBeefBurritoTotal() function connects to the JDBC and pulls total number of steak burritos sold.
     *
     * @param N/A
     * @return static int to present total steak burritos generated by business
     * @throws catch exception if database connection fails
     * @see table at bottom of GUI
     *
     */
    static int getBeefBurritoTotal() {
        int sumBeefBurrito = 0;
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
            ResultSet res = stmt.executeQuery("SELECT count(protein) FROM order_entries WHERE protein='Beef' AND base='Burrito';");

            // pull into table
            while (res.next()) {
                int tmp = res.getInt(1);
                sumBeefBurrito = sumBeefBurrito + tmp;
            }
//            sumBeefBurrito = Math.round(sumBeefBurrito*100.0)/100.0;
//            System.out.println(sumToday);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error accessing Database.");
        }
        return sumBeefBurrito;
    }


    /** getBeefBowlTotal() function connects to the JDBC and pulls total number of beef bowls sold.
     *
     * @param N/A
     * @return static int to present total beef bowls generated by business
     * @throws catch exception if database connection fails
     * @see table at bottom of GUI
     *
     */
    static int getBeefBowlTotal() {
        int sumBeefBowl = 0;
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
            ResultSet res = stmt.executeQuery("SELECT count(protein) FROM order_entries WHERE protein='Beef' AND base='Bowl';");

            // pull into table
            while (res.next()) {
                int tmp = res.getInt(1);
                sumBeefBowl = sumBeefBowl + tmp;
            }
//            sumBeefBowl = Math.round(sumBeefBowl*100.0)/100.0;
//            System.out.println(sumToday);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error accessing Database.");
        }
        return sumBeefBowl;
    }

    /** getCookieTotal() function connects to the JDBC and pulls total number of cookies sold.
     *
     * @param N/A
     * @return static int to present total cookies generated by business
     * @throws catch exception if database connection fails
     * @see table at bottom of GUI
     *
     */
    static int getCookieTotal() {
        int sumCookie = 0;
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
            ResultSet res = stmt.executeQuery("SELECT count(cookie) FROM order_entries WHERE cookie='1';");

            // pull into table
            while (res.next()) {
                int tmp = res.getInt(1);
                sumCookie = sumCookie + tmp;
            }
//            sumCookie = Math.round(sumCookie*100.0)/100.0;
//            System.out.println(sumToday);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error accessing Database.");
        }
        return sumCookie;
    }


    /** getBrownieTotal() function connects to the JDBC and pulls total number of brownies sold.
     *
     * @param N/A
     * @return static int to present total brownies generated by business
     * @throws catch exception if database connection fails
     * @see table at bottom of GUI
     *
     */
    static int getBrownieTotal() {
        int sumBrownie = 0;
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
            ResultSet res = stmt.executeQuery("SELECT count(brownie) FROM order_entries WHERE brownie='1';");

            // pull into table
            while (res.next()) {
                int tmp = res.getInt(1);
                sumBrownie = sumBrownie + tmp;
            }
//            sumBrownie = Math.round(sumBrownie*100.0)/100.0;
//            System.out.println(sumToday);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error accessing Database.");
        }
        return sumBrownie;
    }



    /** getTotalOrders() function connects to the JDBC and pulls total number of orders sold.
     *
     * @param N/A
     * @return static int to present total orders generated by business
     * @throws catch exception if database connection fails
     * @see table at bottom of GUI
     *
     */
    static int getTotalOrders() {
        int totalOrders = 0;
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
            ResultSet res = stmt.executeQuery("SELECT count(order_number) FROM order_entries;");

            // pull into table
            while (res.next()) {
                int tmp = res.getInt(1);
                totalOrders = totalOrders + tmp;
            }
//            sumCookie = Math.round(sumCookie*100.0)/100.0;
//            System.out.println(sumToday);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error accessing Database.");
        }
        return totalOrders;
    }



    /** sumToday() function connects to the JDBC and pulls total sum of orders.
     *
     * @param N/A
     * @return static double to present total revenue generated by business
     * @throws catch exception if database connection fails
     * @see total sum of revenue
     *
     */
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

    /** main() function pieces everything today and runs the manager_view
     *
     * @param N/A
     * @return N/A
     * @throws N/A
     * @see manager_view GUI
     *
     */
    public static void main(String[] args) {
        new manager_view();
    }

}

