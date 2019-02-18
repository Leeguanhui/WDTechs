package com.wd.tech.activity.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wd.tech.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：古祥坤 on 2019/2/18 15:50
 * 邮箱：1724959985@qq.com
 */
public class Fragment_Page_one extends Fragment {
    @BindView(R.id.listview)
    ListView listview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_page_one, null);
        ButterKnife.bind(this, view);
        List<String> strings = new ArrayList<>();
        for (int i = 0; i <50; i++) {
            strings.add("数据" + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, strings);
        listview.setAdapter(adapter);
        return view;
    }
}
