package org.flyants.book.view.dynamic;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.flyants.book.R;
import org.flyants.book.view.dynamic.publish.DynamicPublishView;
import org.flyants.common.mvp.impl.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DynamicListView extends BaseFragment<DynamicListPrecenter> implements UIDynamicListView{


    @BindView(R.id.springView)  SpringView springView;
    @BindView(R.id.recycler_view) RecyclerView recycler_view;
    private DynamicListAdapter adapter;

    @Override
    public DynamicListPrecenter buildPresenter() {
        return new DynamicListPrecenter(this,this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.dynamic_list;
    }

    @Override
    public void setPullLoadMoreCompleted(int page, List<DynamicResp> rows) {
        springView.onFinishFreshAndLoad();
        if(page == 1){
            adapter.refresh(rows);
        }else{
            if(rows.size() > 0){
                adapter.addDataList(rows);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onViewInit() {
        adapter = new DynamicListAdapter(recycler_view);
//        ProxyRecyclerViewAdapter proxyRecyclerViewAdapter =  new ProxyRecyclerViewAdapter(adapter);
//        proxyRecyclerViewAdapter.addHeaderView(searchView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext() );
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recycler_view.setLayoutManager(layoutManager);
        recycler_view.setAdapter(adapter);
        springView.setHeader(new DefaultHeader(getContext()));
        springView.setFooter(new DefaultFooter(getContext()));
        springView.setListener(getPresenter());
//        springView.setEnableFooter(false);
    }

    @OnClick(R.id.publish_go)
    public void onClickPublishGo(){
        startActivity(new Intent(getContext(), DynamicPublishView.class));
    }

    @Override
    public void onViewStart() {

    }

    @Override
    public void onViewDestroy() {

    }
}
