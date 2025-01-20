/*
 * Einfache Testf채lle f체r statische und dynamische Polymorphie in C++
 *
 */


class A {
public:     // es reicht, wenn alles public ist (hier nur, damit das Beispiel mit g++ kompiliert)
    int aval;
    A() { aval = 99; }
    A(int x) { aval = x; }

    virtual void foo() { print_char('A'); print_char('f'); print_int(aval); }

};

class B : public A {
public:     // es reicht, wenn alles public ist (hier nur, damit das Beispiel mit g++ kompiliert)
    int bval;
    B() { bval = 77; }
    B(int x) { bval = x; }

    // 체berschriebene Methode aus A
    virtual void foo() { print_char('B'); print_char('f'); print_int(aval); print_int(bval); }

    // eigene Methode
    virtual void bar() { print_char('B'); print_char('b'); print_int(aval); print_int(bval); }

};

class C : public B {
public:     // es reicht, wenn alles public ist (hier nur, damit das Beispiel mit g++ kompiliert)
    int cval;
    C(int x) { cval = x; }

    // 체berschriebene Methode aus B
    void foo() { print_char('C'); print_char('f'); print_int(aval); print_int(bval); print_int(cval); }

    // eigene Methode
    void bar() { print_char('C'); print_char('b'); print_int(aval); print_int(bval); print_int(cval); }

};


int main() {
    {
        // Statische Polymorphie (Normalfall)
        print_char(' '); print_char('A'); print_char(' ');  // 'A'
        print_char('\n') ;

        B b(9);
        A a = b;

        b.foo();    // B, f, 99, 9
        print_char('\n') ;
        b.bar();    // B, b, 99, 9
        print_char('\n') ;

        a.foo();    // A, f, 99         => statische Polymorphie
        print_char('\n') ;
//        a.bar();    // nicht erlaubt!
    }

    {
        // Statische Polymorphie (trotz Basisklassenreferenz)
        print_char(' '); print_char('B'); print_char(' ');  // 'B'
        print_char('\n') ;

        B b(9);
        A &a = b;

        b.foo();    // B, f, 99, 9
        print_char('\n') ;
        b.bar();    // B, b, 99, 9
        print_char('\n') ;

        a.foo();    // A, f, 99         => statische Polymorphie
        print_char('\n') ;
    }

    {
        // Dynamische Polymorphie (Basisklassenreferenz und virtuelle Methode)
        print_char(' '); print_char('C'); print_char(' ');  // 'C'
        print_char('\n') ;

        C c(9);
        B &b = c;

        c.foo();    // C, f, 99, 77, 9
        print_char('\n');
        c.bar();    // C, b, 99, 77, 9
        print_char('\n');

        b.foo();    // C, f, 99, 77, 9      => dynamische Polymorphie
        print_char('\n') ;
        b.bar();    // B, b, 99, 77         => statische Polymorphie
        print_char('\n') ;
    }


    return 0;
}