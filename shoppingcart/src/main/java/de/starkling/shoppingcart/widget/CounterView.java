package de.starkling.shoppingcart.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.ContextCompat;

import de.starkling.shoppingcart.R;


public class CounterView extends LinearLayout  implements View.OnClickListener{

    private AppCompatImageButton mBtnDecrease;
    private AppCompatImageButton mBtnIncrease;
    private TextView mTextCounter;
    private int mCounterValue = 0 ;
    private boolean minFixToOne = false;
    private CounterValueChangeListener mCounterValueChangeListener;

    private static final long MIN_CLICK_INTERVAL = 0;
    private long mLastClickTime;
    private Context mContext;

    public CounterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_counter, this, true);

        mBtnDecrease = (AppCompatImageButton) getChildAt(0);
        mTextCounter = (TextView) getChildAt(1);
        mBtnIncrease = (AppCompatImageButton) getChildAt(2);

        setClickListener();
    }


    @Override
    public void onClick(View v) {

        long currentClickTime= SystemClock.uptimeMillis();
        long elapsedTime=currentClickTime-mLastClickTime;

         mLastClickTime = currentClickTime;

        if(elapsedTime <= MIN_CLICK_INTERVAL)
            return;

        int i = v.getId();
        if (i == R.id.btn_decrease) {

            if (mCounterValue > 0) {

                if(minFixToOne && mCounterValue == 1 ){
                    return;
                }

                mCounterValue--;
                mTextCounter.setText(String.valueOf(mCounterValue));
                if (mCounterValueChangeListener != null) {
                    mCounterValueChangeListener.onValueDelete(mCounterValue);
                }
            }


        } else if (i == R.id.btn_increase) {
            mCounterValue++;
            mBtnDecrease.setEnabled(true);
            mTextCounter.setText(String.valueOf(mCounterValue));
            if (mCounterValueChangeListener != null) {
                mCounterValueChangeListener.onValueAdd(mCounterValue);
            }

        }
        checkFabColor();
    }

    private void checkFabColor(){
        if(mCounterValue > 0){
            mBtnDecrease.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(mContext ,android.R.color.black)));
        }else{
            mBtnDecrease.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(mContext,android.R.color.darker_gray)));
        }
    }
    private void setClickListener(){
        mBtnDecrease.setOnClickListener(this);
        mBtnIncrease.setOnClickListener(this);
    }

    /**
     * get the counter value
     * @return current value of counter
     */
    public int getCounterValue(){
        return mCounterValue;
    }

    /**
     * set the current value of counter
     * @param mCounterValue
     */
    public void setCounterValue(int mCounterValue){
        this.mCounterValue = mCounterValue;
        mTextCounter.setText(String.valueOf(this.mCounterValue));
        checkFabColor();
    }

    public void minFixToOne(boolean enable){
        minFixToOne = enable;
    }

    public void addCounterValueChangeListener(@NonNull CounterValueChangeListener listener){
         mCounterValueChangeListener =  listener;
    }

    public interface CounterValueChangeListener{
        void onValueAdd(int count);
        void onValueDelete(int count);
    }
}
