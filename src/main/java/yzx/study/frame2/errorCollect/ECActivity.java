package yzx.study.frame2.errorCollect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ECActivity extends AppCompatActivity {

    private EAdapter adapter;
    private List<ECManager.ErrorBean> list;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        list = ECManager.getAll();
        if (list == null || list.isEmpty()) {
            Toast.makeText(ECActivity.this, "没有记录", Toast.LENGTH_SHORT).show();
            return;
        }


        ListView listview = new ListView(this);
        setContentView(listview);
        adapter = new EAdapter();
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(ECActivity.this)
                        .setMessage(list.get(position).err_desc + "\n\n 时间 :" + list.get(position).getErrTimeFormat())
                        .setTitle(list.get(position).err_title)
                        .create().show();
            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ECManager.ErrorBean b = list.remove(position);
                ECManager.delete(b._id);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }


    private class EAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv;
            if (convertView == null) {
                convertView = new TextView(ECActivity.this);
                tv = (TextView) convertView;
                tv.setPadding(8, 8, 8, 8);
            } else
                tv = (TextView) convertView;

            ECManager.ErrorBean b = list.get(position);
            tv.setText(b.err_title);
            tv.append("\n\n");
            tv.append(getTimeGapDesc(b.err_time));

            return convertView;
        }
    }


    private String getTimeGapDesc(String dbTime) {
        if (dbTime == null)
            return "时间不详";
        try {
            long errTime = Long.parseLong(dbTime);
            long nowTime = System.currentTimeMillis();
            long gap = nowTime - errTime;
            if (gap <= 0)
                return "这个错误发生在未来";

            long totalMiao = gap / 1000;
            long miao = totalMiao % 60;

            long totalFen = (totalMiao - miao) / 60;
            long fen = totalFen % 60;

            long totalXiaoshi = (totalFen - fen) / 60;
            long xiaoshi = totalXiaoshi % 24;

            long tian = (totalXiaoshi - xiaoshi) / 24;

            if (miao < 0)
                miao = 0;
            if (fen < 0)
                fen = 0;
            if (xiaoshi < 0)
                xiaoshi = 0;
            if (tian < 0)
                tian = 0;

            return "创建于 : " + tian + "天 " + xiaoshi + "小时 " + fen + "分 " + miao + "秒 前";
        } catch (Exception e) {
            return "时间不详";
        }
    }

}
