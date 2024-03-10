package ast;

public class CallExpr extends Expr
{
    final IdentExpr id;
    final ExprList exprlst;
    
    public  CallExpr(IdentExpr id, ExprList exprlst, Location loc){
        super(loc);
        this.id = id;
        this.exprlst = exprlst;
    }
    public IdentExpr getId(){
        return id;
    }
    public ExprList getExprList(){
        return exprlst;
    }
    @Override
    public String toString(){
        return id.toString() + "(" + exprlst.toString() + ")";
    }
    
}