package main.application.forms.report;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import main.application.components.BodyPanel;
import main.application.components.Button;
import main.application.components.CardPanel;
import main.application.components.ComboBoxInputGroup;
import main.application.components.Header;
import main.model.Type;
import main.model.combobox.TypeComboBoxItem;
import main.util.Dialog;
import main.util.Report;
import main.util.query.QueryBuilder;
import main.util.query.QueryMethod;
import main.util.query.clause.JoinClause;
import main.util.query.clause.OrderByClause;
import main.util.query.clause.WhereClause;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author aldes
 */
public class RoomReportForm extends JPanel {
    
    public RoomReportForm() {
        init();
        initEvents();
    }
    
    private void init() {
        bodyPanel = new BodyPanel();
        
        setLayout(new BorderLayout());
        add(bodyPanel, BorderLayout.CENTER);
        
        JPanel panel = bodyPanel.getPanel();
        
        Header header = new Header();
        header.setTitleText("Laporan Gedung dan Ruangan");
        panel.add(header, "wrap");
        
        CardPanel formPanel = new CardPanel();
        
        JPanel inputPanel = new JPanel(new MigLayout("ins 0", "[]10[]"));
        inputPanel.setOpaque(false);
        
        cbType = new ComboBoxInputGroup<>();
        cbType.setTitleText("Filter Tipe");
        initCbUsers();
        inputPanel.add(cbType);
        
        JPanel buttonPanel = new JPanel(new MigLayout("ins 2", "[]10[]"));
        buttonPanel.setOpaque(false);
        
        cmdSearch = new Button();
        cmdSearch.setText("Cari");
        buttonPanel.add(cmdSearch);
        
        cmdReset = new Button();
        cmdReset.setText("Bersihkan");
        buttonPanel.add(cmdReset);
        
        formPanel.add(inputPanel, "wrap");
        formPanel.add(buttonPanel, "wrap, right");
        panel.add(formPanel, "wrap, growx");
    }
    
    private void initCbUsers() {
        try {
            List<Type> types = new Type().all();
            
            cbType.getInputField().addItem(null);
            
            for (Type type : types) {
                TypeComboBoxItem item = new TypeComboBoxItem(type);
                
                cbType.getInputField().addItem(item);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void initEvents() {
        cmdReset.addActionListener((ae) -> {
            clearForm();
        });
        
        cmdSearch.addActionListener((ae) -> {
            search();
        });
    }
    
    private void clearForm() {
        cbType.getInputField().setSelectedItem(null);
    }
    
    private void search() {
        Long typeId = cbType.getSelectedItem() != null
                ? ((TypeComboBoxItem) cbType.getSelectedItem()).getModel().getId()
                : null;
        
        QueryBuilder builder = QueryBuilder.getInstance();
        
        try {
            String sql = builder.setFrom("rooms")
                    .addSelect("rooms.*")
                    .addSelect("types.`name` AS type_name")
                    .addJoin(new JoinClause("types", "types.id", "rooms.type_id"))
                    .addOrderBy(new OrderByClause("rooms.created_at", "DESC"))
                    .addWhere(new WhereClause("type_id", typeId), typeId != null)
                    .toSql(QueryMethod.SELECT);
            
            Report.showReport("RoomReport", sql);
        } catch (Throwable e) {
            Dialog dialog = new Dialog("Perhatian");
            dialog.setMessage(e.getMessage());
            dialog.setMessageType(JOptionPane.INFORMATION_MESSAGE);
            dialog.setOptionType(JOptionPane.DEFAULT_OPTION);
            dialog.show(getRootPane());
        }
        
    }
    
    private BodyPanel bodyPanel;
    private ComboBoxInputGroup<TypeComboBoxItem> cbType;
    private Button cmdSearch;
    private Button cmdReset;
}
