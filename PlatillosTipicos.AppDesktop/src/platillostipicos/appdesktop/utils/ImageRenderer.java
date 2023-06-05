package platillostipicos.appdesktop.utils;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ImageRenderer extends DefaultTableCellRenderer {

    private int cellHeight; // Altura de la celda de la tabla
    private int tableWidth; // Ancho de la tabla

    public ImageRenderer(int cellHeight, int tableWidth) {
        this.cellHeight = cellHeight;
        this.tableWidth = tableWidth;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof byte[]) {
            // Convertir el array de bytes en una imagen
            byte[] imageBytes = (byte[]) value;
            ImageIcon imageIcon = new ImageIcon(imageBytes);
            Image image = imageIcon.getImage();

            // Escalar la imagen al tamaño deseado manteniendo la relación de aspecto
            int scaledWidth = tableWidth;
            int scaledHeight = cellHeight;
            Image scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

            // Crear un JLabel con la imagen escalada
            JLabel label = new JLabel(new ImageIcon(scaledImage));

            // Ajustar la altura de la celda de la tabla para que se ajuste a la altura de la imagen
            table.setRowHeight(row, cellHeight);

            // Establecer los estilos de la celda según sea necesario
            if (isSelected) {
                label.setOpaque(true);
                label.setBackground(table.getSelectionBackground());
                label.setForeground(table.getSelectionForeground());
            } else {
                label.setOpaque(false);
            }

            // Agregar el mouse listener al JLabel de la imagen
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Handle the click event on the image cell
                    // Implement your logic here to expand the image to full screen
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    // Cambiar el cursor del mouse cuando se pasa sobre la imagen
                    label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // Restaurar el cursor del mouse cuando sale de la imagen
                    label.setCursor(Cursor.getDefaultCursor());
                }
            });

            return label;
        }

        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
    
    
    
    
    public static void configurarColumnaImagen(javax.swing.JTable table,  int columna, byte[] imageBytes, int row, DefaultTableModel model) {
        if (imageBytes != null) {
            int cellHeight = 50; // Ajusta la altura según tus necesidades
            int tableWidth = 50; // Obtener el ancho actual de la tabla
            //int tableWidth = tbPublication.getWidth(); // Obtener el ancho actual de la tabla

            ImageUtils imageRenderer = new ImageUtils(cellHeight, tableWidth);

            table.getColumnModel().getColumn(columna).setCellRenderer(imageRenderer);
            model.setValueAt(imageBytes, row, columna);
        }
    }
}
