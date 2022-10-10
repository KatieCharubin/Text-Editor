public class testDLL {
    
    public static void main(String[] args) {
        
        DLList<Integer> l = new DLList<Integer>();
        for(int i = 0; i < 10; i++){
            l.insertLast(i);
        }
        for(int i= 0; i < l.getSize(); i++){
            l.seek(i);
            System.out.println(l.getData());
        }
        System.out.println();
        boolean ok = l.last();
        while(ok){
            System.out.println(l.getData());
            ok = l.previous();
        }
    }
}
