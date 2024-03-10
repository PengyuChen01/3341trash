package ast;

public class ExprList extends ASTNode {

    final NeExprList neExprListNode;

    public ExprList(NeExprList neExprListNode, Location loc) {
        super(loc);
        this.neExprListNode = neExprListNode;
    }
    public ExprList(Location loc){
        super(loc);
        this.neExprListNode = null;
    }

    public NeExprList getneExprList() {
        return neExprListNode;
    }

    @Override
    public String toString() {
        return neExprListNode.toString();
    }
}