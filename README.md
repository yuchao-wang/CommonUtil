## CommonUtil For Android

- Min sdk on support is 15(Android 4.0.3)

### Demo

[Download](https://codeload.github.com/yuchao-wang/CommonUtil/zip/master)

### Update

#### 1.0.0（2016/05/03）

**ADD**

1. ClickUtil、ConvertUtil、DeviceUtil、DimensionUtil、GsonUtil、NetworkUtil、ViewUtil
2. CountDownTimer:compatible CountDownTimer
3. AsyncTaskNew:no thread pool to control
4. MD5
5. SharePreferenceManager:save single class to preference

### Dependence 

```
compile 'wang.yuchao.android.library.util:CommonUtilLibrary:1.0.0'
```

### How To Use

```
CommonUtilLibrary.init(context);
```

### Proguard

```
-keep class wang.yuchao.android.library.** { *; }
-dontwarn wang.yuchao.android.library.**
```

### [About Me](http://yuchao.wang)

### License

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```