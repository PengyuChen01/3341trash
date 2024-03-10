package ast;

public class WhileStmt extends Stmt {

    final Cond condNode;
    final Stmt stmtNode;

    public WhileStmt(Cond condNode, Stmt stmtNode, Location loc) {
        super(loc);
        this.condNode = condNode;
        this.stmtNode = stmtNode;
    }

    public Cond getCondNode() {
        return condNode;
    }
    public Stmt getStmtNode() {
        return stmtNode;
    }

    @Override
    public String toString() {
        return "while" + "(" + condNode.toString() + " ) " + stmtNode.toString();
    }
}