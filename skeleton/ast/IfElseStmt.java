package ast;

public class IfElseStmt extends Stmt{

    final Cond condNode;
    final Stmt stmtNode1;
    final Stmt stmtNode2;

    public IfElseStmt(Cond condNode, Stmt stmtNode1, Stmt stmtNode2, Location loc) {
        super(loc);
        this.condNode = condNode;
        this.stmtNode1 = stmtNode1;
        this.stmtNode2 = stmtNode2;
    }

    public Cond getCondNode() {
        return condNode;
    }
    public Stmt getStmtNode1() {
        return stmtNode1;
    }
    public Stmt getStmtNode2() {
        return stmtNode2;
    }

    @Override
    public String toString() {
        return "If" + "(" + condNode.toString() + " ) " + stmtNode1.toString() + "Else" + stmtNode2.toString();
    }
}