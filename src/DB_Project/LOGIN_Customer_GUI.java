package DB_Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LOGIN_Customer_GUI extends JFrame{
    JButton LOGIN_B, Back_B, Register_B;
    JLabel LOGIN_CUSTOMER_L,Username_L,Password_L,Register_L;
    JTextField Username_T;
    JPasswordField Password_T;

    LOGIN_Customer_GUI(){
        JPanel LOGIN_USER_P = new JPanel() {@Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            ImageIcon img = new ImageIcon("background.png");
            g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        }};

        setSize(700, 750);
        setTitle("Tourism Network");
        add(LOGIN_USER_P);
        LOGIN_USER_P.setLayout(null);
        LOGIN_USER_P.setBackground(new Color(153,153,153));

        LOGIN_CUSTOMER_L = new JLabel("LOGIN CUSTOMER");
        LOGIN_CUSTOMER_L.setFont(new Font("Serif", Font.BOLD,30));
        LOGIN_CUSTOMER_L.setBounds(210,30,500,35);
        LOGIN_CUSTOMER_L.setForeground(new Color(51,51,51));
        LOGIN_USER_P.add(LOGIN_CUSTOMER_L);

        Username_L = new JLabel("Username : ");
        Username_L.setFont(new Font("Serif", Font.BOLD,15));
        Username_L.setBounds(150,150,120,30);
        LOGIN_USER_P.add(Username_L);

        Username_T = new JTextField();
        Username_T.setBounds(300,150,200,30);
        LOGIN_USER_P.add(Username_T);

        Password_L = new JLabel("Password : ");
        Password_L.setFont(new Font("Serif", Font.BOLD,15));
        Password_L.setBounds(150,200,120,30);
        LOGIN_USER_P.add(Password_L);

        Password_T = new JPasswordField();
        Password_T.setBounds(300,200,200,30);
        LOGIN_USER_P.add(Password_T);

        LOGIN_B = new JButton("LOGIN");
        LOGIN_B.setBounds(360,260,100,25);
        LOGIN_B.setBackground(Color.BLUE);
        LOGIN_B.setForeground(Color.white);
        LOGIN_B.setFont(new Font("Serif", Font.BOLD,15));
        LOGIN_USER_P.add(LOGIN_B);

        Back_B = new JButton("BACK");
        Back_B.setBounds(200,260,80,25);
        Back_B.setBackground(Color.BLUE);
        Back_B.setForeground(Color.white);
        Back_B.setFont(new Font("Serif", Font.BOLD,15));
        LOGIN_USER_P.add(Back_B);

        Register_L = new JLabel("Don't have an account?");
        Register_L.setFont(new Font("Serif", Font.BOLD,15));
        Register_L.setBounds(150,400,200,30);
        LOGIN_USER_P.add(Register_L);

        Register_B = new JButton("REGISTER");
        Register_B.setBounds(360,400,120,25);
        Register_B.setBackground(Color.BLUE);
        Register_B.setForeground(Color.white);
        Register_B.setFont(new Font("Serif", Font.BOLD,15));
        LOGIN_USER_P.add(Register_B);

        setVisible(true);
        LOGIN_Customer_GUI.MyActionListener listener = new MyActionListener();
        LOGIN_B.addActionListener(listener);
        Back_B.addActionListener(listener);
        Register_B.addActionListener(listener);

    }
    public class MyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {

             if (a.getActionCommand().equals("REGISTER")) {
                dispose();
                new REGISTER_Customer_GUI();
            }
            else if (a.getActionCommand().equals("BACK")) {
                dispose();
                new Tourism_Managment_System();
            }
            else if(!IsNotEmpty()){
                JOptionPane.showMessageDialog(null, "WARNING! Textfields left empty");
            }
            else if (a.getActionCommand().equals("LOGIN")) {
                try {
                    Connection C = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
                    PreparedStatement preparedStatement = C.prepareStatement("Select C_ID From Customer Where C_USERNAME = ? and C_PASWORD =?");

                    String s1 = Username_T.getText();
                    String s2 = Password_T.getText();
                    preparedStatement.setString(1, s1);
                    preparedStatement.setString(2, s2);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(null, "Login Successful ");
                        dispose();
                        int C_ID = resultSet.getInt(1);
                        new  MAIN_Customer_GUI(C_ID);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Username or Password Incorrect");
                    }
                    C.close();
                } catch (Exception e) {
                    System.out.println(e);
                    e.printStackTrace();
                }
            }

        }
    }
    private boolean IsNotEmpty(){

        if (Username_T.getText().isEmpty() || Password_T.getText().isEmpty() ){
            return false;
        }
        else{
            return true;
        }
    }
}

