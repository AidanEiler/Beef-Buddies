


// frontend/src/users/LoginUser.js
import React, { useState, useContext } from "react";
import { useNavigate, Link } from "react-router-dom"; // import the Link component
import axios from "axios";
import { AuthContext } from "../AuthContext";

/**
 *This component handles user account authentication and routes the logged in user to their profile page
 */
export default function LoginUser() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const { setIsLoggedIn, setUserId } = useContext(AuthContext);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/user/authenticate", {
        username,
        password,
      });

      if (response.status === 200) {
        const { id } = response.data;
        setIsLoggedIn(true);
        setUserId(id);
        navigate(`/viewuser/${id}`);
      }
    } catch (error) {
      if (error.response && error.response.status === 401) {
        alert("Invalid username or password");
      } else {
        alert("An error occurred. Please try again.");
      }
    }
  };

  return (
    <div className="container">
      <h2 className="text-center m-4">Login</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="username">Username</label>
          <input
            type="text"
            className="form-control"
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input
            type="password"
            className="form-control"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <button type="submit" className="btn btn-primary">
          Login
        </button>
      </form>
      <div className="text-center mt-4">
        Don't have an account?{" "}
        <Link to="/adduser">Create one now.</Link> {/* add the new Link component */}
      </div>
    </div>
  );
}
