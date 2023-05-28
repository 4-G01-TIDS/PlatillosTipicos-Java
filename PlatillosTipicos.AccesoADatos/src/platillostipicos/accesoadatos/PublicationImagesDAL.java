package platillostipicos.accesoadatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import platillostipicos.entidadesdenegocio.PublicationImages;

public class PublicationImagesDAL {

    static String obtenerCampos() {
        return "r.Id, r.ImagePublication1, r.ImagePublication2, r.ImagePublication3, r.ImagePublication4, r.ImagePublication5";
    }

    static int asignarDatosResultSet(PublicationImages pPublicationImages, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        String idStr = pResultSet.getString(pIndex);
        if (idStr != null) {
            pPublicationImages.setId(UUID.fromString(idStr));  // index 1
        } else {
            pPublicationImages.setId(null);  // index 1
            pPublicationImages.setImagePublication1(null);  // index 2
            pPublicationImages.setImagePublication2(null);  // index 3
            pPublicationImages.setImagePublication3(null);  // index 4
            pPublicationImages.setImagePublication4(null);  // index 5
            pPublicationImages.setImagePublication5(null);  // index 6
            return pIndex + 6;
        }
        pIndex++;
        pPublicationImages.setImagePublication1(pResultSet.getBytes(pIndex)); // index 2
        pIndex++;
        pPublicationImages.setImagePublication2(pResultSet.getBytes(pIndex)); // index 2
        pIndex++;
        pPublicationImages.setImagePublication3(pResultSet.getBytes(pIndex)); // index 2
        pIndex++;
        pPublicationImages.setImagePublication4(pResultSet.getBytes(pIndex)); // index 2
        pIndex++;
        pPublicationImages.setImagePublication5(pResultSet.getBytes(pIndex)); // index 2

        return pIndex;
    }

    // <editor-fold defaultstate="collapsed" desc="DELETE">
    public static int eliminarImagenes(PublicationImages pPublicationImages) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "DELETE FROM PublicationImages WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pPublicationImages.getId().toString());
                result = ps.executeUpdate();
                ps.close(); // cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close(); // cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex;
        }
        return result;
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="CREATE">
    public static int crearImagenes(PublicationImages pPublicationImages) throws Exception {

        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "INSERT INTO PublicationImages(Id, ImagePublication1,ImagePublication2,ImagePublication3,ImagePublication4,ImagePublication5) VALUES(?,?,?,?,?,?)";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pPublicationImages.getId().toString());
                ps.setBytes(2, pPublicationImages.getImagePublication1());
                ps.setBytes(3, pPublicationImages.getImagePublication2());
                ps.setBytes(4, pPublicationImages.getImagePublication3());
                ps.setBytes(5, pPublicationImages.getImagePublication4());
                ps.setBytes(6, pPublicationImages.getImagePublication5());

                result = ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return result;
    }
    // </editor-fold> 

}
