import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";

function History() {
    const [videos, setVideos] = useState([]);
    const navigate = useNavigate();
    const fetchHistory = () => {
        const token = localStorage.getItem("token");
        fetch("http://localhost:8080/api/watch-history", {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to fetch history");
                }
                return response.json();
            })
            .then(data => {
                console.log("HISTORY:", data);
                setVideos(data);
            })
            .catch(error => {
                console.error("Error fetching history:", error);
            });
    };
    useEffect(() => {
        fetchHistory();
    }, []);
    const removeFromHistory = (videoId) => {
        const token = localStorage.getItem("token");
        fetch(`http://localhost:8080/api/watch-history/${videoId}`, {
            method: "DELETE",
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to remove from history");
                }

                fetchHistory();
            })
            .catch(error => {
                console.error("Error removing from history:", error);
            });
    };
    const clearHistory = () => {
        const token = localStorage.getItem("token");
        fetch("http://localhost:8080/api/watch-history", {
            method: "DELETE",
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to clear history");
                }
                fetchHistory();
            })
            .catch(error => {
                console.error("Error clearing history:", error);
            });
    };
    return (
        <div>
            <h1>History</h1>
            <button onClick={clearHistory}>
                Clear History
            </button>
            {videos.length === 0 ? (
                <p>No watch history</p>
            ) : (
                videos.map(video => (
                    <div
                        key={video.videoId}
                        onClick={() =>
                            navigate(`/watch/${video.videoId}`)
                        }
                        style={{cursor: "pointer"}}
                    >
                        <img
                            src={video.thumbnailUrl}
                            alt={video.videoTitle}
                            width="200"
                        />
                        <h3>{video.videoTitle}</h3>
                        <p>{video.channelName}</p>
                        <p>{video.views} views</p>
                        <p>
                            Watched: {video.watchedAt}
                        </p>
                        <button
                            onClick={(e) => {
                                e.stopPropagation();
                                removeFromHistory(video.videoId);
                            }}
                        >
                            Remove
                        </button>
                    </div>
                ))
            )}
        </div>
    );
}

export default History;