// frontend/src/layout/Navbar.js
import React, { useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import { AuthContext } from "../AuthContext";
import "./Navbar.css";

/**
 * The Navbar component displays the navigation bar at the top of the application.
 * It includes links to the landing page, create account page, login page, user profile page, friends page, and a logout button.
 */
export default function Navbar() {
  const { isLoggedIn, userId, setIsLoggedIn, setUserId } = useContext(AuthContext); // Access the AuthContext properties
  const navigate = useNavigate();

  /**
   * The handleLogout function is called when the user clicks the Logout button.
   * It sets the isLoggedIn and userId state variables to their initial values, and navigates the user to the landing page.
   */
  const handleLogout = () => {
    setIsLoggedIn(false); // Set the isLoggedIn state variable to false
    setUserId(null); // Set the userId state variable to null
    navigate("/"); // Navigate the user to the landing page
  };

  return (
    <div>
      <nav className="navbar navbar-expand-lg navbar-dark bg custom-navbar">
        <div className="container-fluid">
          <Link className="navbar-brand" to="/">
            BeefBuddies
          </Link>
          {!isLoggedIn && (
            <>
              <Link className="btn btn-outline-light" to="/adduser">
                Create an Account
              </Link>
              <Link className="btn btn-outline-light" to="/login">
                Login
              </Link>
            </>
          )}
          {isLoggedIn && (
            <>
              <Link className="btn btn-outline-light" to={`/viewuser/${userId}`}>
                Profile
              </Link>
              <Link className="btn btn-outline-light" to={`/viewfriends/${userId}`}>
                Friends
              </Link>
              <button className="btn btn-outline-light" onClick={handleLogout}>
                Logout
              </button>
            </>
          )}
          {/* <Link className="btn btn-outline-light" to="/home">
            Home
          </Link> */}
        </div>
      </nav>
    </div>
  );
}
