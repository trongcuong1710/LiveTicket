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
        return App.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**
     * get string from shared preference
     * @param preferencesName : share preference to get
     * @param name : key name
     * @return
     */
    public static String getString(String preferencesName, String name)
    {
        SharedPreferences preferences = getPreference(preferencesName);
        return preferences.getString(name, "");
    }

    /**
     * set shared preference string value
     * @param preferenceName : shared preferences name
     * @param name : key name
     * @param value : value
     */
    public static void setString(String preferenceName, String name, String value)
    {
        SharedPreferences preferences = getPreference(preferenceName);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(name, value);
        editor.apply();
    }

    /**
     * clear shared preference
     * @param name
     */
    public static void Clear(String name)
    {
        SharedPreferences preferences = getPreference(name);

        if (preferences == null)
            return;

        preferences.edit().clear().commit();
        deleteFile("/data/data/" + App.getContext().getPackageName() +  "/shared_prefs/"  + name + ".xml");
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

    /**
     * delete file
     * @param pathToFile
     * @throws IOException
     */
    private static void deleteFile(String pathToFile) {
        File file = new File(pathToFile);
        if (file.delete() == false) {
            return;
        }

        file.delete();
    }
}
