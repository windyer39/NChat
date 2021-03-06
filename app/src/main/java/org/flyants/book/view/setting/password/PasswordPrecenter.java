package org.flyants.book.view.setting.password;

import android.os.CountDownTimer;

import org.apache.commons.lang3.StringUtils;
import org.flyants.book.R;
import org.flyants.book.network.RequestUtils;
import org.flyants.book.network.okhttp.RespEmptyCall;
import org.flyants.book.resources.Apis;
import org.flyants.common.store.OnCallback;
import org.flyants.book.store.UserStore;
import org.flyants.book.view.my.UserInfo;
import org.flyants.book.view.setting.accountsecurity.SetPasswordReq;
import org.flyants.common.mvp.impl.BasePresenter;

class PasswordPrecenter extends BasePresenter<PasswordView, UIPasswordView> {

    Apis apis;

    public int Max = 60;//60秒重发
    public int reRendSmsCode = 60;//还剩于多少秒重发


    String phone;


    public PasswordPrecenter(PasswordView t, UIPasswordView uiPasswordView) {
        super(t, uiPasswordView);
    }

    @Override
    public void onViewInit() {
        apis = RequestUtils.build(Apis.class);

    }

    @Override
    public void onViewStart() {
        UserStore.getInstence().loadObject(context, new OnCallback<UserInfo>() {
            @Override
            public void call(UserInfo userInfo) {
                uiView.setViewAttrs(userInfo.getPhone());
                phone = userInfo.getPhone();
            }
        });
    }

    @Override
    public void onViewDestroy() {

    }

    public void onClickSendVerifyCode() {
        if (reRendSmsCode != 60) {
            return;
        }

        apis.sendSmsCode(phone).enqueue(new RespEmptyCall() {
            public void onSuccess() {
                timer.start();
            }
        });
    }

    public void commit() {
        String code = uiView.getInputCode();
        String pwd = uiView.getNewPassword();
        if (StringUtils.isEmpty(code)) {
            return;
        }
        if (StringUtils.isEmpty(pwd)) {
            return;
        }

        SetPasswordReq passwordReq = new SetPasswordReq();
        passwordReq.setPassword(pwd);
        passwordReq.setSmsCode(code);
        apis.setPassword(passwordReq).enqueue(new RespEmptyCall() {
            @Override
            public void onSuccess() {
                super.onSuccess();
                uiView.updatePwdComplete();
            }
        });

    }


    CountDownTimer timer = new CountDownTimer(reRendSmsCode * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            reRendSmsCode = reRendSmsCode - 1;
            uiView.setVerifyCode(String.format("%s秒后重发", reRendSmsCode), R.color.red);
        }

        @Override
        public void onFinish() {
            reRendSmsCode = Max;
            uiView.setVerifyCode("获取验证码", R.color.colorPrimary);
            timer.cancel();
        }
    };
}
