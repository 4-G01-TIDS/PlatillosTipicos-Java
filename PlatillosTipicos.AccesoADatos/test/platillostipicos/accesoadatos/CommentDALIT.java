package platillostipicos.accesoadatos;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import platillostipicos.entidadesdenegocio.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class CommentDALIT {

    private static Comment currentComment;

    public CommentDALIT() {
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
     * Test of getByPublicationId method, of class CommentDAL.
     */
    @Test
    public void test2GetByPublicationId() throws Exception {
        System.out.println("getByPublicationId");
        ArrayList<Comment> result = CommentDAL.getByPublicationId(currentComment.getPublicationId().toString());
        assertTrue(result.size() > 0);
        currentComment = result.get(0);
    }

    /**
     * Test of asignarDatosResultSet method, of class CommentDAL.
     */
    @Test
    public void test3AsignarDatosResultSet() throws Exception {
        System.out.println("asignarDatosResultSet");
        Comment pComment = new Comment();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = "SELECT u.Id, u.Content, u.CreateDate, u.UserId, u.PublicationId FROM Comments u";
            sql += " WHERE u.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, currentComment.getId().toString());
                try (ResultSet resultSet = ComunDB.obtenerResultSet(ps);) {
                    while (resultSet.next()) {
                        CommentDAL.asignarDatosResultSet(pComment, resultSet, 0);
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
        assertTrue(pComment.getId().equals(currentComment.getId()));
        Publication pPublication = new Publication();
        PublicationDAL.eliminar(PublicationDAL.getPublications(pPublication).get(0));
    }

    /**
     * Test of crear method, of class CommentDAL.
     */
    @Test
    public void test1Crear() throws Exception {
        System.out.println("crear");
        PublicationDALIT publication = new PublicationDALIT();
        publication.test1Crear();
        Comment pComment = new Comment();
        pComment.setContent("Content UNIT TEST");
        pComment.setCreateDate(LocalDateTime.MAX);
        pComment.setUserId(UUID.fromString("23366d79-f322-4448-9e55-1518fc0c34b0"));
        Publication pPublication = new Publication();
        pComment.setPublicationId(PublicationDAL.getPublications(pPublication).get(0).getId());
        int expResult = 1;
        int result = CommentDAL.crear(pComment);
        assertEquals(expResult, result);
        currentComment = pComment;
    }

}
