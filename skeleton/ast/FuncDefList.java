package ast;

public class FuncDefList extends ASTNode {

    final FuncDefList funcDefList1;
    final FuncDef funcDef1;

    public FuncDefList(FuncDef funcDef1, FuncDefList funcDefList1, Location loc) {
        super(loc);
        this.funcDefList1 = funcDefList1;
        this.funcDef1 = funcDef1;
    }

    public FuncDefList getFuncDefList() {
        return funcDefList1;
    }

    public FuncDef getFuncDef() {
        return funcDef1;
    }

    @Override
    public String toString() {
       if(funcDef1 == null){
           return "";
       }
       else if (funcDefList1 != null) {
            return funcDefList1.toString() + funcDef1.toString();
        } 
        return funcDef1.toString();
    }
}