import {useNavigate} from "react-router-dom";

function Sidebar() {
    const navigate = useNavigate();
    return (
        <aside className="sidebar">
            <div>
                Home
            </div>
            <div onClick={() => navigate("/trending")}>
                Trending
            </div>
            <div onClick={() => navigate("/subscriptions")}>
                Subscriptions
            </div>
            <hr/>
            <div
                onClick={() => navigate("/history")}
                style={{cursor: "pointer"}}
            >
                History
            </div>
            <div
                onClick={() => navigate("/watch-later")}
                style={{cursor: "pointer"}}
            >
                Watch Later
            </div>
            <div
                onClick={() => {
                    console.log("PLAYLIST CLICKED");
                    navigate("/playlists");
                }}>
                Playlists
            </div>
        </aside>
    );
}
export default Sidebar;