/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package platillostipicos.accesoadatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author edefl
 */
public class ComunDBIT {
    
    public ComunDBIT() {
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

    /**
     * Test of obtenerConexion method, of class ComunDB.
     */
    @Test
    public void testObtenerConexion() throws Exception {
        System.out.println("obtenerConexion");
        boolean expResult = false;
        Connection result = ComunDB.obtenerConexion();
        assertEquals(expResult, result.isClosed());
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of createStatement method, of class ComunDB.
     */
    @Test
    public void testCreateStatement() throws Exception {
        System.out.println("createStatement");
        Connection pConn = ComunDB.obtenerConexion();
        boolean expResult = false;
        Statement result = ComunDB.createStatement(pConn);
        assertEquals(expResult, result.isClosed());
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of createPreparedStatement method, of class ComunDB.
     */
    @Test
    public void testCreatePreparedStatement() throws Exception {
        System.out.println("createPreparedStatement");
        Connection pConn = ComunDB.obtenerConexion();
        String pSql = "";
        boolean expResult = false;
        PreparedStatement result = ComunDB.createPreparedStatement(pConn, pSql);
        assertEquals(expResult, result.isClosed());
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of obtenerResultSet method, of class ComunDB.
     */
    @Test
    public void testObtenerResultSet_Statement_String() throws Exception {
        System.out.println("obtenerResultSet");
        Connection pConn = ComunDB.obtenerConexion();
        Statement pStatement = pConn.createStatement();
        String pSql = "SELECT TOP 5 * FROM Roles";
        boolean expResult = false;
        ResultSet result = ComunDB.obtenerResultSet(pStatement, pSql);
        assertEquals(expResult, result.isClosed());
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of obtenerResultSet method, of class ComunDB.
     */
    @Test
    public void testObtenerResultSet_PreparedStatement() throws Exception {
        System.out.println("obtenerResultSet");
        Connection pConn = ComunDB.obtenerConexion();
        String pSql = "SELECT TOP 5 * FROM Roles";
        PreparedStatement pPreparedStatement = ComunDB.createPreparedStatement(pConn, pSql);
        boolean expResult = false;
        ResultSet result = ComunDB.obtenerResultSet(pPreparedStatement);
        assertEquals(expResult, result.isClosed());;
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of ejecutarSQL method, of class ComunDB.
     */
    @Test
    public void testEjecutarSQL() throws Exception {
        System.out.println("ejecutarSQL");
        String pSql = "INSERT INTO Roles(RoleName) VALUES('TEST COMUNDB') ";
        int expResult = 0;
        int result = ComunDB.ejecutarSQL(pSql);
        assertNotEquals(expResult, result);
        String pSql2 = "DELETE FROM Roles WHERE RoleName='TEST COMUNDB'";
        int expResult2 = 0;
        int result2 = ComunDB.ejecutarSQL(pSql2);
        assertNotEquals(expResult2, result2);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
}
