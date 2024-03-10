package ast;

public class ReturnStmt extends Stmt {

    final Expr exprNode;

    public ReturnStmt(Expr exprNode, Location loc) {
        super(loc);
        this.exprNode = exprNode;
    }

    public Expr getExpression() {
        return exprNode;
    }

    @Override
    public String toString() {
        return "return" + exprNode.toString() + ";" ;
    }
}