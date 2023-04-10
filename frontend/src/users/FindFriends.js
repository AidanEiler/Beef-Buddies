import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams, Link } from "react-router-dom";

export default function FindFriends() {
  const [users, setUsers] = useState([]);
  const [endpoint, setEndpoint] = useState("DEFAULT");
  const { id: loggedInUserId } = useParams();

  useEffect(() => {
    const fetchUsers = async () => {
      const result = await axios.get(
        `http://localhost:8080/user/${loggedInUserId}/matches?type=${endpoint}`
      );

      const friends = await axios.get(
        `http://localhost:8080/user/${loggedInUserId}/friends`
      );

      const friendIds = friends.data.map((friend) => friend.id);

      const filteredUsers = result.data.filter(
        (user) => !friendIds.includes(user.id)
      );

      setUsers(filteredUsers);
    };

    fetchUsers();
  }, [loggedInUserId, endpoint]);

  const addFriend = async (userId, friendId) => {
    await axios.post(
      `http://localhost:8080/user/${userId}/addFriend/${friendId}`
    );
    window.location.reload();
  };

  const handleEndpointChange = (e) => {
    setEndpoint(e.target.value);
  };

  return (
    <div className="container">
      <h2 className="text-center m-4">All Users</h2>

      <div className="btn-group mb-4" role="group">
        <button
          type="button"
          className={`btn btn-${
            endpoint === "DEFAULT" ? "primary" : "secondary"
          }`}
          value="DEFAULT"
          onClick={handleEndpointChange}
        >
          Matches
        </button>
        <button
          type="button"
          className={`btn btn-${
            endpoint === "LEGS" ? "primary" : "secondary"
          }`}
          value="LEGS"
          onClick={handleEndpointChange}
        >
          Legs Matches
        </button>
        <button
          type="button"
          className={`btn btn-${
            endpoint === "ARMS" ? "primary" : "secondary"
          }`}
          value="ARMS"
          onClick={handleEndpointChange}
        >
          Arms Matches
        </button>
      </div>

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
