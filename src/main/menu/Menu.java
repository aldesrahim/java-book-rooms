package main.menu;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import main.application.forms.masterconsumption.MasterConsumptionForm;
import main.application.forms.masterfacility.MasterFacilityForm;
import main.application.forms.masterroom.MasterRoomForm;
import main.application.forms.mastertype.MasterTypeForm;
import main.application.forms.masteruser.MasterUserForm;
import main.application.forms.other.DashboardForm;
import main.application.forms.reservation.ReservationForm;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author aldes
 */
public class Menu extends javax.swing.JPanel {

    private final List<MenuEvent> events = new ArrayList<>();

    public static Object[] menus = {
        new MenuItem(MenuType.BUTTON, "Dashboard", DashboardForm.class.getName(), "dashboard"),
        new MenuItem(MenuType.TITLE, "MASTER"),
        new MenuItem(MenuType.BUTTON, "Master Tipe", MasterTypeForm.class.getName(), "file"),
        new MenuItem(MenuType.BUTTON, "Master Fasilitas", MasterFacilityForm.class.getName(), "file"),
        new MenuItem(MenuType.BUTTON, "Master Gedung dan Ruangan", MasterRoomForm.class.getName(), "file"),
        new MenuItem(MenuType.BUTTON, "Master Konsumsi", MasterConsumptionForm.class.getName(), "file"),
        new MenuItem(MenuType.BUTTON, "Master Pengguna", MasterUserForm.class.getName(), "file"),
        new MenuItem(MenuType.TITLE, "RESERVASI"),
        new MenuItem(MenuType.BUTTON, "Daftar Reservasi", ReservationForm.class.getName(), "file"),
        new MenuItem(MenuType.TITLE, "LAPORAN"),
        new MenuItem(MenuType.BUTTON, "Laporan Reservasi").setIconName("file"),
        new MenuItem(MenuType.BUTTON, "Laporan Gedung dan Ruangan").setIconName("file"),
        new MenuItem(MenuType.BUTTON, "Laporan Konsumsi").setIconName("file"),
        new MenuItem(MenuType.BUTTON, "Laporan Log Aktivitas").setIconName("file"),
        new MenuItem(MenuType.SEPARATOR),
        new MenuItem(MenuType.LOGOUT, "Logout").setIconName("logout"),};

    /**
     * Creates new form Menu
     */
    public Menu() {
        initComponents();

        putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Menu.background;");

        header.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h1.font;"
                + "border:25,0,25,0;");
        scroll.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:null;"
                + "background:$Menu.background;");

        panelMenu.setLayout(new MigLayout("wrap,fillx", "[grow,fill]"));
        panelMenu.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5;"
                + "background:$Menu.background");

        JScrollBar vscroll = scroll.getVerticalScrollBar();
        vscroll.setUnitIncrement(10);
        vscroll.putClientProperty(FlatClientProperties.STYLE, ""
                + "width:$Menu.scroll.width;"
                + "trackInsets:$Menu.scroll.trackInsets;"
                + "thumbInsets:$Menu.scroll.thumbInsets;"
                + "background:$Menu.ScrollBar.background;"
                + "thumb:$Menu.ScrollBar.thumb");

        createMenus();

//        add(header, "north");
        add(scroll, "grow");
    }

    public void runEvent(int menuIndex) {
        for (MenuEvent event : events) {
            event.selectedIndex(menuIndex);
        }

        MenuItem item = (MenuItem) menus[menuIndex];

        if (item.getType().equals(MenuType.BUTTON)) {
            setSelected(menuIndex);
        }
    }

    public void addEvent(MenuEvent event) {
        events.add(event);
    }

    public void setSelectedMenu(int menuIndex) {
        runEvent(menuIndex);
    }

    public void setSelected(int menuIndex) {
        int size = panelMenu.getComponentCount();
        for (int i = 0; i < size; i++) {
            Component com = panelMenu.getComponent(i);
            if (com instanceof JButton) {
                JButton menu = (JButton) com;
                menu.setSelected(i == menuIndex);
            }
        }
    }

    private void createMenus() {
        int size = menus.length;
        for (int i = 0; i < size; i++) {
            Object item = menus[i];

            if (item instanceof MenuItem) {
                MenuItem menuItem = (MenuItem) item;

                switch (menuItem.getType()) {
                    case TITLE -> {
                        JLabel label = createTitle(menuItem.getTitle());

                        if (menuItem.getIcon() != null) {
                            label.setIcon((Icon) menuItem.getIcon());
                        }

                        panelMenu.add(label);
                    }
                    case BUTTON, LOGOUT -> {
                        final int menuIndex = i;

                        JButton button = createButtonItem(menuItem.getTitle());

                        if (menuItem.getIcon() != null) {
                            button.setIcon((Icon) menuItem.getIcon());
                        }

                        button.addActionListener(l -> {
                            runEvent(menuIndex);
                        });

                        panelMenu.add(button);
                    }
                    case SEPARATOR ->
                        panelMenu.add(createSeparatorItem());
                }
            }
        }
    }

    private JLabel createTitle(String title) {
        JLabel lbTitle = new JLabel(title);
        lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                + "foreground:$Menu.title.foreground;"
                + "border:10,11,0,11");
        return lbTitle;
    }

    private JLabel createSeparatorItem() {
        JLabel separator = new JLabel();
        separator.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:10,11,0,11");
        return separator;
    }

    private JButton createButtonItem(String text) {
        JButton button = new JButton(text);
        button.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Menu.background;"
                + "foreground:$Menu.foreground;"
                + "selectedBackground:$Menu.button.selectedBackground;"
                + "selectedForeground:$Menu.button.selectedForeground;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "arc:10;"
                + "iconTextGap:10;"
                + "margin:5,11,5,11");
        button.setHorizontalAlignment(JButton.LEFT);

        return button;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        header = new javax.swing.JLabel();
        scroll = new javax.swing.JScrollPane();
        panelMenu = new javax.swing.JPanel();

        header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        header.setText("BookRM");

        scroll.setBorder(null);
        scroll.setViewportView(panelMenu);

        net.miginfocom.swing.MigLayout migLayout1 = new net.miginfocom.swing.MigLayout();
        migLayout1.setLayoutConstraints("wrap,fill,inset 0 0 0 0");
        migLayout1.setColumnConstraints("[grow,fill]");
        setLayout(migLayout1);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel header;
    private javax.swing.JPanel panelMenu;
    private javax.swing.JScrollPane scroll;
    // End of variables declaration//GEN-END:variables
}
