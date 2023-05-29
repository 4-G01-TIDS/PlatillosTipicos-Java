package platillostipicos.entidadesdenegocio;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class User {

    private UUID Id;
    private String Name;
    private String LastName;
    private byte[] ImgUser;
    private String Email;
    private boolean EmailConfirmed;
    private String EmailConfirmationToken;
    private String Password;
    private boolean IsCustomer;
    private Date CreationDate;
    private boolean IsNative;
    private String Nationality;
    private String Dui;
    private boolean DuiConfirmed;
    private boolean Membership;
    private String PhoneNumber;
    private boolean PhoneNumberConfirmed;
    private List<UserRole> UserRoles;
    private int top_aux;

    public User() {
    }

    public User(UUID Id, String Name, String LastName, byte[] ImgUser, String Email, boolean EmailConfirmed, String Password, boolean IsCustomer, Date CreationDate, boolean IsNative, String Nationality, String Dui, boolean Membership, String PhoneNumber, boolean PhoneNumberConfirmed, List<UserRole> UserRoles) {
        this.Id = Id;
        this.Name = Name;
        this.LastName = LastName;
        this.ImgUser = ImgUser;
        this.Email = Email;
        this.EmailConfirmed = EmailConfirmed;
        this.Password = Password;
        this.IsCustomer = IsCustomer;
        this.CreationDate = CreationDate;
        this.IsNative = IsNative;
        this.Nationality = Nationality;
        this.Dui = Dui;
        this.Membership = Membership;
        this.PhoneNumber = PhoneNumber;
        this.UserRoles = UserRoles;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public byte[] getImgUser() {
        return ImgUser;
    }

    public void setImgUser(byte[] ImgUser) {
        this.ImgUser = ImgUser;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public boolean isEmailConfirmed() {
        return EmailConfirmed;
    }

    public void setEmailConfirmed(boolean EmailConfirmed) {
        this.EmailConfirmed = EmailConfirmed;
    }

    public String getEmailConfirmationToken() {
        return EmailConfirmationToken;
    }

    public void setEmailConfirmationToken(String EmailConfirmationToken) {
        this.EmailConfirmationToken = EmailConfirmationToken;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public boolean isIsCustomer() {
        return IsCustomer;
    }

    public void setIsCustomer(boolean IsCustomer) {
        this.IsCustomer = IsCustomer;
    }

    public Date getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(Date CreationDate) {
        this.CreationDate = CreationDate;
    }

    public boolean isIsNative() {
        return IsNative;
    }

    public void setIsNative(boolean IsNative) {
        this.IsNative = IsNative;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String Nationality) {
        this.Nationality = Nationality;
    }

    public String getDui() {
        return Dui;
    }

    public void setDui(String Dui) {
        this.Dui = Dui;
    }

    public boolean isDuiConfirmed() {
        return DuiConfirmed;
    }

    public void setDuiConfirmed(boolean DuiConfirmed) {
        this.DuiConfirmed = DuiConfirmed;
    }

    public boolean isMembership() {
        return Membership;
    }

    public void setMembership(boolean Membership) {
        this.Membership = Membership;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public boolean isPhoneNumberConfirmed() {
        return PhoneNumberConfirmed;
    }

    public void setPhoneNumberConfirmed(boolean PhoneNumberConfirmed) {
        this.PhoneNumberConfirmed = PhoneNumberConfirmed;
    }

    public List<UserRole> getUserRoles() {
        return UserRoles;
    }

    public void setUserRoles(List<UserRole> UserRoles) {
        this.UserRoles = UserRoles;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

}
