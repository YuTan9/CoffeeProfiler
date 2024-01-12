package edu.sjsu.android.coffeeprofiler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

public class CustomSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {

    private Paint textPaint;

    public CustomSeekBar(Context context) {
        super(context);
        init();
    }

    public CustomSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(36f);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the current value above the thumb
        String progressText = String.valueOf(getProgress());
        float thumbX = (float) getThumb().getBounds().left + (getThumbOffset() + getThumb().getBounds().width() + textPaint.getTextSize())/2f;
        float thumbY = (float) getThumb().getBounds().top - textPaint.getTextSize() + 10;


        canvas.drawText(progressText, thumbX, thumbY, textPaint);
    }
}