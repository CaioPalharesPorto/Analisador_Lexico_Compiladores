public class FNum extends Token {
    public final float value;
    public FNum(float v) { 
        super(Tag.FLOAT_CONST); 
        value = v; 
    }
    
    @Override
    public String toString(){ 
        return Float.toString(value); 
    }

}
