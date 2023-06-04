package platillostipicos.accesoadatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;
import platillostipicos.entidadesdenegocio.PublicationLike;

public class PublicationLikeDAL {

    // <editor-fold defaultstate="collapsed" desc="CREATE">
    public static int NewLikePublication(PublicationLike pPublicationLike) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "INSERT INTO PublicationLikes(IsLike,CreateDate,UserId,PublicationId) VALUES(?,?,?,?)";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setBoolean(1, pPublicationLike.getIsIsLike());
                // Fecha y hora actual
                LocalDateTime now = LocalDateTime.now();
                Timestamp timestamp = Timestamp.valueOf(now);
                ps.setTimestamp(2, timestamp);
                //
                ps.setString(3, pPublicationLike.getUserId().toString());
                ps.setString(4, pPublicationLike.getPublicationId().toString());
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

    // <editor-fold defaultstate="collapsed" desc="GETBYID">
    public static PublicationLike getLikeByPublicationAndUser(PublicationLike pPublicationLike) throws Exception {
        PublicationLike publicationLike = null;

        try (Connection conn = ComunDB.obtenerConexion()) {
            String sql = "SELECT * FROM PublicationLikes WHERE PublicationId=? AND UserId=?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, pPublicationLike.getPublicationId().toString());
                ps.setString(2, pPublicationLike.getUserId().toString());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        publicationLike = new PublicationLike();
                        publicationLike.setPublicationId(UUID.fromString(rs.getString("PublicationId")));
                        publicationLike.setUserId(UUID.fromString(rs.getString("UserId")));
                        publicationLike.setIsLike(rs.getBoolean("IsLike"));
                    }
                }
            } catch (SQLException ex) {
                throw ex;
            }
        } catch (SQLException ex) {
            throw ex;
        }

        return publicationLike;
    }

    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc="DELETE">
    public static int eliminar(PublicationLike pPublicationLike) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "DELETE FROM PublicationLikes WHERE UserId=? AND PublicationId=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pPublicationLike.getUserId().toString());
                ps.setString(2, pPublicationLike.getPublicationId().toString());

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

    // <editor-fold defaultstate="collapsed" desc="UPDATE">
    public static int update(PublicationLike pPublicationLike) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "UPDATE PublicationLikes SET IsLike=? WHERE UserId=? AND PublicationId=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setBoolean(1, pPublicationLike.getIsIsLike());
                ps.setString(2, pPublicationLike.getUserId().toString());
                ps.setString(3, pPublicationLike.getPublicationId().toString());

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
}
