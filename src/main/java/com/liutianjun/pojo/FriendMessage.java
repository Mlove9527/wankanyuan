package com.liutianjun.pojo;

import java.io.Serializable;
import java.util.Date;

public class FriendMessage implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 发送人ID
     */
    private Integer senderId;

    /**
     * 发送人名
     */
    private String senderName;

    /**
     * 发送人头像
     */
    private String senderHeadimg;

    /**
     * 接收人ID
     */
    private Integer receiverId;

    /**
     * 接收人名
     */
    private String receiverName;

    /**
     * 接收人头像
     */
    private String receiverHeadimg;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 发送时间
     */
    private Date sendTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName == null ? null : senderName.trim();
    }

    public String getSenderHeadimg() {
        return senderHeadimg;
    }

    public void setSenderHeadimg(String senderHeadimg) {
        this.senderHeadimg = senderHeadimg == null ? null : senderHeadimg.trim();
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName == null ? null : receiverName.trim();
    }

    public String getReceiverHeadimg() {
        return receiverHeadimg;
    }

    public void setReceiverHeadimg(String receiverHeadimg) {
        this.receiverHeadimg = receiverHeadimg == null ? null : receiverHeadimg.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", senderId=").append(senderId);
        sb.append(", senderName=").append(senderName);
        sb.append(", senderHeadimg=").append(senderHeadimg);
        sb.append(", receiverId=").append(receiverId);
        sb.append(", receiverName=").append(receiverName);
        sb.append(", receiverHeadimg=").append(receiverHeadimg);
        sb.append(", content=").append(content);
        sb.append(", sendTime=").append(sendTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}