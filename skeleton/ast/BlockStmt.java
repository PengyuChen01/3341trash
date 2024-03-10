package ast;

public class BlockStmt extends Stmt {

    final StmtList stmtListNode;

    public BlockStmt(StmtList stmtListNode, Location loc) {
        super(loc);
        this.stmtListNode = stmtListNode;
    }

    public StmtList getStmtListNode() {
        return stmtListNode;
    }

    @Override
    public String toString() {
        
        return "{" + stmtListNode.toString() + "}";
    }
}