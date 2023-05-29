package platillostipicos.entidadesdenegocio;

import java.util.UUID;

public class UserRole {
    private UUID userId;
    private UUID roleId;
    private User user;
    private Role role;
    private int top_aux;

    public UserRole() {
    }

    public UserRole(UUID userId, UUID roleId, User user, Role role) {
        this.userId = userId;
        this.roleId = roleId;
        this.user = user;
        this.role = role;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }
}