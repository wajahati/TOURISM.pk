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

public class MAIN_VIEW_Package_GUI extends JFrame{
    JButton Back_B;
    JLabel View_L, Hotel_L;
    JTable table_Package;

    public MAIN_VIEW_Package_GUI(){
        JPanel Menu_P = new JPanel()
        {@Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            ImageIcon img = new ImageIcon("background.png");
            g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        }}
                ;
        setSize(700, 750);
        setTitle("TOURISM NETWORK");
        add(Menu_P);
        Menu_P.setLayout(null);
        Menu_P.setBackground(new Color(153,153,153));

        View_L = new JLabel("VIEW PACKAGE");
        View_L.setFont(new Font("Serif", Font.BOLD,25));
        View_L.setBounds(260,50,500,35);
        View_L.setForeground(new Color(51,51,51));
        Menu_P.add(View_L);

        Hotel_L = new JLabel("Available Packages List");
        Hotel_L.setFont(new Font("Serif", Font.BOLD,15));
        Hotel_L.setBounds(20,200,400,25);
        Hotel_L.setForeground(new Color(51,51,51));
        Menu_P.add(Hotel_L);

        table_Package = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        table_Package.setModel(new DefaultTableModel(
                new Object [][] {}, new String [] {

                ".", ".", ".", ".", ".",".", ".", "."
                }
        ));
        showData_Package();
        table_Package.setFont(new Font("Serif", Font.PLAIN,15));
        table_Package.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        resizeColumnWidth(table_Package);
        JScrollPane scrollPane_transportation = new JScrollPane(table_Package);
        scrollPane_transportation.setBounds(10, 230, 660, 300);
        Menu_P.add(scrollPane_transportation);

        Back_B = new JButton("BACK");
        Back_B.setBounds(100,550,80,25);
        Back_B.setBackground(Color.BLUE);
        Back_B.setForeground(Color.white);
        Back_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(Back_B);


        setVisible(true);
        MyListener listener = new MyListener();
        Back_B.addActionListener(listener);
    }


    public class MyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("BACK")) {
                dispose();
                new Tourism_Managment_System();
            }
        }
    }



    private void showData_Package(){
        try{
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
            PreparedStatement preparedStatement;

            System.out.println("Package");
            preparedStatement = con.prepareStatement("" +
                    "SELECT P_ID, P_NAME, P_TYPE, P_PRICE, P_CITY,H_NAME, H_NO_OF_ROOMS, T_CAPACITY\n" +
                    "                    FROM package \n" +
                    "                    INNER JOIN TRANSPORTATION ON TRANSPORTATION_T_ID =T_ID\n" +
                    "                    INNER JOIN hotel ON  HOTEL_H_ID= H_ID  " +
                    "                    WHERE PACKAGE_VISIBILITY='Y'\n" +
                    "                    ORDER BY Package.P_ID ASC");
            DefaultTableModel defaultTableModel = (DefaultTableModel) table_Package.getModel();

            while (defaultTableModel.getRowCount()>0){
                defaultTableModel.removeRow(0); }

            ResultSet rs = preparedStatement.executeQuery();
            String[] TableData  = {
                    "ID", "NAME", "TYPE", "PRICE", "CITY","HOTEL NAME", "NO OF ROOMS", "TRANSPORT CAPACITY"};
            defaultTableModel.addRow(TableData);
            while (rs.next()){
                String[] departmentData = {String.valueOf(rs.getInt(1)), rs.getString(2),
                        rs.getString(3), String.valueOf(rs.getInt(4)),rs.getString(5),
                        rs.getString(6),String.valueOf(rs.getInt(7)),String.valueOf(rs.getInt(8))};

                defaultTableModel.addRow(departmentData);
            }
            con.close();
        } catch (Exception ea){
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
