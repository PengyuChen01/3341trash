package ast;

public class NeExprList extends ASTNode {

    final NeExprList neExprListNode;
    final Expr exprNode;

    public NeExprList(Expr exprNode, NeExprList neExprListNode, Location loc) {
        super(loc);
        this.neExprListNode = neExprListNode;
        this.exprNode = exprNode;
    }
    public NeExprList(Expr exprNode, Location loc) {
        super(loc);
        this.neExprListNode = null;
        this.exprNode = exprNode;
    }


    public NeExprList getneExprList() {
        return neExprListNode;
    }
    public Expr getExpr() {
        return exprNode;
    }

    @Override
    public String toString() {
        String s = null;
        if(neExprListNode == null){
            s =  exprNode.toString();
        }
        s =  exprNode.toString() + "," + neExprListNode.toString();
        return s;
    }
}