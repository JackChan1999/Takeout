package com.itheima.takeout.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.takeout.R;
import com.itheima.takeout.utils.PatchUtils;


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
public class MoreFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_more,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView)view.findViewById(R.id.tv)).setText("更多");

        view.findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PatchTask().execute();
            }
        });
    }

    class PatchTask extends AsyncTask<Void,Void,Integer>{

        @Override
        protected Integer doInBackground(Void... params) {
            String oldPath,newPath,patchPath;
            // 指定包名的程序源文件路径
            oldPath = Environment.getExternalStorageDirectory().toString() + "/takeout/app-debug_1.apk";
            newPath = Environment.getExternalStorageDirectory().toString() + "/takeout/app-debug_2.apk";
            patchPath = Environment.getExternalStorageDirectory() + "/takeout/takeout.patch";

            return PatchUtils.patch(oldPath,newPath,patchPath);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);


            if (integer.equals(0)) {
                Toast.makeText(MoreFragment.this.getContext(), "成功", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(MoreFragment.this.getContext(), "失败", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
