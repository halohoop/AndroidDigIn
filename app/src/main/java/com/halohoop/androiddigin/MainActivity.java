package com.halohoop.androiddigin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lv;
    private String[] items = {"放大镜MagnifierView","理解ColorMatrix","Reveal效果"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(new MyAdapter());
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0://放大镜
                Intent intent = new Intent(this,MagnifierActivity.class);
                startAct(intent);
                break;
            case 1://ColorMatrix
                intent = new Intent(this,ColorMatrixActivity.class);
                startAct(intent);
                break;
            case 2://Reveal效果
                intent = new Intent(this,RevealActivity.class);
                startAct(intent);
                break;
        }
    }

    private void startAct(Intent intent) {
        startActivity(intent);
    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            return items[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView != null) {
                viewHolder = (ViewHolder) convertView.getTag();
            } else {
                View itemView = View.inflate(MainActivity.this, R.layout.item, null);
                viewHolder = new ViewHolder();
                viewHolder.tv = (TextView) itemView.findViewById(R.id.item_tv);
                itemView.setTag(viewHolder);
                convertView = itemView;
            }
            viewHolder.tv.setText(items[position]);
            return convertView;
        }
    }

    static class ViewHolder {
        TextView tv;
    }

}
