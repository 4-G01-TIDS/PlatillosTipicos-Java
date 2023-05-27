package platillostipicos.appdesktop.Publication;

import java.util.ArrayList;
import java.util.UUID;
import javax.swing.JOptionPane;
import platillostipicos.appdesktop.utils.*;
import platillostipicos.entidadesdenegocio.*;
import platillostipicos.accesoadatos.PublicationDAL;

public class FrmPublicationEsc extends javax.swing.JFrame {

    private UUID idUser = UUID.fromString("23366d79-f322-4448-9e55-1518fc0c34b0");
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
                        // notificar al usuario que "Los datos fueron correctamente actualizados"
                        JOptionPane.showMessageDialog(this, "Los datos fueron correctamente actualizados");
                        if (frmPadre != null) {
                            // limpiar los datos de la tabla de datos del formulario FrmPublicationEsc
                            frmPadre.iniciarDatosDeLaTabla(new ArrayList());
                            frmPadre.buscar();
                        }
                        this.cerrarFormulario(false); // Cerrar el formulario utilizando el metodo "cerrarFormulario"
                    } else {
                        // En el caso que las filas modificadas en la base de datos sean cero 
                        // mostrar el siguiente mensaje al usuario "Sucedio un error al momento de actualizar los datos"
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
        iniciarDatos(pPublication, pOpcionForm, pFrmPadre); // Iniciar y obtener los datos de la base de datos para llenar los controles de este formulario
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(lbRestaurant))
                            .addComponent(cbRestaurants, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnOk)
                .addGap(129, 129, 129)
                .addComponent(btnCancelar)
                .addGap(0, 414, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbRestaurant)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbRestaurants, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk)
                    .addComponent(btnCancelar))
                .addContainerGap(130, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.cerrarFormulario(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        // TODO add your handling code here:
        this.enviarDatos();
    }//GEN-LAST:event_btnOkActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnOk;
    private javax.swing.JComboBox<ItemsCombo> cbRestaurants;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbRestaurant;
    private javax.swing.JTextArea txtComentario;
    // End of variables declaration//GEN-END:variables
}
