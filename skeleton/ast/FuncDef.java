package ast;

public class FuncDef extends ASTNode {

    final VarDecl varDeclNode;
    final FormalDeclList FormalDeclListNode;
    final StmtList stmtListNode;

    public FuncDef(VarDecl varDeclNode, FormalDeclList FormalDeclListNode, StmtList stmtListNode,Location loc) {
        super(loc);
        this.varDeclNode = varDeclNode;
        this.FormalDeclListNode = FormalDeclListNode;
        this.stmtListNode = stmtListNode;
    }

    public VarDecl getVarDecl() {
        return varDeclNode;
    }

    public FormalDeclList getFormalDeclList() {
        return FormalDeclListNode;
    }
    public StmtList getStmtList() {
        return stmtListNode;
    }

    @Override
    public String toString() {
        if (FormalDeclListNode == null){
            return varDeclNode.toString() + "(" + ")" + "{" + stmtListNode.toString()+"}" ;
        }
        return varDeclNode.toString() + "(" + FormalDeclListNode.toString() + ")" + "{" + stmtListNode.toString()+"}";
    }
}