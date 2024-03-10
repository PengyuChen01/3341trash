package ast;

public class VarDecl extends ASTNode {

    final Type typeNode;
    final IdentExpr idNode;

    public VarDecl(Type typeNode, IdentExpr idNode, Location loc) {
        super(loc);
        this.typeNode = typeNode;
        this.idNode = idNode;
    }

    public Type getType() {
        return typeNode;
    }

    public IdentExpr getId() {
        return idNode;
    }

    @Override
    public String toString() {
        return typeNode.toString() + " " + idNode.toString();
    }
}