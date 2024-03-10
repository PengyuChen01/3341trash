package ast;

public class NeFormalDeclList extends ASTNode {

    final VarDecl varDeclNode;
    final NeFormalDeclList NeFormalDeclListNode;


    public NeFormalDeclList(VarDecl varDeclNode, NeFormalDeclList NeFormalDeclListNode,Location loc) {
        super(loc);
        this.NeFormalDeclListNode = NeFormalDeclListNode;
        this.varDeclNode = varDeclNode;
    }


    public NeFormalDeclList getNeFormalDeclListNode() {
        return NeFormalDeclListNode;
    }
    public VarDecl getVarDecl() {
        return varDeclNode;
    }

    @Override
    public String toString() {
        if (NeFormalDeclListNode == null){
            return varDeclNode.toString();
        }
        return varDeclNode.toString() + "," + NeFormalDeclListNode.toString();
    }
}