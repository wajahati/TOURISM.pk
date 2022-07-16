package DB_Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tourism_Managment_System extends JFrame {

    JButton Admin_B, Organizer_B,Customer_B, Help_B ,Exit_B;
    JLabel Menu_L,Company_L;

    public Tourism_Managment_System (){



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

        Menu_L = new JLabel("MAIN MENU");
        Menu_L.setFont(new Font("Serif", Font.BOLD,30));
        Menu_L.setBounds(250,60,300,30);
        Menu_L.setForeground(new Color(51,51,51));
        Menu_P.add(Menu_L);


        Admin_B = new JButton("ADMIN");
        Admin_B.setBounds(140,160,200,80);
        Admin_B.setBackground(Color.darkGray);
        Admin_B.setForeground(Color.white);
        Admin_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(Admin_B);

        Customer_B = new JButton("CUSTOMER");
        Customer_B.setBounds(360,160,200,80);
        Customer_B.setBackground(Color.darkGray);
        Customer_B.setForeground(Color.white);
        Customer_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(Customer_B);

        Organizer_B = new JButton("VIEW PACKAGES");
        Organizer_B.setBounds(140,280,420,80);
        Organizer_B.setBackground(Color.darkGray);
        Organizer_B.setForeground(Color.white);
        Organizer_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(Organizer_B);


        Help_B = new JButton("HELP");
        Help_B.setBounds(460,400,100,25);
        Help_B.setBackground(Color.BLUE);
        Help_B.setForeground(Color.white);
        Help_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(Help_B);

        Exit_B = new JButton("EXIT");
        Exit_B.setBounds(140,400,100,25);
        Exit_B.setBackground(Color.BLUE);
        Exit_B.setForeground(Color.white);
        Exit_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(Exit_B);

        setVisible(true);

        MyListener listener = new MyListener();
        Admin_B.addActionListener(listener);
        Organizer_B.addActionListener(listener);
        Customer_B.addActionListener(listener);
        Help_B.addActionListener(listener);
        Exit_B.addActionListener(listener);
    }

    public class MyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("ADMIN")) {
                dispose();
                new LOGIN_Admin_GUI();
            } else if (e.getActionCommand().equalsIgnoreCase("VIEW PACKAGES")) {
                dispose();
                new MAIN_VIEW_Package_GUI();
            } else if (e.getActionCommand().equalsIgnoreCase("CUSTOMER")){
                dispose();
                new LOGIN_Customer_GUI();
            } else if (e.getActionCommand().equalsIgnoreCase("HELP")){
                JOptionPane.showMessageDialog(null, "For Any Query Contact us at : \n "+"111-222-333"+"\n or vist out main office at \nOffice No : "+"12"+"\nPlaza No :  "+"3"+", "+"Bahria Phase-4");
            } else {
                dispose();
            }
        }
    }
}
