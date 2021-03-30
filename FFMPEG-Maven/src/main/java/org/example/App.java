package org.example;

import net.bramp.ffmpeg.FFmpeg;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main(String[] args) throws IOException {

        Runtime.getRuntime().exec("ffmpeg -i img-%02d.jpg video_name.avi");
        
        System.out.println( "Hello World!" );
    }

}
