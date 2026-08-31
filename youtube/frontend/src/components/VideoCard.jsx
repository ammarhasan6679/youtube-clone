import {useNavigate} from "react-router-dom";

function VideoCard({video}) {
    const navigate = useNavigate();
    const handleClick = () => {
        navigate(`/watch/${video.id}`);
    }
    return (
        <div className="video-card"
        onClick={handleClick}>
            <img
                src={video.thumbnailUrl}
                alt={video.videoTitle}
            />
            <h3>{video.videoTitle}</h3>
            <p>{video.channelName}</p>
            <p>{video.views} views</p>

        </div>
    );
}
export default VideoCard;