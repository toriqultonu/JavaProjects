import java.util.Scanner;

class Problem1 {
    public static void main(String[] args){
        int t;
        int a,b;
        Scanner scanner = new Scanner(System.in);
        t = scanner.nextInt();
        for(int i=0;i<t;i++){
            a = scanner.nextInt();
            b = scanner.nextInt();
            if(a>b)
                System.out.println(">");
            if(a<b)
                System.out.println("<");
            if(a==b)
                System.out.println("=");
        }
    }
}
