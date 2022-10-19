import java.sql.*;
import java.text.DecimalFormat;
import java.awt.event.*;
import javax.swing.*;   
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 
import java.util.Enumeration;
import javax.swing.ButtonGroup;


/**
 * Demo is the GUI for server view.
 * Demo extends JFrame and implements ActionListener.
 * Because of this, Demo is allowed to be constructed similar to a JFrame and 
 * ActionListener is being Overridden to allow for radiobutton action listener.
 * 
 * @author      Joshua Batac
 */

class Demo extends JFrame implements ActionListener { 

    int next_food_id = 0; //seasonal item food ID
    int order_id = 0;
    int customer_id = -1; //first customer, sameCustomer.isSelected() will always be false
    double curr_total = 0.0; //current price of order
    
    JTextField total = new JTextField();
    JRadioButton same_customer = new JRadioButton("Same Customer?");
    JButton back_to_login = new JButton("Sign Out");
    ButtonGroup base = new ButtonGroup(); //only one base picked
    ButtonGroup protein = new ButtonGroup(); //only one protein picked
    
    String[][] menu_items = new String[0][0]; //takes data from database 
    JRadioButton[] buttons = new JRadioButton[0]; //used to turn menu_items to buttons
    Integer[]order_items = new Integer[0]; //checks what buttons are checked

    DecimalFormat df = new DecimalFormat("0.00"); //money format
    
    /** Sets up the top part of GUI. 
     * Adds the Bases buttons: Bowl & Burrito
     * 
     * @param       none                    No parameters because it is just a setup
     * @return      void                    Creates top layout of Demo(). Doesn't return anything
     * @throws      none                    No throws because no parameters. Everything is constant and no variables
     */
    void base_setup() {
        //burrito radiobutton setup
        JRadioButton burrito = new JRadioButton();
        burrito.setText("Burrito");
        burrito.setBounds(50,30,120,50);
        this.add(burrito);

        //bowl radiobutton setup
        JRadioButton bowl = new JRadioButton();
        bowl.setText("Bowl");
        bowl.setBounds(50,60,120,50);
        this.add(bowl);

        base.add(burrito);
        base.add(bowl);
    } 

    /** Turns gathered menu item data and turns it into buttons.
     * Menu item is a 2D array and was populated with data from the inventory database.
     * To be able to send orders, buttons must be made to indicate what the customer orders.
     * Turning the array of menu items into RadioButtons allows the server to take orders correctly.
     * 
     * @param       str_arr                 Str_arr represents the menu items where [i][0] = name AND [i][1] = price
     * @return      void                    Creates the buttons that can be clicked to take orders. Doesn't return anything
     * @throws      null_handling           Error handing done with if statements. If null is ever reached in the array, break the loop.
     */
    void arr_to_buttons(String[][] str_arr) {

        int y_val = 120; //gui spaciing
        int protein_space = 0; //extra spacing after protein group
        buttons = new JRadioButton[str_arr.length];
        for (int i = 0; i < str_arr.length; i++) {
            if (str_arr[i][0] == null) { break; }
            JRadioButton temp = new JRadioButton(str_arr[i][0]);
            JLabel temp_ = new JLabel(str_arr[i][1]);
            temp.addActionListener(this);
            this.add(temp);

            //protein check
            if (str_arr[i][2].equals("true\n")) {
                protein.add(temp);
            } else {
                protein_space ++;
            }

            if (protein_space == 1) {
                protein_space++;
                y_val += 30;
            }

            temp.setBounds(50,y_val,200,50);
            temp_.setBounds(630,y_val,120,50);
            this.add(temp_);
            
            buttons[i] = temp;
            y_val += 30;
        }   

    }//end of arr_to_buttons

