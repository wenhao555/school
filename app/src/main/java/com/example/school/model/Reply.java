package com.example.school.model;

public class Reply
{
    private Integer tableId;
    private Integer userId;
    private String userName;
    private Integer replyId;
    private Integer replyUserId;
    private String replyUserName;
    private String content;
    private Long insertTime;

    public Integer getTableId()
    {
        return tableId;
    }

    public void setTableId(Integer tableId)
    {
        this.tableId = tableId;
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

    public Integer getReplyId()
    {
        return replyId;
    }

    public void setReplyId(Integer replyId)
    {
        this.replyId = replyId;
    }

    public Integer getReplyUserId()
    {
        return replyUserId;
    }

    public void setReplyUserId(Integer replyUserId)
    {
        this.replyUserId = replyUserId;
    }

    public String getReplyUserName()
    {
        return replyUserName;
    }

    public void setReplyUserName(String replyUserName)
    {
        this.replyUserName = replyUserName;
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
