/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pblproject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PBLProject extends JFrame implements ActionListener {
    JLabel usernameLabel, passwordLabel, messageLabel;
    JTextField usernameTextField;
    JPasswordField passwordField;
    JButton loginButton, signupButton;
    JPanel panel;

    public PBLProject() {
     
        connect_db();
    }
    Connection con;
    
    public void connect_db(){
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost/NoteNexus", "root", "");
        System.out.println("Database connection successful");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    

        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        messageLabel = new JLabel();
        usernameTextField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        signupButton = new JButton("Signup");
        panel = new JPanel(new GridLayout(3, 2));

        panel.add(usernameLabel);
        panel.add(usernameTextField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(signupButton);

        loginButton.addActionListener(this);
        signupButton.addActionListener(this);

        add(panel, BorderLayout.CENTER);
        add(messageLabel, BorderLayout.SOUTH);

        setTitle("Login/Signup Page");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameTextField.getText();
            String password = new String(passwordField.getPassword());
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/NoteNexus", "root", "");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username='" + username + "' AND password='" + password + "'");
                if (rs.next()) {
                    messageLabel.setText("Login successful!");
                } else {
                    messageLabel.setText("Invalid username or password.");
                }
                con.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (e.getSource() == signupButton) {
            String username = usernameTextField.getText();
            String password = new String(passwordField.getPassword());
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/NoteNexus", "root", "");
                PreparedStatement stmt = con.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
                stmt.setString(1, username);
                stmt.setString(2, password);
                int i = stmt.executeUpdate();
                if (i > 0) {
                    messageLabel.setText("Signup successful!");
                } else {
                    messageLabel.setText("Signup failed.");
                }
                con.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    public static void main(String[] args) {
        new PBLProject();
    }
}

