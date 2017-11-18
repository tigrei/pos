/*
    Point of Sale System Project
    Authors: Clayton Barber, Brandon Barton, Declan Brennan, Maximilian Hasselbusch, Eric Metcalf
    Last Updated: 20 November 2015
 */
package pos;

import java.awt.event.WindowEvent;
import java.rmi.Naming;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;;

public class InventoryLevels extends javax.swing.JFrame {

    Connection con;

    /**
     * Creates new form InventoryLevels
     *
     * @param con
     */
    public InventoryLevels(Connection con) {
        this.con = con;
        Query(null);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void Search(java.awt.event.ActionEvent evt) {
        String sku = SKU.getText();
        String name = Name.getText();
        String platform = Platform.getText();
        if (sku.isEmpty() && name.isEmpty() && platform.isEmpty())   {
            System.out.println("sku:" + sku + " name:" + name + " platform:" + platform);
            return;
        }
        boolean prev = false;
        if (!sku.isEmpty()) {
            sku = " sku = " + sku;
            prev = true;
        }
        if (!name.isEmpty())    {
            name = " name like '%" + name + "%'";
            if (prev) { name = " and" + name; }
            else    { prev = true; }
        }
        if (!platform.isEmpty())    {
            platform = " platform = '" + platform + "'";
            if (prev)   { platform = " and" + platform; }
        }
        String query = "where" + sku + name + platform;
        Query(query);
        this.dispose();
        initComponents();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void Query(String query)    {
        String sku;
        int pnum = 0;
        try {
            Statement s1 = con.createStatement();
            ResultSet result1 = s1.executeQuery("select count(*) from pos.games");
            result1.next();
            pnum = result1.getInt(1);
        } catch (SQLException sqe) {
            System.err.println("Error fetching COUNT from games.");
        }
        data = new String[pnum][];
        for (int i = 0; i < pnum; i++) {
            data[i] = new String[7];
        }
        try {
            Statement s7 = con.createStatement();
            ResultSet result;
            if (query == null || query == "")   {
                System.out.println("first load");
                result = s7.executeQuery("select * from pos.games order by sku");
            }
            else {
                System.out.println("select * from pos.games " + query + " order by sku");
                result = s7.executeQuery("select * from pos.games " + query + " order by sku");
            }
            for (int i = 0; result.next(); i++) {
                data[i][0] = String.valueOf(result.getInt(7));  //sku
                data[i][1] = result.getString(1);   //name
                data[i][2] = result.getString(2);   //platform
                data[i][3] = String.valueOf(result.getInt(3));  //quantity
                data[i][4] = String.valueOf(result.getDouble(4));   //price
                data[i][5] = String.valueOf(result.getDate(5)); //release date
                data[i][6] = result.getString(6);   //esrb
                sku = String.format("%08d", Integer.parseInt(data[i][0]));  //zerofill sku
                data[i][0] = sku;
            }
        } catch (SQLException sqe) {
            System.err.println("Error fetching ALL data from dual.");
            System.err.println(sqe.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Done = new javax.swing.JButton();
        InvTable = new javax.swing.JTable(data, columnNames);
        // Sets Name column to be 300px wide
        InvTable.getColumnModel().getColumn(1).setPreferredWidth(300);

        filter = new javax.swing.JPanel();
        SL = new javax.swing.JLabel();
        Search = new javax.swing.JButton();
        ISL = new javax.swing.JLabel();
        SKU = new javax.swing.JTextField();
        INL = new javax.swing.JLabel();
        Name = new javax.swing.JTextField();
        IPL = new javax.swing.JLabel();
        Platform = new javax.swing.JTextField();

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
                jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
                jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(180, 230, 255));

        jLabel1.setText("Inventory Levels");

        jLabel2.setText("WPS");

        jPanel1.setBackground(new java.awt.Color(180, 230, 255));

        filter.setBackground(new java.awt.Color(180,230,255));

        ISL.setText("SKU: ");

        INL.setText("Name: ");

        IPL.setText("Platform: ");

        Search.setText("Search");
        Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Search(evt);
            }
        });

        jScrollPane1.setViewportView(InvTable);

        Done.setText("Done");
        Done.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DoneActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                    .addContainerGap())
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(Done))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(Done))
        );

        javax.swing.GroupLayout filterLayout = new javax.swing.GroupLayout(filter);
        filter.setLayout(filterLayout);
        filterLayout.setHorizontalGroup(
            filterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(filterLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(ISL)
                    .addComponent(SKU)
                    .addComponent(INL)
                    .addComponent(Name)
                    .addComponent(IPL)
                    .addComponent(Platform)
                    .addComponent(Search))
                .addGap(0,0, Short.MAX_VALUE)
        );
        filterLayout.setVerticalGroup(
            filterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(filterLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(filterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(ISL)
                        .addComponent(SKU)
                        .addComponent(INL)
                        .addComponent(Name)
                        .addComponent(IPL)
                        .addComponent(Platform)
                        .addComponent(Search))
                    .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addContainerGap())
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(filter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addContainerGap())
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2)
                            .addGap(16, 16, 16))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(filter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DoneActionPerformed
        this.dispose();
    }//GEN-LAST:event_DoneActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Done;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable InvTable;
    private String[] columnNames = {"SKU", "Name", "Platform", "Quantity", "Price", "Release Date", "ESRB"};
    private String[][] data;
    //filter results
    private javax.swing.JPanel filter;
    private javax.swing.JLabel SL;
    private javax.swing.JButton Search;
    private javax.swing.JLabel ISL;
    private javax.swing.JTextField SKU;
    private javax.swing.JLabel INL;
    private javax.swing.JTextField Name;
    private  javax.swing.JLabel IPL;
    private javax.swing.JTextField Platform;
    //TODO add price and quantity w/ >, <, =, dropdowns

    // End of variables declaration//GEN-END:variables
}
