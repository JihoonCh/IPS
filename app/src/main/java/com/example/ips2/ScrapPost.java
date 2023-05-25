package com.example.ips2;

public class ScrapPost {
    private String postId;
    private String title;
    private String content;
    public int whichPost;

    public ScrapPost() {
        // 기본 생성자
    }

    public ScrapPost(String postId, String title, String content, int whichPost) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.whichPost = whichPost;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setWhichPost(int whichPost) {
        this.whichPost = whichPost;
    }

    public int whichPost() {
        return whichPost;
    }
}
