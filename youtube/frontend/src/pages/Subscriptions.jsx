import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";

function Subscriptions() {
    const [subscriptions, setSubscriptions] = useState([]);
    const navigate = useNavigate();

    const fetchSubscriptions = () => {
        const token = localStorage.getItem("token");

        fetch("http://localhost:8080/api/subscriptions", {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to fetch subscriptions");
                }
                return response.json();
            })
            .then(data => {
                console.log("SUBSCRIPTIONS:", data);
                setSubscriptions(data);
            })
            .catch(error => {
                console.error("Error fetching subscriptions:", error);
            });
    };

    useEffect(() => {
        fetchSubscriptions();
    }, []);

    const unsubscribe = (channelId) => {
        const token = localStorage.getItem("token");

        fetch(`http://localhost:8080/api/subscriptions/${channelId}`, {
            method: "DELETE",
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to unsubscribe");
                }

                fetchSubscriptions();
            })
            .catch(error => {
                console.error("Error unsubscribing:", error);
            });
    };

    return (
        <div>
            <h1>Subscriptions</h1>

            {subscriptions.length === 0 ? (
                <p>No subscriptions yet</p>
            ) : (
                subscriptions.map(subscription => (
                    <div key={subscription.channelId}>

                        <img
                            src={subscription.profileImageUrl}
                            alt={subscription.channelName}
                            width="80"
                        />

                        <h2>
                            {subscription.channelName}
                        </h2>

                        <p>
                            {subscription.subscriberCount} subscribers
                        </p>

                        <button
                            onClick={() =>
                                unsubscribe(subscription.channelId)
                            }
                        >
                            Unsubscribe
                        </button>

                    </div>
                ))
            )}
        </div>
    );
}

export default Subscriptions;