/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main.application.forms.other;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.util.UIScale;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import main.application.components.Button;
import main.application.components.CardPanel;
import main.application.components.DateInputField;
import main.application.components.Header;
import main.model.Reservation;
import main.service.DashboardService;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author aldes
 */
public class DashboardForm extends javax.swing.JPanel {

    private DashboardService service = new DashboardService();

    /**
     * Creates new form DefaultForm
     */
    public DashboardForm() {
        System.out.println("Dashboard init");

        initComponents();
        init();
    }

    private void init() {
        JPanel panel = bodyPanel.getPanel();

        Header header = new Header();
        header.setTitleText("Dashboard");
        panel.add(header, "wrap");

        initCounter(panel);
        initSearchPanel(panel);
    }

    private void initCounter(JPanel mainPanel) {
        JPanel panel = new JPanel(new MigLayout("ins 0, wrap 3, fillx", "[]15[]"));

        panel.add(createCounterCard("RESERVASI HARI INI", service.todayReservationCount()), "grow");
        panel.add(createCounterCard("RESERVASI BULAN INI", service.thisMonthReservationCount()), "grow");
        panel.add(createCounterCard("RESERVASI TAHUN INI", service.thisYearReservationCount()), "grow");

        mainPanel.add(panel, "wrap, grow");
    }

    private void initSearchPanel(JPanel mainPanel) {
        JPanel panel = new JPanel(new MigLayout("ins 0, wrap, fillx, hidemode 3"));

        JLabel lbTitle = new JLabel("Cek Reservasi");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h3.regular.font;");
        panel.add(lbTitle, "wrap, gapy 15 0");

        JPanel searchPanel = new JPanel(new MigLayout("ins 0", "[]rel:10[]"));
        searchPanel.setOpaque(false);

        DateInputField datePicker = new DateInputField();
        datePicker.setPreferredSize(UIScale.scale(new Dimension(200, 31)));
        datePicker.setMinimumSize(UIScale.scale(new Dimension(200, 31)));

        Button cmdCheck = new Button();
        cmdCheck.setText("Cek");

        CardPanel emptyResultPanel = new CardPanel();
        emptyResultPanel.setLayout(new BorderLayout());
        emptyResultPanel.setVisible(false);

        JLabel lbEmptyResult = new JLabel("Reservasi tidak ditemukan untuk tanggal yang dipilih");
        lbEmptyResult.setHorizontalAlignment(JLabel.CENTER);
        lbEmptyResult.setBorder(new EmptyBorder(15, 0, 15, 0));
        emptyResultPanel.add(lbEmptyResult, BorderLayout.CENTER);

        JPanel resultPanel = new JPanel(new MigLayout("ins 0, wrap 4, fillx, hidemode 3", "[]10[]", "[]10[]"));
        resultPanel.setOpaque(false);
        resultPanel.setVisible(false);

        searchPanel.add(datePicker);
        searchPanel.add(cmdCheck);
        panel.add(searchPanel, "wrap, grow, gapy 0 15");
        panel.add(emptyResultPanel, "wrap, grow");
        panel.add(resultPanel, "wrap, grow");

        mainPanel.add(panel, "wrap, grow");

        cmdCheck.addActionListener((ActionEvent ae) -> {
            resultPanel.removeAll();

            List<Reservation> reservations = service.getReservations(datePicker.getDate());

            if (!reservations.isEmpty()) {
                emptyResultPanel.setVisible(false);
                resultPanel.setVisible(true);

                for (Reservation reservation : reservations) {
                    resultPanel.add(createReservationCard(reservation), "grow");
                }
            } else {
                emptyResultPanel.setVisible(true);
                resultPanel.setVisible(false);
            }
        });
    }

    private CardPanel createReservationCard(Reservation reservation) {
        DateFormat dateFormat = new SimpleDateFormat("EEEEE, dd MMMMM yyyy HH:mm", new Locale("id", "ID"));

        CardPanel panel = new CardPanel();
        panel.setLayout(new MigLayout("ins 15, fillx, hidemode 3, wrap"));

        JLabel lbRoomName = new JLabel(reservation.getRoom().getName());
        lbRoomName.putClientProperty(FlatClientProperties.STYLE, "font:bold $h2.font");

        JLabel lbStatusInfo = new JLabel("Status");
        lbStatusInfo.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        JLabel lbStatus = new JLabel(reservation.getStatus().toString());

        JLabel lbBookedDateInfo = new JLabel("Tanggal Booking");
        lbBookedDateInfo.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        JLabel lbBookedDate1 = new JLabel(dateFormat.format(reservation.getStartedAt()));
        JLabel lbBookedDate2 = new JLabel(dateFormat.format(reservation.getEndedAt()));

        JLabel lbCheckedInDateInfo = new JLabel("Tanggal Check In");
        lbCheckedInDateInfo.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        JLabel lbCheckedInDate = new JLabel(reservation.getCheckedInAt() != null ? dateFormat.format(reservation.getCheckedInAt()) : "-");

        JLabel lbCheckedOutDateInfo = new JLabel("Tanggal Check Out");
        lbCheckedOutDateInfo.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        JLabel lbCheckedOutDate = new JLabel(reservation.getCheckedOutAt() != null ? dateFormat.format(reservation.getCheckedOutAt()) : "-");

        JLabel lbNameInfo = new JLabel("Instansi/Penyelenggara");
        lbNameInfo.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        JLabel lbName = new JLabel(reservation.getName());

        JLabel lbPhoneNumberInfo = new JLabel("No.Telp");
        lbPhoneNumberInfo.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        JLabel lbPhoneNumber = new JLabel(reservation.getPhoneNumber());

        JLabel lbSubjectInfo = new JLabel("Tema Kegiatan");
        lbSubjectInfo.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        JLabel lbSubject = new JLabel(reservation.getSubject());

        panel.add(lbRoomName, "gapy 0 10");
        panel.add(lbStatusInfo);
        panel.add(lbStatus);
        panel.add(lbBookedDateInfo);
        panel.add(lbBookedDate1);
        panel.add(lbBookedDate2);
        panel.add(lbCheckedInDateInfo);
        panel.add(lbCheckedInDate);
        panel.add(lbCheckedOutDateInfo);
        panel.add(lbCheckedOutDate);
        panel.add(lbNameInfo);
        panel.add(lbName);
        panel.add(lbPhoneNumberInfo);
        panel.add(lbPhoneNumber);
        panel.add(lbSubjectInfo);
        panel.add(lbSubject);

        return panel;
    }

    private CardPanel createCounterCard(String title, String data) {
        CardPanel panel = new CardPanel();
        panel.setLayout(new MigLayout("ins 15, fillx, hidemode 3"));

        JLabel lbTitle = new JLabel(title);
        JLabel lbData = new JLabel(data);

        lbTitle.setHorizontalAlignment(JLabel.CENTER);
        lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h2.font;");

        lbData.setHorizontalAlignment(JLabel.CENTER);
        lbData.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold $h1.font;");

        panel.add(lbTitle, "wrap, grow");
        panel.add(lbData, "wrap, grow");

        return panel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bodyPanel = new main.application.components.BodyPanel();

        setLayout(new java.awt.BorderLayout());
        add(bodyPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private main.application.components.BodyPanel bodyPanel;
    // End of variables declaration//GEN-END:variables
}
