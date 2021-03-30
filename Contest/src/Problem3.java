import java.util.Scanner;

class Problem3 {
    public static void main(String[] args){
        int n;
        int a;
        int count=0;
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        for(int i=0;i<n;i++){
            a = scanner.nextInt();
            while(a/6>1) {
                if (a % 6 == 0 && a / 6 > 1)
                    a = a / 6;
                else if (a / 6 > 0)
                    a = a * 2;
                else a = -1;
                count++;
            }
            System.out.println(count);
            count = 0;
        }

    }
}
