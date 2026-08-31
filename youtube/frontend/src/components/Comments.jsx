import {useEffect, useState} from "react";
import CommentItem from "./CommentItem.jsx";

function Comments({videoId}) {
    const [comments, setComments] = useState([]);
    const [text, setText] = useState("");
    const token = localStorage.getItem("token");
    let currentUsername = null;
    if(token) {
        try {
            const payload = JSON.parse(atob(token.split(".")[1]));
            currentUsername = payload.sub;
        }catch (error) {
            console.error("Invalid token:",error);
        }
    }
    const fetchComments = () => {
        fetch(`http://localhost:8080/api/comments/video/${videoId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to fetch comments");
                }
                return response.json();
            })
            .then(data => {
                console.log("COMMENTS:", data);
                setComments(data);
            })
            .catch(error => {
                console.error("Error fetching comments:", error);
            });
    };
    useEffect(() => {
        fetchComments();
    }, [videoId]);
    const addComment = () => {
        if (!text.trim()) {
            return;
        }
        const token = localStorage.getItem("token");
        fetch("http://localhost:8080/api/comments", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            body: JSON.stringify({
                videoId: videoId,
                text: text,
                parentCommentId: null
            })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to add comment");
                }
                setText("");
                fetchComments();
            })
            .catch(error => {
                console.error("Error adding comment:", error);
            });
    };
    const handleReply = (commentId,replyText) => {
        const token = localStorage.getItem("token");
        fetch("http://localhost:8080/api/comments",{
            method : "POST",
            headers: {
                "Content-Type" : "application/json",
                "Authorization": `Bearer ${token}`
            },
            body: JSON.stringify({
                videoId:videoId,
                text: replyText,
                parentCommentId : commentId
            })
        })
            .then(response => {
                if(!response.ok) {
                    throw new Error("Failed to add reply");
                }
                fetchComments();
            })
            .catch(error => {
                console.log("Error adding reply:", error);
            });
    };
    const handleEdit = (commentId,newText) => {
        const token = localStorage.getItem("token");
        fetch(
            `http://localhost:8080/api/comments/${commentId}?text=${encodeURIComponent(newText)}`,
            {
                method: "PUT",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
        )
            .then(response => {
                if(!response.ok) {
                    throw new Error("Failed to update comment");
                }
                fetchComments();
            })
            .catch(error => {
                console.error("Error updating commet:",error);
            });
    };
    const handleDelete = (commentId) => {
        const token = localStorage.getItem("token");
        fetch(`http://localhost:8080/api/comments/${commentId}`, {
            method: "DELETE",
            headers: {
                "Authorization" : `Bearer ${token}`
            }
        })
            .then(response => {
                if(!response.ok) {
                    throw new Error("Failed to delete comment");
                }
                fetchComments();
            })
            .catch(error => {
                console.error("Error deleting comment:", error);
            });
    };
    return (
        <div className="comments-section">
            <h2>Comments</h2>
            <div className="add-comment">
                <input
                    type="text"
                    placeholder="Add a comment..."
                    value={text}
                    onChange={(e) => setText(e.target.value)}
                />
                <button onClick={addComment}>
                    Comment
                </button>
            </div>
            {comments.length === 0 ? (
                <p>No comments yet.</p>
            ) : (
                comments.map(comment => (
                    <CommentItem
                        key={comment.id}
                        comment={comment}
                        onReply={handleReply}
                        currentUsername={currentUsername}
                        onEdit={handleEdit}
                        onDelete={handleDelete}
                    />
                ))
            )}
        </div>
    );
}

export default Comments;