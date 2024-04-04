package vttp.batch4.csf.toiletnearme.models;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class User {

    private String userId;
    private String username;
    private String email;
    private String password;
    private Date createdOn;
    private Date updatedOn;
    private String firstName;
    private String lastName;
    private String profileImage;
    private String role;
    private List<Bookmark> bookmarks;
    private List<Review> reviews;
    
    public User(String userId, String username, String email, String password, Date createdOn, Date updatedOn,
            String firstName, String lastName, String profileImage, String role
            , List<Bookmark> bookmarks, List<Review> reviews) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileImage = profileImage;
        this.role = role;
        this.bookmarks = bookmarks;
        this.reviews = reviews;
    }

    public User() {
    }
     
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Date getCreatedOn() { return createdOn; }
    public void setCreatedOn(Date createdOn) { this.createdOn = createdOn; }
    public Date getUpdatedOn() { return updatedOn; }
    public void setUpdatedOn(Date updatedOn) { this.updatedOn = updatedOn; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public List<Bookmark> getBookmarks() { return bookmarks; }
    public void setBookmarks(List<Bookmark> bookmarks) { this.bookmarks = bookmarks; }
    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
   
    @Override
    public String toString() {
        return "User{userId=%s, username=%s, email=%s, password=%.2f, createdOn=%s, updatedOn=%s, firstName=%s, lastName=%s, profileImage=%s, role: %s, bookmarks: %s, reviews: %s}"
        .formatted(userId, username, email, password, createdOn.toString(), updatedOn.toString(), firstName, lastName
            ,profileImage, role, bookmarks,reviews);
    }

    public User fromSQL(SqlRowSet rs) {
        return new User(
            rs.getString("user_id"),
            rs.getString("username"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getDate("created_on"),
            rs.getDate("updated_on"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("profile_image"),
            rs.getString("role"),
            bookmarks,
            reviews 
        );
    }

 
}
