package DB_Project;

import oracle.sql.INTERVALDS;

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
import java.util.ArrayList;

public class DELETE_ORGANIZER_Admin_GUI extends JFrame {
    JButton Delete_B, Back_B;
    JLabel DELETE_EMP_L, Organizer_ID_L, Organizer_L;
    JComboBox<String> Organizer_CB;
    ArrayList<String> Organizer_ID_AL;
    String[] Organizer_ID_ARR;

    JTable table_Organizer;

    DELETE_ORGANIZER_Admin_GUI(){
        JPanel ADD_EMP_P = new JPanel(){@Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            ImageIcon img = new ImageIcon("background.png");
            g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        }};
        Organizer_ID_AL = new ArrayList<>();
        setSize(700, 750);
        setTitle("Tourism Network");
        add(ADD_EMP_P);
        ADD_EMP_P.setLayout(null);
        ADD_EMP_P.setBackground(new Color(153,153,153));

        DELETE_EMP_L = new JLabel("DELETE ORGANIZER");
        DELETE_EMP_L.setFont(new Font("Serif", Font.BOLD,25));
        DELETE_EMP_L.setBounds(170,20,500,35);
        DELETE_EMP_L.setForeground(new Color(51,51,51));
        ADD_EMP_P.add(DELETE_EMP_L);

        Organizer_L = new JLabel("Available Organizer List");
        Organizer_L.setFont(new Font("Serif", Font.BOLD,15));
        Organizer_L.setBounds(20,100,400,25);
        Organizer_L.setForeground(new Color(51,51,51));
        ADD_EMP_P.add(Organizer_L);

        /////Transportation
        table_Organizer = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        table_Organizer.setModel(new DefaultTableModel(
                new Object [][] {}, new String [] {
//                "ID", "FIRST NAME", "LAST NAME", "PACKAGES MANAGING", "PHONE No." ,"EMAIL"
                ".", ".", ".", ".",".", "." ,"."
                }
        ));
        showData_Organizer();
        table_Organizer.setFont(new Font("Serif", Font.PLAIN,15));
        table_Organizer.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        resizeColumnWidth(table_Organizer);
        JScrollPane scrollPane_transportation = new JScrollPane(table_Organizer);
        scrollPane_transportation.setBounds(10, 130, 660, 300);
        ADD_EMP_P.add(scrollPane_transportation);

        Organizer_ID_L = new JLabel("Enter ID : ");
        Organizer_ID_L.setFont(new Font("Serif", Font.BOLD,15));
        Organizer_ID_L.setBounds(150,480,120,30);
        ADD_EMP_P.add(Organizer_ID_L);

        Organizer_CB = new JComboBox<String>(Organizer_ID_ARR);
        Organizer_CB.setBounds(300,480,50,30);
        ADD_EMP_P.add(Organizer_CB);

        Delete_B = new JButton("DELETE");
        Delete_B.setBounds(360,540,100,25);
        Delete_B.setBackground(Color.BLUE);
        Delete_B.setForeground(Color.white);
        ADD_EMP_P.add(Delete_B);

        Back_B = new JButton("BACK");
        Back_B.setBounds(200,540,80,25);
        Back_B.setBackground(Color.BLUE);
        Back_B.setForeground(Color.white);
        ADD_EMP_P.add(Back_B);

        setVisible(true);
        DELETE_ORGANIZER_Admin_GUI.MyListener listener = new MyListener();
        Delete_B.addActionListener(listener);
        Back_B.addActionListener(listener);
    }


    public class MyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("BACK")) {
                dispose();
                new MAIN_Organizer_GUI();
            }
            else if (Count_Organizers()==1){
                JOptionPane.showMessageDialog(null, "WARNING! Only One organizer left\nAdd another to delete this one");
            }
            else if(e.getActionCommand().equalsIgnoreCase("DELETE")){

                try {
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
                    PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM Organizer WHERE O_ID=?");
                    PreparedStatement preparedStatement1 = con.prepareStatement("DELETE FROM Salary WHERE ORGANIZER_O_ID=?");

                    String s1 = Organizer_CB.getSelectedItem().toString();;
                    Assign_Another_Organizer(Integer.parseInt(s1),con);
                    preparedStatement1.setInt(1, Integer.parseInt(s1));
                    preparedStatement.setInt(1, Integer.parseInt(s1));
                    preparedStatement1.executeQuery();
                    preparedStatement.executeQuery();
                    JOptionPane.showMessageDialog(new JFrame(), "Deleted Successfully Successfully");
                    dispose();
                    con.close();
                    new DELETE_ORGANIZER_Admin_GUI();
                } catch (Exception ae) {
                    System.out.println(ae.toString());
                    ae.printStackTrace();
                }
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
                        rs.getString(3), String.valueOf(rs.getInt(4)),String.valueOf(rs.getInt(5)),rs.getString(6),rs.getString(7)};
                System.out.println(departmentData[0]);
                Organizer_ID_AL.add(departmentData[0]);
                defaultTableModel.addRow(departmentData);
            }
            Organizer_ID_ARR = Organizer_ID_AL.toArray(new String[Organizer_ID_AL.size()]);
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
            }if(width > 300)
                width=300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    public int Count_Organizers(){
        try{
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
            PreparedStatement ps1 =con.prepareStatement("SELECT count(*) FROM Organizer");
            ResultSet r1= ps1.executeQuery();
                if (r1.next()) {
                    return (r1.getInt(1));
                }
            con.close();
        } catch (Exception ea){
            ea.printStackTrace();
        }
        return 0;
    }
    public void Assign_Another_Organizer(int O_ID,  Connection con ){
        try{
            int New_Organizer=0;
            PreparedStatement ps1 =con.prepareStatement("SELECT O_id FROM (SELECT O_id FROM Organizer ORDER BY dbms_random.value) WHERE rownum = 1");
            PreparedStatement ps2 =con.prepareStatement("UPDATE Package SET  ORGANIZER_O_ID = ? WHERE ORGANIZER_O_ID= ?");
            PreparedStatement ps4 = con.prepareStatement("UPDATE Organizer SET  O_TRIPS_ORGANIZED = (SELECT  sum(A) " +
                    "FROM ( (SELECT count(*) as A FROM Package WHERE ORGANIZER_O_ID= ? ) UNION  ALL (SELECT O_TRIPS_ORGANIZED as A FROM Organizer WHERE o_id= ? ))) WHERE O_ID= ?");

            while (true) {
                ResultSet r1 = ps1.executeQuery();
                if (r1.next()) {
                    New_Organizer = (r1.getInt(1));
                    if (New_Organizer != O_ID) {

                        ps4.setInt(1,O_ID);
                        ps4.setInt(2,New_Organizer);
                        ps4.setInt(3,New_Organizer);
                        ps4.executeQuery();

                        ps2.setInt(1, New_Organizer);
                        ps2.setInt(2, O_ID);
                        ps2.executeQuery();
                        return;
                    }
                } else {
                    return;
                }
            }
        } catch (Exception ea){
            ea.printStackTrace();
        }
    }
}
