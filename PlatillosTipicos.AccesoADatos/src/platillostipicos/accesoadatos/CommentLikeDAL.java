package platillostipicos.accesoadatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.sql.*;
import java.util.UUID;
import platillostipicos.entidadesdenegocio.CommentLike;

public class CommentLikeDAL {

    // <editor-fold defaultstate="collapsed" desc="CREATE">
    public static int NewLike(CommentLike pCommentLike) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "INSERT INTO CommentLikes(IsLike,CreateDate,UserId,CommentId) VALUES(?,?,?,?)";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setBoolean(1, pCommentLike.getisIsLike());
                // Fecha y hora actual
                LocalDateTime now = LocalDateTime.now();
                Timestamp timestamp = Timestamp.valueOf(now);
                ps.setTimestamp(2, timestamp);
                //
                ps.setString(3, pCommentLike.getUserId().toString());
                ps.setString(4, pCommentLike.getCommentId().toString());
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
    public static CommentLike getByPublicationId(CommentLike pCommentLike) throws Exception {
        CommentLike commentLike = null;
        
        try (Connection conn = ComunDB.obtenerConexion()) {
            String sql = "SELECT * FROM CommentLikes WHERE CommentId=? AND UserId=?";
            
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, pCommentLike.getCommentId().toString());
                ps.setString(2, pCommentLike.getUserId().toString());
                
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        commentLike = new CommentLike();
                        commentLike.setId(UUID.fromString(rs.getString("Id")));
                        commentLike.setCommentId(UUID.fromString(rs.getString("CommentId")));
                        commentLike.setUserId(UUID.fromString(rs.getString("UserId")));
                        commentLike.setIsLike(rs.getBoolean("IsLike"));
                        commentLike.setCreateDate(rs.getTimestamp("CreateDate").toLocalDateTime());
                    }
                }
            } catch (SQLException ex) {
                throw ex;
            }
        } catch (SQLException ex) {
            throw ex;
        }
        
        return commentLike;
    }

    // </editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="DELETE">
    public static int eliminar(CommentLike pCommentLike) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "DELETE FROM CommentLikes WHERE UserId=? AND CommentId=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pCommentLike.getUserId().toString());
                ps.setString(2, pCommentLike.getCommentId().toString());
                
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
    public static int update(CommentLike pCommentLike) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "UPDATE CommentLikes SET IsLike=? WHERE UserId=? AND CommentId=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setBoolean(1, pCommentLike.getisIsLike());
                ps.setString(2, pCommentLike.getUserId().toString());
                ps.setString(3, pCommentLike.getCommentId().toString());
                
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
