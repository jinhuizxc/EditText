package com.example.jh.edittext.scrollview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jh.edittext.R;

/**
 * 1、设置edittext不可编辑与可以编辑，
 * 2、软键盘的拉起与隐藏
 * <p>
 * 动态设置windowSoftInputMode
 * https://blog.csdn.net/figo0423/article/details/51376681
 */
public class EditTextActivity extends Activity implements View.OnTouchListener, View.OnClickListener {

    private EditText mEditText;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittext);

        mEditText = (EditText) findViewById(R.id.edit_text);
        mEditText.setOnTouchListener(this);
        mEditText.setOnClickListener(this);
        mEditText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mEditText.getText().toString().length() > 100){
                    Toast.makeText(EditTextActivity.this, "setOnLongClickListener", Toast.LENGTH_SHORT).show();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    hideSoftKeyboard(mEditText, EditTextActivity.this);
                    return true;
                }else {
                    hideSoftKeyboard(mEditText, EditTextActivity.this);
                    return false;
                }

            }
        });


        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 100) {
                    Toast.makeText(EditTextActivity.this, "字数达到100", Toast.LENGTH_SHORT).show();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    hideSoftKeyboard(mEditText, EditTextActivity.this);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * https://blog.csdn.net/z191726501/article/details/50701165
     * @param view
     * @param motionEvent
     * @return
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
        if ((view.getId() == R.id.edit_text && canVerticalScroll(mEditText))) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                mEditText.setFocusable(true);
                view.getParent().requestDisallowInterceptTouchEvent(true);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                hideSoftKeyboard(mEditText, EditTextActivity.this);
            }else if (motionEvent.getAction() == MotionEvent.ACTION_SCROLL) {
                hideSoftKeyboard(mEditText, EditTextActivity.this);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE){
                hideSoftKeyboard(mEditText, this);
                view.getParent().requestDisallowInterceptTouchEvent(true);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                mEditText.setFocusable(false);
                hideSoftKeyboard(mEditText, EditTextActivity.this);
                view.getParent().requestDisallowInterceptTouchEvent(true);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }
        }else {
            hideSoftKeyboard(mEditText, EditTextActivity.this);
            view.getParent().requestDisallowInterceptTouchEvent(true);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
        return false;
    }

    /**
     * EditText竖直方向是否可以滚动
     *
     * @param editText 需要判断的EditText
     * @return true：可以滚动  false：不可以滚动
     */
    private boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() - editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if (scrollDifference == 0) {
            return false;
        }

        if ((scrollY > 0) || (scrollY < scrollDifference - 1)){
            hideSoftKeyboard(mEditText, this);
            return  true;
        }else {
            hideSoftKeyboard(mEditText, this);
            return false;
        }
//        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }


    public static void hideSoftKeyboard(EditText editText, Context context) {
        if (editText != null && context != null) {
            InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    public static void showSoftKeyboard(EditText editText, Context context) {
        if (editText != null && context != null) {
            InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, 0);

        }
    }


    @Override
    public void onClick(View v) {
        if (mEditText.getText().toString().length() > 100) {
            hideSoftKeyboard(mEditText, this);
        }else {
            hideSoftKeyboard(mEditText, this);
        }

    }


}

