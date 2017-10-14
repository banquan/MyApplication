package lgj.example.com.biyesheji.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import lgj.example.com.biyesheji.model.AllPicInfo;
import lgj.example.com.biyesheji.widget.ShowPicItemView;


/**
 * Created by yu on 2017/10/11.
 */

public class ShowPicAdapter extends RecyclerView.Adapter<ShowPicAdapter.ShowPicListViewHolder> {

    private Context mContext;
    private List<AllPicInfo> mAllPicInfos;

    public ShowPicAdapter(Context context, List<AllPicInfo> allPicInfos) {
        mContext = context;
        mAllPicInfos = allPicInfos;
    }


    @Override
    public ShowPicListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShowPicListViewHolder(new ShowPicItemView(mContext));
    }

    @Override
    public void onBindViewHolder(final ShowPicListViewHolder holder, final int position) {
        holder.mShowPicItemView.bindView(mAllPicInfos.get(position));
        holder.mShowPicItemView.initPicInfo(mAllPicInfos);
        holder.mShowPicItemView.initImgUrlIndex(position);
    }

    @Override
    public int getItemCount() {
        return mAllPicInfos.size();
    }

    public void refreshAlbumAdpater(List<AllPicInfo> allPicInfos) {
        mAllPicInfos.clear();
        mAllPicInfos = allPicInfos;
        notifyDataSetChanged();
    }


    public class ShowPicListViewHolder extends RecyclerView.ViewHolder {

        private ShowPicItemView mShowPicItemView;

        public ShowPicListViewHolder(ShowPicItemView itemView) {
            super(itemView);
            mShowPicItemView = itemView;
        }
    }


}
