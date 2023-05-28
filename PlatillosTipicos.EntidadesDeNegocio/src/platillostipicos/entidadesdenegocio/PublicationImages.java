package platillostipicos.entidadesdenegocio;

import java.util.UUID;

public class PublicationImages {

    private int top_aux;
    private UUID Id;
    private byte[] ImagePublication1;
    private byte[] ImagePublication2;
    private byte[] ImagePublication3;
    private byte[] ImagePublication4;
    private byte[] ImagePublication5;

    public PublicationImages() {
    }

    public PublicationImages(UUID Id, byte[] ImagePublication1, byte[] ImagePublication2, byte[] ImagePublication3, byte[] ImagePublication4, byte[] ImagePublication5) {
        this.Id = Id;
        this.ImagePublication1 = ImagePublication1;
        this.ImagePublication2 = ImagePublication2;
        this.ImagePublication3 = ImagePublication3;
        this.ImagePublication4 = ImagePublication4;
        this.ImagePublication5 = ImagePublication5;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID Id) {
        this.Id = Id;
    }

    public byte[] getImagePublication1() {
        return ImagePublication1;
    }

    public void setImagePublication1(byte[] ImagePublication1) {
        this.ImagePublication1 = ImagePublication1;
    }

    public byte[] getImagePublication2() {
        return ImagePublication2;
    }

    public void setImagePublication2(byte[] ImagePublication2) {
        this.ImagePublication2 = ImagePublication2;
    }

    public byte[] getImagePublication3() {
        return ImagePublication3;
    }

    public void setImagePublication3(byte[] ImagePublication3) {
        this.ImagePublication3 = ImagePublication3;
    }

    public byte[] getImagePublication4() {
        return ImagePublication4;
    }

    public void setImagePublication4(byte[] ImagePublication4) {
        this.ImagePublication4 = ImagePublication4;
    }

    public byte[] getImagePublication5() {
        return ImagePublication5;
    }

    public void setImagePublication5(byte[] ImagePublication5) {
        this.ImagePublication5 = ImagePublication5;
    }

}
