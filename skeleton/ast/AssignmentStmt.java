package ast;

public class AssignmentStmt extends Stmt {

    final IdentExpr idNode;
    final Expr exprNode;

    public AssignmentStmt(IdentExpr idNode, Expr exprNode, Location loc) {
        super(loc);
        this.idNode = idNode;
        this.exprNode = exprNode;
    }

    public IdentExpr getIdNode() {
        return idNode;
    }
    public Expr getExprNode() {
        return exprNode;
    }

    @Override
    public String toString() {
        return idNode.toString() + " = " + exprNode.toString() + ";";
    }
}