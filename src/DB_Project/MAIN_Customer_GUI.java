package DB_Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MAIN_Customer_GUI extends JFrame{
    JButton Select_B, BILLS_B, Booking_B, Complain_B, LOGOUT_B;
    JLabel Menu_L,Company_L;

    public int C_ID;

    public MAIN_Customer_GUI(int C_id){
        this.C_ID=C_id;
        JPanel Menu_P = new JPanel() {@Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            ImageIcon img = new ImageIcon("background.png");
            g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        }};
        setSize(700, 750);
        setTitle("TOURISM NETWORK");
        add(Menu_P);
        Menu_P.setLayout(null);
        Menu_P.setBackground(new Color(153,153,153));

        Company_L = new JLabel("TOURISM NETWORK");
        Company_L.setFont(new Font("Serif", Font.BOLD,35));
        Company_L.setBounds(160,10,400,35);
        Company_L.setForeground(new Color(51,51,51));
        Menu_P.add(Company_L);

        Menu_L = new JLabel("CUSTOMER MENU");
        Menu_L.setFont(new Font("Serif", Font.BOLD,30));
        Menu_L.setBounds(240,60,300,30);
        Menu_L.setForeground(new Color(51,51,51));
        Menu_P.add(Menu_L);

        Select_B = new JButton("SELECT PACKAGE");
        Select_B.setBounds(140,160,420,80);
        Select_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(Select_B);

        BILLS_B = new JButton("BILLS (PAY/PAID)");
        BILLS_B.setBounds(140,280,420,80);
        BILLS_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(BILLS_B);

        Booking_B = new JButton("BOOKING HISTORY");
        Booking_B.setBounds(140,400,420,80);
        Booking_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(Booking_B);

        Complain_B = new JButton("COMPLAIN");
        Complain_B.setBounds(140,520,420,80);
        Complain_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(Complain_B);

        LOGOUT_B = new JButton("LOGOUT");
        LOGOUT_B.setBounds(140,640,120,25);
        LOGOUT_B.setBackground(Color.BLUE);
        LOGOUT_B.setForeground(Color.white);
        LOGOUT_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(LOGOUT_B);
        setVisible(true);

        MAIN_Customer_GUI.MyListener listener = new MyListener();
        Select_B.addActionListener(listener);
        BILLS_B.addActionListener(listener);
        Booking_B.addActionListener(listener);
        Complain_B.addActionListener(listener);
        LOGOUT_B.addActionListener(listener);
    }

    public class MyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("SELECT PACKAGE")) {
                if (Already_Selected(C_ID)){
                    JOptionPane.showMessageDialog(new JFrame(), "You Already Selected a package\nYou cannot select another package until previous package is confirmed by the admin");
                } else if (!Packages_Available()){
                    System.out.println(Packages_Available());
                    JOptionPane.showMessageDialog(new JFrame(), "No Packages To Select from");
                } else{
                    dispose();
                    new SELECT_PACKAGE_Customer_GUI(C_ID);
                }

            } else if (e.getActionCommand().equalsIgnoreCase("BILLS (PAY/PAID)")) {
                if (!Bills_Available()){JOptionPane.showMessageDialog(new JFrame(), "Data Available");}
                else {
                    dispose();
                    new PAY_CHARGES_Customer_GUI(C_ID);
                }
            } else if (e.getActionCommand().equalsIgnoreCase("BOOKING HISTORY")){
                if (!History_Available()){JOptionPane.showMessageDialog(new JFrame(), "No History Available");}
                else{
                    dispose();
                    new BOOKING_HISTORY_Customer_GUI(C_ID);
                }
            } else if (e.getActionCommand().equalsIgnoreCase("COMPLAIN")){
                dispose();
                new MAIN_COMPLAIN_Customer_GUI(C_ID);
            } else if (e.getActionCommand().equalsIgnoreCase("LOGOUT")) {
                dispose();
                new LOGIN_Customer_GUI();
            }
        }
    }
    public boolean Already_Selected(int C_ID){
        try{
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
            PreparedStatement st = con.prepareStatement("select BOOKING_C_ID FROM Confirmation WHERE  BOOKING_C_STATUS = 'N' and CUSTOMER_C_ID= ?");
            st.setInt(1, C_ID);
            ResultSet r1=st.executeQuery();
            if (r1.next()) {
                System.out.println("COUNT"+ r1.getInt(1));
                return true;
            }
            con.close();
        } catch (Exception ea){
            ea.printStackTrace();
        }
        return false;
    }

    public boolean Packages_Available(){
        try{
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
            PreparedStatement st = con.prepareStatement("Select * FROM Package WHERE  PACKAGE_VISIBILITY='Y'");
            ResultSet r1=st.executeQuery();
            if (r1.next()) {
                return true;
            }
            con.close();
        } catch (Exception ea){
            ea.printStackTrace();
        }
        return false;
    }

    public boolean Bills_Available(){
        try{
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
            PreparedStatement st = con.prepareStatement("Select * FROM Charges WHERE CUSTOMER_C_ID=?");
            st.setInt(1,C_ID);
            ResultSet r1=st.executeQuery();
            if (r1.next()) {
                return true;
            }
            con.close();
        } catch (Exception ea){
            ea.printStackTrace();
        }
        return false;
    }

    public boolean History_Available(){
        try{
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
            PreparedStatement st = con.prepareStatement("Select * FROM Booking_History WHERE CUSTOMER_C_ID=?");
            st.setInt(1,C_ID);
            ResultSet r1=st.executeQuery();
            if (r1.next()) {
                return true;
            }
            con.close();
        } catch (Exception ea){
            ea.printStackTrace();
        }
        return false;
    }



}


