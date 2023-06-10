package platillostipicos.accesoadatos;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.*;
import org.junit.*;
import java.sql.*;
import static org.junit.Assert.*;
import org.junit.runners.MethodSorters;
import platillostipicos.entidadesdenegocio.Publication;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PublicationDALIT {

    private static Publication currentPublication;

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

    /**
     * Test of crear method, of class PublicationDAL.
     */
    @Test
    public void test1Crear() throws Exception {
        System.out.println("crear");
        Publication pPublication = new Publication();
        pPublication.setDescription("Description UNIT TEST");
        pPublication.setPublicationDate(LocalDateTime.MIN);
        pPublication.setUserId(UUID.fromString("23366d79-f322-4448-9e55-1518fc0c34b0"));
        pPublication.setRestaurantId(UUID.fromString("807487cc-fc4d-11ed-be56-0242ac120002"));
        int expResult = 1;
        int result = PublicationDAL.crear(pPublication);
        assertEquals(expResult, result);
    }

    public int testIndividualQuerySelect(Publication pPublication) throws Exception {
        ComunDB comundb = new ComunDB();
        ComunDB.UtilQuery pUtilQuery = comundb.new UtilQuery("", null, 0);
        PublicationDAL.querySelect(pPublication, pUtilQuery);
        return pUtilQuery.getNumWhere();
    }

    /**
     * Test of GetById method, of class PublicationDAL.
     */
    @Test
    public void test4GetById() throws Exception {
        System.out.println("GetById");
        Publication result = PublicationDAL.GetById(currentPublication);
        assertEquals(currentPublication.getId(), result.getId());
    }

    /**
     * Test of obtenerCampos method, of class PublicationDAL.
     */
    @Test
    public void test7ObtenerCampos() {
        System.out.println("obtenerCampos");
        String expResult = "";
        String result = PublicationDAL.obtenerCampos();
        assertNotEquals(expResult, result);
    }
    /**
     * Test of asignarDatosResultSet method, of class PublicationDAL.
     */
    @Test
    public void test6AsignarDatosResultSet() throws Exception {
        System.out.println("asignarDatosResultSet");
        Publication publication = new Publication();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = "SELECT " + PublicationDAL.obtenerCampos() + " FROM Publications u";
            sql += " WHERE u.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, currentPublication.getId().toString());
                try (ResultSet resultSet = ComunDB.obtenerResultSet(ps);) {
                    while (resultSet.next()) {
                        PublicationDAL.asignarDatosResultSet(publication, resultSet, 0);
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
        } catch (SQLException ex) {
            throw ex;
        }
        assertTrue(publication.getId().equals(currentPublication.getId()));
    }

    /**
     * Test of getPublications method, of classz PublicationDAL.
     */
    @Test
    public void test3GetPublications() throws Exception {
        System.out.println("getPublications");
        Publication pPublication = new Publication();
        pPublication.setDescription("Description UNIT TEST");
        pPublication.setUserId(UUID.fromString("23366d79-f322-4448-9e55-1518fc0c34b0"));
        pPublication.setRestaurantId(UUID.fromString("807487cc-fc4d-11ed-be56-0242ac120002"));
        ArrayList<Publication> result = PublicationDAL.getPublications(pPublication);
        assertTrue(result.size() > 0);
        currentPublication = result.get(0);
    }

    /**
     * Test of querySelect method, of class PublicationDAL.
     */
    @Test
    public void test2QuerySelect() throws Exception {
        System.out.println("querySelect");
        int index = 0;
        Publication pPublication = new Publication();
        pPublication.setId(UUID.randomUUID());
        index++;
        assertTrue(testIndividualQuerySelect(pPublication) == index);
        pPublication.setDescription("TEST");
        index++;
        assertTrue(testIndividualQuerySelect(pPublication) == index);
        pPublication.setPublicationDate(LocalDateTime.MIN);
        index++;
        assertTrue(testIndividualQuerySelect(pPublication) == index);
        pPublication.setUserId(UUID.randomUUID());
        index++;
        assertTrue(testIndividualQuerySelect(pPublication) == index);
        pPublication.setPublicationImagesId(UUID.randomUUID());
        index++;
        assertTrue(testIndividualQuerySelect(pPublication) == index);
        pPublication.setRestaurantId(UUID.randomUUID());
    }

    /**
     * Test of modificar method, of class PublicationDAL.
     */
    @Test
    public void test5Modificar() throws Exception {
        System.out.println("modificar");
        Publication pPublication = new Publication();
        pPublication.setId(currentPublication.getId());
        pPublication.setDescription("Description UNIT TEST M");

        int expResult = 1;
        int result = PublicationDAL.modificar(pPublication);
        assertEquals(expResult, result);
    }
    /**
     * Test of eliminar method, of class PublicationDAL.
     */
      @Test
    public void test8Eliminar() throws Exception {
        System.out.println("eliminar");
        int expResult = 1;
        int result = PublicationDAL.eliminar(currentPublication);
        assertEquals(expResult, result);
        Publication deletePublication = PublicationDAL.GetById(currentPublication);
          assertTrue(deletePublication.getId() == null);
    }
}
