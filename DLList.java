//===================================
// Java Templated Doubly Linked Class
//===================================

//===================================
// Imports
//===================================
import java.util.*;

//===================================
// DLL Class
//===================================
class DLList<T>{

    //===================================
    // (Internal) DLListNode Class
    //===================================
    private class DLListNode<T>{
        //data members
        public T data;
        public DLListNode<T> previous;
        public DLListNode<T> next;

        //overloaded constructor
        DLListNode(T value){
            data = value;
            previous = null;
            next = null;
        }
    }

    //===================================
    // Data Members
    //===================================
    private DLListNode<T> front;
    private DLListNode<T> back;
    private DLListNode<T> current;
    private int size;
    private int index;

    //===================================
    // Member Functions (methods)
    //===================================

    //default constructor
    public DLList(){
        clear();
    }

    //copy constructor (deep copy)
    public DLList(DLList<T> other){
        front = other.front;
        back = other.back;
        current = other.current;
        size = other.size;
        index = other.index;
    }

    //clear list method
    public void clear(){
        front = null;
        back = null;
        current = null;
        size = 0;
        index = -1;
    }

    //get size method
    public int getSize(){
        return size;
    }

    //get index method
    public int getIndex(){
        return index;
    }

    //is empty method
    public boolean isEmpty(){
        return (getSize() == 0);
    }

    //is at first node method
    public boolean atFirst(){
        return (index == 0);
    }

    //is at last node method
    public boolean atLast(){
        return (index == (getSize() -1));
    }

    //get data at current method
    public T getData(){
        if(!isEmpty())
            return current.data;
        else 
            return null;
    }

    //set data at current method
    public T setData(T x){
        if(!isEmpty()){
            current.data = x;
            return x;
        } else {
            return null;
        }
    }

    //seek to first node method
    public boolean first(){
        return (seek(0));
    }

    //seek to the next node method
    public boolean next(){
        return (seek(getIndex() +1));
    }

    //seek to the previous node method
    public boolean previous(){
        return (seek(getIndex() - 1));
    }

    //seek to the last node method
    public boolean last(){
        return (seek(getIndex()));
    }

    //seek method
    public boolean seek(int loc){
        //local variables
        boolean retval = false;

        //test if empty list
        if (isEmpty())
            retval = false;

        //is loc in range
       else if(loc < 0 || loc >= getSize())
            retval = false;

        //is loc == 0
        else if(loc == 0){
            current = front;
            index = 0;
            retval = true;
        }

        //is loc == last index
        else if(loc == getSize() -1){
            current = back;
            index = getSize() -1;
            retval = true;
        }
        //is loc < current index
        else if(loc < getIndex()){
            for(; getIndex() != loc; index--)
                current = current.previous;
            retval = true;
        }

        //is loc > current index
        else if(loc > getIndex()){
            for(; getIndex() != loc; index++){
                current = current.next;
            }
            retval = true;
        }

        //else ... loc is at the current index ... do nothing
        else{
            retval = true;
        }

        return(retval);
    }

    //insert front method
    public boolean insertFirst(T item){

        boolean retval = false;
        DLListNode<T> nn = new DLListNode<T>(item);
        if(isEmpty()){
            //seek(0);
            front = nn;
            back = nn;
            current = nn;
            size = 1;
            index = 0;
            retval = true;
        } else if (getSize() == 1){
            seek(0);
            nn.next = front;
            front.previous = nn;
            front = nn;
            current = nn;
            size++;
            retval = true;
        } else if (getSize() >1){    //same as 1?
            nn.next = front;
            front.previous = nn;
            front = nn;
            current = nn;
            size++;
            index = 0;
            retval = true;
        }
        return retval;
        //seek(0);
        //return(insertAt(item));

    }

    //insert at current location method
    public boolean insertAt(T item){
        //local variables
        DLListNode<T> nn = new DLListNode<T>(item);
        boolean retval = false;

        if(isEmpty()){
            //seek(0);
            front = nn;
            back = nn;
            current = nn;
            size++;
            index++;
            retval = true;
        } else if(getSize() == 1){
            seek(0);
            nn.next = front;
            front.previous = nn;
            front = nn;
            current = nn;
            size++;
            retval = true;
        } else if(getSize() > 1){
            if(atFirst()){
                insertFirst(item);
            } else if(atLast()){
                insertLast(item);
            } else {
                nn.next = current;
                nn.previous = current.previous;
                current.previous.next = nn;
                current.previous = nn;
                current = nn;
                size++;
                retval = true;
            }
        }
        return retval;
    }

    //insert last method
    public boolean insertLast(T item){

        DLListNode<T> nn = new DLListNode<T>(item);
        boolean retval = false;

        if(isEmpty()){
            //seek(0);
            front = nn;
            back = nn;
            current = nn;
            size++;
            index++;
            retval = true;
        } else if(getSize() == 1){
            back.next = nn;
            nn.previous = back;
            back = nn;
            current = nn;
            size++;
            index++;
            retval = true;
        } else if(getSize() > 1){
            back.next = nn;
            nn.previous = back;
            back = nn;
            current = nn;
            size++;
            index++;
            retval = true;
        }
        return retval;
    }

     //delete first method
     public boolean deleteFirst(){

        boolean retval = false;

        if(isEmpty()){
            retval = false;
        } else if (getSize() == 1){
            index = -1;
            size = 0;
            front = null;
            back = null;
            current = null;
            retval = true;
        } else if(getSize() > 1){
            seek(0);
            current = current.next;
            current.previous = null;
            front.next = null;
            front = current;
            size--;
            retval = true;
        }

        return retval;
    }

    //delete at current location method
    public boolean deleteAt(){

        boolean retval = false;

        if(isEmpty()){
            retval = false;
        } else if (getSize() == 1){
            index = -1;
            size = 0;
            front = null;
            back = null;
            current = null;
            retval = true;
        } else if (getSize() > 1){
            if(atFirst()){
                deleteFirst();
            } else if(atLast()){
                deleteLast();
            } else {
                DLListNode<T> delref = new DLListNode<T>(null);
                current.previous.next = current.next;
                current.next.previous = current.previous;
                delref = current;
                current = current.next;
                delref.previous = null;
                delref.next = null;
                delref = null;
                size--;
                retval = true;
            }
        }
        return retval;
    } 

    //delete last method
    public boolean deleteLast(){

        boolean retval = false;

        if(isEmpty()){
            retval = false;
        } else if (getSize() == 1){
            index = -1;
            size = 0;
            front = null;
            back = null;
            current = null;
            retval = true;
        } else if (getSize() > 1){
            seek(size -1);
            current = current.previous;
            current.next = null;
            back.previous = null;
            back = current;
            size --;
            index--;
            retval = true;
        }
        return retval;
    }
}
