/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.application.components;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.event.ItemEvent;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import main.application.components.table.cell.TableActionCellEditor;
import main.application.components.table.cell.TableActionCellRender;
import main.application.components.table.cell.TableActionEvent;
import main.application.components.table.cell.TableActionVisibility;
import main.model.Model;
import main.model.table.TableModel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author aldes
 */
public class TableWithPagination extends JPanel {

    private TableModel tableModel;

    private final Object[] limitData = {
        10, 50, 200, 1000
    };

    private Integer currentPage = 1;
    private Integer perPage;
    private Integer totalPage;
    private Integer totalData;
    private Integer offset;

    private String searchTerm = "";

    public TableWithPagination() {
        initComponents();
    }

    public TableWithPagination(TableModel model) {
        this.tableModel = model;
        initComponents();
        initPagination();
        initEvents();
    }

    private void initPagination() {
        perPage = Integer.valueOf(selPerPage.getSelectedItem().toString());
        totalData = tableModel.getAllRowCount().intValue();
        totalPage = ((Double) Math.ceil(totalData.doubleValue() / perPage.doubleValue())).intValue();
        offset = perPage * (currentPage - 1);
        offset = offset == 0 ? 1 : offset + 1;

        if (currentPage > totalPage) {
            currentPage = 1;
        }

        try {
            Model m = ((Model) tableModel.getModel());
            m.scopePaginate(currentPage, perPage);
            m.scopeSearch(searchTerm);

            tableModel.setRows(m.all());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        cmdFirst.setEnabled(!currentPage.equals(1));
        cmdPrevious.setEnabled(!currentPage.equals(1));
        cmdLast.setEnabled(!currentPage.equals(totalPage));
        cmdNext.setEnabled(!currentPage.equals(totalPage));

        table.setModel(tableModel);

        labelInfo.setText(String.format("Menampilkan %s hingga %s dari %s entri", offset, (perPage > totalData ? totalData : perPage) * currentPage, totalData));

        initAction();

        scroll.repaint();
        scroll.revalidate();
    }

    private void initEvents() {
        cmdPrevious.addActionListener((ae) -> {
            if (currentPage > 1) {
                currentPage--;
                initPagination();
            }
        });
        cmdNext.addActionListener((ae) -> {
            if (currentPage < totalPage) {
                currentPage++;
                initPagination();
            }
        });
        cmdFirst.addActionListener((ae) -> {
            currentPage = 1;
            initPagination();
        });
        cmdLast.addActionListener((ae) -> {
            currentPage = totalPage;
            initPagination();
        });
        selPerPage.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                initPagination();
            }

        });
    }

    private void initComponents() {
        setLayout(new MigLayout("fillx,ins 0"));

        table = new JTable();
        scroll = new JScrollPane(table);

        pagination = new JPanel(new MigLayout("ins 0", "[]10[]"));

        cmdFirst = new Button();
        cmdFirst.setText("<<");
        cmdPrevious = new Button();
        cmdPrevious.setText("<");

        selPerPage = new JComboBox(limitData);
        selPerPage.putClientProperty(FlatClientProperties.STYLE, ""
                + "padding:5,11,5,11;"
                + "arc:10;");

        cmdNext = new Button();
        cmdNext.setText(">");
        cmdLast = new Button();
        cmdLast.setText(">>");

        labelInfo = new JLabel();

        pagination.add(labelInfo, "pushx");
        pagination.add(cmdFirst);
        pagination.add(cmdPrevious);
        pagination.add(selPerPage);
        pagination.add(cmdNext);
        pagination.add(cmdLast);

        groupSearch = new SearchInputGroup(new SearchInputGroupEvent() {
            @Override
            public void onSearch(String searchText) {
                currentPage = 1;
                searchTerm = groupSearch.getInput().getText();

                initPagination();
            }

            @Override
            public void onReset(String searchText) {
                currentPage = 1;
                searchTerm = "";

                initPagination();
            }
        });
        groupSearch.setIsWithResetBtn(true);
        add(groupSearch, "wrap, right");

        add(scroll, "wrap, growx");
        add(pagination, "wrap, growx");
    }

    private void initAction() {
        if (tableModel.isWithAction()) {
            setAction(tableModel.getActionIndex(), tableModel.getActionEvent());
        }
    }

    public void setAction(int columnIndex, TableActionEvent event) {
        TableActionVisibility actionVisibility = tableModel.getActionVisiblity();
        TableActionCellRender cellRenderer = new TableActionCellRender(actionVisibility);
        TableActionCellEditor cellEditor = new TableActionCellEditor(event, actionVisibility);

        getTable().getColumnModel().getColumn(columnIndex).setCellRenderer(cellRenderer);
        getTable().getColumnModel().getColumn(columnIndex).setCellEditor(cellEditor);
        getTable().getColumnModel().getColumn(columnIndex).setPreferredWidth(130);
        getTable().getColumnModel().getColumn(columnIndex).setMaxWidth(130);
        getTable().setRowHeight(40);
    }

    public JTable getTable() {
        return table;
    }

    public TableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(TableModel model) {
        this.tableModel = model;
    }

    public Button getCmdPrevious() {
        return cmdPrevious;
    }

    public Button getCmdNext() {
        return cmdNext;
    }

    public Button getCmdFirst() {
        return cmdFirst;
    }

    public Button getCmdLast() {
        return cmdLast;
    }

    public JComboBox getSelPerPage() {
        return selPerPage;
    }

    private JScrollPane scroll;
    private JTable table;

    private SearchInputGroup groupSearch;
    private JLabel labelInfo;
    private JPanel pagination;
    private Button cmdPrevious;
    private Button cmdNext;
    private Button cmdFirst;
    private Button cmdLast;
    private JComboBox selPerPage;
}
