package org.flyants.book.view.dynamic.publish;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;

import org.flyants.book.R;
import org.flyants.book.network.image.ImageLoader;
import org.flyants.book.network.image.glide.CenterCropImageLoaderImpl;
import org.flyants.book.utils.ToastUtils;
import org.flyants.book.view.my.UserInfo;
import org.flyants.common.mvp.impl.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class DynamicPublishView extends BaseActivity<DynamicPublishPrecenter> implements UIDynamicPublishView {

    @BindView(R.id.close_icon) ImageView close_icon;
    @BindView(R.id.small_icon) ImageView small_icon;
    @BindView(R.id.publish) TextView publish;

    ImageLoader imageLoader = new CenterCropImageLoaderImpl();

    @Override
    public DynamicPublishPrecenter buildPresenter() {
        return new DynamicPublishPrecenter(this,this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.dynamic_publish;
    }

    @Override
    public void onViewInit() {

    }

    @Override
    public void setViewAttrs(UserInfo userInfo) {
        imageLoader.loader(userInfo.getEncodedPrincipal(),small_icon);
    }

    @OnClick(R.id.close_icon)
    public void onClickClose(){
        onBackPressed();
    }

    @OnClick(R.id.publish)
    public void onClickPublish(){
        ToastUtils.show("发布");
    }

    @Override
    public void onViewStart() {

    }

    @Override
    public void onViewDestroy() {

    }
}