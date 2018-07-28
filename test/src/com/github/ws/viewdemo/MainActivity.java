package com.github.ws.viewdemo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.R;
import com.github.ws.viewdemo.entity.Item;
import com.prize.widget.CircleMenuLayout;
import com.prize.widget.CircleMenuLayout.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private CircleMenuLayout circleMenuLayout;

    List<Item> mList = new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circleMenuLayout = (CircleMenuLayout) findViewById(R.id.cm);

        Item item = new Item();
        item.imageRes = R.drawable.aql;
        item.text = "1";
        mList.add(item);
        item = new Item();
        item.imageRes = R.drawable.dc;
        item.text = "2";
        mList.add(item);
        item = new Item();
        item.imageRes = R.drawable.cz;
        item.text = "3";
        mList.add(item);
        item = new Item();
        item.imageRes = R.drawable.dp;
        item.text = "4";
        mList.add(item);
        item = new Item();
        item.imageRes = R.drawable.kb;
        item.text = "璁㈤";
        //mList.add(item);
        item = new Item();
        item.imageRes = R.drawable.rw;
        item.text = "蹇��";
        //mList.add(item);


        circleMenuLayout.setAdapter(new MyAdapter());

        circleMenuLayout.setOnItemClickListener(new CircleMenuLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(MainActivity.this, mList.get(position).text + " Click", Toast.LENGTH_SHORT).show();
            }
        });
        
        circleMenuLayout.setOnItemLongClickListener(new OnItemLongClickListener() { 
            @Override
            public boolean onItemLongClick(View v, int position) {
                Toast.makeText(MainActivity.this, mList.get(position).text + " Long Click", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    public class  MyAdapter extends  BaseAdapter{
        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
            View itemView = mInflater.inflate(R.layout.circle_menu_item, parent, false);
            initMenuItem(itemView, position);
            return itemView;
        }

        // 鍒濆鍖栬彍鍗曢」
        private void initMenuItem(View itemView, int position) {
            // 鑾峰彇鏁版嵁椤�
            final Item item = (Item) getItem(position);
            ImageView iv = (ImageView) itemView
                    .findViewById(R.id.iv_icon);
            TextView tv = (TextView) itemView
                    .findViewById(R.id.tv_text);
            // 鏁版嵁缁戝畾
            iv.setImageResource(item.imageRes);
            tv.setText(item.text);
        }
    }
}
