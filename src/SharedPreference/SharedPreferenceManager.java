package SharedPreference;

import android.content.*;
import com.example.liveticket.*;
import java.io.*;

/**
 * Created by DFS on 7/15/2014.
 */
public class SharedPreferenceManager
{
    /**
     * determine whether a shared preference existed
     * @param name
     */
    public static boolean isExist(String name)
    {
        File f = new File("/data/data/" + App.getContext().getPackageName() +  "/shared_prefs/"  + name + ".xml");

        return f.exists();
    }

    /**
     * get shared preference
     * @param name : preference name
     * @return
     */
    public static SharedPreferences getPreference(String name)
    {
        if (!isExist(name))
            return null;

        return App.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**
     * get string from shared preference
     * @param preferences : share preference to get
     * @param name : key name
     * @return
     */
    public static String getString(SharedPreferences preferences, String name)
    {
        return preferences.getString(name, "");
    }

    /**
     * set shared preference string value
     * @param preferences : shared preferences to set
     * @param name : key name
     * @param value : value
     */
    public static void setString(SharedPreferences preferences, String name, String value)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(name, value);
        editor.apply();
    }

    /**
     * clear shared preference
     * @param preferences
     */
    public static void Clear(SharedPreferences preferences)
    {
        if (preferences == null)
            return;

        preferences.edit().clear();
        preferences.edit().commit();
    }

    /**
     * create new shared preference
     * @param name : shared preference name
     * @return
     */
    public static SharedPreferences Create(String name)
    {
        return App.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
    }
}
