package com.osb.youtube.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(VideoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleVideoNotFound(
            VideoNotFoundException ex
    ) {
        ErrorResponse error = new ErrorResponse(
                404,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(
            UserNotFoundException ex
    ) {
        ErrorResponse error= new ErrorResponse(
                404,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFound(
            CategoryNotFoundException ex
    ) {
        ErrorResponse error = new ErrorResponse(
                404,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(
            UnauthorizedException ex
    ) {
        ErrorResponse error = new ErrorResponse(
                403,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(error);
    }
    @ExceptionHandler(ChannelNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleChannelNotFound(
            ChannelNotFoundException ex
    ) {
        ErrorResponse error = new ErrorResponse(
                404,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
    @ExceptionHandler(WatchLaterAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleWatchLaterAlreadyExist(
            WatchLaterAlreadyExistsException ex
    ) {
        ErrorResponse error = new ErrorResponse(
                409,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }
    @ExceptionHandler(WatchLaterNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWatchLaterNotFound(
            WatchLaterNotFoundException ex
    ) {
        ErrorResponse error = new ErrorResponse(
                404,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
    @ExceptionHandler(ReactionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleReactionNotFound(
            ReactionNotFoundException ex
    ) {
        ErrorResponse error = new ErrorResponse(
                404,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
    @ExceptionHandler(CannotSubscribeToOwnChannelException.class)
    public ResponseEntity<ErrorResponse> handleCannotSubscribeToOwnChannel(
            CannotSubscribeToOwnChannelException ex
    ) {
        ErrorResponse error = new ErrorResponse(
                400,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
    @ExceptionHandler(AlreadySubscribedException.class)
    public ResponseEntity<ErrorResponse> handleAlreadySubscribed(
            AlreadySubscribedException ex
    ) {
        ErrorResponse error = new ErrorResponse(
                409,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }
    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSubscriptionNotFound(
            SubscriptionNotFoundException ex
    ) {
        ErrorResponse error = new ErrorResponse(
                404,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
    @ExceptionHandler(PlaylistNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePlaylistNotFound(
            PlaylistNotFoundException ex
    ) {
        ErrorResponse error = new ErrorResponse(
                404,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
    @ExceptionHandler(CannotModifyOtherPlaylistException.class)
    public ResponseEntity<ErrorResponse> handleCannotModifyOtherPlaylist(
            CannotModifyOtherPlaylistException ex
    ) {
        ErrorResponse error = new ErrorResponse(
                403,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(error);
    }
    @ExceptionHandler(VideoAlreadyExistsInPlaylistException.class)
    public ResponseEntity<ErrorResponse> handleVideoAlreadyExistsInPlaylist(
            VideoAlreadyExistsInPlaylistException ex
    ) {
        ErrorResponse error = new ErrorResponse(
                409,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }
    @ExceptionHandler(VideoNotFoundInPlaylistException.class)
    public ResponseEntity<ErrorResponse> handleVideoNotFoundInPlaylist(
            VideoNotFoundInPlaylistException ex
    ) {
        ErrorResponse error = new ErrorResponse(
                404,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
    @ExceptionHandler(CannotDeleteOtherPlaylistException.class)
    public ResponseEntity<ErrorResponse> cannotDeleteOtherPlaylist(
            CannotDeleteOtherPlaylistException ex
    ) {
        ErrorResponse error = new ErrorResponse(
                403,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(error);
    }
    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotificationNotFound (
            NotificationNotFoundException ex
    ) {
        ErrorResponse error = new ErrorResponse(
                404,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
    @ExceptionHandler(CannotModifyOthersNotification.class)
    public ResponseEntity<ErrorResponse> handleCannotModifyOtherNotification(
            CannotModifyOthersNotification ex
    ) {
        ErrorResponse error = new ErrorResponse(
                403,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(error);
    }
    @ExceptionHandler(CannotDeleteOthersNotification.class)
    public ResponseEntity<ErrorResponse> handleCannotDeleteOthersNotification(
            CannotDeleteOthersNotification ex
    ) {
        ErrorResponse error = new ErrorResponse(
                403,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(error);
    }
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCommentNotFoundException(
            CommentNotFoundException ex
    ) {
        ErrorResponse error = new ErrorResponse(
                404,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
    @ExceptionHandler(CannotUpdateOthersCommentException.class)
    public ResponseEntity<ErrorResponse> handleCannotUpdateOthersComment(
            CannotUpdateOthersCommentException ex
    ) {
        ErrorResponse error = new ErrorResponse(
                403,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(error);
    }
    @ExceptionHandler(CannotDeleteOthersComment.class)
    public ResponseEntity<ErrorResponse> handleCannotDeleteOtherComment(
            CannotDeleteOthersComment ex
    ) {
        ErrorResponse error = new ErrorResponse(
                403,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(error);
    }
    @ExceptionHandler(CannotJoinYourOwnChannelException.class)
    public ResponseEntity<ErrorResponse> handleCannotJoinYourOwnChannel(
            CannotJoinYourOwnChannelException ex
    ) {
        ErrorResponse error = new ErrorResponse(
                403,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(error);
    }
    @ExceptionHandler(ParentCommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleParentCommentNotFOund (
            ParentCommentNotFoundException ex
    ) {
        ErrorResponse error = new ErrorResponse(
                404,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExists (
            UsernameAlreadyExistsException ex
    ) {
        ErrorResponse error = new ErrorResponse(
                409,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists (
            EmailAlreadyExistsException ex
    ) {
        ErrorResponse error = new ErrorResponse(
                409,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }
}
