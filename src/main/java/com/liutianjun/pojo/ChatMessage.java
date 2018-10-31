package com.liutianjun.pojo;

import java.io.Serializable;

public class ChatMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String objname;//对象名称
	private String username;//自己名称
	private String headimg;//自己头像
	private String content;//消息内容
	private String status;//状态信息
	
	public ChatMessage() {
		
	}
	public ChatMessage(String content, String status) {
		this.content = content;
		this.status = status;
	}
	public String getObjname() {
		return objname;
	}
	public void setObjname(String objname) {
		this.objname = objname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHeadimg() {
		return headimg;
	}
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((headimg == null) ? 0 : headimg.hashCode());
		result = prime * result + ((objname == null) ? 0 : objname.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChatMessage other = (ChatMessage) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (headimg == null) {
			if (other.headimg != null)
				return false;
		} else if (!headimg.equals(other.headimg))
			return false;
		if (objname == null) {
			if (other.objname != null)
				return false;
		} else if (!objname.equals(other.objname))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
	
	
}
