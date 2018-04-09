package com.cjj.android_materialrefreshlayout;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.cjj.android_materialrefreshlayout.footer.CustomFootItem;
import com.cjj.android_materialrefreshlayout.footer.DefaultFootItem;
import com.cjj.android_materialrefreshlayout.footer.OnLoadMoreListener;
import com.cjj.android_materialrefreshlayout.footer.RecyclerViewWithFooter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by cjj on 2016/2/24.
 */
public class SimpleActivity extends BaseActivity {

    private MaterialRefreshLayout materialRefreshLayout;
    private RecyclerViewWithFooter rv;
    private DefaultFootItem defaultFootItem;
    private CustomFootItem customFootItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        initsToolbar();

        materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                //刷新最好延时0.5秒，不然箭头会出问题，时常不现实箭头
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefresh();
                    }
                }, 3000);
                materialRefreshLayout.finishRefreshLoadMore();

            }

            @Override
            public void onfinish() {
                Toast.makeText(SimpleActivity.this, "finish", Toast.LENGTH_LONG).show();
            }

        });

        rv = (RecyclerViewWithFooter) findViewById(R.id.recyclerview);
        defaultFootItem = new DefaultFootItem();
        rv.setFootItem(defaultFootItem);//默认是这种
//      customFootItem = new CustomFootItem();
//        rv.setFootItem(new CustomFootItem());//自定义

        //加载出现了两次，可以添加 private boolean ifLoading = false;来做判断是否正在加载中来不让同时两次加载
        rv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                defaultFootItem.onBindData(rv, RecyclerViewWithFooter.STATE_LOADING);
                rv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        defaultFootItem.onBindData(rv,RecyclerViewWithFooter.STATE_LOADING);
                        defaultFootItem.onBindData(rv, RecyclerViewWithFooter.STATE_END);
//                        defaultFootItem.onBindData(rv, RecyclerViewWithFooter.STATE_EMPTY);
//                        defaultFootItem.onBindData(rv, RecyclerViewWithFooter.STATE_FINISH_LOADING);
                    }
                }, 2000);
            }
        });
        setupRecyclerView(rv);
    }

    private void initsToolbar() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());



        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(SimpleActivity.this));
    }

    private List<String> getRandomSublist(String[] array, int amount) {
        ArrayList<String> list = new ArrayList<>(amount);
        Random random = new Random();
        while (list.size() < amount) {
            list.add(array[random.nextInt(array.length)]);
        }
        return list;
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {


        public static class ViewHolder extends RecyclerView.ViewHolder {

            public final ImageView mImageView;

            public ViewHolder(View view) {
                super(view);
                mImageView = (ImageView) view.findViewById(R.id.avatar);
            }


        }

        public SimpleStringRecyclerViewAdapter(Context context) {
            super();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            if (position == 0) {
                holder.mImageView.setImageResource(R.drawable.a6);
            } else if (position == 1) {
                holder.mImageView.setImageResource(R.drawable.a5);
            }

        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

}
