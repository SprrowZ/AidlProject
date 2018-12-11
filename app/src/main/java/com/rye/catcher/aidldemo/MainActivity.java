package com.rye.catcher.aidldemo;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rye.catcher.activity.IDemoAIDL;
import com.rye.catcher.activity.IMyAidlInterface;
import com.rye.catcher.activity.PersonBean;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private EditText et_num1;
    private EditText et_num2;
    private TextView result;
    private Button sendRequest;
    //最初测试用
    private IDemoAIDL aidl;
    //传递实体类
    private IMyAidlInterface aidlEx;

//    private ServiceConnection conn=new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            //绑定的时候调用
//            //拿到远程的服务
//            aidl= IDemoAIDL.Stub.asInterface(service);
//            Log.i("zzz", "onServiceConnected: ");
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            //解绑的时候调用
//            aidl=null;
//        }
//    };
    //传递实体类
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            aidlEx=IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
           aidlEx=null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        bindService();
    }

    private void initView() {
        et_num1=findViewById(R.id.et_num1);
        et_num2=findViewById(R.id.et_num2);
        result=findViewById(R.id.result);
        sendRequest=findViewById(R.id.sendRequest);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //最初测试用
//                int num1= Integer.parseInt(et_num1.getText().toString());
//                int num2= Integer.parseInt(et_num2.getText().toString());
//                try {
//                    int resu=aidl.add(num1,num2);
//                    result.setText(String.valueOf(resu));
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                    result.setText("出错！");
//                }
                try {
                    ArrayList<PersonBean> result= (ArrayList<PersonBean>) aidlEx.add(new PersonBean("宇智波鼬",true));
                    Log.d("Persons", "onClick: "+result.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void bindService() {
        Intent intent=new Intent();
        intent.setAction("com.rye.catcher.project.services.IRemoteService");
        intent.setPackage("com.rye.catcher.zzg.anyOATest");
        //测试用
      //  bindService(intent,conn, Service.BIND_AUTO_CREATE);
        bindService(intent,connection,Service.BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //最初测试用
        //unbindService(conn);
        unbindService(connection);
    }
}
