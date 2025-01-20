/*
 * Einfache Testfälle für Klassen in C++
 *
 */


class A {
public:     // es reicht, wenn alles public ist (hier nur, damit das Beispiel mit g++ kompiliert)
    int value;
};

class B {
public:     // es reicht, wenn alles public ist (hier nur, damit das Beispiel mit g++ kompiliert)
    int value;
    B() { value = 0; }
    B(int x) { value = x; }
    B(B& rhs) { value = rhs.value; }
    B& operator=( B& rhs) {
        (*this).value = rhs.value;    // "this" sollte erkannt werden
        return *this;               // dito braucht man ein "*this" ...
    }

};

class C {
public:     // es reicht, wenn alles public ist (hier nur, damit das Beispiel mit g++ kompiliert)
    int value;
    C() { value = 0; }
    C(int x) { value = x; }

};


int main() {
    {
        // Klasse mit Defaults (C'tor, Zuweisung)
        print_char('A');        // 'A'
        print_char('\n');

        A x;
        x.value = 9;
        print_int(x.value);     // 9
        print_char('\n');

        A y = x;
        print_int(x.value);     // 9
        print_char('\n');
        print_int(y.value);     // 9
        print_char('\n');

        y.value = 7;
        print_int(x.value);     // 9
        print_char('\n');
        print_int(y.value);     // 7
        print_char('\n');

        A z = y;
        print_int(x.value);     // 9
        print_char('\n');
        print_int(y.value);     // 7
        print_char('\n');
        print_int(z.value);     // 7
        print_char('\n');

        A foo;
        foo = z;
        z.value = 99;
        print_int(foo.value);   // 7
        print_char('\n');
        print_int(z.value);     // 99
        print_char('\n');
    }


    {
        // Klasse mit selbst implementierten C'toren und Zuweisung
        print_char('B');        // 'B'
        print_char('\n');

        B x;
        x.value = 9;
        print_int(x.value);     // 9
        print_char('\n');

        B y = x;
        print_int(x.value);     // 9
        print_char('\n');
        print_int(y.value);     // 9
        print_char('\n');

        y.value = 7;
        print_int(x.value);     // 9
        print_char('\n');
        print_int(y.value);     // 7
        print_char('\n');

        B z = y;
        print_int(x.value);     // 9
        print_char('\n');
        print_int(y.value);     // 7
        print_char('\n');
        print_int(z.value);     // 7
        print_char('\n');

        B foo;
        foo = z;
        z.value = 99;
        print_int(foo.value);   // 7
        print_char('\n');
        print_int(z.value);     // 99
        print_char('\n');
    }


    {
        // Klasse mit TEILWEISE selbst implementierten C'toren und Zuweisung, Rest Default vom Compiler
        print_char('C');        // 'C'
        print_char('\n');

        C x;
        x.value = 9;
        print_int(x.value);     // 9
        print_char('\n');

        C y = x;
        print_int(x.value);     // 9
        print_char('\n');
        print_int(y.value);     // 9
        print_char('\n');

        y.value = 7;
        print_int(x.value);     // 9
        print_char('\n');
        print_int(y.value);     // 7
        print_char('\n');

        C z = y;
        print_int(x.value);     // 9
        print_char('\n');
        print_int(y.value);     // 7
        print_char('\n');
        print_int(z.value);     // 7
        print_char('\n');

        C foo;
        foo = z;
        z.value = 99;
        print_int(foo.value);   // 7
        print_char('\n');
        print_int(z.value);     // 99
        print_char('\n');
    }


    return 0;
}
