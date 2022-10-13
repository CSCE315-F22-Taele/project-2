
import java.sql.*;
import java.awt.event.*;

import javax.management.QueryEval;
import javax.swing.*;   

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 

import java.io.IOException;  


/*
CSCE 315
9-27-2021 Lab
  javac *java && java -cp ".:postgresql-42.2.8.jar" order_entry_test
 */

class Demo extends JFrame { 
    JButton jButton;
    JButton reset;
    JLabel title;

    JRadioButton same_customer;

    ButtonGroup grp_base; 
    JRadioButton jrb_burrito; 
    JRadioButton jrb_bowl;
 
    ButtonGroup grp_protein; 
    JRadioButton jrb_chicken; 
    JRadioButton jrb_steak;
    JRadioButton jrb_vegetable;
    JLabel label_chicken;
    JLabel label_steak;
    JLabel label_vegetable;


    JRadioButton jrb_queso; 
    JRadioButton jrb_guac;
    JLabel label_queso;
    JLabel label_guac;

    JRadioButton jrb_c_salsa; 
    JRadioButton jrb_c_queso;
    JRadioButton jrb_c_guac;
    JLabel label_c_salsa;
    JLabel label_c_queso;
    JLabel label_c_guac;

    JRadioButton jrb_brownie; 
    JRadioButton jrb_cookie;
    JLabel label_brownie;
    JLabel label_cookie;

