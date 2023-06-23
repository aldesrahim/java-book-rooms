/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.application.forms.reservation;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import main.application.Application;
import main.application.components.BodyPanel;
import main.application.components.Button;
import main.application.components.CardPanel;
import main.application.components.ComboBoxInputGroup;
import main.application.components.DateTimeInputGroup;
import main.application.components.HeaderWithButton;
import main.application.components.NumberInputField;
import main.application.components.NumberInputGroup;
import main.application.components.TextInputGroup;
import main.application.forms.FormType;
import main.model.Consumption;
import main.model.ConsumptionReservation;
import main.model.Reservation;
import main.model.Room;
import main.model.combobox.RoomComboBoxItem;
import main.service.ReservationService;
import main.service.other.Response;
import main.util.Dialog;
import main.util.validation.Validation;
import main.util.validation.ValidationItem;
import main.util.validation.rule.RuleNotEmpty;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author aldes
 */
public class ReservationInputForm extends JPanel {

    private Reservation reservation = new Reservation();
    private FormType formType = FormType.CREATE;
    private List<Consumption> consumptions = new ArrayList<>();
    private final ReservationService service = new ReservationService();

    private List<Long> consumptionIds = new ArrayList<>();
    private Map<Long, ConsumptionReservation> consumptionReservationMap = new HashMap<>();

    private boolean isReadOnly = false;

    public ReservationInputForm() {
        init();
    }

    public ReservationInputForm(Reservation reservation, FormType formType) {
        this.reservation = reservation;
        this.formType = formType;

        this.isReadOnly = formType.equals(FormType.VIEW);

        initConsumptionReservationMap();
        
        init();
        populateForm();
    }

    private void init() {
        setLayout(new BorderLayout());

        bodyPanel = new BodyPanel();

        JPanel panel = bodyPanel.getPanel();

        initConsumptions();
        initHeader(panel);
        initForm(panel);
        initFormEvent();

        add(bodyPanel, BorderLayout.CENTER);

    }

    private void initConsumptions() {
        try {
            consumptions = new Consumption().all();
        } catch (SQLException ex) {
            //
        }
    }

    private void initHeader(JPanel panel) {
        header = new HeaderWithButton((ActionEvent ae) -> {
            goBack();
        });

        header.setTitleText(formType.getTitle("Reservasi"));
        header.setButtonText("Kembali");
        panel.add(header, "wrap, growx");
    }

    private void initForm(JPanel panel) {
        formPanel = new CardPanel();

        JPanel inputPanel = new JPanel(new MigLayout("ins 0, wrap 4, hidemode 3", "[]10[]"));
        inputPanel.setOpaque(false);

        groupId = new TextInputGroup();
        groupId.setTitleText("ID");
        groupId.getInputField().setEnabled(false);
        groupId.setVisible(!formType.equals(FormType.CREATE));
        inputPanel.add(groupId);

        cbRoom = new ComboBoxInputGroup<>();
        cbRoom.setTitleText("Gedung/Ruangan");
        inputPanel.add(cbRoom);
        initRoomComboBoxItem();

        groupName = new TextInputGroup();
        groupName.setTitleText("Nama");
        inputPanel.add(groupName);

        groupPhoneNumber = new NumberInputGroup();
        groupPhoneNumber.setTitleText("No. Telp");
        inputPanel.add(groupPhoneNumber);

        groupAttendance = new NumberInputGroup();
        groupAttendance.setTitleText("Jumlah Peserta");
        inputPanel.add(groupAttendance);

        groupStartedAt = new DateTimeInputGroup();
        groupStartedAt.setTitleText("Dari Tanggal (Booking)");
        inputPanel.add(groupStartedAt);

        groupEndedAt = new DateTimeInputGroup();
        groupEndedAt.setTitleText("Sampai Tanggal (Booking)");
        inputPanel.add(groupEndedAt);

        groupSubject = new TextInputGroup();
        groupSubject.setTitleText("Tema Kegiatan");
        inputPanel.add(groupSubject);

        formPanel.add(inputPanel, "wrap");

        initConsumptionsForm(formPanel);

        JPanel buttonPanel = new JPanel(new MigLayout("ins 2, hidemode 3", "[]10[]"));
        buttonPanel.setOpaque(false);

        cmdSave = new Button();
        cmdSave.setText("Simpan");
        cmdSave.setVisible(!formType.equals(FormType.VIEW));
        buttonPanel.add(cmdSave);

        cmdClear = new Button();
        cmdClear.setText("Bersihkan");
        cmdClear.setVisible(formType.equals(FormType.CREATE));
        buttonPanel.add(cmdClear);

        formPanel.add(buttonPanel, "wrap, right");

        panel.add(formPanel, "wrap, growx");
    }

