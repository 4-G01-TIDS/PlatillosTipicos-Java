
package platillostipicos.entidadesdenegocio;

import java.util.UUID;

public class RestaurantImages {
    private UUID Id ;
        private byte[] ImageRestaurant_menu ;
        private byte[] ImageRestaurant_principal ;
        private byte[] ImageRestaurant_1 ;
        private byte[] ImageRestaurant_2 ;
        private byte[] ImageRestaurant_3 ;
        private Restaurant Restaurant ;
        private int top_aux;

    public RestaurantImages() {
    }

    public RestaurantImages(UUID Id, byte[] ImageRestaurant_menu, byte[] ImageRestaurant_principal, byte[] ImageRestaurant_1, byte[] ImageRestaurant_2, byte[] ImageRestaurant_3) {
        this.Id = Id;
        this.ImageRestaurant_menu = ImageRestaurant_menu;
        this.ImageRestaurant_principal = ImageRestaurant_principal;
        this.ImageRestaurant_1 = ImageRestaurant_1;
        this.ImageRestaurant_2 = ImageRestaurant_2;
        this.ImageRestaurant_3 = ImageRestaurant_3;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID Id) {
        this.Id = Id;
    }

    public byte[] getImageRestaurant_menu() {
        return ImageRestaurant_menu;
    }

    public void setImageRestaurant_menu(byte[] ImageRestaurant_menu) {
        this.ImageRestaurant_menu = ImageRestaurant_menu;
    }

    public byte[] getImageRestaurant_principal() {
        return ImageRestaurant_principal;
    }

    public void setImageRestaurant_principal(byte[] ImageRestaurant_principal) {
        this.ImageRestaurant_principal = ImageRestaurant_principal;
    }

    public byte[] getImageRestaurant_1() {
        return ImageRestaurant_1;
    }

    public void setImageRestaurant_1(byte[] ImageRestaurant_1) {
        this.ImageRestaurant_1 = ImageRestaurant_1;
    }

    public byte[] getImageRestaurant_2() {
        return ImageRestaurant_2;
    }

    public void setImageRestaurant_2(byte[] ImageRestaurant_2) {
        this.ImageRestaurant_2 = ImageRestaurant_2;
    }

    public byte[] getImageRestaurant_3() {
        return ImageRestaurant_3;
    }

    public void setImageRestaurant_3(byte[] ImageRestaurant_3) {
        this.ImageRestaurant_3 = ImageRestaurant_3;
    }

    public Restaurant getRestaurant() {
        return Restaurant;
    }

    public void setRestaurant(Restaurant Restaurant) {
        this.Restaurant = Restaurant;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }
        
        
}
