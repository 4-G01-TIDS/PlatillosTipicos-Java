/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package platillostipicos.accesoadatos;

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
public class PublicationDALIT {

    public PublicationDALIT() {
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

    @Test
    public void testObtenerCampos() {
        System.out.println("obtenerCampos");
        String expResult = "u.Id, u.Description, u.PublicationDate, u.UserId, u.PublicationImagesId, u.RestaurantId, u.User";
        String result = PublicationDAL.obtenerCampos();
        assertEquals(expResult, result);
    }

}
