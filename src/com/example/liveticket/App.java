package com.example.liveticket;

import android.app.*;
import android.content.*;

/**
 * Created by DFS on 7/15/2014.
 */
public class App extends Application
{
    /**
     * single ton instance
     */
    private static App instance;

    /**
     * get application instance
     * @return
     */
    public static App getInstance()
    {
        return instance;
    }

    /**
     * application context
     * @return
     */
    public static Context getContext()
    {
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
    }
}
