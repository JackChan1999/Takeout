package com.itheima.takeout.model.dao.bean;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "t_user")
public class UserBean {
    @DatabaseField(columnName = "_id",id = true)
    public int _id;


    @DatabaseField()
    public String name;
    @DatabaseField()
    public float balance;
    @DatabaseField()
    public int discount;
    @DatabaseField()
    public int integral;
    @DatabaseField()
    public String phone;
    @DatabaseField
    public boolean login;



    // 需要有一个集合去装当前用户的所有地址列表信息
    @ForeignCollectionField(eager = true)
    public ForeignCollection<AddressBean> addressList;


}
