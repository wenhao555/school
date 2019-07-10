package com.example.school.model;


import java.util.List;

public class ClassCircle
{
    public Integer getClassCircleId()
    {
        return classCircleId;
    }

    public void setClassCircleId(Integer classCircleId)
    {
        this.classCircleId = classCircleId;
    }


    public String getNotice()
    {
        return notice;
    }

    public void setNotice(String notice)
    {
        this.notice = notice;
    }

    public String getHomework()
    {
        return homework;
    }

    public void setHomework(String homework)
    {
        this.homework = homework;
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

    private Integer classCircleId;

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    private int userId;
    private String notice;
    private String homework;

    public Integer getTeacherId()
    {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId)
    {
        this.teacherId = teacherId;
    }

    private Integer teacherId;

    private String picture;
    private List<Comment> commentList;
    private List<Reply> replyList;
    private List<Integer> zanList;
}
