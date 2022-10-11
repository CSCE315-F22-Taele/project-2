
import java.sql.*;
import java.awt.event.*;

import javax.management.QueryEval;
import javax.swing.*;   


/*
CSCE 315
9-27-2021 Lab
  javac *java && java -cp ".:postgresql-42.2.8.jar" psqlCustomer
 */

class Demo extends JFrame { 
    JButton jButton;
    
    JLabel label_base;
    ButtonGroup grp_base; 
    JRadioButton jrb_burrito; 
    JRadioButton jrb_bowl;
 
    JLabel label_protein;
    ButtonGroup grp_protein; 
    JRadioButton jrb_chicken; 
    JRadioButton jrb_steak;
    JRadioButton jrb_vegetable;


    JLabel label_sauce;
    JRadioButton jrb_queso; 
    JRadioButton jrb_guac;

    JLabel label_dessert;
    JRadioButton jrb_brownie; 
    JRadioButton jrb_cookie;

    JLabel label_chips;
    JRadioButton jrb_c_salsa; 
    JRadioButton jrb_c_queso;
    JRadioButton jrb_c_guac;

    JLabel label_drinks; 
    JRadioButton jrb_small_drink; 
    JRadioButton jrb_large_drink; 

  
    public Demo() { 
  

        this.setLayout(null); 

        //send order
        jButton = new JButton("Send Order"); 
        jButton.setBounds(50,250 , 120, 30); 
        this.add(jButton); 

        //base
        jrb_burrito = new JRadioButton();
        jrb_bowl = new JRadioButton();
        grp_base = new ButtonGroup();

        label_base = new JLabel("Burrito or Bowl"); 
        jrb_burrito.setText("Burrito");
        jrb_bowl.setText("Bowl");

        jrb_burrito.setBounds(50,30,120,50);
        jrb_bowl.setBounds(50,60,120,50);
        label_base.setBounds(50,0,150,50);

        this.add(jrb_burrito);
        this.add(jrb_bowl);
        this.add(label_base);
        grp_base.add(jrb_burrito);
        grp_base.add(jrb_bowl);

        //protein
        jrb_chicken = new JRadioButton();
        jrb_steak = new JRadioButton();
        jrb_vegetable = new JRadioButton();
        grp_protein = new ButtonGroup();
        label_protein = new JLabel("Protein");

        jrb_chicken.setText("Chicken");
        jrb_steak.setText("Steak");
        jrb_vegetable.setText("Vegetable");

        jrb_chicken.setBounds(300,30,120,50);
        jrb_steak.setBounds(300,60,120,50);
        jrb_vegetable.setBounds(300,90,120,50);
        label_protein.setBounds(300,0,150,50);

        this.add(jrb_chicken);
        this.add(jrb_steak);
        this.add(jrb_vegetable);
        this.add(label_protein);
        grp_protein.add(jrb_chicken);
        grp_protein.add(jrb_steak);
        grp_protein.add(jrb_vegetable);

        //sauce
        jrb_queso = new JRadioButton();
        jrb_guac = new JRadioButton();
        label_sauce = new JLabel("Sauces");

        jrb_queso.setText("Queso");
        jrb_guac.setText("Guac");

        jrb_queso.setBounds(550,30,120,50);
        jrb_guac.setBounds(550,60,120,50);
        label_sauce.setBounds(550,0,150,50);

        this.add(jrb_queso);
        this.add(jrb_guac);
        this.add(label_sauce);

        //chips
        jrb_c_salsa = new JRadioButton();
        jrb_c_queso = new JRadioButton();
        jrb_c_guac = new JRadioButton();
        label_chips = new JLabel("Chips");

        jrb_c_salsa.setText("Chips and Salsa");
        jrb_c_queso.setText("Chips and Queso");
        jrb_c_guac.setText("Chips and Guac");

        jrb_c_salsa.setBounds(800,30,200,50);
        jrb_c_queso.setBounds(800,60,200,50);
        jrb_c_guac.setBounds(800,90,200,50);
        label_chips.setBounds(800,0,200,50);

        this.add(jrb_c_salsa);
        this.add(jrb_c_queso);
        this.add(jrb_c_guac);
        this.add(label_chips);

        //dessert
        jrb_brownie = new JRadioButton();
        jrb_cookie = new JRadioButton();
        label_dessert = new JLabel("Desserts");

        jrb_brownie.setText("Brownie");
        jrb_cookie.setText("Cookie");

        jrb_brownie.setBounds(1050,30,120,50);
        jrb_cookie.setBounds(1050,60,120,50);
        label_dessert.setBounds(1050,0,200,50);

        this.add(jrb_brownie);
        this.add(jrb_cookie);
        this.add(label_dessert);


        //drinks
        jrb_small_drink = new JRadioButton(); 
        jrb_large_drink = new JRadioButton(); 
        label_drinks = new JLabel("Drink Size"); 

        jrb_small_drink.setText("16oz"); 
        jrb_large_drink.setText("22oz"); 

        jrb_small_drink.setBounds(1300, 30, 120, 50); 
        jrb_large_drink.setBounds(1300, 60, 120, 50); 
        label_drinks.setBounds(1300, 0, 150, 50); 

        this.add(jrb_small_drink); 
        this.add(jrb_large_drink); 
        this.add(label_drinks); 
  
        jButton.addActionListener(new ActionListener() { 
  
            public void actionPerformed(ActionEvent e) 
            { 
                //base
                String base = "N/A";
                if (jrb_burrito.isSelected()) { base = "Burrito"; } 
                else if (jrb_bowl.isSelected()) { base = "Bowl";} 
                String base_output = "Base: " + base;
                JOptionPane.showMessageDialog(Demo.this,  base_output); 
               
                //protein
                String protein = "N/A";
                if (jrb_chicken.isSelected()) { protein = "Chicken"; } 
                else if (jrb_steak.isSelected()) { protein = "Steak";}
                else if (jrb_vegetable.isSelected()) {protein = "Vegetable";} 
                String protein_output = "Protein: " + protein;
                JOptionPane.showMessageDialog(Demo.this,  protein_output);

                //sauce
                String queso = "0";
                String guac = "0";  
                if (jrb_queso.isSelected()) { queso = "1"; } 
                if (jrb_guac.isSelected()) { guac = "1"; } 
                String queso_output = "Queso: " + queso;
                String guac_output = "Guac: " + guac;
                JOptionPane.showMessageDialog(Demo.this,  queso_output); 
                JOptionPane.showMessageDialog(Demo.this, guac_output); 

                //chips
                String c_salsa = "0";
                String c_queso = "0";
                String c_guac = "0";  
                if (jrb_c_salsa.isSelected()) { c_salsa = "0";}
                if (jrb_c_queso.isSelected()) { c_queso = "1"; } 
                if (jrb_c_guac.isSelected()) { c_guac = "1"; } 
                String c_salsa_output = "Chips and Salsa: " + c_salsa;
                String c_queso_output = "Chips and Queso: " + c_queso;
                String c_guac_output = "Chips and Guac: " + c_guac;
                JOptionPane.showMessageDialog(Demo.this, c_salsa_output); 
                JOptionPane.showMessageDialog(Demo.this, c_queso_output); 
                JOptionPane.showMessageDialog(Demo.this, c_guac_output); 

                //dessert
                String brownie = "0";
                String cookie = "0";
                if (jrb_brownie.isSelected()) {brownie = "1";}
                if (jrb_cookie.isSelected()) {cookie = "1";}
                String brownie_output = "Brownie: " + brownie;
                String cookie_output = "Cookie: " + cookie;
                JOptionPane.showMessageDialog(Demo.this, brownie_output);
                JOptionPane.showMessageDialog(Demo.this, cookie_output);

                //drink
                String small_drink = "0";
                String large_drink = "0";  
                if (jrb_small_drink.isSelected()) { small_drink = "1"; } 
                if (jrb_large_drink.isSelected()) { large_drink = "1"; } 
                String small_output = "16 oz: " + small_drink;
                String large_output = "22 oz: " + large_drink;
                JOptionPane.showMessageDialog(Demo.this,  small_output); 
                JOptionPane.showMessageDialog(Demo.this, large_output); 

            } 
        }); 
    } 

}


public class order_entry_test {
  public static void main(String args[]) {
    Demo f = new Demo(); 
  
    // Setting Bounds of JFrame. 
    f.setBounds(100, 100, 1450, 350); 

    // Setting Title of frame. 
    f.setTitle("order entry"); 

    // Setting Visible status of frame as true. 
    f.setVisible(true); 

    /* PSQL CONNECTION STUFF 

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

    try {
       //create a statement object
        Statement stmt = conn.createStatement();
        // String sqlStatement = "INSERT INTO customer (customer_id, total_cost) VALUES (" + customer_number + ", " + total_cost + ")";
        // stmt.executeUpdate(sqlStatement);
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
    */
  }//end main
}//end Class
