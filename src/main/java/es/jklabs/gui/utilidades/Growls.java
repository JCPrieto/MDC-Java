package es.jklabs.gui.utilidades;

import es.jklabs.utilidades.Constantes;
import es.jklabs.utilidades.Logger;
import es.jklabs.utilidades.Mensajes;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class Growls {

    private static final String NOTIFY_SEND = "notify-send";
    private static TrayIcon trayIcon;
    private static Boolean notifySendAvailable;

    private Growls() {

    }

    public static void mostrarError(String cuerpo, Exception e) {
        mostrarError(null, cuerpo, e);
    }

    public static void mostrarError(String titulo, String cuerpo, Exception e) {
        if (trayIcon != null) {
            trayIcon.displayMessage(titulo != null ? Mensajes.getMensaje(titulo) : null, Mensajes.getError(cuerpo), TrayIcon.MessageType.ERROR);
        } else if (isNotifySendAvailable()) {
            try {
                Runtime.getRuntime().exec(new String[]{NOTIFY_SEND,
                        titulo != null ? Mensajes.getMensaje(titulo) : Constantes.NOMBRE_APP,
                        Mensajes.getError(cuerpo),
                        "--icon=dialog-error"});
            } catch (IOException e2) {
                Logger.error(e2);
            }
        } else {
            String dialogTitle = titulo != null ? Mensajes.getMensaje(titulo) : Constantes.NOMBRE_APP;
            JOptionPane.showMessageDialog(null, Mensajes.getError(cuerpo), dialogTitle, JOptionPane.ERROR_MESSAGE);
        }
        Logger.error(cuerpo, e);
    }

    public static void init() {
        trayIcon = null;
    }

    private static boolean isNotifySendAvailable() {
        if (notifySendAvailable != null) {
            return notifySendAvailable;
        }
        String osName = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
        if (!osName.contains("linux")) {
            notifySendAvailable = false;
            return false;
        }
        String path = System.getenv("PATH");
        if (path == null || path.isBlank()) {
            notifySendAvailable = false;
            return false;
        }
        String[] dirs = path.split(java.util.regex.Pattern.quote(File.pathSeparator));
        for (String dir : dirs) {
            if (dir == null || dir.isBlank()) {
                continue;
            }
            File candidate = new File(dir, NOTIFY_SEND);
            if (candidate.isFile() && candidate.canExecute()) {
                notifySendAvailable = true;
                return true;
            }
        }
        notifySendAvailable = false;
        return false;
    }
}
