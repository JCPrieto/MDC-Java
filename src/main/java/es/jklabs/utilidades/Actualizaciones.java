package es.jklabs.utilidades;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Actualizaciones {

    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();
    private static final Pattern TAG_PATTERN = Pattern.compile("\"tag_name\"\\s*:\\s*\"([^\"]+)\"");
    private static final Pattern ZIP_PATTERN = Pattern.compile("\"browser_download_url\"\\s*:\\s*\"([^\"]+\\.zip)\"");

    private Actualizaciones() {

    }

    public static ReleaseInfo fetchLatestRelease() throws IOException, InterruptedException {
        String apiUrl = String.format("https://api.github.com/repos/%s/%s/releases/latest",
                Constantes.GITHUB_OWNER, Constantes.GITHUB_REPO);
        HttpRequest request = HttpRequest.newBuilder(URI.create(apiUrl))
                .header("Accept", "application/vnd.github+json")
                .header("User-Agent", "MCDJava")
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (response.statusCode() != 200) {
            throw new IOException("Unexpected response: " + response.statusCode());
        }
        String body = response.body();
        String tag = matchFirst(TAG_PATTERN, body);
        if (tag == null) {
            return null;
        }
        String zipUrl = matchFirst(ZIP_PATTERN, body);
        return new ReleaseInfo(normalizeVersion(tag), zipUrl);
    }

    public static Path downloadReleaseZip(String url, Path targetDir) throws IOException, InterruptedException {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("Missing download URL");
        }
        if (targetDir == null || !Files.isDirectory(targetDir)) {
            throw new IllegalArgumentException("Invalid target directory");
        }
        String filename = extractFileName(url);
        Path target = targetDir.resolve(filename);
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .header("Accept", "application/octet-stream")
                .header("User-Agent", "MCDJava")
                .build();
        HttpResponse<Path> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofFile(target));
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IOException("Download failed: " + response.statusCode());
        }
        return response.body();
    }

    public static boolean isNewer(String candidate, String current) {
        return compareVersions(candidate, current) > 0;
    }

    private static int compareVersions(String left, String right) {
        int[] leftParts = toVersionParts(left);
        int[] rightParts = toVersionParts(right);
        int max = Math.max(leftParts.length, rightParts.length);
        for (int i = 0; i < max; i++) {
            int l = i < leftParts.length ? leftParts[i] : 0;
            int r = i < rightParts.length ? rightParts[i] : 0;
            if (l != r) {
                return Integer.compare(l, r);
            }
        }
        return 0;
    }

    private static int[] toVersionParts(String version) {
        if (version == null || version.isBlank()) {
            return new int[]{0};
        }
        String normalized = normalizeVersion(version);
        String[] parts = normalized.split("\\.");
        int[] values = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            values[i] = parseLeadingInt(parts[i]);
        }
        return values;
    }

    private static int parseLeadingInt(String part) {
        if (part == null) {
            return 0;
        }
        StringBuilder digits = new StringBuilder();
        for (int i = 0; i < part.length(); i++) {
            char c = part.charAt(i);
            if (!Character.isDigit(c)) {
                break;
            }
            digits.append(c);
        }
        if (digits.length() == 0) {
            return 0;
        }
        return Integer.parseInt(digits.toString());
    }

    private static String normalizeVersion(String raw) {
        if (raw == null) {
            return "";
        }
        String trimmed = raw.trim();
        if (trimmed.startsWith("v") || trimmed.startsWith("V")) {
            trimmed = trimmed.substring(1);
        }
        return trimmed;
    }

    private static String matchFirst(Pattern pattern, String text) {
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group(1) : null;
    }

    private static String extractFileName(String url) {
        try {
            String path = URI.create(url).getPath();
            if (path != null && !path.isBlank()) {
                int idx = path.lastIndexOf('/');
                if (idx >= 0 && idx + 1 < path.length()) {
                    return path.substring(idx + 1);
                }
            }
        } catch (IllegalArgumentException ignored) {
            // fallback below
        }
        return "MCDJava-release.zip";
    }

    public static final class ReleaseInfo {
        private final String version;
        private final String zipUrl;

        public ReleaseInfo(String version, String zipUrl) {
            this.version = version;
            this.zipUrl = zipUrl;
        }

        public String getVersion() {
            return version;
        }

        public String getZipUrl() {
            return zipUrl;
        }
    }
}