    JRadioButton jrb_small_drink; 
    JRadioButton jrb_large_drink; 
    JLabel label_small_drink;
    JLabel label_large_drink;

  
    public Demo() { 
  

        this.setLayout(null); 

        //send order
        jButton = new JButton("Send Order"); 
        jButton.setBounds(50,690 , 120, 30); 
        this.add(jButton); 

        reset = new JButton("Reset");
        reset.setBounds(200,690,120,30);
        this.add(reset);

        reset.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            grp_base.clearSelection();
            grp_protein.clearSelection();
            jrb_queso.setSelected(false);
            jrb_guac.setSelected(false);
            jrb_brownie.setSelected(false);
            jrb_cookie.setSelected(false); 
            jrb_c_salsa.setSelected(false);
            jrb_c_queso.setSelected(false);
            jrb_c_guac.setSelected(false);
            jrb_small_drink.setSelected(false);
            jrb_large_drink.setSelected(false);

            
          }
       });

       /*
        title = new JLabel("CABO GRILL");
        title.setBounds(10, 0, 120,50);

       this.add(title);
       */

        //base
        jrb_burrito = new JRadioButton();
        jrb_bowl = new JRadioButton();
        grp_base = new ButtonGroup();


        jrb_burrito.setText("Burrito");
        jrb_bowl.setText("Bowl");
        jrb_burrito.setBounds(50,30,120,50);
        jrb_bowl.setBounds(50,60,120,50);


        this.add(jrb_burrito);
        this.add(jrb_bowl);
        grp_base.add(jrb_burrito);
        grp_base.add(jrb_bowl);

        //protein
        jrb_chicken = new JRadioButton();
        jrb_steak = new JRadioButton();
        jrb_vegetable = new JRadioButton();
        grp_protein = new ButtonGroup();

        label_chicken = new JLabel("$7.29");
        label_steak = new JLabel("$8.29");
        label_vegetable = new JLabel("$7.29");

        jrb_chicken.setText("Chicken");
        jrb_steak.setText("Steak");
        jrb_vegetable.setText("Vegetable");

        jrb_chicken.setBounds(50,120,120,50);
        jrb_steak.setBounds(50,150,120,50);
        jrb_vegetable.setBounds(50,180,120,50);

        label_chicken.setBounds(630,120,120,50);
        label_steak.setBounds(630,150,120,50);
        label_vegetable.setBounds(630,180,120,50);

        this.add(jrb_chicken);
        this.add(jrb_steak);
        this.add(jrb_vegetable);
        grp_protein.add(jrb_chicken);
        grp_protein.add(jrb_steak);
        grp_protein.add(jrb_vegetable);
        this.add(label_chicken);
        this.add(label_steak);
        this.add(label_vegetable);

        //sauce
        jrb_queso = new JRadioButton();
        jrb_guac = new JRadioButton();

        jrb_queso.setText("Queso");
        jrb_guac.setText("Guac");

        label_queso = new JLabel("$2.00");
        label_guac = new JLabel("$2.00");

        jrb_queso.setBounds(50,240,120,50);
        jrb_guac.setBounds(50,270,120,50);
        label_queso.setBounds(630,240,120,50);
        label_guac.setBounds(630, 270, 120, 50);

        this.add(jrb_queso);
        this.add(jrb_guac);
        this.add(label_queso);
        this.add(label_guac);

        //chips
        jrb_c_salsa = new JRadioButton();
        jrb_c_queso = new JRadioButton();
        jrb_c_guac = new JRadioButton();

        label_c_salsa = new JLabel("$2.19");
        label_c_queso = new JLabel("$3.64");
        label_c_guac = new JLabel("$3.69");

        jrb_c_salsa.setText("Chips and Salsa");
        jrb_c_queso.setText("Chips and Queso");
        jrb_c_guac.setText("Chips and Guac");

        jrb_c_salsa.setBounds(50,330,200,50);
        jrb_c_queso.setBounds(50,360,200,50);
        jrb_c_guac.setBounds(50,390,200,50);
        
        label_c_salsa.setBounds(630,330,200,50);
        label_c_queso.setBounds(630,360,200,50);
        label_c_guac.setBounds(630,390,200,50);


        this.add(jrb_c_salsa);
        this.add(jrb_c_queso);
        this.add(jrb_c_guac);

        this.add(label_c_salsa);
        this.add(label_c_queso);
        this.add(label_c_guac);

        //dessert
        jrb_brownie = new JRadioButton();
        jrb_cookie = new JRadioButton();

        label_brownie = new JLabel("$1.99");
        label_cookie = new JLabel("$1.99");

        jrb_brownie.setText("Brownie");
        jrb_cookie.setText("Cookie");

        jrb_brownie.setBounds(50,450,120,50);
        jrb_cookie.setBounds(50,480,120,50);

        label_brownie.setBounds(630,450,200,50);
        label_cookie.setBounds(630,480,200,50);

        this.add(jrb_brownie);
        this.add(jrb_cookie);

        this.add(label_brownie);
        this.add(label_cookie);


        //drinks
        jrb_small_drink = new JRadioButton(); 
        jrb_large_drink = new JRadioButton(); 

        jrb_small_drink.setText("16oz"); 
        jrb_large_drink.setText("22oz"); 

        label_small_drink = new JLabel("$2.25");
        label_large_drink = new JLabel("$2.45");

        jrb_small_drink.setBounds(50, 570, 120, 50); 
        jrb_large_drink.setBounds(50, 600, 120, 50); 

        label_small_drink.setBounds(630,570,200,50);
        label_large_drink.setBounds(630,600,200,50);

        this.add(jrb_small_drink); 
        this.add(jrb_large_drink); 
        this.add(label_small_drink);
        this.add(label_large_drink);


        same_customer = new JRadioButton();
        same_customer.setText("Same Customer?");
        same_customer.setBounds(50,630,200,50);
        this.add(same_customer);
  
        jButton.addActionListener(new ActionListener() { 

          DateTimeFormatter dtf = DateTimeFormatter.ofPattern("/MM/dd/YYYY");  
          LocalDateTime now = LocalDateTime.now(); 
          
          DateTimeFormatter dtf_order= DateTimeFormatter.ofPattern("MMddYYYY");  
          String order_date_num = dtf_order.format(now);

          
          String date = dtf.format(now);
          

          int ordernumber = 0;
          int customer = -1; //very first customer will always never have same customer selected
          public void actionPerformed(ActionEvent e) { 
            //order number 
            double cost = 0.0;
            //base
            String base = "N/A";
            if (jrb_burrito.isSelected()) { base = "Burrito"; } 
            else if (jrb_bowl.isSelected()) { base = "Bowl";} 
            String base_output = "Base: " + base;
            //JOptionPane.showMessageDialog(Demo.this,  base_output); 
            grp_base.clearSelection();
            
            //protein
            String protein = "N/A";
            if (jrb_chicken.isSelected()) { protein = "Chicken"; cost += 7.29;} 
            else if (jrb_steak.isSelected()) { protein = "Steak"; cost += 8.29;}
            else if (jrb_vegetable.isSelected()) {protein = "Vegetable"; cost += 7.29;} 
            String protein_output = "Protein: " + protein;
            //JOptionPane.showMessageDialog(Demo.this,  protein_output);
            grp_protein.clearSelection();

            //sauce
            String queso = "0";
            String guac = "0";  
            if (jrb_queso.isSelected()) { queso = "1"; cost += 2.00; } 
            if (jrb_guac.isSelected()) { guac = "1"; cost += 2.00; } 
            String queso_output = "Queso: " + queso;
            String guac_output = "Guac: " + guac;
            //JOptionPane.showMessageDialog(Demo.this,  queso_output); 
            //JOptionPane.showMessageDialog(Demo.this, guac_output); 
            jrb_queso.setSelected(false);
            jrb_guac.setSelected(false);

            //chips
            String c_salsa = "0";
            String c_queso = "0";
            String c_guac = "0";  
            if (jrb_c_salsa.isSelected()) { c_salsa = "0"; cost += 2.19;}
            if (jrb_c_queso.isSelected()) { c_queso = "1"; cost += 3.64;} 
            if (jrb_c_guac.isSelected()) { c_guac = "1"; cost += 3.69;} 
            String c_salsa_output = "Chips and Salsa: " + c_salsa;
            String c_queso_output = "Chips and Queso: " + c_queso;
            String c_guac_output = "Chips and Guac: " + c_guac;
            //JOptionPane.showMessageDialog(Demo.this, c_salsa_output); 
            //JOptionPane.showMessageDialog(Demo.this, c_queso_output); 
            //JOptionPane.showMessageDialog(Demo.this, c_guac_output); 
            jrb_c_salsa.setSelected(false);
            jrb_c_queso.setSelected(false);
            jrb_c_guac.setSelected(false);

            //dessert
            String brownie = "0";
            String cookie = "0";
            if (jrb_brownie.isSelected()) {brownie = "1"; cost += 1.99;}
            if (jrb_cookie.isSelected()) {cookie = "1"; cost += 1.99;}
            String brownie_output = "Brownie: " + brownie;
            String cookie_output = "Cookie: " + cookie;
            //JOptionPane.showMessageDialog(Demo.this, brownie_output);
            //JOptionPane.showMessageDialog(Demo.this, cookie_output);
            jrb_brownie.setSelected(false);
            jrb_cookie.setSelected(false);

            //drink
            String small_drink = "0";
            String large_drink = "0";  
            if (jrb_small_drink.isSelected()) { small_drink = "1"; cost += 2.25;} 
            if (jrb_large_drink.isSelected()) { large_drink = "1"; cost += 2.45;} 
            String small_output = "16 oz: " + small_drink;
            String large_output = "22 oz: " + large_drink;
            //JOptionPane.showMessageDialog(Demo.this,  small_output); 
            //JOptionPane.showMessageDialog(Demo.this, large_output); 
            jrb_small_drink.setSelected(false);
            jrb_large_drink.setSelected(false);

            if (! (same_customer.isSelected()) ) {customer += 1;}
            same_customer.setSelected(false);

            int ordernumber_stmt = Integer.parseInt( order_date_num + ordernumber);
            String sql_stmt = "INSERT INTO order_entries (order_number, customer, base, protein, guacamole, queso, chips_salsa, chips_queso, chips_guac, brownie, cookie, drink_16oz, drink_22oz, cost, date) VALUES (" + ordernumber_stmt + ", " + customer + ", \'" + base + "\', \'" + protein + "\', \'" + guac + "\', \'" + queso + "\', \'" + c_salsa + "\', \'" + c_queso + "\', \'" + c_guac + "\', \'" + brownie + "\', \'" + cookie + "\', \'" + small_drink + "\', \'" + large_drink + "\', " + cost + ", \'" + date + "\')";



            //JOptionPane.showMessageDialog(Demo.this, sql_stmt);
            ordernumber += 1;

            Connection conn = null;
            String teamNumber = "22";
            String sectionNumber = "913";
            String dbName = "csce315_" + sectionNumber + "_" + teamNumber ;
            String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
            dbSetup myCredentials = new dbSetup(); 
        
            //Connecting to the database
            try {
                conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
             } catch (Exception e_) {
                e_.printStackTrace();
                System.err.println(e_.getClass().getName()+": "+e_.getMessage());
                System.exit(0);
             }

             System.out.println("Opened database successfully");

             try{
              //create a statement object
              Statement stmt = conn.createStatement();

               stmt.executeUpdate(sql_stmt); //executeUpdate to get arround execption
             }
            catch (Exception e_){
              e_.printStackTrace();
              System.err.println(e_.getClass().getName()+": "+e_.getMessage());
          }
       
          
           //closing the connection
           try {
             conn.close();
             System.out.println("Connection Closed.");
           } catch(Exception e_) {
             System.out.println("Connection NOT Closed.");
           }//end try catch
            
            } 
        }); 
        }

        
        

}


public class order_entry_test {
  public static void main(String args[]) {
    Demo f = new Demo(); 
  
    // Setting Bounds of JFrame. 
    f.setBounds(100, 100, 768, 768); 

    // Setting Title of frame. 
    f.setTitle("CABO GRILL ORDER ENTRY"); 

    // Setting Visible status of frame as true. 
    f.setVisible(true); 

  }//end main
}//end Class
