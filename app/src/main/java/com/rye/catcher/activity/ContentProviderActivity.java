package com.rye.catcher.activity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rye.catcher.aidldemo.R;

public class ContentProviderActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String uri = "content://com.rye.catcher/ugc";
    private static final String COLUMN_NAME = "_name";

    private TextView tvQuery;
    private TextView tvInsert;

    private EditText tvEdit;

    private TextView tvResult;


    public static void start(Context context) {
        Intent intent = new Intent(context, ContentProviderActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        initEvent();
    }

    private void initEvent() {
        tvQuery = findViewById(R.id.tv_query);
        tvInsert = findViewById(R.id.tv_insert);
        tvEdit = findViewById(R.id.tv_edit);
        tvResult = findViewById(R.id.tv_result);
        tvQuery.setOnClickListener(this);
        tvInsert.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_query:
                query();
                break;
            case R.id.tv_insert:
                insert();
                break;
        }
    }


    private void query() {
        StringBuilder result = new StringBuilder();
        Cursor cursor = getContentResolver().query(Uri.parse(uri), null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            result.append(name)
                    .append("\n");
        }
        tvResult.setText(result);
    }

    private void insert() {
        String insertName = tvEdit.getText().toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, insertName);
        Uri result = getContentResolver().insert(Uri.parse(uri), contentValues);
        //通知数据发生改变,
        getContentResolver().notifyChange(Uri.parse(uri), null);
        long parseId = ContentUris.parseId(result);
        String ryeResult;
        if (parseId > 0) {
            ryeResult = "保存成功~";
        } else {
            ryeResult = "保存失败！";
        }
        Toast.makeText(this, ryeResult, Toast.LENGTH_SHORT).show();
    }

}