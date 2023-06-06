/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package User;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import platillostipicos.accesoadatos.UserDAL;
import platillostipicos.appdesktop.utils.FormEscOpcion;
import platillostipicos.appdesktop.utils.ImageUtils;
import platillostipicos.entidadesdenegocio.User;

/**
 *
 * @author MINEDUCYT
 */
public class FrmUserEsc extends javax.swing.JFrame {
    
    private FrmUserLec frmPadre;
    private int opcionForm;
    private User publicationActual;
    
     private void cerrarFormulario(boolean pIsEvtClosing) {
        if (frmPadre != null) {
            frmPadre.setEnabled(true); // habilitar el formulario FrmUsuarioLec
            frmPadre.setVisible(true); // mostrar el formulario FrmUsuarioLec
        }
        if (pIsEvtClosing == false) { // verificar que no se este cerrando el formulario desde el evento formWindowClosing 
            this.setVisible(false); // Cerrar el formulario FrmRolEsc
            this.dispose(); // Cerrar todos los procesos abiertos en el formulario FrmRolEsc
        }
    }
    
     private boolean validarDatos() {
        boolean result = true;
        if (txtName == null) {
            JOptionPane.showMessageDialog(this, "Nombre es requerido");
            result = false;
        }
        if (txtPassword == null) {
            JOptionPane.showMessageDialog(this, "Password es requerido");
            result = false;
        }
        if (txtEmail == null) {
            JOptionPane.showMessageDialog(this, "Email es requerido");
            result = false;
        }
        return result; // retorna la variable result con el valor true o false para saber si los datos son validos o no
    }
     
     
     private int obtenerMensajeDeConfirmacion() {
        String mess = "¿Seguro que desea ";
        switch (opcionForm) {
            case FormEscOpcion.CREAR:
                mess += " Guardar?";
                break;
            case FormEscOpcion.MODIFICAR:
                mess += " Modificar?";
                break;
            case FormEscOpcion.ELIMINAR:
                mess += " Eliminar?";
                break;
            default:
                break;
        }
        int input = JOptionPane.showConfirmDialog(this, mess, "",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return input;
    }
     
     
     private void llenarEntidadConLosDatosDeLosControles() {
        this.publicationActual.setName(this.txtName.getText());
        this.publicationActual.setEmail(this.txtEmail.getText());
        this.publicationActual.setPassword(this.txtPassword.getText());
        
         byte[] imageData1 = ImageUtils.getImageDataFromLabel(lblSelectedImage1);
        this.publicationActual.setImgUser(imageData1);
    }
     
     private void iniciarDatos(User pPublication, int pOpcionForm, FrmUserLec pFrmPadre) {
        addExtraButtons();
        frmPadre = pFrmPadre;
        publicationActual = new User();
        opcionForm = pOpcionForm;
        this.txtName.setEditable(true);
        switch (pOpcionForm) {
            case FormEscOpcion.CREAR:
                this.btnOk.setMnemonic('N'); // modificar la tecla de atajo del boton btnOk a la letra N
                this.setTitle("Crear un nuevo usuario");
                break;
//            case FormEscOpcion.MODIFICAR:
//                btnOk.setText("Confirmar");
//                this.btnOk.setMnemonic('M'); // modificar la tecla de atajo del boton btnOk a la letra M
//                this.setTitle("Editar Publicacion");
//                this.lbRestaurant.setVisible(false);
//                this.cbRestaurants.setVisible(false);
//                llenarControles(pPublication);
//                break;
//            case FormEscOpcion.ELIMINAR:
//                btnOk.setText("Eliminar");
//                this.btnOk.setMnemonic('E');
//                this.setTitle("Eliminar el Publicacion");
//                this.txtComentario.setEditable(false);
//                this.cbRestaurants.setEnabled(false);
//                this.btnInputImg1.setVisible(false);
//                this.btnInputImg2.setVisible(false);
//                this.btnInputImg3.setVisible(false);
//                this.btnInputImg4.setVisible(false);
//                this.btnInputImg5.setVisible(false);
//
//                llenarControles(pPublication);
//                break;
//            case FormEscOpcion.VER:
//                btnOk.setText("Ver"); // modificar el texto del boton btnOk a "Ver" cuando la pOpcionForm sea VER
//                btnOk.setVisible(false); // ocultar el boton btnOk cuando pOpcionForm sea opcion VER
//                this.setTitle("Ver el Publicacion"); // modificar el titulo de la pantalla de FrmRolEsc
//                this.txtName.setEditable(false);
//                llenarControles(pPublication);// llamar el metodo llenarControles para llenar los controles del formulario con los datos de la clase usuario
//                break;
//            default:
//                break;
        }
    }
     
    private void enviarDatos() {
        try {
            if (validarDatos()) {
                if (obtenerMensajeDeConfirmacion() == JOptionPane.YES_OPTION) {
                    llenarEntidadConLosDatosDeLosControles();
                    int resultado = 0;
                    switch (opcionForm) {
                        case FormEscOpcion.CREAR:
                            // si la propiedad opcionForm es CREAR guardar esos datos en la base de datos
                            resultado = UserDAL.crear(this.publicationActual);
                            break;
                        case FormEscOpcion.MODIFICAR:
                            // si la propiedad opcionForm es MODIFICAR actualizar esos datos en la base de datos
                            //resultado = UserDAL.modificar(this.publicationActual);
                            break;
                        case FormEscOpcion.ELIMINAR:
                            // si la propiedad opcionForm es ELIMINAR entonces quitamos ese registro de la base de datos
                            //resultado = UserDAL.eliminar(this.publicationActual);
                            break;
                        default:
                            break;
                    }
                    if (resultado != 0) {
                        JOptionPane.showMessageDialog(this, "Los datos fueron correctamente actualizados");
                        if (frmPadre != null) {
                            // limpiar los datos de la tabla de datos del formulario FrmPublicationEsc
                            frmPadre.iniciarDatosDeLaTabla(new ArrayList());
                            //frmPadre.limpiarControles();
                        }
                        this.cerrarFormulario(false); // Cerrar el formulario utilizando el metodo "cerrarFormulario"
                    } else {
                        JOptionPane.showMessageDialog(this, "Sucedio un error al momento de actualizar los datos");
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Sucedio el siguiente error: " + ex.getMessage());
        }

    }
    
    
     public FrmUserEsc(User pPublication, int pOpcionForm, FrmUserLec pFrmPadre) {
        initComponents();
        iniciarDatos(pPublication, pOpcionForm, pFrmPadre);
    }
    /**
     * Creates new form FrmUserEsc
     */
    public FrmUserEsc() {
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnInputImg1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        lblSelectedImage1 = new javax.swing.JLabel();
        btnOk = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Nombre");

        jLabel2.setText("Email");

        jLabel3.setText("Contraseña");

        jLabel4.setText("Imagen 1");

        btnInputImg1.setText("Selecciona la imagen 1");

        jLabel5.setText("Registro");

        btnOk.setText("Publicar");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                    .addComponent(txtName)
                    .addComponent(txtPassword))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel4))
                    .addComponent(btnInputImg1)
                    .addComponent(lblSelectedImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
            .addGroup(layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(btnOk)
                .addGap(56, 56, 56)
                .addComponent(btnCancelar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(182, 182, 182)
                .addComponent(jLabel5)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnInputImg1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblSelectedImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk)
                    .addComponent(btnCancelar))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        // TODO add your handling code here:
        this.enviarDatos();
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.cerrarFormulario(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(FrmUserEsc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmUserEsc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmUserEsc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmUserEsc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmUserEsc().setVisible(true);
            }
        });
    }

    
    
    private void addExtraButtons() {
        btnInputImg1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectImage(btnInputImg1);
            }
        });

        
        
    }
    
    private void selectImage(JButton button) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar imagen");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "png"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            // Mostrar la imagen seleccionada en la etiqueta correspondiente
            if (button == btnInputImg1) {
                ImageIcon imageIcon = new ImageIcon(filePath);
                Image image = imageIcon.getImage().getScaledInstance(lblSelectedImage1.getWidth(), lblSelectedImage1.getHeight(), Image.SCALE_DEFAULT);
                lblSelectedImage1.setIcon(new ImageIcon(image));
            }
        }
        
        
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnInputImg1;
    private javax.swing.JButton btnOk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblSelectedImage1;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPassword;
    // End of variables declaration//GEN-END:variables
}
