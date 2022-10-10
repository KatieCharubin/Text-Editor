import java.io.*;
import java.util.Scanner;

class LTE{

    static boolean number = false;
    public static void main(String[] args) throws IOException {

        //create empty buffer and clipboard
        Buffer buffer = new Buffer();
        Buffer clipboard = new Buffer();

        //checks if there are more than one file
        if(args.length > 1){
            System.out.println("Too many command line arguments");
            return;
        //checks if there is 1 file
        } else if(args.length == 1){    
            File file = new File(args[0]);

            //checks if valid file
            if(file.isFile()){
                Scanner fileS = new Scanner(file);

                //add each file line to dllist
                 while (fileS.hasNextLine()){
                    buffer.lines.insertLast(fileS.nextLine());
                }
                fileS.close();
            } else {
                System.out.println("Not a valid file.");
            }
        }

        Scanner in = new Scanner(System.in);

        CommandLine cl = new CommandLine();
        boolean done = false;

        //reads and processes commands until the user quits
        while(!done){
            cl.read(in);
            switch(cl.cmd){
                case "h":
                    help(cl);
                    break;
                case "r":
                    readFile(cl, buffer);
                    break;
                case "w":
                    writeFile(cl, buffer);
                    break;
                case "f":
                    changeFile(cl, buffer);
                    break;
                case "q":
                    quit(cl, buffer);
                    done = true;
                    break;
                case "q!":
                    forceQuit(cl, buffer);
                    done = true;
                    break;
                case "t":
                    top(cl, buffer);
                    break;
                case "b":
                    bottom(cl, buffer);
                    break;
                case "g":
                    goTo(cl, buffer);
                    break;
                case "-":
                    prev(cl, buffer);
                    break;
                case "+":
                    next(cl, buffer);
                    break;
                case "=":
                    printLine(cl, buffer);
                    break;
                case "n":
                    toggle(cl);
                    break;
                case "#":
                    printlc(cl, buffer);
                    break;
                case "p":
                    print(cl, buffer);
                    break;
                case "pr":
                    printRange(cl, buffer);
                    break;
                case "?":
                    searchB(cl, buffer);
                    break;
                case "/":
                    searchF(cl, buffer);
                    break;
                case "s":
                    subText(cl, buffer);
                    break;
                case "sr":
                    subTextR(cl, buffer);
                    break;
                case "d":
                    deleteLine(cl, buffer, clipboard);
                    break;
                case "dr":
                    deleteR(cl, buffer, clipboard);
                    break;
                case "c":
                    copy(cl, buffer, clipboard);
                    break;
                case "cr":
                    copyR(cl, buffer, clipboard);
                    break;
                case "pa":
                    pasteA(cl, buffer, clipboard);
                    break;
                case "pb":
                    pasteB(cl, buffer, clipboard);
                    break;
                case "ia":
                    insertA(cl, buffer, clipboard);
                    break;
                case "ic":
                    insertC(cl, buffer, clipboard);
                    break;
                case "ib":
                    insertB(cl, buffer, clipboard);
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
               }
        }

        in.close();
    }

    //checks if given input is a number
    public static boolean valNum(String st){
        int num = 0;
        try { 
            num = Integer.parseInt(st); 
        } catch(NumberFormatException e) { 
            System.out.println("Invalid number");
            return false;
        } catch(NullPointerException e) {
            System.out.println("Invalid number");
            return false;
        }   
        return true;
    }

