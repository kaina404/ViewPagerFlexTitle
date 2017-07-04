package xu.weiboline;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static xu.weiboline.Tool.getScreenWidth;
import static xu.weiboline.Tool.getTextViewLength;

/**
 * Created by lovexujh on 2017/7/3
 */

public class ViewPagerTitle extends HorizontalScrollView {

    private String[] titles;
    private ArrayList<TextView> textViews = new ArrayList<>();
    private OnTextViewClick onTextViewClick;
    private DynamicLine dynamicLine;
    private ViewPager viewPager;
    private MyOnPageChangeListener onPageChangeListener;
    private int margin;
    private LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private int length;
    private int defaultIndex;
    private float defaultTextSize = 18;
    private float selectedTextSize = 25;
    private int defaultTextColor = Color.GRAY;
    private int selectedTextColor = Color.BLACK;


    public ViewPagerTitle(Context context) {
        this(context, null);
    }

    public ViewPagerTitle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }


    public void initData(String[] titles, ViewPager viewPager, int defaultIndex) {
        this.titles = titles;
        this.viewPager = viewPager;
        createDynamicLine();
        createTextViews(titles);
        setDefaultIndex(defaultIndex);

        onPageChangeListener = new MyOnPageChangeListener(getContext(), viewPager, dynamicLine, this, length, margin);
        viewPager.addOnPageChangeListener(onPageChangeListener);

    }

    public ArrayList<TextView> getTextView() {
        return textViews;
    }


    private void createDynamicLine() {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dynamicLine = new DynamicLine(getContext());
        dynamicLine.setLayoutParams(params);
    }


    private void createTextViews(String[] titles) {
        LinearLayout contentLl = new LinearLayout(getContext());
        contentLl.setLayoutParams(contentParams);
        contentLl.setOrientation(LinearLayout.VERTICAL);
        addView(contentLl);


        LinearLayout textViewLl = new LinearLayout(getContext());
        textViewLl.setLayoutParams(contentParams);
        textViewLl.setOrientation(LinearLayout.HORIZONTAL);

        margin = getTextViewMargins(titles);

        textViewParams.setMargins(margin, 0, margin, 0);

        for (int i = 0; i < titles.length; i++) {
            TextView textView = new TextView(getContext());
            textView.setText(titles[i]);
            textView.setTextColor(Color.GRAY);
            textView.setTextSize(defaultTextSize);
            textView.setLayoutParams(textViewParams);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setOnClickListener(onClickListener);
            textView.setTag(i);
            textViews.add(textView);
            textViewLl.addView(textView);
            length = length + textViewParams.leftMargin + (int) getTextViewLength(textView) + textViewParams.rightMargin;
        }
        contentLl.addView(textViewLl);
        contentLl.addView(dynamicLine);
    }

    private int getTextViewMargins(String[] titles) {
        int defaultMargins = 30;
        float countLength = 0;
        TextView textView = new TextView(getContext());
        textView.setTextSize(defaultTextSize);
        TextPaint paint = textView.getPaint();


        for (int i = 0; i < titles.length; i++) {
            countLength = countLength + defaultMargins + paint.measureText(titles[i]) + defaultMargins;
        }
        int screenWidth = getScreenWidth(getContext());

        if (countLength <= screenWidth) {
            return (screenWidth / titles.length - (int) paint.measureText(titles[1])) / 2;
        } else {
            return defaultMargins;
        }
    }


    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < textViews.size(); i++) {
                if (i == (int) v.getTag()) {
                    ((TextView) v).setTextColor(selectedTextColor);
                    ((TextView) v).setTextSize(selectedTextSize);
                } else {
                    textViews.get(i).setTextColor(defaultTextColor);
                    textViews.get(i).setTextSize(defaultTextSize);
                }
            }
            viewPager.setCurrentItem((int) v.getTag());
            if (onTextViewClick != null) {
                onTextViewClick.textViewClick((TextView) v, (int) v.getTag());
            }

        }
    };

    public void setDefaultIndex(int index) {
        this.defaultIndex = index;
        setCurrentItem(index);
    }

    public void setCurrentItem(int index) {
        for (int i = 0; i < textViews.size(); i++) {
            if (i == index) {
                textViews.get(i).setTextColor(selectedTextColor);
                textViews.get(i).setTextSize(selectedTextSize);
            } else {
                textViews.get(i).setTextColor(defaultTextColor);
                textViews.get(i).setTextSize(defaultTextSize);
            }
        }
    }

    public interface OnTextViewClick {
        void textViewClick(TextView textView, int index);
    }

    public void setOnTextViewClickListener(OnTextViewClick onTextViewClick) {
        this.onTextViewClick = onTextViewClick;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        viewPager.removeOnPageChangeListener(onPageChangeListener);
    }


}
