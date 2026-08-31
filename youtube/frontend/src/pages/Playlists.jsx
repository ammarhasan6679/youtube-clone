import {useEffect,useState} from "react";
import {useNavigate} from "react-router-dom";

function Playlists() {
    const navigate = useNavigate();
    const [playlists,setPlaylists] = useState([]);
    const [playlistName,setPlaylistName] = useState("");
    const fetchPlaylists = () => {
        const token = localStorage.getItem("token");
        fetch("http://localhost:8080/api/playlists", {
            method: "GET",
            headers : {
                "Authorization" : `Bearer ${token}`
            }
        }).then(response => {
            if(!response.ok) {
                throw new Error("Failed to fetch playlists");
            }
            return response.json();
        })
            .then(data => {
                console.log("PLAYLISTS:",data);
                setPlaylists(data);
            })
            .catch(error => {
                console.error("Error fetching playlists:",error);
            });
    };
    useEffect(() => {
        fetchPlaylists();
    },[]);
    const createPlaylist = () => {
        if(!playlistName.trim()) {
            return;
        }
        const token = localStorage.getItem("token");
        fetch("http://localhost:8080/api/playlists", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            body: JSON.stringify({
                name:playlistName
            })
        }).then(response =>{
            if(!response.ok) {
                throw new Error("Failed to create playlist");
            }
            setPlaylistName("");
            fetchPlaylists();
        }).catch(error => {
            console.log("Error creating playlists:" , error);
        })
    };
    const removeVideoFromPlaylist = (playlistId,videoId) => {
        const token = localStorage.getItem("token");
        fetch(`http://localhost:8080/api/playlists/${playlistId}/videos/${videoId}`,
            {
                method: "DELETE",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })
            .then(response => {
                if(!response.ok) {
                    throw new Error("Failed to remove video from playlsit");
                }
                fetchPlaylists();
            }).catch(error => {
                console.error("Error removing video from playlsits:",error);
        });
    };
    const deletePlaylist = (playlistId) => {
        const token = localStorage.getItem("token");
        fetch(`http://localhost:8080/api/playlists/${playlistId}`, {
            method: "DELETE",
            headers : {
                "Authorization" : `Bearer ${token}`
            }
        }).then(response => {
            if(!response.ok) {
                throw new Error ("Failed to delete paylist");
            }
            fetchPlaylists();
        }).catch(error => {
            console.error("Error deleteing playlist:" ,error);
        })
    }
    return (
        <div>
            <h1>My Playlsits</h1>
            <div>
                <input
                type="text"
                placeholder="Playlist name"
                value = {playlistName}
                onChange={(e) => setPlaylistName(e.target.value)}
                />
                <button onClick={createPlaylist}>
                    Create playlist
                </button>
            </div>
            {
                playlists.length === 0 ? (
                    <p>No playlists yet</p>
                ) : (
                    playlists.map(playlist => (
                        <div key={playlist.id}>
                            <h2>
                                {playlist.name}
                            </h2>
                            <button onClick={() => deletePlaylist(playlist.id)}>
                                Delete PLaylist
                            </button>
                            {playlist.videos.length === 0 ? (
                                <p>No videos in this playlist</p>
                            ) : (
                                playlist.videos.map(video => (
                                    <div key={video.videoId}
                                         onClick={() => navigate(`/watch/${video.videoId}`)}
                                         style={{cursor: "pointer"}}
                                    >
                                        <img
                                            src={video.thumbnailUrl}
                                            alt={video.videoTitle}
                                            width="200"
                                        />
                                        <h3>
                                            {video.videoTitle}
                                        </h3>
                                        <p>
                                            {video.channelName}
                                        </p>
                                        <button onClick={() =>
                                        removeVideoFromPlaylist(
                                            playlist.id,
                                            video.videoId
                                        )}>
                                            Remove
                                        </button>
                                    </div>
                                ))
                            )}

                        </div>
                    ))
                )
             }
        </div>
    );
}
export default Playlists;