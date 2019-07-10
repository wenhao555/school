package com.example.school.model;


import java.util.List;

public class CommunityCircle
{
    private int communityCircleId;
    private int teacherId;
    private int userId;
    private String userName;
    private String content;
    private String picture;
    private List<Comment> commentList;
    private List<Reply> replyList;

    public Integer getCommunityCircleId()
    {
        return communityCircleId;
    }

    public void setCommunityCircleId(Integer communityCircleId)
    {
        this.communityCircleId = communityCircleId;
    }

    public Integer getTeacherId()
    {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId)
    {
        this.teacherId = teacherId;
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

    public String getPicture()
    {
        return picture;
    }

    public void setPicture(String picture)
    {
        this.picture = picture;
    }

    public List<Comment> getCommentList()
    {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList)
    {
        this.commentList = commentList;
    }

    public List<Reply> getReplyList()
    {
        return replyList;
    }

    public void setReplyList(List<Reply> replyList)
    {
        this.replyList = replyList;
    }

    public List<Integer> getZanList()
    {
        return zanList;
    }

    public void setZanList(List<Integer> zanList)
    {
        this.zanList = zanList;
    }

    private List<Integer> zanList;
}
