package platillostipicos.appdesktop.Publication;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import platillostipicos.accesoadatos.*;
import platillostipicos.appdesktop.*;
import platillostipicos.appdesktop.utils.*;
import platillostipicos.entidadesdenegocio.*;

public final class FrmPublicationLec extends javax.swing.JFrame {

    private UUID idUser = UUID.fromString("cdab3e61-1160-403d-bd1e-ccca91b7c6aa");

    private javax.swing.JFrame frmPadre; // Propiedad para almacenar la pantalla de Inicio del sistema

    // <editor-fold defaultstate="collapsed" desc="Codigo para las clases,propiedades y metodos locales del formulario FrmPublicationLec">
    // Crear la clase anidada ColumnaTabla para saber la posicion de las columnas en la tabla de datos
    public class ColumnaTabla {

        static final int ID = 0;
        static final int DESCRIPTION = 1;
        static final int PUBLICATIONDATE = 2;
        static final int USERID = 3;
        static final int PUBLICATIONIMAGESID = 4;
        static final int RESTAURANTID = 5;
        static final int IMAGE1 = 6;
        static final int IMAGE2 = 7;
        static final int IMAGE3 = 8;
        static final int IMAGE4 = 9;
        static final int IMAGE5 = 10;
        static final int COMMENT = 11;

    }

    private void ocultarColumnasDeLaTabla(int pColumna) {
        this.tbPublication.getColumnModel().getColumn(pColumna).setMaxWidth(0); // le dejamos en el ancho maximo de la tabla en cero en la columna
        this.tbPublication.getColumnModel().getColumn(pColumna).setMinWidth(0);// le dejamos en el ancho minimo de la tabla en cero  en la columna
        // le dejamos en el ancho maximo de la tabla en cero en el header
        this.tbPublication.getTableHeader().getColumnModel().getColumn(pColumna).setMaxWidth(0);
        // le dejamos en el ancho minimo de la tabla en cero  en el header
        this.tbPublication.getTableHeader().getColumnModel().getColumn(pColumna).setMinWidth(0);
    }

    public void limpiarControles() {
        this.cbRestaurants.setSelectedItem(new ItemsCombo(0, null, null));
        this.cbUsers.setSelectedItem(new ItemsCombo(0, null, null));
        buscar();
    }

    private void buscar() {
        try {
            Publication publicationSearch = new Publication();
            ItemsCombo itemsCbPublications = (ItemsCombo) cbRestaurants.getSelectedItem();
            boolean cbRestaurant = itemsCbPublications.getValue().trim().isEmpty();
            publicationSearch.setRestaurantId(!cbRestaurant ? UUID.fromString(itemsCbPublications.getValue()) : null);
            //////////////////////////////////////////////////
            ItemsCombo itemsCbUsuarios = (ItemsCombo) cbUsers.getSelectedItem();
            boolean cbUsuarios = itemsCbUsuarios.getValue().trim().isEmpty();
            publicationSearch.setUserId(!cbUsuarios ? UUID.fromString(itemsCbUsuarios.getValue()) : null);

            ArrayList<Publication> publications = PublicationDAL.getPublications(publicationSearch);

            iniciarDatosDeLaTabla(publications);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Sucedio el siguiente error: " + ex.getMessage());
        }
    }

