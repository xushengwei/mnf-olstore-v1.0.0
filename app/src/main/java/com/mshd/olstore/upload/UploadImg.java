package com.mshd.olstore.upload;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.mshd.olstore.mvp.model.entity.RespGetToken;
import com.mshd.olstore.retrofit.Contact;


/**
 * @author xushengwei
 * @date 2018/11/12
 */
public class UploadImg {

    public static void upload2Oss(Context mContext, RespGetToken model, final OnUploadImgCallBack callBack, String... photoPathList) {
        OssUploadConfig ossUploadConfig = OssUploadConfig.getInstance(mContext);
        String endpoint = Contact.OSS_URL;
        //model.getData().getHost();
        String accessKeyId = model.getData().getAccesskeyid();
        String accessKeySecret = model.getData().getAccesskeysecret();
        String securityToken = model.getData().getSecuritytoken();
        String objectKey = model.getData().getDir();

        ossUploadConfig.initOss(endpoint, accessKeyId, accessKeySecret, securityToken);
        if (photoPathList == null) {
            return;
        }
        for (int i = 0; i < photoPathList.length; i++) {
            final String ossFilePath = objectKey + TimeUtils.getNowMills() + i + ".png";
            String photoPath = photoPathList[i];
            final int currentUpLoadIndex = i;
            //单张图片上传
            ossUploadConfig.uploadFile(Contact.OSS_BUCKET_NAME, ossFilePath, photoPath);
            ossUploadConfig.setOnUploadFile(new OssUploadConfig.OnUploadFile() {
                @Override
                public void onUploadFileSuccess(String info) {
                    LogUtils.d(currentUpLoadIndex + "-成功：" + info);
                    if (callBack != null) {
                        callBack.onUploadFileSuccess(currentUpLoadIndex, ossFilePath);

                    }

                }


                @Override
                public void onUploadFileFailed(String errCode) {
                    LogUtils.d(currentUpLoadIndex + "-失败：" + errCode);
                    if (callBack != null) {
                        callBack.onUploadFileFailed(errCode);
                    }

                    return;

                }
            });
        }

    }


    public interface OnUploadImgCallBack {
        void onUploadFileSuccess(int index, String ossFilePath);

        void onUploadFileFailed(String errCode);
    }

}
