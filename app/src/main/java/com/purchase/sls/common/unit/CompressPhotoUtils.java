package com.purchase.sls.common.unit;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JWC on 2018/6/11.
 */

public class CompressPhotoUtils {
    private List<String> fileList = new ArrayList<>();

    public void CompressPhoto(Activity context, List<String> list, CompressCallBack callBack) {
        CompressTask task = new CompressTask(context, list, callBack);
        task.execute();
    }

    class CompressTask extends AsyncTask<Void, Integer, Integer> {
        private Context context;
        private List<String> list;
        private CompressCallBack callBack;

        CompressTask(Context context, List<String> list, CompressCallBack callBack) {
            this.context = context;
            this.list = list;
            this.callBack = callBack;
        }

        /**
         * 运行在UI线程中，在调用doInBackground()之前执行
         */
        @Override
        protected void onPreExecute() {
        }

        /**
         * 后台运行的方法，可以运行非UI线程，可以执行耗时的方法
         */
        @Override
        protected Integer doInBackground(Void... params) {
            for (int i = 0; i < list.size(); i++) {
                String path = BitmapUtils.compressImageUpload(list.get(i), (Activity) context);
                fileList.add(path);
            }
            return null;
        }

        /**
         * 运行在ui线程中，在doInBackground()执行完毕后执行
         */
        @Override
        protected void onPostExecute(Integer integer) {
            callBack.success(fileList);
        }

        /**
         * 在publishProgress()被调用以后执行，publishProgress()用于更新进度
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
        }
    }
    public interface CompressCallBack {
        void success(List<String> list);
    }
}
