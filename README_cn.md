# QualityMatters [English](https://github.com/AndroidClasses/qualitymatters/blob/master/README.md)

本app全部遵[安卓开发文化这篇文章](http://artemzin.com/blog/android-development-culture-the-document-qualitymatters/)所有原则.

它包含有：

* CI持续集成 (Travis)
* UT单元测试 (有些用到Robolectric库, 有些则是纯JUnit runner外加模拟的`android.jar`).
* IT集成测试Http, REST, JSON解析和RxJava配合在一起后工作正常.
* 功能(UI)测试(库Espresso自定义规则, 模拟服务器和屏幕架构)从用户视角验证app像预期那样运行.
* 静态代码分析(FindBugs, PMD, Android Lint, Checkstyle) (详见`build.gradle`).
* 代码覆盖(进行中，借助jacoco-coverage插件在覆盖率不够大时停止编译示警).
* 开发者设置菜单提供启用/禁用[Stetho](http://facebook.github.io/stetho/), [LeakCanary](https://github.com/square/leakcanary), 等. 参考以下完整列表(并请自行添加更多工具!).
* MVP, RxJava, Dagger 2, Retrofit 2等等.

---
>❤️ by Artem Zinnatullin出品 [https://twitter.com/artem_zin](https://twitter.com/artem_zin).

编译工程请运行`sh ci.sh` (是的, 好简单, 因为它就应该简单啊).

截屏:

<img src="/site/screenshot1.png" width="400"> <img src="/site/screenshot2.png" width="400">

###开发者设置

**工具合集:**

* [Stetho](http://facebook.github.io/stetho/) — 通过Chromium的Developer Tools检测应用(网络请求, db, preferences等等). 开发者必备项.
* [LeakCanary](https://github.com/square/leakcanary) — 不依赖IDE而独立检测内存泄露! QA品控和开发者必备项.
* [TinyDancer](https://github.com/brianPlummer/TinyDancer) — 直接在手机屏幕查看帧率. QA品控和开发者必备项.

**实现细节**

开发者设置只出现在debug编译类型，它用到的库的源码和资源文件只会打进debug包．同时主代码线只需要知道开发这设置的抽象接口，debug版本实现了这些接口并实例化具体对象，release版本DeveloperSettingsModule(Dagger)只返回没有任何操作的DeveloperSettingsModel．

**为何只在debug版本?**
答案很简单-dex限制．LeakCanary引入将近3千个方法，Stetho有２千这样．我们加入开发这设置的工具愈多，我们的apk就愈加臃肿肥大．尤其主线代码方法数已经接近65k时，情况更糟糕．我们的产品app我们不得不为debug版本打开multidex选项.(如果这个不敏感的项目，也可以视情况把一些实用的工具在release版本里集成，默认隐藏，通过一些彩蛋或后门方式激活显示，而debug版本则默认就显示. 具体策略可视项目需要而定)

###包的结构

很多人问为啥实用基于组件来组织报的结构：`presenters`, `models`, 等待.而不是基于功能来组织:`itemslist`, `developersettings`,等等.

基于组件的包组织形式对项目新人(比如那些读这个app的人们)能轻易的发现app都有哪些`presenters`,有哪些`views`, `models`如此这般.如果你在读代码时想要快速跳转到那些相关的类时，你可以[在GitHub简单按下`t`进行搜索](https://github.com/blog/793-introducing-the-file-finder)要找的文件!
