package platillostipicos.accesoadatos;

import java.sql.ResultSet;
import java.util.UUID;
import platillostipicos.entidadesdenegocio.PublicationImages;

public class PublicationImagesDAL {

    static String obtenerCampos() {
        return "r.Id, r.ImagePublication1, r.ImagePublication2, r.ImagePublication3, r.ImagePublication4, r.ImagePublication5";
    }

    static int asignarDatosResultSet(PublicationImages pPublicationImages, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        String idStr = pResultSet.getString(pIndex);
        if (idStr != null) {
            pPublicationImages.setId(UUID.fromString(idStr));  // index 1
        } else {
            pPublicationImages.setId(null);  // index 1
            pPublicationImages.setImagePublication1(null);  // index 2
            pPublicationImages.setImagePublication2(null);  // index 3
            pPublicationImages.setImagePublication3(null);  // index 4
            pPublicationImages.setImagePublication4(null);  // index 5
            pPublicationImages.setImagePublication5(null);  // index 6
            return pIndex + 6;
        }
        pIndex++;
        pPublicationImages.setImagePublication1(pResultSet.getBytes(pIndex)); // index 2
        pIndex++;
        pPublicationImages.setImagePublication2(pResultSet.getBytes(pIndex)); // index 2
        pIndex++;
        pPublicationImages.setImagePublication3(pResultSet.getBytes(pIndex)); // index 2
        pIndex++;
        pPublicationImages.setImagePublication4(pResultSet.getBytes(pIndex)); // index 2
        pIndex++;
        pPublicationImages.setImagePublication5(pResultSet.getBytes(pIndex)); // index 2

        return pIndex;
    }

}
