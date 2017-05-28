package com.itheima.takeout.model.net.bean;

import java.util.List;

/**
 * 商品类型封装
 */

public class GoodsTypeInfo {
    public int id;//商品类型id
    public String name;//商品类型名称
    public String info;//特价信息
    public List<GoodsInfo> list;//商品列表

    // 点击摸个头时，需要知道其分组容器中对应组元素中第一条的下标
    public int groupFirstIndex;

}
