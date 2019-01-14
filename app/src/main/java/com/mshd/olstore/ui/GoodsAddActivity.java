package com.mshd.olstore.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.mshd.olstore.R;
import com.mshd.olstore.base.BasePresenter;
import com.mshd.olstore.base.MvpActivity;
import com.mshd.olstore.mvp.contract.NormalContract;
import com.mshd.olstore.mvp.contract.OssTokenContract;
import com.mshd.olstore.mvp.contract.StoreGoodsAddContract;
import com.mshd.olstore.mvp.model.entity.RespGetToken;
import com.mshd.olstore.mvp.model.entity.RespPriceReduction;
import com.mshd.olstore.mvp.presenter.OssTokenPresenter;
import com.mshd.olstore.mvp.presenter.StoreGoodsAddPresenter;
import com.mshd.olstore.upload.UploadImg;
import com.mshd.olstore.utils.BigDecimalUtil;
import com.mshd.olstore.utils.CashierInputFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 添加商品
 *
 * @author xushengwei
 * @date 2019/1/7
 */
public class GoodsAddActivity extends MvpActivity<OssTokenPresenter> implements OssTokenContract.View, View.OnClickListener, StoreGoodsAddContract.View {

    private ImageView iv_goods;
    private String photoPath;
    private EditText et_goodsTitle;
    private EditText et_goodsPrice;
    private StoreGoodsAddPresenter storeGoodsAddPresenter;
    private boolean isRuKu;
    private String ossFilePath;
    private String priceReduction;
    private TextView tv_goodsBuyPice;
    private TextView tv_goodsDownPayment;
    private TextView tv_goodsDownRepayment;
    private LinearLayout ll_priceItem;

