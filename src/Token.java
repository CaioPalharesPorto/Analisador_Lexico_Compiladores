public class Token {
    public final int tag;
    public Token(int t){
        this.tag = t; 
    }

    @Override
    public String toString() {
        if (tag < 256) return String.valueOf((char) tag); //para caracteres ASCII
        return ""+tag; 
    }
}
