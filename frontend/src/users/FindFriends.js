import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams, Link } from "react-router-dom";
/**
 *This component displays the list of users who match the logged-in user's criteria and allows the logged-in user to add them as friends.
 */
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
    
      const filteredUsers = result.data
        .filter((user) => !friendIds.includes(user.id))
        .map((user) => {
          const distance = user.distance;
          const maxDistance = result.data[result.data.length - 1].distance;
          const percent = 100 - (distance / maxDistance) * 100;
          return { ...user, percent };
        });
    
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
      className="list-group-item d-flex justify-content-start align-items-center"
    >
      <img
        src={`${process.env.PUBLIC_URL}/bb.png`}
        alt={`${user.first_name} ${user.last_name}`}
        width="50"
        height="50"
        className="me-3 rounded-circle"
      />
      <div className="d-flex flex-column">
        <span className="fw-bold">{user.username}</span>
        <span>{user.first_name} {user.last_name}</span>
      </div>
      <div className="flex-grow-1">
        <h5>{user.percent.toFixed(0)}%</h5>
        {user.percent === 100 && <span className="badge bg-dark">PERFECT MATCH</span>}
        {user.percent >= 76 && user.percent <= 99.5 && <span className="badge bg-success">GREAT MATCH!</span>}
        {user.percent >= 51 && user.percent <= 75 && <span className="badge bg-info">GOOD MATCH</span>}
        {user.percent >= 26 && user.percent <= 50 && <span className="badge bg-warning">OK MATCH</span>}
        {user.percent > 0 && user.percent <= 25 && <span className="badge bg-danger">BAD MATCH!</span>}
        {user.percent === 0 && <span className="badge bg-dark">TERRIBLE MATCH!!!</span>}

      </div>
      <div className="ms-auto">
        <button
          className="btn btn-primary"
          onClick={() => addFriend(loggedInUserId, user.id)}
        >
          Add Friend
        </button>
      </div>
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
