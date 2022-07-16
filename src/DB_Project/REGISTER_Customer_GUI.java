package DB_Project;

import javax.management.StringValueExp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class REGISTER_Customer_GUI extends JFrame {
    JButton Submit_B, Back_B;
    JLabel REG_Customer,Fname_L, Lname_L,Email_L,USERNAME_L,PASSWORD_L,CON_PASSWORD_L,PhoneNo_L;
    JTextField Fname_T, Lname_T,Email_T,USERNAME_T, PhoneNo_T;
    JPasswordField  PASSWORD_T,CON_PASSWORD_T;

    public REGISTER_Customer_GUI(){
        JPanel ADD_USER_P = new JPanel() {@Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            ImageIcon img = new ImageIcon("background.png");
            g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        }};
        setSize(700, 750);
        setTitle("Tourism Network");
        add(ADD_USER_P);
        ADD_USER_P.setLayout(null);
        ADD_USER_P.setBackground(new Color(153,153,153));

        REG_Customer = new JLabel("ADD USER");
        REG_Customer.setFont(new Font("Serif", Font.BOLD,25));
        REG_Customer.setBounds(160,50,500,35);
        REG_Customer.setForeground(new Color(51,51,51));
        ADD_USER_P.add(REG_Customer);

        Fname_L = new JLabel("First Name :");
        Fname_L.setFont(new Font("Serif", Font.BOLD,15));
        Fname_L.setBounds(150,130,120,30);
        ADD_USER_P.add(Fname_L);

        Fname_T = new JTextField();
        Fname_T.setBounds(300,130,200,30);
        ADD_USER_P.add(Fname_T);

        Lname_L = new JLabel("Last Name :");
        Lname_L.setFont(new Font("Serif", Font.BOLD,15));
        Lname_L.setBounds(150,180,120,30);
        ADD_USER_P.add(Lname_L);

        Lname_T = new JTextField(20);
        Lname_T.setBounds(300,180,200,30);
        ADD_USER_P.add(Lname_T);

        USERNAME_L = new JLabel("Username :");
        USERNAME_L.setFont(new Font("Serif", Font.BOLD,15));
        USERNAME_L.setBounds(150,230,120,30);
        ADD_USER_P.add(USERNAME_L);

        USERNAME_T = new JTextField(20);
        USERNAME_T.setBounds(300,230,200,30);
        ADD_USER_P.add(USERNAME_T);

        PASSWORD_L = new JLabel("Password :");
        PASSWORD_L.setFont(new Font("Serif", Font.BOLD,15));
        PASSWORD_L.setBounds(150,280,120,30);
        ADD_USER_P.add(PASSWORD_L);

        PASSWORD_T = new JPasswordField(20);
        PASSWORD_T.setBounds(300,280,200,30);
        ADD_USER_P.add(PASSWORD_T);

        CON_PASSWORD_L = new JLabel("Confirm Password :");
        CON_PASSWORD_L.setFont(new Font("Serif", Font.BOLD,15));
        CON_PASSWORD_L.setBounds(150,330,150,30);
        ADD_USER_P.add(CON_PASSWORD_L);

        CON_PASSWORD_T = new JPasswordField(20);
        CON_PASSWORD_T.setBounds(300,330,200,30);
        ADD_USER_P.add(CON_PASSWORD_T);

        Email_L = new JLabel("Email :");
        Email_L.setFont(new Font("Serif", Font.BOLD,15));
        Email_L .setBounds(150,380,120,30);
        ADD_USER_P.add(Email_L );

        Email_T = new JTextField(20);
        Email_T.setBounds(300,380,200,30);
        ADD_USER_P.add(Email_T);

        PhoneNo_L = new JLabel("Phone No :");
        PhoneNo_L.setFont(new Font("Serif", Font.BOLD,15));
        PhoneNo_L.setBounds(150,430,120,30);
        ADD_USER_P.add(PhoneNo_L);

        PhoneNo_T = new JTextField(20);
        PhoneNo_T.setBounds(300,430,200,30);
        ADD_USER_P.add(PhoneNo_T);



        Back_B = new JButton("BACK");
        Back_B.setBounds(100,490,80,25);
        Back_B.setBackground(Color.BLUE);
        Back_B.setForeground(Color.white);
        ADD_USER_P.add(Back_B);

        Submit_B = new JButton("SUBMIT");
        Submit_B.setBounds(280,490,80,25);

        Submit_B.setBackground(Color.BLUE);
        Submit_B.setForeground(Color.white);
        ADD_USER_P.add(Submit_B);

        setVisible(true);
        REGISTER_Customer_GUI.MyListener listener = new MyListener();
        Submit_B.addActionListener(listener);
        Back_B.addActionListener(listener);

    }
    public class MyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("BACK")) {
                dispose();
                new LOGIN_Customer_GUI();
            }
            else if(!IsNotEmpty()){
                JOptionPane.showMessageDialog(null, "WARNING! Textfields left empty");
            }
            else if(!Correct_Password()){
                JOptionPane.showMessageDialog(null, "WARNING! Password and confirm password does not match ");
            }
            else if (!isValid(Email_T.getText())){
                JOptionPane.showMessageDialog(null, "WARNING! Email format incorrect");
            }
            else if (e.getActionCommand().equalsIgnoreCase("SUBMIT")){

                try {
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
                    PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO \"PROJECT\".\"CUSTOMER\" (C_USERNAME, C_PASWORD, C_FIRSTNAME, C_LASTNAME, C_EMAIL) VALUES ( ?, ?, ?, ?, ?)\n");

                    String s1 = USERNAME_T.getText();
                    String s2 = PASSWORD_T.getText();
                    String s3 = Fname_T.getText();
                    String s4 = Lname_T.getText();
                    String s5 = Email_T.getText();
                    String s6 = PhoneNo_T.getText();

                    preparedStatement.setString(1, s1);
                    preparedStatement.setString(2, s2);
                    preparedStatement.setString(3, s3);
                    preparedStatement.setString(4, s4);
                    preparedStatement.setString(5, s5);

                    if(Exist_UserName(s1,con)){
                        JOptionPane.showMessageDialog(null,"ERROR !\nUsername Already Exists already exists");
                    } else{
                        preparedStatement.executeQuery();
                        ADD_ContactNo(s1, s6, con);
                        JOptionPane.showMessageDialog(new JFrame(), "Registered Successfully");
                        dispose();
                        con.close();
                        new LOGIN_Customer_GUI();
                    }
                } catch (Exception ae) {
                    System.out.println(ae.toString());
                }
            }
        }

    }
    public boolean Exist_UserName(String UserName, Connection connection) throws SQLException {
        PreparedStatement st = connection.prepareStatement("select C_ID from Customer where C_USERNAME = ?");
        st.setString(1, UserName);
        ResultSet r1=st.executeQuery();
        if(r1.next()) {
             return true;
        }
        return false;
    }

    public void ADD_ContactNo(String UserName,String PhoneNo ,Connection connection) throws SQLException {
        PreparedStatement st = connection.prepareStatement("select C_ID from Customer where C_USERNAME = ?");
        st.setString(1, UserName);
        ResultSet r1=st.executeQuery();
        int C_ID=0;
        if(r1.next()) {
            C_ID = r1.getInt(1);
        }


        PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO \"PROJECT\".\"CONTACTS\" (CONTACT_NUMBER, CUSTOMER_C_ID) VALUES ( ?,? )\n\n");
        preparedStatement1.setString(1, PhoneNo);
        preparedStatement1.setString(2, String.valueOf(C_ID));
        preparedStatement1.executeQuery();
    }

    private boolean IsNotEmpty(){
        if (Fname_T.getText().isEmpty() || Email_T.getText().isEmpty()|| USERNAME_T.getText().isEmpty()|| PASSWORD_T.getText().isEmpty()|| CON_PASSWORD_T.getText().isEmpty() || PhoneNo_T.getText().isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }private boolean Correct_Password(){
        if ((PASSWORD_T.getText()).equals(CON_PASSWORD_T.getText())){
            return true;
        }
        else{
            return false;
        }
    }static boolean isValid(String email) {
        String regex = "^[\\w-\\.+]*[\\w-\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}

