package main.application.forms;

import com.formdev.flatlaf.util.UIScale;
import java.awt.Component;
import javax.swing.JPanel;
import main.application.Application;
import main.menu.Menu;
import main.menu.MenuItem;
import main.menu.MenuType;
import main.model.User;

/**
 *
 * @author aldes
 */
public class MainForm extends javax.swing.JPanel {

    private final User authUser;
    
    /**
     * Creates new form DefaultForm
     */
    public MainForm(User authUser) {
        this.authUser = authUser;
        
        initComponents();
        init();

        setSelectedMenu(0);
    }

    private void init() {
        int menuWidth = UIScale.scale(280);

        initMenuEvent();

        add(menu, "west, wmin " + menuWidth);
        add(panelBody, "grow");
    }

    private void initMenuEvent() {
        menu.addEvent((int menuIndex) -> {
            MenuItem item = (MenuItem) Menu.menus[menuIndex];

            Object com = item.getComponent();

            if (com != null && com instanceof JPanel) {
                showForm((JPanel) com);
            }

            if (item.getType().equals(MenuType.LOGOUT)) {
                Application.logout();
            }
        });
    }

    public void setSelectedMenu(int menuIndex) {
        menu.setSelectedMenu(menuIndex);
    }

    public void showForm(Component component) {
        panelBody.removeAll();
        panelBody.add(component);
        panelBody.repaint();
        panelBody.revalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBody = new javax.swing.JPanel();
        menu = new main.menu.Menu();

        panelBody.setLayout(new java.awt.BorderLayout());

        net.miginfocom.swing.MigLayout migLayout1 = new net.miginfocom.swing.MigLayout();
        migLayout1.setLayoutConstraints("fill");
        migLayout1.setColumnConstraints("[grow,fill]");
        setLayout(migLayout1);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private main.menu.Menu menu;
    private javax.swing.JPanel panelBody;
    // End of variables declaration//GEN-END:variables
}
