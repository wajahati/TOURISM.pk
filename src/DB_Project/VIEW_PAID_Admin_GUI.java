package DB_Project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VIEW_PAID_Admin_GUI extends JFrame {


    JButton PAID_B,UNPAID_B,ALL_B, Back_B;
    JLabel MANAGE_BILL_L, BILL_L, OPTION_L;
    JComboBox<String> CHARGES_CB;


    JTable table_BILL;

    public VIEW_PAID_Admin_GUI(){
        JPanel ADD_EMP_P = new JPanel() {@Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            ImageIcon img = new ImageIcon("background.png");
            g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        }};
        setSize(700, 750);
        setTitle("Tourism Network");
        add(ADD_EMP_P);
        ADD_EMP_P.setLayout(null);
        ADD_EMP_P.setBackground(new Color(153,153,153));

        MANAGE_BILL_L = new JLabel("VIEW Bills");
        MANAGE_BILL_L.setFont(new Font("Serif", Font.BOLD,25));
        MANAGE_BILL_L.setBounds(170,20,500,35);
        MANAGE_BILL_L.setForeground(new Color(51,51,51));
        ADD_EMP_P.add(MANAGE_BILL_L);

        BILL_L = new JLabel("BILLS HISTORY");
        BILL_L.setFont(new Font("Serif", Font.BOLD,15));
        BILL_L.setBounds(20,100,400,25);
        BILL_L.setForeground(new Color(51,51,51));
        ADD_EMP_P.add(BILL_L);


        table_BILL = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        table_BILL.setModel(new DefaultTableModel(
                new Object [][] {}, new String [] {

                ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", "."
        }
        ));

        table_BILL.setFont(new Font("Serif", Font.PLAIN,15));

        JScrollPane scrollPane_transportation = new JScrollPane(table_BILL);
        scrollPane_transportation.setBounds(10, 130, 660, 300);
        ADD_EMP_P.add(scrollPane_transportation);

        OPTION_L = new JLabel("DISPLAY OPTIONS : ");
        OPTION_L.setFont(new Font("Serif", Font.BOLD,15));
        OPTION_L.setBounds(130,450,200,30);
        ADD_EMP_P.add(OPTION_L);

        PAID_B = new JButton("PAID BILLS");
        PAID_B.setBounds(100,490,160,35);
        PAID_B.setFont(new Font("Serif", Font.BOLD,15));
        PAID_B.setBackground(Color.BLUE);
        PAID_B.setForeground(Color.white);
        ADD_EMP_P.add(PAID_B);


        UNPAID_B = new JButton("UNPAID BILLS");
        UNPAID_B.setBounds(280,490,160,35);
        UNPAID_B.setFont(new Font("Serif", Font.BOLD,15));
        UNPAID_B.setBackground(Color.BLUE);
        UNPAID_B.setForeground(Color.white);
        ADD_EMP_P.add(UNPAID_B);

        ALL_B = new JButton("ALL BILLS");
        ALL_B.setBounds(460,490,160,35);
        ALL_B.setFont(new Font("Serif", Font.BOLD,15));
        ALL_B.setBackground(Color.BLUE);
        ALL_B.setForeground(Color.white);
        ADD_EMP_P.add(ALL_B);


        Back_B = new JButton("BACK");
        Back_B.setBounds(320,545,80,25);
        Back_B.setFont(new Font("Serif", Font.BOLD,15));
        Back_B.setBackground(Color.BLUE);
        Back_B.setForeground(Color.white);
        ADD_EMP_P.add(Back_B);

        setVisible(true);
        MyListener listener = new MyListener();
        PAID_B.addActionListener(listener);
        UNPAID_B.addActionListener(listener);
        ALL_B.addActionListener(listener);
        Back_B.addActionListener(listener);
    }

    public class MyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getActionCommand().equalsIgnoreCase("PAID BILLS")){

                try {
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
                    PreparedStatement preparedStatement = con.prepareStatement("" +
                            "SELECT CHARGES_ID, TOTAL_PRICE, CHARGES_STATUS, BOOKING_C_STATUS,C_FIRSTNAME,C_LASTNAME, NO_OF_DAYS, to_char(FROM_DATE,'dd-MON-yyyy'), P_NAME, P_TYPE, P_PRICE, P_CITY, H_NAME, H_NO_OF_ROOMS, T_CAPACITY\n" +
                            "                     FROM Charges\n" +
                            "                     INNER JOIN Confirmation ON CONFIRMATION_BOOKING_C_ID = BOOKING_C_ID\n" +
                            "                     INNER JOIN package ON PACKAGE_P_ID=P_ID\n" +
                            "                     INNER JOIN Customer ON Charges.CUSTOMER_C_ID=C_ID\n" +
                            "                     INNER JOIN TRANSPORTATION ON TRANSPORTATION_T_ID =T_ID\n" +
                            "                     INNER JOIN hotel ON  HOTEL_H_ID= H_ID\n" +
                            "                     WHERE BOOKING_C_STATUS = 'Y' and CHARGES_STATUS='Y'   \n" +
                            "                     ORDER BY Confirmation.BOOKING_C_ID ASC");
                    showData_Package(con,preparedStatement);
                    table_BILL.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    resizeColumnWidth(table_BILL);
                    con.close();
                } catch (Exception ae) {
                    System.out.println(ae.toString());
                }
            }

            if(e.getActionCommand().equalsIgnoreCase("UNPAID BILLS")){

                try {
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
                    PreparedStatement preparedStatement = con.prepareStatement("" +
                            "SELECT CHARGES_ID, TOTAL_PRICE, CHARGES_STATUS, BOOKING_C_STATUS,C_FIRSTNAME,C_LASTNAME, NO_OF_DAYS, to_char(FROM_DATE,'dd-MON-yyyy'), P_NAME, P_TYPE, P_PRICE, P_CITY, H_NAME, H_NO_OF_ROOMS, T_CAPACITY\n" +
                            "                     FROM Charges\n" +
                            "                     INNER JOIN Confirmation ON CONFIRMATION_BOOKING_C_ID = BOOKING_C_ID\n" +
                            "                     INNER JOIN package ON PACKAGE_P_ID=P_ID\n" +
                            "                     INNER JOIN Customer ON Charges.CUSTOMER_C_ID=C_ID\n" +
                            "                     INNER JOIN TRANSPORTATION ON TRANSPORTATION_T_ID =T_ID\n" +
                            "                     INNER JOIN hotel ON  HOTEL_H_ID= H_ID\n" +
                            "                     WHERE BOOKING_C_STATUS = 'Y' and CHARGES_STATUS='N'   \n" +
                            "                     ORDER BY Confirmation.BOOKING_C_ID ASC");
                    showData_Package(con,preparedStatement);
                    table_BILL.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    resizeColumnWidth(table_BILL);
                    con.close();
                } catch (Exception ae) {
                    System.out.println(ae.toString());
                }
            }

            if(e.getActionCommand().equalsIgnoreCase("ALL BILLS")){

                try {
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
                    PreparedStatement preparedStatement = con.prepareStatement("" +
                            "SELECT CHARGES_ID, TOTAL_PRICE, CHARGES_STATUS, BOOKING_C_STATUS,C_FIRSTNAME,C_LASTNAME, NO_OF_DAYS, to_char(FROM_DATE,'dd-MON-yyyy'), P_NAME, P_TYPE, P_PRICE, P_CITY,\n" +
                            "                 H_NAME, H_NO_OF_ROOMS, T_CAPACITY\n" +
                            "                 FROM Charges\n" +
                            "                 INNER JOIN Confirmation ON CONFIRMATION_BOOKING_C_ID = BOOKING_C_ID\n" +
                            "                 INNER JOIN package ON PACKAGE_P_ID=P_ID\n" +
                            "                 INNER JOIN Customer ON Charges.CUSTOMER_C_ID=C_ID\n" +
                            "                 INNER JOIN TRANSPORTATION ON TRANSPORTATION_T_ID =T_ID\n" +
                            "                 INNER JOIN hotel ON  HOTEL_H_ID= H_ID\n" +
                            "                 WHERE BOOKING_C_STATUS = 'Y'  \n" +
                            "                 ORDER BY Confirmation.BOOKING_C_ID ASC");
                    showData_Package(con,preparedStatement);
                    table_BILL.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    resizeColumnWidth(table_BILL);
                    con.close();
                } catch (Exception ae) {
                    System.out.println(ae.toString());
                }
            } if (e.getActionCommand().equalsIgnoreCase("BACK")) {
                dispose();
                new MAIN_Admin_GUI();
            }
        }
    }

    private void showData_Package(Connection con, PreparedStatement preparedStatement){
        try{

            DefaultTableModel defaultTableModel = (DefaultTableModel) table_BILL.getModel();
            while (defaultTableModel.getRowCount()>0){
                defaultTableModel.removeRow(0); }
            String[] TableData  = {
                    "CHARGES ID","TOTAL COST","CHARGES STATUS", "CONFIRMATION STATUS", "CUSTOMER NAME", "NO OF DAYS","FROM DATE",  "PACKAGE NAME", "PACKAGE TYPE","PACKAGE PRICE","HOTEL CITY","HOTEL NAME","NO OF ROOMS","TRANSPORT CAPACITY"};
            defaultTableModel.addRow(TableData);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                String[] departmentData = {String.valueOf(rs.getInt(1)) ,String.valueOf(rs.getInt(2)),Bill_Status(rs.getString(3)),Confirmation_Status(rs.getString(4)),
                        (rs.getString(5)+" "+rs.getString(6)),String.valueOf(rs.getInt(7)),
                        (rs.getString(8)), rs.getString(9),
                        rs.getString(10), String.valueOf(rs.getInt(11)), rs.getString(12),
                        rs.getString(13),String.valueOf(rs.getInt(14)),String.valueOf(rs.getInt(15))};

                defaultTableModel.addRow(departmentData);
            }
        }
        catch (Exception ea){
            ea.printStackTrace();
        }
    }

    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 30;
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +15 , width);
            }
            if(width > 300)
                width=300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    public String Confirmation_Status(String status){
        if (status.equals("Y")){
            return "Confirmed";
        }else{
            return "Not Confirmed";
        }}

    public String Bill_Status(String status){
        if (status.equals("Y")){
            return "Paid";
        }else{
            return "Unpaid";
        }
    }
}

