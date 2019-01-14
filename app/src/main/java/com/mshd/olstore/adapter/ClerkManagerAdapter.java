package com.mshd.olstore.adapter;

import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mshd.olstore.R;
import com.mshd.olstore.mvp.model.entity.RespStoreClerk;

import java.util.List;

/**
 * @author xushengwei
 * @date 2019/1/5
 */
public class ClerkManagerAdapter extends BaseQuickAdapter<RespStoreClerk.StoreClerkData, BaseViewHolder> {


    public ClerkManagerAdapter(int layoutResId, @Nullable List<RespStoreClerk.StoreClerkData> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, RespStoreClerk.StoreClerkData item) {
        TextView tv_clerkId =helper.getView(R.id.tv_clerkId);
        TextView tv_clerkName =helper.getView(R.id.tv_clerkName);
        TextView tv_clerkPhone =helper.getView(R.id.tv_clerkPhone);
        tv_clerkId.setText(item.getIdentifier());
        tv_clerkName.setText(item.getStoreClerkName());
        tv_clerkPhone.setText(item.getPhone());
    }
}
