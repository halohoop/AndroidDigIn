package com.halohoop.viewpagertransdemo;

import android.support.v4.view.ViewPager.PageTransformer;
import android.util.Log;
import android.view.View;

import static android.content.ContentValues.TAG;

public class WelcompagerTransformer implements PageTransformer {

	@Override
	public void transformPage(View view, float position) {
		Log.i(TAG, "transformPage1: "+view+"---"+position);
		if(position<=1&&position>=-1){
//			ViewGroup v = (ViewGroup) view.findViewById(R.id.rl);
//			int childCount = v.getChildCount();
//			for (int i = 0; i < childCount; i++) {
//				View childView = v.getChildAt(i);
//				float factor = (float) Math.random()*3;
//				if(childView.getTag()==null){
//					childView.setTag(factor);
//				}else{
//					factor = (float) childView.getTag();
//				}
//				/*
//				 * position : 0 ~ -1
//				 * translationX: 0 ~ childView.getWidth();
//				 */
//				childView.setTranslationX(factor*v.getWidth()*(+position));
//				childView.setPivotX(childView.getWidth()>>1);
//				childView.setPivotY(childView.getHeight() >> 1);
//				float fraction = 0;
//				if (position <= 0) {
//					fraction = (0 - position) / 1f;
//				} else if (position > 0) {
//					fraction = (1 - position) / 1f;
//				}
//				childView.setRotation(720 * 3 * (1 - fraction));
//				Log.i(TAG, "transformPage: left:"+childView+"----"+childView.getLeft());
//			}
//			float fraction = 0;
//			if (position<=0) {
//				fraction = (0 - position) / 1f;
//			}else if(position>0){
//				fraction = (1 - position) / 1f;
//			}
//			Log.i(TAG, "transformPage: fraction:"+view+"----"+fraction);
//			for (int i = 0; i < childCount; i++) {
//				View childView = v.getChildAt(i);
//				/**
//				 * position : 0 ~ -1
//				 * translationX: 0 ~ childView.getWidth();
//				 */
//				childView.setTranslationX((v.getWidth()>>1)*fraction);
//			}

//			ViewGroup v = (ViewGroup) view;
//			int childCount = v.getChildCount();
//			for (int i = 0; i < childCount; i++) {
//				View childView = v.getChildAt(i);
//				childView.setPivotX(childView.getWidth()>>1);
//				childView.setPivotY(childView.getHeight() >> 1);
//				float fraction = 0;
//				if (position <= 0) {
//					fraction = (0 - position) / 1f;
//				} else if (position > 0) {
//					fraction = (1 - position) / 1f;
//				}
//				childView.setRotation(360 * (1 - fraction));
//				childView.setTranslationX(v.getWidth() * (-position));
//			}
			
			
			//Ч��1
//			view.setScaleX(1-Math.abs(position));
//			view.setScaleY(1-Math.abs(position));
			//Ч��2
			view.setScaleX(Math.max(0.9f,1-Math.abs(position)));
			view.setScaleY(Math.max(0.9f,1-Math.abs(position)));
			//Ч��3 3D��ת
//			view.setPivotX(position<0f?view.getWidth():0f);//���ҳ�棺0~-1���ұߵ�ҳ�棺1~0
//			view.setPivotY(view.getHeight()*0.5f);
//			view.setRotationY(position*45f);//0~90��
			//Ч��4 3D�ڷ�ת
//			view.setPivotX(position<0f?view.getWidth():0f);//���ҳ�棺0~-1���ұߵ�ҳ�棺1~0
//			view.setPivotY(view.getHeight()*0.5f);
//			view.setRotationY(-position*45f);//0~90��
			
			view.setPivotX(view.getWidth()*0.5f);//���ҳ�棺0~-1���ұߵ�ҳ�棺1~0
			view.setPivotY(view.getHeight()*0.5f);
			view.setRotationY(-position*45f);//0~90��
			
			
		}
	}

}
