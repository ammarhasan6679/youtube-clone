import { useEffect, useState } from "react";
import VideoCard from "../components/VideoCard.jsx";

function Trending() {
    const[videos,setVideos] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/api/trending")
            .then(response => {
                if(!response.ok) {
                    throw new Error("Failed to fetch trending videos");
                }
                return response.json();
            })
            .then(data => {
                setVideos(data);
            })
            .catch(error => {
                console.log("Error fetching trending videos:",error);
            });
    },[]);

    return (
        <div>
            <h1>Trending</h1>
            <div>
                {videos.map(video => (
                    <VideoCard
                        key = {video.id}
                        video = {video}
                    />
                ))}
            </div>
        </div>
    );
}
export default Trending;