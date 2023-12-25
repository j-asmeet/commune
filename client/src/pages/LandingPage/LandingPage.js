import React from 'react';
import './LandingPage.css';
import { NavLink } from 'react-router-dom';

function LandingPage() {
    return (
        <>
            <div className="landing-page parent-container d-flex align-items-center min-vh-100">
                <div className='overlay'></div>
                <div className="container text-center text-light">
                    <h1 className="display-3">Welcome to Our Events and Community Hub</h1>
                    <div>
                        <p className="landing-description">Discover and join vibrant communities that share your passions.</p>
                        <p className="landing-description">Immerse yourself in unforgettable events that spark inspiration.</p>
                        <p className="landing-description">Together, let's create moments that unite and empower us all.</p>
                    </div>
                    <NavLink to="/signup"><button className="next-btn">Get Started</button></NavLink>
                </div>
            </div>
            {/* <NavLink to="/onboarding">
                <button>
                    Onboarding
                </button>
            </NavLink>
            <NavLink to="/user/login">
                <button>
                    login
                </button>
            </NavLink>
            <NavLink to="/create-community">
                <button>
                    Create community
                </button>
            </NavLink>
            <NavLink to="/user/my-community">
                <button>
                    My communities
                </button>
            </NavLink> */}
        </>
    );
}

export default LandingPage;