    public static void help(CommandLine c){

        if(c.count == 0){
            System.out.println("Command     Arguments       Description");
            System.out.println("h                           Display help");
            System.out.println("r           filespec        Read a file into the current buffer");
            System.out.println("w                           Write the current buffer to a file on disk");
            System.out.println("f           filespec        Change the name of the current buffer");
            System.out.println("q                           Quit the line editor");
            System.out.println("q!                          Quit the line editor without saving");
            System.out.println("t                           Go to the first line in the buffer");
            System.out.println("b                           Go to the last line in the buffer");
            System.out.println("g           num             Go to the line num in the buffer");
            System.out.println("-                           Go to the previous line");
            System.out.println("+                           Go to the next line");
            System.out.println("=                           Print the current line number");
            System.out.println("n                           Toggle line number displayed");
            System.out.println("#                           Print the number of lines and characters in the buffer");
            System.out.println("p                           Print the current line");
            System.out.println("pr          start stop      Print several lines");
            System.out.println("?           pattern         Search backwards for pattern");
            System.out.println("/           pattern         Searches forwards for a pattern");
            System.out.println("s           text1 text2     Substitute all occurances of text1 with text2 on current line");
            System.out.println("sr  text1 text2 start stop  Substitute all occurances of text1 with text2 between start and stop");
            System.out.println("d                           Delete current line from buffer and copy into the clipboard (CUT)");
            System.out.println("dr          start stop      Delete several lines from buffer and copy into clipboard (CUT)");
            System.out.println("c                           Copy current line to clipboard (COPY)");
            System.out.println("cr          start stop      Copy lines between start and stop into the clipboard (COPY)");
            System.out.println("pa                          Paste contents of the clipboard above the line (PASTE)");
            System.out.println("pb                          Paste contents of clipboard below current line (PASTE)");
            System.out.println("ia                          Insert new lines of text above current line until '.' appears on its own line");
            System.out.println("ic                          Insert new lines of text at current line until '.' appears on its own line (REPLACE LINE)");
            System.out.println("ib                          Insert new lines of text after current line until '.' appears on its own line");
        } else {
            System.out.println("Invalid command");
        }
    }

    public static void readFile(CommandLine c, Buffer b) throws IOException{
        if(c.count == 1){
            String filespec = c.arg1;
            File f = new File(filespec);

            if (f.isFile()){

                if(b.getDirty()){
                    System.out.println("Saving changes first");
                    writeFile(c, b);
                }

                b.setFilename(filespec);

                if(b.lines.isEmpty()){
                    b.lines.clear();
                }
            
                Scanner s = new Scanner(f);
                while(s.hasNextLine()){
                    b.lines.insertLast(s.nextLine());
                }
                s.close();
                b.setDirty(true);
        
            } else {
                System.out.println("==>> FILE DOES NOT EXIST <<==");
            } 
        } else {
            System.out.println("Invalid command");
    }
    //test

    }

    public static void writeFile(CommandLine c, Buffer b) throws IOException{

        if(c.count == 0){
            if(b.lines.isEmpty()){
                System.out.println("==>> BUFFER IS EMPTY <<==");
            } else {
                String filespec = b.getFilename();
                FileWriter fw = new FileWriter(filespec);
                PrintWriter pw = new PrintWriter(fw);
                int index = b.lines.getIndex();
    
                boolean ok = b.lines.first();
               
                while(!ok){
                    pw.println(b.lines.getData());
                    ok = b.lines.next();
                }
                pw.close();
                b.lines.seek(index);
                b.setDirty(false);
    
            }
        } else {
            System.out.println("Invalid command");
        }

    }

    public static void changeFile(CommandLine c, Buffer b){
        if(c.count == 1){
            String filespec = c.arg1;
            b.setFilename(filespec);
            b.setDirty(true);
            System.out.println("New filename: " + b.getFilename());
        } else {
            System.out.println("Invalid command");
        }
    }

    public static void quit(CommandLine c, Buffer b) throws IOException{

        if(c.count == 0){
            if(b.getDirty()){
                System.out.println("Saving before quitting");
                writeFile(c, b);
            }
        } else {
            System.out.println("Invalid command");
        }
    }

    public static void forceQuit(CommandLine c, Buffer b){
        if(c.count == 0){
            System.out.println("Forcing quit");
        } else {
            System.out.println("Invalid command");
        }
        
    }

    public static void top(CommandLine c, Buffer b){

        if(c.count == 0){
            if(b.lines.isEmpty()){
                System.out.println("==>> BUFFER IS EMPTY <<==");
            } else {
                b.lines.seek(0);
            }
        } else {
            System.out.println("Invalid command");
        }
    }

    public static void bottom(CommandLine c, Buffer b){
        if(c.count == 0){
            if(b.lines.isEmpty()){
                System.out.println("==>> BUFFER IS EMPTY <<==");
            } else {
                b.lines.last();
            }
        } else {
            System.out.println("Invalid command");
        }
    }

    public static void goTo(CommandLine c, Buffer b){
        if(c.count == 1){
            if(valNum(c.arg1)){
                int n = Integer.parseInt(c.arg1);
                if(n > 0 && n <= b.lines.getSize() ){
                    b.lines.seek(n -1);
                } else {
                    System.out.println("==>> RANGE ERROR - num MUST BE [1.." + b.lines.getSize() + "] <<==");
                }
            } else {
                System.out.println("Invalid numbers. Enter next command.");
            }
        } else {
            System.out.println("Invalid command");
        }

    }

