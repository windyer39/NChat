package org.flyants.common.mvp.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.monke.basemvplib.R;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import org.flyants.common.mvp.PrecenterEvent;
import org.flyants.common.mvp.ViewEvent;
import org.flyants.common.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseActivity<P extends PrecenterEvent> extends AppCompatActivity implements ViewEvent {

    public List<String> applyPermission(){
        return new ArrayList<String>();
    }

    private Boolean isBindView = false;

    private  P presenter;

    public void onDeniedPermission(){
        setContentView(R.layout.denied_permission);
        findViewById(R.id.id_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public int getStatusBarColor(){
        return R.color.white;
    }


    public Boolean isTextDark(){return true;}

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        StatusBarUtil.setStatusBarMode(this, isTextDark(), getStatusBarColor());
        if(applyPermission().size() > 0){
            requestPermission();
        }else{
            onBindView();
        }
    }

    private void onBindView(){
        setContentView(getLayoutId());
        ButterKnife.bind(this );
        onViewInit();
        if(getPresenter() != null) {
            getPresenter().onViewInit();
        }
        isBindView = true;
    }

    protected Activity getActivity(){
        return this;
    }


    public abstract P buildPresenter();

    public P getPresenter(){
        if(presenter == null){
            presenter = buildPresenter();
        }
        return presenter;
    }

    @Override
    public void onStart() {
        super.onStart();
        onViewStart();
        if(getPresenter() != null && isBindView)
            getPresenter().onViewStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onViewDestroy();
        if(getPresenter() != null && isBindView)
            getPresenter().onViewDestroy();
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
//        overridePendingTransition(R.anim.right_to_left_in,R.anim.fake_anim);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
//        overridePendingTransition(R.anim.right_to_left_in,R.anim.fake_anim);
    }


    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(R.anim.fake_anim,R.anim.left_to_right_out);
    }

    private void requestPermission() {
        List<String> permissions = applyPermission();
        String[] permission = new String[permissions.size()];
        permissions.toArray(permission);

        AndPermission.with(this)
        .runtime()
        .permission(permission)
        .rationale(new Rationale<List<String>>() {
            @Override
            public void showRationale(Context context, List<String> data, RequestExecutor executor) {
                executor.execute();
            }
        })
        .onGranted(new Action<List<String>>() {
            @Override
            public void onAction(List<String> data) {
                Log.d("onGranted",data.toString());
                onBindView();
            }
        }).onDenied(new Action<List<String>>() {
            @Override
            public void onAction(List<String> data) {
                Log.d("onDenied",data.toString());
                onDeniedPermission();
            }
        })
        .start();
    }

}
