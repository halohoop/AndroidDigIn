# Halohoop Android Skill Dig In
## 1.Magifier，放大镜效果
![demo pic](./device-2017-05-04-181727.gif)

* Matrix的使用；
* 求大小两圆切点，数学几何；
* ShapeDrawable的使用；
* BitmapShader的使用；

## 2.[歌词走马灯](https://github.com/halohoop/PartsShowTextView)
![demo pic](./device-2017-05-04-120156.gif)

* LinearGradient的使用;
* Matrix的使用(setTranslate);
* Paint.measureText();
* Paint.setShader();
* onDraw中循环调用postInvalidateDelayed；

## 3.理解ColorMatrix

![demo pic](./ColorMatrix.png)

* ColorMatrix的使用；
* 色调setRotate
* 饱和度setSaturation
* 亮度setScale
* 集中特殊的处理（如：灰度、反相、怀旧、去色、高饱和）

## 4.Reveal效果

![demo pic](./device-2017-05-06-002903.gif)

* 代码自定义drawable，内存画布;
* Gravity.apply的使用，从Rect扣出另一个Rect;
* canvas.clipRect裁剪画布，限定绘制区域；
* canvas.save();和canvas.restore();的使用;
* Drawable的setLevel以及onLevelChange的使用;
* Drawble重绘调用invalidateSelf;

## 5.RadialGradient实现水波纹效果

![demo pic](./device-2017-05-12-010202.gif)

* RadialGradient的使用，也是shader的一种（子类）;
* BitmapShader的使用;
* ComposeShader的使用;
* ComposeShader结合PorterDuff.Mode的使用;

## 6.SweepGradient制作Radar雷达效果效果

![Demo pic](./device-2017-05-13-092925.gif)

* SweepGradient的使用，也是shader的一种（子类）;
* 果冻效果;
* onDraw中调用invalidate的方式做循环;

## 7.刮刮纸ScratchView

![Demo pic](./device-2017-05-15-182750.gif)

* Xfermode的使用;
* Xfermode生效可能需要关闭硬件加速setLayerType(LAYER_TYPE_SOFTWARE, null);
* Xfermode生效可能需要Canvas.saveLayer;
* 让笔触更加平滑的算法quadTo;
* 自创的加解密一个文件的方法，把文件放到raw中，然后用流解析;

## 8.menu怎么用

* 重写方法onCreateOptionsMenu;
* R.menu.main_menu  xml中定义点击事件;

## 9.FloatingActionButton和Snackbar怎么用
## 10.单例吐司Toast，不需要等待上一个消失

## 11.To be continued...


## License

    Copyright 2017, Halohoop

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.