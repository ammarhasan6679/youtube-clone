import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";

function WatchLater() {
    const [videos, setVideos] = useState([]);
    const navigate = useNavigate();

    const fetchWatchLater = () => {
        const token = localStorage.getItem("token");

        fetch("http://localhost:8080/api/watch-later", {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to fetch Watch Later");
                }
                return response.json();
            })
            .then(data => {
                console.log("WATCH LATER:", data);
                setVideos(data);
            })
            .catch(error => {
                console.error("Error fetching Watch Later:", error);
            });
    };

    useEffect(() => {
        fetchWatchLater();
    }, []);

    const removeFromWatchLater = (videoId) => {
        const token = localStorage.getItem("token");

        fetch(`http://localhost:8080/api/watch-later/${videoId}`, {
            method: "DELETE",
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to remove video");
                }

                fetchWatchLater();
            })
            .catch(error => {
                console.error("Error removing from Watch Later:", error);
            });
    };

    return (
        <div>
            <h1>Watch Later</h1>

            {videos.length === 0 ? (
                <p>No videos in Watch Later</p>
            ) : (
                videos.map(video => (
                    <div
                        key={video.videoId}
                        onClick={() => navigate(`/watch/${video.videoId}`)}
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

                        <button
                            onClick={(e) => {
                                e.stopPropagation();

                                removeFromWatchLater(video.videoId);
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

export default WatchLater;