// frontend/src/users/ViewUser.js
import React from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import axios from "axios";
/**
 * The ViewUser component displays a profile information and handles the requests to other links for finding friends and viewing friends.
 */
export default function ViewUser() {
  const [user, setUser] = useState({
    first_name: "",
    last_name: "",
    username: "",
    email: "",
    password: "",
  });
  

  const { id } = useParams();
  useEffect(() => {
    loadUser();
  }, []);

  const loadUser = async () => {
    const result = await axios.get(`http://localhost:8080/user/${id}`);
    setUser(result.data);
  };
  const navigate = useNavigate();

  const goToViewFriends = () => {
    navigate(`/viewfriends/${id}`);
  };

  const goToFindFriends = () => {
    navigate(`/findfriends/${id}`);
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2">
          <h2 className="text-center m-4"> User Details</h2>

          <div className="card">
            <div className="card-header">
              <ul className="list-group list-group-flush">
                <li className="list-group-item">
                  <b>First Name:</b>
                  {user.first_name}
                </li>
                <li className="list-group-item">
                  <b>Last Name:</b>
                  {user.last_name}
                </li>
                <li className="list-group-item">
                  <b>Username:</b>
                  {user.username}
                </li>
                <li className="list-group-item">
                  <b>Email:</b>
                  {user.email}
                </li>
               
              </ul>
            </div>
          </div>
          <Link className="btn btn-primary my-2" to={"/"}>
            Back to Home
          </Link>
          <Link
            className="btn btn-outline-primary mx-2"
            to={`/edituser/${user.id}`}
          >
            Edit
          </Link>
          <button
            className="btn btn-secondary my-2 mx-2"
            onClick={goToViewFriends}
          >
            View Buddies
          </button>
          <button
            className="btn btn-secondary my-2 mx-2"
            onClick={goToFindFriends}
          >
            Find Buddies
          </button>
        </div>
      </div>
    </div>
  );
}
