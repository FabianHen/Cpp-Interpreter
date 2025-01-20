# Cpp-Compiler
* ## Basisdatentypen: bool, int, char
    * Vollständig umgesetzt
    * Standartwerte (false, 0, ' ') automatisch festgelegt bei Deklaration 
* ## Variablen
    * Vollständig umgesetzt
    * Deklaration (mit und ohne Zuweisung)
    * Bindings
    * Typechecks
* ## Arrays
    * Vollständig umgesetzt
    * Unendlich mehrdimensional
    * Automatisch gefüllt mit null
    * Passende Dimensionen und Typen werden bei Definition überprüft
    * Array access wird gecheckt -> Eigener ```Index out of bounds``` Error
* ## C++-Referenzen
    * Vollständig umgesetzt
* ## Zuweisungen und Expressions
    * Operatoren ```++``` und ```--``` eingebaut => Dabei Unterscheidung von ```i++``` und ```++i``` eingebaut
    * Verschiedene Zuweisungs-operatoren (```+=```, ```-=```, ```*=```, ```/=```) umgesetzt
    * Rechnungen nur mit Integern möglich
* ## Kontrollfluss: if-then-else, while-Schleifen
    * Vollständig umgesetzt
    * ```Else if``` zudem auch möglich
    * Vergleichsoperator ```==``` und ```!=``` eingebaut => vergleich dabei nur mit built in types möglich
    * Konkatenation von conditions durch ```&&``` und ```||``` möglich

* ## Funktionen (Definition, Deklaration, Aufrufe)
    * Vollständig umgesetzt
    * Deklaration und anschließende Definition
    * Überladung 
    * Überschreibung ausschließlich von ```virtual``` methoden (anders als im Beispiel)
        * Rein virtuelle Methoden (abstrakte Klassen und Templates) wurden nicht umgesetzt
    * Optionales ```override``` keyword => zwingt zu überschreiben
    * ```Return``` statements werden nur auf korrekten Typen gecheckt (nicht in jedem Pfad einer Methode muss ein return vorkommen) 
    * ```Return``` statements beenden nur den aktuellen Block und nicht die komplette Funktion
    * Ist ein ```Return``` im root scope einer Funktion vorhanden funktioniert dieses wie vorgesehen
* ## Klassen (mit Attributen und Methoden)
    * Vollständig umgesetzt
    * zugriff auf Klassenvariablen und Funktionen
    * Big 3:
        * Standart Constructor: Überladung, Aufruf von Super-Constructors, Aufruf durch: ```A a;```, ```A a()``` wie in cpp nicht erlaubt. Konstruktoraufruf mit Parametern mit  ```A a(x, y, ...);```
        * Copy Constructor: normaler Aufruf nur möglich bei überschriebenen Copy Constructor, sonst durch ```A a = b;``` aufrufbar. Bei dieser aufruf Art ist ein standart Copy Constructor umgesetzt.
        * Zuweisungsoperator : Aufruf möglich durch ```a = b;```, kann überschrieben werden, standart vorhanden
    * Variablen und Funktionen müssen Klassen intern vor Nutzung deklariert sein, anderst als in cpp
* ## Einfach-Vererbung
    * Vollständig umgesetzt
    * Vererbung von Funktionen und Attributen
    * Überschreibung von Funktionen aus allen Oberklassen
* ## Polymorphie (dynamisch, statisch)
    * polymorphie dynamisch und statisch vollständig umgesetzt 
    * Verhalten unterscheidet sich von dem des Beispiels, entspricht jedoch dem Verhalten des standart cpp compilers (Fehler in den test files?)
    Beispiel:
        ``` C c(9);
        B &b = c;

        b.foo();    // C, f, 99, 77, 9      => dynamische Polymorphie
        b.bar();    // B, b, 99, 77         => statische Polymorphie 

    Ausgabe von uns: Cf99779**Cb**99779 (cpp Compiler hat die gleiche Ausgabe)

* ## Eingebaute Funktionen: print_bool, print_int, print_char 
    * Vollständig als print umgesetzt =>  für new line muss man '\n' printen
    * print_bool() printed anstatt von ```0``` oder ```1``` ```true``` oder ```false```

* ## Allgemeines
    * Interpreter startet nur bei erfolgteichem Durchlauf des STBuilders (Semantische Analyse)
    * Sematische Analyse (Typecheck und Aufbau der Symboltabelle) wird in einem Durchlauf bearbeitet 