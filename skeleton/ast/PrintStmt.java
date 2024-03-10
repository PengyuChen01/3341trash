package ast;

public class PrintStmt extends Stmt {

    final Expr exprNode;

    public PrintStmt(Expr exprNode, Location loc) {
        super(loc);
        this.exprNode = exprNode;
    }

    public Expr getExpression() {
        return exprNode;
    }

    @Override
    public String toString() {
        return "print" + exprNode.toString() + ";" ;
    }
}