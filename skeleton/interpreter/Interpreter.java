package interpreter;
import java.io.*;
import java.util.Random;

import parser.ParserWrapper;
import ast.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Interpreter {

    // Process return codes
    public static final int EXIT_SUCCESS = 0;
    public static final int EXIT_PARSING_ERROR = 1;
    public static final int EXIT_STATIC_CHECKING_ERROR = 2;
    public static final int EXIT_DYNAMIC_TYPE_ERROR = 3;
    public static final int EXIT_NIL_REF_ERROR = 4;
    public static final int EXIT_QUANDARY_HEAP_OUT_OF_MEMORY_ERROR = 5;
    public static final int EXIT_DATA_RACE_ERROR = 6;
    public static final int EXIT_NONDETERMINISM_ERROR = 7;
    //private static HashMap<String, Long> variables = new HashMap<String, Long>();
    private static HashMap<String, FuncDef> function = new HashMap<String, FuncDef>();
    static private Interpreter interpreter;
    static private boolean returnFlag = false;
    public static Interpreter getInterpreter() {
        return interpreter;
    }

    public static void main(String[] args) {

        String gcType = "NoGC"; // default for skeleton, which only supports NoGC
        long heapBytes = 1 << 14;
        int i = 0;
        String filename;
        long quandaryArg;
        try {
            for (; i < args.length; i++) {
                String arg = args[i];
                if (arg.startsWith("-")) {
                    if (arg.equals("-gc")) {
                        gcType = args[i + 1];
                        i++;
                    } else if (arg.equals("-heapsize")) {
                        heapBytes = Long.valueOf(args[i + 1]);
                        i++;
                    } else {
                        throw new RuntimeException("Unexpected option " + arg);
                    }
                } else {
                    if (i != args.length - 2) {
                        throw new RuntimeException("Unexpected number of arguments");
                    }
                    break;
                }
            }
            filename = args[i];
            quandaryArg = Long.valueOf(args[i + 1]);
        } catch (Exception ex) {
            System.out.println("Expected format: quandary [OPTIONS] QUANDARY_PROGRAM_FILE INTEGER_ARGUMENT");
            System.out.println("Options:");
            System.out.println("  -gc (MarkSweep|Explicit|NoGC)");
            System.out.println("  -heapsize BYTES");
            System.out.println("BYTES must be a multiple of the word size (8)");
            return;
        }

        Program astRoot = null;
        Reader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        try {
            astRoot = ParserWrapper.parse(reader);
        } catch (Exception ex) {
            ex.printStackTrace();
            Interpreter.fatalError("Uncaught parsing error: " + ex, Interpreter.EXIT_PARSING_ERROR);
        }
        //astRoot.println(System.out);
        interpreter = new Interpreter(astRoot);
        interpreter.initMemoryManager(gcType, heapBytes);
       
        String returnValueAsString = interpreter.executeRoot(astRoot, quandaryArg).toString();
        System.out.println("Interpreter returned " + returnValueAsString);
    }

    final Program astRoot;
    final Random random;

    private Interpreter(Program astRoot) {
        this.astRoot = astRoot;
        this.random = new Random();
    }

    void initMemoryManager(String gcType, long heapBytes) {
        if (gcType.equals("Explicit")) {
            throw new RuntimeException("Explicit not implemented");            
        } else if (gcType.equals("MarkSweep")) {
            throw new RuntimeException("MarkSweep not implemented");            
        } else if (gcType.equals("RefCount")) {
            throw new RuntimeException("RefCount not implemented");            
        } else if (gcType.equals("NoGC")) {
            // Nothing to do
        }
    }
    
    Object executeRoot(Program astRoot, long arg) {
        return evaluate(astRoot.getFuncDefList(),arg);
    }
  
   
    Object evaluate(FuncDefList funcDefList, long arg) {
        ArrayList<Long> args = new ArrayList<>();
        args.add(arg);
        FuncDef mainFunc = funcDefList.getFuncDef();
        String id = funcDefList.getFuncDef().getVarDecl().getId().getIdent();
        if (id.equals("main")) {
            mainFunc = funcDefList.getFuncDef();
        }
        function.put(id, funcDefList.getFuncDef());
        System.out.println(funcDefList.getFuncDef());
        // Build a map of function definitions.
        while (funcDefList.getFuncDefList() != null) {
            funcDefList = funcDefList.getFuncDefList();
            id = funcDefList.getFuncDef().getVarDecl().getId().getIdent();
            System.out.println(id);
            if (id.equals("main")) {
                mainFunc = funcDefList.getFuncDef();
            }
            function.put(id, funcDefList.getFuncDef());
        }
        
        // If a 'main' function was found, evaluate it.
        if (mainFunc.getVarDecl().getId().getIdent().equals("main")) {
            
            return evaluate(mainFunc, args, new HashMap<String, Long>());
        } else {
            throw new RuntimeException("No main method found");
        }
    }
    
    Object evaluate(FuncDef funcDef, ArrayList<Long> args, Map<String, Long> variablesMap) {
       if (funcDef.getFormalDeclList() != null) {
            evaluate(funcDef.getFormalDeclList(), args, variablesMap);
        }
        return evaluate(funcDef.getStmtList(),variablesMap);
    }
    Object evaluate(StmtList stmtList, Map<String, Long> variablesMap){
        Object stmt = evaluate(stmtList.getStmt(), variablesMap);
        if (returnFlag) return stmt;
        while (stmtList.getStmtList() != null)
        {
            stmtList = stmtList.getStmtList();
            stmt = evaluate(stmtList.getStmt(),variablesMap);
            if(returnFlag == true)
            {
                break;
            }
        }
        return stmt;
    }
    Object evaluate(Type type){
        return evaluateType(type);
    }

    void evaluate(FormalDeclList formalDeclList, ArrayList<Long> args, Map<String, Long> variablesMap){
        evaluate(formalDeclList.getNeFormalDeclListNode(), args, variablesMap);
    }
    void evaluate(NeFormalDeclList neFormalDeclList, ArrayList<Long> arg, Map<String, Long> variablesMap){
        int idx = 0;
        if (idx + 1 > arg.size()) {
            throw new RuntimeException("Not enough arguments");
        }
        variablesMap.put(neFormalDeclList.getVarDecl().getId().getIdent(), arg.get(idx));
        while (neFormalDeclList.getNeFormalDeclListNode() != null) {
            neFormalDeclList = neFormalDeclList.getNeFormalDeclListNode();
            idx++;
            if (idx + 1 > arg.size()) {
                throw new RuntimeException("Not enough arguments");
            }
            variablesMap.put(neFormalDeclList.getVarDecl().getId().getIdent(), arg.get(idx));
        }
    }
    /* 
    Object evaluate(ExprList exprList, Map<String, Long> variablesMap){
        Object value = evaluate(exprList.getneExprList(),variablesMap);
       if (exprList.getneExprList() != null){
           return evaluate(exprList.getneExprList(),variablesMap);   
       }
       return value;
    }
    */
    /* 
    Object evaluate(NeExprList neExprList, Map<String, Long> variablesMap){
        Object firstExprValue = evaluate(neExprList.getExpr(),variablesMap);
    
        if (neExprList.getneExprList() != null) {
            return evaluate(neExprList.getneExprList(), variablesMap);
        }
        return firstExprValue; 
    }
    */

    Boolean evaluateCond(Cond cond,Map<String, Long> variablesMap){
           switch(cond.getConditionOperator()){
                case Cond.LESSTHANOREQUAL: return (long)evaluate(cond.getExpr1(),variablesMap) <= (long)evaluate(cond.getExpr2(),variablesMap);
                case Cond.GREATERTHANOREQUAL: return (long)evaluate(cond.getExpr1(),variablesMap) >= (long)evaluate(cond.getExpr2(),variablesMap);
                case Cond.EQUALTO: return (long)evaluate(cond.getExpr1(),variablesMap) == (long)evaluate(cond.getExpr2(),variablesMap);
                case Cond.NOTEQUALTO: return (long)evaluate(cond.getExpr1(),variablesMap) != (long)evaluate(cond.getExpr2(),variablesMap);
                case Cond.LESSTHAN: return (long)evaluate(cond.getExpr1(),variablesMap) < (long)evaluate(cond.getExpr2(),variablesMap);
                case Cond.GREATERTHAN: return (long)evaluate(cond.getExpr1(),variablesMap) > (long)evaluate(cond.getExpr2(),variablesMap);
                case Cond.AND: return evaluateCond((Cond)cond.getExpr1(),variablesMap) && evaluateCond((Cond)cond.getExpr2(),variablesMap);
                case Cond.OR: return evaluateCond((Cond)cond.getExpr1(),variablesMap) || evaluateCond((Cond)cond.getExpr2(),variablesMap);
                case Cond.NOT: return !(evaluateCond((Cond)cond.getExpr1(),variablesMap));

                default: throw new RuntimeException("Unhandled operator");
              
           }
    
    }
    Object evaluate(Stmt stmt, Map<String, Long> variablesMap){
        if (stmt instanceof DeclarationStmt){
            DeclarationStmt declStmt = (DeclarationStmt)stmt;
            String varName = declStmt.getVarDecl().getId().toString();
            Object value = evaluate(declStmt.getExpression(),variablesMap);
            variablesMap.put(varName, (Long)value);
            return value;
        }
        else if (stmt instanceof AssignmentStmt) {
            AssignmentStmt assignStmt = (AssignmentStmt)stmt;
            String varName = assignStmt.getIdNode().toString();
            Object value = evaluate(assignStmt.getExprNode(),variablesMap);
            // Check if variable is declared, if not, declare it
            if (!variablesMap.containsKey(varName)) {
                variablesMap.put(varName, (Long)value);
            }
            // Assign value to variable
            variablesMap.put(varName, (Long)value);
            return value;
        } else if (stmt instanceof IfStatement) {
            IfStatement ifStatement = (IfStatement)stmt;
            Boolean condition = evaluateCond(ifStatement.getCondNode(),variablesMap);
            Object value = null;
            if (condition){
                value = evaluate(ifStatement.getStmtNode(), variablesMap);
            }
            return value;
        }
         
        else if (stmt instanceof IfElseStmt){
            IfElseStmt ifElseStmt = (IfElseStmt)stmt;
        
            Boolean condition = evaluateCond(ifElseStmt.getCondNode(),variablesMap);
            System.out.println(condition);
            
            Object value = null;
            if (condition){
                value = evaluate(ifElseStmt.getStmtNode1(), variablesMap);
            } else {
                value = evaluate(ifElseStmt.getStmtNode2(),variablesMap);
            }
            return value;
        }
        
         else if (stmt instanceof WhileStmt) {
            WhileStmt whileStmt = (WhileStmt)stmt;
            Boolean condition = evaluateCond(whileStmt.getCondNode(),variablesMap);
            Object value = evaluate(whileStmt.getStmtNode(),variablesMap);

            while (!condition) {
                value = evaluate(whileStmt.getStmtNode(),variablesMap);
                condition = evaluateCond(whileStmt.getCondNode(),variablesMap);
            }
            return value;
        }
        else if (stmt instanceof CallStmt) {
            CallStmt callStmt = (CallStmt)stmt;
            //Object value = evaluate(callStmt.getExprList(),variablesMap);
            return null;
        }
        
         else if (stmt instanceof PrintStmt) {
            PrintStmt printStmt = (PrintStmt)stmt;
            Object value = evaluate(printStmt.getExpression(),variablesMap);
            System.out.println(value);
            return value;
        }else if (stmt instanceof ReturnStmt) {
            ReturnStmt returnStmt = (ReturnStmt)stmt;
            returnFlag = true;
            Object value = evaluate(returnStmt.getExpression(),variablesMap);
            return value;
        }else if (stmt instanceof BlockStmt) {
            BlockStmt blockStmt = (BlockStmt)stmt;
            Object value = evaluate(blockStmt.getStmtListNode(), variablesMap);
            return value;
        }
         else {
            throw new RuntimeException("Unhandled Stmt type");
        }
    }

    Object evaluate(Expr expr, Map<String, Long> variablesMap) {
        if (expr instanceof ConstExpr) {
            return ((ConstExpr)expr).getValue();
        } else if (expr instanceof BinaryExpr) {
            BinaryExpr binaryExpr = (BinaryExpr)expr;
            switch (binaryExpr.getOperator()) {
                case BinaryExpr.PLUS: return (Long)evaluate(binaryExpr.getLeftExpr(),variablesMap) + (Long)evaluate(binaryExpr.getRightExpr(),variablesMap);
                case BinaryExpr.MINUS: return (Long)evaluate(binaryExpr.getLeftExpr(),variablesMap) - (Long)evaluate(binaryExpr.getRightExpr(),variablesMap);
                case BinaryExpr.TIMES: return (Long)evaluate(binaryExpr.getLeftExpr(),variablesMap) * (Long)evaluate(binaryExpr.getRightExpr(),variablesMap);
                default: throw new RuntimeException("Unhandled operator");
            }
        } else if (expr instanceof UnaryMinusExpr){
            Expr child = ((UnaryMinusExpr)expr).getExpr();
            long value = (long) evaluate(child, variablesMap);
            long newValue = -value;
            return newValue;
        } else if(expr instanceof IdentExpr){            
            return variablesMap.get(((IdentExpr)expr).getIdent());
        } else if(expr instanceof CallExpr){
            ArrayList<Long> args = new ArrayList<>();
            if (((CallExpr)expr).getExprList() != null) {
                NeExprList neExprList = ((CallExpr)expr).getExprList().getneExprList();
                args.add((long)evaluate(neExprList.getExpr(), variablesMap));
                while (neExprList.getneExprList() != null) {
                    neExprList = neExprList.getneExprList();
                    args.add((long)evaluate(neExprList.getExpr(), variablesMap));
                }
            }
            if (((CallExpr)expr).getId().equals("randomInt")) {
                Random random = new Random();
                return (long)random.nextInt((args.get(0)).intValue());
            }
            Map<String, Long> tempMap = new HashMap<>(variablesMap);
            return evaluate(function.get(((CallExpr)expr).getId()), args, tempMap);

           
            
        
           // return evaluate(function.get(functionName).getStmtList());
        }
        else {
            throw new RuntimeException("Unhandled Expr type");
        }
    }
    Object evaluateType(Type type){
        return type.getType();
    }
	public static void fatalError(String message, int processReturnCode) {
        System.out.println(message);
        System.exit(processReturnCode);
	}
}
