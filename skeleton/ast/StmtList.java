package ast;

public class StmtList extends ASTNode {

    final StmtList StmtListNode;
    final Stmt StmtNode;

    public StmtList(Stmt StmtNode, StmtList StmtListNode, Location loc) {
        super(loc);
        this.StmtListNode = StmtListNode;
        this.StmtNode = StmtNode;
    }
    public StmtList(Location loc){
        super(loc);
        this.StmtListNode = null;
        this.StmtNode = null;
    }

    public StmtList getStmtList() {
        return StmtListNode;
    }

    public Stmt getStmt() {
        return StmtNode;
    }

    @Override
    public String toString() {
       if(StmtNode == null){
           return "";
       }
        return StmtNode.toString() + StmtListNode.toString();
    }
}