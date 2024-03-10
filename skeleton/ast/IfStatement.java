package ast;

public class IfStatement extends Stmt {

    final Cond condNode;
    final Stmt stmtNode;

    public IfStatement(Cond condNode, Stmt stmtNode, Location loc) {
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
        return "If" + "(" + condNode.toString() + " ) " + stmtNode.toString();
    }
}