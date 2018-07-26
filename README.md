V# SimpleWeather
---------------
<!-- TOC -->

- [概述](#概述)
- [城市选择](#城市选择)
- [天气预报](#天气预报)
- [日历](#日历)
- [设置](#设置)

<!-- /TOC -->
# 概述
# 城市选择
# 天气预报
# 日历
日历界面可以显示当前月的日历，当天日期通过红色圆圈标注,可以通过左右滑动/点击前后两个ImageButton切换月份来查看其他月份日历，点击home（房子造型）的图片按钮即可回到主页。

具体效果如下图：

![](http://simpleweather.oss-cn-beijing.aliyuncs.com/18-7-25/33619403.jpg)

文件组织如下图：

![](http://simpleweather.oss-cn-beijing.aliyuncs.com/18-7-25/6912410.jpg)

**CalendarActivity.java:**
通过主页的导航栏中“日历”选项进入，显示页面布局文件（activity_calendar.xml），并对前后翻页、回到主页、左右滑动进行响应，更新视图。

**CalendarCard.java:**
定义一个日历卡,每一个月代表一个日历卡,我们通过计算每个月的日期,然后根据计算出来的位置绘制我们的数字。  所有绘制的操作在onDraw方面里实现,我这里定于了一个组对象Row、单元格元素Cell,通过Row[row].cell[col]来确定一个单元格,每次调用invalidate重绘视图。

**DateUtil.java & CustomDate.java：**
确定日期格式，获取与计算每个月的日期。

**CalendarViewAdapter.java:**
左右切换时选用ViewPager,传入一个日历卡数组,让ViewPager循环复用这几个日历卡,避免消耗内存。

**布局文件activity_calendar.xml:**
整体采用LinearLayout布局，导航栏使用RelativeLayout，显示日历部分使用TableLayout。

# 设置