    public static void prev(CommandLine c, Buffer b){

        if(c.count == 0){
            if(b.lines.isEmpty()){
                System.out.println("==>> BUFFER IS EMPTY <<==");
            } else if (b.lines.atFirst()){
                System.out.println("==>> ALREADY AT TOP OF BUFFER <<==");
            } else {
                b.lines.previous();
            }
        } else {
            System.out.println("Invalid command");
        }
    }

    public static void next(CommandLine c, Buffer b){

        if(c.count == 0){
            if(b.lines.isEmpty()){
                System.out.println("==>> BUFFER IS EMPTY <<==");
            } else if (b.lines.atLast()){
                System.out.println("==>> ALREADY AT BOTTOM OF BUFFER <<==");
            } else {
                b.lines.next();
            }
        } else {
            System.out.println("Invalid command");
        }
    }

    public static void toggle(CommandLine c){
        if(c.count == 0){
            number = true;
        } else {
            System.out.println("Invalid command");
        }
    }

    public static void print(CommandLine c, Buffer b){

        if(c.count == 0){
            if(b.lines.isEmpty()){
                System.out.println("==>> BUFFER IS EMPTY <<==");
            } else if(number){
                int lineN = b.lines.getIndex() + 1;
                System.out.println(lineN + ". " + b.lines.getData());
            } else {
                System.out.println(b.lines.getData());
            }
        } else {
            System.out.println("Invalid command");
        }
    }

    public static void printRange(CommandLine c, Buffer b){

        if(c.count == 2){
            if(valNum(c.arg1) && valNum(c.arg2)){
                int start = Integer.parseInt(c.arg1);
                int stop = Integer.parseInt(c.arg2);
                if(b.lines.isEmpty()){
                    System.out.println("==>> BUFFER IS EMPTY <<==");
                } else if (start > 0 && stop <= b.lines.getSize() && start < stop){
                    start--;
        
                    if(number){
                        for (int i = start; i < stop; i++){
                            b.lines.seek(i);
                            System.out.println((i+1) + ". " + b.lines.getData());
                        }
                    } else {
                        for(int i = start; i<stop; i++){
                            b.lines.seek(i);
                            System.out.println(b.lines.getData());
                        }
                        
                    }
                    
                } else {
                    System.out.println("==>> RANGE ERROR - start stop MUST BE [1.." + b.lines.getSize() + "] <<==");
                }
            } else {
                System.out.println("Invalid arguments");
            }
        } else {
            System.out.println("Invalid command");
        }
        
    }

    public static void searchB(CommandLine c, Buffer b){

        if(c.count == 1){
            String p = c.arg1;
            if(b.lines.isEmpty()){
                System.out.println("==>> BUFFER IS EMPTY <<==");
            } else if (b.lines.atFirst()){
                System.out.println("==>> ALREADY AT TOP OF BUFFER <<==");
            } else {
                boolean cont = true;
                boolean found = false;;
                int foundLine = 0;
                int current = b.lines.getIndex();
                Scanner sc = new Scanner(System.in);
               
                while(!b.lines.atFirst() && cont){
                    b.lines.previous();
                    String s = b.lines.getData();
                    if(s.indexOf(p) > -1){
                        foundLine = b.lines.getIndex() + 1;
                        found = true;
                        System.out.println("Pattern found on line: " + foundLine);
                        System.out.println("Do you want to continue the search? y/n");

                        if(sc.nextLine().equals("y")){
                            cont = true;
                        } else {
                            cont = false;
                        }
                    }
                }
                //sc.close();
                if(found == false){
                    System.out.println("==>> STRING pattern NOT FOUND <<==");
                    b.lines.seek(current);
                    b.lines.seek(foundLine);
                }
            }
        } else {
            System.out.println("Invalid command");
        }
        
    }

    public static void searchF(CommandLine c, Buffer b){

        if(c.count == 1){
            String p = c.arg1;
            if(b.lines.isEmpty()){
                System.out.println("==>> BUFFER IS EMPTY <<==");
            } else if (b.lines.atLast()){
                System.out.println("==>> ALREADY AT BOTTOM OF BUFFER <<==");
            } else {
                boolean found = false;
                boolean cont = true;
                int foundLine = 0;
                int current = b.lines.getIndex();
                Scanner sc = new Scanner(System.in);

                while(!b.lines.atLast() && cont == true){
                    b.lines.next(); 
                    String s = b.lines.getData();
                    if(s.indexOf(p) > -1){
                        found = true;
                        foundLine = b.lines.getIndex() + 1;
                        System.out.println("Pattern found on line: " + foundLine);
                        System.out.println("Do you want to continue the search? y/n");
                        //Scanner sc = new Scanner(System.in);
                        if(sc.nextLine().equals("y")){
                            cont = true;
                        } else {
                            cont = false;
                        }
  
                    }
                }
                //sc.close();
                if(!found){
                    System.out.println("==>> STRING pattern NOT FOUND <<==");
                    b.lines.seek(current);
                } 
            }
        } else {
            System.out.println("Invalid command");
        }
    }

