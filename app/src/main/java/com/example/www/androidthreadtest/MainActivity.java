package com.example.www.androidthreadtest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private static final int UPDATE_TEXT = 1;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button changeBtn = (Button) findViewById(R.id.change_btn);
        textView = (TextView)findViewById(R.id.text_view);
        changeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * 子线程中不能直接进行UI操作，异常信息如下：
                         * android.view.ViewRootImpl$CalledFromWrongThreadException:
                         * Only the original thread that created a view hierarchy can touch its views.
                         */
                       // textView.setText("Nice to meet you!");


                        //发送消息给主线程
                        Message message = new Message();
                        message.what = UPDATE_TEXT;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
    }

    /**
     * 异步消息处理对象
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_TEXT :
                    textView.setText("Nice to meet you!");
                    break;
                default:
            }
        }
    };
}
