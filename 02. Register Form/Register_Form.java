import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register_Form {
    private JPanel pnlBase;
    private JLabel lblFirstName;
    private JLabel lblLastName;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JLabel lblEmail;
    private JLabel lblClass;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JTextField txtEmail;
    private JTextField txtClass;
    private JButton btnSignUp;
    private JLabel lbl_className;
    private JLabel lbl_firstName;
    private JLabel lbl_lastName;
    private JLabel lbl_username;
    private JLabel lbl_password;
    private JLabel lbl_email;
    private JLabel lblInfoFirstName;
    private JLabel lblInfoLastName;
    private JLabel lblInfoClass;
    private String patternName = "[А-Я][а-я]+([- \\s][А-Я][а-я]+)?";

    public Register_Form() {
        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Sign up person

                //Correct first name
                if(isValidFirstName() && !txtFirstName.getText().isEmpty()){
                    txtFirstName.setBackground(Color.WHITE);
                    lbl_firstName.setText("OK");
                    lbl_firstName.setForeground(Color.GREEN);
                }else{
                    lbl_firstName.setText("");
                    txtFirstName.setBackground(Color.RED);
                }
                //Correct last name
                if(isValidLastName() && !txtLastName.getText().isEmpty()){
                    txtLastName.setBackground(Color.WHITE);
                    lbl_lastName.setText("OK");
                    lbl_lastName.setForeground(Color.GREEN);
                }else{
                    lbl_lastName.setText("");
                    txtLastName.setBackground(Color.RED);
                }
                //Correct username
                if(isValidUsername() && !txtUsername.getText().isEmpty()){
                    txtUsername.setBackground(Color.WHITE);
                    lbl_username.setText("OK");
                    lbl_username.setForeground(Color.GREEN);
                }else{
                    lbl_username.setText("");
                    txtUsername.setBackground(Color.RED);
                }
                //Correct password
                if(isValidPassword() && !txtPassword.getText().isEmpty()){
                    txtPassword.setBackground(Color.WHITE);
                    lbl_password.setText("OK");
                    lbl_password.setForeground(Color.GREEN);
                }else{
                    lbl_password.setText("");
                    txtPassword.setBackground(Color.RED);
                }
                //Correct email
                if(isValidEmail() && !txtEmail.getText().isEmpty()){
                    txtEmail.setBackground(Color.WHITE);
                    lbl_email.setText("OK");
                    lbl_email.setForeground(Color.GREEN);
                }else{
                    lbl_email.setText("");
                    txtEmail.setBackground(Color.RED);
                }
                //Correct class name
                if(isValidClass() && !txtClass.getText().isEmpty()){
                    txtClass.setBackground(Color.WHITE);
                    lbl_className.setText("OK");
                    lbl_className.setForeground(Color.GREEN);
                }else{
                    lbl_className.setText("");
                    txtClass.setBackground(Color.RED);
                }

                if(isValidFirstName() && isValidLastName() && isValidUsername() && isValidPassword() && isValidEmail() && isValidClass()){
                    String fullName = txtFirstName.getText() + " " + txtLastName.getText();
                    String username = txtUsername.getText();
                    String email = txtEmail.getText();
                    String schoolClass = txtClass.getText();

                    String message = String.format("Please, press 'yes' if the information is correct. \n Your name: %s \n " +
                            "Username: %s \n Email: %s \n Class: %s", fullName, username, email, schoolClass);

                    int confPane = JOptionPane.showConfirmDialog(null, message);
                    System.out.println(confPane);
                }
            }
        });
    }

    private boolean isValidFirstName(){
        return txtFirstName.getText().matches(patternName);
    }

    private boolean isValidLastName(){
        return txtLastName.getText().matches(patternName);
    }

    private boolean isValidUsername(){ return txtUsername.getText().matches("([a-z0-9_.]+)*"); }

    private boolean isValidPassword(){ return txtPassword.getText().matches("([a-zA-Z]+([A-Z0-9<\\(\\[\\{\\\\^\\-=$!|\\]\\}@\\)?*+.\">]+)?).{8,}"); }

    private boolean isValidEmail(){ return txtEmail.getText().matches("[a-z]+[a-zA-z0-9._*\\-]+[@][a-z]+[.][a-z]+"); }

    private boolean isValidClass(){ return txtClass.getText().matches("[56789](\\s)?[А-И]|([1][012])(\\s)?[А-И]$"); }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Register Form");
        frame.setContentPane(new Register_Form().pnlBase);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 370));
        frame.pack();
        frame.setVisible(true);
    }
}


