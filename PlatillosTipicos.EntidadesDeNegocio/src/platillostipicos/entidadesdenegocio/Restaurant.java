package platillostipicos.entidadesdenegocio;

import java.time.LocalDateTime;

public class Restaurant {
     private int Id;
    private String Name;
    private String Description;
    private float Latitude;
    private float Longitude;
    private String PhoneNumber;
    private boolean PhoneNumberConfirmed;
    private String Website;
    private String Instagram;
    private String Facebook;
    private String Whatsapp;
    private boolean IsApproved;
    private LocalDateTime CreationDate;
    private int UserId;
    private int MunicipalityId;
    private int RestaurantImagesId;
    //private Municipality Municipality;
    //private RestaurantImages RestaurantImages;
    private User User;
    private int top_aux;
    
    public Restaurant() {
    }

    public Restaurant(int Id, String Name, String Description, float Latitude, float Longitude, String PhoneNumber, boolean PhoneNumberConfirmed, String Website, String Instagram, String Facebook, String Whatsapp, boolean IsApproved, LocalDateTime CreationDate, int UserId, int MunicipalityId, int RestaurantImagesId) {
        this.Id = Id;
        this.Name = Name;
        this.Description = Description;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.PhoneNumber = PhoneNumber;
        this.PhoneNumberConfirmed = PhoneNumberConfirmed;
        this.Website = Website;
        this.Instagram = Instagram;
        this.Facebook = Facebook;
        this.Whatsapp = Whatsapp;
        this.IsApproved = IsApproved;
        this.CreationDate = CreationDate;
        this.UserId = UserId;
        this.MunicipalityId = MunicipalityId;
        this.RestaurantImagesId = RestaurantImagesId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public float getLatitude() {
        return Latitude;
    }

    public void setLatitude(float Latitude) {
        this.Latitude = Latitude;
    }

    public float getLongitude() {
        return Longitude;
    }

    public void setLongitude(float Longitude) {
        this.Longitude = Longitude;
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

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String Website) {
        this.Website = Website;
    }

    public String getInstagram() {
        return Instagram;
    }

    public void setInstagram(String Instagram) {
        this.Instagram = Instagram;
    }

    public String getFacebook() {
        return Facebook;
    }

    public void setFacebook(String Facebook) {
        this.Facebook = Facebook;
    }

    public String getWhatsapp() {
        return Whatsapp;
    }

    public void setWhatsapp(String Whatsapp) {
        this.Whatsapp = Whatsapp;
    }

    public boolean isIsApproved() {
        return IsApproved;
    }

    public void setIsApproved(boolean IsApproved) {
        this.IsApproved = IsApproved;
    }

    public LocalDateTime getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(LocalDateTime CreationDate) {
        this.CreationDate = CreationDate;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public int getMunicipalityId() {
        return MunicipalityId;
    }

    public void setMunicipalityId(int MunicipalityId) {
        this.MunicipalityId = MunicipalityId;
    }

    public int getRestaurantImagesId() {
        return RestaurantImagesId;
    }

    public void setRestaurantImagesId(int RestaurantImagesId) {
        this.RestaurantImagesId = RestaurantImagesId;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User User) {
        this.User = User;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }
    
}
