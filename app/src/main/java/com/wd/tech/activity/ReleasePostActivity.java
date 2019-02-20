package com.wd.tech.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.wd.tech.R;
import com.wd.tech.activity.adapter.ReleasePostAdapter;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.utils.StringUtils;

import butterknife.BindView;

public class ReleasePostActivity extends WDActivity {

    @BindView(R.id.bo_text)
    EditText mText;

    @BindView(R.id.bo_image_list)
    RecyclerView mImageList;

    ReleasePostAdapter addCircleAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_release_post;
    }

    @Override
    protected void initView() {
        addCircleAdapter = new ReleasePostAdapter();
        addCircleAdapter.setSign(1);
        addCircleAdapter.add(R.drawable.addpicture);
        mImageList.setLayoutManager(new GridLayoutManager(this, 3));
        mImageList.setAdapter(addCircleAdapter);

    }

    @Override
    protected void destoryData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {//resultcode是setResult里面设置的code值
            String filePath = getFilePath(null, requestCode, data);
            if (!StringUtils.isEmpty(filePath)) {
                addCircleAdapter.add(filePath);
                addCircleAdapter.notifyDataSetChanged();
//                Bitmap bitmap = UIUtils.decodeFile(new File(filePath),200);
//                mImage.setImageBitmap(bitmap);
            }
        }
    }

}
