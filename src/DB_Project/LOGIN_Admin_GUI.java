package DB_Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LOGIN_Admin_GUI extends JFrame {
    JButton LOGIN_B, Back_B;
    JLabel LOGIN_ADMIN_L,Username_L,Password_L;
    JTextField Username_T;
    JPasswordField Password_T;

    LOGIN_Admin_GUI(){
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

        LOGIN_ADMIN_L = new JLabel("LOGIN ADMIN");
        LOGIN_ADMIN_L.setFont(new Font("Serif", Font.BOLD,30));
        LOGIN_ADMIN_L.setBounds(250,30,500,35);
        LOGIN_ADMIN_L.setForeground(new Color(51,51,51));
        LOGIN_USER_P.add(LOGIN_ADMIN_L);

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

        setVisible(true);
        LOGIN_Admin_GUI.MyActionListener listener = new MyActionListener();
        LOGIN_B.addActionListener(listener);
        Back_B.addActionListener(listener);

    }
    public class MyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            if (a.getActionCommand().equals("BACK")) {
                dispose();
                new Tourism_Managment_System();
            }
            else if(!IsNotEmpty()){
                JOptionPane.showMessageDialog(null, "WARNING! Textfields left empty");
            }  else if (a.getActionCommand().equals("LOGIN")) {
                try {
                    Connection C = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
                    PreparedStatement preparedStatement = C.prepareStatement("Select * From Admin Where USERNAME = ? and PASSWORD =?");
                    String s1 = Username_T.getText();
                    String s2 = Password_T.getText();

                    preparedStatement.setString(1, s1);
                    preparedStatement.setString(2, s2);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(null, "Logged-in Successfully \npress OK to continue");
                        dispose();
                        new MAIN_Admin_GUI();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Username or Password");
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

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    static boolean isValid(String email) {
        String regex = "^[\\w-\\.+]*[\\w-\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

}

