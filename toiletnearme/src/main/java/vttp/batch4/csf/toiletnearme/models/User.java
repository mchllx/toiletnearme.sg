package vttp.batch4.csf.toiletnearme.models;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {

    private String userId;
    private String username;
    private String email;
    private String password;
    private Date createdOn;
    private Date updatedOn;
    private String firstName;
    private String lastName;
    private String profileImage;
    private List<Bookmark> bookmarks;
    private List<Review> reviews;
    private Set<Role> authorities = new HashSet<>();
    private boolean accountNonExpired;
    private boolean isEnabled;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;

    public User(String userId, String username, String email, String password, Date createdOn, Date updatedOn,
            String firstName, String lastName, String profileImage, List<Bookmark> bookmarks,
            List<Review> reviews, Set<Role> authorities, boolean accountNonExpired, boolean isEnabled,
            boolean accountNonLocked, boolean credentialsNonExpired) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileImage = profileImage;
        this.bookmarks = bookmarks;
        this.reviews = reviews;
        this.authorities = authorities;
        this.accountNonExpired = accountNonExpired;
        this.isEnabled = isEnabled;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
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
    public List<Bookmark> getBookmarks() { return bookmarks; }
    public void setBookmarks(List<Bookmark> bookmarks) { this.bookmarks = bookmarks; }
    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
    public Set<Role> getAuthorities() { return authorities; }
    public void setAuthorities(Set<Role> authorities) { this.authorities = authorities; }
    public boolean isAccountNonExpired() { return accountNonExpired; }
    public void setAccountNonExpired(boolean accountNonExpired) { this.accountNonExpired = accountNonExpired; }
    public boolean isEnabled() { return isEnabled; }
    public void setEnabled(boolean isEnabled) { this.isEnabled = isEnabled; }
    public boolean isAccountNonLocked() { return accountNonLocked; }
    public void setAccountNonLocked(boolean accountNonLocked) { this.accountNonLocked = accountNonLocked; }
    public boolean isCredentialsNonExpired() { return credentialsNonExpired; }
    public void setCredentialsNonExpired(boolean credentialsNonExpired) { this.credentialsNonExpired = credentialsNonExpired; }
   
    @Override
    public String toString() {
        return "User{userId=%s, username=%s, password=%s, email=%s, createdOn=%s, updatedOn=%s, firstName=%s, lastName=%s, profileImage=%s, bookmarks: %s, reviews: %s, authorities:%s, accountNonExpired:%b, isEnabled:%b, accountNonLocked:%b, credentialsNonExpired:%b}"
        .formatted(userId, username, email, password, createdOn.toString(), updatedOn.toString(), firstName, lastName
            ,profileImage, bookmarks, reviews, authorities, accountNonExpired, isEnabled, accountNonLocked, credentialsNonExpired);
    }

    public User fromSQL(SqlRowSet rs) {
        
        if (rs.getString("role") == "USER") {
            authorities.add(Role.ROLE_USER);
        } else {
            authorities.add(Role.ROLE_ADMIN);
        }

        return new User(
            rs.getString("user_id"),
            rs.getString("username"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getDate("created_on"),
            rs.getDate("last_update"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("profile_image"),
            bookmarks,
            reviews,
            authorities,
            rs.getBoolean("expired"),
            rs.getBoolean("enabled"),
            rs.getBoolean("locked"),
            rs.getBoolean("credentials") 
        );
    }
 
}
