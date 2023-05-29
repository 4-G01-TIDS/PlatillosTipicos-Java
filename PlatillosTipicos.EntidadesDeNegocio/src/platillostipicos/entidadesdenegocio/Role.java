package platillostipicos.entidadesdenegocio;

import java.util.ArrayList;
import java.util.UUID;
public class Role {
    private UUID RoleID;
    private UUID RoleName;
    private ArrayList<UserRole> UserRoles;
    private int top_aux;

    public Role() {
    }

    public Role(UUID RoleID, UUID RoleName, ArrayList<UserRole> UserRoles) {
        this.RoleID = RoleID;
        this.RoleName = RoleName;
        this.UserRoles = UserRoles;
    }

    public UUID getRoleID() {
        return RoleID;
    }

    public void setRoleID(UUID RoleID) {
        this.RoleID = RoleID;
    }

    public UUID getRoleName() {
        return RoleName;
    }

    public void setRoleName(UUID RoleName) {
        this.RoleName = RoleName;
    }

    public ArrayList<UserRole> getUserRoles() {
        return UserRoles;
    }

    public void setUserRoles(ArrayList<UserRole> UserRoles) {
        this.UserRoles = UserRoles;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }
}