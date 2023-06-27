/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.application.forms.masterroom;

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
import main.application.components.HeaderWithButton;
import main.application.components.NumberInputField;
import main.application.components.NumberInputGroup;
import main.application.components.TextInputGroup;
import main.application.forms.FormType;
import main.model.Facility;
import main.model.FacilityRoom;
import main.model.Room;
import main.model.Type;
import main.model.combobox.TypeComboBoxItem;
import main.service.RoomService;
import main.service.other.Response;
import main.util.Dialog;
import main.util.validation.Validation;
import main.util.validation.ValidationItem;
import main.util.validation.rule.RuleNotEmpty;
import main.util.validation.rule.RuleUnique;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author aldes
 */
public class MasterRoomInputForm extends JPanel {

    private Room room = new Room();
    private FormType formType = FormType.CREATE;
    private List<Facility> facilities = new ArrayList<>();
    private final RoomService service = new RoomService();

    private List<Long> facilityIds = new ArrayList<>();
    private Map<Long, FacilityRoom> facilityRoomMap = new HashMap<>();

    private boolean isReadOnly = false;

    public MasterRoomInputForm() {
        init();
    }

    public MasterRoomInputForm(Room room, FormType formType) {
        this.room = room;
        this.formType = formType;

        this.isReadOnly = formType.equals(FormType.VIEW);

        initFacilityRoomMap();

        init();
        populateForm();
    }

    private void init() {
        setLayout(new BorderLayout());

        bodyPanel = new BodyPanel();

        JPanel panel = bodyPanel.getPanel();

        initFacilities();
        initHeader(panel);
        initForm(panel);
        initFormEvent();

        add(bodyPanel, BorderLayout.CENTER);

    }

    private void initFacilities() {
        try {
            facilities = new Facility().all();
        } catch (SQLException ex) {
            //
        }
    }

    private void initHeader(JPanel panel) {
        header = new HeaderWithButton((ActionEvent ae) -> {
            goBack();
        });

        header.setTitleText(formType.getTitle("Gedung dan Ruangan"));
        header.setButtonText("Kembali");
        panel.add(header, "wrap, growx");
    }

