package ast;

public class Cond extends Expr {

    public static final int LESSTHANOREQUAL = 1;
    public static final int GREATERTHANOREQUAL = 2;
    public static final int EQUALTO = 3;
    public static final int NOTEQUALTO = 4;
    public static final int LESSTHAN = 5;
    public static final int GREATERTHAN = 6;
    public static final int AND = 7;
    public static final int OR = 8;
    public static final int NOT = 9;
    
    
    final int conditionOperator;
    final Expr expr1;
    final Expr expr2; 

    // Corrected the constructor name to match the class name.
    public Cond(Expr expr1,int conditionOperator, Expr expr2, Location loc) {
        super(loc);
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.conditionOperator = conditionOperator;
    }

    public Expr getExpr1() {
        return expr1;
    }

    public Expr getExpr2() {
        return expr2;
    }
    public int getConditionOperator() {
        return conditionOperator;
    }
    // Other methods would remain the same.

    // Assume this method is to generate a string representation of an if statement for printing.
    @Override
    public String toString() {
        String s = null;
        switch (conditionOperator) {
            case 1:  s = "(" + expr1.toString() + "<=" + expr2.toString() + ")"; break;
            case 2: s = "(" + expr1.toString() + ">=" + expr2.toString() + ")"; break;
            case 3: s = "(" + expr1.toString() + "==" + expr2.toString() + ")"; break;
            case 4: s = "(" + expr1.toString() + "!=" + expr2.toString() + ")"; break;
            case 5: s = "(" + expr1.toString() + "<" + expr2.toString()+ ")"; break;
            case 6: s = "(" + expr1.toString() + ">" + expr2.toString() + ")"; break;
            case 7: s = "(" + expr1.toString() + "&&" + expr2.toString() + ")"; break;
            case 8: s = "(" + expr1.toString() + "||" + expr2.toString() + ")"; break;
            case 9: s = "(!" + expr1.toString() + ")"; break;
            default: s = "it's not correct"; break;
        }
        return s;
    }
}

