package main.application;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.fonts.inter.FlatInterFont;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.SwingUtilities;
import main.application.forms.LoginForm;
import main.application.forms.MainForm;
import main.model.User;

public class Application extends javax.swing.JFrame {

    private static Application app;
    private MainForm mainForm;
    private LoginForm loginForm;

    private final static User authUser = new User();

    /**
     * Creates new form Application
     */
    public Application() {
        initComponents();
        setSize(new Dimension(1200, 768));
        setLocationRelativeTo(null);

        mainForm = new MainForm(authUser);
        loginForm = new LoginForm(authUser);

        setContentPane(loginForm);
//        setContentPane(mainForm);
    }

    public static void showForm(Component component) {
        app.mainForm.showForm(component);
    }

    public static boolean isAuthenticated() {
        return Application.authUser.getId() != null;
    }

    public static User getAuthUser() {
        return Application.authUser;
    }

    public static void setAuthUser(User authUser) {
        Application.authUser.setId(authUser.getId());
        Application.authUser.setName(authUser.getName());
        Application.authUser.setUsername(authUser.getUsername());
        Application.authUser.setPassword(authUser.getPassword());
    }

    public static void login() {
        FlatAnimatedLafChange.showSnapshot();
        setSelectedMenu(0);
        app.setContentPane(app.mainForm);
        app.repaint();
        app.revalidate();
        SwingUtilities.updateComponentTreeUI(app.mainForm);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    public static void logout() {
        Application.authUser.setId(null);
        
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.loginForm);
        app.repaint();
        app.revalidate();
        SwingUtilities.updateComponentTreeUI(app.loginForm);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    public static void setSelectedMenu(int index) {
        app.mainForm.setSelectedMenu(index);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SMI Gedung dan Ruangan");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1368, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 768, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatRobotoFont.install();
        FlatInterFont.install();

        FlatLaf.setPreferredFontFamily(FlatRobotoFont.FAMILY);
        FlatLaf.setPreferredLightFontFamily(FlatRobotoFont.FAMILY_LIGHT);
        FlatLaf.setPreferredSemiboldFontFamily(FlatRobotoFont.FAMILY_SEMIBOLD);

        FlatLaf.registerCustomDefaultsSource("main.theme");
        FlatDarculaLaf.setup();
//        FlatIntelliJLaf.setup();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                app = new Application();
                app.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
