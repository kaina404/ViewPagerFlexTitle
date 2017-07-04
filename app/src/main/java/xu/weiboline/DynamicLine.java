package xu.weiboline;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by lovexujh on 2017/6/30
 */

public class DynamicLine extends View {
    private static final String TAG = "test_tag_2";
    private float startX, stopX;
    private Paint paint;
    private Scroller scroller;
    private boolean defaultScrooll;
    private int viewStopX;
    private int lastLineWidth;
    private int location3;
    private int location2;
    private int location1;
    private RectF rectF = new RectF(startX, 0, stopX, 0);


    public DynamicLine(Context context) {
        this(context, null);
    }

    public DynamicLine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
//        paint.setColor(getResources().getColor(R.color.colorAccent));
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
        paint.setShader(new LinearGradient(0, 100, getScreenWidth((Activity) getContext()), 100, Color.parseColor("#ffc125"), Color.parseColor("#ff4500"), Shader.TileMode.MIRROR));

        scroller = new Scroller(getContext());
    }

    public int getScreenWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(20, MeasureSpec.getMode(heightMeasureSpec));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        rectF.set(startX, 0, stopX, 10);
        canvas.drawRoundRect(rectF, 5, 5, paint);
    }


    public void updateView(float startX, float stopX) {
        this.startX = startX;
        this.stopX = stopX;
        invalidate();
    }


    //int startX, int startY, int dx, int dy, int duration
    public void setLocation(int location1, int location2, int location3, boolean defaultScrooll) {
        sportL = 0;
        this.defaultScrooll = defaultScrooll;
        this.location1 = location1;
        this.location2 = location2;
        this.location3 = location3;
        if (defaultScrooll) {
            stopX = location3;
            scroller.startScroll(location1, 0, location2 - location1, 0, 2000);

        } else {

            startX = location1;
            scroller.startScroll(location2, 0, location3 - location2, 0, 2000);

        }


       /* this.startX = startX;
        this.stopX = viewStopX;
        this.viewStopX = lastLineWidth;
        this.defaultScrooll = defaultScrooll;
        if (defaultScrooll) {
            scroller.startScroll(startX, 0, lastLineWidth, 0, 2000);

        } else {
            scroller.startScroll(startX + lastLineWidth, 0, lastLineWidth, 0, 2000);

        }*/
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    int sportL = 0;

    @Override
    public void computeScroll() {
        //先判断mScroller滚动是否完成
        if (scroller.computeScrollOffset()) {
            //这里调用View的scrollTo()完成实际的滚动
//            scrollTo(scroller.getCurrX(), scroller.getCurrY());
//            Log.d("datarts", startX + "===" + stopX);

            if (defaultScrooll) {
                startX = scroller.getCurrX();
            } else {
                sportL = scroller.getCurrX() - location2;
                stopX = location3 - sportL;

            }

            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
    }

    public static float getTextViewLength(TextView textView, String text) {
        TextPaint paint = textView.getPaint();
        // 得到使用该paint写上text的时候,像素为多少
        return paint.measureText(text);
    }

}
