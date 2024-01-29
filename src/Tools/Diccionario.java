package Tools;

import java.util.HashSet;
import java.util.Set;

public class Diccionario {

    private Set<String> diccionario = new HashSet<>(){{
        add("empresa");
        add("Agroindustrias");
        add("principales");
        add("beneficio");
        add("productos");
        add("agroindustriales");
        add("alimentarios");
        add("beneficios");
    }};

    public Set<String> getDiccionario() {
        return diccionario;
    }
}
