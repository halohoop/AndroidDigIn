package com.halohoop.androiddigin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;

import com.halohoop.androiddigin.widgets.ColorMatrixView;

public class ColorMatrixActivity extends AppCompatActivity implements View.OnClickListener {

    private GridView gv;
    private EditText[] ets = new EditText[20];
    private ColorMatrixView cmv;
    private float[] GREY_MODE = {
            0.33F, 0.59F, 0.11F, 0, 0,//R
            0.33F, 0.59F, 0.11F, 0, 0,//G
            0.33F, 0.59F, 0.11F, 0, 0,//B
            0, 0, 0, 1, 0             //A
    };
    private float[] INVERT_MODE = {
            -1, 0, 0, 1, 0,//R
            0, -1, 0, 1, 0,//G
            0, 0, -1, 1, 0,//B
            0, 0, 0, 1, 0  //A
    };
    private float[] MEMO_MODE = {
            0.393f, 0.769f, 0.189f, 0, 0,
            0.349f, 0.686f, 0.168f, 0, 0,
            0.272f, 0.534f, 0.131f, 0, 0,
            0, 0, 0, 1f, 0,
    };
    private float[] DESATURATE_MODE = {
            1.5f, 1.5f, 1.5f, 0, -1,
            1.5f, 1.5f, 1.5f, 0, -1,
            1.5f, 1.5f, 1.5f, 0, -1,
            0, 0, 0, 1f, 0,
    };
    private float[] HIGH_SATU_MODE = {
            1.438f, -0.122f, -0.016f, 0, -0.03f,
            -0.062f, 1.378f, -0.016f, 0, -0.05f,
            -0.062f, -0.122f, 1.483f, 0, -0.02f,
            0, 0, 0, 1f, 0,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_matrix);
        gv = (GridView) findViewById(R.id.gv);
        findViewById(R.id.btn_reset).setOnClickListener(this);
        findViewById(R.id.btn_change).setOnClickListener(this);

        findViewById(R.id.btn_gray).setOnClickListener(this);
        findViewById(R.id.btn_invert).setOnClickListener(this);
        findViewById(R.id.btn_memo).setOnClickListener(this);
        findViewById(R.id.btn_desaturate).setOnClickListener(this);
        findViewById(R.id.btn_high_sat).setOnClickListener(this);

        cmv = (ColorMatrixView) findViewById(R.id.cmv);
        initEts();
        gv.setAdapter(new MyAdapter());
        resetColors();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reset:
                resetColors();
                break;
            case R.id.btn_change:
                cmv.change(getColors());
                break;
            case R.id.btn_gray:
                cmv.change(GREY_MODE);
                setColors(GREY_MODE);
                break;
            case R.id.btn_invert:
                cmv.change(INVERT_MODE);
                setColors(INVERT_MODE);
                break;
            case R.id.btn_memo:
                cmv.change(MEMO_MODE);
                setColors(MEMO_MODE);
                break;
            case R.id.btn_desaturate:
                cmv.change(DESATURATE_MODE);
                setColors(DESATURATE_MODE);
                break;
            case R.id.btn_high_sat:
                cmv.change(HIGH_SATU_MODE);
                setColors(HIGH_SATU_MODE);
                break;
        }
    }

    private void initEts() {
        for (int position = 0; position < ets.length; position++) {
            if (ets[position] == null) {
                EditText editText = new EditText(ColorMatrixActivity.this);
                ets[position] = editText;
            }
        }
    }

    private void resetColors() {
        for (int position = 0; position < ets.length; position++) {
            if (position == 0
                    || position == 6
                    || position == 12
                    || position == 18) {
                ets[position].setText("1");
            } else {
                ets[position].setText("0");
            }
        }

    }

    private float[] getColors() {
        float[] floats = new float[20];
        for (int i = 0; i < floats.length; i++) {
            floats[i] = Float.valueOf(ets[i].getText().toString());
        }
        return floats;
    }

    private void setColors(float[] src) {
        for (int i = 0; i < src.length; i++) {
            ets[i].setText("" + src[i]);
        }
    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return ets.length;
        }

        @Override
        public Object getItem(int position) {
            return ets[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return ets[position];
        }
    }

}
