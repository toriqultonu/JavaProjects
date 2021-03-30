package sample;

import java.io.File;

public class ConvertVideoold {
    public static void covert(String path){
        StringBuffer temp = new StringBuffer(path);
        temp.delete(42,temp.length());
        temp.delete(0,6);
        File mediaFile = new File(path);


       //   ffmpeg -i 4.3gp -c:v libx264 -vf scale=1920:1080 -r 60 -c:a aac -ar 48000 -b:a 160k -strict experimental -f mpegts 4.ts
        String secondary = new String("ffmpeg -i "+mediaFile+" -c:v libx264 -vf scale=1920:1080 -r 60 -c:a aac -ar 48000 -b:a 160k -strict experimental -f mpegts "+mediaFile+".ts");

        StringBuffer command = new StringBuffer("cmd /c "+secondary);
        try
        {
            Process p = Runtime.getRuntime().exec(command.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }


}