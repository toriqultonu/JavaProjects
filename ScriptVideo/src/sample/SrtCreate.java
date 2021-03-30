package sample;

import java.io.FileWriter;
import java.io.IOException;

public class SrtCreate {
    public static String srter(int[] sec, String[] subs){
        StringBuffer sb = new StringBuffer("");
        int i = 0;
        for(;i<sec.length-1;i++){
            String s =  i+1 + "\n"+"00:00:"+sec[i] +",000" + " --> " + "00:00:"+sec[i+1]+",000"+"\n"+subs[i]+"\n\n";
            sb.append(s);
        }
        return sb.toString();
    }
    public static void main(String[] args){
        int[] sec = {2,4,6,8,10};
        String[] subs = {"Hello ","How are you", "Toriqul Islam ","Where do you read?", "And in which departmen"};
        System.out.println(srter(sec,subs));
        try{
            FileWriter myWriter = new FileWriter("script.srt");
            myWriter.write(srter(sec, subs));
            myWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
