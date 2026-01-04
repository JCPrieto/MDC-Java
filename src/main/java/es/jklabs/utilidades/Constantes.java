package es.jklabs.utilidades;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constantes {

    public static final String NOMBRE_APP = "M.C.D. Java";
    public static final String VERSION = resolveVersion();

    private static final String GROUP_ID = "es.jklabs.desktop";
    private static final String ARTIFACT_ID = "MCDJava";

    private Constantes() {

    }

    private static String resolveVersion() {
        String version = Constantes.class.getPackage().getImplementationVersion();
        if (isBlank(version)) {
            version = loadPomPropertiesVersion();
        }
        if (isBlank(version)) {
            version = loadPomXmlVersion();
        }
        return !isBlank(version) ? version : "dev";
    }

    private static String loadPomPropertiesVersion() {
        String path = String.format("META-INF/maven/%s/%s/pom.properties", GROUP_ID, ARTIFACT_ID);
        try (InputStream inputStream = Constantes.class.getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                return null;
            }
            Properties properties = new Properties();
            properties.load(inputStream);
            return trimToNull(properties.getProperty("version"));
        } catch (IOException e) {
            return null;
        }
    }

    private static String loadPomXmlVersion() {
        Path pomPath = Path.of("pom.xml");
        if (!Files.isRegularFile(pomPath)) {
            return null;
        }
        try {
            String content = Files.readString(pomPath, StandardCharsets.UTF_8);
            Pattern pattern = Pattern.compile(
                    "<artifactId>\\s*" + ARTIFACT_ID + "\\s*</artifactId>\\s*<version>\\s*([^<]+)\\s*</version>",
                    Pattern.DOTALL
            );
            Matcher matcher = pattern.matcher(content);
            if (matcher.find()) {
                return matcher.group(1).trim();
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
