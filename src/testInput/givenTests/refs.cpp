/*
 * Einfache Testfälle für Referenzen in C++
 *
 */


int main() {
    bool a = true;
    int b = 42;
    char c = 'c';


    // Referenzen definieren
    bool &aa = a;
    int &bb = b;
    char &cc = c;

    print_bool(aa);  //  1
    print_char('\n');
    print_int(bb);   // 42
    print_char('\n');
    print_char(cc);  // 'c'
    print_char('\n');


    // Zugriff auf Objekte über die Referenz
    aa = false;
    bb = 7;
    cc = 'x';

    print_bool(a);  // 0
    print_char('\n');
    print_int(b);   // 7
    print_char('\n');
    print_char(c);  // 'x'
    print_char('\n');


    // Referenzen auf Referenzen
    bool &aaa = aa;

    aaa = true;
    print_bool(a);  // 1
    print_char('\n');


    // Re-Definition darf nicht akzeptiert werden
//    bool &a = bb;
//    int &b = aa;
//    char &c = cc;


    return 0;
}
