package platillostipicos.accesoadatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import platillostipicos.entidadesdenegocio.Comment;

public class CommentDAL {
    // <editor-fold defaultstate="collapsed" desc="READ">

    // <editor-fold defaultstate="collapsed" desc="GETBYID">
    public static ArrayList<Comment> getByPublicationId(String publicationId) throws Exception {
        ArrayList<Comment> comments = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion()) {
            String sql = "SELECT * FROM Comments WHERE PublicationId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, publicationId);
                obtenerDatos(ps, comments);
            } catch (SQLException ex) {
                throw ex;
            }
        } catch (SQLException ex) {
            throw ex;
        }
        return comments;
    }

    // </editor-fold> 
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Comment> pComments) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS)) {
            while (resultSet.next()) {
                Comment comment = new Comment();
                int index = asignarDatosResultSet(comment, resultSet, 0); // Inicializar el índice aquí
                pComments.add(comment);
            }
        } catch (SQLException ex) {
            throw ex;
        }
    }

    static int asignarDatosResultSet(Comment pComment, ResultSet pResultSet, int pIndex) throws Exception {
        /* SELECT u.Id(indice 1), u.Content(indice 2), u.CreateDate(indice 3),
         u.UserId(indice 4), u.PublicationId(indice 5) * FROM Comment */
        pIndex++;
        String idString = pResultSet.getString(pIndex);
        pComment.setId(idString != null ? UUID.fromString(idString) : null);

        pIndex++;
        pComment.setContent(pResultSet.getString(pIndex));

        pIndex++;
        pComment.setCreateDate(pResultSet.getTimestamp(pIndex).toLocalDateTime());

        pIndex++;
        String userIdString = pResultSet.getString(pIndex);
        pComment.setUserId(userIdString != null ? UUID.fromString(userIdString) : null);

        pIndex++;
        String publicationIdString = pResultSet.getString(pIndex);
        pComment.setPublicationId(publicationIdString != null ? UUID.fromString(publicationIdString) : null);

        return pIndex;
    }
    // </editor-fold> 
}
