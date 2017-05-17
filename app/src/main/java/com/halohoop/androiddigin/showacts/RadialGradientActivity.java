package com.halohoop.androiddigin.showacts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.halohoop.androiddigin.R;
import com.halohoop.androiddigin.widgets.RadialGradientRippleView;

public class RadialGradientActivity extends AppCompatActivity {

    private RadialGradientRippleView mRgwv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radial_gradient);
        mRgwv = (RadialGradientRippleView) findViewById(R.id.rgwv);
    }

    public void minusRippleWidth(View view) {
        mRgwv.setmRadiusWidth(mRgwv.getmRadiusWidth() - 10);
    }

    public void plusRippleWidth(View view) {
        mRgwv.setmRadiusWidth(mRgwv.getmRadiusWidth() + 10);
    }
}
