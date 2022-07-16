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

public class VIEW_Organizers_GUI extends JFrame {
    JButton Back_B;
    JLabel View_L, PAckage_L;
    JTable table_Organizer;

    public VIEW_Organizers_GUI(){
        JPanel Menu_P = new JPanel()
        {@Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            ImageIcon img = new ImageIcon("background.png");
            g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        }};
        setSize(850, 750);
        setTitle("TOURISM NETWORK");
        add(Menu_P);
        Menu_P.setLayout(null);
        Menu_P.setBackground(new Color(153,153,153));

        View_L = new JLabel("VIEW ORGANIZERS");
        View_L.setFont(new Font("Serif", Font.BOLD,25));
        View_L.setBounds(260,50,500,35);
        View_L.setForeground(new Color(51,51,51));
        Menu_P.add(View_L);

        PAckage_L = new JLabel("Available Organizers List");
        PAckage_L.setFont(new Font("Serif", Font.BOLD,15));
        PAckage_L.setBounds(20,200,400,25);
        PAckage_L.setForeground(new Color(51,51,51));
        Menu_P.add(PAckage_L);

        table_Organizer = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        table_Organizer.setModel(new DefaultTableModel(
                new Object [][] {}, new String [] {
                ".", ".", ".", ".",".", "." ,"."
                }
        ));
        showData_Organizer();
        table_Organizer.setFont(new Font("Serif", Font.PLAIN,15));
        table_Organizer.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        resizeColumnWidth(table_Organizer);
        JScrollPane scrollPane_transportation = new JScrollPane(table_Organizer);
        scrollPane_transportation.setBounds(10, 230, 760, 300);
        Menu_P.add(scrollPane_transportation);

        Back_B = new JButton("BACK");
        Back_B.setBounds(100,550,80,25);
        Back_B.setBackground(Color.BLUE);
        Back_B.setForeground(Color.white);
        Back_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(Back_B);


        setVisible(true);
        VIEW_Organizers_GUI.MyListener listener = new MyListener();
        Back_B.addActionListener(listener);
    }


    public class MyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("BACK")) {
                dispose();
                new MAIN_Organizer_GUI();
            }
        }

    }


    private void showData_Organizer(){
        try{
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
            PreparedStatement preparedStatement;

            System.out.println("Organizer");
            preparedStatement = con.prepareStatement("" +
                    "SELECT O_ID, O_FIRSTNAME, O_LASTNAME, O_TRIPS_ORGANIZED,AMOUNT, O_PHONENO, O_EMAIL \n" +
                    "FROM Organizer\n" +
                    "INNER JOIN Salary ON ORGANIZER_O_ID=O_ID ORDER BY O_ID ASC");
            DefaultTableModel defaultTableModel = (DefaultTableModel) table_Organizer.getModel();

            while (defaultTableModel.getRowCount()>0){
                defaultTableModel.removeRow(0); }

            ResultSet rs = preparedStatement.executeQuery();
            String  [] DATA = {"ID", "FIRST NAME", "LAST NAME", "PACKAGES MANAGING","SALARY", "PHONE No." ,"EMAIL"};
            System.out.println(DATA);
            defaultTableModel.addRow(DATA);
            while (rs.next()){
                String[] departmentData = {String.valueOf(rs.getInt(1)), rs.getString(2),
                        rs.getString(3), String.valueOf(rs.getInt(4)), String.valueOf(rs.getInt(5)),rs.getString(6),rs.getString(7)};
                System.out.println(departmentData[0]);
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


