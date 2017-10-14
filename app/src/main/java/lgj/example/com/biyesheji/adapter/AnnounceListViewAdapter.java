package lgj.example.com.biyesheji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.model.Announcement;


/**
 * Created by Administrator on 2017/10/12.
 */

public class AnnounceListViewAdapter extends BaseAdapter{

    private List<Announcement> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public AnnounceListViewAdapter(Context context, List<Announcement> data){
        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
    }
    /**
     * 组件集合，对应list.xml中的控件
     * @author Administrator
     */
    public final class Zujian{
        public TextView title_announce;
        public TextView content_announce;
        public TextView time_announce;
    }
    @Override
    public int getCount() {
        return data.size();
    }
    /**
     * 获得某一位置的数据
     */
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
    /**
     * 获得唯一标识
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Zujian zujian=null;
        if(convertView==null){
            zujian=new Zujian();
            //获得组件，实例化组件
            convertView=layoutInflater.inflate(R.layout.listview_announcement, null);
            zujian.title_announce=(TextView) convertView.findViewById(R.id.title_announce);
            zujian.content_announce=(TextView)convertView.findViewById(R.id.content_announce);
            zujian.time_announce=(TextView)convertView.findViewById(R.id.time_announce);
            convertView.setTag(zujian);
        }else{
            zujian=(Zujian)convertView.getTag();
        }
        //绑定数据

        zujian.title_announce.setText(data.get(position).getTitle());
        zujian.content_announce.setText(data.get(position).getContent());
        zujian.time_announce.setText(data.get(position).getTime());
        return convertView;
    }
}
