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
//    private MyOnPageChangeListener onPageChangeListener;
    private DynamicLine dynamicLine;
    private ViewPagerTitle viewPagerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {


        views = new ArrayList<>();

        viewPagerTitle = (ViewPagerTitle)findViewById(R.id.pager_title);

//        dynamicLine = (DynamicLine)findViewById(R.id.line);

        pager = (ViewPager) findViewById(R.id.view_pager);
        viewPagerTitle.initData(new String[]{"layout1", "layout2", "layout3"}, pager, 0);

//        onPageChangeListener = new MyOnPageChangeListener(pager, dynamicLine);


        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.layout1, null);
        view2 = inflater.inflate(R.layout.layout2, null);
        view3 = inflater.inflate(R.layout.layout3, null);

        views = new ArrayList<>();
        views.add(view1);
        views.add(view2);
        views.add(view3);

//        pager.addOnPageChangeListener(onPageChangeListener);

        adapter = new MyPagerAdapter(views);
        pager.setAdapter(adapter);
    }




    @Override
    protected void onDestroy() {
//        pager.removeOnPageChangeListener(onPageChangeListener);
        super.onDestroy();
    }
}
