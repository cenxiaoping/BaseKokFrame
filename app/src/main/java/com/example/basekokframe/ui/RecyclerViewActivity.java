package com.example.basekokframe.ui;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseDelegateMultiAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.basekokframe.R;
import com.example.basekokframe.base.KokBaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends KokBaseActivity<RecyclerViewPresenter> implements RecyclerViewActivityView {

    private RecyclerView mListView;
    private List<String> mData = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.activity_recyclerview;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mListView = findViewById(R.id.listView);
        mData.add("");
        mData.add("");
        mData.add("");
        mData.add("");
        mData.add("");
        mData.add("");
        mData.add("");
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getDrawable(R.drawable.home_list_divider));
        mListView.addItemDecoration(dividerItemDecoration);

        mListView.setAdapter(new BaseQuickAdapter(R.layout.item_list_demo, mData) {
            @Override
            protected void convert(@NotNull BaseViewHolder baseViewHolder, Object o) {

            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            }
        });
    }
}
