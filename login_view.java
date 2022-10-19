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

/**
 * login_view shows login page for the point of sale system  
 * @author Semion Dyadkin */
public class login_view implements ActionListener {
    private static JLabel username;
    private static JTextField userText;
    private static JLabel password;
    private static JPasswordField passText;
    private static JButton button;
    private static JLabel success;
    

    // GUI
    JPanel panel = new JPanel();
    JFrame frame = new JFrame();
    /** login_view() function is the constructor for class login_view
     *
     * @param N/A
     * @return N/A
     * @throws N/A
     * @see Frame general GUI stuff for login
     *
     */
    login_view(){
        // frame
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(panel);

        // panel
        panel.setLayout(null);
        username = new JLabel("Username");
        username.setBounds(10, 20, 80, 25);
        panel.add(username);

        // username field
        userText = new JTextField(20);
        userText.setBounds(100, 25, 165, 20);
        panel.add(userText);

        // password field
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

        panel.revalidate();
        panel.repaint();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setVisible(true);
        
    }
    /** actionPerformed(ActionEvent event) function provides functionallity to the button pressed which is login
     *  
     * @param actionEvent event -- 'this' is use case
     * @return void function
     * @throws catch exception if database connection fails
     * @see new panel when entered correctly or incorrect username/password
     *
     */
    // When login is clicked, this function will check the username field first
    // if its in database. If its not, it will tell you that username is wrong, if you entered
    // an incorrect password to an exisiting username, you will get username is wrong.
    // If a server logged in, they will see only what a server should see: order entries.
    // If its a manager that's logged, he will see only what a manager should see, manager view.
    public void actionPerformed(ActionEvent event){

        // building sql connection 
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
        // false is manager and true is server
        boolean id_flag = false; 
        String temp_user = user; 
        try {  
            // Manager is odd numbers, server is even numbers
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
        
            if(!password.equals(pass)){
                success.setText("Password entered is incorrect");
            }
            else{
                success.setText("Successfully logged in");
                // server logs in
                if(id_flag == true){
                    new Demo();
                    frame.dispose();
                }
                // Manager logs in
                else{
                    new manager_view();
                    frame.dispose();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    /** isNumeric() function checks if a string is a numerical value (int) 
     *
     * @param String str -- the String to be checked
     * @return boolean  
     * @throws catch N/A
     * @see N/A
     *
     */
    public static boolean isNumeric(String str) { 
        try {  
          Integer.parseInt(str);
          return true;
        } catch(NumberFormatException e){  
          return false;  
        }  
      }
          /** main() function instantiates the login_view
     *
     * @param N/A
     * @return N/A
     * @throws N/A
     * @see login_view GUI
     *
     */
      public static void main(String[] args){
        new login_view();
      }
}