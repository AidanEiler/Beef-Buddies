// frontend/src/index.js
import React, { useState } from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import { AuthContext } from "./AuthContext";

// Create a root instance to render the application
const root = ReactDOM.createRoot(document.getElementById("root"));

/**
 * The AppWrapper component is used to provide the AuthContext to the App component.
 * It creates state variables for isLoggedIn and userId, and provides them to the AuthContext component as well as to the App component.
 */
function AppWrapper() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userId, setUserId] = useState(null);


  return (
    // Provides the AuthContext to the App component
    <AuthContext.Provider value={{ isLoggedIn, setIsLoggedIn, userId, setUserId }}>
      <App />
    </AuthContext.Provider>
  );
}
// Render the AppWrapper component inside the root instance
root.render(
  <React.StrictMode>
    <AppWrapper />
  </React.StrictMode>
);

reportWebVitals();
