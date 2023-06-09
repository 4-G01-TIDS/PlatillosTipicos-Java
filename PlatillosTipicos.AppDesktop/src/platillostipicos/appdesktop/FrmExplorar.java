/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package platillostipicos.appdesktop;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import platillostipicos.accesoadatos.PublicationDAL;
import platillostipicos.appdesktop.utils.*;
import platillostipicos.entidadesdenegocio.Publication;

public class FrmExplorar extends javax.swing.JFrame {

    // <editor-fold defaultstate="collapsed" desc="Codigo para las clases,propiedades y metodos locales del formulario FrmExplorar">
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

    }

    private void ocultarColumnasDeLaTabla(int pColumna) {
        this.tbPublication.getColumnModel().getColumn(pColumna).setMaxWidth(0); // le dejamos en el ancho maximo de la tabla en cero en la columna
        this.tbPublication.getColumnModel().getColumn(pColumna).setMinWidth(0);// le dejamos en el ancho minimo de la tabla en cero  en la columna
        // le dejamos en el ancho maximo de la tabla en cero en el header
        this.tbPublication.getTableHeader().getColumnModel().getColumn(pColumna).setMaxWidth(0);
        // le dejamos en el ancho minimo de la tabla en cero  en el header
        this.tbPublication.getTableHeader().getColumnModel().getColumn(pColumna).setMinWidth(0);
    }

    private void publications() {
        try {
            Publication publication = new Publication();
            ArrayList<Publication> publications = PublicationDAL.getPublications(publication);
            iniciarDatosDeLaTabla(publications);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Sucedio el siguiente error: " + ex.getMessage());
        }
    }

    public void iniciarDatosDeLaTabla(ArrayList<Publication> pPublications) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("Id");
        model.addColumn("Descripcion");
        model.addColumn("Fecha de publicacion");
        model.addColumn("UserId");
        model.addColumn("PublicationImagesId");
        model.addColumn("RestaurantId");
        model.addColumn("Imagen1");
        model.addColumn("Imagen2");
        model.addColumn("Imagen3");
        model.addColumn("Imagen4");
        model.addColumn("Imagen5");

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

            configurarColumnaImagen(ColumnaTabla.IMAGE1, imageBytes1, i, model);
            configurarColumnaImagen(ColumnaTabla.IMAGE2, imageBytes2, i, model);
            configurarColumnaImagen(ColumnaTabla.IMAGE3, imageBytes3, i, model);
            configurarColumnaImagen(ColumnaTabla.IMAGE4, imageBytes4, i, model);
            configurarColumnaImagen(ColumnaTabla.IMAGE5, imageBytes5, i, model);
        }

        tbPublication.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = tbPublication.columnAtPoint(e.getPoint());

                // Verificar si se hizo clic en una columna de imagen (IMAGE1, IMAGE2, IMAGE3, etc.)
                if (column >= ColumnaTabla.IMAGE1 && column <= ColumnaTabla.IMAGE5) {
                    int row = tbPublication.rowAtPoint(e.getPoint());
                    byte[] imageBytes = (byte[]) tbPublication.getValueAt(row, column);

                    // Verificar si el valor de imageBytes es null
                    if (imageBytes != null) {
                        ImageIcon imageIcon = new ImageIcon(imageBytes);
                        JLabel imageLabel = new JLabel(imageIcon);
                        JOptionPane.showMessageDialog(null, imageLabel, "Imagen", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        });

        ocultarColumnasDeLaTabla(ColumnaTabla.ID);
        ocultarColumnasDeLaTabla(ColumnaTabla.USERID);
        ocultarColumnasDeLaTabla(ColumnaTabla.PUBLICATIONIMAGESID);
        ocultarColumnasDeLaTabla(ColumnaTabla.RESTAURANTID);
    }

    private void configurarColumnaImagen(int columna, byte[] imageBytes, int row, DefaultTableModel model) {
        if (imageBytes != null) {
            int cellHeight = 200; // Ajusta la altura según tus necesidades
            int tableWidth = 200; // Obtener el ancho actual de la tabla
          //int tableWidth = tbPublication.getWidth(); // Obtener el ancho actual de la tabla

            ImageRenderer imageRenderer = new ImageRenderer(cellHeight, tableWidth);

            tbPublication.getColumnModel().getColumn(columna).setCellRenderer(imageRenderer);
            model.setValueAt(imageBytes, row, columna);
        }
    }

    public FrmExplorar(javax.swing.JFrame pFrmPadre) {

        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        publications();
    }
// </editor-fold> 

    /**
     * Creates new form FrmExplorar
     */
    public FrmExplorar() {
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPublication = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        meInicio = new javax.swing.JMenu();

        jMenu1.setText("jMenu1");

        jMenu2.setText("jMenu2");

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

        meInicio.setText("Logo");
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
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 199, Short.MAX_VALUE)
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenu meInicio;
    private javax.swing.JTable tbPublication;
    // End of variables declaration//GEN-END:variables
}
