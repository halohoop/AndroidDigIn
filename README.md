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

## 5.To be continued

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