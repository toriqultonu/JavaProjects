package sample;

import javafx.beans.Observable;
import javafx.beans.value.ObservableDoubleValue;
import javafx.scene.control.ProgressBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConvertVideo extends Controller{
    public static double progress;

    public ConvertVideo() throws FileNotFoundException {
    }

    public static void progress(String path) throws IOException {
        StringBuffer temp = new StringBuffer(path);
        temp.delete(0,6);
        File mediaFile = new File(path);
        //   ffmpeg -i 4.3gp -c:v libx264 -vf scale=1920:1080 -r 60 -c:a aac -ar 48000 -b:a 160k -strict experimental -f mpegts 4.ts
        ProcessBuilder pb = new ProcessBuilder("ffmpeg","-i",temp.toString(),"-c:v","libx264","-vf","scale=1920:1080" ,"-r","60","-c:a","aac","-ar","48000","-b:a","160k","-strict","experimental","-f","mpegts",temp.toString()+".ts");
        final Process p = pb.start();

        final double[] transfer = new double[1];


        new Thread() {
            public void run() {
                Scanner sc = new Scanner(p.getErrorStream());

                // Find duration
                Pattern durPattern = Pattern.compile("(?<=Duration: )[^,]*");
                String dur = sc.findWithinHorizon(durPattern, 0);
                if (dur == null)
                    throw new RuntimeException("Could not parse duration.");
                String[] hms = dur.split(":");
                double totalSecs = Integer.parseInt(hms[0]) * 3600
                        + Integer.parseInt(hms[1]) *   60
                        + Double.parseDouble(hms[2]);
                System.out.println("Total duration: " + totalSecs + " seconds.");

                // Find time as long as possible.
                Pattern timePattern = Pattern.compile("(?<=time=)[\\d:.]*");
                String[] matchSplit;
                String match;
                while (null != (match = sc.findWithinHorizon(timePattern, 0))) {
                    matchSplit = match.split(":");
                    progress = Integer.parseInt(matchSplit[0]) * 3600 +
                            Integer.parseInt(matchSplit[1]) * 60 +
                            Double.parseDouble(matchSplit[2]) / totalSecs;

                    System.out.println("this is " + progress);
                    System.out.printf("Progress: %.2f%%%n", progress * 100);
                }
            }
        }.start();
    }
}
