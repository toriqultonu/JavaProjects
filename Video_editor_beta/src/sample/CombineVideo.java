package sample;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CombineVideo {
    static void createOutput(String[] media_files) {
        StringBuffer sb;
        StringBuffer temp = new StringBuffer(media_files[0]);
        temp.delete(42,temp.length());
        temp.delete(0,6);
        StringBuffer sb2 = new StringBuffer();
        for (int i = 0; i < media_files.length; i++) {
            sb = new StringBuffer(media_files[i]);
            sb.delete(0,6);
            sb2.append("file " +"'"+ sb.toString() +"'" + "\n");
        }
        try{
            FileWriter myWriter = new FileWriter(temp+"/combine.txt");
            myWriter.write(sb2.toString());
            myWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        StringBuffer s = new StringBuffer("cmd /c start  cmd.exe /k ");
        s.append("\"cd " + temp + " && ffmpeg -f concat -safe 0 -i combine.txt -c copy Result/video.mp4\"");

        try
        {
            Process p = Runtime.getRuntime().exec(s.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
