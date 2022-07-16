package DB_Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ADD_ORGANIZER_Admin_GUI extends JFrame{
    JButton Submit_B, Back_B;
    JLabel REG_Customer,Fname_L, Lname_L,Email_L,PhoneNo_L;
    JTextField Fname_T, Lname_T,Email_T, PhoneNo_T;

    public ADD_ORGANIZER_Admin_GUI(){
        JPanel ADD_USER_P = new JPanel()
        {@Override
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

        REG_Customer = new JLabel("ADD ORGANIZER");
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

        Email_L = new JLabel("Email :");
        Email_L.setFont(new Font("Serif", Font.BOLD,15));
        Email_L .setBounds(150,230,120,30);
        ADD_USER_P.add(Email_L );

        Email_T = new JTextField(20);
        Email_T.setBounds(300,230,200,30);
        ADD_USER_P.add(Email_T);

        PhoneNo_L = new JLabel("Phone No :");
        PhoneNo_L.setFont(new Font("Serif", Font.BOLD,15));
        PhoneNo_L.setBounds(150,280,120,30);
        ADD_USER_P.add(PhoneNo_L);

        PhoneNo_T = new JTextField(20);
        PhoneNo_T.setBounds(300,280,200,30);
        ADD_USER_P.add(PhoneNo_T);



        Back_B = new JButton("BACK");
        Back_B.setBounds(100,340,80,25);
        Back_B.setBackground(Color.BLUE);
        Back_B.setForeground(Color.white);
        ADD_USER_P.add(Back_B);

        Submit_B = new JButton("SUBMIT");
        Submit_B.setBounds(280,340,80,25);

        Submit_B.setBackground(Color.BLUE);
        Submit_B.setForeground(Color.white);
        ADD_USER_P.add(Submit_B);

        setVisible(true);
        ADD_ORGANIZER_Admin_GUI.MyListener listener = new MyListener();
        Submit_B.addActionListener(listener);
        Back_B.addActionListener(listener);

    }
    public class MyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().equalsIgnoreCase("BACK")) {
                dispose();
                new MAIN_Organizer_GUI();
            }else if(!IsNotEmpty()){
                JOptionPane.showMessageDialog(null, "WARNING! Data(Textfields) left empty");
            }
            else if (!isValid(Email_T.getText())){
                JOptionPane.showMessageDialog(null, "WARNING! Email format incorrect");
            }
            else if(e.getActionCommand().equalsIgnoreCase("SUBMIT")){

                try {
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
                    PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO \"PROJECT\".\"ORGANIZER\" (O_FIRSTNAME, O_LASTNAME, O_TRIPS_ORGANIZED, O_EMAIL, ADMIN_USERNAME, O_PHONENO) VALUES (?, ?, '0', ?, 'Admin', ?)\n");
                    PreparedStatement preparedStatement1 = con.prepareStatement("INSERT INTO \"PROJECT\".\"SALARY\" (AMOUNT, ORGANIZER_O_ID) VALUES ('10000', (SELECT O_ID FROM Organizer WHERE O_FIRSTNAME=? AND O_EMAIL=?))");
                    String s3 = Fname_T.getText();
                    String s4 = Lname_T.getText();
                    String s5 = Email_T.getText();
                    String s6 = PhoneNo_T.getText();

                    preparedStatement.setString(1, s3);
                    preparedStatement.setString(2, s4);
                    preparedStatement.setString(3, s5);
                    preparedStatement.setString(4, s6);

                    preparedStatement1.setString(1, s3);
                    preparedStatement1.setString(2, s5);



                    if(Exist_UserName(s3, s5,con)){
                        JOptionPane.showMessageDialog(null,"ERROR !\nOrganizer with this Name and Email Already exists");
                    } else{
                        preparedStatement.executeQuery();
                        preparedStatement1.executeQuery();
                        JOptionPane.showMessageDialog(new JFrame(), "Registered Successfully");
                        dispose();
                        con.close();
                        new ADD_ORGANIZER_Admin_GUI();
                    }
                } catch (Exception ae) {
                    System.out.println(ae.toString());
                    ae.printStackTrace();
                }
            }
        }

    }
    public boolean Exist_UserName(String UserName, String Email ,Connection connection) throws SQLException {
        PreparedStatement st = connection.prepareStatement("select O_ID from Organizer where O_FIRSTNAME = ? and  O_EMAIL = ? ");
        st.setString(1, UserName);
        st.setString(2, Email);
        ResultSet r1=st.executeQuery();
        if(r1.next()) {
            return true;
        }
        return false;
    }

    private boolean IsNotEmpty(){
        if (Fname_T.getText().isEmpty() || Lname_T.getText().isEmpty()|| Email_T.getText().isEmpty()|| PhoneNo_T.getText().isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }
    static boolean isValid(String email) {
        String regex = "^[\\w-\\.+]*[\\w-\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}
