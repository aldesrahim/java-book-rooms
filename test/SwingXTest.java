
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.painter.BusyPainter;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author aldes
 */
public class SwingXTest {

    private static SwingXTest app;

    private void init() {
        JXFrame frame = new JXFrame("test", true);
        JXBusyLabel label = new JXBusyLabel();
        frame.add(label);
        //...
        label.setBusy(true);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        app = new SwingXTest();
        app.init();
    }

    public class MyBusyLabel extends JXBusyLabel {

        public MyBusyLabel(Dimension prefSize) {
            super(prefSize);
        }
    }
}
