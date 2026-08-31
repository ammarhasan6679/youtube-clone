import "./index.css"
import Navbar from "./components/Navbar.jsx";
import Sidebar from "./components/Sidebar.jsx";
import {useEffect, useState} from "react";
import VideoCard from "./components/VideoCard.jsx";
import {Routes,Route} from "react-router-dom";
import WatchVideo from "./pages/WatchVideo.jsx";
import Login from "./pages/Login.jsx";
import Register from "./pages/Register.jsx";
import Playlists from "./pages/Playlists.jsx";
import WatchLater from "./pages/WatchLater.jsx";
import History from "./pages/History.jsx";
import Subscriptions from "./pages/Subscriptions.jsx";
import Notifications from "./pages/Notifications.jsx";
import Trending from "./pages/Trending.jsx";
function Home() {
  const [videos, setVideos] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/videos")
        .then(response => response.json())
        .then(data => {
          setVideos(data);
        })
        .catch(error => {
          console.error("Error fetching videos:", error);
        })
  },[]);
  return (
      <>
      <Navbar/>
        <div className="layout">
          <Sidebar/>
          <main className="content">
            <h2>Home</h2>
            <div className="video-grid">
              {videos.map(video => (
                  <VideoCard
                  key = {video.id}
                  video={video}
                  />
              ))}
            </div>
          </main>
        </div>
        </>
  )
}
function App() {
    return (
            <Routes>
                <Route path = "/" element={<Home/>}/>
                <Route
                path = "/watch/:videoId"
                element={<WatchVideo/>}
                />
                <Route
                path = "/login"
                element={<Login/>}
                />
                <Route
                    path = "/register"
                    element={<Register/>}
                />
                <Route
                    path="/playlists"
                    element={<Playlists/>}
                />
                <Route
                    path="/watch-later"
                    element={<WatchLater/>}
                />
                <Route
                    path="/history"
                    element={<History/>}
                />
                <Route
                    path="/subscriptions"
                    element={<Subscriptions/>}
                /><Route
                path="/notifications"
                element={<Notifications/>}
            />
                <Route
                    path="/trending"
                    element={<Trending/>}
                />
            </Routes>
    )
}
export default App;