package lgj.example.com.biyesheji.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import lgj.example.com.biyesheji.model.PicInAlbumBean;
import lgj.example.com.biyesheji.widget.PicInAlbumItemView;

/**
 * Created by yu on 2017/10/11.
 */

public class ShowPicAdapter extends RecyclerView.Adapter<ShowPicAdapter.ShowPicListViewHolder> {

    private Context mContext;
    private List<PicInAlbumBean> mAllPicInfos;

    public ShowPicAdapter(Context context, List<PicInAlbumBean> allPicInfos) {
        mContext = context;
        mAllPicInfos = allPicInfos;
    }


    @Override
    public ShowPicAdapter.ShowPicListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShowPicListViewHolder(new PicInAlbumItemView(mContext));
    }

    @Override
    public void onBindViewHolder(final ShowPicAdapter.ShowPicListViewHolder holder, final int position) {
        holder.mShowPicItemView.bindView(mAllPicInfos.get(position));
        holder.mShowPicItemView.initPicInfo(mAllPicInfos);
        holder.mShowPicItemView.initImgUrlIndex(position);
    }

    @Override
    public int getItemCount() {
        return mAllPicInfos.size();
    }

    public void refreshAlbumAdpater(List<PicInAlbumBean> allPicInfos) {
        mAllPicInfos.clear();
        mAllPicInfos = allPicInfos;
        notifyDataSetChanged();
    }


    public class ShowPicListViewHolder extends RecyclerView.ViewHolder {

        private PicInAlbumItemView mShowPicItemView;

        public ShowPicListViewHolder(PicInAlbumItemView itemView) {
            super(itemView);
            mShowPicItemView = itemView;
        }
    }


}
