package org.flyants.book.view.my.meqrcode;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.google.zxing.encoding.EncodingHandler;

import org.flyants.book.R;
import org.flyants.book.custom.Header;
import org.flyants.book.network.image.ImageLoader;
import org.flyants.book.network.image.glide.CenterCropImageLoaderImpl;
import org.flyants.book.view.my.UserInfo;
import org.flyants.common.mvp.impl.BaseActivity;
import org.flyants.common.utils.StatusBarUtil;

import butterknife.BindView;

public class MeQrCodeView extends BaseActivity<MeQrCodePrecenter> implements UIMeQrCodeView {

    @BindView(R.id.idHeader)  Header idHeader;
//    @BindView(R.id.item_setting_background)  LinearLayout item_setting_background;
//    @BindView(R.id.item_setting_auto_video)  LinearLayout item_setting_auto_video;
    @BindView(R.id.icon) ImageView icon;
    @BindView(R.id.me_qrcode) ImageView me_qrcode;
    @BindView(R.id.nickName) TextView nickName;
    @BindView(R.id.chat_no) TextView chat_no;
    ImageLoader imageLoader = new CenterCropImageLoaderImpl();
    @Override
    public int getStatusBarColor() {
        return R.color.chat_background_color;
    }

    @Override
    public MeQrCodePrecenter buildPresenter() {
        return new MeQrCodePrecenter(this,this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.me_qrcode;
    }

    @Override
    public void onViewInit() {
        idHeader.setHeaderTitle("我的二维码");
        idHeader.setTitleColor(R.color.white);
        idHeader.setBackgrundColor(R.color.chat_background_color);
        idHeader.setBackColorWhite();
    }

    @Override
    public Boolean isTextDark() {
        return false;
    }

    @Override
    public void onViewStart() {

    }

    @Override
    public void setViewAttrs(UserInfo resp) {
        imageLoader.loader(resp.getEncodedPrincipal(),icon);
        nickName.setText(resp.getNickName()+"");
        chat_no.setText(getString(R.string.app_name)+"号:"+resp.getPeopleNo()+"");

        try {
            Bitmap mBitmap = EncodingHandler.createQRCode(resp.getPeopleNo(), 500);
            if(mBitmap != null){
                me_qrcode.setImageBitmap(mBitmap);
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewDestroy() {

    }

}