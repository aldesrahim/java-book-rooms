/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main.application.forms.masteruser;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import main.application.Application;
import main.application.components.Button;
import main.application.components.CardPanel;
import main.application.components.PasswordInputGroup;
import main.application.components.TableWithPagination;
import main.application.components.TextInputGroup;
import main.application.components.table.cell.TableActionEvent;
import main.model.User;
import main.model.table.UserTableModel;
import main.service.UserService;
import main.service.other.Response;
import main.util.Dialog;
import main.util.Password;
import main.util.validation.Validation;
import main.util.validation.ValidationItem;
import main.util.validation.rule.RuleNotEmpty;
import main.util.validation.rule.RuleUnique;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author aldes
 */
public class MasterUserForm extends JPanel {

    private UserService service = new UserService();
    private UserTableModel model;
    private int atTableRowIndex;

    /**
     * Creates new form DefaultForm
     */
    public MasterUserForm() {
        initComponents();
        init();
    }

    private void init() {
        JPanel panel = bodyPanel.getPanel();

        header.setTitleText("Master Pengguna");
        panel.add(header, "growx, wrap");

        initForm(panel);
        initTable(panel);
        initFormEvent();
    }

    private void initTable(JPanel panel) {
        model = new UserTableModel();
        model.setTableAction(new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                edit(row);
            }

            @Override
            public void onDelete(int row) {
                delete(row);
            }

            @Override
            public void onView(int row) {
                //
            }

        });

        TableWithPagination table = new TableWithPagination(model);
        panel.add(table, "wrap, growx");
    }

    private void initForm(JPanel panel) {
        formPanel = new CardPanel();

        JPanel inputPanel = new JPanel(new MigLayout("ins 0, wrap 4", "[]10[]"));
        inputPanel.setOpaque(false);

        groupId = new TextInputGroup();
        groupId.setTitleText("ID");
        groupId.getInputField().setEnabled(false);
        inputPanel.add(groupId);

        groupName = new TextInputGroup();
        groupName.setTitleText("Nama");
        inputPanel.add(groupName);

        groupUsername = new TextInputGroup();
        groupUsername.setTitleText("Username");
        inputPanel.add(groupUsername);

        groupPassword = new PasswordInputGroup();
        groupPassword.setTitleText("Password");
        inputPanel.add(groupPassword);
        
        JLabel infoLabel = new JLabel("*) Hanya isi kolom password jika ingin mengganti password lama");
        inputPanel.add(infoLabel, "wrap, span 4");

        formPanel.add(inputPanel, "wrap");

        JPanel buttonPanel = new JPanel(new MigLayout("ins 2", "[]10[]"));
        buttonPanel.setOpaque(false);

        cmdSave = new Button();
        cmdSave.setText("Simpan");
        buttonPanel.add(cmdSave);

        cmdClear = new Button();
        cmdClear.setText("Bersihkan");
        buttonPanel.add(cmdClear);

        formPanel.add(buttonPanel, "wrap, right");

        panel.add(formPanel, "wrap, growx");
    }

    private void initFormEvent() {
        cmdClear.addActionListener(((ae) -> {
            clearForm();
        }));

        cmdSave.addActionListener(((ae) -> {
            save();
        }));
    }

    private void populateForm(User user) {
        clearForm();

        Long id = user.getId();

        if (id != null) {
            groupId.getInputField().setText(String.valueOf(user.getId()));
        }

        groupName.getInputField().setText(user.getName());
        groupUsername.getInputField().setText(user.getUsername());
    }

    private void clearForm() {
        groupId.getInputField().setText("");
        groupName.getInputField().setText("");
        groupUsername.getInputField().setText("");
        groupPassword.getInputField().setText("");

        groupId.hideError();
        groupName.hideError();
        groupUsername.hideError();
        groupPassword.hideError();
    }

    private void edit(int row) {
        User data = (User) model.find(row);
        populateForm(data);
        atTableRowIndex = row;
    }

    private void delete(int row) {
        User data = (User) model.find(row);

        Dialog dialog = new Dialog("Perhatian");
        dialog.setMessage("Anda yakin ?");
        dialog.setMessageType(JOptionPane.QUESTION_MESSAGE);
        dialog.setOptionType(JOptionPane.YES_NO_OPTION);
        Object status = dialog.show(getRootPane());

        if (!status.equals(0)) {
            return;
        }

        Response response = service.delete(data);

        if (response.isSuccess()) {
            model.delete(row);
        }

        dialog.setMessageType(response.isSuccess() ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
        dialog.setMessage(response.getMessage());
        dialog.setOptionType(JOptionPane.DEFAULT_OPTION);
        dialog.show(getRootPane());

        clearForm();
    }

    private void save() {
        boolean isInsert = true;
        String id = groupId.getInputValue();
        String name = groupName.getInputValue();
        String username = groupUsername.getInputValue();
        String password = groupPassword.getInputValue();

        User data = new User();
        data.setName(name);
        data.setUsername(username);

        if (!id.isEmpty()) {
            data.setId(Long.valueOf(id));
            isInsert = false;

            User selectedUser = (User) model.find(atTableRowIndex);
            data.setPassword(selectedUser.getPassword());
        }

        if (!password.isEmpty()) {
            data.setPassword(Password.hash(password));
        }

        try {
            if (!Application.getAuthUser().isAdmin()) {
                
                if (isInsert) {
                    throw new Exception("Anda tidak memiliki akses untuk menambah pengguna baru");
                }
                
                if (!Application.getAuthUser().getId().equals(data.getId())) {
                    throw new Exception("Anda tidak memiliki akses untuk mengubah pengguna ini");
                }
            }
            
            Validation formValidation = new Validation()
                    .addItem(new ValidationItem("Nama", groupName.getInputField())
                            .addRule(new RuleNotEmpty())
                    )
                    .addItem(new ValidationItem("Username", groupUsername.getInputField())
                            .addRule(new RuleNotEmpty())
                            .addRule(new RuleUnique("users", "username", isInsert ? null : id))
                    );

            if (!isInsert) {
                formValidation
                        .addItem(new ValidationItem("ID", groupId.getInputField())
                                .addRule(new RuleNotEmpty())
                        );
            } else {
                formValidation
                        .addItem(new ValidationItem("Password", groupPassword.getInputField())
                                .addRule(new RuleNotEmpty())
                        );
            }

            if (!formValidation.validate()) {
                throw new Exception(formValidation.getErrorMessageString());
            }

            Response response = service.save(data);

            if (!response.isSuccess()) {
                throw new Exception(response.getMessage());
            }

            if (isInsert) {
                model.insert(data);
            } else {
                model.update(atTableRowIndex, data);
            }

            Dialog dialog = new Dialog("Perhatian");
            dialog.setMessageType(JOptionPane.INFORMATION_MESSAGE);
            dialog.setMessage(response.getMessage());
            dialog.setOptionType(JOptionPane.DEFAULT_OPTION);
            dialog.show(getRootPane());

            clearForm();
        } catch (Exception ex) {
            Dialog dialog = new Dialog("Perhatian");
            dialog.setMessageType(JOptionPane.ERROR_MESSAGE);
            dialog.setMessage(ex.getMessage());
            dialog.setOptionType(JOptionPane.DEFAULT_OPTION);
            dialog.show(getRootPane());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        header = new main.application.components.Header();
        bodyPanel = new main.application.components.BodyPanel();

        setLayout(new java.awt.BorderLayout());
        add(bodyPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private CardPanel formPanel;
    private TextInputGroup groupName;
    private TextInputGroup groupId;
    private TextInputGroup groupUsername;
    private PasswordInputGroup groupPassword;
    private Button cmdSave;
    private Button cmdClear;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private main.application.components.BodyPanel bodyPanel;
    private main.application.components.Header header;
    // End of variables declaration//GEN-END:variables
}
