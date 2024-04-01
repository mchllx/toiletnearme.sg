package vttp.batch4.csf.toiletnearme.models;

import java.util.Date;
import java.util.List;

public class User {

    private Integer userId;
    private String username;
    private String email;
    private String password;
    private Date createdOn;
    private String firstName;
    private String lastName;
    private String profileImage;
    private String role;
    private List<Review> reviews;

    public User(Integer userId, String username, String email, String password, Date createdOn, String firstName,
            String lastName, String profileImage, String role, List<Review> reviews) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdOn = createdOn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileImage = profileImage;
        this.role = role;
        this.reviews = reviews;
    }

    public User() {
    }
     
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Date getCreatedOn() { return createdOn; }
    public void setCreatedOn(Date createdOn) { this.createdOn = createdOn; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; } 

    
}
