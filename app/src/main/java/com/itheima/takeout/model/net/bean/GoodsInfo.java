package com.itheima.takeout.model.net.bean;

/**
 * 商品数据的封装
 */

public class GoodsInfo {

    public int id;//商品id
    public String name;//商品名称
    public String icon;//商品图片
    public String form;//组成
    public int monthSaleNum;//月销售量
    public boolean bargainPrice;//特价
    public boolean isNew;//是否是新产品
    public float newPrice;//新价
    public int oldPrice;//原价public


    public int headId;// 进行分组操作，同组数据该字段值相同
    public int headIndex;  // 当前条目对应头数据所在集合的下标

    public int count;// 用于购物数量统计
}