    @Override
    protected OssTokenPresenter createPresenter() {
        return new OssTokenPresenter(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_add);
        mImmersionBar.statusBarDarkFont(true, 0.2f).statusBarView(R.id.top_view).init();
        storeGoodsAddPresenter = new StoreGoodsAddPresenter(this, this);
        storeGoodsAddPresenter.priceReduction();
        initToolBar("添加商品");

        ImageView iv_back = findViewById(R.id.iv_back);
        TextView tv_addGoodsPic = findViewById(R.id.tv_addGoodsPic);
        iv_goods = findViewById(R.id.iv_goods);
        Button btn_goodsRuKu = findViewById(R.id.btn_goodsRuKu);
        Button btn_goodsShangJia = findViewById(R.id.btn_goodsShangJia);
        et_goodsTitle = findViewById(R.id.et_goodsTitle);
        et_goodsPrice = findViewById(R.id.et_goodsPrice);
        tv_goodsBuyPice = findViewById(R.id.tv_goodsBuyPice);
        tv_goodsDownPayment = findViewById(R.id.tv_goodsDownPayment);
        tv_goodsDownRepayment = findViewById(R.id.tv_goodsDownRepayment);
        ll_priceItem = findViewById(R.id.ll_priceItem);

        iv_back.setImageResource(R.mipmap.arrow_left);
        InputFilter[] filters = {new CashierInputFilter()};
        et_goodsPrice.setFilters(filters);
        et_goodsPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String inputString = et_goodsPrice.getText().toString().trim();
                if (StringUtils.isEmpty(priceReduction)) {
                    //未获取到减款金额，隐藏计算金额布局
                    ll_priceItem.setVisibility(View.GONE);
                } else {
                    ll_priceItem.setVisibility(View.VISIBLE);
                    String amt = BigDecimalUtil.substract(inputString, priceReduction);
                    if (Double.parseDouble(amt) < 0) {
                        amt = "0.00";
                    }

                    tv_goodsBuyPice.setText("￥" + BigDecimalUtil.substract(inputString, "0.00"));
                    tv_goodsDownPayment.setText("￥" + BigDecimalUtil.substract(amt, "0.00"));
                    tv_goodsDownRepayment.setText("￥" +BigDecimalUtil.substract(priceReduction, "0.00"));
                }
            }
        });


        tv_addGoodsPic.setOnClickListener(this);
        btn_goodsRuKu.setOnClickListener(this);
        btn_goodsShangJia.setOnClickListener(this);

    }

    private void goodsShangJia(String ossPhotoPath) {

        String goodsTitle = et_goodsTitle.getText().toString().trim();
        String goodsPrice = et_goodsPrice.getText().toString().trim();
        checkParams(goodsTitle, goodsPrice);
        Map<String, String> map = new HashMap<>();
        map.put("goodsName", goodsTitle);
        map.put("goodsBuyPice", goodsPrice);
        map.put("goodsUrl", ossPhotoPath);
        storeGoodsAddPresenter.storeGoodsAdd(map);
    }

    private void goodsRuKu(String ossPhotoPath) {
        String goodsTitle = et_goodsTitle.getText().toString().trim();
        String goodsPrice = et_goodsPrice.getText().toString().trim();
        checkParams(goodsTitle, goodsPrice);
        Map<String, String> map = new HashMap<>();
        map.put("goodsName", goodsTitle);
        map.put("goodsBuyPice", goodsPrice);
        map.put("goodsUrl", ossPhotoPath);
        storeGoodsAddPresenter.storeGoodsRuKu(map);
    }


    private void checkParams(String title, String price) {


        if (StringUtils.isEmpty(title)) {
            ToastUtils.showShort("请输入商品标题");
            return;
        }

        if (StringUtils.isEmpty(price)) {
            ToastUtils.showShort("请输入商品价格");
            return;
        }
    }

    private void getOssToken() {
        if (TextUtils.isEmpty(photoPath)) {
            ToastUtils.showShort("请选择要上传的图片");
            return;
        }

        //商户端 ：2
        mvpPresenter.getOssToken("2");
    }

    private void uploadInfo(RespGetToken model) {
        if (StringUtils.isEmpty(photoPath)) {
            ToastUtils.showShort("请选择要上传的图片");
            return;
        }

        UploadImg.upload2Oss(mActivity, model, new UploadImg.OnUploadImgCallBack() {
            @Override
            public void onUploadFileSuccess(int index, String ossFilePath) {
                GoodsAddActivity.this.ossFilePath = ossFilePath;

            }

            @Override
            public void onUploadFileFailed(String errCode) {
                ToastUtils.showShort("上传到云服务失败:" + errCode);
            }
        }, photoPath);

    }

    private void getPhoto() {
        PictureSelector.create(GoodsAddActivity.this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                //.theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .previewVideo(true)// 是否可预览视频 true or false
                .enablePreviewAudio(true) // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(false)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                //.glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(3, 2)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                //.hideBottomControls()// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
                //.compressSavePath(getPath())//压缩图片保存地址
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
                //.selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                //.previewEggs()// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                // .cropCompressQuality()// 裁剪压缩质量 默认90 int
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                //.videoQuality()// 视频录制质量 0 or 1 int
                //.videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
                //.videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
                //.recordVideoSecond()//视频秒数录制 默认60s int
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的

                    String path;
                    LocalMedia localMedia = selectList.get(0);
                    path = localMedia.getPath();
                    if (localMedia.isCut()) {
                        path = localMedia.getCutPath();
                    }
                    if (localMedia.isCompressed()) {
                        path = localMedia.getCompressPath();
                    }
                    photoPath = path;
                    Glide.with(mActivity).load(path).into(iv_goods);
                    getOssToken();
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void onSuccess() {
        if (isRuKu) {
            //入库
            ToastUtils.showShort("入库成功");
        } else {
            //上架
            ToastUtils.showShort("上架成功");
        }

        finish();
    }

    @Override
    public void onGetPriceReduction(RespPriceReduction mode) {
        priceReduction = mode.getData();
    }

    @Override
    public void onGetTokenSuccess(RespGetToken respGetToken) {
        uploadInfo(respGetToken);
    }

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        dismissProgressDialog();
    }

    @Override
    public void onFailure(String code, String msg) {
        ToastUtils.showShort(msg + "-" + code);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_addGoodsPic:
                getPhoto();
                break;
            case R.id.btn_goodsRuKu:
                isRuKu = true;
                goodsRuKu(ossFilePath);
                break;
            case R.id.btn_goodsShangJia:
                isRuKu = false;
                goodsShangJia(ossFilePath);
                break;
            default:
                break;
        }
    }

}
