
//frontend/src/users/EditUser.js
import React, { useEffect } from "react";
import axios from "axios";
import { useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";

/**
 * The EditUser component displays a profile information and handles the user's edit request.
 */
export default function EditUser() {

    let navigate=useNavigate();
    const{id} = useParams();
    const [user,setUser]=useState({
        first_name:"",
        last_name:"",
        username:"",
        email:"",
        password:"", 
        bench: "",
        squat: "",
        curl: "",
    });

    const{first_name,last_name,username,email,password, bench, squat, curl}=user;

    const onInputChange=(e)=>{
        setUser({...user,[e.target.name]:e.target.value});
    };

    useEffect(()=>{
        loadUser();
    }, []);

    const onSubmit=async (e)=>{
        e.preventDefault();
        await axios.put(`http://localhost:8080/user/${id}`,user);
        navigate(`/viewuser/${id}`);
      };

      const loadUser = async()=>{
        const result=await axios.get(`http://localhost:8080/user/${id}`)
        setUser(result.data)
    }
  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2">
          <h2 className="text-center m-4"> Edit User</h2>
          <form onSubmit={(e)=>onSubmit(e)}>
          <div className="mb-3">
            <label htmlFor="First Name" className="form-label">
              First Name
            </label>
            <input
              type={"text"}
              className="form-control"
              placeholder="Enter your first name"
              name="first_name"
              value={first_name}
              onChange={(e)=>onInputChange(e)}
            />
          </div>
          <div className="mb-3">
            <label htmlFor="Last Name" className="form-label">
              Last Name
            </label>
            <input
              type={"text"}
              className="form-control"
              placeholder="Enter your last name"
              name="last_name"
              value={last_name}
              onChange={(e)=>onInputChange(e)}

            />
          </div>
          <div className="mb-3">
            <label htmlFor="Username" className="form-label">
              Username
            </label>
            <input
              type={"text"}
              className="form-control"
              placeholder="Enter your Username"
              name="username"
              value={username}
              onChange={(e)=>onInputChange(e)}

            />
          </div>
          <div className="mb-3">
            <label htmlFor="Email" className="form-label">
              E-mail
            </label>
            <input
              type={"text"}
              className="form-control"
              placeholder="Enter your e-mail"
              name="email"
              value={email}
              onChange={(e)=>onInputChange(e)}
            />
          </div>
          <div className="mb-3">
            <label htmlFor="Password" className="form-label">
              Password
            </label>
            <input
              type={"text"}
              className="form-control"
              placeholder="Enter your password"
              name="password"
              value={password}
              onChange={(e)=>onInputChange(e)}
            />
          </div>
          <div className="mb-3">
            <label htmlFor="Bench" className="form-label">
              Bench
            </label>
            <input
              type={"text"}
              className="form-control"
              placeholder="Enter your max bench"
              name="bench"
              value={bench}
              onChange={(e)=>onInputChange(e)}
            />
          </div>
          <div className="mb-3">
            <label htmlFor="Squat" className="form-label">
              Squat
            </label>
            <input
              type={"text"}
              className="form-control"
              placeholder="Enter your max squat"
              name="squat"
              value={squat}
              onChange={(e)=>onInputChange(e)}
            />
          </div>
          <div className="mb-3">
            <label htmlFor="Curl" className="form-label">
              Curl
            </label>
            <input
              type={"text"}
              className="form-control"
              placeholder="Enter your max curl"
              name="curl"
              value={curl}
              onChange={(e)=>onInputChange(e)}
            />
          </div>
          
          <button type="submit" className="btn btn-outline-primary">submit</button>
          
          <Link className="btn btn-outline-danger mx-2" to="/">cancel</Link>
          <Link
        className="btn btn-outline-primary mx-2"
        to={`/viewUser/${id}`}
      >
        Back
      </Link>
          </form>
        </div>
      </div>
    </div>
  );
}



