package com.example.jh.edittext;


import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * EditText 内容监听变化
 * <p>
 * 方法一：
 * 在 xml 文件中设置文本编辑框属性作字符数限制
 * 如：android:maxLength="10" 即限制最大输入字符个数为10
 * <p>
 * 2.
 * 3.
 *
 * 关于多个EditText的光标问题
 */
public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    EditText edit1;
    EditText edit2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit1 = (EditText) findViewById(R.id.edit1);
        edit2 = (EditText) findViewById(R.id.edit2);
        /**
         * EditText编辑框内容发生变化时的监听回调
         */
        edit1.addTextChangedListener(new MyTextChangedListener());
        /**
         *  1、EditText输入框的动态监听方法
         *  A:监听 输入结束点击键盘确认键执行的 方法
         */
        //这个方法没什么用啊？
//        edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                Log.e("输入完点击确认执行该方法", "输入结束");
//                return false;
//            }
//        });

    }

    /**
     * 文本监听的方法实现
     */
    public class MyTextChangedListener implements TextWatcher {

        /**
         * 编辑框的内容发生改变之前的回调方法
         */
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Log.e(TAG, "beforeTextChanged =" + s.toString());
        }

        /**
         * 编辑框的内容正在发生改变时的回调方法 >>用户正在输入
         * 我们可以在这里实时地 通过搜索匹配用户的输入
         */
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.e(TAG, "onTextChanged =" + s.toString());
            Log.e(TAG, "s.length() =" + s.length());
            if (s.length() >= 10){
                Toast.makeText(MainActivity.this, "输入字符过长，无法再输入", Toast.LENGTH_SHORT).show();
            }


    }

    /**
     * 编辑框的内容改变以后,用户没有继续输入时 的回调方法
     */
    @Override
    public void afterTextChanged(Editable s) {
        Log.e(TAG, "afterTextChanged =" + s.toString());
    }
}
}
