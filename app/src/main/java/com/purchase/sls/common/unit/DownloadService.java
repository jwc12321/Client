package com.purchase.sls.common.unit;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;

public class DownloadService extends Service {

    public static void start(Context context, String url, String md5) {
        Intent service = new Intent(context, DownloadService.class);
        service.putExtra(URL_KEY, url);
        service.putExtra(MD5_KEY,md5);
        context.startService(service);
    }

   private static MaterialDialog materialDialog;
    //"content://downloads/my_downloads"必须这样写不可更改
    public static final Uri CONTENT_URI = Uri.parse("content://downloads/my_downloads");
    public static final String URL_KEY = "URL";

    public static final String MD5_KEY = "MD5";

    private String md5;

    private static  long downloadId;

    private File file  = new File(Environment.getExternalStorageDirectory() + "/download/purchase.apk");

    private BroadcastReceiver receiver;
    private static DownloadManager dm;

    public static void setMaterialDialog( MaterialDialog diolog) {

        materialDialog=diolog;
    }
    private   DownloadChangeObserver downloadObserver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long longExtra = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                Log.d("111","执行这里3333=="+longExtra+"===="+downloadId);
                if (longExtra == downloadId) {
                    String md5 = MD5.getMD5(file);
                    Log.d("111","执行这里4444=="+DownloadService.this.md5+"===="+md5);
//                    if (DownloadService.this.md5 != null && DownloadService.this.md5.equals(md5)) {
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setDataAndType(Uri.fromFile(file.getAbsoluteFile()),
                                "application/vnd.android.package-archive");
                        startActivity(intent);
//                    }
                    stopSelf();
                }
            }
        };
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        String url = intent.getStringExtra(URL_KEY);
        md5 = intent.getStringExtra(MD5_KEY);
        startDownload(url);
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    private void startDownload(String url) {
        if (file.exists()){
            file.delete();
        }
       dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse(url));
        request.setMimeType("application/vnd.android.package-archive");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "purchase.apk");
        downloadId = dm.enqueue(request);
        downloadObserver = new DownloadChangeObserver(null);
        getContentResolver().registerContentObserver(CONTENT_URI, true, downloadObserver);

    }

    public static void stopDownload(){
        dm.remove(downloadId);
    }

    //用于显示下载进度
    class DownloadChangeObserver extends ContentObserver {

        public DownloadChangeObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadId);
            DownloadManager dManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            final Cursor cursor = dManager.query(query);
            if (cursor != null && cursor.moveToFirst()) {
                final int totalColumn = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
                final int currentColumn = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
                int totalSize = cursor.getInt(totalColumn);
                int currentSize = cursor.getInt(currentColumn);
                float percent = (float) currentSize / (float) totalSize;
                int progress = Math.round(percent * 100);
                materialDialog.setProgress(progress);
                if(progress == 100) {
                    materialDialog.dismiss();
                }
            }
        }
    }
}
