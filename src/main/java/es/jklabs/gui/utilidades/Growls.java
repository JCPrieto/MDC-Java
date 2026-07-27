package es.jklabs.gui.utilidades;

import com.sshtools.twoslices.*;
import es.jklabs.utilidades.Constantes;
import es.jklabs.utilidades.Logger;
import es.jklabs.utilidades.Mensajes;

import javax.swing.*;

public class Growls {

    private static final DesktopNotifier NOTIFIER = new TwoSlicesDesktopNotifier();

    private Growls() {

    }

    public static void mostrarError(String cuerpo, Exception e) {
        mostrarError(null, cuerpo, e);
    }

    public static void mostrarError(String titulo, String cuerpo, Exception e) {
        String resolvedTitle = titulo != null ? Mensajes.getMensaje(titulo) : Constantes.NOMBRE_APP;
        String resolvedBody = Mensajes.getError(cuerpo);
        try {
            NOTIFIER.show(resolvedTitle, resolvedBody);
        } catch (RuntimeException notificationError) {
            Logger.error(notificationError);
            JOptionPane.showMessageDialog(null, resolvedBody, resolvedTitle, JOptionPane.ERROR_MESSAGE);
        }
        Logger.error(cuerpo, e);
    }

    public static void init() {
        ToasterSettings settings = new ToasterSettings()
                .setAppName(Constantes.NOMBRE_APP)
                .setTimeout(10);
        ToasterFactory.setSettings(settings);
    }

    private interface DesktopNotifier {
        void show(String title, String body);
    }

    private static class TwoSlicesDesktopNotifier implements DesktopNotifier {
        @Override
        public void show(String title, String body) {
            ToastBuilder builder = Toast.builder()
                    .type(ToastType.ERROR)
                    .title(title)
                    .content(body);
            builder.toast();
        }
    }
}
