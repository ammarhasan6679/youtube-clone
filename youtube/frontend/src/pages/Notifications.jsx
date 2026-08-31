import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";

function Notifications() {
    const [notifications, setNotifications] = useState([]);
    const [unreadCount, setUnreadCount] = useState(0);
    const navigate = useNavigate();
    const fetchNotifications = () => {
        const token = localStorage.getItem("token");
        fetch("http://localhost:8080/api/notifications",{
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }).then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch notifications");
            }
            return response.json();
        })
            .then(data => {
                console.log("NOTIFICATIONS:", data);
                setNotifications(data);
            })
            .catch(error => {
                console.error("Error fetching notifications:", error);
            });
    };
    const fetchUnreadCount = () =>{
        const token = localStorage.getItem("token");
        fetch("http://localhost:8080/api/notifications/unread/count", {
            headers: {
                "Authorization" : `Bearer ${token}`
            }
        }).then(response => {
            if(!response.ok) {
                throw  new Error("Failed to fetch unread count");
            }
            return response.json();
        }) .then(data => {
            setUnreadCount(data);
        })
            .catch(error => {
                console.error("Error fetching unread count:", error);
            });

    };
    useEffect(() => {
        fetchNotifications();
        fetchUnreadCount();
    },[])
    const markAsRead = (notificationId) => {
        const token = localStorage.getItem("token");
        fetch(`http://localhost:8080/api/notifications/${notificationId}/read`, {
            method: "PATCH",
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to mark notification as read");
                }
                fetchNotifications();
                fetchUnreadCount();
            })
            .catch(error => {
                console.error("Error marking notification as read:", error);
            });
    };
    const markAllAsRead = () => {
        const token = localStorage.getItem("token");
        fetch("http://localhost:8080/api/notifications/read-all", {
            method: "PATCH",
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to mark all notifications as read");
                }
                fetchNotifications();
                fetchUnreadCount();
            })
            .catch(error => {
                console.error("Error marking all notifications as read:", error);
            });
    };
    const deleteNotification = (notificationId) => {
        const token = localStorage.getItem("token");
        fetch(
            `http://localhost:8080/api/notifications/${notificationId}`,
            {
                method: "DELETE",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
        )
            .then(response => {
                if(!response.ok) {
                    throw new Error("Failed to delete notification");
                }
                fetchNotifications();
                fetchUnreadCount();
            }).catch(error=> {
                console.error("Error deleleting notifcation:", error);
        })
    }
    return (
        <div>
            <h1>Notifications</h1>
            <p>Unread: {unreadCount}</p>
            <button onClick={markAllAsRead}>
                Mark all as read
            </button>
            {notifications.length === 0 ? (
                <p>No notifications</p>
            ) : (
                notifications.map(notification => (
                    <div key={notification.id}
                         onClick={() => {
                             if (notification.videoId) {
                                 navigate(`/watch/${notification.videoId}`);
                             }
                         }}
                         style={{
                             cursor: notification.videoId ? "pointer" : "default"
                         }}>
                        <p>{notification.message}</p>
                        <p>{notification.type}</p>
                        {!notification.isRead && (
                            <button
                                onClick={(e) => {
                                    e.stopPropagation();
                                    markAsRead(notification.id);
                                }}
                            >
                                Mark as read
                            </button>
                        )}
                        <button
                            onClick={(e) => {
                                e.stopPropagation();
                                deleteNotification(notification.id);
                            }}
                        >
                            Delete
                        </button>
                    </div>
                ))
            )}
        </div>
    );
}

export default Notifications;
