package org.flyants.book.view.conversation;

import androidx.recyclerview.widget.RecyclerView;

import org.flyants.book.R;
import org.flyants.book.network.image.ImageLoader;
import org.flyants.book.network.image.glide.CenterCropImageLoaderImpl;
import org.flyants.book.view.base.BaseRecyclerAdapter;
import org.flyants.book.view.base.RecyclerHolder;

import java.util.ArrayList;
import java.util.Collection;

public class ConversationAdapter extends BaseRecyclerAdapter<ConversationResp> {

    private ImageLoader imageLoader = new CenterCropImageLoaderImpl();

//
//    static List<ConversationResp>   lists = new ArrayList<ConversationResp>(){
//        public List<ConversationResp> $(){
//            for (int i = 0; i < 10; i++) {
//                this.add(new ConversationResp());
//            }
//            return this;
//        }
//    }.$();


    public ConversationAdapter(RecyclerView recyclerView) {
        this(recyclerView, new ArrayList<>(), R.layout.conversation_item);
    }

    @Override
    public void convert(RecyclerHolder holder, ConversationResp item, int position, boolean isScrolling) {
        imageLoader.loader(item.getIcon(),holder.getView(R.id.icon));
        holder.setText(R.id.name,item.getName());
        holder.setText(R.id.time,item.getType());
        holder.setText(R.id.msg,item.getType());
    }

    public ConversationAdapter(RecyclerView v, Collection<ConversationResp> datas, int itemLayoutId) {
        super(v, datas, itemLayoutId);
    }


}
