package es.jklabs.gui.utilidades;

import es.jklabs.utilidades.Constantes;
import es.jklabs.utilidades.Logger;
import es.jklabs.utilidades.Mensajes;

import java.awt.*;
import java.io.IOException;

public class Growls {

    private static final String NOTIFY_SEND = "notify-send";
    private static TrayIcon trayIcon;

    private Growls() {

    }

    public static void mostrarError(String cuerpo, Exception e) {
        mostrarError(null, cuerpo, e);
    }

    public static void mostrarError(String titulo, String cuerpo, Exception e) {
        if (trayIcon != null) {
            trayIcon.displayMessage(titulo != null ? Mensajes.getMensaje(titulo) : null, Mensajes.getError(cuerpo), TrayIcon.MessageType.ERROR);
        } else {
            try {
                Runtime.getRuntime().exec(new String[]{NOTIFY_SEND,
                        titulo != null ? Mensajes.getMensaje(titulo) : Constantes.NOMBRE_APP,
                        Mensajes.getError(cuerpo),
                        "--icon=dialog-error"});
            } catch (IOException e2) {
                Logger.error(e2);
            }
        }
        Logger.error(cuerpo, e);
    }

    public static void init() {
        trayIcon = null;
        //ToDo Comentamos hasta que tengamos un icono
        /*if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
            SystemTray tray = SystemTray.getSystemTray();
            trayIcon = new TrayIcon(new ImageIcon(Objects.requireNonNull(Growls.class.getClassLoader().getResource
                    ("img/icons/s3-bucket.png"))).getImage(), Constantes.NOMBRE_APP);
            trayIcon.setImageAutoSize(true);
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                Logger.error("establecer.icono.systray", e);
            }
        }*/
    }
}
