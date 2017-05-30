package com.itheima.takeout.model.dao.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * ============================================================
 * Copyright：JackChan和他的朋友们有限公司版权所有 (c) 2017
 * Author：   JackChan
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChan1999
 * GitBook：  https://www.gitbook.com/@alleniverson
 * CSDN博客： http://blog.csdn.net/axi295309066
 * 个人博客： https://jackchan1999.github.io/
 * 微博：     AndroidDeveloper
 * <p>
 * Project_Name：Takeout
 * Package_Name：com.itheima.takeout
 * Version：1.0
 * time：2017/5/30 13:52
 * des ：外卖客户端
 * gitVersion：2.12.0.windows.1
 * updateAuthor：AllenIverson
 * updateDate：2017/5/30 13:52
 * updateDes：${TODO}
 * ============================================================
 */
@DatabaseTable(tableName = "t_address")
public class AddressBean {
    @DatabaseField(generatedId = true)
    public int _id;

    @DatabaseField(canBeNull = false)
    public String name;
    @DatabaseField(canBeNull = false)
    public String sex;
    @DatabaseField(canBeNull = false)
    public String phone;
    @DatabaseField(canBeNull = false)
    public String receiptAddress;
    @DatabaseField(canBeNull = false)
    public String detailAddress;
    @DatabaseField(canBeNull = false)
    public String label;
    @DatabaseField(canBeNull = false)
    public long timeStamp;
    @DatabaseField(canBeNull = false)
    public double longitude;
    @DatabaseField(canBeNull = false)
    public double latitude;



    @DatabaseField(canBeNull = false,foreign = true,foreignColumnName = "_id",columnName = "user_id")
    public UserBean user;


    public AddressBean() {
    }

    public AddressBean(String name, String sex, String phone, String receiptAddress, String detailAddress, String label, double longitude, double latitude) {
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.receiptAddress = receiptAddress;
        this.detailAddress = detailAddress;
        this.label = label;
        this.longitude=longitude;
        this.latitude=latitude;

        timeStamp=System.currentTimeMillis();
    }
}
