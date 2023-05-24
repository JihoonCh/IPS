package com.example.ips2;
public class Comment {
    private String commentId;
    private String commentText;

    public Comment() {
        // 기본 생성자 (Firebase에서 객체로 변환할 때 필요)
    }

    public Comment(String commentId, String commentText) {
        this.commentId = commentId;
        this.commentText = commentText;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getCommentText() {
        return commentText;
    }
}
