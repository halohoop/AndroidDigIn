package com.halohoop.androiddigin;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.halohoop.androiddigin.utils.Utils;
import com.halohoop.androiddigin.widgets.RevealDrawable;

public class RevealActivity extends AppCompatActivity implements View.OnClickListener {

    private RevealDrawable revealDrawable;
    private int level = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView imageView = new ImageView(RevealActivity.this);
        Drawable unSelectedDrawable = getResources().getDrawable(R.drawable.pic);
        Drawable selectedDrawable = Utils.handleDrawable(getResources(),
                ((BitmapDrawable) unSelectedDrawable).getBitmap());
        revealDrawable = new RevealDrawable(selectedDrawable, unSelectedDrawable);
        imageView.setImageDrawable(revealDrawable);
        imageView.setOnClickListener(this);
        setContentView(imageView);
        Toast.makeText(this, "Click Please!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        level += 250;
        if (level > 10000) {
            level %= 10000;
        }
        revealDrawable.setLevel(level);
    }
}
