package DB_Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MAIN_Admin_GUI extends JFrame {
    JButton Package_B, Organizer_B, Booking_B,Complain_B,Bills_B , BACK_B;
    JLabel Menu_L,Company_L;

    public MAIN_Admin_GUI(){



        JPanel Menu_P = new JPanel()
        {@Override
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

        Menu_L = new JLabel("ADMIN MENU");
        Menu_L.setFont(new Font("Serif", Font.BOLD,30));
        Menu_L.setBounds(250,60,300,30);
        Menu_L.setForeground(new Color(51,51,51));
        Menu_P.add(Menu_L);

        Package_B = new JButton("MANAGE PACKAGE");
        Package_B.setBounds(140,160,420,80);
        Package_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(Package_B);

        Organizer_B = new JButton("MANAGE ORGANIZER");
        Organizer_B.setBounds(140,260,420,80);
        Organizer_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(Organizer_B);

        Booking_B = new JButton("MANAGE BOOKINGS");
        Booking_B.setBounds(140,360,420,80);
        Booking_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(Booking_B);

        Bills_B = new JButton("VIEW BILLS (Paid/Unpaid)");
        Bills_B.setBounds(140,460,420,80);
        Bills_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(Bills_B);

        Complain_B = new JButton("MANAGE COMPLAINS");
        Complain_B.setBounds(140,560,420,80);
        Complain_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(Complain_B);



        BACK_B = new JButton("BACK");
        BACK_B.setBounds(140,660,100,25);
        BACK_B.setBackground(Color.BLUE);
        BACK_B.setForeground(Color.white);
        BACK_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(BACK_B);

        setVisible(true);

        MAIN_Admin_GUI.MyListener listener = new MyListener();
        Package_B.addActionListener(listener);
        Organizer_B.addActionListener(listener);
        Booking_B.addActionListener(listener);
        Bills_B.addActionListener(listener);
        Complain_B.addActionListener(listener);
        BACK_B.addActionListener(listener);
    }

    public class MyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("MANAGE PACKAGE")) {
                dispose();
                new MAIN_Package_GUI();
            } else if (e.getActionCommand().equalsIgnoreCase("MANAGE ORGANIZER")) {
                dispose();
                new MAIN_Organizer_GUI();
            } else if (e.getActionCommand().equalsIgnoreCase("MANAGE BOOKINGS")){
                if (Bookings_Available()){
                    dispose();
                    new MAIN_Bookings_GUI();
                }else{
                    JOptionPane.showMessageDialog(null,"No Request to View");
                }
            }  else if (e.getActionCommand().equalsIgnoreCase("VIEW BILLS (Paid/Unpaid)")){
                if (Bills_Available()) {
                    dispose();
                    new VIEW_PAID_Admin_GUI();
                }else{
                    JOptionPane.showMessageDialog(null,"No Bills to View");
                }
            }else if (e.getActionCommand().equalsIgnoreCase("MANAGE COMPLAINS")){
                if (Response_Required()) {
                    dispose();
                    new COMPLAIN_RESONSE_Admin_GUI();
                }else{
                    JOptionPane.showMessageDialog(null,"No Complaint to Respond");
                }
            } else if (e.getActionCommand().equalsIgnoreCase("BACK")) {
                dispose();
                new Tourism_Managment_System();
            }
        }
    }
    public boolean Bookings_Available(){
        try{
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
            PreparedStatement st = con.prepareStatement("Select * FROM CONFIRMATION WHERE BOOKING_C_STATUS = 'N'");
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
            PreparedStatement st = con.prepareStatement("Select * FROM Charges");
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



    public boolean Response_Required(){
        try{
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
            PreparedStatement st = con.prepareStatement("Select * FROM COMPLAIN Where RESPONSE_STATUS='N'");
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
