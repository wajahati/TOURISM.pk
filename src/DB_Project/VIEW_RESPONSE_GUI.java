package DB_Project;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.text.DefaultStyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

public class VIEW_RESPONSE_GUI extends JFrame {

    JButton DIS_COMPLAIN_B, SUBMIT_B, Back_B;
    JLabel RESPONSE_CB_L,MANAGE_BILL_L,BILL_L,Respond_L;


    JTextArea  Complain_RESPONSE_T;
    JComboBox<String> Organizer_CB;
    ArrayList<String> Organizer_ID_AL;
    String[] Organizer_ID_ARR;
    JScrollPane DETAILS;
    JPanel ADD_EMP_P;
    JTable table_BILL;
    int C_ID;

    public VIEW_RESPONSE_GUI(int C_ID){
        this.C_ID = C_ID;
        Organizer_ID_AL= new ArrayList<>();
        ADD_EMP_P = new JPanel() {@Override
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

        MANAGE_BILL_L = new JLabel("RESPONSES");
        MANAGE_BILL_L.setFont(new Font("Serif", Font.BOLD,25));
        MANAGE_BILL_L.setBounds(170,20,500,35);
        MANAGE_BILL_L.setForeground(new Color(51,51,51));
        ADD_EMP_P.add(MANAGE_BILL_L);

        BILL_L = new JLabel("Responded Complains : ");
        BILL_L.setFont(new Font("Serif", Font.BOLD,15));
        BILL_L.setBounds(100,100,700,25);
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
                ".", ".", ".", "."
        }
        ));
        showData_Organizer();
        table_BILL.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        resizeColumnWidth(table_BILL);
        table_BILL.setFont(new Font("Serif", Font.PLAIN,15));

        JScrollPane scrollPane_transportation = new JScrollPane(table_BILL);
        scrollPane_transportation.setBounds(100, 130, 500, 200);
        ADD_EMP_P.add(scrollPane_transportation);

        RESPONSE_CB_L = new JLabel("Select ID :");
        RESPONSE_CB_L.setFont(new Font("Serif", Font.BOLD,15));
        RESPONSE_CB_L.setBounds(100,360,500,35);
        RESPONSE_CB_L.setForeground(new Color(51,51,51));
        ADD_EMP_P.add(RESPONSE_CB_L);

        Organizer_CB = new JComboBox<String>(Organizer_ID_ARR);
        Organizer_CB.setBounds(200,360,50,35);
        ADD_EMP_P.add(Organizer_CB);

        DIS_COMPLAIN_B = new JButton("SHOW RESPONSE");
        DIS_COMPLAIN_B.setBounds(270,360,200,35);
        DIS_COMPLAIN_B.setFont(new Font("Serif", Font.BOLD,15));
        DIS_COMPLAIN_B.setBackground(Color.BLUE);
        DIS_COMPLAIN_B.setForeground(Color.white);

        ADD_EMP_P.add(DIS_COMPLAIN_B);

        Respond_L = new JLabel("RESPONSE : ");
        Respond_L.setFont(new Font("Serif", Font.BOLD,15));
        Respond_L.setBounds(100,390,500,30);
        Respond_L.setVisible(false);
        ADD_EMP_P.add(Respond_L);

        Complain_RESPONSE_T = new JTextArea();
        Complain_RESPONSE_T.setLineWrap(true);
        Complain_RESPONSE_T.setWrapStyleWord(true);
        Complain_RESPONSE_T.setEditable(false);
        Complain_RESPONSE_T.setFont(new Font("Serif", Font.PLAIN,15));
        DETAILS = new JScrollPane(Complain_RESPONSE_T);
        DETAILS.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        DETAILS.setBounds(100, 420, 500, 200);
        DETAILS.setVisible(false);
        ADD_EMP_P.add(DETAILS);



        Back_B = new JButton("BACK");
        Back_B.setBounds(150,650,100,30);
        Back_B.setFont(new Font("Serif", Font.BOLD,15));
        Back_B.setBackground(Color.BLUE);
        Back_B.setForeground(Color.white);
        ADD_EMP_P.add(Back_B);

        setVisible(true);
        MyListener listener = new MyListener();
        DIS_COMPLAIN_B.addActionListener(listener);

        Back_B.addActionListener(listener);
    }

    public class MyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("SHOW RESPONSE")) {
                try {
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
                    PreparedStatement preparedStatement = con.prepareStatement("SELECT RESPONSE FROM Complain WHERE COMPLAIN_ID = ? ");
                    String s1 = Organizer_CB.getSelectedItem().toString();
                    preparedStatement.setString(1, s1);
                    ResultSet rs = preparedStatement.executeQuery();
                    if (rs.next()){
                        Respond_L.setVisible(true);
                        DETAILS.setVisible(true);
                        Complain_RESPONSE_T.setText(rs.getString(1));
                    }
                    con.close();
                } catch (Exception ae) {
                    System.out.println(ae.toString());
                }
            }

            if (e.getActionCommand().equalsIgnoreCase("BACK")) {
                dispose();
                new MAIN_COMPLAIN_Customer_GUI(C_ID);
            }
        }
    }
    private void showData_Organizer(){
        try{
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
            PreparedStatement preparedStatement;

            System.out.println("Organizer");
            preparedStatement = con.prepareStatement("SELECT COMPLAIN_ID,COMPLAIN_SUBJECT,to_char(COMPLAIN_DATE,'dd-MON-yyyy') FROM Complain WHERE RESPONSE_STATUS='Y' and CUSTOMER_C_ID= ? ORDER BY COMPLAIN_ID");
            preparedStatement.setInt(1, C_ID);
            DefaultTableModel defaultTableModel = (DefaultTableModel) table_BILL.getModel();
            String [] data={
                    "Complain ID","SUBJECT","STATUS","DATED"};

            while (defaultTableModel.getRowCount()>0){
                defaultTableModel.removeRow(0); }

            ResultSet rs = preparedStatement.executeQuery();
            defaultTableModel.addRow(data);
            while (rs.next()){
                String[] departmentData = {String.valueOf(rs.getInt(1)), rs.getString(2),
                        "Responded", String.valueOf(rs.getString(3))};
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
            }
            if(width > 300)
                width=300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }
}
