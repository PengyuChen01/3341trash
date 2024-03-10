package ast;

public class DeclarationStmt extends Stmt {

    final VarDecl varDeclNode;
    final Expr exprNode;

    public DeclarationStmt(VarDecl varDeclNode, Expr exprNode, Location loc) {
        super(loc);
        this.varDeclNode = varDeclNode;
        this.exprNode = exprNode;
    }

    public Expr getExpression() {
        return exprNode;
    }
    public VarDecl getVarDecl() {
        return varDeclNode;
    }

    @Override
    public String toString() {
        return varDeclNode.toString() + " = " + exprNode.toString() + ";";
    }
}