/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Modelo.Administrador;
import Modelo.Simplechat;
import static Vistas.Registrar.listadmin;
import static java.awt.image.ImageObserver.HEIGHT;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author Jeremy
 */
public class Registrar extends javax.swing.JFrame {

    /**
     * Creates new form Registrar
     */
    
    Administrador admin;
    login Lg;
    Simplechat sc =new Simplechat();
    Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
						+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    
    
    public static LinkedList listadmin = new LinkedList();
    
    
    public Registrar() {
        initComponents();
        setSize(600, 471);
                this.setLocationRelativeTo(null);
    }
    
       public void guardarAdmin(){
    
        String vacio = "";
        String vcorreo= "";
        //obtiene los datos ingresados
        String nom  =   Cnombre.getText();
        String ape  =   Capellido.getText();
        String cor  =   Ccorreo.getText();
        String clv  =   Cclave.getText();
        String cond  =   Ccond.getText();
        
        
        
        
         Matcher mather = pattern.matcher(cor);
        if (mather.find() == true) {
            vcorreo =cor;
                        
        }
        
        //valida que esten todos los campos completos
        if(vacio.equals(nom) || vacio.equals(ape) || vacio.equals(vcorreo) || vacio.equals(clv) || vacio.equals(cond) ){
        
            JOptionPane.showMessageDialog(null, "Campos por completar", "Datos incompleto", HEIGHT);
            
        } /*if (cond != "profesor" || cond != "Profesor" || cond != "alumno" || cond != "Alumno" ){
        
         JOptionPane.showMessageDialog(null, "Elija una condicion", "Datos incompleto", HEIGHT);
        
        }*/else{
        
        //Guarda los datos
            admin = new Administrador(clv,cond,nom,ape,cor);
            listadmin.add(admin);
            JOptionPane.showMessageDialog(null, "Datos guardados correctamente", "Administrador", HEIGHT);
            
            Lg =new login(admin);
            //Lg =new login();
            Lg.setVisible(true);
            this.setVisible(false);
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

        Cnombre = new javax.swing.JTextField();
        Capellido = new javax.swing.JTextField();
        Ccorreo = new javax.swing.JTextField();
        Cclave = new javax.swing.JTextField();
        Ccond = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        Bcancelar = new javax.swing.JButton();
        Bregistrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(690, 480));
        setMinimumSize(new java.awt.Dimension(690, 480));
        setPreferredSize(new java.awt.Dimension(690, 480));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Cnombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CnombreActionPerformed(evt);
            }
        });
        getContentPane().add(Cnombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 75, 160, -1));
        getContentPane().add(Capellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 75, 160, -1));
        getContentPane().add(Ccorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 138, 160, -1));
        getContentPane().add(Cclave, new org.netbeans.lib.awtextra.AbsoluteConstraints(341, 138, 159, -1));

        Ccond.setText("Profesor / Alumno");
        getContentPane().add(Ccond, new org.netbeans.lib.awtextra.AbsoluteConstraints(223, 203, 160, -1));

        jLabel8.setFont(new java.awt.Font("Gadugi", 0, 18)); // NOI18N
        jLabel8.setText(" REGISTRO DE ADMINISTRADOR");
        jLabel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, -1, -1));

        jLabel9.setFont(new java.awt.Font("Gadugi", 0, 12)); // NOI18N
        jLabel9.setText("Nombre:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 76, -1, -1));

        jLabel10.setFont(new java.awt.Font("Gadugi", 0, 12)); // NOI18N
        jLabel10.setText("Apellido:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(283, 76, -1, -1));

        jLabel11.setFont(new java.awt.Font("Gadugi", 0, 12)); // NOI18N
        jLabel11.setText("Correo:");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 139, -1, -1));

        jLabel12.setFont(new java.awt.Font("Gadugi", 0, 12)); // NOI18N
        jLabel12.setText("Contraseña:");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(268, 139, -1, -1));

        jLabel13.setFont(new java.awt.Font("Gadugi", 0, 12)); // NOI18N
        jLabel13.setText("Condicion:");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 204, -1, -1));

        Bcancelar.setBackground(new java.awt.Color(255, 50, 57));
        Bcancelar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Bcancelar.setForeground(new java.awt.Color(255, 255, 255));
        Bcancelar.setText("CANCELAR");
        Bcancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Bcancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BcancelarActionPerformed(evt);
            }
        });
        getContentPane().add(Bcancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, 140, 30));

        Bregistrar.setBackground(new java.awt.Color(255, 255, 255));
        Bregistrar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        Bregistrar.setText("REGISTRAR ADMINISTRADOR");
        Bregistrar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Bregistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BregistrarActionPerformed(evt);
            }
        });
        getContentPane().add(Bregistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 360, 230, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BcancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BcancelarActionPerformed

        Lg =new login(admin);
        //Lg =new login();
        Lg.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_BcancelarActionPerformed

    private void BregistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BregistrarActionPerformed

        //registar administrador

        guardarAdmin();

    }//GEN-LAST:event_BregistrarActionPerformed

    private void CnombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CnombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CnombreActionPerformed

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
            java.util.logging.Logger.getLogger(Registrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Registrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Registrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Registrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Registrar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Bcancelar;
    private javax.swing.JButton Bregistrar;
    private javax.swing.JTextField Capellido;
    private javax.swing.JTextField Cclave;
    private javax.swing.JTextField Ccond;
    private javax.swing.JTextField Ccorreo;
    private javax.swing.JTextField Cnombre;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}