    public static void subText(CommandLine c, Buffer b){
        if(c.count == 2){
            String s1 = c.arg1;
            String s2 = c.arg2;
            if(!b.lines.isEmpty()){
                String s = b.lines.getData();
                String newS = s.replaceAll(s1, s2);
                b.lines.setData(newS);
                b.setDirty(true);
            }
        } else {
            System.out.println("Invalid command");
        }
    }


    public static void subTextR(CommandLine c, Buffer b){

        if(c.count == 4){
            if(valNum(c.arg3) && valNum(c.arg4)){
                String s1 = c.arg1;
                String s2 = c.arg2;
                int start = Integer.parseInt(c.arg3);
                int stop = Integer.parseInt(c.arg4);
                if(start > 0 && stop >= start && stop <= b.lines.getSize()){
                    int sindex = start-1;
                    for(int i = sindex; i < stop; i++){
                        b.lines.seek(i);
                        String s = b.lines.getData();
                        String newS = s.replaceAll(s1, s2);
                        b.lines.setData(newS);
                    }
                    b.setDirty(true);
               } else {
                System.out.println("==>> INDICES OUT OF RANGE <<==");
               }
            } else {
                System.out.println("Invalid arguments");
            }
        } else {
            System.out.println("Invalid command");
        }
    }
    //line number

    public static void deleteLine(CommandLine c, Buffer b, Buffer cb){

        if(c.count == 0){
            if(b.lines.isEmpty()){
                System.out.println("==>> BUFFER IS EMPTY <<==");
            } else {
                if(cb.lines.isEmpty()){
                    String data = b.lines.getData();
                    cb.lines.insertAt(data);
                    b.lines.deleteAt();
                } else {
                    cb.lines.clear();
                    String data = b.lines.getData();
                    cb.lines.insertAt(data);
                    b.lines.deleteAt();
                }
                b.setDirty(true);
            }
        } else {
            System.out.println("Invalid command");
        }
    }

    public static void deleteR(CommandLine c, Buffer b, Buffer cb){

        if(c.count == 2){
            if(valNum(c.arg1) && valNum(c.arg2)){
                int s = Integer.parseInt(c.arg1);
                int e = Integer.parseInt(c.arg2);
                if(s > 0 && e <= b.lines.getSize() && e >= s){
                    if(b.lines.isEmpty()){
                        System.out.println("==>> BUFFER IS EMPTY <<==");
                    } else {
                        if(cb.lines.isEmpty()){
                            b.lines.seek(s);
                            for(int i = s; i < e+1; i++){
                                String st = b.lines.getData();
                                cb.lines.insertLast(st);
                                b.lines.deleteAt();
                            }
                        } else {
                            cb.lines.clear();
                            b.lines.seek(s);
                            for(int i = s; i < e+1; i++){
                                String st = b.lines.getData();
                                cb.lines.insertLast(st);
                                b.lines.deleteAt();
                            }
                        }
                        b.setDirty(true);
                    }
                } else {
                    System.out.println("==>> INDICES OUT OF RANGE <<==");
                }
            } else {
                System.out.println("Invalid argument");
            }
        } else {
            System.out.println("Invalid command");
        }
    }

    public static void copy(CommandLine c, Buffer b, Buffer cb){
        if(c.count == 0){
            if(b.lines.isEmpty()){
                System.out.println("==>> BUFFER IS EMPTY <<==");
            } else {
                if(!cb.lines.isEmpty()){
                    cb.lines.clear();
                }
                String data = b.lines.getData();
                cb.lines.insertAt(data);
                cb.setDirty(true);
            }
        } else {
            System.out.println("Invalid command");
        }
    }

