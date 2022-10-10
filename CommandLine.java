import java.util.Scanner;
import java.util.StringTokenizer;

public class CommandLine {
    
    String cmd;
    String arg1;
    String arg2;
    String arg3;
    String arg4;
    String extra;
    int count;
    String[] valid = {"h", "r", "w", "f", "q!", "t", "b", "g", "-", "+", "=", "n", "#", "p", "pr", "?", "/", "s", "sr", "d", "dr", "c", "cr", "pa", "pb", "ia", "ic", "ib"};

    public CommandLine(){
        count = 0;
    }

    public boolean read(Scanner scan){
        count = 0;
        String s = scan.nextLine();
        StringTokenizer tok = new StringTokenizer(s, " ");
        cmd = tok.nextToken();
            
        if(tok.hasMoreTokens()){
            arg1 = tok.nextToken();
            count++;
        }
        if(tok.hasMoreTokens()){
            arg2 = tok.nextToken();
            count++;
        }
        if(tok.hasMoreTokens()){
            arg3 = tok.nextToken();
            count++;
        }
        if(tok.hasMoreTokens()){
            arg4 = tok.nextToken();
            count++;
        }
        if(tok.hasMoreTokens()){
            extra = tok.nextToken("").trim();
        }
        return findIndex(valid, cmd);

    }

    public boolean findIndex(String[] list, String s){
        boolean found = false;
        for (int i = 0; i < list.length; i++){
            if(list[i].equals(s)){
                found = true;
            }
        }
        return found;
    }
    
}
