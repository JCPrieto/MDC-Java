package es.jklabs.utilidades;

public class Constantes {

    public static final String NOMBRE_APP = "M.C.D. Java";
    public static final String VERSION = resolveVersion();

    private Constantes() {

    }

    private static String resolveVersion() {
        String version = Constantes.class.getPackage().getImplementationVersion();
        return version != null ? version : "dev";
    }
}
