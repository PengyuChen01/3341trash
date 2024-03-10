package ast;

public class Type extends ASTNode {

    public static final int INT = 1;
    public static final int Q = 2;
    public static final int REF = 3;
    public int type;
    public Type(int type, Location loc) {
        super(loc);
        this.type = type;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        String s = null;
        switch (type) {
            case 1:  s = "int"; break;
            case 2: s = "Q"; break;
            case 3: s = "Ref"; break;
        }
        return s;
    }
}