    private void initRoomComboBoxItem() {

        try {
            List<Room> rooms = new Room().all();

            for (Room room : rooms) {
                RoomComboBoxItem item = new RoomComboBoxItem(room);
                
                cbRoom.getInputField().addItem(item);
                
                if (reservation.getRoomId() != null && item.getModel().getId().equals(reservation.getRoomId())) {
                    cbRoom.getInputField().setSelectedItem(item);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private void initFormEvent() {
        cmdSave.addActionListener((ae) -> {
            save();
        });
        cmdClear.addActionListener((ae) -> {
            clearForm();
        });
    }

    private void initConsumptionsForm(JPanel panel) {
        JPanel inputPanel = new JPanel(new MigLayout("ins 0, wrap"));
        inputPanel.setOpaque(false);

        JLabel label = new JLabel("Konsumsi");
        inputPanel.add(label, "wrap");

        consumptionItem = new ArrayList<>();
        consumptionItemQty = new ArrayList<>();

        for (int i = 0; i < consumptions.size(); i++) {
            consumptionItemStatus.add(false);

            final int ii = i;

            Consumption consumption = consumptions.get(i);

            JPanel consumptionItemPanel = new JPanel(new MigLayout("", "[]rel[]"));
            consumptionItemPanel.setOpaque(false);

            JCheckBox item = new JCheckBox(consumption.getName());
            consumptionItem.add(item);

            NumberInputField itemQty = new NumberInputField(0);
            itemQty.setEnabled(false);
            itemQty.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Qty");
            consumptionItemQty.add(itemQty);

            // events
            item.addItemListener((ItemEvent e) -> {
                boolean isSelected = e.getStateChange() == ItemEvent.SELECTED;

                consumptionItemStatus.set(ii, isSelected);
                consumptionItemQty.get(ii).setEnabled(isSelected);
            });

            consumptionItemPanel.add(item, "pushx");
            consumptionItemPanel.add(itemQty);

            inputPanel.add(consumptionItemPanel, "growx");

            if (!formType.equals(FormType.CREATE)) {
                boolean isInArray = consumptionIds.contains(consumption.getId());

                item.setSelected(isInArray);
                itemQty.setText(isInArray ? consumptionReservationMap.get(consumption.getId()).getQty().toString() : "0");
                
                if (isReadOnly && !isInArray) {
                    consumptionItemPanel.setVisible(false);
                }
            }
            
            if (isReadOnly) {
                item.setEnabled(false);
                itemQty.setEnabled(false);
            }
        }

        panel.add(inputPanel, "wrap, gaptop 10");
    }

    private void clearForm() {
        groupId.getInputField().setText("");
        groupName.getInputField().setText("");
        groupPhoneNumber.getInputField().setText("");
        groupSubject.getInputField().setText("");

        groupId.hideError();
        groupName.hideError();
        groupPhoneNumber.hideError();
        groupSubject.hideError();

        for (int i = 0; i < consumptionItem.size(); i++) {
            JCheckBox item = consumptionItem.get(i);
            NumberInputField itemQty = consumptionItemQty.get(i);

            item.setSelected(false);
            itemQty.setText("0");
        }
    }

    private void readOnly() {
        groupId.getInputField().setEnabled(false);
        groupName.getInputField().setEnabled(false);
        groupPhoneNumber.getInputField().setEnabled(false);
        groupSubject.getInputField().setEnabled(false);

        groupId.hideError();
        groupName.hideError();
        groupPhoneNumber.hideError();
        groupSubject.hideError();

        for (int i = 0; i < consumptionItem.size(); i++) {
            JCheckBox item = consumptionItem.get(i);
            NumberInputField itemQty = consumptionItemQty.get(i);

            item.setEnabled(false);
            itemQty.setEnabled(false);
        }

        cbRoom.getInputField().setEnabled(false);
    }

    private void save() {
        boolean isInsert = formType.equals(FormType.CREATE);

        String id = groupId.getInputValue();
        String name = groupName.getInputValue();
        Long roomId = ((RoomComboBoxItem) cbRoom.getInputField().getSelectedItem()).getModel().getId();
        String attendance = groupAttendance.getInputValue();
        String startedAt = groupStartedAt.getInputValue();
        String endedAt = groupEndedAt.getInputValue();
        String phoneNumber = groupPhoneNumber.getInputValue();
        String subject = groupSubject.getInputValue();

        List<ConsumptionReservation> consumptionReservation = new ArrayList<>();

        for (int i = 0; i < consumptionItemStatus.size(); i++) {
            if (consumptionItemStatus.get(i)) {
                ConsumptionReservation item = new ConsumptionReservation();
                item.setConsumptionId(consumptions.get(i).getId());
                item.setQty(Integer.valueOf(consumptionItemQty.get(i).getText()));

                consumptionReservation.add(item);
            }
        }

        if (!id.isEmpty()) {
            reservation.setId(Long.valueOf(id));
        }

        if (isInsert) {
            reservation.setId(null);
        }

        reservation.setRoomId(roomId);
        reservation.setName(name);
        reservation.setPhoneNumber(phoneNumber);
        reservation.setSubject(subject);
        reservation.setAttendance(Integer.valueOf(attendance));
        reservation.setStartedAt(groupStartedAt.getTimestamp());
        reservation.setEndedAt(groupEndedAt.getTimestamp());

        try {
            Validation formValidation = new Validation()
                    .addItem(new ValidationItem(groupName)
                            .addRule(new RuleNotEmpty())
                    )
                    .addItem(new ValidationItem(groupAttendance)
                            .addRule(new RuleNotEmpty())
                    )
                    .addItem(new ValidationItem(groupPhoneNumber)
                            .addRule(new RuleNotEmpty())
                    )
                    .addItem(new ValidationItem(groupStartedAt)
                            .addRule(new RuleNotEmpty())
                    )
                    .addItem(new ValidationItem(groupEndedAt)
                            .addRule(new RuleNotEmpty())
                    );

            if (!isInsert) {
                formValidation.addItem(new ValidationItem(groupId)
                        .addRule(new RuleNotEmpty()));
            }

            if (!formValidation.validate()) {
                throw new Exception(formValidation.getErrorMessageString());
            }

            if (!service.isDateAvailable(roomId, startedAt, endedAt)) {
                throw new Exception("Gedung/Ruangan ini tidak tersedia untuk tanggal yang dipilih");
            }

            Response response = service.save(reservation, consumptionReservation);

            if (!response.isSuccess()) {
                throw new Exception(response.getMessage());
            }

            Dialog dialog = new Dialog("Perhatian");
            dialog.setMessageType(JOptionPane.INFORMATION_MESSAGE);
            dialog.setMessage(response.getMessage());
            dialog.setOptionType(JOptionPane.DEFAULT_OPTION);
            dialog.show(getRootPane());

            clearForm();
            goBack();
        } catch (Exception ex) {
            Dialog dialog = new Dialog("Perhatian");
            dialog.setMessageType(JOptionPane.ERROR_MESSAGE);
            dialog.setMessage(ex.getMessage());
            dialog.setOptionType(JOptionPane.DEFAULT_OPTION);
            dialog.show(getRootPane());
        }
    }

    private void populateForm() {
        groupId.getInputField().setText(reservation.getId().toString());
        groupName.getInputField().setText(reservation.getName());
        groupPhoneNumber.getInputField().setText(reservation.getPhoneNumber());
        groupAttendance.getInputField().setText(reservation.getAttendance().toString());
        groupStartedAt.getInputField().setDate(reservation.getStartedAt());
        groupEndedAt.getInputField().setDate(reservation.getEndedAt());
        groupSubject.getInputField().setText(reservation.getSubject());

        if (isReadOnly) {
            readOnly();
        }
    }

    private void initConsumptionReservationMap() {
        int size = reservation.getConsumptionReservation().size();
        for (int i = 0; i < size; i++) {
            ConsumptionReservation fr = reservation.getConsumptionReservation().get(i);
            consumptionIds.add(fr.getConsumptionId());
            consumptionReservationMap.put(fr.getConsumptionId(), fr);
        }
    }

    private void goBack() {
        Application.showForm(new ReservationForm()); // GO BACK
    }

    private BodyPanel bodyPanel;
    private HeaderWithButton header;
    private CardPanel formPanel;
    private TextInputGroup groupName;
    private NumberInputGroup groupPhoneNumber;
    private NumberInputGroup groupAttendance;
    private TextInputGroup groupSubject;
    private TextInputGroup groupId;
    private DateTimeInputGroup groupStartedAt;
    private DateTimeInputGroup groupEndedAt;
    private Button cmdSave;
    private Button cmdClear;

    private ComboBoxInputGroup<RoomComboBoxItem> cbRoom;
    private List<Boolean> consumptionItemStatus = new ArrayList<>();
    private List<JCheckBox> consumptionItem;
    private List<NumberInputField> consumptionItemQty;
}
