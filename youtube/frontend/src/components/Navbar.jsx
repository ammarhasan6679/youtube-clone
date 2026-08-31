import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
function Navbar() {
    const navigate = useNavigate();
    const [isLoggedIn, setIsLoggedIn] = useState(
        localStorage.getItem("token") !== null
    )
    const handleLogout = () => {
        localStorage.removeItem("token");
        setIsLoggedIn(false);
        navigate("/")
    }
    return(
        <nav className="navbar">
            <div className="logo">
                Youtube
            </div>
            <div>
                <input
                type="text"
                placeholder="Search"
                />
                <button>
                    🔍
                </button>
            </div>
            <div className="profile">
                👤
            </div>
            <div>
                {isLoggedIn ? (
                    <button onClick={handleLogout}
                    >Sign Out</button>
                ): (
                    <>
                        <Link to="/login">
                            <button>Login</button>
                        </Link>
                        <Link to="/register"> <button>Sign Up</button> </Link>
                    </>
                )}
            </div>
        </nav>
    );
}
export default Navbar;