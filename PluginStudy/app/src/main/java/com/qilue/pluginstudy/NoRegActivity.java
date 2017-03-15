package com.qilue.pluginstudy;

import android.app.Activity;
import android.os.Bundle;

/**
 * 未在 Manifest 中注册 Activity
 */
public class NoRegActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_reg);
    }
}
