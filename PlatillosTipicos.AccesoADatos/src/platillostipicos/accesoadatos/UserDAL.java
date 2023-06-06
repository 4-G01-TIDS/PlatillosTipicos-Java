/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package platillostipicos.accesoadatos;
import platillostipicos.entidadesdenegocio.User;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
/**
 *
 * @author Stevn
 */
public class UserDAL {
    
    // <editor-fold defaultstate="collapsed" desc="Obtener Datos">
     // Metodo para obtener los campos a utilizar en la consulta SELECT de la tabla de Usuario
    static String obtenerCampos() {
        return "u.Id, u.Name, u.LastName, u.Email, u.ImgUser, u.EmailConfirmed, u.IsCustomer, u.CreationDate, u.IsNative, u.Nationality, u.Dui, u.PhoneNumber, u.IsCustomer ";
    }
    
    // Metodo para  ejecutar el ResultSet de la consulta SELECT a la tabla de Rol 
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<User> pUser) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { // obtener el ResultSet desde la clase ComunDB
            while (resultSet.next()) { // Recorrer cada una de la fila que regresa la consulta  SELECT de la tabla Rol
                User users = new User(); 
                asignarDatosResultSet(users, resultSet, 0); // Llenar las propiedaddes de la Entidad Rol con los datos obtenidos de la fila en el ResultSet
                pUser.add(users); // Agregar la entidad Rol al ArrayList de Rol
            }
            resultSet.close(); // Cerrar el ResultSet
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener ResultSet de la clase ComunDB   en el caso que suceda 
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="GETBYID">
    public static User GetById(User pPublication) throws Exception {
        User publication = new User();
        ArrayList<User> publications = new ArrayList();
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
        if (publication.getImgUser()!= null) {
            User pImgs = new User();
            pImgs.setImgUser(publication.getImgUser());
//            publication.setImgUser(UserDAL.GetByIdImgs(pImgs));
        }
        return publication;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="READ">
    static int asignarDatosResultSet(User pUser, ResultSet pResultSet, int pIndex) throws Exception {
        //  SELECT r.Id(indice 1),r.Nombre(indice 2) * FROM Rol
        pIndex++;
        pUser.setId(UUID.fromString(pResultSet.getString(pIndex))); // index 1
        pIndex++;
        pUser.setName(pResultSet.getString(pIndex)); // index 2
        pIndex++;
        pUser.setLastName(pResultSet.getString(pIndex) == null?null:pResultSet.getString(pIndex)); // index 2
        pIndex++;
        pUser.setEmail(pResultSet.getString(pIndex)); // index 2
        pIndex++;
        pUser.setImgUser(pResultSet.getBytes(pIndex) == null?null:pResultSet.getBytes(pIndex));
        return pIndex;
    }
   
    
    // Metodo para obtener el SELECT a la tabla Usuario y el top en el caso que se utilice una base de datos SQL SERVER
    private static String obtenerSelect(User pUser) {
        String sql;
        sql = "SELECT ";
        if (pUser.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
             // Agregar el TOP a la consulta SELECT si el gestor de base de datos es SQL SERVER y getTop_aux es mayor a cero
            sql += "TOP " + pUser.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Users u");
        return sql;
    } 
    
    // Metodo para obtener Order by a la consulta SELECT de la tabla Usuario y ordene los registros de mayor a menor 
    private static String agregarOrderBy(User pUser) {
        String sql = " ORDER BY u.Id DESC";
        if (pUser.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            // Agregar el LIMIT a la consulta SELECT de la tabla de Usuario en el caso que getTop_aux() sea mayor a cero y el gestor de base de datos
            // sea MYSQL
            sql += " LIMIT " + pUser.getTop_aux() + " ";
        }
        return sql;
    }
    
    
    // Metodo para obtener todos los registro de la tabla de Usuario que cumplan con los filtros agregados 
     // a la consulta SELECT de la tabla de Usuario 
    public static ArrayList<User> buscar(User pUsuario) throws Exception {
        ArrayList<User> usuarios = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = obtenerSelect(pUsuario); // obtener la consulta SELECT de la tabla Usuario
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            querySelect(pUsuario, utilQuery); // Asignar el filtro a la consulta SELECT de la tabla de Usuario 
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pUsuario); // Concatenar a la consulta SELECT de la tabla Usuario el ORDER BY por Id
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // obtener el PreparedStatement desde la clase ComunDB
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pUsuario, utilQuery); // Asignar los parametros al PreparedStatement de la consulta SELECT de la tabla de Usuario
                obtenerDatos(ps, usuarios); // Llenar el ArrayList de Usuario con las fila que devolvera la consulta SELECT a la tabla de Usuario
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } 
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        return usuarios; // Devolver el ArrayList de Usuario
    }
    
    static void querySelect(User pUsuario, ComunDB.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement(); // obtener el PreparedStatement al cual aplicar los parametros
        if (pUsuario.getId() != null) { // verificar si se va incluir el campo Id en el filtro de la consulta SELECT de la tabla de Usuario
            pUtilQuery.AgregarWhereAnd(" u.Id=? "); // agregar el campo Id al filtro de la consulta SELECT y agregar el WHERE o AND
            if (statement != null) {
                 // agregar el parametro del campo Id a la consulta SELECT de la tabla de Usuario
                statement.setString(pUtilQuery.getNumWhere(), pUsuario.getId().toString());
            }
        }
        // verificar si se va incluir el campo IdRol en el filtro de la consulta SELECT de la tabla de Usuario
//        if (pUsuario.getIdRol() > 0) {
//            pUtilQuery.AgregarWhereAnd(" u.IdRol=? "); // agregar el campo IdRol al filtro de la consulta SELECT y agregar en el WHERE o AND
//            if (statement != null) {
//                 // agregar el parametro del campo IdRol a la consulta SELECT de la tabla de Usuario
//                statement.setInt(pUtilQuery.getNumWhere(), pUsuario.getIdRol());
//            }
//        }
        // verificar si se va incluir el campo Nombre en el filtro de la consulta SELECT de la tabla de Usuario
        if (pUsuario.getName() != null && pUsuario.getName().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" u.Name LIKE ? "); // agregar el campo Nombre al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                 // agregar el parametro del campo Nombre a la consulta SELECT de la tabla de Usuario
                statement.setString(pUtilQuery.getNumWhere(), "%" + pUsuario.getName() + "%");
            }
        }
        // Verificar si se va incluir el campo Apellido en el filtro de la consulta SELECT de la tabla de Usuario
        if (pUsuario.getLastName() != null && pUsuario.getLastName().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" u.LastName LIKE ? "); // agregar el campo Apellido al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                 // agregar el parametro del campo Apellido a la consulta SELECT de la tabla de Usuario
                statement.setString(pUtilQuery.getNumWhere(), "%" + pUsuario.getLastName() + "%");
            }
        }//
        // Verificar si se va incluir el campo Login en el filtro de la consulta SELECT de la tabla de Usuario
        if (pUsuario.getEmail() != null && pUsuario.getEmail().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" u.Email=? "); // agregar el campo Login al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                 // agregar el parametro del campo Login a la consulta SELECT de la tabla de Usuario
                statement.setString(pUtilQuery.getNumWhere(), pUsuario.getEmail());
            }
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="CREATE">
    public static int crear(User pUser) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "INSERT INTO Users(Name,CreationDate,Password, LastName,ImgUser,Email) VALUES(?,?,?,?,?,?)";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pUser.getName());
                // Fecha y hora actual
                LocalDateTime now = LocalDateTime.now();
                Timestamp timestamp = Timestamp.valueOf(now);
                ps.setTimestamp(2, timestamp);
                //
                ps.setString(3, pUser.getPassword());
                ps.setString(4, pUser.getLastName());
                ps.setBytes(5, pUser.getImgUser());
                ps.setString(6, pUser.getEmail());
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
