package xu.weiboline;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import static xu.weiboline.Tool.getScreenWidth;


public class DynamicLine extends View {
    private int lineHeight;
    private int shaderColorEnd;
    private int shaderColorStart;
    private float startX, stopX;
    private Paint paint;
    private RectF rectF = new RectF(startX, 0, stopX, 0);


    public DynamicLine(Context context) {
        this(context, null);
    }

    public DynamicLine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerTitle);

        shaderColorStart = array.getColor(R.styleable.ViewPagerTitle_line_start_color, Color.parseColor("#ffc125"));
        shaderColorEnd = array.getColor(R.styleable.ViewPagerTitle_line_end_color, Color.parseColor("#ff4500"));
        lineHeight = array.getInteger(R.styleable.ViewPagerTitle_line_height, 20);
        array.recycle();
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
        paint.setShader(new LinearGradient(0, 100, getScreenWidth(getContext()), 100, shaderColorStart, shaderColorEnd, Shader.TileMode.MIRROR));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(lineHeight, MeasureSpec.getMode(heightMeasureSpec));
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

}
