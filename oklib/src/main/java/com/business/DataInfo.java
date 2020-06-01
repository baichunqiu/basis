package com.business;

import java.io.Serializable;

/**
 * @author: BaiCQ
 * @ClassName: DataInfo
 * @date: 2018/6/27
 * @Description: DataInfo Json解析实体
 */
public class DataInfo implements Serializable{
    //code
    private int code;
    //net info
    private String message;
    //数据集
    private String body;
    //页码索引
    private int index;
    //总页
    private int total;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
