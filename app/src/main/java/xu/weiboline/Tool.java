package xu.weiboline;

import android.content.Context;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;


/**
 * Created by lovexujh on 2017/7/4
 */

public class Tool {

    public static float getTextViewLength(TextView textView) {
        TextPaint paint = textView.getPaint();
        return paint.measureText(textView.getText().toString());
    }

    public static float getTextViewLength(TextView textView, int textSize) {
        TextPaint paint = textView.getPaint();
        paint.setTextSize(textSize);
        return paint.measureText(textView.getText().toString());
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
}
