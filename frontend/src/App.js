// beefbuddies/frontend/src/App.js
import "./App.css";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import Navbar from "./layout/Navbar";
import Home from "./pages/Home";
import Landing from "./pages/Landing";

import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import AddUser from "./users/AddUser";
import EditUser from "./users/EditUser";
import ViewUser from "./users/ViewUser";
import LoginUser from "./users/LoginUser";
import ViewFriends from "./users/ViewFriends";
import MessageFriend from "./users/MessageFriend";
import FindFriends from "./users/FindFriends";
function App() {
  return (
    <div className="App">
      <Router>
        <Navbar />
        <Routes>
        <Route exact path="/" element={<Landing />} />
          <Route exact path="/home" element={<Home />} />
          <Route exact path="/adduser" element={<AddUser />} />
          <Route exact path="/edituser/:id" element={<EditUser />} />
          <Route exact path="/viewuser/:id" element={<ViewUser />} />
          <Route exact path="/login" element={<LoginUser />}></Route>
          <Route exact path="/viewfriends/:id" element={<ViewFriends />}></Route>
          <Route exact path="/messagefriend/:senderId/:receiverId" element={<MessageFriend />}></Route>
          <Route exact path="/findfriends/:id" element={<FindFriends />}></Route>

        </Routes>
      </Router>
    </div>
  );
}

export default App;