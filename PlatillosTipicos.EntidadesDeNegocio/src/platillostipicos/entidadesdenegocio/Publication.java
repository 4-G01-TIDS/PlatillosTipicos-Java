package platillostipicos.entidadesdenegocio;

import java.time.LocalDateTime;
import java.util.UUID;

public class Publication {

    private int top_aux;
    private UUID id;
    private boolean repost;
    private String description;
    private LocalDateTime publicationDate;
    private UUID userId;
    private UUID publicationImagesId;
    private UUID restaurantId;
    private User user;
     private PublicationImages publicationImages;
    // private Restaurant restaurant;

    public Publication() {
    }

    public Publication(UUID id, boolean repost, String description, LocalDateTime publicationDate, UUID userId, UUID publicationImagesId, UUID restaurantId) {
        this.id = id;
        this.repost = repost;
        this.description = description;
        this.publicationDate = publicationDate;
        this.userId = userId;
        this.publicationImagesId = publicationImagesId;
        this.restaurantId = restaurantId;
    }

    public int getTop_aux() {
          return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isRepost() {
        return repost;
    }

    public void setRepost(boolean repost) {
        this.repost = repost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getPublicationImagesId() {
        return publicationImagesId;
    }

    public void setPublicationImagesId(UUID publicationImagesId) {
        this.publicationImagesId = publicationImagesId;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PublicationImages getPublicationImages() {
        return publicationImages;
    }

    public void setPublicationImages(PublicationImages publicationImages) {
        this.publicationImages = publicationImages;
    }
    
    
}
