package com.halohoop.androiddigin.widgets;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Pooholah on 2017/6/22.
 */

public class VpTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        if (position <= 0 && position >= -1) {
            ViewGroup v = (ViewGroup) page;
            int childCount = v.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = v.getChildAt(i);
                childView.setTranslationX(v.getWidth() * (-position));
            }
        }else if(position <= 1 && position >= 0){
			ViewGroup v = (ViewGroup) page;
			int childCount = v.getChildCount();
			for (int i = 0; i < childCount; i++) {
				View childView = v.getChildAt(i);
				childView.setPivotX(childView.getWidth()>>1);
				childView.setPivotY(childView.getHeight() >> 1);
				float fraction = 0;
				if (position <= 0) {
					fraction = (0 - position) / 1f;
				} else if (position > 0) {
					fraction = (1 - position) / 1f;
				}
				childView.setRotation(360 * (1 - fraction));
                float factor = (float) Math.random()*3;
                if(childView.getTag()==null){
                    childView.setTag(factor);
                }else{
                    factor = (float) childView.getTag();
                }
                childView.setTranslationX(factor*childView.getWidth()*(+position));
			}
        }

    }
}
