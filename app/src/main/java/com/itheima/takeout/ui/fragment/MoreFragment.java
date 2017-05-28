package com.itheima.takeout.ui.fragment;

import android.content.Intent;
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
 * Created by itheima.
 */
public class MoreFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_,null);
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
