import AST.ASTNode;
import java.io.IOException;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Main {
  public static void main(String... args) throws IOException {
    String iteration = "First"; // First, Second, Third or Fourth
    String filename = "src/testInput/" + iteration + "Iteration.txt";
    ANTLRInputStream input = new ANTLRFileStream(filename);
    CppLexer lexer = new CppLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    CppParser parser = new CppParser(tokens);
    ParseTree tree = parser.program();
    ASTBuilder astBuilder = new ASTBuilder();
    ASTNode node = astBuilder.visit(tree);
  }
}
