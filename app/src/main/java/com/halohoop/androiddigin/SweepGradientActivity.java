package com.halohoop.androiddigin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.halohoop.androiddigin.widgets.SweepGradientRadarView;

import java.util.Random;

public class SweepGradientActivity extends AppCompatActivity implements View.OnClickListener {

    private SweepGradientRadarView sgrv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sweep_gradient);
        sgrv = (SweepGradientRadarView) findViewById(R.id.sgrv);
        findViewById(R.id.iv).setOnClickListener(this);
        sgrv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv:
                Random random = new Random();
                int i = random.nextInt(26) + 65;
                sgrv.newSpot((char) i + "");
                break;
        }
    }

}
