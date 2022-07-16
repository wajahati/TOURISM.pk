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

public class BOOKING_HISTORY_Customer_GUI extends JFrame {

    JButton Back_B;
    JLabel View_History_L, History_L;
    JTable table_History;

    int C_ID;

    public BOOKING_HISTORY_Customer_GUI(int C_ID){
        this.C_ID = C_ID;
        JPanel ADD_EMP_P = new JPanel()
        {@Override
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

        View_History_L = new JLabel("VIEW BOOKING HISTORY");
        View_History_L.setFont(new Font("Serif", Font.BOLD,25));
        View_History_L.setBounds(170,20,500,35);
        View_History_L.setForeground(new Color(51,51,51));
        ADD_EMP_P.add(View_History_L);

        History_L = new JLabel("HISTORY");
        History_L.setFont(new Font("Serif", Font.BOLD,15));
        History_L.setBounds(20,100,400,25);
        History_L.setForeground(new Color(51,51,51));
        ADD_EMP_P.add(History_L);

        table_History = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        table_History.setModel(new DefaultTableModel(
                new Object [][] {}, new String [] {

                ".", ".", ".", ".", ".", ".", ".", ".", ".", "."
        }
        ));
        showData_Package();
        table_History.setFont(new Font("Serif", Font.PLAIN,15));
        table_History.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        resizeColumnWidth(table_History);
        JScrollPane scrollPane_transportation = new JScrollPane(table_History);
        scrollPane_transportation.setBounds(10, 130, 660, 300);
        ADD_EMP_P.add(scrollPane_transportation);

        Back_B = new JButton("BACK");
        Back_B.setBounds(320,545,80,25);
        Back_B.setFont(new Font("Serif", Font.BOLD,15));
        Back_B.setBackground(Color.BLUE);
        Back_B.setForeground(Color.white);
        ADD_EMP_P.add(Back_B);

        setVisible(true);
        MyListener listener = new MyListener();
        Back_B.addActionListener(listener);

    }

    public class MyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().equalsIgnoreCase("BACK")) {
                dispose();
                new MAIN_Customer_GUI(C_ID);
            }
        }
    }

    private void showData_Package(){
        try{
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
            PreparedStatement preparedStatement = con.prepareStatement("" +
                    "SELECT \n" +
                    "        BH_ID, PACKAGE_NAME, PACKAGE_TYPE, TOTAL_PRICE, PACKAGE_CITY,to_char(FROM_DATE,'dd-MON-yyyy'), HOTEL_NAME,\n" +
                    "        NO_OF_ROOMS, PACKAGE_PRICE, TRANNSPORT_CAPACITY \n" +
                    "FROM \n" +
                    "        Booking_History\n" +
                    "WHERE \n" +
                    "        CUSTOMER_C_ID=?\n" +
                    "ORDER BY " +
                    "        BH_ID ASC");

            DefaultTableModel defaultTableModel = (DefaultTableModel) table_History.getModel();
            while (defaultTableModel.getRowCount()>0){
                defaultTableModel.removeRow(0); }
            String[] TableData  = {
                    "HISTORY ID", "PACKAGE NAME", "PACKAGE TYPE", "TOTAL COST", "PACKAGE CITY","FROM DATE", "HOTEL NAME","NO OF ROOMS", "PRICE PER DAY", "TRANSPORT CAPACITY"};
            defaultTableModel.addRow(TableData);
            preparedStatement.setInt(1,C_ID);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                String[] departmentData = {String.valueOf(rs.getInt(1)) ,rs.getString(2),rs.getString(3),String.valueOf(rs.getInt(4)),rs.getString(5),
                        rs.getString(6),rs.getString(7),String.valueOf(rs.getInt(8)), String.valueOf(rs.getInt(9)), String.valueOf(rs.getInt(10))};

                defaultTableModel.addRow(departmentData);
            }
            con.close();
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
}


