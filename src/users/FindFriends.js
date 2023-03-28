
//frontend/src/users/FindFriends.js
import React, { useState, useEffect } from "react";
import axios from "axios";
import {  useParams, Link } from "react-router-dom";

export default function FindFriends() {
    const [users, setUsers] = useState([]);
    const { id: loggedInUserId } = useParams();
  
    useEffect(() => {
      const fetchUsers = async () => {
        const result = await axios.get(
          `http://localhost:8080/users/basic?exclude=${loggedInUserId}`
        );
        setUsers(result.data);
      };
  
      fetchUsers();
    }, [loggedInUserId]);
  
    const addFriend = async (userId, friendId) => {
      await axios.post(
        `http://localhost:8080/user/${userId}/addFriend/${friendId}`
      );
    };

    
    return (
      <div className="container">
        <h2 className="text-center m-4">All Users</h2>
        <ul className="list-group">
          {users.map((user) => (
            <li
              key={user.id}
              className="list-group-item d-flex justify-content-between align-items-center"
            >
              {user.first_name} {user.last_name} ({user.username})
              <button
                className="btn btn-primary"
                onClick={() => addFriend(loggedInUserId, user.id)}
              >
                Add Friend
              </button>
            </li>
          ))}          
        </ul>

        <Link
            className="btn btn-outline-primary mx-2"
            to={`/viewUser/${loggedInUserId}`}
          >
            Back
          </Link>
      </div>
    );
  }
  

