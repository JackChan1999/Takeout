package com.itheima.takeout.presenter.activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itheima.takeout.model.net.bean.PaymentInfo;
import com.itheima.takeout.model.net.bean.ResponseInfo;
import com.itheima.takeout.presenter.BasePresenter;
import com.itheima.takeout.ui.IView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

/**
 * 支付的业务处理
 */

public class PaymentPresenter extends BasePresenter {
    // Dagger操作步骤：
    // 1.在Dagger的module包下PresenterModule类中增加一个生成当前业务对象的方法（@Provides）
    // 2.在Bas儿Activity中添加一个@Inject注解，注入当前业务类实例

    private IView view;

    public PaymentPresenter(IView view) {
        this.view = view;
    }

    public void getData(String orderId){
        Call<ResponseInfo> payment = responseInfoAPI.payment(orderId);
        payment.enqueue(new CallbackAdapter());
    }


    @Override
    protected void failed(String msg) {

    }

    @Override
    protected void parserData(String data) {

        try {
            JSONObject object=new JSONObject(data);
            int payDownTime = object.getInt("payDownTime");
            double money = object.getDouble("money");
            String paymentInfo = object.getString("paymentInfo");

            Gson gson=new Gson();
            List<PaymentInfo> paymentInfos = gson.fromJson(paymentInfo, new TypeToken<List<PaymentInfo>>() {
            }.getType());


            // 1.使用Map集合将散数据整合传输
            // 2.将success方法中的参数调整成 Object...

            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("payDownTime",payDownTime);
            dataMap.put("money",money);
            dataMap.put("paymentInfo",paymentInfos);
            view.success(dataMap);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        /**
         * {
             "payDownTime": 15,
             "money": 15,
             "paymentInfo": [
                 {
                 "id": 1,
                 "name": "支付宝",
                 "url": "http://10.0.2.2:8080/TakeoutService/imgs/payment/zfb.png"
                 },
                 {
                 "id": 2,
                 "name": "微信支付",
                 "url": "http://10.0.2.2:8080/TakeoutService/imgs/payment/wx.png"
                 }
             ]
         }

         */

//        view.success(null);
    }
}
