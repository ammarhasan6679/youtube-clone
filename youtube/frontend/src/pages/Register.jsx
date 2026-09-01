import {useState} from "react";
import {useNavigate} from "react-router-dom";



function Register() {
    const [username,setUsername] = useState("");
    const[displayName,setDisplayName] = useState("");
    const[email,setEmail] = useState("");
    const[password,setPassword] = useState("");
    const[error,setError] = useState("");

    const navigate = useNavigate();

    const handleRegister = async(e) => {
        e.preventDefault();
        setError("");
        try {
            const response = await fetch(
                "http://localhost:8080/api/auth/register",
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        username: username,
                        displayName: displayName,
                        email: email,
                        password: password
                    })
                }
            );
            if (!response.ok) {
                throw new Error("Registration failed");
            }
            navigate("/login");
        }catch (error) {
            setError(error.message);
        }
    };

    return (
        <div>
            <h1>Create Account</h1>
            <form onSubmit={handleRegister}>
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
                <input
                    type="text"
                    placeholder="DisplayName"
                    value={displayName}
                    onChange={(e) => setDisplayName(e.target.value)}
                />
                <input
                    type = "email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <button type="submit">
                    Register
                </button>
            </form>
            {error && (
                <p>{error}</p>
            )}
        </div>
    );
}
export default Register;