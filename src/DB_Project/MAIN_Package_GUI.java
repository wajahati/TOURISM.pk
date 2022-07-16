package DB_Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MAIN_Package_GUI extends JFrame{
    JButton Create_B, View_B, Delete_B, BACK_B;
    JLabel Menu_L,Company_L;

    public MAIN_Package_GUI(){



        JPanel Menu_P = new JPanel()
        {@Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            ImageIcon img = new ImageIcon("background.png");
            g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        }}        ;


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

        Menu_L = new JLabel("PACKAGE MENU");
        Menu_L.setFont(new Font("Serif", Font.BOLD,30));
        Menu_L.setBounds(250,60,300,30);
        Menu_L.setForeground(new Color(51,51,51));
        Menu_P.add(Menu_L);

        Create_B = new JButton("CREATE PACKAGE");
        Create_B.setBounds(140,160,420,80);
        Create_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(Create_B);

        View_B = new JButton("VIEW PACKAGES");
        View_B.setBounds(140,280,420,80);
        View_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(View_B);

        Delete_B = new JButton("DELETE PACKAGE");
        Delete_B.setBounds(140,400,420,80);
        Delete_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(Delete_B);

        BACK_B = new JButton("BACK");
        BACK_B.setBounds(140,520,100,25);
        BACK_B.setBackground(Color.BLUE);
        BACK_B.setForeground(Color.white);
        BACK_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(BACK_B);

        setVisible(true);

        MAIN_Package_GUI.MyListener listener = new MyListener();
        Create_B.addActionListener(listener);
        View_B.addActionListener(listener);
        Delete_B.addActionListener(listener);
        BACK_B.addActionListener(listener);
    }

    public class MyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("CREATE PACKAGE")) {
                if (Organizer_Available()){
                    dispose();
                    new CREATE_PACKAGE_Admin_GUI();
                }
                else{
                    JOptionPane.showMessageDialog(null,"Add At least One Organizer before Creating Package");
                }
            } else if (e.getActionCommand().equalsIgnoreCase("VIEW PACKAGES")) {
                if (Packages_Available()) {
                    dispose();
                    new VIEW_Packages_GUI();
                }else{
                    JOptionPane.showMessageDialog(null,"No Package to view");
                }
            } else if (e.getActionCommand().equalsIgnoreCase("DELETE PACKAGE")){
                if (Packages_Available()) {
                    dispose();
                    new DELETE_PACKAGE_Admin_GUI();
                }else{
                    JOptionPane.showMessageDialog(null,"No Package to delete");
                }
            } else if (e.getActionCommand().equalsIgnoreCase("BACK")) {
                dispose();
                new MAIN_Admin_GUI();
            }
        }
    }
    public boolean Organizer_Available(){
        try{
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
            PreparedStatement st = con.prepareStatement("Select * FROM Organizer ");
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
}

