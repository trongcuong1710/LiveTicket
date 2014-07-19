package util;

import java.io.File;

/**
 * Created by TY on 7/20/2014.
 */
public class FileUtil
{
    /**
     * delete file
     * @param pathToFile
     * @throws java.io.IOException
     */
    public static void deleteFile(String pathToFile)
    {
        File file = new File(pathToFile);
        if (file.delete() == false) {
            return;
        }

        file.delete();
    }
}
