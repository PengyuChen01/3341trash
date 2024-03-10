package ast;

public class FormalDeclList extends ASTNode {

    final NeFormalDeclList NeFormalDeclListNode;


    public FormalDeclList(NeFormalDeclList NeFormalDeclListNode,Location loc) {
        super(loc);
        this.NeFormalDeclListNode = NeFormalDeclListNode;
    }

    public NeFormalDeclList getNeFormalDeclListNode() {
        return NeFormalDeclListNode;
    }

    @Override
    public String toString() {
        if (NeFormalDeclListNode == null){
            return "";
        }
        return NeFormalDeclListNode.toString();
    }
}