import {useState} from "react";

function CommentItem({comment,onReply,currentUsername,onEdit,onDelete}) {
    const [replyText,setReplyText] = useState("");
    const [showReplyBox,setShowReplyBox] = useState(false);
    const[isEditing, setIsEditing] = useState(false);
    const[editText,setEditText] = useState(comment.text);
    const isOwner = comment.username  === currentUsername;
    const handleReplyClick = () => {
        setShowReplyBox(true);
        setReplyText("");
    };
    const handleSubmitReply = () => {
        if(!replyText.trim()) {
            return;
        }
        onReply(comment.id,replyText);
        setReplyText("");
        setShowReplyBox(false);
    };
    const handleEditClick = () => {
        setIsEditing(true);
        setEditText(comment.text);
    }
    const handleCancelReply = () => {
        setShowReplyBox(false);
        setReplyText("");
    };
    const handleCancelEdit = () => {
        setIsEditing(false);
        setEditText(comment.text);
    }
    const handleSaveEdit = () => {
        if(!editText.trim()) {
            return;
        }
        onEdit(comment.id,editText);
        setIsEditing(false);
    }
    return (
        <div>
            <strong>
                {comment.username}
            </strong>
            {isEditing ? (
                <div>
                    <input
                        type="text"
                        value={editText}
                        onChange={(e) => setEditText(e.target.value)}
                    />
                    <button onClick={handleSaveEdit}>
                        Save
                    </button>
                    <button onClick={handleCancelEdit}>
                        Cancel
                    </button>
                </div>
            ) : (
                <p>
                    {comment.text}
                </p>
            )}
            <button onClick={handleReplyClick}>
                Reply
            </button>
            {isOwner && !isEditing && (
                <>
                    <button onClick={handleEditClick}>
                        Edit
                    </button>
                    <button onClick={() => onDelete(comment.id)}>
                        Delete
                    </button>
                </>
            )}
            {showReplyBox && (
                <div>
                    <input
                        type="text"
                        value={replyText}
                        onChange={(e) => setReplyText(e.target.value)}
                    />
                    <button onClick={handleSubmitReply}>
                        Reply
                    </button>
                    <button onClick={handleCancelReply}>
                        Cancel
                    </button>
                </div>
            )}
            {comment.replies && comment.replies.length > 0 && (
                <div className="replies">
                    {comment.replies.map(reply => (
                        <CommentItem
                            key={reply.id}
                            comment={reply}
                            onReply={onReply}
                            currentUsername={currentUsername}
                            onEdit={onEdit}
                            onDelete={onDelete}
                        />
                    ))}
                </div>
            )}
        </div>
    );
}
export default CommentItem;