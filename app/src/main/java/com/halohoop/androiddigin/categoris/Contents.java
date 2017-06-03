package com.halohoop.androiddigin.categoris;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pooholah on 2017/5/17.
 * 这些数据需要换成数据库存储
 */

public class Contents {
    //后期还需要改成数据库来存储，并且数据库支持导出，一遍删除重新的时候可以重建数据
    public static String[] CONTENTS = {"放大镜MagnifierView", "理解ColorMatrix", "Reveal效果",
            "RadialGradient水波纹", "SweepGradient制作Radar雷达效果效果", "刮刮纸Xfermode",
            "menu怎么用", "FloatingActionButton和Snackbar怎么用", "单例吐司Toast，不需要等待上一个消失",
            "ListFragment怎么用", "FragmentStatePagerAdapter怎么用", "DialogFragment怎么用",
            "PreferenceFragment怎么用", "ViewDragHelper的使用", "Material Design各种实例",
            "TouchDelegate怎么使用","波浪，水涨起来",
    };
    //0--效果，特效
    //1--逻辑模板代码 套路
    //2--交互
    public static int[] CATEGORIS = {0, 0, 0,
            0, 0, 0,
            1, 1, 1,
            1, 1, 1,
            1, 1, 2,
            1, 0,
    };
    public static int CATEGORIS_COUNT = 3;

    //0--2个textview
    //1--3个textview
    public static int[] ITEM_TYPES = {0, 0, 0,
            0, 0, 0,
            0, 0, 0,
            0, 0, 1,
            1, 0, 0,
            0, 0,
    };
    public static int LIST_ITEM_TYPE_COUNT = 2;

    public static String getCategoryStr(int category) {
        switch (category) {
            case 0:
                return "特效";
            case 1:
                return "套路";
            case 2:
                return "交互";
        }
        return "";
    }

    /**
     * 加载全部或者某一类
     *
     * @param catagory
     * @return
     */
    public static List<ItemBean> queryItemBeans(int catagory) {
        List<ItemBean> itemBeens = new ArrayList<>();
        if (catagory == -1) {
            for (int i = 0; i < CONTENTS.length; i++) {//全部加载
                ItemBean itemBean = new ItemBean(i, CONTENTS[i],
                        getCategoryStr(CATEGORIS[i]), Contents.ITEM_TYPES[i]);
                itemBean.setCategoryTextColor(CATEGORIS[i]);
                itemBeens.add(itemBean);
            }
            return itemBeens;
        }
        for (int i = 0; i < CONTENTS.length; i++) {
            if (catagory == CATEGORIS[i]) {//特效
                //只加载 特效 或者 套路
                ItemBean itemBean = new ItemBean(i, CONTENTS[i],
                        getCategoryStr(CATEGORIS[i]), Contents.ITEM_TYPES[i]);
                itemBean.setCategoryTextColor(CATEGORIS[i]);
                itemBeens.add(itemBean);
            }
        }
        return itemBeens;
    }

    public static class ItemBean {
        private int index;
        private String name;
        private String category;
        private int categoryTextColor;
        private int itemtype;

        public ItemBean(int index, String name, String category, int itemtype) {
            this.index = index;
            this.name = name;
            this.category = category;
            this.itemtype = itemtype;
        }

        public int getCategoryTextColor() {
            return categoryTextColor;
        }

        public void setCategoryTextColor(int category) {
            switch (category) {
                case 0:
                    this.categoryTextColor = Color.RED;
                    break;
                case 1:
                    this.categoryTextColor = Color.BLUE;
                    break;
                case 2:
                    this.categoryTextColor = Color.GREEN;
                    break;
                default:
                    this.categoryTextColor = Color.RED;
            }
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getItemtype() {
            return itemtype;
        }

        public void setItemtype(int itemtype) {
            this.itemtype = itemtype;
        }
    }

}
