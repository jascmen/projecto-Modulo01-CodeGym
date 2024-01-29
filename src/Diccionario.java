import java.util.HashSet;
import java.util.Set;

public class Diccionario {

    private Set<String> diccionario = new HashSet<>(){{
        add("hola");
        add("como");
        add("estas");
        add("amor");
    }};

    public Set<String> getDiccionario() {
        return diccionario;
    }
}