    private void initForm(JPanel panel) {
        formPanel = new CardPanel(); // ini kotak biru

        JPanel inputPanel = new JPanel(new MigLayout("ins 0, wrap 4, hidemode 3", "[]10[]"));
        inputPanel.setOpaque(false);

        groupId = new TextInputGroup();
        groupId.setTitleText("ID");
        groupId.getInputField().setEnabled(false);
        groupId.getInputField().putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Auto-generate oleh sistem");
        inputPanel.add(groupId);

        cbType = new ComboBoxInputGroup<>();
        cbType.setTitleText("Tipe");
        inputPanel.add(cbType);
        initTypeComboBoxItem();

        groupName = new TextInputGroup();
        groupName.setTitleText("Nama");
        inputPanel.add(groupName);

        groupCapacity = new NumberInputGroup();
        groupCapacity.setTitleText("Kapasitas");
        inputPanel.add(groupCapacity, "wrap");

        groupDescription = new TextInputGroup();
        groupDescription.setTitleText("Deskripsi");
        inputPanel.add(groupDescription, "growx, span 4");

        formPanel.add(inputPanel, "wrap");

        initFacilitiesForm(formPanel);

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

    private void initTypeComboBoxItem() {

        try {
            List<Type> types = new Type().all();

            for (Type type : types) {
                TypeComboBoxItem item = new TypeComboBoxItem(type);
                
                cbType.getInputField().addItem(item);

                if (room.getTypeId() != null && item.getModel().getId().equals(room.getTypeId())) {
                    cbType.getInputField().setSelectedItem(item);
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

    private void initFacilitiesForm(JPanel formPanel) {
        JPanel inputPanel = new JPanel(new MigLayout("ins 0, wrap 2, hidemode 3"));
        inputPanel.setOpaque(false);

        JLabel label = new JLabel("Fasilitas");
        inputPanel.add(label, "wrap");

        facilityItem = new ArrayList<>();
        facilityItemQty = new ArrayList<>();

        for (int i = 0; i < facilities.size(); i++) {
            facilityItemStatus.add(false);

            final int ii = i;

            Facility facility = facilities.get(i);

            JPanel facilityItemPanel = new JPanel(new MigLayout("", "[]rel[]"));
            facilityItemPanel.setOpaque(false);

            JCheckBox item = new JCheckBox(facility.getName());
            facilityItem.add(item);

            NumberInputField itemQty = new NumberInputField(0);
            itemQty.setEnabled(false);
            itemQty.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Qty");
            facilityItemQty.add(itemQty);

            // events
            item.addItemListener((ItemEvent e) -> {
                boolean isSelected = e.getStateChange() == ItemEvent.SELECTED;

                facilityItemStatus.set(ii, isSelected);
                facilityItemQty.get(ii).setEnabled(isSelected);
            });

            facilityItemPanel.add(item, "pushx");
            facilityItemPanel.add(itemQty);

            inputPanel.add(facilityItemPanel, "growx");

            if (!formType.equals(FormType.CREATE)) {
                boolean isInArray = facilityIds.contains(facility.getId());

                item.setSelected(isInArray);
                itemQty.setText(isInArray ? facilityRoomMap.get(facility.getId()).getQty().toString() : "0");
                
                if (isReadOnly && !isInArray) {
                    facilityItemPanel.setVisible(false);
                }
            }
            
            if (isReadOnly) {
                item.setEnabled(false);
                itemQty.setEnabled(false);
            }
        }

        formPanel.add(inputPanel, "wrap, gaptop 10");
    }

    private void clearForm() {
        groupId.getInputField().setText("");
        groupName.getInputField().setText("");
        groupCapacity.getInputField().setText("");
        groupDescription.getInputField().setText("");

        groupId.hideError();
        groupName.hideError();
        groupCapacity.hideError();
        groupDescription.hideError();

        for (int i = 0; i < facilityItem.size(); i++) {
            JCheckBox item = facilityItem.get(i);
            NumberInputField itemQty = facilityItemQty.get(i);

            item.setSelected(false);
            itemQty.setText("0");
        }
    }

    private void initReadOnly() {
        groupId.getInputField().setEnabled(false);
        groupName.getInputField().setEnabled(false);
        groupCapacity.getInputField().setEnabled(false);
        groupDescription.getInputField().setEnabled(false);

        groupId.hideError();
        groupName.hideError();
        groupCapacity.hideError();
        groupDescription.hideError();

        cbType.getInputField().setEnabled(false);
    }

    private void save() {
        boolean isInsert = formType.equals(FormType.CREATE);

        String id = groupId.getInputValue();
        String name = groupName.getInputValue();
        String capacity = groupCapacity.getInputValue();
        String description = groupDescription.getInputValue();
        Long typeId = ((TypeComboBoxItem) cbType.getInputField().getSelectedItem()).getModel().getId();

        List<FacilityRoom> facilityRoom = new ArrayList<>();

        for (int i = 0; i < facilityItemStatus.size(); i++) {
            if (facilityItemStatus.get(i)) {
                FacilityRoom item = new FacilityRoom();
                item.setFacilityId(facilities.get(i).getId());
                item.setQty(Integer.valueOf(facilityItemQty.get(i).getText()));

                facilityRoom.add(item);
            }
        }

        if (!id.isEmpty()) {
            room.setId(Long.valueOf(id));
        }

        if (isInsert) {
            room.setId(null);
        }

        room.setTypeId(typeId);
        room.setName(name);
        room.setCapacity(capacity.isEmpty() ? 0 : Integer.valueOf(capacity));
        room.setDescription(description);

        try {
            Validation formValidation = new Validation()
                    .addItem(new ValidationItem(groupName)
                            .addRule(new RuleNotEmpty())
                            .addRule(new RuleUnique("rooms", "name", isInsert ? null : id))
                    );

            if (!isInsert) {
                formValidation.addItem(new ValidationItem(groupId)
                        .addRule(new RuleNotEmpty()));
            }

            if (!formValidation.validate()) {
                throw new Exception(formValidation.getErrorMessageString());
            }

            Response response = service.save(room, facilityRoom);

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
        groupId.getInputField().setText(room.getId().toString());
        groupName.getInputField().setText(room.getName());
        groupCapacity.getInputField().setText(room.getCapacity().toString());
        groupDescription.getInputField().setText(room.getDescription());

        if (isReadOnly) {
            initReadOnly();
        }
    }

    private void initFacilityRoomMap() {
        int size = room.getFacilityRoom().size();
        for (int i = 0; i < size; i++) {
            FacilityRoom fr = room.getFacilityRoom().get(i);
            facilityIds.add(fr.getFacilityId());
            facilityRoomMap.put(fr.getFacilityId(), fr);
        }
    }

    private void goBack() {
        Application.showForm(new MasterRoomForm());
    }

    private BodyPanel bodyPanel;
    private HeaderWithButton header;
    private CardPanel formPanel;
    private TextInputGroup groupName;
    private NumberInputGroup groupCapacity;
    private TextInputGroup groupDescription;
    private TextInputGroup groupId;
    private Button cmdSave;
    private Button cmdClear;

    private ComboBoxInputGroup<TypeComboBoxItem> cbType;
    private List<Boolean> facilityItemStatus = new ArrayList<>();
    private List<JCheckBox> facilityItem;
    private List<NumberInputField> facilityItemQty;
}
