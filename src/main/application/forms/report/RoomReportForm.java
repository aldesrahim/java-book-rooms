package main.application.forms.report;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import main.application.components.BodyPanel;
import main.application.components.Button;
import main.application.components.CardPanel;
import main.application.components.ComboBoxInputGroup;
import main.application.components.DateInputGroup;
import main.application.components.Header;
import main.model.User;
import main.model.combobox.UserComboBoxItem;
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
 * @author PT BUKUKU
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
        header.setTitleText("Laporan Log Aktivitas");
        panel.add(header, "wrap");
        
        CardPanel formPanel = new CardPanel();
        
        JPanel inputPanel = new JPanel(new MigLayout("ins 0", "[]10[]"));
        inputPanel.setOpaque(false);
        
        cbUser = new ComboBoxInputGroup<>();
        cbUser.setTitleText("Filter User");
        initCbUsers();
        inputPanel.add(cbUser);
        
        groupDate1 = new DateInputGroup();
        groupDate1.setTitleText("Filter Dari Tanggal");
        inputPanel.add(groupDate1);
        
        groupDate2 = new DateInputGroup();
        groupDate2.setTitleText("Filter Sampai Tanggal");
        inputPanel.add(groupDate2, "wrap");
        
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
            List<User> users = new User().all();
            
            cbUser.getInputField().addItem(null);
            
            for (User user : users) {
                UserComboBoxItem item = new UserComboBoxItem(user);
                
                cbUser.getInputField().addItem(item);
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
        groupDate1.setDate(null);
        groupDate2.setDate(null);
        cbUser.getInputField().setSelectedItem(null);
    }
    
    private void search() {
        Date date1 = groupDate1.getDate();
        Date date2 = groupDate2.getDate();
        Long userId = cbUser.getSelectedItem() != null
                ? ((UserComboBoxItem) cbUser.getSelectedItem()).getModel().getId()
                : null;
        
        QueryBuilder builder = QueryBuilder.getInstance();
        
        try {
            String sql = builder.setFrom("activity_logs")
                    .addSelect("activity_logs.*")
                    .addSelect("users.`name` AS user_name")
                    .addJoin(new JoinClause("users", "users.id", "activity_logs.user_id"))
                    .addOrderBy(new OrderByClause("activity_logs.created_at", "DESC"))
                    .addWhere(new WhereClause("DATE(activity_logs.created_at)", ">=", date1), date1 != null)
                    .addWhere(new WhereClause("DATE(activity_logs.created_at)", "<=", date2), date2 != null)
                    .addWhere(new WhereClause("user_id", userId), userId != null)
                    .toSql(QueryMethod.SELECT);
            
            Report.showReport("ActivityLogReport", sql);
        } catch (Throwable e) {
            Dialog dialog = new Dialog("Perhatian");
            dialog.setMessage(e.getMessage());
            dialog.setMessageType(JOptionPane.INFORMATION_MESSAGE);
            dialog.setOptionType(JOptionPane.DEFAULT_OPTION);
            dialog.show(getRootPane());
        }
        
    }
    
    private BodyPanel bodyPanel;
    private ComboBoxInputGroup<UserComboBoxItem> cbUser;
    private DateInputGroup groupDate1;
    private DateInputGroup groupDate2;
    private Button cmdSearch;
    private Button cmdReset;
}
