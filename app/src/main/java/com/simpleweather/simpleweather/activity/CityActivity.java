package com.simpleweather.simpleweather.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.simpleweather.simpleweather.R;

import java.util.*;

public class CityActivity extends AppCompatActivity {
    public static final String cityDataList = "[{\"cityName\":\"广州\",\"firstLetter\":\"*\"},{\"cityName\":\"北京\",\"firstLetter\":\"*\"},{\"cityName\":\"河源\",\"firstLetter\":\"*\"},{\"cityName\":\"襄阳\",\"firstLetter\":\"*\"},{\"cityName\":\"上海\",\"firstLetter\":\"*\"},{\"cityName\":\"深圳\",\"firstLetter\":\"*\"},{\"cityName\":\"鞍山\",\"firstLetter\":\"A\"},{\"cityName\":\"安庆\",\"firstLetter\":\"A\"},{\"cityName\":\"安阳\",\"firstLetter\":\"A\"},{\"cityName\":\"阿坝\",\"firstLetter\":\"A\"},{\"cityName\":\"安顺\",\"firstLetter\":\"A\"},{\"cityName\":\"安康\",\"firstLetter\":\"A\"},{\"cityName\":\"阿里\",\"firstLetter\":\"A\"},{\"cityName\":\"阿勒泰\",\"firstLetter\":\"A\"},{\"cityName\":\"阿克苏\",\"firstLetter\":\"A\"},{\"cityName\":\"阿拉尔\",\"firstLetter\":\"A\"},{\"cityName\":\"阿拉善盟\",\"firstLetter\":\"A\"},{\"cityName\":\"澳门\",\"firstLetter\":\"A\"},{\"cityName\":\"北京\",\"firstLetter\":\"B\"},{\"cityName\":\"保定\",\"firstLetter\":\"B\"},{\"cityName\":\"本溪\",\"firstLetter\":\"B\"},{\"cityName\":\"白城\",\"firstLetter\":\"B\"},{\"cityName\":\"白山\",\"firstLetter\":\"B\"},{\"cityName\":\"蚌埠\",\"firstLetter\":\"B\"},{\"cityName\":\"亳州\",\"firstLetter\":\"B\"},{\"cityName\":\"滨州\",\"firstLetter\":\"B\"},{\"cityName\":\"白银\",\"firstLetter\":\"B\"},{\"cityName\":\"巴中\",\"firstLetter\":\"B\"},{\"cityName\":\"毕节\",\"firstLetter\":\"B\"},{\"cityName\":\"白沙\",\"firstLetter\":\"B\"},{\"cityName\":\"保亭\",\"firstLetter\":\"B\"},{\"cityName\":\"保山\",\"firstLetter\":\"B\"},{\"cityName\":\"宝鸡\",\"firstLetter\":\"B\"},{\"cityName\":\"百色\",\"firstLetter\":\"B\"},{\"cityName\":\"北海\",\"firstLetter\":\"B\"},{\"cityName\":\"博尔塔拉\",\"firstLetter\":\"B\"},{\"cityName\":\"巴音郭楞\",\"firstLetter\":\"B\"},{\"cityName\":\"包头\",\"firstLetter\":\"B\"},{\"cityName\":\"巴彦淖尔\",\"firstLetter\":\"B\"},{\"cityName\":\"重庆\",\"firstLetter\":\"C\"},{\"cityName\":\"承德\",\"firstLetter\":\"C\"},{\"cityName\":\"沧州\",\"firstLetter\":\"C\"},{\"cityName\":\"长治\",\"firstLetter\":\"C\"},{\"cityName\":\"朝阳\",\"firstLetter\":\"C\"},{\"cityName\":\"常州\",\"firstLetter\":\"C\"},{\"cityName\":\"滁州\",\"firstLetter\":\"C\"},{\"cityName\":\"巢湖\",\"firstLetter\":\"C\"},{\"cityName\":\"池州\",\"firstLetter\":\"C\"},{\"cityName\":\"长沙\",\"firstLetter\":\"C\"},{\"cityName\":\"郴州\",\"firstLetter\":\"C\"},{\"cityName\":\"常德\",\"firstLetter\":\"C\"},{\"cityName\":\"潮州\",\"firstLetter\":\"C\"},{\"cityName\":\"成都\",\"firstLetter\":\"C\"},{\"cityName\":\"澄迈\",\"firstLetter\":\"C\"},{\"cityName\":\"昌江\",\"firstLetter\":\"C\"},{\"cityName\":\"楚雄\",\"firstLetter\":\"C\"},{\"cityName\":\"崇左\",\"firstLetter\":\"C\"},{\"cityName\":\"昌都\",\"firstLetter\":\"C\"},{\"cityName\":\"昌吉\",\"firstLetter\":\"C\"},{\"cityName\":\"赤峰\",\"firstLetter\":\"C\"},{\"cityName\":\"大同\",\"firstLetter\":\"D\"},{\"cityName\":\"大连\",\"firstLetter\":\"D\"},{\"cityName\":\"丹东\",\"firstLetter\":\"D\"},{\"cityName\":\"大兴安岭\",\"firstLetter\":\"D\"},{\"cityName\":\"大庆\",\"firstLetter\":\"D\"},{\"cityName\":\"德州\",\"firstLetter\":\"D\"},{\"cityName\":\"东营\",\"firstLetter\":\"D\"},{\"cityName\":\"东营\",\"firstLetter\":\"D\"},{\"cityName\":\"东莞\",\"firstLetter\":\"D\"},{\"cityName\":\"定西\",\"firstLetter\":\"D\"},{\"cityName\":\"达州\",\"firstLetter\":\"D\"},{\"cityName\":\"德阳\",\"firstLetter\":\"D\"},{\"cityName\":\"儋州\",\"firstLetter\":\"D\"},{\"cityName\":\"东方\",\"firstLetter\":\"D\"},{\"cityName\":\"定安\",\"firstLetter\":\"D\"},{\"cityName\":\"德宏\",\"firstLetter\":\"D\"},{\"cityName\":\"大理\",\"firstLetter\":\"D\"},{\"cityName\":\"迪庆\",\"firstLetter\":\"D\"},{\"cityName\":\"鄂州\",\"firstLetter\":\"E\"},{\"cityName\":\"恩施\",\"firstLetter\":\"E\"},{\"cityName\":\"鄂尔多斯\",\"firstLetter\":\"E\"},{\"cityName\":\"抚顺\",\"firstLetter\":\"F\"},{\"cityName\":\"阜新\",\"firstLetter\":\"F\"},{\"cityName\":\"阜阳\",\"firstLetter\":\"F\"},{\"cityName\":\"福州\",\"firstLetter\":\"F\"},{\"cityName\":\"抚州\",\"firstLetter\":\"F\"},{\"cityName\":\"佛山\",\"firstLetter\":\"F\"},{\"cityName\":\"防城港\",\"firstLetter\":\"F\"},{\"cityName\":\"赣州\",\"firstLetter\":\"G\"},{\"cityName\":\"广州\",\"firstLetter\":\"G\"},{\"cityName\":\"甘南\",\"firstLetter\":\"G\"},{\"cityName\":\"广安\",\"firstLetter\":\"G\"},{\"cityName\":\"甘孜\",\"firstLetter\":\"G\"},{\"cityName\":\"广元\",\"firstLetter\":\"G\"},{\"cityName\":\"贵阳\",\"firstLetter\":\"G\"},{\"cityName\":\"果洛\",\"firstLetter\":\"G\"},{\"cityName\":\"桂林\",\"firstLetter\":\"G\"},{\"cityName\":\"贵港\",\"firstLetter\":\"G\"},{\"cityName\":\"固原\",\"firstLetter\":\"G\"},{\"cityName\":\"高雄\",\"firstLetter\":\"G\"},{\"cityName\":\"邯郸\",\"firstLetter\":\"H\"},{\"cityName\":\"衡水\",\"firstLetter\":\"H\"},{\"cityName\":\"葫芦岛\",\"firstLetter\":\"H\"},{\"cityName\":\"哈尔滨\",\"firstLetter\":\"H\"},{\"cityName\":\"鹤岗\",\"firstLetter\":\"H\"},{\"cityName\":\"黑河\",\"firstLetter\":\"H\"},{\"cityName\":\"淮安\",\"firstLetter\":\"H\"},{\"cityName\":\"杭州\",\"firstLetter\":\"H\"},{\"cityName\":\"湖州\",\"firstLetter\":\"H\"},{\"cityName\":\"合肥\",\"firstLetter\":\"H\"},{\"cityName\":\"淮南\",\"firstLetter\":\"H\"},{\"cityName\":\"淮北\",\"firstLetter\":\"H\"},{\"cityName\":\"黄山\",\"firstLetter\":\"H\"},{\"cityName\":\"菏泽\",\"firstLetter\":\"H\"},{\"cityName\":\"鹤壁\",\"firstLetter\":\"H\"},{\"cityName\":\"黄冈\",\"firstLetter\":\"H\"},{\"cityName\":\"黄石\",\"firstLetter\":\"H\"},{\"cityName\":\"衡阳\",\"firstLetter\":\"H\"},{\"cityName\":\"怀化\",\"firstLetter\":\"H\"},{\"cityName\":\"惠州\",\"firstLetter\":\"H\"},{\"cityName\":\"河源\",\"firstLetter\":\"H\"},{\"cityName\":\"海口\",\"firstLetter\":\"H\"},{\"cityName\":\"红河\",\"firstLetter\":\"H\"},{\"cityName\":\"海北\",\"firstLetter\":\"H\"},{\"cityName\":\"海东\",\"firstLetter\":\"H\"},{\"cityName\":\"黄南\",\"firstLetter\":\"H\"},{\"cityName\":\"海南\",\"firstLetter\":\"H\"},{\"cityName\":\"海西\",\"firstLetter\":\"H\"},{\"cityName\":\"汉中\",\"firstLetter\":\"H\"},{\"cityName\":\"贺州\",\"firstLetter\":\"H\"},{\"cityName\":\"河池\",\"firstLetter\":\"H\"},{\"cityName\":\"哈密\",\"firstLetter\":\"H\"},{\"cityName\":\"和田\",\"firstLetter\":\"H\"},{\"cityName\":\"呼伦贝尔\",\"firstLetter\":\"H\"},{\"cityName\":\"呼和浩特\",\"firstLetter\":\"H\"},{\"cityName\":\"晋中\",\"firstLetter\":\"J\"},{\"cityName\":\"晋城\",\"firstLetter\":\"J\"},{\"cityName\":\"锦州\",\"firstLetter\":\"J\"},{\"cityName\":\"吉林\",\"firstLetter\":\"J\"},{\"cityName\":\"鸡西\",\"firstLetter\":\"J\"},{\"cityName\":\"佳木斯\",\"firstLetter\":\"J\"},{\"cityName\":\"嘉兴\",\"firstLetter\":\"J\"},{\"cityName\":\"金华\",\"firstLetter\":\"J\"},{\"cityName\":\"九江\",\"firstLetter\":\"J\"},{\"cityName\":\"吉安\",\"firstLetter\":\"J\"},{\"cityName\":\"景德镇\",\"firstLetter\":\"J\"},{\"cityName\":\"济南\",\"firstLetter\":\"J\"},{\"cityName\":\"济宁\",\"firstLetter\":\"J\"},{\"cityName\":\"济源\",\"firstLetter\":\"J\"},{\"cityName\":\"焦作\",\"firstLetter\":\"J\"},{\"cityName\":\"荆州\",\"firstLetter\":\"J\"},{\"cityName\":\"荆门\",\"firstLetter\":\"J\"},{\"cityName\":\"揭阳\",\"firstLetter\":\"J\"},{\"cityName\":\"江门\",\"firstLetter\":\"J\"},{\"cityName\":\"金昌\",\"firstLetter\":\"J\"},{\"cityName\":\"嘉峪关\",\"firstLetter\":\"J\"},{\"cityName\":\"酒泉\",\"firstLetter\":\"J\"},{\"cityName\":\"基隆\",\"firstLetter\":\"J\"},{\"cityName\":\"嘉义\",\"firstLetter\":\"J\"},{\"cityName\":\"开封\",\"firstLetter\":\"K\"},{\"cityName\":\"昆明\",\"firstLetter\":\"K\"},{\"cityName\":\"克州\",\"firstLetter\":\"K\"},{\"cityName\":\"克拉玛依\",\"firstLetter\":\"K\"},{\"cityName\":\"喀什\",\"firstLetter\":\"K\"},{\"cityName\":\"廊坊\",\"firstLetter\":\"L\"},{\"cityName\":\"临汾\",\"firstLetter\":\"L\"},{\"cityName\":\"吕梁\",\"firstLetter\":\"L\"},{\"cityName\":\"辽阳\",\"firstLetter\":\"L\"},{\"cityName\":\"辽源\",\"firstLetter\":\"L\"},{\"cityName\":\"连云港\",\"firstLetter\":\"L\"},{\"cityName\":\"丽水\",\"firstLetter\":\"L\"},{\"cityName\":\"六安\",\"firstLetter\":\"L\"},{\"cityName\":\"龙岩\",\"firstLetter\":\"L\"},{\"cityName\":\"临沂\",\"firstLetter\":\"L\"},{\"cityName\":\"莱芜\",\"firstLetter\":\"L\"},{\"cityName\":\"聊城\",\"firstLetter\":\"L\"},{\"cityName\":\"洛阳\",\"firstLetter\":\"L\"},{\"cityName\":\"漯河\",\"firstLetter\":\"L\"},{\"cityName\":\"娄底\",\"firstLetter\":\"L\"},{\"cityName\":\"兰州\",\"firstLetter\":\"L\"},{\"cityName\":\"陇南\",\"firstLetter\":\"L\"},{\"cityName\":\"泸州\",\"firstLetter\":\"L\"},{\"cityName\":\"乐山\",\"firstLetter\":\"L\"},{\"cityName\":\"凉山\",\"firstLetter\":\"L\"},{\"cityName\":\"六盘水\",\"firstLetter\":\"L\"},{\"cityName\":\"临高\",\"firstLetter\":\"L\"},{\"cityName\":\"乐东\",\"firstLetter\":\"L\"},{\"cityName\":\"陵水\",\"firstLetter\":\"L\"},{\"cityName\":\"临沧\",\"firstLetter\":\"L\"},{\"cityName\":\"丽江\",\"firstLetter\":\"L\"},{\"cityName\":\"来宾\",\"firstLetter\":\"L\"},{\"cityName\":\"柳州\",\"firstLetter\":\"L\"},{\"cityName\":\"拉萨\",\"firstLetter\":\"L\"},{\"cityName\":\"林芝\",\"firstLetter\":\"L\"},{\"cityName\":\"牡丹江\",\"firstLetter\":\"M\"},{\"cityName\":\"马鞍山\",\"firstLetter\":\"M\"},{\"cityName\":\"茂名\",\"firstLetter\":\"M\"},{\"cityName\":\"梅州\",\"firstLetter\":\"M\"},{\"cityName\":\"绵阳\",\"firstLetter\":\"M\"},{\"cityName\":\"眉山\",\"firstLetter\":\"M\"},{\"cityName\":\"南京\",\"firstLetter\":\"N\"},{\"cityName\":\"南通\",\"firstLetter\":\"N\"},{\"cityName\":\"宁波\",\"firstLetter\":\"N\"},{\"cityName\":\"宁德\",\"firstLetter\":\"N\"},{\"cityName\":\"南平\",\"firstLetter\":\"N\"},{\"cityName\":\"南昌\",\"firstLetter\":\"N\"},{\"cityName\":\"南阳\",\"firstLetter\":\"N\"},{\"cityName\":\"宁夏\",\"firstLetter\":\"N\"},{\"cityName\":\"南充\",\"firstLetter\":\"N\"},{\"cityName\":\"内江\",\"firstLetter\":\"N\"},{\"cityName\":\"怒江\",\"firstLetter\":\"N\"},{\"cityName\":\"南宁\",\"firstLetter\":\"N\"},{\"cityName\":\"那曲\",\"firstLetter\":\"N\"},{\"cityName\":\"盘锦\",\"firstLetter\":\"P\"},{\"cityName\":\"莆田\",\"firstLetter\":\"P\"},{\"cityName\":\"萍乡\",\"firstLetter\":\"P\"},{\"cityName\":\"平顶山\",\"firstLetter\":\"P\"},{\"cityName\":\"濮阳\",\"firstLetter\":\"P\"},{\"cityName\":\"平凉\",\"firstLetter\":\"P\"},{\"cityName\":\"攀枝花\",\"firstLetter\":\"P\"},{\"cityName\":\"普洱\",\"firstLetter\":\"P\"},{\"cityName\":\"秦皇岛\",\"firstLetter\":\"Q\"},{\"cityName\":\"齐齐哈尔\",\"firstLetter\":\"Q\"},{\"cityName\":\"七台河\",\"firstLetter\":\"Q\"},{\"cityName\":\"衢州\",\"firstLetter\":\"Q\"},{\"cityName\":\"泉州\",\"firstLetter\":\"Q\"},{\"cityName\":\"青岛\",\"firstLetter\":\"Q\"},{\"cityName\":\"潜江\",\"firstLetter\":\"Q\"},{\"cityName\":\"清远\",\"firstLetter\":\"Q\"},{\"cityName\":\"庆阳\",\"firstLetter\":\"Q\"},{\"cityName\":\"黔南\",\"firstLetter\":\"Q\"},{\"cityName\":\"黔东南\",\"firstLetter\":\"Q\"},{\"cityName\":\"黔西南\",\"firstLetter\":\"Q\"},{\"cityName\":\"琼海\",\"firstLetter\":\"Q\"},{\"cityName\":\"琼中\",\"firstLetter\":\"Q\"},{\"cityName\":\"曲靖\",\"firstLetter\":\"Q\"},{\"cityName\":\"日照\",\"firstLetter\":\"R\"},{\"cityName\":\"日喀\",\"firstLetter\":\"R\"},{\"cityName\":\"上海\",\"firstLetter\":\"S\"},{\"cityName\":\"石家庄\",\"firstLetter\":\"S\"},{\"cityName\":\"朔州\",\"firstLetter\":\"S\"},{\"cityName\":\"沈阳\",\"firstLetter\":\"S\"},{\"cityName\":\"四平\",\"firstLetter\":\"S\"},{\"cityName\":\"松原\",\"firstLetter\":\"S\"},{\"cityName\":\"双鸭山\",\"firstLetter\":\"S\"},{\"cityName\":\"绥化\",\"firstLetter\":\"S\"},{\"cityName\":\"苏州\",\"firstLetter\":\"S\"},{\"cityName\":\"宿迁\",\"firstLetter\":\"S\"},{\"cityName\":\"绍兴\",\"firstLetter\":\"S\"},{\"cityName\":\"宿州\",\"firstLetter\":\"S\"},{\"cityName\":\"厦门\",\"firstLetter\":\"S\"},{\"cityName\":\"三明\",\"firstLetter\":\"S\"},{\"cityName\":\"上饶\",\"firstLetter\":\"S\"},{\"cityName\":\"商丘\",\"firstLetter\":\"S\"},{\"cityName\":\"三门峡\",\"firstLetter\":\"S\"},{\"cityName\":\"神农架\",\"firstLetter\":\"S\"},{\"cityName\":\"十堰\",\"firstLetter\":\"S\"},{\"cityName\":\"随州\",\"firstLetter\":\"S\"},{\"cityName\":\"邵阳\",\"firstLetter\":\"S\"},{\"cityName\":\"汕尾\",\"firstLetter\":\"S\"},{\"cityName\":\"韶关\",\"firstLetter\":\"S\"},{\"cityName\":\"汕头\",\"firstLetter\":\"S\"},{\"cityName\":\"深圳\",\"firstLetter\":\"S\"},{\"cityName\":\"遂宁\",\"firstLetter\":\"S\"},{\"cityName\":\"三亚\",\"firstLetter\":\"S\"},{\"cityName\":\"商洛\",\"firstLetter\":\"S\"},{\"cityName\":\"山南\",\"firstLetter\":\"S\"},{\"cityName\":\"石嘴山\",\"firstLetter\":\"S\"},{\"cityName\":\"石河子\",\"firstLetter\":\"S\"},{\"cityName\":\"天津\",\"firstLetter\":\"T\"},{\"cityName\":\"唐山\",\"firstLetter\":\"T\"},{\"cityName\":\"太原\",\"firstLetter\":\"T\"},{\"cityName\":\"铁岭\",\"firstLetter\":\"T\"},{\"cityName\":\"通化\",\"firstLetter\":\"T\"},{\"cityName\":\"泰州\",\"firstLetter\":\"T\"},{\"cityName\":\"台州\",\"firstLetter\":\"T\"},{\"cityName\":\"铜陵\",\"firstLetter\":\"T\"},{\"cityName\":\"泰安\",\"firstLetter\":\"T\"},{\"cityName\":\"天门\",\"firstLetter\":\"T\"},{\"cityName\":\"天水\",\"firstLetter\":\"T\"},{\"cityName\":\"铜仁\",\"firstLetter\":\"T\"},{\"cityName\":\"屯昌\",\"firstLetter\":\"T\"},{\"cityName\":\"铜川\",\"firstLetter\":\"T\"},{\"cityName\":\"塔城\",\"firstLetter\":\"T\"},{\"cityName\":\"吐鲁番\",\"firstLetter\":\"T\"},{\"cityName\":\"图木舒克\",\"firstLetter\":\"T\"},{\"cityName\":\"通辽\",\"firstLetter\":\"T\"},{\"cityName\":\"台北\",\"firstLetter\":\"T\"},{\"cityName\":\"台中\",\"firstLetter\":\"T\"},{\"cityName\":\"台南\",\"firstLetter\":\"T\"},{\"cityName\":\"无锡\",\"firstLetter\":\"W\"},{\"cityName\":\"温州\",\"firstLetter\":\"W\"},{\"cityName\":\"芜湖\",\"firstLetter\":\"W\"},{\"cityName\":\"潍坊\",\"firstLetter\":\"W\"},{\"cityName\":\"威海\",\"firstLetter\":\"W\"},{\"cityName\":\"武汉\",\"firstLetter\":\"W\"},{\"cityName\":\"武威\",\"firstLetter\":\"W\"},{\"cityName\":\"五指山\",\"firstLetter\":\"W\"},{\"cityName\":\"文昌\",\"firstLetter\":\"W\"},{\"cityName\":\"万宁\",\"firstLetter\":\"W\"},{\"cityName\":\"文山\",\"firstLetter\":\"W\"},{\"cityName\":\"渭南\",\"firstLetter\":\"W\"},{\"cityName\":\"梧州\",\"firstLetter\":\"W\"},{\"cityName\":\"吴忠\",\"firstLetter\":\"W\"},{\"cityName\":\"乌鲁木齐\",\"firstLetter\":\"W\"},{\"cityName\":\"五家渠\",\"firstLetter\":\"W\"},{\"cityName\":\"乌海\",\"firstLetter\":\"W\"},{\"cityName\":\"乌兰察布\",\"firstLetter\":\"W\"},{\"cityName\":\"邢台\",\"firstLetter\":\"X\"},{\"cityName\":\"忻州\",\"firstLetter\":\"X\"},{\"cityName\":\"徐州\",\"firstLetter\":\"X\"},{\"cityName\":\"宣城\",\"firstLetter\":\"X\"},{\"cityName\":\"新余\",\"firstLetter\":\"X\"},{\"cityName\":\"新乡\",\"firstLetter\":\"X\"},{\"cityName\":\"许昌\",\"firstLetter\":\"X\"},{\"cityName\":\"信阳\",\"firstLetter\":\"X\"},{\"cityName\":\"襄阳\",\"firstLetter\":\"X\"},{\"cityName\":\"孝感\",\"firstLetter\":\"X\"},{\"cityName\":\"咸宁\",\"firstLetter\":\"X\"},{\"cityName\":\"仙桃\",\"firstLetter\":\"X\"},{\"cityName\":\"湘潭\",\"firstLetter\":\"X\"},{\"cityName\":\"湘西\",\"firstLetter\":\"X\"},{\"cityName\":\"西双版纳\",\"firstLetter\":\"X\"},{\"cityName\":\"西宁\",\"firstLetter\":\"X\"},{\"cityName\":\"西安\",\"firstLetter\":\"X\"},{\"cityName\":\"咸阳\",\"firstLetter\":\"X\"},{\"cityName\":\"锡林郭勒盟\",\"firstLetter\":\"X\"},{\"cityName\":\"兴安盟\",\"firstLetter\":\"X\"},{\"cityName\":\"新竹\",\"firstLetter\":\"X\"},{\"cityName\":\"香港\",\"firstLetter\":\"X\"},{\"cityName\":\"阳泉\",\"firstLetter\":\"Y\"},{\"cityName\":\"运城\",\"firstLetter\":\"Y\"},{\"cityName\":\"营口\",\"firstLetter\":\"Y\"},{\"cityName\":\"延边\",\"firstLetter\":\"Y\"},{\"cityName\":\"伊春\",\"firstLetter\":\"Y\"},{\"cityName\":\"扬州\",\"firstLetter\":\"Y\"},{\"cityName\":\"盐城\",\"firstLetter\":\"Y\"},{\"cityName\":\"鹰潭\",\"firstLetter\":\"Y\"},{\"cityName\":\"宜春\",\"firstLetter\":\"Y\"},{\"cityName\":\"烟台\",\"firstLetter\":\"Y\"},{\"cityName\":\"宜昌\",\"firstLetter\":\"Y\"},{\"cityName\":\"岳阳\",\"firstLetter\":\"Y\"},{\"cityName\":\"益阳\",\"firstLetter\":\"Y\"},{\"cityName\":\"永州\",\"firstLetter\":\"Y\"},{\"cityName\":\"阳江\",\"firstLetter\":\"Y\"},{\"cityName\":\"云浮\",\"firstLetter\":\"Y\"},{\"cityName\":\"宜宾\",\"firstLetter\":\"Y\"},{\"cityName\":\"雅安\",\"firstLetter\":\"Y\"},{\"cityName\":\"玉溪\",\"firstLetter\":\"Y\"},{\"cityName\":\"玉树\",\"firstLetter\":\"Y\"},{\"cityName\":\"延安\",\"firstLetter\":\"Y\"},{\"cityName\":\"榆林\",\"firstLetter\":\"Y\"},{\"cityName\":\"玉林\",\"firstLetter\":\"Y\"},{\"cityName\":\"银川\",\"firstLetter\":\"Y\"},{\"cityName\":\"伊犁\",\"firstLetter\":\"Y\"},{\"cityName\":\"张家口\",\"firstLetter\":\"Z\"},{\"cityName\":\"镇江\",\"firstLetter\":\"Z\"},{\"cityName\":\"舟山\",\"firstLetter\":\"Z\"},{\"cityName\":\"漳州\",\"firstLetter\":\"Z\"},{\"cityName\":\"淄博\",\"firstLetter\":\"Z\"},{\"cityName\":\"枣庄\",\"firstLetter\":\"Z\"},{\"cityName\":\"郑州\",\"firstLetter\":\"Z\"},{\"cityName\":\"周口\",\"firstLetter\":\"Z\"},{\"cityName\":\"驻马店\",\"firstLetter\":\"Z\"},{\"cityName\":\"株洲\",\"firstLetter\":\"Z\"},{\"cityName\":\"张家界\",\"firstLetter\":\"Z\"},{\"cityName\":\"珠海\",\"firstLetter\":\"Z\"},{\"cityName\":\"肇庆\",\"firstLetter\":\"Z\"},{\"cityName\":\"湛江\",\"firstLetter\":\"Z\"},{\"cityName\":\"中山\",\"firstLetter\":\"Z\"},{\"cityName\":\"张掖\",\"firstLetter\":\"Z\"},{\"cityName\":\"自贡\",\"firstLetter\":\"Z\"},{\"cityName\":\"资阳\",\"firstLetter\":\"Z\"},{\"cityName\":\"遵义\",\"firstLetter\":\"Z\"},{\"cityName\":\"昭通\",\"firstLetter\":\"Z\"},{\"cityName\":\"中卫\",\"firstLetter\":\"Z\"}]";

