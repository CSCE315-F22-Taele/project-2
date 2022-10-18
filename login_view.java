import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//@author Semion Dyadkin
public class login_view implements ActionListener {
    private static JLabel username;
    private static JTextField userText;
    private static JLabel password;
    private static JPasswordField passText;
    private static JButton button;
    private static JLabel success;
    

        //GUI
        JPanel panel = new JPanel();
        JFrame frame = new JFrame();
    login_view(){
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(panel);

        panel.setLayout(null);
        username = new JLabel("Username");
        username.setBounds(10, 20, 80, 25);
        panel.add(username);

        userText = new JTextField(20);
        userText.setBounds(100, 25, 165, 20);
        panel.add(userText);


        password = new JLabel("Password");
        password.setBounds(10, 50, 80, 25);
        panel.add(password);
        passText = new JPasswordField();
        passText.setBounds(100, 50, 165, 20);
        panel.add(passText);
        

        //button
        button = new JButton("Login");
        button.setBounds(10, 80, 80, 25);
        button.addActionListener(this);
        panel.add(button);

        success = new JLabel("");
        success.setBounds(10, 110, 300, 25);
        panel.add(success);

        //frame.pack();
        panel.revalidate();
        panel.repaint();
        frame.setVisible(true);
        
    }

    public void actionPerformed(ActionEvent event){

        //building sql connection 
        Connection conn = null;
        String teamNumber = "22";
        String sectionNumber = "913";
        String dbName = "csce315_" + sectionNumber + "_" + teamNumber ;
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup();
        
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
         } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
         }
    

        String user = userText.getText();
        boolean id_flag = false; // false/odd is manager and true/even is server
        String temp_user = user; 
        try {  
            if(Integer.parseInt(temp_user) % 2 == 0){
                id_flag = true;
            }
          } catch(NumberFormatException e){  
          }  
        if(!isNumeric(user)){
            success.setText("Username entered is incorrect");
            return;
        }
        String password = passText.getText();
        try{
            // while(true){
                Statement stmt = conn.createStatement();
                String sqlStatement = "SELECT password FROM manager WHERE manager_id = " + user;
                if(id_flag){
                    sqlStatement = "SELECT password FROM server WHERE server_id = " + user;
                }
                ResultSet result = stmt.executeQuery(sqlStatement);
                if(!result.next()){
                    success.setText("Username entered is incorrect");
                    return;
                }
                String pass = result.getString("password");
        
                // if(pass.length() == 0){
                //     success.setText("Username entered is incorrect");
                // }
                
                if(!password.equals(pass)){
                    success.setText("Password entered is incorrect");
                }
                else{
                    success.setText("Successfully logged in");
                }
            //}
        }
        catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    public static boolean isNumeric(String str) { 
        try {  
          Integer.parseInt(str);
          return true;
        } catch(NumberFormatException e){  
          return false;  
        }  
      }
      public static void main(String[] args){
        new login_view();
      }
}