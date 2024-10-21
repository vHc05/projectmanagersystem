/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package home;

/**
 *
 * @author Alumno
 */
public class UI extends javax.swing.JFrame {

    /**
     * Creates new form UI
     */
    public UI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        csvButton = new javax.swing.JButton();
        jsonButton = new javax.swing.JButton();
        xmlButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        datButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(536, 106));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        csvButton.setText("Menú CSV");
        csvButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        csvButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                csvButtonActionPerformed(evt);
            }
        });
        getContentPane().add(csvButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 91, 50));

        jsonButton.setText("Menú JSON");
        jsonButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jsonButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jsonButtonActionPerformed(evt);
            }
        });
        getContentPane().add(jsonButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, -1, 50));

        xmlButton.setText("Menú XML");
        xmlButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        xmlButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xmlButtonActionPerformed(evt);
            }
        });
        getContentPane().add(xmlButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, 91, 50));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("GESTIÓN DE PROYECTOS DE EMPRESA");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 12, -1, -1));

        datButton.setText("Menú DAT");
        datButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        datButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                datButtonActionPerformed(evt);
            }
        });
        getContentPane().add(datButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 50, 91, 50));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void csvButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_csvButtonActionPerformed
        // TODO add your handling code here:
        new CSVInterface().setVisible(true);
    }//GEN-LAST:event_csvButtonActionPerformed

    private void jsonButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jsonButtonActionPerformed
        // TODO add your handling code here:
        new JSONInterface().setVisible(true);
    }//GEN-LAST:event_jsonButtonActionPerformed

    private void xmlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xmlButtonActionPerformed
        // TODO add your handling code here:
        new XMLInterface().setVisible(true);
    }//GEN-LAST:event_xmlButtonActionPerformed

    private void datButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_datButtonActionPerformed
        // TODO add your handling code here:
//        new DATInterface().setVisible(true);
    }//GEN-LAST:event_datButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton csvButton;
    private javax.swing.JButton datButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton jsonButton;
    private javax.swing.JButton xmlButton;
    // End of variables declaration//GEN-END:variables

}
