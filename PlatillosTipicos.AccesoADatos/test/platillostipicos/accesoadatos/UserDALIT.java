/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package platillostipicos.accesoadatos;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import platillostipicos.entidadesdenegocio.User;
//
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

/**
 *
 * @author MINEDUCYT
 */
public class UserDALIT {

    private static User usuarioActual;
    
    public UserDALIT() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    public int testIndividualQuerySelect(User pUsuario) throws Exception {
        ComunDB comundb = new ComunDB();
        ComunDB.UtilQuery pUtilQuery = comundb.new UtilQuery("", null, 0);
        UserDAL.querySelect(pUsuario, pUtilQuery);
        return pUtilQuery.getNumWhere();
    }

    /**
     * Test of obtenerCampos method, of class UserDAL.
     */
    @Test
    public void testObtenerCampos() {
        System.out.println("obtenerCampos");
        String expResult = "u.Id, u.Name, u.LastName, u.Email, u.ImgUser, u.EmailConfirmed, u.IsCustomer, u.CreationDate, u.IsNative, u.Nationality, u.Dui, u.PhoneNumber, u.IsCustomer ";
        String result = UserDAL.obtenerCampos();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetById method, of class UserDAL.
     */
    @Test
    public void testGetById() throws Exception {
        System.out.println("GetById");
        User result = UserDAL.GetById(usuarioActual);
        assertEquals(usuarioActual.getId(), result.getId());
    }

    /**
     * Test of crear method, of class UserDAL.
     */
//    @Test
//    public void testCrear() throws Exception {
//        System.out.println("crear");
//        User usuario = new User();
//        usuario.setName("Julian");
//        usuario.setCreationDate(LocalDateTime.now());
//        usuario.setPassword("12345");
//        usuario.setLastName("KASALSA");
//        //El GMAIL DE USUARIO DEBE CAMBIARSE PARA HACER LA PRUEBA UNITARIA
//        usuario.setEmail("Arrobagmail");
//        int expResult = 0;
//        int result = UserDAL.crear(usuario);
//        assertNotEquals(expResult, result);
//    }
    /**
     * Test of asignarDatosResultSet method, of class UserDAL.
     */
    @Test
    public void testAsignarDatosResultSet() throws Exception {
        System.out.println("asignarDatosResultSet");
        User pUser = new User();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = "SELECT " + UserDAL.obtenerCampos() + " FROM Users u";
            sql += " WHERE u.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, usuarioActual.getId().toString());
                try (ResultSet resultSet = ComunDB.obtenerResultSet(ps);) {
                    while (resultSet.next()) {
                        UserDAL.asignarDatosResultSet(pUser, resultSet, 0);
                    }
                    resultSet.close();
                } catch (SQLException ex) {
                    throw ex;
                }
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } // Handle any errors that may have occurred.
        catch (SQLException ex) {
            throw ex;
        }
        System.out.println(pUser.getId().getClass() + " " + pUser.getId());
        System.out.println(usuarioActual.getId().getClass() + " " + usuarioActual.getId());
        assertTrue(pUser.getId().equals(usuarioActual.getId()));
    }

    /**
     * Test of buscar method, of class UserDAL.
     */
    @Test
    public void testBuscar() throws Exception {
       System.out.println("buscar");
        User pUser = new User();
        pUser.setId(UUID.fromString("23366D79-F322-4448-9E55-1518FC0C34B0"));
        pUser.setName("Admin");
        pUser.setLastName(null);
        pUser.setEmail("admin@gmail.com");
        ArrayList<User> result = UserDAL.buscar(pUser);
        assertTrue(result.size() > 0);
        usuarioActual = result.get(0);
    }

    /**
     * Test of querySelect method, of class UserDAL.
     */
    @Test
    public void testQuerySelect() throws Exception {
        System.out.println("querySelect");
        int index = 0;
        User pUser = new User();
        pUser.setId(UUID.randomUUID());
        index++;
        assertTrue(testIndividualQuerySelect(pUser) == index);
        pUser.setName("TEST");
        index++;
        assertTrue(testIndividualQuerySelect(pUser) == index);
        pUser.setLastName("TEST LASTNAME");
        index++;
        assertTrue(testIndividualQuerySelect(pUser) == index);
        pUser.setEmail("TEST EMAIL");
        index++;
        assertTrue(testIndividualQuerySelect(pUser) == index);
        pUser.setCreationDate(LocalDateTime.MIN);
      
//        pUser.setPassword("TEST PASSWORD");
//        index++;
//        assertTrue(testIndividualQuerySelect(pUser) == index);
    }

    /**
     * Test of modificar method, of class UserDAL.
     */
    @Test
    public void testModificar() throws Exception {
        System.out.println("modificar");
        User pUser = new User();
        pUser.setId(usuarioActual.getId());
        pUser.setName("Nombre UNIT TEST M");
        int expResult = 1;
        int result = UserDAL.modificar(pUser);
        assertEquals(expResult, result);
    }

    /**
     * Test of eliminar method, of class UserDAL.
     */
    @Test
    public void testEliminar() throws Exception {
        System.out.println("eliminar");
        int expResult = 1;
        int result = UserDAL.eliminar(usuarioActual);
        assertEquals(expResult, result);
        User deleteUser = UserDAL.GetById(usuarioActual);
          assertTrue(deleteUser.getId() == null);
    }

    /**
     * Test of modificarImgP method, of class UserDAL.
     */
//    @Test
//    public void testModificarImgP() throws Exception {
//        System.out.println("modificarImgP");
//        User pUser = null;
//        int expResult = 0;
//        int result = UserDAL.modificarImgP(pUser);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
