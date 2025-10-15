package dto;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private int userId;
    private String username;
    private String email;
    private String password;
    private String address;
    private int approved; // 0 or 1
    private int blocked;  // 0 or 1

    public UserDTO() {}

    // Getters & Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    

    public int getApproved() { 
        return approved; 
    }
    public void setApproved(int approved) { 
        this.approved = approved; 
    }

    public int getBlocked() {
        return blocked; 
    }
    public void setBlocked(int blocked) {
        this.blocked = blocked;
    }
}
