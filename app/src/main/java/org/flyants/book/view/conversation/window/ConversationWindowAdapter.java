package org.flyants.book.view.conversation.window;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.flyants.book.R;
import org.flyants.book.network.image.ImageLoader;
import org.flyants.book.network.image.glide.CenterCropImageLoaderImpl;
import org.flyants.book.utils.LogUtils;
import org.flyants.book.view.base.BaseRecyclerAdapter;
import org.flyants.book.view.base.RecyclerHolder;
import org.flyants.book.view.conversation.ConversationResp;
import org.flyants.book.view.my.UserInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConversationWindowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    UserInfo userInfo;
    List<MessageResp> messageRespList = new ArrayList<>();

    Context mContext;

    public ConversationWindowAdapter(UserInfo userInfo, Context mContext) {
        this.userInfo = userInfo;
        this.mContext = mContext;
    }

    private ImageLoader imageLoader = new CenterCropImageLoaderImpl();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0){
            View item = LayoutInflater.from(mContext).inflate(R.layout.conversation_window_text_left , parent ,false);
            LogUtils.d("aaa","onCreateViewHolder————"+viewType);
            TextView content = item.findViewById(R.id.item_content);
            ImageView item_icon = item.findViewById(R.id.item_icon);
            return new TextHolder(item,content,item_icon);
        }else if(viewType == 1){
            View item = LayoutInflater.from(mContext).inflate(R.layout.conversation_window_text_right , parent ,false);
            LogUtils.d("aaa","onCreateViewHolder————"+viewType);
            TextView content = item.findViewById(R.id.item_content);
            ImageView item_icon = item.findViewById(R.id.item_icon);
            return new TextHolder(item,content,item_icon);
        }else{
            View item = LayoutInflater.from(mContext).inflate(R.layout.conversation_window_text_right , parent ,false);
            LogUtils.d("aaa","onCreateViewHolder————"+viewType);
            TextView content = item.findViewById(R.id.item_content);
            ImageView item_icon = item.findViewById(R.id.item_icon);
            return new TextHolder(item,content,item_icon);
        }
    }

    public Boolean isRight(MessageResp messageResp) {
        return messageResp.getBody().length() % 2 == 0;
    }


    @Override
    public int getItemViewType(int position) {
        MessageResp messageResp = messageRespList.get(position);
        return messageResp.getBody().length() % 2 == 0 ? 0 : 1;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof  TextHolder){
            MessageResp mData = messageRespList.get(position);
            ((TextHolder) holder).content.setText(mData.getBody());
//            ((TextHolder) holder).content.setText(mData.getBody());
        }
    }

    @Override
    public int getItemCount() {
        return messageRespList.size();
    }


    public void refresh(List<MessageResp> rows) {
        messageRespList.addAll(rows);
        notifyDataSetChanged();
    }
}

class TextHolder extends RecyclerView.ViewHolder {

    TextView content;
    ImageView icon;

    public TextHolder(@NonNull View itemView) {
        super(itemView);
    }

    public TextHolder(@NonNull View itemView, TextView content, ImageView icon) {
        super(itemView);
        this.content = content;
        this.icon = icon;
    }

}