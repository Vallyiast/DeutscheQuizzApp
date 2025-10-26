package com.example.deutschquiz;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LinesView extends View {

    private final Paint paint = new Paint();
    private final List<Pair<PointF, PointF>> lines = new ArrayList<>();

    public LinesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(8f);
        paint.setAntiAlias(true);
    }

    public void addLine(PointF start, PointF end) {
        lines.add(new Pair<>(start, end));
        invalidate();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        for (Pair<PointF, PointF> line : lines) {
            canvas.drawLine(line.first.x, line.first.y, line.second.x, line.second.y, paint);
        }
    }
}
