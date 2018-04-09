package cn.zcoder.xxp.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;

import com.upholstery.share.base.R;


/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/3/4
 * Description :
 */

public class TimerButton extends android.support.v7.widget.AppCompatButton {
    private CountDownTimer mCountDownTimer;
    //默认的点击后的文字
    private final String DEFAULT_CLICKED_TEXT_FORMAT = "%s秒";
    //默认计时总秒数
    private final int DEFAULT_SECOND_IN_FUTURE = 60;
    private long mSecondInFuture;
    private String mClickedTextFormat;
    //正常状态下的text
    private String mNormalText;

    public TimerButton(Context context) {
        this(context, null);
    }

    public TimerButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.CENTER);
        mNormalText = (String) getText();
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TimerButton);
        mClickedTextFormat = typedArray.getString(R.styleable.TimerButton_clickedTextFormat);
        if (TextUtils.isEmpty(mClickedTextFormat)) {
            mClickedTextFormat = DEFAULT_CLICKED_TEXT_FORMAT;
        }
        mSecondInFuture = typedArray.getInt(R.styleable.TimerButton_secondInFuture, DEFAULT_SECOND_IN_FUTURE);

        mCountDownTimer = new CountDownTimer(mSecondInFuture * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                setText(String.format(mClickedTextFormat, millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                setText(mNormalText);
                setClickable(true);
            }
        };
    }


    @Override
    public boolean performClick() {

        return super.performClick();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }


    public void start() {
        mCountDownTimer.start();
        setClickable(false);
    }

    public void stop(){
        mCountDownTimer.cancel();
        setText(mNormalText);
        setClickable(true);
    }
}
