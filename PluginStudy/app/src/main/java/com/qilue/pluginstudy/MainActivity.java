package com.qilue.pluginstudy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.qilue.pluginstudy.aidl.AdditionServiceConnection;
import com.qilue.pluginstudy.proxy.ProxyTest;

public class MainActivity extends Activity {
    private AdditionServiceConnection mAIDLConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindAIDLService();
    }


    private void bindAIDLService() {
        mAIDLConnection = new AdditionServiceConnection();
        Intent intent = new Intent("com.qilue.pluginstudy.aidl.AIDL_SERVICE");
        intent.setPackage(getPackageName());
        bindService(intent, mAIDLConnection, Context.BIND_AUTO_CREATE);

    }

    public void onDynamicProxyClick(View view) {
        ProxyTest.testProxy();
    }

    public void onHookInstrumentationClick(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }

    public void onAIDLClick(View view) {
        int a = mAIDLConnection.add(5, 3);
        Toast.makeText(this, "result: " + a, Toast.LENGTH_SHORT).show();
    }
}
