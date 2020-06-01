package com.bcq.net.controller;

public interface IPage {
    String KEY_PAGE_SIZE = "per_page";
    String KEY_PAGE_INDEX = "page";
    int PAGE_SIZE = 15;//每页显示的记录数据 默认15条
    int PAGE_FIRST = 1;//第一页的索引 0 或者 1
}
