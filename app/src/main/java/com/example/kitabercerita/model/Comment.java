package com.example.kitabercerita.model;

public class Comment {
    private String commentId;
    private String description;
    private Integer likeCount;
    private String userId;
    private String postId;
    private Boolean isNotified;

    public Comment(String commentId, String description, String userId, String postId, Integer likeCount, Boolean isNotified) {
        this.commentId = commentId;
        this.description = description;
        this.userId = userId;
        this.postId = postId;
        this.likeCount = likeCount;
        this.isNotified = isNotified;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
