import java.util.Scanner;

public class testCL {
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        CommandLine cl = new CommandLine();
        cl.read(s);
        System.out.println(cl.cmd);
        System.out.println(cl.arg1);
        System.out.println(cl.arg2);
        System.out.println(cl.arg3);
        System.out.println(cl.arg4);
        System.out.println(cl.extra);
    }
}
