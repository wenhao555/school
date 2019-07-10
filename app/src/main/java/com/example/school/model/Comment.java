package com.example.school.model;


public class Comment
{
    private Integer commentId;
    private Integer userId;
    private String userName;
    private String content;
    private Long insertTime;

    public Integer getCommentId()
    {
        return commentId;
    }

    public void setCommentId(Integer commentId)
    {
        this.commentId = commentId;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Long getInsertTime()
    {
        return insertTime;
    }

    public void setInsertTime(Long insertTime)
    {
        this.insertTime = insertTime;
    }
}
