package platillostipicos.accesoadatos;

import java.time.LocalDateTime;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import platillostipicos.entidadesdenegocio.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PublicationLikeDALIT {

    private static PublicationLike currentPublicationLike;

    public PublicationLikeDALIT() {
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
     * Test of NewLikePublication method, of class PublicationLikeDAL.
     */
    @Test
    public void test1NewLikePublication() throws Exception {
        System.out.println("NewLikePublication");
        PublicationLike pPublicationLike = new PublicationLike();
        PublicationDALIT publication = new PublicationDALIT();
        publication.test1Crear();
        pPublicationLike.setIsLike(false);
        pPublicationLike.setCreateDate(LocalDateTime.MAX);
        User user = new User();
        pPublicationLike.setUserId(UserDAL.buscar(user).get(0).getId());
        Publication pPublication = new Publication();
        pPublicationLike.setPublicationId(PublicationDAL.getPublications(pPublication).get(0).getId());
        int expResult = 1;
        int result = PublicationLikeDAL.NewLikePublication(pPublicationLike);
        assertEquals(expResult, result);
        currentPublicationLike = pPublicationLike;
    }

    /**
     * Test of getLikeByPublicationAndUser method, of class PublicationLikeDAL.
     */
    @Test
    public void test2GetLikeByPublicationAndUser() throws Exception {
        System.out.println("getLikeByPublicationAndUser");
        PublicationLike result = PublicationLikeDAL.getLikeByPublicationAndUser(currentPublicationLike);
        currentPublicationLike = result;
        assertTrue(currentPublicationLike.getId() != null);
    }

    /**
     * Test of eliminar method, of class PublicationLikeDAL.
     */
    @Test
    public void testEliminar() throws Exception {
        System.out.println("eliminar");
        int expResult = 1;
        int result = PublicationLikeDAL.eliminar(currentPublicationLike);
        assertEquals(expResult, result);
        Publication pPublication = new Publication();
        assertTrue(PublicationDAL.eliminar(PublicationDAL.getPublications(pPublication).get(0)) == 1);
    }

    /**
     * Test of update method, of class PublicationLikeDAL.
     */
    @Test
    public void test3Update() throws Exception {
        System.out.println("update");
        PublicationLike pPublicationLike = new PublicationLike();
        pPublicationLike = currentPublicationLike;
        pPublicationLike.setIsLike(true);
        int expResult = 1;
        int result = PublicationLikeDAL.update(pPublicationLike);
        assertEquals(expResult, result);
    }

}
