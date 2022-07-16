package DB_Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MAIN_Organizer_GUI extends JFrame{
    JButton Add_B, View_B, Update_B, Delete_B, BACK_B;
    JLabel Menu_L,Company_L;

    public MAIN_Organizer_GUI(){



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

        Menu_L = new JLabel("ORGANIZER MENU");
        Menu_L.setFont(new Font("Serif", Font.BOLD,30));
        Menu_L.setBounds(200,60,300,30);
        Menu_L.setForeground(new Color(51,51,51));
        Menu_P.add(Menu_L);

        Add_B = new JButton("ADD ORGANIZER");
        Add_B.setBounds(140,160,420,80);
        Add_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(Add_B);

        View_B = new JButton("VIEW ORGANIZERS");
        View_B.setBounds(140,280,420,80);
        View_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(View_B);

        Delete_B = new JButton("DELETE ORGANIZER");
        Delete_B.setBounds(140,400,420,80);
        Delete_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(Delete_B);

        BACK_B = new JButton("BACK");
        BACK_B.setBounds(140,640,100,25);
        BACK_B.setBackground(Color.BLUE);
        BACK_B.setForeground(Color.white);
        BACK_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(BACK_B);

        setVisible(true);

        MAIN_Organizer_GUI.MyListener listener = new MyListener();
        Add_B.addActionListener(listener);
        View_B.addActionListener(listener);
        Delete_B.addActionListener(listener);

        BACK_B.addActionListener(listener);
    }

    public class MyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("ADD ORGANIZER")) {
                dispose();
                new ADD_ORGANIZER_Admin_GUI();
            } else if (e.getActionCommand().equalsIgnoreCase("VIEW ORGANIZERS")) {
                dispose();
                new VIEW_Organizers_GUI();
            } else if (e.getActionCommand().equalsIgnoreCase("DELETE ORGANIZER")){
                dispose();
                new DELETE_ORGANIZER_Admin_GUI();
            } else if (e.getActionCommand().equalsIgnoreCase("BACK")) {
                dispose();
                new MAIN_Admin_GUI();
            }
        }
    }
}
