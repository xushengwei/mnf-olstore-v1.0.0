package com.mshd.olstore.ui;

import android.Manifest;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

import com.mshd.olstore.R;
import com.mshd.olstore.baidu.control.InitConfig;
import com.mshd.olstore.baidu.control.MySyntherizer;
import com.mshd.olstore.baidu.control.NonBlockSyntherizer;
import com.mshd.olstore.baidu.listener.UiMessageListener;
import com.mshd.olstore.baidu.util.AutoCheck;
import com.mshd.olstore.baidu.util.OfflineResource;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mshd.olstore.baidu.MainHandlerConstant.PRINT;
import static com.mshd.olstore.baidu.MainHandlerConstant.UI_CHANGE_INPUT_TEXT_SELECTION;
import static com.mshd.olstore.baidu.MainHandlerConstant.UI_CHANGE_SYNTHES_TEXT_SELECTION;


public class TestActivity extends AppCompatActivity {

    protected String appId = "15322098";

    protected String appKey = "1shCoyv82TmoG4UT7boEFI8X";

    protected String secretKey = "w16nrz6sfaXtXbKm1YuHzVmIEwraWQvp";

    // TtsMode.MIX; 离在线融合，在线优先； TtsMode.ONLINE 纯在线； 没有纯离线
    protected TtsMode ttsMode = TtsMode.MIX;

    // 离线发音选择，VOICE_FEMALE即为离线女声发音。
    // assets目录下bd_etts_common_speech_m15_mand_eng_high_am-mix_v3.0.0_20170505.dat为离线男声模型；
    // assets目录下bd_etts_common_speech_f7_mand_eng_high_am-mix_v3.0.0_20170512.dat为离线女声模型
    protected String offlineVoice = OfflineResource.VOICE_MALE;

    // ===============初始化参数设置完毕，更多合成参数请至getParams()方法中设置 =================

    // 主控制类，所有合成控制方法从这个类开始
    protected MySyntherizer synthesizer;
    protected Handler mainHandler;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mainHandler = new Handler() {
            /*
             * @param msg
             */
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                handle(msg);
            }

        };

        String[] mPermissionList = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                //Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE
        };

        Acp.getInstance(getApplicationContext()).request(new AcpOptions.Builder()
                        .setPermissions(mPermissionList)
                        .setDeniedMessage("未获取到权限，此模块无法运行，请设置")
                        .setDeniedCloseBtn("关闭")
                        .setDeniedSettingBtn("去设置")
                        .setRationalMessage("应用需要此权限才能正常运行")
                        .setRationalBtn("确定")
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        initialTts();

                    }

                    @Override
                    public void onDenied(List<String> permissions) {

                    }
                });

        editText = findViewById(R.id.editText);
        Button button =findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputString = editText.getText().toString().trim();
                if (TextUtils.isEmpty(inputString)){
                    inputString="没有输入要播报的内容";
                }

                int result = synthesizer.speak(inputString);
                checkResult(result,"loadModel");
            }
        });

    }


    private void checkResult(int result, String method) {
        if (result != 0) {
            Toast.makeText(TestActivity.this,"error code :" + result + " method:" + method + ", 错误码文档:http://yuyin.baidu.com/docs/tts/122 ",Toast.LENGTH_LONG).show();
        }
    }



    /**
     * 初始化引擎，需要的参数均在InitConfig类里
     * <p>
     * DEMO中提供了3个SpeechSynthesizerListener的实现
     * MessageListener 仅仅用log.i记录日志，在logcat中可以看见
     * UiMessageListener 在MessageListener的基础上，对handler发送消息，实现UI的文字更新
     * FileSaveListener 在UiMessageListener的基础上，使用 onSynthesizeDataArrived回调，获取音频流
     */
    protected void initialTts() {
        LoggerProxy.printable(true); // 日志打印在logcat中
        // 设置初始化参数
        // 此处可以改为 含有您业务逻辑的SpeechSynthesizerListener的实现类
        SpeechSynthesizerListener listener = new UiMessageListener(mainHandler);

        Map<String, String> params = getParams();


        // appId appKey secretKey 网站上您申请的应用获取。注意使用离线合成功能的话，需要应用中填写您app的包名。包名在build.gradle中获取。
        InitConfig initConfig = new InitConfig(appId, appKey, secretKey, ttsMode, params, listener);

        // 如果您集成中出错，请将下面一段代码放在和demo中相同的位置，并复制InitConfig 和 AutoCheck到您的项目中
        // 上线时请删除AutoCheck的调用
        AutoCheck.getInstance(getApplicationContext()).check(initConfig, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 100) {
                    AutoCheck autoCheck = (AutoCheck) msg.obj;
                    synchronized (autoCheck) {
                        String message = autoCheck.obtainDebugMessage();
                        //toPrint(message); // 可以用下面一行替代，在logcat中查看代码
                        Log.w("AutoCheckMessage", message);
                    }
                }
            }

        });
        synthesizer = new NonBlockSyntherizer(this, initConfig, mainHandler); // 此处可以改为MySyntherizer 了解调用过程
    }

    /**
     * 合成的参数，可以初始化时填写，也可以在合成前设置。
     *
     * @return
     */
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        // 以下参数均为选填
        // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        params.put(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // 设置合成的音量，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_VOLUME, "9");
        // 设置合成的语速，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_SPEED, "5");
        // 设置合成的语调，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_PITCH, "5");

        params.put(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_HIGH_SPEED_NETWORK);
        // 该参数设置为TtsMode.MIX生效。即纯在线模式不生效。
        // MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线

        // 离线资源文件， 从assets目录中复制到临时目录，需要在initTTs方法前完成
        OfflineResource offlineResource = createOfflineResource(offlineVoice);
        // 声学模型文件路径 (离线引擎使用), 请确认下面两个文件存在
        params.put(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, offlineResource.getTextFilename());
        params.put(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE,
                offlineResource.getModelFilename());
        return params;
    }


    protected OfflineResource createOfflineResource(String voiceType) {
        OfflineResource offlineResource = null;
        try {
            offlineResource = new OfflineResource(this, voiceType);
        } catch (IOException e) {
            // IO 错误自行处理
            e.printStackTrace();
            //toPrint("【error】:copy files from assets failed." + e.getMessage());
        }
        return offlineResource;
    }

    protected void handle(Message msg) {
        int what = msg.what;
        switch (what) {
            case PRINT:
                //print(msg);
                break;
            case UI_CHANGE_INPUT_TEXT_SELECTION:
//                if (msg.arg1 <= mInput.getText().length()) {
//                    mInput.setSelection(0, msg.arg1);
//                }
                break;
            case UI_CHANGE_SYNTHES_TEXT_SELECTION:
//                SpannableString colorfulText = new SpannableString(mInput.getText().toString());
//                if (msg.arg1 <= colorfulText.toString().length()) {
//                    colorfulText.setSpan(new ForegroundColorSpan(Color.GRAY), 0, msg.arg1, Spannable
//                            .SPAN_EXCLUSIVE_EXCLUSIVE);
//                    mInput.setText(colorfulText);
//                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        synthesizer.release();
        super.onDestroy();
    }

}
