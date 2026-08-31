import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import Navbar from "../components/Navbar.jsx";
import VideoCard from "../components/VideoCard.jsx";

function Search() {
    const [searchParms] = useSearchParams();
    const query = searchParms.get("query");

    const [videos,setVideos] = useState([]);

    useEffect(() => {
        if(!query) {
            return;
        }
        fetch(
            `http://localhost:8080/api/videos/search?query=${encodeURIComponent(query)}`
        )
            .then(response => {
                if(!response.ok) {
                    throw new Error("Failed to search videos");
                }
                return response.json();
            })
            .then(data => {
                setVideos(data);
            })
            .catch(error => {
                console.error("Search erro : ",error);
            });
    },[query]);
    return(
        <>
            <div className="content"> {videos.length === 0 ? (
                <p>No videos found</p> ) :
                ( <div className="video-grid">
                        {videos.map(video =>
                            ( <VideoCard key={video.id}
                                         video={video} /> ))}
                </div>
                )}
            </div>
            </>

    );
}
export default Search;