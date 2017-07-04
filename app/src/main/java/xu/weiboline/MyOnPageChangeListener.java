package xu.weiboline;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import static android.support.v4.view.ViewPager.SCROLL_STATE_SETTLING;
import static xu.weiboline.Tool.getScreenWidth;
import static xu.weiboline.Tool.getTextViewLength;

/**
 * Created by lovexujh on 2017/6/30
 */

public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

    private static final String TAG = "test_tag";
    private final Context context;
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
    private int[] location = new int[2];



    public MyOnPageChangeListener(Context context, ViewPager viewPager, DynamicLine dynamicLine, ViewPagerTitle viewPagerTitle, int allLength, int margin) {
        this.viewPagerTitle = viewPagerTitle;
        this.pager = viewPager;
        this.dynamicLine = dynamicLine;
        this.context = context;
        textViews = viewPagerTitle.getTextView();
        pagerCount = textViews.size();
//        screenWidth = getScreenWidth(context);
        screenWidth = getScreenWidth(context);

        lineWidth = (int) getTextViewLength(textViews.get(0));

        everyLength = allLength / pagerCount;
//        dis = Math.max((everyLength - lineWidth) / 2, 20);
        dis = margin;

    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if (lastPosition > position) {
            Log.d(TAG, "lastPosition < position positionOffset = " + positionOffset + "][position=" + position+"][ locationtm 0 = ");

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
            if (lastPosition + 1 < textViews.size()) {
                textViews.get(lastPosition + 1).getLocationOnScreen(location);
                if (location[0] > screenWidth) {
                    viewPagerTitle.smoothScrollBy(screenWidth / 2, 0);
                } else if (location[0] < 0) {
                    viewPagerTitle.smoothScrollBy(-screenWidth / 2, 0);
                }
            }

        }

    }

}
