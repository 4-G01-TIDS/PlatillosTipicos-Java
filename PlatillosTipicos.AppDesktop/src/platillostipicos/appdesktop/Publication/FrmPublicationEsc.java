package platillostipicos.appdesktop.Publication;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
import javax.swing.ImageIcon;
import platillostipicos.appdesktop.utils.*;
import platillostipicos.entidadesdenegocio.*;
import platillostipicos.accesoadatos.PublicationDAL;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FrmPublicationEsc extends javax.swing.JFrame {

//    private Image selectedImage1;
    private UUID idUser = UUID.fromString("cdab3e61-1160-403d-bd1e-ccca91b7c6aa");
    private FrmPublicationLec frmPadre;
    private int opcionForm;
    private Publication publicationActual;

    private void llenarControles(Publication pPublication) {
        try {
            publicationActual = PublicationDAL.GetById(pPublication);
            this.txtComentario.setText(publicationActual.getDescription());
            String restaurantIdValue = publicationActual.getRestaurantId().toString();

// Recorrer los elementos del JComboBox para buscar el objeto ItemsCombo con el valor deseado
            for (int i = 0; i < cbRestaurants.getItemCount(); i++) {
                ItemsCombo item = cbRestaurants.getItemAt(i);
                if (item.getValue().equals(restaurantIdValue)) {
                    cbRestaurants.setSelectedItem(item);
                    break; // Se encontró el elemento, salir del bucle
                }
            }

        } catch (Exception ex) {
            // Enviar el mensaje al usuario de la pantalla en el caso que suceda un error al obtener los datos de la base de datos
            JOptionPane.showMessageDialog(frmPadre, "Sucedio el siguiente error: " + ex.getMessage());
        }
    }

    private void iniciarDatos(Publication pPublication, int pOpcionForm, FrmPublicationLec pFrmPadre) {
        addExtraButtons();
        frmPadre = pFrmPadre;
        publicationActual = new Publication();
        opcionForm = pOpcionForm;
        this.frmPadre.iniciarComboRestaurant(cbRestaurants, pFrmPadre);
        this.txtComentario.setEditable(true);
        switch (pOpcionForm) {
            case FormEscOpcion.CREAR:
                this.btnOk.setMnemonic('N'); // modificar la tecla de atajo del boton btnOk a la letra N
                this.setTitle("Crear un nueva publication");
                break;
            case FormEscOpcion.MODIFICAR:
                btnOk.setText("Confirmar");
                this.btnOk.setMnemonic('M'); // modificar la tecla de atajo del boton btnOk a la letra M
                this.setTitle("Editar Publicacion");
                this.lbRestaurant.setVisible(false);
                this.cbRestaurants.setVisible(false);
                llenarControles(pPublication);
                break;
            case FormEscOpcion.ELIMINAR:
                btnOk.setText("Eliminar");
                this.btnOk.setMnemonic('E');
                this.setTitle("Eliminar el Publicacion");
                this.txtComentario.setEditable(false);
                this.cbRestaurants.setEnabled(false);
                llenarControles(pPublication);
                break;
            case FormEscOpcion.VER:
                btnOk.setText("Ver"); // modificar el texto del boton btnOk a "Ver" cuando la pOpcionForm sea VER
                btnOk.setVisible(false); // ocultar el boton btnOk cuando pOpcionForm sea opcion VER
                this.setTitle("Ver el Publicacion"); // modificar el titulo de la pantalla de FrmRolEsc
                this.txtComentario.setEditable(false);
                this.cbRestaurants.setEnabled(false);
                llenarControles(pPublication);// llamar el metodo llenarControles para llenar los controles del formulario con los datos de la clase usuario
                break;
            default:
                break;
        }
    }

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

    private void llenarEntidadConLosDatosDeLosControles() {
        this.publicationActual.setDescription(this.txtComentario.getText());
        ItemsCombo itemPublication = (ItemsCombo) cbRestaurants.getSelectedItem();
        this.publicationActual.setUserId(idUser);
        this.publicationActual.setRestaurantId(UUID.fromString(itemPublication.getValue()));

        PublicationImages images = new PublicationImages();

// Obtener los datos de imagen para lblSelectedImage1
        byte[] imageData1 = ImageUtils.getImageDataFromLabel(lblSelectedImage1);
        images.setImagePublication1(imageData1);

// Obtener los datos de imagen para lblSelectedImage2
        byte[] imageData2 = ImageUtils.getImageDataFromLabel(lblSelectedImage2);
        images.setImagePublication2(imageData2);

// Obtener los datos de imagen para lblSelectedImage3
        byte[] imageData3 = ImageUtils.getImageDataFromLabel(lblSelectedImage3);
        images.setImagePublication3(imageData3);

// Obtener los datos de imagen para lblSelectedImage4
        byte[] imageData4 = ImageUtils.getImageDataFromLabel(lblSelectedImage4);
        images.setImagePublication4(imageData4);

// Obtener los datos de imagen para lblSelectedImage5
        byte[] imageData5 = ImageUtils.getImageDataFromLabel(lblSelectedImage5);
        images.setImagePublication5(imageData5);

        if (imageData1 != null || imageData2 != null || imageData3 != null || imageData4 != null || imageData5 != null) {
            UUID idImagesPublication = UUID.randomUUID();
            images.setId(idImagesPublication);
        }
        this.publicationActual.setPublicationImagesId(images.getId());

        this.publicationActual.setPublicationImages(images);

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

    private boolean validarDatos() {
        boolean result = true;
        ItemsCombo itemRestaurant = (ItemsCombo) cbRestaurants.getSelectedItem();
        if (itemRestaurant.getValue().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona el restaurante");
            result = false;
        }
        if (idUser == null) {
            JOptionPane.showMessageDialog(this, "Id de usuario es requerido");
            result = false;
        }
        return result; // retorna la variable result con el valor true o false para saber si los datos son validos o no
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
                            resultado = PublicationDAL.crear(this.publicationActual);
                            break;
                        case FormEscOpcion.MODIFICAR:
                            // si la propiedad opcionForm es MODIFICAR actualizar esos datos en la base de datos
                            resultado = PublicationDAL.modificar(this.publicationActual);
                            break;
                        case FormEscOpcion.ELIMINAR:
                            // si la propiedad opcionForm es ELIMINAR entonces quitamos ese registro de la base de datos
                            resultado = PublicationDAL.eliminar(this.publicationActual);
                            break;
                        default:
                            break;
                    }
                    if (resultado != 0) {
                        JOptionPane.showMessageDialog(this, "Los datos fueron correctamente actualizados");
                        if (frmPadre != null) {
                            // limpiar los datos de la tabla de datos del formulario FrmPublicationEsc
                            frmPadre.iniciarDatosDeLaTabla(new ArrayList());
                            frmPadre.limpiarControles();
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

    public FrmPublicationEsc(Publication pPublication, int pOpcionForm, FrmPublicationLec pFrmPadre) {
        initComponents();
        iniciarDatos(pPublication, pOpcionForm, pFrmPadre);
    }

    /**
     * Creates new form Create
     */
    public FrmPublicationEsc() {
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
        lbRestaurant = new javax.swing.JLabel();
        cbRestaurants = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtComentario = new javax.swing.JTextArea();
        btnOk = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnInputImg1 = new javax.swing.JButton();
        btnInputImg2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnInputImg3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnInputImg4 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btnInputImg5 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        lblSelectedImage1 = new javax.swing.JLabel();
        lblSelectedImage2 = new javax.swing.JLabel();
        lblSelectedImage3 = new javax.swing.JLabel();
        lblSelectedImage4 = new javax.swing.JLabel();
        lblSelectedImage5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Comentario");

        lbRestaurant.setText("Selecciona el restaurante");

        txtComentario.setColumns(20);
        txtComentario.setRows(5);
        jScrollPane1.setViewportView(txtComentario);

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

        jLabel2.setText("Imagen 1");

        btnInputImg1.setText("Selecciona la imagen 1");

        btnInputImg2.setText("Selecciona la imagen 2");

        jLabel3.setText("Imagen 2");

        btnInputImg3.setText("Selecciona la imagen 3");
        btnInputImg3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInputImg3ActionPerformed(evt);
            }
        });

        jLabel4.setText("Imagen 3");

        btnInputImg4.setText("Selecciona la imagen 4");

        jLabel5.setText("Imagen 4");

        btnInputImg5.setText("Selecciona la imagen 5");

        jLabel6.setText("Imagen 5");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbRestaurants, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(lbRestaurant)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(62, 62, 62)
                                        .addComponent(jLabel2))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(46, 46, 46)
                                        .addComponent(btnInputImg1))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblSelectedImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 73, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(123, 123, 123))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(btnInputImg2)
                                                .addGap(67, 67, 67))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(73, 73, 73)
                                        .addComponent(lblSelectedImage2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4))
                                    .addComponent(btnInputImg3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblSelectedImage3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnInputImg4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5))
                                    .addComponent(lblSelectedImage4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel6)
                                        .addGap(171, 171, 171))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(175, 175, 175)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(btnInputImg5)
                                            .addComponent(lblSelectedImage5, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE)))))))
                .addGap(90, 90, 90))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnOk)
                .addGap(56, 56, 56)
                .addComponent(btnCancelar)
                .addGap(499, 499, 499))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnInputImg1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnInputImg2))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnInputImg3)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSelectedImage2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblSelectedImage3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lbRestaurant)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbRestaurants, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblSelectedImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel6))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnInputImg4)
                                    .addComponent(btnInputImg5)))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSelectedImage4, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSelectedImage5, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(143, 143, 143)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addExtraButtons() {
        btnInputImg1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectImage(btnInputImg1);
            }
        });

        btnInputImg2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectImage(btnInputImg2);
            }
        });

        btnInputImg3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectImage(btnInputImg3);
            }
        });

        btnInputImg4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectImage(btnInputImg4);
            }
        });

        btnInputImg5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectImage(btnInputImg5);
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
            } else if (button == btnInputImg2) {
                ImageIcon imageIcon = new ImageIcon(filePath);
                Image image = imageIcon.getImage().getScaledInstance(lblSelectedImage2.getWidth(), lblSelectedImage2.getHeight(), Image.SCALE_DEFAULT);
                lblSelectedImage2.setIcon(new ImageIcon(image));
            } else if (button == btnInputImg3) {
                ImageIcon imageIcon = new ImageIcon(filePath);
                Image image = imageIcon.getImage().getScaledInstance(lblSelectedImage3.getWidth(), lblSelectedImage3.getHeight(), Image.SCALE_DEFAULT);
                lblSelectedImage3.setIcon(new ImageIcon(image));
            } else if (button == btnInputImg4) {
                ImageIcon imageIcon = new ImageIcon(filePath);
                Image image = imageIcon.getImage().getScaledInstance(lblSelectedImage4.getWidth(), lblSelectedImage4.getHeight(), Image.SCALE_DEFAULT);
                lblSelectedImage4.setIcon(new ImageIcon(image));
            } else if (button == btnInputImg5) {
                ImageIcon imageIcon = new ImageIcon(filePath);
                Image image = imageIcon.getImage().getScaledInstance(lblSelectedImage5.getWidth(), lblSelectedImage5.getHeight(), Image.SCALE_DEFAULT);
                lblSelectedImage5.setIcon(new ImageIcon(image));
            }
        }
    }


    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.cerrarFormulario(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        // TODO add your handling code here:
        this.enviarDatos();
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnInputImg3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInputImg3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnInputImg3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnInputImg1;
    private javax.swing.JButton btnInputImg2;
    private javax.swing.JButton btnInputImg3;
    private javax.swing.JButton btnInputImg4;
    private javax.swing.JButton btnInputImg5;
    private javax.swing.JButton btnOk;
    private javax.swing.JComboBox<ItemsCombo> cbRestaurants;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbRestaurant;
    private javax.swing.JLabel lblSelectedImage1;
    private javax.swing.JLabel lblSelectedImage2;
    private javax.swing.JLabel lblSelectedImage3;
    private javax.swing.JLabel lblSelectedImage4;
    private javax.swing.JLabel lblSelectedImage5;
    private javax.swing.JTextArea txtComentario;
    // End of variables declaration//GEN-END:variables
}
