//backend/src/main/java/com/example/backend/model/User.java
/**

 This package contains the User class, which represents a user in the application.
 The class defines properties for the user's ID, username, first name, last name, email, password, and fitness stats (bench, squat, and curl).
 It also contains a Set of User entities representing the user's friends, as well as a byte array for the user's profile picture.
 The class is annotated with the Entity annotation to indicate that it is a persistent entity in the database.
 It also defines a ManyToMany relationship with itself using the friends Set and the user_friends join table.
 Additionally, the class defines a new field for the user's distance, which is used for user matching based two users' stats proximity.
 @author jomo
 */


package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String first_name;
    private String last_name;
    private String email;

    private String password;

    private Long bench = 0L;
    private Long squat = 0L;
    private Long curl = 0L;

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Lob
    private byte[] profilePicture; // new field for profile picture

    public Long getSquat() {
        return squat;
    }

    public void setSquat(Long squat) {
        this.squat = squat;
    }

    public Long getCurl() {
        return curl;
    }

    public void setCurl(Long curl) {
        this.curl = curl;
    }


    public Long getBench() {
        return bench;
    }

    public void setBench(Long bench) {
        this.bench = bench;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private Set<User> friends = new HashSet<>();

    // getters and setters for friends
    @JsonIgnore
    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public void addFriend(User friend) {
        friends.add(friend);
        friend.getFriends().add(this);
    }

    private Double distance; // Add a new field for the distance

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getDistance() {
        return distance;
    }


}