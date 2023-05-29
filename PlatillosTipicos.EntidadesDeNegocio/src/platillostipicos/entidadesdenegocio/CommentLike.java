package platillostipicos.entidadesdenegocio;

import java.time.LocalDateTime;
import java.util.UUID;

public class CommentLike {

    private int top_aux;
    private UUID Id;
    private boolean IsLike;
    private LocalDateTime CreateDate;
    private UUID UserId;
    private UUID CommentId;
    private User User;
    private Comment Comment;

    public CommentLike() {
    }

    public CommentLike(UUID Id, boolean IsLike, LocalDateTime CreateDate, UUID UserId, UUID CommentId) {
        this.Id = Id;
        this.IsLike = IsLike;
        this.CreateDate = CreateDate;
        this.UserId = UserId;
        this.CommentId = CommentId;
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

    public boolean isIsLike() {
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

    public UUID getCommentId() {
        return CommentId;
    }

    public void setCommentId(UUID CommentId) {
        this.CommentId = CommentId;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User User) {
        this.User = User;
    }

    public Comment getComment() {
        return Comment;
    }

    public void setComment(Comment Comment) {
        this.Comment = Comment;
    }

}
