/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.application.components.table.cell;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;

/**
 *
 * @author aldes
 */
public class ActionPanel extends JPanel {

    private TableActionEvent event;

    public ActionPanel() {
        init();
    }

    private void init() {
        setLayout(new BorderLayout());

        cmdEdit = new ActionButton("edit");
        cmdView = new ActionButton("view");
        cmdDelete = new ActionButton("delete");

        cmdView.setSelected(false);
        cmdView.setVisible(false);
        cmdEdit.setSelected(false);
        cmdDelete.setSelected(false);

        /**
         * Fix button https://stackoverflow.com/a/67666743/9701449
         */
        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p2.setOpaque(false);
        p2.add(cmdView, "");
        p2.add(cmdEdit, "");
        p2.add(cmdDelete, "");

        JPanel p1 = new JPanel(new BorderLayout());
        p1.setOpaque(false);
        p1.add(p2);

        add(p1);
    }

    public void initEvent(TableActionEvent event, int row) {
        cmdView.addActionListener((ActionEvent ae) -> {
            event.onView(row);
        });
        cmdEdit.addActionListener((ActionEvent ae) -> {
            event.onEdit(row);
        });
        cmdDelete.addActionListener((ActionEvent ae) -> {
            event.onDelete(row);
        });
    }

    public void setActionVisibility(TableActionVisibility actionVisibility) {
        setCmdViewVisibile(actionVisibility.isViewActionVisible());
        setCmdEditVisibile(actionVisibility.isEditActionVisible());
        setCmdDeleteVisibile(actionVisibility.isDeleteActionVisible());
    }

    public void setCmdEditVisibile(boolean visible) {
        cmdEdit.setVisible(visible);
    }

    public void setCmdDeleteVisibile(boolean visible) {
        cmdDelete.setVisible(visible);
    }

    public void setCmdViewVisibile(boolean visible) {
        cmdView.setVisible(visible);
    }

    private ActionButton cmdEdit;
    private ActionButton cmdDelete;
    private ActionButton cmdView;
}
