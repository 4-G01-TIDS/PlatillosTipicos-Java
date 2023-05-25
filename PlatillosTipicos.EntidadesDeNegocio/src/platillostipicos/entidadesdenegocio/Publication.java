package platillostipicos.entidadesdenegocio;

import java.time.LocalDateTime;
import java.util.UUID;

public class Publication {

    private int top_aux;
    private UUID Id;
    private boolean Repost;
    private String Description;
    private LocalDateTime PublicationDate;
    private UUID UserId;
    private UUID PublicationImagesId;
    private UUID RestaurantId;
    private User User;
//        private PublicationImages? PublicationImages;
//        private Restaurant? Restaurant;

    public Publication() {
    }

    public Publication(UUID Id, boolean Repost, String Description, LocalDateTime PublicationDate, UUID UserId, UUID PublicationImagesId, UUID RestaurantId) {
        this.Id = Id;
        this.Repost = Repost;
        this.Description = Description;
        this.PublicationDate = PublicationDate;
        this.UserId = UserId;
        this.PublicationImagesId = PublicationImagesId;
        this.RestaurantId = RestaurantId;
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

    public boolean isRepost() {
        return Repost;
    }

    public void setRepost(boolean Repost) {
        this.Repost = Repost;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public LocalDateTime getPublicationDate() {
        return PublicationDate;
    }

    public void setPublicationDate(LocalDateTime PublicationDate) {
        this.PublicationDate = PublicationDate;
    }

    public UUID getUserId() {
        return UserId;
    }

    public void setUserId(UUID UserId) {
        this.UserId = UserId;
    }

    public UUID getPublicationImagesId() {
        return PublicationImagesId;
    }

    public void setPublicationImagesId(UUID PublicationImagesId) {
        this.PublicationImagesId = PublicationImagesId;
    }

    public UUID getRestaurantId() {
        return RestaurantId;
    }

    public void setRestaurantId(UUID RestaurantId) {
        this.RestaurantId = RestaurantId;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User User) {
        this.User = User;
    }

    
}
