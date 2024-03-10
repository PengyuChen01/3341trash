package ast;

import java.io.PrintStream;

public class Program extends ASTNode {

    final FuncDefList func1;

    public Program(FuncDefList func1, Location loc) {
        super(loc);
        this.func1 = func1;
    }

    public FuncDefList getFuncDefList() {
        return func1;
    }

    public void println(PrintStream ps) {
        ps.println(func1.toString());
    }
}
