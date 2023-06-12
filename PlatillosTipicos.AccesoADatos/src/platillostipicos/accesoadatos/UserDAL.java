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

    // <editor-fold defaultstate="collapsed" desc="Existe Login">
    private static boolean existeLogin(User pUsuario) throws Exception {
        boolean existe = false;
        ArrayList<User> usuarios = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = obtenerSelect(pUsuario);  // Obtener la consulta SELECT de la tabla Usuario
            // Concatenar a la consulta SELECT de la tabla Usuario el WHERE y el filtro para saber si existe el login
            sql += " WHERE u.Email=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setString(1, pUsuario.getEmail());  // Agregar el parametros a la consulta donde estan el simbolo ? #1 
                obtenerDatos(ps, usuarios); // Llenar el ArrayList de USuario con las fila que devolvera la consulta SELECT a la tabla de Usuario
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;  // Enviar al siguiente metodo el error al ejecutar PreparedStatement el en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        if (usuarios.size() > 0) { // Verificar si el ArrayList de Usuario trae mas de un registro en tal caso solo debe de traer uno
            User usuario;
            // Se solucciono tenia valor de 1 cuando debe de ser cero
            usuario = usuarios.get(0); // Si el ArrayList de Usuario trae un registro o mas obtenemos solo el primero 
            if (usuario.getEmail() != null) {
                // Si el Id de Usuario es mayor a cero y el Login que se busco en la tabla de Usuario es igual al que solicitamos
                // en los parametros significa que el login ya existe en la base de datos y devolvemos true en la variable "existe"
                existe = true;
            }
        }
        return existe; //Devolver la variable "existe" con el valor true o false si existe o no el Login en la tabla de Usuario de la base de datos

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="GETBYID">
    public static User GetById(User pUser) throws Exception {
        User user = new User();
        ArrayList<User> users = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pUser);
            sql += " WHERE u.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pUser.getId().toString());
                obtenerDatos(ps, users);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        if (users.size() > 0) {
            user = users.get(0);
        }
        if (user.getImgUser() != null) {
            User pImgs = new User();
            pImgs.setImgUser(user.getImgUser());
//            publication.setImgUser(UserDAL.GetByIdImgs(pImgs));
        }
        return user;
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

    // <editor-fold defaultstate="collapsed" desc="READ">
    static int asignarDatosResultSet(User pUser, ResultSet pResultSet, int pIndex) throws Exception {
        //  SELECT r.Id(indice 1),r.Nombre(indice 2) * FROM Rol
        pIndex++;
        pUser.setId(UUID.fromString(pResultSet.getString(pIndex))); // index 1
        pIndex++;
        pUser.setName(pResultSet.getString(pIndex)); // index 2
        pIndex++;
        pUser.setLastName(pResultSet.getString(pIndex) == null ? null : pResultSet.getString(pIndex)); // index 2
        pIndex++;
        pUser.setEmail(pResultSet.getString(pIndex)); // index 2
        pIndex++;
        pUser.setImgUser(pResultSet.getBytes(pIndex) == null ? null : pResultSet.getBytes(pIndex));
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
        sql += (obtenerCampos() + "FROM Users u");
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
        } catch (SQLException ex) {
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

    // <editor-fold defaultstate="collapsed" desc="UPDATE">
    public static int modificar(User pUsuario) throws Exception {
        int result;
        String sql;
        boolean existe = existeLogin(pUsuario); // verificar si el usuario que se va a modificar ya existe en nuestra base de datos
        if (existe == false) {
            try (Connection conn = ComunDB.obtenerConexion();) {
                sql = "UPDATE Users SET Name=?, LastName=?, Email=?, ImgUser=? WHERE Id=?";
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // obtener el PreparedStatement desde la clase ComunDB
                    ps.setString(1, pUsuario.getName()); // agregar el parametro a la consulta donde estan el simbolo ? #2  
                    ps.setString(2, pUsuario.getLastName()); // agregar el parametro a la consulta donde estan el simbolo ? #3  
                    ps.setString(3, pUsuario.getEmail()); // agregar el parametro a la consulta donde estan el simbolo ? #4  
                    ps.setBytes(4, pUsuario.getImgUser()); // agregar el parametro a la consulta donde estan el simbolo ? #5  
                    ps.setString(5, pUsuario.getPassword());
                    result = ps.executeUpdate(); // ejecutar la consulta UPDATE en la base de datos
                    ps.close(); // cerrar el PreparedStatement
                } catch (SQLException ex) {
                    throw ex; // enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda 
                }
                conn.close(); // cerrar la conexion a la base de datos
            } catch (SQLException ex) {
                throw ex; // enviar al siguiente metodo el error al obtener la conexion en el caso que suceda 
            }
        } else {
            result = 0;
            throw new RuntimeException("Login ya existe"); // enviar una exception para notificar que el login existe
        }
        return result; // Retornar el numero de fila afectadas en el UPDATE en la base de datos 
    }
// </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="DELETE">
    public static int eliminar(User pUsuario) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { 
            sql = "DELETE FROM Users WHERE Id=?"; 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { 
                ps.setString(1, pUsuario.getId().toString());  
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
    
    // <editor-fold defaultstate="collapsed" desc="MOD.IMG">
    public static int modificarImgP(User pUser) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "UPDATE Users SET ImgUser=? WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setBytes(1, pUser.getImgUser());
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
