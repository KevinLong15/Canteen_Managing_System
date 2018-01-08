package com.ideabobo.gap;

import android.content.Context;
import android.os.Bundle;

import org.apache.cordova.*;

public class MainActivity extends CordovaActivity 
{
	public Context ctx = null;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.init();
        ctx = this.getApplicationContext();;
        super.loadUrl("file:///android_asset/clientuser/index.html");
    }
    
}

