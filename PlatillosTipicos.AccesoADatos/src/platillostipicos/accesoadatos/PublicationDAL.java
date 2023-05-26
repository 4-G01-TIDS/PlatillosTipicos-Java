package platillostipicos.accesoadatos;

import platillostipicos.entidadesdenegocio.Publication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.HashMap;
import platillostipicos.entidadesdenegocio.*;

public class PublicationDAL {

    static String obtenerCampos() {
        return "u.Id, u.Description, u.PublicationDate, u.UserId, u.PublicationImagesId, u.RestaurantId";
    }

    private static String agregarOrderBy(Publication pPublication) {
        String sql = " ORDER BY u.PublicationDate DESC";
        if (pPublication.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            sql += " LIMIT " + pPublication.getTop_aux();
        }
        return sql;
    }

    static int asignarDatosResultSet(Publication pPublication, ResultSet pResultSet, int pIndex) throws Exception {
        /* SELECT u.Id(indice 1), u.Description(indice 2), u.PublicationDate(indice 3),
         u.UserId(indice 4), u.PublicationImagesId(indice 5), u.RestaurantId(indice 6) * FROM Usuario */
        pIndex++;
        pPublication.setId(UUID.fromString(pResultSet.getString(pIndex))); // index 1
        pIndex++;
        pPublication.setDescription(pResultSet.getString(pIndex)); // index 2
        pIndex++;
        pPublication.setPublicationDate(pResultSet.getTimestamp(pIndex).toLocalDateTime()); // index 3
        pIndex++;
        pPublication.setUserId(UUID.fromString(pResultSet.getString(pIndex))); // index 4
        pIndex++;
        String value = pResultSet.getString(pIndex);
        if (value != null) {
            pPublication.setPublicationImagesId(UUID.fromString(value)); // index 5
        } else {
            pPublication.setPublicationImagesId(null); // index 5
        }
        pIndex++;
        pPublication.setRestaurantId(UUID.fromString(pResultSet.getString(pIndex))); // index 6
        return pIndex;
    }

    private static void obtenerPublicacionIncluirImagenes(PreparedStatement pPS, ArrayList<Publication> pPublications) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { // obtener el ResultSet desde la clase ComunDB
            HashMap<UUID, PublicationImages> rolMap = new HashMap(); //crear un HashMap para automatizar la creacion de instancias de la clase PublicationImages
            while (resultSet.next()) { // Recorrer cada una de la fila que regresa la consulta  SELECT de la tabla Publication JOIN a la tabla de PublicationImages
                Publication publication = new Publication();
                // Llenar las propiedaddes de la Entidad Publication con los datos obtenidos de la fila en el ResultSet
                int index = asignarDatosResultSet(publication, resultSet, 0);
                if (rolMap.containsKey(publication.getPublicationImagesId()) == false) {
                    PublicationImages publicatinImg = new PublicationImages();
                    PublicationImagesDAL.asignarDatosResultSet(publicatinImg, resultSet, index);
                    rolMap.put(publicatinImg.getId(), publicatinImg);
                    publication.setPublicationImages(publicatinImg); // agregar PublicationImages a Publication
                } else {
                    publication.setPublicationImages(rolMap.get(publication.getPublicationImagesId()));
                }
                pPublications.add(publication); // Agregar publication de la fila actual al ArrayList de publication
            }
            resultSet.close(); // cerrar el ResultSet
        } catch (SQLException ex) {
            throw ex; // enviar al siguiente metodo el error al obtener ResultSet de la clase ComunDB   en el caso que suceda 
        }
    }

    public static ArrayList<Publication> getPublications(Publication pPublication) throws Exception {
        ArrayList<Publication> publications = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = "SELECT ";
            if (pPublication.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
                sql += "TOP " + pPublication.getTop_aux() + " ";
            }
            sql += obtenerCampos();
            sql += ",";
            sql += PublicationImagesDAL.obtenerCampos();
            sql += " FROM Publications u";
            sql += " LEFT JOIN PublicationImages r on (u.publicationImagesId=r.Id)";
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pPublication);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                obtenerPublicacionIncluirImagenes(ps, publications);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return publications;
    }

}
