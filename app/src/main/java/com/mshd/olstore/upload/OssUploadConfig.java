package com.mshd.olstore.upload;

/**
 * @author xushengwei
 * @date 2018/11/6
 */

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.OSSRequest;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

/**
 * 步骤1 首先创建对象
 * 2 调用initOss 初始化oss 相关
 * 3 上传文件
 * 视频上传到Oss的流程  本地请求服务器获取accessKeyId，accessKeySecret，securityToken --->上传文件到OSS服务器的同时
 * 并设置本司服务器的回调地址
 * ---> 上传成功后oss会根据之前设置的回调通知本司服务器 本地也会收到关于oss的上传回调
 * <p>
 * STS 鉴权服务器地址，使用前请参照文档           https://help.aliyun.com/document_detail/31920.html
 * <p>
 * 移动端所需服务端提的参数 STS凭证的三个参数 accessKeyId，accessKeySecret，securityToken
 * <p>
 * 所用的oss服务器的地址endPoint，
 * bucketName oss上的名称，
 * objectKey oss上所存储文件的名称 eg: vic/test/xxx.mp4
 * 前半段由服务器提供，最好在请求STS的时候返回给移动端（这样后期服务器迁移也不会出现问题，），
 * 后一部分xxx 文件名自行解决，但是要保证文件名称唯一，查找使用， uploadFilePath 本地存储文件的路径
 */

public class OssUploadConfig {
    private static final String TAG = OssUploadConfig.class.getSimpleName();
    private static OssUploadConfig aliyunOssUploadFileUtil;
    /**
     * 服务器的地址
     */
//    private static final String ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";

    private ClientConfiguration conf = null;
    private OSS oss = null;
    private PutObjectRequest put = null;
    private OSSAsyncTask task = null;
    private OnUploadFile onUploadFile;

    //public static final String CALLBACK_URL = "callbackUrl";
    //public static final String CALLBACK_BODY = "callbackBody";

    /**
     * callback 的回调地址 eg : "http://oss-demo.aliyuncs.com:23450";
     * 通过这个地址oss可以将所需要的数据传到应用服务器，服务器就可以根据上传的参数为所欲为了
     */
    //private String mCallbackAddress = "http://oss-demo.aliyuncs.com:23450";
    private Context mContext;

    private OssUploadConfig(Context context) {
        this.mContext = context;
    }

    public static OssUploadConfig getInstance(Context context) {
        if (aliyunOssUploadFileUtil == null) {
            synchronized (OssUploadConfig.class) {
                if (aliyunOssUploadFileUtil == null) {
                    aliyunOssUploadFileUtil = new OssUploadConfig(context);
                }
            }
        }
        return aliyunOssUploadFileUtil;
    }


    /**
     * 调用这个方法之前必须先设置accessKeyId，accessKeySecret，securityToken;
     */
    public void initOss(String endpoint,String accessKeyId,
                        String accessKeySecret,
                        String securityToken) {
        conf = new ClientConfiguration();
        conf.setConnectionTimeout(5 * 60 * 1000);
        conf.setSocketTimeout(5 * 60 * 1000);
        conf.setMaxConcurrentRequest(5);
        conf.setMaxErrorRetry(2);
        OSSLog.enableLog();
        //请求后台服务器返回
        //    "StatusCode":200,
        //    "AccessKeyId":"STS.3p***dgagdasdg",
        //    "AccessKeySecret":"rpnwO9***tGdrddgsR2YrTtI",
        //   "SecurityToken":"CAES+wMIARKAAZhjH0EUOIhJMQBMjRywXq7MQ/cjLYg80Aho1ek0Jm63XMhr9Oc5s˙∂˙∂3qaPer8p1YaX1NTDiCFZWFkvlHf1pQhuxfKBc+mRR9KAbHUefqH+rdjZqjTF7p2m1wJXP8S6k+G2MpHrUe6TYBkJ43GhhTVFMuM3BZajY3VjZWOXBIODRIR1FKZjIiEjMzMzE0MjY0NzM5MTE4NjkxMSoLY2xpZGSSDgSDGAGESGTETqOio6c2RrLWRlbW8vKgoUYWNzOm9zczoqOio6c2RrLWRlbW9KEDExNDg5MzAxMDcyNDY4MThSBTI2ODQyWg9Bc3N1bWVkUm9sZVVzZXJgAGoSMzMzMTQyNjQ3MzkxMTg2OTExcglzZGstZGVtbzI=",
        //   "Expiration":"2017-12-12T07:49:09Z",
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(
                accessKeyId, accessKeySecret,
                securityToken
        );

        oss = new OSSClient(mContext, endpoint, credentialProvider, conf);

    }


    /**
     * 上传文件
     *
     * @param bucketName     oss上所建的仓库名称  eg : smartDev 如果设置的合理，将会报错，oss会提示说设置的bucketName不可用
     * @param objectKey      oss上所存储文件的名称 eg: vic/test/xxx.mp4
     * @param uploadFilePath 本地存储文件的路径
     */
    public void uploadFile(String bucketName, String objectKey,
                           String uploadFilePath
                           // , final String filename, final int reminderId
    ) {
        put = new PutObjectRequest(
                bucketName,
                objectKey,
                uploadFilePath);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("application/octet-stream");
        put.setMetadata(metadata);
        put.setCRC64(OSSRequest.CRC64Config.YES);
//        if (!TextUtils.isEmpty(mCallbackAddress)) {
//            put.setCallbackParam(new HashMap<String, String>() {
//                {
//                    //这里 filename 中的object 不用替换，如果到oss了，object 将自动在服务器自动被上传文件名替换 并且是全路径名称
//                    put(CALLBACK_URL, mCallbackAddress);
//                    put(CALLBACK_BODY, "filename=${object}" + "&reminder_id=${x:" + reminderId + "}");
////                    put(CALLBACK_BODY,"filename=${object}");
//                }
//            });
//        }

        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d(TAG, "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });

        task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {

                Log.d(TAG, "uploadSuccess");

                if (onUploadFile != null) {
                    onUploadFile.onUploadFileSuccess("上传成功");
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                if (onUploadFile != null) {
                    try {
                        if (serviceException != null) {
                            onUploadFile.onUploadFileFailed(serviceException.toString());
                        } else {
                            onUploadFile.onUploadFileFailed("上传失败");
                        }
                    } catch (Exception e) {
                        onUploadFile.onUploadFileFailed("上传失败");
                    }

                }
                // Request exception
                if (clientException != null) {
                    // Local exception, such as a network exception
                    clientException.printStackTrace();
                }
                if (serviceException != null) {
                    // Service exception
                    Log.d(TAG, "ErrorCode=" + serviceException.getErrorCode());
                    Log.d(TAG, "RequestId=" + serviceException.getRequestId());
                    Log.d(TAG, "HostId=" + serviceException.getHostId());
                    Log.d(TAG, "RawMessage=" + serviceException.getRawMessage());
                }
            }
            // task.cancel(); // Cancel the task

            // task.waitUntilFinished(); // Wait till the task is finished
        });
    }


    public void setOnUploadFile(OnUploadFile onUploadFile) {
        this.onUploadFile = onUploadFile;
    }

    public interface OnUploadFile {
        void onUploadFileSuccess(String info);

        void onUploadFileFailed(String errCode);
    }

}


