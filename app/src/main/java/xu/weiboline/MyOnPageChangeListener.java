package xu.weiboline;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import static android.support.v4.view.ViewPager.SCROLL_STATE_SETTLING;

/**
 * Created by lovexujh on 2017/6/30
 */

public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

    private static final String TAG = "test_tag";
    private ArrayList<TextView> textViews;
    private ViewPagerTitle viewPagerTitle;
    private DynamicLine dynamicLine;

    private ViewPager pager;
    private int pagerCount;
    private int screenWidth;
    private int lineWidth;
    private int everyLength;
    private int lastPosition;
    private int dis;


    public MyOnPageChangeListener(Context context, ViewPager viewPager, DynamicLine dynamicLine, ViewPagerTitle viewPagerTitle) {
        this.viewPagerTitle = viewPagerTitle;
        this.pager = viewPager;
        this.dynamicLine = dynamicLine;
        textViews = viewPagerTitle.getTextView();
        pagerCount = textViews.size();
        screenWidth = getScreenWidth((Activity) context);

        lineWidth = (int)getTextViewLength(textViews.get(0));

        everyLength = screenWidth / pagerCount;
        dis = (everyLength - lineWidth) / 2;

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if (lastPosition > position) {
            Log.d(TAG, "lastPosition < position positionOffset = " + positionOffset + "][position=" + position);
            dynamicLine.updateView((position + positionOffset) * everyLength + dis, (lastPosition + 1) * everyLength - dis);

        } else {
            Log.d(TAG, "lastPosition >= position positionOffset = " + positionOffset + "][position=" + position);
            if (positionOffset > 0.5f) {
                positionOffset = 0.5f;
            }
            dynamicLine.updateView(lastPosition * everyLength + dis, (position + positionOffset * 2) * everyLength + dis + lineWidth);

        }

    }


    @Override
    public void onPageSelected(int position) {
        Log.d(TAG, lastPosition + "[[[[");
        viewPagerTitle.setCurrentItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == SCROLL_STATE_SETTLING) {
            lastPosition = pager.getCurrentItem();
        }
        Log.d(TAG, "state = " + state);
    }

    public int getScreenWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public  float getTextViewLength(TextView textView) {
        TextPaint paint = textView.getPaint();
        // 得到使用该paint写上text的时候,像素为多少
        return paint.measureText(textView.getText().toString());
    }
}
