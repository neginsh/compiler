import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import gen.*;

public class Compiler {

    public static void main(String[] args) throws IOException{
        CharStream stream = CharStreams.fromFileName("E:\\Files\\College Files\\Session 6\\Compiler\\Project\\Phase 2\\JythonCompiler\\compiler\\src\\input\\codes.txt");
        jythonLexer lexer = new jythonLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        jythonParser parser = new jythonParser(tokens);
        ParseTree tree = parser.program();
        ParseTreeWalker walker = new ParseTreeWalker();
        jythonListener listener = new jythonBaseListener();
        walker.walk(listener, tree);
    }

}
