
package platillostipicos.entidadesdenegocio;

import java.util.UUID;

public class RestaurantUser {
    
     private UUID RestaurantId;
     private UUID UserId;
    private int top_aux;

    public RestaurantUser() {
    }

    public RestaurantUser(UUID RestaurantId, UUID UserId) {
        this.RestaurantId = RestaurantId;
        this.UserId = UserId;
    }

    public UUID getRestaurantId() {
        return RestaurantId;
    }

    public void setRestaurantId(UUID RestaurantId) {
        this.RestaurantId = RestaurantId;
    }

    public UUID getUserId() {
        return UserId;
    }

    public void setUserId(UUID UserId) {
        this.UserId = UserId;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }
    
    
    
}