    private List<String> list = new ArrayList<String>();
    private List<String> listTag = new ArrayList<String>();

    public void getData() {
        String city_name_list[] = CityActivity.this.getResources()
                .getStringArray(R.array.city_description_list);
        String city_list_tag[] = CityActivity.this.getResources()
                .getStringArray(R.array.city_group_list);
        // ///////////////////////////
        String cityTag[] = {"热门", "A", "B", "C", "D", "E", "F", "G", "H", "J",
                "K", "L", "M", "N", "Q", "S", "T", "W", "X", "Y", "Z"};
        int listsize[] = {0, 19, 5, 6, 9, 7, 1, 3, 6, 13, 13, 5, 8, 5, 7, 7,
                10, 6, 11, 7, 11, 9};

        for (int j = 1; j < listsize.length; j++) {
            list.add(cityTag[j - 1]);
            listTag.add(cityTag[j - 1]);
            listsize[j] = listsize[j - 1] + listsize[j];
            for (int i = listsize[j - 1]; i < listsize[j]; i++) {
                list.add(city_name_list[i]);
                // System.out.println(city_list_tag[i]);
                //city_group.add(city_list_tag[i]);
            }
        }
    }

    public class CityListAdapter extends ArrayAdapter<String> {

        private List<String> listTag = null;

