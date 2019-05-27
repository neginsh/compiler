import classes.SymbolTable;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.IOException;
import gen.*;

public class Compiler {

    public static void main(String[] args) throws IOException{
        final File folder = new File("compiler/src/inputs");
        SymbolTable table = new SymbolTable();
        for (final File fileEntry : folder.listFiles()) {
            CharStream stream = CharStreams.fromFileName(fileEntry.getAbsolutePath());
            jythonLexer lexer = new jythonLexer(stream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            jythonParser parser = new jythonParser(tokens);
            ParseTree tree = parser.program();
            ParseTreeWalker walker = new ParseTreeWalker();
            jythonListener listener = new jythonBaseListener(table);
            walker.walk(listener, tree);
        }
    }

}
