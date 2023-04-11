import React from 'react';
import './Landing.css';
import { useNavigate } from 'react-router-dom'; 

/**

 * This component renders the landing page of the BeefBuddies app.
 * It displays a brief description of the app and two CTA buttons, "Learn More" and "Get Started".
 * The "Get Started" button redirects the user to the login page using the useNavigate hook from react-router-dom.
 */
function MainContent() {
  const navigate = useNavigate(); // initialize the useNavigate hook

  const handleGetStartedClick = () => {
    navigate('/login'); // redirect the user to the login page when the "Get Started" button is clicked
  };

  return (
    <main className="main-content">
      <h2>Find your gym partner today with BeefBuddies</h2>
      <p>BeefBuddies is an app for gym-goers who want to find a workout partner. Whether you're looking for someone to spot you during heavy lifts or just need a little extra motivation, BeefBuddies has got you covered.</p>
      <div className="cta-buttons">
        <button className="cta-btn">Learn More</button>
        <button className="cta-btn" onClick={handleGetStartedClick}>Get Started</button>
      </div>
    </main>
  );
}

function Footer() {
  return (
    <footer className="footer">
      <p>&copy; 2023 BeefBuddies. All rights reserved.</p>
    </footer>
  );
}

function Landing() {
  return (
    <div>
      <MainContent />
      <Footer />
    </div>
  );
}

export default Landing;
