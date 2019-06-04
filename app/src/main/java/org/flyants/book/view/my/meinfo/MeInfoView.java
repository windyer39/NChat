package org.flyants.book.view.my.meinfo;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.flyants.book.R;
import org.flyants.book.custom.EmptySpringHeader;
import org.flyants.book.custom.Header;
import org.flyants.book.custom.ProxyRecyclerViewAdapter;
import org.flyants.book.network.image.ImageLoader;
import org.flyants.book.network.image.glide.CenterCropImageLoaderImpl;
import org.flyants.book.view.dynamic.DynamicResp;
import org.flyants.book.view.my.UserInfo;
import org.flyants.common.mvp.impl.BaseActivity;
import org.flyants.common.utils.StatusBarUtil;

import java.util.List;

import butterknife.BindView;

public class MeInfoView extends BaseActivity<MeInfoPrecenter> implements UIMeInfoView {

    @BindView(R.id.springView) SpringView springView;
//    @BindView(R.id.idHeader) Header idHeader;
    @BindView(R.id.id_header_layout) LinearLayout id_header_layout;
    @BindView(R.id.idHeader) ImageView idHeader;
    @BindView(R.id.header_right) ImageView header_right;

    @BindView(R.id.recycler_view)  RecyclerView recycler_view;

    MeInfoHeaderView meInfoHeader;
    ImageLoader imageLoader = new CenterCropImageLoaderImpl();
    MeInfoAdapter adapter;

    @Override
    public int getStatusBarColor() {
        return R.color.white;
    }


    @Override
    public MeInfoPrecenter buildPresenter() {
        return new MeInfoPrecenter(this, this);
    }

    @Override
    public int getLayoutId() {
        StatusBarUtil.setTranslucentStatus(this);
        return R.layout.me_info;
    }

    @Override
    public void onViewInit() {

        ViewGroup.LayoutParams layoutParams = id_header_layout.getLayoutParams();
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(layoutParams);
        lp.setMargins(0,getStatusBarHeight(),0,0);
        id_header_layout.setLayoutParams(lp);

        meInfoHeader = new MeInfoHeaderView(this,springView);
        meInfoHeader.setRecycler_view(recycler_view);
        meInfoHeader.setPresenter(getPresenter());
//        idHeader.setHeaderTitle("");
//        idHeader.setBackgrundColor(0);

        idHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        adapter = new MeInfoAdapter(recycler_view);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity() );
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        recycler_view.setLayoutManager(layoutManager);

        ProxyRecyclerViewAdapter proxyAdapter = new ProxyRecyclerViewAdapter(adapter);
        proxyAdapter.addHeaderView(meInfoHeader.rootView);
        recycler_view.setAdapter(proxyAdapter);
//        recyclerView.addItemDecoration( new DividerGridItemDecoration(this ));
        recycler_view.setItemAnimator( new DefaultItemAnimator());
        springView.setHeader(new EmptySpringHeader());
//        springView.setHeader(new DefaultHeader(getActivity()));
        springView.setFooter(new DefaultFooter(getActivity()));
        springView.setListener(getPresenter());
        springView.setEnableFooter(true);
        springView.setEnableHeader(true);
    }

    @Override
    public void onViewStart() {

    }


    @Override
    public void setViewAttrs(UserInfo resp) {
        imageLoader.loader(resp.getEncodedPrincipal(), meInfoHeader.icon);
        meInfoHeader. nickName.setText(resp.getNickName() + "");
        meInfoHeader. chat_no.setText(getString(R.string.AntsChatId) + ":" + resp.getPeopleNo() + "");
        meInfoHeader. people_introduction.setText(resp.getIntroduction() + "");
        if (resp.getPeopleAssistCount() > 0) {
            meInfoHeader.peopleAssist.setImageResource(R.mipmap.ab0);
        }
        meInfoHeader.peopleAssistCount.setText(resp.getPeopleAssistCount() + "");
        meInfoHeader.location.setText("所在地：   " + resp.getLocation() + "");
    }

    @Override
    public void onViewDestroy() {

    }

    @Override
    public void setPullLoadMoreCompleted(int page, List<DynamicResp> list) {
        springView.onFinishFreshAndLoad();
        adapter.notifyDataSetChanged();
    }


    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}