package com.wd.tech.fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wd.tech.R;
import com.wd.tech.activity.ClusterActivity;
import com.wd.tech.activity.GroupChatActivity;
import com.wd.tech.activity.IMActivity;
import com.wd.tech.activity.NewFriendsActivity;
import com.wd.tech.bean.FriendInfoList;
import com.wd.tech.bean.InitFriendlist;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDFragment;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.core.utils.UIUtils;
import com.wd.tech.presenter.DeleteFriendRelationPresenter;
import com.wd.tech.presenter.InitFriendListPresenter;
import com.wd.tech.presenter.TransferFriendGroupPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zxk
 * on 2019/2/18 19:47
 * QQ:666666
 * Describe:
 */
public class LinkmanFragment extends WDFragment {

    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.search_image)
    ImageView searchImage;
    @BindView(R.id.x_recyclerview)
    SmartRefreshLayout pullToRefreshScrollView;
    @BindView(R.id.layout_newsyou)
    LinearLayout layoutNewsyou;
    @BindView(R.id.layout_qun)
    LinearLayout layoutQun;
    @BindView(R.id.ex_pandable_listview)
    ExpandableListView exPandableListview;
    @BindView(R.id.news_peng)
    TextView newsPeng;
    @BindView(R.id.qun_tong)
    TextView qunTong;
    @BindView(R.id.qun_layout)
    LinearLayout qunLayout;
    @BindView(R.id.group)
    TextView grouP;
    //Model：定义的数据
    private List<InitFriendlist> groupS;
    private List<FriendInfoList> childs;

    private InitFriendListPresenter listPresenter;
    private String sessionId;
    private int userId;
    private LoginUserInfoBean bean;
    private PopupWindow window;
    private View inflate;
    private DeleteFriendRelationPresenter deleteFriendRelationPresenter;
    private TransferFriendGroupPresenter transferFriendGroupPresenter;
    private int black;

    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.linkman_layout;
    }

    @Override
    protected void initView() {
        deleteFriendRelationPresenter = new DeleteFriendRelationPresenter(new Delet());
        transferFriendGroupPresenter = new TransferFriendGroupPresenter(new Tran());
        listPresenter = new InitFriendListPresenter(new InitFr());
        bean = getUserInfo(getContext());
        if (bean != null) {
            sessionId = bean.getSessionId();
            userId = bean.getUserId();
            listPresenter.request(userId, sessionId);

        } else {
            exPandableListview.setVisibility(View.GONE);
        }
        listPresenter.request(userId, sessionId);
        getShow();
        exPandableListview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Intent intent = new Intent(getContext(), IMActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, groupS.get(i).getFriendInfoList().get(i1).getUserName());
                FriendInfoList friendInfoList = groupS.get(i).getFriendInfoList().get(i1);
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
                intent.putExtra("userNames", friendInfoList.getUserName());
                startActivity(intent);
                return true;
            }
        });
        exPandableListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View childView, int flatPos, long id) {
                if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    long packedPos = ((ExpandableListView) parent).getExpandableListPosition(flatPos);
                    int groupPosition = ExpandableListView.getPackedPositionGroup(packedPos);
                    int childPosition = ExpandableListView.getPackedPositionChild(packedPos);
                    FriendInfoList friendInfoList = groupS.get(groupPosition).getFriendInfoList().get(childPosition);
                    int friendUid = friendInfoList.getFriendUid();

                    window.showAsDropDown(childView.findViewById(R.id.item_pop_show), -80, 15);
                    for (int i = 0; i < groupS.size(); i++) {
                        if (groupS.get(i).getGroupName().equals("黑名单")) {
                            black = groupS.get(i).getGroupId();
                        }
                    }

                    getWindow(inflate, friendUid);
                    return true;
                }

                return false;
            }

        });
        pullToRefreshScrollView.setEnableRefresh(true);
        pullToRefreshScrollView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                listPresenter.request(userId, sessionId);
            }
        });
        exPandableListview.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    @Override
    public void onResume() {
        super.onResume();
        bean = getUserInfo(getContext());
        if (bean != null) {
            sessionId = bean.getSessionId();
            userId = bean.getUserId();
            listPresenter.request(userId, sessionId);
        } else {
            exPandableListview.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.layout_newsyou, R.id.layout_qun})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_newsyou:
                Intent intent1 = new Intent(getActivity(), NewFriendsActivity.class);
                startActivity(intent1);
                break;
            case R.id.layout_qun:
                Intent intent = new Intent(getActivity(), ClusterActivity.class);
                startActivity(intent);
                break;
        }
    }


    private void getShow() {
        inflate = View.inflate(getContext(), R.layout.popu_item_long_layout, null);
        window = new PopupWindow(inflate, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setTouchable(true);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setBackgroundDrawable(new BitmapDrawable());

    }

    private void getWindow(View inflate, final int id) {
        TextView popuItemLongDelete = inflate.findViewById(R.id.popu_item_long_delete);
        TextView popuItemLongBad = inflate.findViewById(R.id.popu_item_long_bad);
        popuItemLongDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFriendRelationPresenter.request(userId, sessionId, id);
                window.dismiss();
            }
        });
        popuItemLongBad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (black == 0) {
                    window.dismiss();
                    UIUtils.showToastSafe("没有黑名单");
                    return;
                }
                transferFriendGroupPresenter.request(userId, sessionId, black, id);
                window.dismiss();
            }
        });
    }


    @OnClick(R.id.qun_layout)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(), GroupChatActivity.class);
        startActivity(intent);
    }


    class MyExpandableListView extends BaseExpandableListAdapter {


        @Override
        public int getGroupCount() {
            return groupS.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return groupS.get(groupPosition).getFriendInfoList().size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groupS.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return groupS.get(groupPosition).getFriendInfoList().get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        //父布局
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHodler hodler;
            if (convertView == null) {
                convertView = View.inflate(parent.getContext(), R.layout.expandablelistview_one_item, null);
                hodler = new GroupHodler();
                hodler.groupname = convertView.findViewById(R.id.tv_group);
                convertView.setTag(hodler);
            } else {
                hodler = (GroupHodler) convertView.getTag();
            }
            InitFriendlist initFriendlist = groupS.get(groupPosition);

            hodler.groupname.setText(initFriendlist.getGroupName());

            return convertView;
        }



        //子布局
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            MyHolder holder;
            if (convertView == null) {
                convertView = View.inflate(parent.getContext(), R.layout.expandablelistview_two_item, null);
                holder = new MyHolder();
                holder.headric = convertView.findViewById(R.id.iv_child);
                holder.qianming = convertView.findViewById(R.id.tv_child);
                holder.name = convertView.findViewById(R.id.tv_qian);
                convertView.setTag(holder);
            } else {
                holder = (MyHolder) convertView.getTag();
            }
            FriendInfoList friendInfoList = groupS.get(groupPosition).getFriendInfoList().get(childPosition);
            holder.headric.setImageURI(friendInfoList.getHeadPic());
            holder.qianming.setText(friendInfoList.getNickName());
            holder.name.setText(friendInfoList.getSignature());
            return convertView;
        }


        //父框件
        class GroupHodler {
            TextView groupname;
        }

        //子框件 (一个复选框 ,, 文字 ,, 价格 ,, 图片 ,, 还有自定义一个类)
        class MyHolder {
            SimpleDraweeView headric;
            TextView qianming;
            TextView name;

        }
    }

    private class InitFr implements ICoreInfe<Result<List<InitFriendlist>>> {
        @Override
        public void success(Result<List<InitFriendlist>> data) {
            if (data.getStatus().equals("0000")) {
                groupS = data.getResult();
                exPandableListview.setAdapter(new MyExpandableListView());

                pullToRefreshScrollView.finishRefresh();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class Delet implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                listPresenter.request(userId, sessionId);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class Tran implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                listPresenter.request(userId, sessionId);

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
