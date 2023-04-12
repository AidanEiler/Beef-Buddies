import { useParams, useNavigate, Link } from "react-router-dom";
import React, { useState, useEffect } from "react";
import axios from "axios";
import './ViewFriends.css';

/**
 *This component displays the list of friends of the user and allows the user to navigate to message a friend or view the schedule.
 */
export default function ViewFriends() {
  const [friends, getFriends] = useState([]);
  const { id } = useParams();

  useEffect(() => {
    const fetchFriends = async () => {
      const result = await axios.get(`http://localhost:8080/user/${id}/friends`);
      console.log('API response:', result.data);
      getFriends(result.data);
    };

    fetchFriends();
  }, [id]);

  const navigate = useNavigate();

  const goToMessageFriend = (friendId) => {
    navigate(`/messageFriend/${id}/${friendId}`);
  };

 

  return (
    <div className="container">
      <h2 className="text-center m-4">My Buddies</h2>
      <ul className="list-group">
        {friends && friends.length > 0 ? (
          friends.map((friend) => (
            <div key={friend.id}>
              <li className="vflist-group-item">
                {friend.first_name} {friend.last_name} ({friend.username})
              </li>
              <button
                className="btn btn-secondary my-2 mx-2"
                onClick={() => goToMessageFriend(friend.id)}
              >
                Message Buddy
              </button>
            
            </div>
          ))
        ) : (
          <p>No buddies found.</p>
        )}
      </ul>

      <Link
            className="btn btn-outline-primary mx-2"
            to={`/viewUser/${id}`}
          >
            Back
          </Link>
    </div>
  );
}
