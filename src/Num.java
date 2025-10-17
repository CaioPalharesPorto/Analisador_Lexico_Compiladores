public class Num extends Token {
    public final int value;
    public Num(int v) { 
        super(Tag.INT_CONST); 
        value = v; 
    }
    
    @Override
    public String toString(){ 
        return Integer.toString(value); 
    }
}
