import java.util.Scanner;

class Problem2 {
    public static void main(String[] args){
        int n;
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        int count=0,count2;
        int a,b;
        for(int i=0;i<n;i++){
           a = scanner.nextInt();
           b = scanner.nextInt();
           count2 = (count + b) - a;
           if(count2>count)
               count = count2;
        }
        System.out.println(count);
    }
}
