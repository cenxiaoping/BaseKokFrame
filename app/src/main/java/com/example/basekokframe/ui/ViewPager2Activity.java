package com.example.basekokframe.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.basekokframe.R;
import com.example.basekokframe.base.KokBaseActivity;

public class ViewPager2Activity extends KokBaseActivity<ViewPager2Presenter> implements ViewPager2View {

    private ViewPager2 viewPager;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_viewpager2;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        viewPager = findViewById(R.id.viewPager);

        viewPager.setUserInputEnabled(true);
        viewPager.canScrollVertically(View.LAYOUT_DIRECTION_LOCALE);
        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager.setAdapter(new RecyclerView.Adapter<ViewPagerViewHolder>() {
            @NonNull
            @Override
            public ViewPagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ViewPagerViewHolder(LayoutInflater.from(ViewPager2Activity.this).inflate(R.layout.item_viewpager, parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull ViewPagerViewHolder holder, int position) {
                holder.textView.setText(position + "");
            }

            @Override
            public int getItemCount() {
                return 10;
            }
        });
    }

    public static class ViewPagerViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public ViewPagerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_content);
        }
    }
}
