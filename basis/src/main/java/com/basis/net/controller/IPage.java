package com.basis.net.controller;

public interface IPage {
    String KEY_PAGE_SIZE = "pageSize";
    String KEY_PAGE_INDEX = "pageNo";
    int PAGE_SIZE = 20;//每页显示的记录数据 默认15条
    int PAGE_FIRST = 1;//第一页的索引 0 或者 1
}
