package platillostipicos.accesoadatos;

import platillostipicos.entidadesdenegocio.Publication;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.HashMap;
import platillostipicos.entidadesdenegocio.*;

public class PublicationDAL {

    // <editor-fold defaultstate="collapsed" desc="CREATE">
    public static int crear(Publication pPublication) throws Exception {
        if (pPublication.getPublicationImagesId() != null) {
            PublicationImagesDAL.crearImagenes(pPublication.getPublicationImages());

        }

        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "INSERT INTO Publications(Description,PublicationDate,UserId,PublicationImagesId,RestaurantId) VALUES(?,?,?,?,?)";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pPublication.getDescription());
                // Fecha y hora actual
                LocalDateTime now = LocalDateTime.now();
                Timestamp timestamp = Timestamp.valueOf(now);
                ps.setTimestamp(2, timestamp);
                //
                ps.setString(3, pPublication.getUserId().toString());
                ps.setString(4, pPublication.getPublicationImagesId() != null ? pPublication.getPublicationImagesId().toString() : null);
                ps.setString(5, pPublication.getRestaurantId().toString());
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
    public static Publication GetById(Publication pPublication) throws Exception {
        Publication publication = new Publication();
        ArrayList<Publication> publications = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pPublication);
            sql += " WHERE u.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pPublication.getId().toString());
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
        if (publication.getPublicationImagesId() != null) {
            PublicationImages pImgs = new PublicationImages();
            pImgs.setId(publication.getPublicationImagesId());
            publication.setPublicationImages(PublicationImagesDAL.GetByIdImgs(pImgs));
        }
        return publication;
    }
    // </editor-fold> 

    static String obtenerCampos() {
        return "u.Id, u.Description, u.PublicationDate, u.UserId, u.PublicationImagesId, u.RestaurantId";
    }

    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Publication> pPublications) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { // obtener el ResultSet desde la clase ComunDB
            while (resultSet.next()) { // Recorrer cada una de la fila que regresa la consulta  SELECT de la tabla Publications
                Publication publication = new Publication();
                // Llenar las propiedaddes de la Entidad Usuario con los datos obtenidos de la fila en el ResultSet
                asignarDatosResultSet(publication, resultSet, 0);
                pPublications.add(publication);
            }
            resultSet.close(); // cerrar el ResultSet
        } catch (SQLException ex) {
            throw ex;// enviar al siguiente metodo el error al obtener ResultSet de la clase ComunDB   en el caso que suceda 
        }
    }

    private static String obtenerSelect(Publication pPublication) {
        String sql;
        sql = "SELECT ";
        if (pPublication.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
            // Agregar el TOP a la consulta SELECT si el gestor de base de datos es SQL SERVER y getTop_aux es mayor a cero
            sql += "TOP " + pPublication.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Publications u");
        return sql;
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
            querySelect(pPublication, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pPublication);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pPublication, utilQuery);
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

    static void querySelect(Publication pPublication, ComunDB.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement(); // obtener el PreparedStatement al cual aplicar los parametros

        if (pPublication.getId() != null) {
            pUtilQuery.AgregarWhereAnd(" id = ? ");
            if (statement != null) {
                statement.setObject(pUtilQuery.getNumWhere(), pPublication.getId());
            }
        }

        if (pPublication.getDescription() != null && !pPublication.getDescription().trim().isEmpty()) {
            pUtilQuery.AgregarWhereAnd(" description LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pPublication.getDescription() + "%");
            }
        }

        if (pPublication.getPublicationDate() != null) {
            pUtilQuery.AgregarWhereAnd(" publicationDate = ? ");
            if (statement != null) {
                statement.setObject(pUtilQuery.getNumWhere(), pPublication.getPublicationDate());
            }
        }

        if (pPublication.getUserId() != null) {
            pUtilQuery.AgregarWhereAnd(" userId = ? ");
            if (statement != null) {
                statement.setObject(pUtilQuery.getNumWhere(), pPublication.getUserId());
            }
        }

        if (pPublication.getPublicationImagesId() != null) {
            pUtilQuery.AgregarWhereAnd(" publicationImagesId = ? ");
            if (statement != null) {
                statement.setObject(pUtilQuery.getNumWhere(), pPublication.getPublicationImagesId());
            }
        }

        if (pPublication.getRestaurantId() != null) {
            pUtilQuery.AgregarWhereAnd(" restaurantId = ? ");
            if (statement != null) {
                statement.setObject(pUtilQuery.getNumWhere(), pPublication.getRestaurantId());
            }
        }

    }

    // </editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="UPDATE">
    public static int modificar(Publication pPublication) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "UPDATE Publications SET Description=? WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                PublicationImagesDAL.modificarImgP(pPublication.getPublicationImages());
                ps.setString(1, pPublication.getDescription());
                ps.setString(2, pPublication.getId().toString());
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
    public static int eliminar(Publication pPublication) throws Exception {

        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "DELETE FROM Publications WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pPublication.getId().toString());
                result = ps.executeUpdate();
                ps.close(); // cerrar el PreparedStatement
                if (pPublication.getPublicationImagesId() != null) {
                    PublicationImagesDAL.eliminarImagenes(pPublication.getPublicationImages());
                }
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