    public static void copyR(CommandLine c, Buffer b, Buffer cb){
        if(c.count == 2){
            if(valNum(c.arg1) && valNum(c.arg2)){
                int s = Integer.parseInt(c.arg1);
                int e = Integer.parseInt(c.arg2);
                if(b.lines.isEmpty()){
                    System.out.println("==>> BUFFER IS EMPTY <<==");
                } else if(s <= b.lines.getSize() && s > 0 && e <= b.lines.getSize() && e >= s){
                    b.lines.seek(s);
                    if(!cb.lines.isEmpty()){
                        cb.lines.clear();
                    }
                    for(int i = s; s < e+1; i++){
                        String data = b.lines.getData();
                        cb.lines.insertLast(data);
                        b.lines.next();
                    }
                    cb.setDirty(true);
                } else {
                    System.out.println("==>> INDICES OUT OF RANGE <<==");
                }
            } else {
                System.out.println("Invalid argument");
            }
        } else {
            System.out.println("Invalid command");
        }
    }

    public static void pasteA(CommandLine c, Buffer b, Buffer cb){

        if(c.count == 0){
            if(cb.lines.isEmpty()){
                System.out.println("==>> CLIPBOARD IS EMPTY <<==");
            } else {
                cb.lines.last();
                for(int i = 0; i < cb.lines.getSize(); i++){    
                    String data = cb.lines.getData();
                    b.lines.insertAt(data);
                    b.lines.next();
                    cb.lines.previous();
                }
                b.setDirty(true);
            }
        } else {
            System.out.println("Invalid command");
        }
        
    }

    public static void pasteB(CommandLine c, Buffer b, Buffer cb){

        if(c.count == 0){
            if(cb.lines.isEmpty()){
                System.out.println("==>> CLIPBOARD IS EMPTY <<==");
            } else {
                cb.lines.last();
                b.lines.next();
                for(int i = 0; i < cb.lines.getSize(); i++){    
                    String data = cb.lines.getData();
                    b.lines.insertAt(data);
                    cb.lines.previous();
                }
                b.setDirty(true);
            }
        } else {
            System.out.println("Invalid command");
        }
    }

    public static void insertA(CommandLine c, Buffer b, Buffer cb) throws IOException{
        if(c.count == 0){
            Scanner s = new Scanner(System.in);
            String st;
            int currentLine = b.lines.getIndex();
            boolean done = false;
            cb.lines.clear();

            while(!done){
                st = s.nextLine();
                if(st.equals(".")){
                    done = true;
                } else {
                    cb.lines.insertLast(st);
                }
            }

            //s.close();
            cb.setDirty(true);
            pasteA(c, b, cb);
            b.lines.seek(currentLine);

        } else {
            System.out.println("Invalid command");
        }
    }
    //line number matters

    public static void insertB(CommandLine c, Buffer b, Buffer cb){
        if(c.count == 0){
            Scanner s = new Scanner(System.in);
            String st;
            int currentLine = b.lines.getIndex();
            cb.lines.clear();
            boolean done = false;

            while(!done){
                st = s.nextLine();

                if(st.equals(".")){
                    done = true;
                } else {
                    cb.lines.insertLast(st);
                }
            }
            //s.close();
            cb.setDirty(true);
            pasteB(c, b, cb);
            b.lines.seek(currentLine);

        } else {
            System.out.println("Invalid command");
        }
        
    }

    public static void insertC(CommandLine c, Buffer b, Buffer cb){
        if(c.count == 0){
            Scanner s = new Scanner(System.in);
            String st;
            cb.lines.clear();
            boolean done = false; 

            while(!done){
                st = s.nextLine();
                if(st.equals(".")){
                    done = true;
                } else {
                    cb.lines.insertLast(st);
                }
            }
            //s.close();
            cb.setDirty(true);
            pasteA(c, b, cb);
        } else {
            System.out.println("Invalid command");
        }
    }

    public static void printLine(CommandLine c, Buffer b){
        if(c.count == 0){
            System.out.println("Current line: " + (b.lines.getIndex() + 1));
        } else {
            System.out.println("Invalid command");
        }
    }

    public static void printlc(CommandLine c, Buffer b){
        if(c.count == 0){
            int lines = b.lines.getSize();
            int chars = 0;

            for(int i = 0; i < b.lines.getSize(); i++){
                String st = b.lines.getData();
                for(int j = 0; j < st.length(); j++){
                    if(st.charAt(j) != ' '){
                        chars++;
                    }
                }
                b.lines.next();
            }

            System.out.println("There are " + lines + " lines and " + chars + " characters.");
        } else {
            System.out.println("Invalid command");
        }
    }
}