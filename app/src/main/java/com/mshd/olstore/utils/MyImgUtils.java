package com.mshd.olstore.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author xushengwei
 * @date 2018/11/16
 */
public class MyImgUtils {

    public static void saveLayoutByDrawing(final Activity context, final View view) {

        String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        Acp.getInstance(context).request(new AcpOptions.Builder()
                        .setPermissions(mPermissionList)
                        //以下为自定义提示语、按钮文字
                        .setDeniedMessage("未获取到读写权限，此模块无法使用，请设置")
                        .setDeniedCloseBtn("关闭")
                        .setDeniedSettingBtn("去设置")
                        .setRationalMessage("此模块需要读写权限才能正常运行")
                        .setRationalBtn("确定")
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                                view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
                                view.setDrawingCacheEnabled(true);
                                view.buildDrawingCache();
                                Bitmap bmp = view.getDrawingCache(); // 获取图片
                                if (bmp == null) {
                                    ToastUtils.showShort("保存失败");
                                    return;
                                }
                                final boolean isSuccess = saveImageToGallery(context, bmp);
                                context.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (isSuccess) {
                                            ToastUtils.showShort("保存成功");
                                        } else {
                                            ToastUtils.showShort("保存失败");
                                        }
                                    }
                                });

                                view.destroyDrawingCache(); // 保存过后释放资源

                            }
                        }).start();
                    }

                    @Override
                    public void onDenied(List<String> permissions) {

                    }
                });


    }

    //保存文件到指定路径
    public static boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "mshd";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            return isSuccess;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
