package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class User {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;
  private String name;
  private String email;
  private String password;
  private int bench;
  private int curl;
  private int squat;
  
  /*
   * Constructs a user given the username and password
   * @param myName Username of  the user
   * @param myPassword Password of the user
   * @return A User object
   */
  public User(String myName, String myPassword) {
	name = myName;
	password = myPassword;
	//compare uname and pword to database 
	//retrieve rest of info from database if it matches
}

  /*
   * Default constructor for a User
   */
  public User(){
	  id = null;
	  name = null;
	  email = null;
	  password = null;
	  bench = 0;
	  curl = 0;
	  squat = 0;
  }

  /*
   * Gets the max bench weight of a user
   * @return Max bench stat
   */
  public Integer getBench() {
	  return bench;
  }

  /*
   * Sets the max bench weight of a user
   */
  public void setBench(Integer bench) {
	  this.bench = bench;
  }

  /*
   * Gets the max curl weight of a user
   * @return Max curl stat
   */
  public Integer getCurl() {
	  return curl;
  }

  /*
   * Sets the max curl weight of a user
   */
  public void setCurl(Integer curl) {
	  this.curl = curl;
  }

  /*
   * Gets the max squat weight of a user
   * @return Max squat stat
   */
  public Integer getSquat() {
	  return squat;
  }

  /*
   * Sets the max squat weight of a user
   */
  public void setSquat(Integer squat) {
	  this.squat = squat;
  }

  /*
   * Gets the id of a user
   * @return User id
   */
  public Integer getId() {
	  return id;
  }

  /*
   * Sets the id of a user
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /*
   * Gets the name of a user
   * @return Name of user
   */
  public String getName() {
    return name;
  }

  /*
   * Sets the name of a user
   */
  public void setName(String name) {
    this.name = name;
  }

  /*
   * Gets a user's email
   * @return Email of user
   */
  public String getEmail() {
    return email;
  }

  /*
   * Sets the email of a user
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /*
   * Gets a user's password
   * @return The user's password
   */
  public String getPassword() {
	 return password;
  }
  
  /*
   * Sets a user's password
   */
  public void setPassword(String password) {
	 this.password = password;
  }
  
  /*
   * Converts the user's information to a string
   * @return User info
   */
  @Override
  public String toString() {
	  return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", bench=" + bench
			  + ", curl=" + curl + ", squat=" + squat + "]";
  }
}