     /** Miscellaneous buttons for various task. The buttons: back_to_login, sendOrder, total, clear, addSeasonal.
     * <ul>
     * <li>back_to_login: Logs out of order entry GUI and back to log in page.
     * <li>sendOrder: Sends order and queries SQL command to update order_entries and inventory
     * <li>total: textbox showing updated current total
     * <li>addSeasonal: adding a seasonal item to GUI, order_entries, and inventory
     * </ul>
     * 
     * @param       none                    No parameters because it is just a setup
     * @return      void                    Creates misc buttons for various taks. No return.
     * @throws      NumberFormatException   addSeasonal throws if user input price is not a valid double.
     */
    void misc_buttons() {

        //logout
        back_to_login.setBounds(590,45,120,30);
        this.add(back_to_login);
        back_to_login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                new login_view();
                dispose();

            }
        });

        //same customer radiobutton
        same_customer.setBounds(30,640,200,50);
        this.add(same_customer);

        //send Order 
        JButton sendOrder = new JButton("Send Order");
        sendOrder.setBounds(30,690,120,30);

        sendOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String base_choice = getSelectedButtonText(base);
                String protein_choice = getSelectedButtonText(protein);
                String sqlStatement = sqlStatementFunction(base_choice, protein_choice);
                sendData(sqlStatement);

            }
        });


        //total textbox
        total.setEditable(false);
        total.setText("Total: " + df.format(curr_total));
        total.setBounds(600,690,100,30);
        this.add(total);
        this.add(sendOrder);

        //clear
        JButton clear = new JButton("Clear");
        clear.setBounds(150, 690,120,30);
        this.add(clear);

        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //resetting all buttons
                base.clearSelection();
                protein.clearSelection();
                curr_total = 0;
                total.setText("Total: " + df.format(curr_total));

                for (int i = 0; i < buttons.length; i++) {
                    if (buttons[i] == null) { break; }
                    buttons[i].setSelected(false);
                    
                }
            }
        });

        //add Seasonal
        JButton addSeasonal = new JButton("Add Seasonal Item");
        addSeasonal.setBounds(270,690,200,30);
        this.add(addSeasonal);
        addSeasonal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String seasonal_name = JOptionPane.showInputDialog("Name of Seasonal Item: ");

                //check if name is valid
                if (seasonal_name.isBlank()) {
                    JOptionPane.showMessageDialog(null, "Not a Valid Name");
                    return;
                }

                //check if name doesn't already exists
                for (int i = 0; i < menu_items.length; i++) {
                    if (menu_items[i][0] == null) {break;}
                    String temp = menu_items[i][0];

                    if (temp.trim().equals(seasonal_name.trim())) { //trim() because menu_items[i][0] has \n at the end
                        JOptionPane.showMessageDialog(null,"Menu Item Already Exists.");
                        return;
                    }

                }

                String seasonal_price = JOptionPane.showInputDialog("Price of \"" + seasonal_name + "\"(x.xx): ");

                //check if price is valid double
                try {
                    Double.parseDouble(seasonal_price);
                } catch (NumberFormatException e_) {
                    JOptionPane.showMessageDialog(null,"Not a Valid Price.");
                    return;
                }

                String max_count = JOptionPane.showInputDialog("Max Count of \"" + seasonal_name + "\"(non-neg int): ");

                //check if max_count is valid int
                if (  Integer.parseInt(max_count) <= 0) {
                    JOptionPane.showMessageDialog(null,"Not a Valid Count.");
                    return;
                }

                addSeasonalItem(seasonal_name,seasonal_price,max_count);

                JOptionPane.showMessageDialog(null,"Added \"" + seasonal_name + "\": $" + seasonal_price + " to menu.");
            }
        });
    }

    /** The purpose of this function is to look through the button group and returns what the name of what button is being selected.
     * Used for button groups: base and protein
     * Base: base contains Bowl and Burrito
     * Protein: Chicken, Steak, Ground Beef, Roasted Vegetables
     * 
     * @param       buttonGroup             Takes in a button group to iterate through (Base or Protein)
     * @return      String                  Returns the name of the button
     * @throws      "N/A"                   If no button was pressed, returns N/A
     */
    String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                //JOptionPane.showMessageDialog(null,button.getText());
                return button.getText();
            }
        }

        return "N/A";
    }

    /** Creates a valid sql Statement that can be queried into the database.
     * The Base and Protein is determined by getSelectedButtonText() and is used to create a statements that can be passed.
     * This statement is used in sendData to updatee order_entries and inventory
     * 
     * @param       base_                   Predetermined String base made by getSelectedButtonText() used in return string
     * @param       protein_                Predetermined String protein made by getSelectedButtonText() used in return string
     * @return      String                  Returns valid String for SQL Queries
     * @throws      null_checker            If nothing is ordered, returns without creating a sqlStatment. A invalid statement will not run.
     */
    String sqlStatementFunction(String base_, String protein_) {

        //0-4 are proteins, everything after is something to add

        /*
        Guacamole, Queso, Chips_Salsa, Chips_Queso, Chips_Guac, Brownie, Cookie, 16, 22, cost, date, seasonal items
        4          5      6            7            8           9        10      11  12  13    14    15+
        */


        //finding time for dt column 
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("/MM/dd/YYYY");  
        LocalDateTime now = LocalDateTime.now(); 

        String date = dtf.format(now);

        //used for unique order number
        DateTimeFormatter dtf_order= DateTimeFormatter.ofPattern("MMddYYYY");  
        String order_date_num = dtf_order.format(now);

        if (!(same_customer.isSelected())) {
            customer_id++;
        }
        String ret = "";
        if (order_items.length == 0) {return null;}
        int ordernumber_stmt = Integer.parseInt( order_date_num + order_id);
        order_id++;

        base_ = base_.replace("\n","");
        protein_ = protein_.replace("\n", "");
        
        //if length is greater than 13 (normal menu), there is a seasonal item added
        if (buttons.length > 13) {
            ret = "INSERT INTO order_entries (order_number, customer, base, protein, guacamole, queso, chips_salsa, chips_queso, chips_guac, brownie, cookie, drink_16oz, drink_22oz, cost, date) VALUES ( " + ordernumber_stmt + "," + customer_id + ",\'" + base_ + "\',\'" + protein_  + "\', \'" + order_items[4] + "\', \'" + order_items[6] + "\', \'" + order_items[6] + "\', \'" + order_items[7] + "\', \'" + order_items[8] + "\', \'" + order_items[9] + "\', \'" + order_items[10] + "\', \'" + order_items[11] + "\', \'" + order_items[12] + "\', " + df.format(curr_total) + ", \'" + date + "\', \'" + order_items[13] + "\')";
        } else {
            ret = "INSERT INTO order_entries (order_number, customer, base, protein, guacamole, queso, chips_salsa, chips_queso, chips_guac, brownie, cookie, drink_16oz, drink_22oz, cost, date) VALUES ( " + ordernumber_stmt + "," + customer_id + ",\'" + base_ + "\',\'" + protein_  + "\', \'" + order_items[4] + "\', \'" + order_items[6] + "\', \'" + order_items[6] + "\', \'" + order_items[7] + "\', \'" + order_items[8] + "\', \'" + order_items[9] + "\', \'" + order_items[10] + "\', \'" + order_items[11] + "\', \'" + order_items[12] + "\', " + df.format(curr_total) + ", \'" + date + "\')";
        }

        base.clearSelection();
        protein.clearSelection();
        JOptionPane.showMessageDialog(null,"Total: " + df.format(curr_total));
        curr_total = 0;
        total.setText("Total: " + df.format(curr_total));

        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] == null) { break; }
            buttons[i].setSelected(false);
            
        }
        return ret;
    }

    /** Adds user input seasonal item to GUI, inventory, and order_entries.
     * GUI: Adds seasonal item as a button.
     * Inventory: Adds seasonal item as a row.
     * Order_entries: Adds seasonal item as a column.
     * 
     * @param       seasonal_name           Name of seasonal item
     * @param       seasonal_price          Price of seasonal item
     * @param       max_count               Max amount of seaonal item in inventory
     * @return      void                    No return because this function sends and updates data to GUI, inventory, and order_entries.
     * @throws      JDBC_Exceptions         Try, catch, and throws for connecting to db, actions in db, and closing db.
     */
    void addSeasonalItem(String seasonal_name, String seasonal_price, String max_count) {
        Connection conn = null;
        String teamNumber = "22";
        String sectionNumber = "913";
        String dbName = "csce315_" + sectionNumber + "_" + teamNumber ;
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        new dbSetup(); 
    
        //Connecting to the database
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

          System.out.println("Opened database successfully");    


        try {
            Statement stmt = conn.createStatement();

            String sqlStatement = "INSERT INTO inventory (food_id, food_name, current_count, max_count, sell_price, is_menu_item) VALUES (" + next_food_id + ",\'" + seasonal_name + "\',0," + max_count + "," + seasonal_price + ",'t')";

            //inserting into inventory
            stmt.executeUpdate(sqlStatement);

            //inserting into order_entries
            sqlStatement = "ALTER TABLE order_entries ADD " + seasonal_name + " BIT";
            stmt.executeUpdate(sqlStatement);

            
            
        } catch (Exception e) {
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

        updateView();
        //SwingUtilities.updateComponentTreeUI(this);
    }
        
    /** Goes into to database and retrieves all menu items from inventory.
     * 
     * @param       none                    No parameters, retrieves data from database and adds it to String[][]
     * @return      String[][]              Returns rows from inventory that contains name and price
     * @throws      JDBC_Exceptions         Try, catch, and throws for connecting to db, actions in db, and closing db.
     */
    String[][] getData() { //string[] that contains [food_name] [sell_price]
        String[][] data = new String[0][0];
        Connection conn = null;
        String teamNumber = "22";
        String sectionNumber = "913";
        String dbName = "csce315_" + sectionNumber + "_" + teamNumber ;
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        new dbSetup(); 
    
        //Connecting to the database
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

          System.out.println("Opened database successfully");    


        try {
            Statement stmt = conn.createStatement();

            String sqlStatement = "SELECT count(food_id) AS count FROM inventory";

            //send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);
            result.next();
            int length = Integer.parseInt(result.getString("count"));
            next_food_id = length; //used for seasonal item
            sqlStatement = "SELECT * FROM inventory ORDER BY food_id ASC";
            
            result = stmt.executeQuery(sqlStatement);

            
            String[][]temp = new String [length][3]; //used to get exact length of array needed for data
            int entry_nr = 0;
            while (result.next()) {

                boolean is_menu_item = result.getBoolean("is_menu_item");

                if (is_menu_item) {
                    temp[entry_nr][0] = result.getString("food_name")+"\n";
                    temp[entry_nr][1] = result.getString("sell_price")+"\n";


                    String is_prot = Boolean.toString(result.getBoolean("is_protein"));
                    temp[entry_nr][2] = is_prot+"\n";
                    entry_nr++;
                }

            }
            data = new String[entry_nr][3];

            for (int i = 0; i < entry_nr; i++) {
                data[i][0] = temp[i][0];
                data[i][1]= temp[i][1];
                data[i][2] = temp[i][2];
            }


            
        } catch (Exception e) {
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
        
        return data;

      } //end of getdata

    /** Sends Data to database.
     * Updates the order_entries by adding a row.
     * Updates the inventory by modifying current count of bought items.
     * 
     * @param       sqlStatement            Takes in sqlStatement created by sqlStatementFunction() to be queried
     * @return      void                    No return because this function sends and updates data to inventory and order_entries. 
     * @throws      JDBC_Exceptions         Try, catch, and throws for connecting to db, actions in db, and closing db.
     */
    void sendData(String sqlStatement) {
        Boolean isempty = true;
        for (int i = 0; i < order_items.length; i++) {
            if (order_items[i] != 0) { isempty = false; } 
        }

        if (isempty) { return; }

        
        Connection conn = null;
        String teamNumber = "22";
        String sectionNumber = "913";
        String dbName = "csce315_" + sectionNumber + "_" + teamNumber ;
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        new dbSetup(); 

        //Connecting to the database
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        System.out.println("Opened database successfully");

        
        try {
            //create a statement object
            Statement stmt = conn.createStatement();

            //sending order to order entries
            stmt.executeUpdate(sqlStatement); //executeUpdate to get arround execption

            //updating inventory
            for (int i = 0; i < order_items.length; i++) {
                if (order_items[i] == 1) {
                    String column_name = menu_items[i][0];
                    column_name = column_name.replace("\n", "");
                    
                    String SQLstmt_ = "UPDATE inventory SET current_count = current_count - 1 WHERE food_name = \'"+ column_name + "\'";
                    System.out.println(SQLstmt_);
                    stmt.executeUpdate(SQLstmt_);

                }
            }
        } catch (Exception e_){
            e_.printStackTrace();
            System.err.println(e_.getClass().getName()+": "+e_.getMessage());
        }
    
        //closing the connection
        try {
        conn.close();
        System.out.println("Connection Closed.");
        } catch(Exception e) {
        System.out.println("Connection NOT Closed.");
        }//end try catch

        order_items = new Integer[buttons.length]; //reseting ordered items
    }

    /** Used to reload after adding a Seasonal Item. 
     * Essentially creates a new instance of Demo() and deletes the old one. 
     * Used specifically in addSeasonalItem().
     * 
     * @param       none                    Reloads the class so no parameters needed. All information is in function
     * @return      void                    Creates new instance in function, doesn't return anything.
     * @throws      none                    Calls constructor again and there are no throws in constructor
     */
    void updateView() {
        //basically constructor
        new Demo(); 
        buttons = new JRadioButton[0];
        menu_items = getData();
        this.setLayout(null);
        base_setup();
        arr_to_buttons(menu_items);
        misc_buttons();
    }

    /** Demo Constructor, create GUI and uses all functions.
     * 
     * @param       none                    Demo class constructor
     * @return      none                    Demo class constructor
     * @throws      none                    Demo class constructor
     */
    public Demo() {
        menu_items = getData();
        this.setLayout(null);
        base_setup();
        arr_to_buttons(menu_items);
        misc_buttons();
        this.setBounds(100, 100, 768, 768); 
        this.setTitle("CABO GRILL ORDER ENTRY"); 
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true); 

        JPanel panel=new JPanel();
        JScrollPane scrollBar=new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.add(scrollBar);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    /** Overriding Actionlistener to work with JRadioButtons.
     * Allows JRadioButtons to real time update the current total of the customer's order.
     * 
     * @param       ActionEvent             ActionEvent used to override Action Listener                    
     * @return      void                    Used as an ActionListener so no return
     * @throws      null_checker            if button[i] is null, break and return
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        curr_total = 0.0;
        boolean zero = true;

        order_items = new Integer[buttons.length];
        for (int i = 0; i < buttons.length; i++ ) {

            if (buttons[i] == null) { break; }
            if (buttons[i].isSelected()) {
                zero = false;
                curr_total += Double.parseDouble(menu_items[i][1]);
                order_items[i] = 1;
            } else {
                order_items[i] = 0;
            }
        }

        if (zero) {
            curr_total = 0.0;
        }


        total.setText("Total: " + df.format(curr_total));


    }
}

public class order_entry_test { public static void main(String args[]) { new Demo(); } }