package com.wangxu.demo8;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by dell on 2018/1/8.
 */

public class QuickIndexView extends View{
    private final static String[] WORDS = {"当","热","A","B","C","D","E","F","G","H","I","J",
            "K","L","M","N","O","P","Q","R","S","T",
            "U","V","W","X","Y","Z","#"};
    private Paint paint;

    public QuickIndexView(Context context) {
        super(context,null);
    }

    public QuickIndexView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public QuickIndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, null, 0);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        //设置抗锯齿
        paint.setAntiAlias(true);
        //设置字体大小
        paint.setTextSize(DensityUtils.dip2px(getContext(),11));
        paint.setFakeBoldText(true);//设置文字粗体


    }
    private int cellWidth;
    private int cellHeight;
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        cellWidth = getMeasuredWidth();
        cellHeight = getMeasuredHeight()/WORDS.length;
    }
    //onDraw方法中我们主要处理的是如何得到每个字母显示的位置。
    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < WORDS.length; i++) {
            String word = WORDS[i];
            Rect bound = new Rect();
            paint.getTextBounds(word, 0, word.length(), bound);
            int x = (cellWidth - bound.width()) / 2;
            int y = i * cellHeight + (cellWidth + bound.width()) / 2;
            canvas.drawText(word, x, y, paint);
        }
    }


    private int curIndex = -1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                int y = (int) event.getY();
                int index = y / cellHeight;
                if(index>=0 && index<WORDS.length) {
                    if(index!=curIndex){
                        curIndex = index;
                        if(indexChangeListener!=null){
                            indexChangeListener.onIndexChange(WORDS[curIndex]);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int y1 = (int) event.getY();
                int index1 = y1 / cellHeight;
                if(index1>=0 && index1<WORDS.length) {
                    if (index1 != curIndex) {
                        curIndex = index1;
                        if(indexChangeListener!=null){
                            indexChangeListener.onIndexChange(WORDS[curIndex]);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                curIndex = -1;
                break;
        }
        return true;
    }

    private OnIndexChangeListener indexChangeListener;

    public void setOnIndexChangeListener(OnIndexChangeListener indexChangeListener) {
        this.indexChangeListener = indexChangeListener;
    }

    public interface OnIndexChangeListener{
        void onIndexChange(String words);
    }



}
