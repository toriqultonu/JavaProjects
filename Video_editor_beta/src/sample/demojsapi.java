package sample;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import javax.speech.*;
import java.util.*;
import javax.speech.synthesis.*;

public class demojsapi
{
    public static class Import{
        private String name;
        private Voice voice;

        public Import(String name){
            this.name = name;
            this.voice = VoiceManager.getInstance().getVoice(this.name);
            this.voice.allocate();
        }
        public void say(String something){
            this.voice.speak(something);
        }
    }

    public static void main(String[] args){
       Import model = new Import("kevin16");

       String sayme = "Hello World";
       model.say(sayme);
    }
}