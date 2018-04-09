package com.upholstery.share.battery.mvp.ui.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IMeasurablePagerTitleView;

public class ClipPagerTitleView extends View implements IMeasurablePagerTitleView {
    private String mText;
    private int mTextColor;
    private int mClipColor;
    private boolean mLeftToRight;
    private float mClipPercent;
    private Paint mPaint;
    private Rect mTextBounds = new Rect();

    public ClipPagerTitleView(Context context) {
        super(context);
        this.init(context);
    }

    private void init(Context context) {
        int textSize = UIUtil.dip2px(context, 16.0D);
        this.mPaint = new Paint(1);
        this.mPaint.setTextSize((float)textSize);
        int padding = UIUtil.dip2px(context, 30.0D);
        this.setPadding(padding, 0, padding, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.measureTextBounds();
        this.setMeasuredDimension(this.measureWidth(widthMeasureSpec), this.measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int result = size;
        switch(mode) {
        case -2147483648:
            int width = this.mTextBounds.width() + this.getPaddingLeft() + this.getPaddingRight();
            result = Math.min(width, size);
            break;
        case 0:
            result = this.mTextBounds.width() + this.getPaddingLeft() + this.getPaddingRight();
        }

        return result;
    }

    private int measureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int result = size;
        switch(mode) {
        case -2147483648:
            int height = this.mTextBounds.height() + this.getPaddingTop() + this.getPaddingBottom();
            result = Math.min(height, size);
            break;
        case 0:
            result = this.mTextBounds.height() + this.getPaddingTop() + this.getPaddingBottom();
        }

        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int x = (this.getWidth() - this.mTextBounds.width()) / 2;
        Paint.FontMetrics fontMetrics = this.mPaint.getFontMetrics();
        int y = (int)(((float)this.getHeight() - fontMetrics.bottom - fontMetrics.top) / 2.0F);
        this.mPaint.setColor(this.mTextColor);
        canvas.drawText(this.mText, (float)x, (float)y, this.mPaint);
        canvas.save();
        if(this.mLeftToRight) {
            canvas.clipRect(0.0F, 0.0F, (float)this.getWidth() * this.mClipPercent, (float)this.getHeight());
        } else {
            canvas.clipRect((float)this.getWidth() * (1.0F - this.mClipPercent), 0.0F, (float)this.getWidth(), (float)this.getHeight());
        }

        this.mPaint.setColor(this.mClipColor);
        canvas.drawText(this.mText, (float)x, (float)y, this.mPaint);
        canvas.restore();
    }

    @Override
    public void onSelected(int index, int totalCount) {
    }

    @Override
    public void onDeselected(int index, int totalCount) {
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        this.mLeftToRight = !leftToRight;
        this.mClipPercent = 1.0F - leavePercent;
        this.invalidate();
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        this.mLeftToRight = leftToRight;
        this.mClipPercent = enterPercent;
        this.invalidate();
    }

    private void measureTextBounds() {
        this.mPaint.getTextBounds(this.mText, 0, this.mText == null?0:this.mText.length(), this.mTextBounds);
    }

    public String getText() {
        return this.mText;
    }

    public void setText(String text) {
        this.mText = text;
        this.requestLayout();
    }

    public float getTextSize() {
        return this.mPaint.getTextSize();
    }

    public void setTextSize(float textSize) {
        this.mPaint.setTextSize(textSize);
        this.requestLayout();
    }

    public int getTextColor() {
        return this.mTextColor;
    }

    public void setTextColor(int textColor) {
        this.mTextColor = textColor;
        this.invalidate();
    }

    public int getClipColor() {
        return this.mClipColor;
    }

    public void setClipColor(int clipColor) {
        this.mClipColor = clipColor;
        this.invalidate();
    }

    @Override
    public int getContentLeft() {
        int contentWidth = this.mTextBounds.width();
        return this.getLeft() + this.getWidth() / 2 - contentWidth / 2;
    }

    @Override
    public int getContentTop() {
        Paint.FontMetrics metrics = this.mPaint.getFontMetrics();
        float contentHeight = metrics.bottom - metrics.top;
        return (int)((float)(this.getHeight() / 2) - contentHeight / 2.0F);
    }

    @Override
    public int getContentRight() {
        int contentWidth = this.mTextBounds.width();
        return this.getLeft() + this.getWidth() / 2 + contentWidth / 2;
    }

    @Override
    public int getContentBottom() {
        Paint.FontMetrics metrics = this.mPaint.getFontMetrics();
        float contentHeight = metrics.bottom - metrics.top;
        return (int)((float)(this.getHeight() / 2) + contentHeight / 2.0F);
    }
}
