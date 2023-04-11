import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link, useParams } from "react-router-dom";
/**
 * Displays a list of users and a "Message" button for each user.
 * When the "Message" button is clicked, it navigates to the "View Messages" page for that user.
 */
export default function Messages() {
    const [users, setUsers] = useState([]);

    const { id } = useParams();
  
    useEffect(() => {
      loadUsers();
    }, []);
    const loadUsers = async () => {
      const result = await axios.get("http://localhost:8080/users");
      setUsers(result.data);
    };
    return (
      <div className="container">
        <div className="py-4">
          <table className="table border shadow">
            <thead>
              <tr>
                <th scope="col">#</th>
                <th scope="col">Username</th>
  
              </tr>
            </thead>
            <tbody>
              {users.map((user, index) => (
                <tr>
                  <th scope="row" key={index}>
                    {index + 1}
                  </th>
                  <td>{user.username}</td>
                  <td>
                    <Link 
                    className="btn btn-primary mx-2"
                    to={`/viewmessages/${user.id}`}
                    >
                      Message
                      </Link>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    );
  }
