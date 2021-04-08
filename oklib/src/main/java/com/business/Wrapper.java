package com.business;

import com.business.parse.IPage;
import com.business.parse.IWrap;
import com.google.gson.JsonElement;

public class Wrapper implements IWrap {
    private int code;
    //net info
    private String message;
    //数据集
    private JsonElement body;
    //页码索引
    private int page = 0;
    //总页
    private int total = 1;

    public Wrapper setCode(int code) {
        this.code = code;
        return this;
    }

    public void setBody(JsonElement body) {
        this.body = body;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPage(int page, int total) {
        this.page = page;
        this.total = total;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public JsonElement getBody() {
        return body;
    }

    @Override
    public IPage getPage() {
        return new Page(page, total);
    }

    public class Page implements IPage {
        private int page;
        private int total;

        public Page(int page, int total) {
            this.page = page;
            this.total = total;
        }

        @Override
        public int getPage() {
            return page;
        }

        @Override
        public int getTotal() {
            return total;
        }
    }
}

