public class Buffer {
    
    private String filename;
    private boolean dirty;
    public DLList<String> lines;

    public Buffer(){
        filename = "buffer";
        dirty = false;
        lines = new DLList<String>();
    }

    public Buffer(String f, boolean d, DLList<String> l){
        filename = f;
        dirty = d;
        lines = l;
    }

    public String getFilename(){
        return filename;
    }

    public void setFilename(String s){
        filename = s;
    }

    public boolean getDirty(){
        return dirty;
    }

    public void setDirty(boolean b){
        dirty = b;
    }

    public void reset(){
        dirty = false;
    }

    //get and set filename
    //get, set, reset dirtybit
}
