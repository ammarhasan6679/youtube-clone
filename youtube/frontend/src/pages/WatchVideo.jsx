import {useEffect,useState} from "react";
import {useParams} from "react-router-dom";
import Comments from "../components/Comments.jsx";

function WatchVideo() {
    const {videoId} = useParams();
    const [video,setVideo] = useState(null);
    const[likeCount,setLikeCount] = useState(0);
    const[dislikeCount, setDislikeCount] = useState(0);
    const [playlists, setPlaylists] = useState([]);
    const [showPlaylists, setShowPlaylists] = useState(false);
    const [isSubscribed,setIsSubscribed] = useState(false);
    const[subscriberCount, setSubscriberCount] = useState(0);

    useEffect(() => {
        const token = localStorage.getItem("token");
        fetch(`http://localhost:8080/api/videos/watch/${videoId}`
        )
            .then(response =>{
                if(!response.ok) {
                    throw new Error("Failed to fetch video");
                }
                return response.json();
            })
            .then(data => {
                console.log("VIDEO DATA:", data);
                console.log("CHANNEL ID:", data.channelId);
                setVideo(data);
            })
            .catch(error => {
                console.log("Error fetching video:", error);
            });
    }, [videoId]);
    const fetchCounts = () => {
        fetch(`http://localhost:8080/api/video-likes/${videoId}/likes`)
            .then(response => response.json())
            .then(data => {
                setLikeCount(data);
            })
            .catch(error => {
                console.error("Error fetching like count:" , error);
            });
        fetch(`http://localhost:8080/api/video-likes/${videoId}/dislikes`)
            .then(response => response.json())
            .then(data => {
                setDislikeCount(data);
            })
            .catch(error => {
                console.error("Error fetching like count:" , error);
            });

    }
    useEffect(() => {
        fetchCounts();
    },[videoId]);
    const fetchPlaylists = () => {
        const token = localStorage.getItem("token");
        fetch("http://localhost:8080/api/playlists", {
            method: "GET",
            headers: {
                "Authorization" : `Bearer ${token}`
            }
        }).then(response => {
            if(!response.ok) {
                throw new Error('Failed to fetch playlists');
            }
            return response.json();
        }).then(data => {
            setPlaylists(data);
        }).catch(error => {
            console.error("Error fetching playlists:" , error);
        });
    };
    const addToPlaylist = (playlistId) => {
        const token = localStorage.getItem("token");
        fetch(
            `http://localhost:8080/api/playlists/${playlistId}/videos/${videoId}`,
            {
                method: "POST",
                headers: {
                    "Authorization":`Bearer ${token}`
                }
            }
        ).then(response => {
            if(!response.ok) {
                throw new  Error ("Failed to add video to playlist");
            }
            alert("Video added toplaylist");
            setShowPlaylists(false);
        }).catch(error => {
            console.error("Error adding video to playlist:", error);
        });
    };
    const likeVideo = () => {
        const token = localStorage.getItem("token");
        fetch(
            `http://localhost:8080/api/video-likes/${videoId}?status=LIKE`,
            {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
        ).then(response => {
            if (!response.ok) {
                throw new Error("Failed to like video");
            }
            fetchCounts();
        })
            .catch(error => {
                console.error("Like error:", error);
            });
    };
        const dislikeVideo = () => {
            const token = localStorage.getItem("token");
            fetch(
                `http://localhost:8080/api/video-likes/${videoId}?status=DISLIKE`,
                {
                    method: "POST",
                    headers: {
                        "Authorization": `Bearer ${token}`
                    }
                }
            )
                .then(response => {
                    if(!response.ok) {
                        throw  new Error("Failed to dislike video");
                    }
                    fetchCounts();
                })
                .catch(error => {
                    console.error("Dislike error:", error);
                });
        };
    const addToWatchLater = () => {
        const token = localStorage.getItem("token");

        fetch(`http://localhost:8080/api/watch-later/${videoId}`, {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to add to Watch Later");
                }

                alert("Added to Watch Later");
            })
            .catch(error => {
                console.error("Error adding to Watch Later:", error);
            });
    };
    const fetchSubscriptionStatus = () => {
        const token = localStorage.getItem("token");
        if(!token || !video?.channelId) {
            return;
        }
        fetch(
            `http://localhost:8080/api/subscriptions/${video.channelId}/status`,
            {
                headers: {
                    "Authorization":`Bearer ${token}`
                }
            }
        ).then(response => {
            if(!response.ok) {
                throw new Error("Failed to fetch subscriprion status");
            }
            return response.json();
        })
            .then(data => {
                setIsSubscribed(data);
            })
            .catch(error => {
                console.error("Subscription status error:", error);
            });
    };

    const fetchSubscriberCount =() => {
        const token = localStorage.getItem("token");
        if(!video?.channelId) {
            return;
        }
        fetch(
            `http://localhost:8080/api/subscriptions/${video.channelId}/count`,
            {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
        ).then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch subscriber count");
            }
            return response.json();
        }).then(data => {
            setSubscriberCount(data);
        })
            .catch(error => {
                console.error("Subscriber count error:", error);
            });
    };
    useEffect(() => {
        if (video?.channelId) {
            fetchSubscriptionStatus();
            fetchSubscriberCount();
        }
    }, [video]);

    const handleSubscription = () => {

        const token = localStorage.getItem("token");

        if (!video?.channelId) {
            return;
        }

        const method = isSubscribed ? "DELETE" : "POST";

        fetch(
            `http://localhost:8080/api/subscriptions/${video.channelId}`,
            {
                method: method,
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
        )
            .then(response => {
                if (!response.ok) {
                    throw new Error(
                        isSubscribed
                            ? "Failed to unsubscribe"
                            : "Failed to subscribe"
                    );
                }

                setIsSubscribed(!isSubscribed);
                fetchSubscriberCount();
            })
            .catch(error => {
                console.error("Subscription error:", error);
            });
    };

    if(!video) {
        return <p>Loading...</p>;
    }
    return (
        <div className="watch-page">
            <video
            src = {video.videoUrl}
            controls
            width="900"
            />
            <h1>{video.videoTitle}</h1>
            <p>{video.channelName}</p>
            <p>{video.views} views</p>
            <p>{video.videoDescription}</p>
            <div className="video-actions">

                <button onClick={likeVideo}>
                    👍 Like {likeCount}
                </button>

                <button onClick={dislikeVideo}>
                    👎 Dislike {dislikeCount}
                </button>
                <button onClick={() => {
                    fetchPlaylists();
                    setShowPlaylists(true);
                }}>
                    Save to playlist
                </button>
                <button onClick={addToWatchLater}>
                    Watch Later
                </button>
                <button onClick={handleSubscription}>
                    {isSubscribed ? "Subscribed" : "Subscribe"}
                </button>

                <p>
                    {subscriberCount} subscribers
                </p>
            </div>
            {showPlaylists && (
                <div>
                    <h3>Save to playlist</h3>

                    {playlists.length === 0 ? (
                        <p>No playlists available</p>
                    ) : (
                        playlists.map(playlist => (
                            <div key={playlist.id}>

                                <button
                                    onClick={() => addToPlaylist(playlist.id)}
                                >
                                    {playlist.name}
                                </button>
                            </div>
                        ))
                    )}

                    <button onClick={() => setShowPlaylists(false)}>
                        Cancel
                    </button>

                </div>
            )}
            <Comments videoId={videoId} />
        </div>


    );



}
export default WatchVideo;