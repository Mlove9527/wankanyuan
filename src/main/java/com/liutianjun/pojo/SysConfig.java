package com.liutianjun.pojo;

import java.io.Serializable;

public class SysConfig implements Serializable {
    private Integer id;

    /**
     * 可注册 0-否 1-是
     */
    private Integer isRegistrable;

    /**
     * 用户空间
     */
    private Integer size;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsRegistrable() {
        return isRegistrable;
    }

    public void setIsRegistrable(Integer isRegistrable) {
        this.isRegistrable = isRegistrable;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", isRegistrable=").append(isRegistrable);
        sb.append(", size=").append(size);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}