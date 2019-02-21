package com.wd.tech.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.activity.adapter.ReleasePostAdapter;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.core.utils.StringUtils;
import com.wd.tech.presenter.ReleasePostPresenter;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class ReleasePostActivity extends WDActivity {

    @BindView(R.id.id_editor_detail)
    EditText mText;
    @BindView(R.id.id_editor_detail_font_count)
    TextView textView;
    @BindView(R.id.bo_image_list)
    RecyclerView mImageList;

    ReleasePostAdapter addCircleAdapter;
    private ReleasePostPresenter releasePostPresenter;
    private String sessionId;
    private int userId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_release_post;
    }

    @Override
    protected void initView() {
        //数据库

        LoginUserInfoBean userInfo = getUserInfo(this);

        userId = userInfo.getUserId();
        sessionId = userInfo.getSessionId();

        addCircleAdapter = new ReleasePostAdapter();
        addCircleAdapter.setSign(1);
        addCircleAdapter.add(R.drawable.addpicture);
        mImageList.setLayoutManager(new GridLayoutManager(this, 3));
        mImageList.setAdapter(addCircleAdapter);
        //发布帖子
        releasePostPresenter = new ReleasePostPresenter(new ReleasePost());
        //计算输入框数量
        textView.setText(String.valueOf("0/400"));
        mText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                textView.setText(String.valueOf(s.length()) + "/400");
                if (s.length() >= 400) {
                    Toast.makeText(ReleasePostActivity.this, "上限了，亲", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @OnClick(R.id.textview2)
    public void textview2() {
        releasePostPresenter.request(userId, sessionId, mText.getText().toString(), addCircleAdapter.getList());
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

    //发布帖子成功接口
    private class ReleasePost implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
            finish();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
