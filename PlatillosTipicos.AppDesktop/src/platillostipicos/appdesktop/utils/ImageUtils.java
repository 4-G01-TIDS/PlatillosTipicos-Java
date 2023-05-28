package platillostipicos.appdesktop.utils;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class ImageUtils extends DefaultTableCellRenderer {

    private int cellHeight; // Altura de la celda de la tabla
    private int tableWidth; // Ancho de la tabla

    public ImageUtils(int cellHeight, int tableWidth) {
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
    
    
    
    /**
     * Convierte una imagen en un array de bytes en el formato especificado.
     *
     * @param image  La imagen a convertir.
     * @param format El formato de la imagen (por ejemplo, "png", "jpg").
     * @return El array de bytes que representa la imagen en el formato especificado.
     * @throws IOException Si ocurre un error al convertir la imagen.
     */
    public static byte[] imageToByteArray(Image image, String format) throws IOException {
        BufferedImage bufferedImage = toBufferedImage(image);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, format, baos);
        return baos.toByteArray();
    }

    /**
     * Convierte una imagen en un objeto BufferedImage.
     *
     * @param image La imagen a convertir.
     * @return El objeto BufferedImage resultante.
     */
    private static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        BufferedImage bufferedImage = new BufferedImage(
                image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        bufferedImage.getGraphics().drawImage(image, 0, 0, null);
        return bufferedImage;
    }

    /**
     * Obtiene los datos de imagen en formato de bytes a partir de un objeto Icon.
     *
     * @param icon El objeto Icon que contiene la imagen.
     * @return El array de bytes que representa la imagen, o null si el Icon no es una instancia de ImageIcon.
     */
    public static byte[] getImageDataFromIcon(Icon icon) {
        if (icon instanceof ImageIcon) {
            Image image = ((ImageIcon) icon).getImage();

            // Convertir la imagen a un array de bytes
            try {
                return imageToByteArray(image, "png");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Obtiene los datos de imagen en formato de bytes a partir de un objeto JLabel que contiene un Icon.
     *
     * @param label El JLabel que contiene la imagen.
     * @return El array de bytes que representa la imagen, o null si el Icon del JLabel no es una instancia de ImageIcon.
     */
    public static byte[] getImageDataFromLabel(JLabel label) {
        Icon icon = label.getIcon();
        return getImageDataFromIcon(icon);
    }
}
