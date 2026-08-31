import {useState} from"react";
import {useNavigate} from "react-router-dom"

function Login() {
    const [username,setUsername] = useState("");
    const [password, setPassword] = useState("");
    const[error,setError] = useState("");

    const navigate = useNavigate();
    const hanfleLogin = async(e) => {
        e.preventDefault();
        setError("");
        try {
            const response = await fetch (
                "http://localhost:8080/api/auth/login",
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        username:username,
                        password:password
                    })
                }
            );
            if(!response.ok) {
                throw new Error("Invalid username or password");
            }
            const data = await response.json();
            localStorage.setItem("token",data.token);
            navigate("/");
        }catch (error) {
            setError(error.message);
        }

    };
    return (
        <div>
            <h1>Login</h1>
            <form onSubmit={hanfleLogin}>
                <input
                    type = "text"
                    placeholder= "Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <button type="submit">
                    Login
                </button>
            </form>
            {error && (
                <p>{error}</p>
            )}
        </div>
    );
}
export default Login;