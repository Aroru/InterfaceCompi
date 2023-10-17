import java.util.ArrayList;
import java.util.List;

class TablaSimbolos {
    private List<Symbol> symbols;

    public TablaSimbolos() {
        this.symbols = new ArrayList<>();
    }

    public void addSymbol(int line, String identifier, String dataType) {
        symbols.add(new Symbol(line, identifier, dataType));
    }

    public boolean symbolExists(String identifier) {
        for (Symbol symbol : symbols) {
            if (symbol.getIdentifier().equals(identifier)) {
                return true;
            }
        }
        return false;
    }

    

    // Clase interna Symbol
    private class Symbol {
        private int line; // línea donde se encuentra el símbolo.
        private String identifier; // nombre del identificador.
        private String dataType; // tipo de dato.

        public Symbol(int line, String identifier, String dataType) {
            this.line = line;
            this.identifier = identifier;
            this.dataType = dataType;
        }

        // Getters para Symbol
        public int getLine() {
            return line;
        }

        public String getIdentifier() {
            return identifier;
        }

        public String getDataType() {
            return dataType;
        }

        // Setters para Symbol
        public void setLine(int line) {
            this.line = line;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }
    }

    // Otros métodos útiles como la búsqueda de símbolos, eliminación, etc.
    

public void imprimirTabla() {
    System.out.println("---- Tabla de Símbolos ----");
    System.out.println("Linea | Identificador | Tipo de Dato");
    System.out.println("-----------------------------------");
    for (Symbol symbol : symbols) {
        System.out.println(symbol.getLine() + " | " + symbol.getIdentifier() + " | " + symbol.getDataType());
    }
}
}