
// frontend/src/users/MessageFriend.js
import React, { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import axios from "axios";

export default function MessageFriend() {
  const { senderId, receiverId } = useParams();
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");
  const[user, getUser]=useState({username: "",});


  useEffect(() => {
    loadMessages();
  }, []);
  useEffect(() => {
    loadUser();
  });

  
const loadUser = async () => {
    const user = await axios.get(
        `http://localhost:8080/user/${receiverId}`
    );
    getUser(user.data)
}

  const loadMessages = async () => {
    const result = await axios.get(
      `http://localhost:8080/messages/${senderId}/${receiverId}`
    );
    setMessages(result.data);
  };

  

  const sendMessage = async () => {
    const message = {
      sender: { id: senderId },
      receiver: { id: receiverId },
      content: newMessage,
    };
    await axios.post("http://localhost:8080/messages", message);
    setNewMessage("");
    loadMessages();
  };

  return (
    <div className="container">
      <h2 className="text-center m-4">Message to {user.username}
</h2> 
      <ul className="list-group">
        {messages.map((message) => (
          <li key={message.id} className={`${
            message.sender.id === parseInt(senderId)
              ? "message-box-blue"
              : "message-box-green"
          }`}
        >
          <p>{message.content}</p>
          </li>
        ))}
      </ul>
      <div className="input-group mb-3">
        <input
          type="text"
          className="form-control"
          placeholder="Type your message"
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
        />
        <button className="btn btn-primary" onClick={sendMessage}>
          Send
        </button>
      </div>
      <Link
            className="btn btn-outline-primary mx-2"
            to={`/viewFriends/${senderId}`}
          >
            Back
          </Link>
    </div>
  );
}



