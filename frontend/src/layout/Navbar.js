// frontend/src/layout/Navbar.js
import React, { useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import { AuthContext } from "../AuthContext";
import "./Navbar.css";

export default function Navbar() {
  const { isLoggedIn, userId, setIsLoggedIn, setUserId } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    setIsLoggedIn(false);
    setUserId(null);
    navigate("/");
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
          <Link className="btn btn-outline-light" to="/home">
            Home
          </Link>
        </div>
      </nav>
    </div>
  );
}
