package sample;

import io.github.ritikchanna.FFMPEG;

import java.io.IOException;
import java.io.InputStream;

public class RunScript {
    public static void main(String[] args) {

        try{
            String path="cmd /c start c:\\test.bat";
            Runtime rn=Runtime.getRuntime();
            Process pr=rn.exec(path);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
