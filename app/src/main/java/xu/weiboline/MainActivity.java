package xu.weiboline;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private MyPagerAdapter adapter;
    private ArrayList<View> views;
    private View view1;
    private View view2;
    private View view3;
    private ViewPagerTitle viewPagerTitle;
    private View view4;
    private View view5;
    private View view6;
    private View view7;
    private View view8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {


        views = new ArrayList<>();

        viewPagerTitle = (ViewPagerTitle)findViewById(R.id.pager_title);
        pager = (ViewPager) findViewById(R.id.view_pager);
        viewPagerTitle.initData(new String[]{"关注", "推荐", "视频", "直播", "图片", "段子", "精华", "热门"}, pager, 0);


        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.layout1, null);
        view2 = inflater.inflate(R.layout.layout2, null);
        view3 = inflater.inflate(R.layout.layout3, null);
        view4 = inflater.inflate(R.layout.layout4, null);
        view5 = inflater.inflate(R.layout.layout5, null);
        view6 = inflater.inflate(R.layout.layout6, null);
        view7 = inflater.inflate(R.layout.layout7, null);
        view8 = inflater.inflate(R.layout.layout8, null);

        views = new ArrayList<>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        views.add(view5);
        views.add(view6);
        views.add(view7);
        views.add(view8);

        adapter = new MyPagerAdapter(views);
        pager.setAdapter(adapter);
    }


}
