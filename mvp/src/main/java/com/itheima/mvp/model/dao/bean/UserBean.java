package com.itheima.mvp.model.dao.bean;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "t_user")
public class UserBean {
    @DatabaseField(columnName = "_id",id = true)
    private int _id;
    // 需要有一个集合去装当前用户的所有地址列表信息
    @ForeignCollectionField(eager = true)
    private ForeignCollection<AddressBean> addressList;


    public UserBean() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public ForeignCollection<AddressBean> getAddressList() {
        return addressList;
    }

    public void setAddressList(ForeignCollection<AddressBean> addressList) {
        this.addressList = addressList;
    }
}
