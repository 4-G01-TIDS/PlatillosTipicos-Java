package platillostipicos.accesoadatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import platillostipicos.entidadesdenegocio.PublicationImages;

public class PublicationImagesDAL {

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

    // <editor-fold defaultstate="collapsed" desc="READ">
    // <editor-fold defaultstate="collapsed" desc="GETBYID">
    public static PublicationImages GetByIdImgs(PublicationImages pPublicationImages) throws Exception {
        PublicationImages publication = new PublicationImages();
        ArrayList<PublicationImages> publications = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pPublicationImages);
            sql += " WHERE r.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pPublicationImages.getId().toString());
                obtenerDatos(ps, publications);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        if (publications.size() > 0) {
            publication = publications.get(0);
        }
        return publication;
    }
    // </editor-fold> 

    static String obtenerCampos() {
        return "r.Id, r.ImagePublication1, r.ImagePublication2, r.ImagePublication3, r.ImagePublication4, r.ImagePublication5";
    }

    private static void obtenerDatos(PreparedStatement pPS, ArrayList<PublicationImages> pPublicationImages) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) {
            while (resultSet.next()) {
                PublicationImages publicationImg = new PublicationImages();
                asignarDatosResultSet(publicationImg, resultSet, 0);
                pPublicationImages.add(publicationImg);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    private static String obtenerSelect(PublicationImages pPublicationImages) {
        String sql;
        sql = "SELECT ";
        if (pPublicationImages.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
            // Agregar el TOP a la consulta SELECT si el gestor de base de datos es SQL SERVER y getTop_aux es mayor a cero
            sql += "TOP " + pPublicationImages.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM PublicationImages r");
        return sql;
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
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="UPDATE">
    public static int modificarImgP(PublicationImages pPublicationImages) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "UPDATE PublicationImages SET ImagePublication1=?, ImagePublication2=?, ImagePublication3=?, ImagePublication4=?, ImagePublication5=? WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setBytes(1, pPublicationImages.getImagePublication1());
                ps.setBytes(2, pPublicationImages.getImagePublication2());
                ps.setBytes(3, pPublicationImages.getImagePublication3());
                ps.setBytes(4, pPublicationImages.getImagePublication4());
                ps.setBytes(5, pPublicationImages.getImagePublication5());
                ps.setString(6, pPublicationImages.getId().toString());

                result = ps.executeUpdate();
                ps.close(); // cerrar el PreparedStatement
            } catch (SQLException ex) {
                result = 0;

                throw ex; // enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda 
            }
            conn.close(); // cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex; // enviar al siguiente metodo el error al obtener la conexion en el caso que suceda 
        }
        return result; // Retornar el numero de fila afectadas en el UPDATE en la base de datos 
    }
    // </editor-fold> 

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

}
