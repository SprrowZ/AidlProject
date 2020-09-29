package com.rye.catcher.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.rye.catcher.aidldemo.R;

/**
 * Create by rye
 * at 2020-09-25
 *
 * @description:
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvAidl;
    private TextView tvContentProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initEvent();
    }

    private void initEvent() {
        tvAidl = findViewById(R.id.tv_aidl);
        tvContentProvider = findViewById(R.id.tv_content_provider);

        tvAidl.setOnClickListener(this);
        tvContentProvider.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_aidl:
                AIDLClientActivity.start(this);
                break;

            case R.id.tv_content_provider:
                ContentProviderActivity.start(this);
                break;
        }
    }
}
