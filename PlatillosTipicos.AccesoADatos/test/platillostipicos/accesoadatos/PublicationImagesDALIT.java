package platillostipicos.accesoadatos;

import java.sql.*;
import java.util.UUID;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runners.MethodSorters;
import platillostipicos.entidadesdenegocio.PublicationImages;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PublicationImagesDALIT {

    private static PublicationImages currentPublicationImages;

    public PublicationImagesDALIT() {
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
     * Test of crearImagenes method, of class PublicationImagesDAL.
     */
    @Test
    public void test1CrearImagenes() throws Exception {
        System.out.println("crearImagenes");
        PublicationImages pPublicationImages = new PublicationImages();
        byte[] imageBytes = {(byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};
        pPublicationImages.setId(UUID.randomUUID());
        pPublicationImages.setImagePublication1(imageBytes);
        pPublicationImages.setImagePublication2(imageBytes);
        pPublicationImages.setImagePublication3(imageBytes);
        pPublicationImages.setImagePublication4(imageBytes);
        pPublicationImages.setImagePublication5(imageBytes);

        int expResult = 1;
        int result = PublicationImagesDAL.crearImagenes(pPublicationImages);
        assertEquals(expResult, result);
        currentPublicationImages = pPublicationImages;
    }

    /**
     * Test of GetByIdImgs method, of class PublicationImagesDAL.
     */
    @Test
    public void test2GetByIdImgs() throws Exception {
        System.out.println("GetByIdImgs");
        PublicationImages result = PublicationImagesDAL.GetByIdImgs(currentPublicationImages);
        assertEquals(currentPublicationImages.getId(), result.getId());
    }

    /**
     * Test of obtenerCampos method, of class PublicationImagesDAL.
     */
    @Test
    public void testObtenerCampos() {
        System.out.println("obtenerCampos");
        String expResult = "";
        String result = PublicationImagesDAL.obtenerCampos();
        assertNotEquals(expResult, result);
    }

    /**
     * Test of asignarDatosResultSet method, of class PublicationImagesDAL.
     */
    @Test
    public void test4AsignarDatosResultSet() throws Exception {
        System.out.println("asignarDatosResultSet");
        PublicationImages publicationImgs = new PublicationImages();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = "SELECT " + PublicationImagesDAL.obtenerCampos() + " FROM PublicationImages r";
            sql += " WHERE r.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, currentPublicationImages.getId().toString());
                try (ResultSet resultSet = ComunDB.obtenerResultSet(ps);) {
                    while (resultSet.next()) {
                        PublicationImagesDAL.asignarDatosResultSet(publicationImgs, resultSet, 0);
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
        assertTrue(publicationImgs.getId().equals(currentPublicationImages.getId()));
    }

    /**
     * Test of modificarImgP method, of class PublicationImagesDAL.
     */
    @Test
    public void test3ModificarImgP() throws Exception {
        System.out.println("modificarImgP");
        PublicationImages pPublicationImages = new PublicationImages();
        pPublicationImages.setImagePublication1(null);
        pPublicationImages.setImagePublication2(null);
        pPublicationImages.setImagePublication3(null);
        pPublicationImages.setImagePublication4(null);
        pPublicationImages.setImagePublication5(null);
        pPublicationImages.setId(currentPublicationImages.getId());
        int expResult = 1;
        int result = PublicationImagesDAL.modificarImgP(pPublicationImages);
        assertEquals(expResult, result);
    }

    /**
     * Test of eliminarImagenes method, of class PublicationImagesDAL.
     */
    @Test
    public void testEliminarImagenes() throws Exception {
        System.out.println("eliminarImagenes");
        int expResult = 1;
        int result = PublicationImagesDAL.eliminarImagenes(currentPublicationImages);
        assertEquals(expResult, result);
    }
}
