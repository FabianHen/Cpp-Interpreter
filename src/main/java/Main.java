import AST.ASTNode;
import java.io.IOException;
import java.util.List;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Main {
  public static void printTree(ASTNode node, String indent, boolean isLast) {
    // Ausgabe des aktuellen Knotens mit Einrückung und Verzweigungssymbol
    System.out.print(indent);
    if (isLast) {
      System.out.print("L__ "); // Letzter Knoten auf der aktuellen Ebene
      indent += "    ";
    } else {
      System.out.print("|-- "); // Weitere Knoten auf der aktuellen Ebene
      indent += "|   ";
    }
    System.out.println(node);
    // Rekursives Durchlaufen der Kinder des aktuellen Knotens
    List<ASTNode> children = node.getChildren();
    for (int i = 0; i < children.size(); i++) {
      printTree(children.get(i), indent, i == children.size() - 1);
    }
  }

  public static void main(String... args) throws IOException {
    String iteration = "First"; // First, Second, Third or Fourth
    String filename = "src/testInput/grammarTest_cpp.txt"; // + iteration + "Iteration.txt";
    ANTLRInputStream input = new ANTLRFileStream(filename);
    CppLexer lexer = new CppLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    CppParser parser = new CppParser(tokens);
    ParseTree tree = parser.program();
    ASTBuilder astBuilder = new ASTBuilder();
    ASTNode node = astBuilder.visit(tree);
    printTree(node, "", true);
  }
}
