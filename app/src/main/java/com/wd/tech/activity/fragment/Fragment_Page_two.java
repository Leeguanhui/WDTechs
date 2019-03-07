package com.wd.tech.activity.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.wd.tech.R;
import com.wd.tech.activity.AddFriendsActivity;
import com.wd.tech.activity.FlockActivity;
import com.wd.tech.activity.IGActivity;
import com.wd.tech.activity.IMActivity;
import com.wd.tech.activity.adapter.WDFragmentAdapter;
import com.wd.tech.bean.FriendInfoList;
import com.wd.tech.core.WDFragment;
import com.wd.tech.fragment.LinkmanFragment;
import com.wd.tech.fragment.MessageFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 作者：古祥坤 on 2019/2/18 15:50
 * 邮箱：1724959985@qq.com
  */
public class Fragment_Page_two extends WDFragment {


    @BindView(R.id.message)
    RadioButton message;
    @BindView(R.id.linkman_contacts)
    RadioButton linkmanContacts;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.addition)
    ImageView addition;
    private List<Fragment> list;
    private WDFragmentAdapter wdFragmentAdapter;
    private PopupWindow popupWindow;
    private LinkmanFragment linkmanFragment;
    private EaseConversationListFragment conversationListFragment;

    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page_two;
    }

    @Override
    protected void initView() {
        list = new ArrayList<>();

        conversationListFragment = new EaseConversationListFragment();
        linkmanFragment = new LinkmanFragment();
        list.add(conversationListFragment);
        list.add(linkmanFragment);


        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {

            @Override
            public void onListItemClicked(EMConversation conversation) {
                EMConversation.EMConversationType type = conversation.getType();
                if (type == EMConversation.EMConversationType.Chat) {
                    Intent intent = new Intent(getContext(), IMActivity.class);
                    intent.putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId());
                    intent.putExtra("userNames", conversation.conversationId());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), IGActivity.class);
                    intent.putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId());
                    intent.putExtra("userNames", conversation.conversationId());
                    startActivity(intent);
                }
            }
        });
        wdFragmentAdapter = new WDFragmentAdapter(getChildFragmentManager(), list);
        viewPager.setAdapter(wdFragmentAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                radioGroup.check(radioGroup.getChildAt(i).getId());
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);


    }


    @OnClick({R.id.message, R.id.linkman_contacts})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.message:
                viewPager.setCurrentItem(0, false);
                break;
            case R.id.linkman_contacts:
                viewPager.setCurrentItem(1, false);
                break;
        }
    }

    @OnClick(R.id.addition)
    public void onViewClicked() {
        View view = View.inflate(getContext(), R.layout.append_popwind, null);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        TextView text_you = view.findViewById(R.id.text_you);
        text_you.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddFriendsActivity.class);
                startActivity(intent);
            }
        });
        TextView text_qun = view.findViewById(R.id.text_qun);
        text_qun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FlockActivity.class);
                startActivity(intent);
            }
        });
        popupWindow.showAsDropDown(addition, 0, 55);
    }

}
