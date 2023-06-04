package platillostipicos.entidadesdenegocio;

import java.time.LocalDateTime;
import java.util.UUID;

public class PublicationLike {

    private int top_aux;
    private UUID Id;
    private boolean IsLike;
    private LocalDateTime CreateDate;
    private UUID UserId;
    private UUID PublicationId;
    private User User;
    private Publication Publication;

    public PublicationLike() {
    }

    public PublicationLike(UUID Id, boolean IsLike, LocalDateTime CreateDate, UUID UserId, UUID PublicationId) {
        this.Id = Id;
        this.IsLike = IsLike;
        this.CreateDate = CreateDate;
        this.UserId = UserId;
        this.PublicationId = PublicationId;
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

    public boolean getIsIsLike() {
        return IsLike;
    }

    public void setIsLike(boolean IsLike) {
        this.IsLike = IsLike;
    }

    public LocalDateTime getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(LocalDateTime CreateDate) {
        this.CreateDate = CreateDate;
    }

    public UUID getUserId() {
        return UserId;
    }

    public void setUserId(UUID UserId) {
        this.UserId = UserId;
    }

    public UUID getPublicationId() {
        return PublicationId;
    }

    public void setPublicationId(UUID PublicationId) {
        this.PublicationId = PublicationId;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User User) {
        this.User = User;
    }

    public Publication getPublication() {
        return Publication;
    }

    public void setPublication(Publication Publication) {
        this.Publication = Publication;
    }

}
