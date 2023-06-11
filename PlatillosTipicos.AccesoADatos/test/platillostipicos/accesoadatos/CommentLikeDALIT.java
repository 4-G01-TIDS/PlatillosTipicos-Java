package platillostipicos.accesoadatos;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import platillostipicos.entidadesdenegocio.Comment;
import platillostipicos.entidadesdenegocio.CommentLike;
import platillostipicos.entidadesdenegocio.Publication;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommentLikeDALIT {
    private static CommentLike currentCommentLike;
    
    public CommentLikeDALIT() {
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
     * Test of NewLike method, of class CommentLikeDAL.
     */
    @Test
    public void test1NewLike() throws Exception {
        System.out.println("NewLike");
        CommentLike pCommentLike = new CommentLike();
        CommentDALIT comment = new CommentDALIT();
        comment.test1Crear();
        pCommentLike.setIsLike(false);
        pCommentLike.setCreateDate(LocalDateTime.MAX);
        pCommentLike.setUserId(UUID.fromString("23366D79-F322-4448-9E55-1518FC0C34B0"));
        Publication publication = new Publication();
        Comment pComment = CommentDAL.getByPublicationId(PublicationDAL.getPublications(publication).get(0).getId().toString()).get(0);
        pCommentLike.setCommentId(pComment.getId());
        int expResult = 1;
        int result = CommentLikeDAL.NewLike(pCommentLike);
        assertEquals(expResult, result);
        currentCommentLike = pCommentLike;
    }

    /**
     * Test of getByPublicationId method, of class CommentLikeDAL.
     */
    @Test
    public void test2GetByPublicationId() throws Exception {
        System.out.println("getByPublicationId");
        CommentLike result = CommentLikeDAL.getByPublicationId(currentCommentLike);
        currentCommentLike = result;
        assertTrue(currentCommentLike.getId() != null);
    }

    /**
     * Test of eliminar method, of class CommentLikeDAL.
     */
    @Test
    public void test4Eliminar() throws Exception {
        System.out.println("eliminar");
        int expResult = 1;
        int result = CommentLikeDAL.eliminar(currentCommentLike);
        assertEquals(expResult, result);
        Publication pPublication = new Publication();
        assertTrue(PublicationDAL.eliminar(PublicationDAL.getPublications(pPublication).get(0)) == 1);
    }

    /**
     * Test of update method, of class CommentLikeDAL.
     */
    @Test
    public void test3Update() throws Exception {
        System.out.println("update");
        CommentLike pCommentLike = new CommentLike();
        pCommentLike = currentCommentLike;
        pCommentLike.setIsLike(true);
        int expResult = 1;
        int result = CommentLikeDAL.update(pCommentLike);
        assertEquals(expResult, result);
    }
    
}