    public void iniciarDatosDeLaTabla(ArrayList<Publication> pPublications) throws Exception {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("Id");
        model.addColumn("Descripción");
        model.addColumn("Fecha de publicación");
        model.addColumn("UserId");
        model.addColumn("PublicationImagesId");
        model.addColumn("RestaurantId");
        model.addColumn("Imagen1");
        model.addColumn("Imagen2");
        model.addColumn("Imagen3");
        model.addColumn("Imagen4");
        model.addColumn("Imagen5");
        model.addColumn("Comentarios");
//        model.addColumn("Like");

        this.tbPublication.setModel(model);
        Object row[] = null;

        for (int i = 0; i < pPublications.size(); i++) {
            Publication publication = pPublications.get(i);
            model.addRow(row);
            model.setValueAt(publication.getId(), i, ColumnaTabla.ID);
            model.setValueAt(publication.getDescription(), i, ColumnaTabla.DESCRIPTION);
            model.setValueAt(publication.getPublicationDate().toString(), i, ColumnaTabla.PUBLICATIONDATE);
            model.setValueAt(publication.getUserId(), i, ColumnaTabla.USERID);
            model.setValueAt(publication.getPublicationImagesId(), i, ColumnaTabla.PUBLICATIONIMAGESID);
            model.setValueAt(publication.getRestaurantId(), i, ColumnaTabla.RESTAURANTID);
            byte[] imageBytes1 = publication.getPublicationImages().getImagePublication1();
            byte[] imageBytes2 = publication.getPublicationImages().getImagePublication2();
            byte[] imageBytes3 = publication.getPublicationImages().getImagePublication3();
            byte[] imageBytes4 = publication.getPublicationImages().getImagePublication4();
            byte[] imageBytes5 = publication.getPublicationImages().getImagePublication5();
            ImageRenderer.configurarColumnaImagen(tbPublication, ColumnaTabla.IMAGE1, imageBytes1, i, model);
            ImageRenderer.configurarColumnaImagen(tbPublication, ColumnaTabla.IMAGE2, imageBytes2, i, model);
            ImageRenderer.configurarColumnaImagen(tbPublication, ColumnaTabla.IMAGE3, imageBytes3, i, model);
            ImageRenderer.configurarColumnaImagen(tbPublication, ColumnaTabla.IMAGE4, imageBytes4, i, model);
            ImageRenderer.configurarColumnaImagen(tbPublication, ColumnaTabla.IMAGE5, imageBytes5, i, model);
            model.setValueAt("Opinar", i, ColumnaTabla.COMMENT);
        }

        tbPublication.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                int column = tbPublication.columnAtPoint(event.getPoint());

                // Verificar si se hizo clic en una columna de imagen (IMAGE1, IMAGE2, IMAGE3, etc.)
                if (column >= ColumnaTabla.IMAGE1 && column <= ColumnaTabla.IMAGE5) {
                    int row = tbPublication.rowAtPoint(event.getPoint());
                    byte[] imageBytes = (byte[]) tbPublication.getValueAt(row, column);

                    // Verificar si el valor de imageBytes es null
                    if (imageBytes != null) {
                        ImageIcon imageIcon = new ImageIcon(imageBytes);
                        JLabel imageLabel = new JLabel(imageIcon);
                        JOptionPane.showMessageDialog(null, imageLabel, "Imagen", JOptionPane.PLAIN_MESSAGE);
                    }
                } else if (column == ColumnaTabla.COMMENT) {
                    try {
                        int row = tbPublication.rowAtPoint(event.getPoint());
                        Publication selectedPublication = pPublications.get(row);
                        ArrayList<Comment> comments = CommentDAL.getByPublicationId(selectedPublication.getId().toString());

                        JPanel panel = new JPanel();
                        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                        for (Comment comment : comments) {
                            JPanel commentPanel = new JPanel(new BorderLayout());
                            Comment selectedComment = comment;
                            CommentLike commentLike = new CommentLike();
                            commentLike.setUserId(idUser);
                            commentLike.setCommentId(selectedComment.getId());

                            CommentLike isLike = CommentLikeDAL.getByPublicationId(commentLike);
                            boolean votedIsLike = false;
                            boolean votedIsDislike = false;

                            if (isLike != null) {
                                votedIsLike = (isLike.getisIsLike() && isLike.getUserId().equals(idUser));
                                votedIsDislike = (!isLike.getisIsLike() && isLike.getUserId().equals(idUser));
                            }

                            JLabel commentLabel = new JLabel(comment.getContent());
                            commentPanel.add(commentLabel, BorderLayout.CENTER);

                            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

                            JButton thumbsUpButton = new JButton();
                            PublicationUtils.setThumbsUpButtonIcon(thumbsUpButton, votedIsLike);
                            thumbsUpButton.setPreferredSize(new Dimension(16, 16));
                            thumbsUpButton.setBorderPainted(false);
                            thumbsUpButton.setOpaque(false);
                            thumbsUpButton.setContentAreaFilled(false);
                            thumbsUpButton.putClientProperty("voted", true);
                            thumbsUpButton.addActionListener(e -> handleThumbs((JButton) e.getSource(), selectedComment));

                            buttonPanel.add(thumbsUpButton);

                            JButton thumbsDownButton = new JButton();
                            PublicationUtils.setThumbsDownButtonIcon(thumbsDownButton, votedIsDislike);
                            thumbsDownButton.setPreferredSize(new Dimension(16, 16));
                            thumbsDownButton.setBorderPainted(false);
                            thumbsDownButton.setOpaque(false);
                            thumbsDownButton.setContentAreaFilled(false);
                            thumbsDownButton.putClientProperty("voted", false);
                            thumbsDownButton.addActionListener(e -> handleThumbs((JButton) e.getSource(), selectedComment));

                            buttonPanel.add(thumbsDownButton);

                            commentPanel.add(buttonPanel, BorderLayout.EAST);

                            panel.add(commentPanel);
                        }

                        JTextArea commentTextArea = new JTextArea();
                        commentTextArea.setLineWrap(true);
                        commentTextArea.setWrapStyleWord(true);
                        FormUtils.setPlaceholderText(commentTextArea, "Escribe tu comentario aquí"); // Establecer el texto del marcador de posición

                        panel.add(new JLabel("Nuevo Comentario:"));
                        panel.add(commentTextArea);

                        JScrollPane scrollPane = new JScrollPane(panel);
                        scrollPane.setPreferredSize(new Dimension(400, 300)); // Establecer el tamaño preferido del JScrollPane

                        PublicationLike publicationLike = new PublicationLike();
                        publicationLike.setUserId(idUser);
                        publicationLike.setPublicationId(selectedPublication.getId());
                        PublicationLike isLikePublication = PublicationLikeDAL.getLikeByPublicationAndUser(publicationLike);
                        boolean votedIsLike = false;
                        boolean votedIsDislike = false;

                        if (isLikePublication != null) {
                            votedIsLike = (isLikePublication.getIsIsLike() && isLikePublication.getUserId().equals(idUser));
                            votedIsDislike = (!isLikePublication.getIsIsLike() && isLikePublication.getUserId().equals(idUser));
                        }

                        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

                        JButton thumbsUpPublication = new JButton();
                        PublicationUtils.setThumbsUpButtonIcon(thumbsUpPublication, votedIsLike);
                        thumbsUpPublication.setPreferredSize(new Dimension(16, 16));
                        thumbsUpPublication.setBorderPainted(false);
                        thumbsUpPublication.setOpaque(false);
                        thumbsUpPublication.setContentAreaFilled(false);
                        thumbsUpPublication.putClientProperty("voted", true);
                        thumbsUpPublication.addActionListener(e -> handleThumbsPublications((JButton) e.getSource(), selectedPublication));

                        buttonPanel.add(thumbsUpPublication);

                        JButton thumbsDownButton = new JButton();
                        PublicationUtils.setThumbsDownButtonIcon(thumbsDownButton, votedIsDislike);
                        thumbsDownButton.setPreferredSize(new Dimension(16, 16));
                        thumbsDownButton.setBorderPainted(false);
                        thumbsDownButton.setOpaque(false);
                        thumbsDownButton.setContentAreaFilled(false);
                        thumbsDownButton.putClientProperty("voted", false);
                        thumbsDownButton.addActionListener(e -> handleThumbsPublications((JButton) e.getSource(), selectedPublication));

                        buttonPanel.add(thumbsDownButton);

                        Object[] options = {"Enviar comentario", "Cancelar"};
                        Object[] message = {scrollPane, buttonPanel};

                        int option = JOptionPane.showOptionDialog(null, message, "Comentarios",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                null, options, options[0]);

                        if (option == 0) {
                            String nuevoComentario = commentTextArea.getText();
                            String placeholder = "Escribe tu comentario aquí";

                            while (nuevoComentario.equals(placeholder)) {
                                JOptionPane.showMessageDialog(null, "Escribe un comentario", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                                option = JOptionPane.showOptionDialog(null, message, "Comentarios",
                                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                        null, new Object[]{"Enviar comentario", "Cancelar"}, "Enviar comentario");

                                if (option == 0) {
                                    nuevoComentario = commentTextArea.getText();
                                } else {
                                    break;
                                }
                            }

                            if (option == 0) {
                                Comment comment = new Comment();
                                comment.setContent(nuevoComentario);
                                comment.setUserId(idUser);
                                comment.setPublicationId(selectedPublication.getId()); // Establecer el ID de la publicación
                                CommentDAL.crear(comment);
                            }
                        } else if (option == 1) {
                            // Cancelar
                        }
                    } catch (Exception ex) {
                        // JOptionPane.showMessageDialog(null, "Error al obtener los comentarios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });

        ocultarColumnasDeLaTabla(ColumnaTabla.ID);
        ocultarColumnasDeLaTabla(ColumnaTabla.USERID);
        ocultarColumnasDeLaTabla(ColumnaTabla.PUBLICATIONIMAGESID);
        ocultarColumnasDeLaTabla(ColumnaTabla.RESTAURANTID);
    }

    private void handleThumbsPublications(JButton button, Publication selectedPublication) {
        boolean voted = (boolean) button.getClientProperty("voted");
        PublicationLike publicationLike = new PublicationLike();
        publicationLike.setUserId(idUser);
        publicationLike.setPublicationId(selectedPublication.getId());
        publicationLike.setIsLike(voted);

        try {
            PublicationLike existingLike = PublicationLikeDAL.getLikeByPublicationAndUser(publicationLike);
            boolean isExistingLike = existingLike != null;

            if (voted) {
                if (isExistingLike && existingLike.getIsIsLike()) {
                    // Si ya había seleccionado "like" y hace clic nuevamente en "like", se elimina la selección
                    PublicationLikeDAL.eliminar(publicationLike);
                    PublicationUtils.setThumbsUpButtonIcon(button, false);
                } else {
                    // Selecciona "like" y deselecciona "dislike"
                    PublicationUtils.setThumbsUpButtonIcon(button, true);
                    PublicationUtils.setThumbsDownButtonIcon(PublicationUtils.getThumbsDownButton(button.getParent()), false);

                    if (isExistingLike && !existingLike.getIsIsLike()) {
                        // Si ya había seleccionado "dislike", se actualiza a "like"
                        PublicationLikeDAL.update(publicationLike);
                    } else {
                        // Si no había ninguna selección anterior, se crea un nuevo "like"
                        PublicationLikeDAL.NewLikePublication(publicationLike);
                    }
                }
            } else {
                if (isExistingLike && !existingLike.getIsIsLike()) {
                    // Si ya había seleccionado "dislike" y hace clic nuevamente en "dislike", se elimina la selección
                    PublicationLikeDAL.eliminar(publicationLike);
                    PublicationUtils.setThumbsDownButtonIcon(button, false);
                } else {
                    // Selecciona "dislike" y deselecciona "like"
                    PublicationUtils.setThumbsUpButtonIcon(PublicationUtils.getThumbsUpButton(button.getParent()), false);
                    PublicationUtils.setThumbsDownButtonIcon(button, true);

                    if (isExistingLike && existingLike.getIsIsLike()) {
                        // Si ya había seleccionado "like", se actualiza a "dislike"
                        PublicationLikeDAL.update(publicationLike);
                    } else {
                        // Si no había ninguna selección anterior, se crea un nuevo "dislike"
                        PublicationLikeDAL.NewLikePublication(publicationLike);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(FrmPublicationLec.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

// Dentro del método handleThumbs
    public void handleThumbss(JButton button, Comment comment) {
        // Aquí se ejecuta el código para el manejo del like o dislike

        // Mostrar el mensaje de alerta
        String message = "Has dado " + " al comentario";
        JOptionPane.showMessageDialog(null, message, "Mensaje", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleThumbs(JButton button, Comment selectedComment) {
        boolean voted = (boolean) button.getClientProperty("voted");
        CommentLike commentLike = new CommentLike();
        commentLike.setUserId(idUser);
        commentLike.setCommentId(selectedComment.getId());
        commentLike.setIsLike(voted);

        try {
            CommentLike existingLike = CommentLikeDAL.getByPublicationId(commentLike);
            boolean isExistingLike = existingLike != null;

            if (voted) {
                if (isExistingLike && existingLike.getisIsLike()) {
                    // Si ya había seleccionado "like" y hace clic nuevamente en "like", se elimina la selección
                    CommentLikeDAL.eliminar(commentLike);
                    PublicationUtils.setThumbsUpButtonIcon(button, false);
                } else {
                    // Selecciona "like" y deselecciona "dislike"
                    PublicationUtils.setThumbsUpButtonIcon(button, true);
                    PublicationUtils.setThumbsDownButtonIcon(PublicationUtils.getThumbsDownButton(button.getParent()), false);

                    if (isExistingLike && !existingLike.getisIsLike()) {
                        // Si ya había seleccionado "dislike", se actualiza a "like"
                        CommentLikeDAL.update(commentLike);
                    } else {
                        // Si no había ninguna selección anterior, se crea un nuevo "like"
                        CommentLikeDAL.NewLike(commentLike);
                    }
                }
            } else {
                if (isExistingLike && !existingLike.getisIsLike()) {
                    // Si ya había seleccionado "dislike" y hace clic nuevamente en "dislike", se elimina la selección
                    CommentLikeDAL.eliminar(commentLike);
                    PublicationUtils.setThumbsDownButtonIcon(button, false);
                } else {
                    // Selecciona "dislike" y deselecciona "like"
                    PublicationUtils.setThumbsUpButtonIcon(PublicationUtils.getThumbsUpButton(button.getParent()), false);
                    PublicationUtils.setThumbsDownButtonIcon(button, true);

                    if (isExistingLike && existingLike.getisIsLike()) {
                        // Si ya había seleccionado "like", se actualiza a "dislike"
                        CommentLikeDAL.update(commentLike);
                    } else {
                        // Si no había ninguna selección anterior, se crea un nuevo "dislike"
                        CommentLikeDAL.NewLike(commentLike);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(FrmPublicationLec.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean llenarEntidadConLaFilaSeleccionadaDeLaTabla(Publication pPublication) {
        int filaSelect;
        boolean isSelectRow = false;
        filaSelect = this.tbPublication.getSelectedRow();
        if (filaSelect != -1) {
            isSelectRow = true;
            pPublication.setId((UUID) this.tbPublication.getValueAt(filaSelect, ColumnaTabla.ID));
            pPublication.setDescription((String) this.tbPublication.getValueAt(filaSelect, ColumnaTabla.DESCRIPTION));
        }
        return isSelectRow;
    }

    private void abrirFormularioDeEscritura(int pOpcionForm) {
        Publication publication = new Publication();
        if (pOpcionForm == FormEscOpcion.CREAR || this.llenarEntidadConLaFilaSeleccionadaDeLaTabla(publication)) {
            FrmPublicationEsc frmPublicationEsc = new FrmPublicationEsc(publication, pOpcionForm, this);
            frmPublicationEsc.setVisible(true);
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "No ha seleccionado ninguna fila.");
        }

    }

    public void iniciarComboRestaurant(javax.swing.JComboBox<ItemsCombo> pJComboBox, javax.swing.JFrame pFrame) {
        pJComboBox.addItem(new ItemsCombo(0, "SELECCIONAR", ""));
        pJComboBox.addItem(new ItemsCombo(1, "Restaurante", "807487cc-fc4d-11ed-be56-0242ac120002"));
        pJComboBox.addItem(new ItemsCombo(2, "Restaurante2", "c71c0a02-8f35-4b50-9fad-01b7d662ad0d"));

    }

    public void iniciarComboUsuarios(javax.swing.JComboBox<ItemsCombo> pJComboBox, javax.swing.JFrame pFrame) {
        pJComboBox.addItem(new ItemsCombo(0, "SELECCIONAR", ""));
        pJComboBox.addItem(new ItemsCombo(1, "Usuario", "23366d79-f322-4448-9e55-1518fc0c34b0"));
        pJComboBox.addItem(new ItemsCombo(2, "Usuario2", "cdab3e61-1160-403d-bd1e-ccca91b7c6aa"));

    }

    private void iniciarDatos(javax.swing.JFrame pFrmPadre) {
        frmPadre = pFrmPadre;
        pFrmPadre.setEnabled(true); // deshabilitar el formulario FrmInicio
        iniciarComboRestaurant(this.cbRestaurants, this.frmPadre);
        iniciarComboUsuarios(this.cbUsers, this.frmPadre);
        buscar();
    }

    public FrmPublicationLec(javax.swing.JFrame pFrmPadre) {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        iniciarDatos(pFrmPadre);
    }
// </editor-fold> 

    /**
     * Creates new form FrmExplorar
     */
    public FrmPublicationLec() {
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

        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        btnModificar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPublication = new javax.swing.JTable();
        btnNewPublication = new javax.swing.JButton();
        cbRestaurants = new javax.swing.JComboBox<>();
        btnBuscar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnModificar1 = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnVer = new javax.swing.JButton();
        cbUsers = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        btnLimpiar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        meInicio = new javax.swing.JMenu();

        jMenu1.setText("jMenu1");

        jMenu2.setText("jMenu2");

        btnModificar.setMnemonic('M');
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tbPublication.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tbPublication);

        btnNewPublication.setText("Crear Publicacion");
        btnNewPublication.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewPublicationActionPerformed(evt);
            }
        });

        btnBuscar.setMnemonic('B');
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jLabel2.setText("Restaurante");

        btnModificar1.setMnemonic('M');
        btnModificar1.setText("Modificar");
        btnModificar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificar1ActionPerformed(evt);
            }
        });

        btnEliminar.setMnemonic('E');
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnVer.setMnemonic('V');
        btnVer.setText("Ver");
        btnVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerActionPerformed(evt);
            }
        });

        jLabel1.setText("Usuario");

        btnLimpiar.setMnemonic('L');
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        meInicio.setText("INICIO");
        meInicio.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        meInicio.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                meInicioMenuSelected(evt);
            }
        });
        jMenuBar1.add(meInicio);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 851, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnNewPublication)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificar1)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addGap(18, 18, 18)
                        .addComponent(btnVer))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(19, 19, 19)
                                        .addComponent(jLabel1)))
                                .addGap(79, 79, 79)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(cbRestaurants, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnBuscar)
                                .addGap(58, 58, 58)
                                .addComponent(btnLimpiar)))))
                .addContainerGap(454, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbRestaurants, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscar)
                    .addComponent(btnLimpiar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewPublication)
                    .addComponent(btnModificar1)
                    .addComponent(btnEliminar)
                    .addComponent(btnVer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void meInicioMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_meInicioMenuSelected
        // TODO add your handling code here:
        FrmInicio frmInicio = new FrmInicio();
        frmInicio.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_meInicioMenuSelected

    private void btnNewPublicationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewPublicationActionPerformed
        // TODO add your handling code here:
        this.abrirFormularioDeEscritura(FormEscOpcion.CREAR);
    }//GEN-LAST:event_btnNewPublicationActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        this.buscar(); // llamar el metodo de buscar
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        this.abrirFormularioDeEscritura(FormEscOpcion.MODIFICAR);
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnModificar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificar1ActionPerformed
        // TODO add your handling code here:
        this.abrirFormularioDeEscritura(FormEscOpcion.MODIFICAR);
    }//GEN-LAST:event_btnModificar1ActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        this.abrirFormularioDeEscritura(FormEscOpcion.ELIMINAR);
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerActionPerformed
        // TODO add your handling code here:
        this.abrirFormularioDeEscritura(FormEscOpcion.VER);
    }//GEN-LAST:event_btnVerActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        this.limpiarControles();
    }//GEN-LAST:event_btnLimpiarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnModificar1;
    private javax.swing.JButton btnNewPublication;
    private javax.swing.JButton btnVer;
    private javax.swing.JComboBox<ItemsCombo> cbRestaurants;
    private javax.swing.JComboBox<ItemsCombo> cbUsers;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenu meInicio;
    private javax.swing.JTable tbPublication;
    // End of variables declaration//GEN-END:variables
}
