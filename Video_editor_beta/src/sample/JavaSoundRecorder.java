package sample;


import javax.sound.sampled.*;
import java.io.File;

public class JavaSoundRecorder {

    public static TargetDataLine targetDataLine;
    public static void record(){

        try {
            AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);

            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            if (!AudioSystem.isLineSupported(info)) System.out.println("Error happed");

            targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
            targetDataLine.open();
            targetDataLine.start();
            Thread thread = new Thread() {
                @Override
                public void run() {
                    AudioInputStream audioInputStream = new AudioInputStream(targetDataLine);
                    File audioFile = new File("C:/Users/nafiu/Desktop/Coding/Audios/record.wav");
                    try {
                        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, audioFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
                //Thread.sleep(10000);
                //targetDataLine.stop();
                //targetDataLine.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}