        public CityListAdapter(Context context, List<String> objects, List<String> tags) {
            super(context, 0, objects);
            this.listTag = tags;
        }

        @Override
        public boolean isEnabled(int position) {
            if (listTag.contains(getItem(position))) {
                return false;
            }
            return super.isEnabled(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (listTag.contains(getItem(position))) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.city_list_item_tag, null);
            } else {
                view = LayoutInflater.from(getContext()).inflate(R.layout.city_list_item, null);
            }
            TextView textView = (TextView) view.findViewById(R.id.group_list_item_text);
            textView.setText(getItem(position));
            return view;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_list_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.city_activity_city_select);
        Intent intent = getIntent();
        String currentCity = intent.getStringExtra(MainActivity.CURRENT_CITY);

//        Gson gson = new Gson();
//        Type listType = new TypeToken<ArrayList<City>>() {
//        }.getType();
//        ArrayList<City> cities = gson.fromJson(cityDataList, listType);
//        Collections.sort(cities, new Comparator<City>() {
//            @Override
//            public int compare(City city, City t1) {
//                return city.getFirstLetter().compareTo(t1.getFirstLetter());
//            }
//        });
//        ArrayList<String> cityNames = new ArrayList<>();
//        for (City city : cities) {
//            cityNames.add(city.getCityName());
//        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.select_dialog_singlechoice,
//                cityNames);
//
//        ListView listView = findViewById(R.id.listview_city);
//        listView.setAdapter(adapter);
        getData();
        CityListAdapter adapter = new CityListAdapter(this, list, listTag);
        ListView listView = findViewById(R.id.group_list);
        listView.setAdapter(adapter);
    }
}
