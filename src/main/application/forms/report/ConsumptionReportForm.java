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
import main.application.enums.ReservationStatus;
import main.model.Consumption;
import main.model.Room;
import main.model.User;
import main.model.combobox.ConsumptionComboBoxItem;
import main.model.combobox.RoomComboBoxItem;
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
public class ConsumptionReportForm extends JPanel {

    public ConsumptionReportForm() {
        init();
        initEvents();
    }

    private void init() {
        bodyPanel = new BodyPanel();

        setLayout(new BorderLayout());
        add(bodyPanel, BorderLayout.CENTER);

        JPanel panel = bodyPanel.getPanel();

        Header header = new Header();
        header.setTitleText("Laporan Konsumsi");
        panel.add(header, "wrap");

        CardPanel formPanel = new CardPanel();

        JPanel inputPanel = new JPanel(new MigLayout("ins 0, wrap 4", "[]10[]"));
        inputPanel.setOpaque(false);

        cbRoom = new ComboBoxInputGroup<>();
        cbRoom.setTitleText("Filter Gedung/Ruangan");
        initCbRooms();
        inputPanel.add(cbRoom);

        cbConsumption = new ComboBoxInputGroup<>();
        cbConsumption.setTitleText("Filter Konsumsi");
        initCbConsumptions();
        inputPanel.add(cbConsumption);

        groupDate1 = new DateInputGroup();
        groupDate1.setTitleText("Filter Dari Tanggal");
        inputPanel.add(groupDate1);

        groupDate2 = new DateInputGroup();
        groupDate2.setTitleText("Filter Sampai Tanggal");
        inputPanel.add(groupDate2);

        cbUser = new ComboBoxInputGroup<>();
        cbUser.setTitleText("Filter User");
        initCbUsers();
        inputPanel.add(cbUser, "wrap");

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

    private void initCbRooms() {
        try {
            List<Room> rooms = new Room().all();

            cbRoom.getInputField().addItem(null);

            for (Room room : rooms) {
                RoomComboBoxItem item = new RoomComboBoxItem(room);

                cbRoom.getInputField().addItem(item);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initCbConsumptions() {
        try {
            List<Consumption> consumptions = new Consumption().all();

            cbConsumption.getInputField().addItem(null);

            for (Consumption consumption : consumptions) {
                ConsumptionComboBoxItem item = new ConsumptionComboBoxItem(consumption);

                cbConsumption.getInputField().addItem(item);
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
        Long roomId = cbRoom.getSelectedItem() != null
                ? ((RoomComboBoxItem) cbRoom.getSelectedItem()).getModel().getId()
                : null;

        if ((date1 != null && date2 == null) || (date2 != null && date1 == null)) {
            Dialog dialog = new Dialog("Perhatian");
            dialog.setMessage("Salah satu tanggal tidak boleh kosong");
            dialog.setMessageType(JOptionPane.INFORMATION_MESSAGE);
            dialog.setOptionType(JOptionPane.DEFAULT_OPTION);
            dialog.show(getRootPane());

            return;

        }

        QueryBuilder builder = QueryBuilder.getInstance();

        try {
            String sql = builder.setFrom("`consumption_reservation` a")
                    .addSelect("a.*")
                    .addSelect("c.`name` AS `name`")
                    .addSelect("d.`name` AS room_name")
                    .addSelect("e.`name` AS user_name")
                    .addSelect("""
                               CASE b.`status`
                               WHEN 0 THEN 'Dipesan' 
                               WHEN 1 THEN 'Check In' 
                               WHEN 2 THEN 'Check Out'
                               ELSE 'Dibatalkan' 
                               END AS `status`
                    """)
                    .addSelect("""
                               CASE b.`status` 
                               WHEN 0 THEN b.started_at
                               WHEN 1 THEN b.checked_in_at
                               WHEN 2 THEN b.checked_in_at
                               ELSE b.updated_at 
                               END AS `start_date`
                    """)
                    .addSelect("""
                               CASE b.`status` 
                               WHEN 0 THEN b.ended_at
                               WHEN 2 THEN b.checked_out_at
                               ELSE null
                               END AS `end_date`
                    """)
                    .addJoin(new JoinClause("reservations b", "a.reservation_id", "b.id"))
                    .addJoin(new JoinClause("consumptions c", "a.consumption_id", "c.id"))
                    .addJoin(new JoinClause("rooms d", "b.room_id", "d.id"))
                    .addJoin(new JoinClause("users e", "e.id", "=", "b.user_id", "LEFT JOIN"))
                    .addOrderBy(new OrderByClause("start_date", "DESC"))
                    .addWhere(new WhereClause()
                            .addSub(new WhereClause("OR")
                                    .addSub(new WhereClause("status", ReservationStatus.BOOKED.toInt()))
                                    .addSub(new WhereClause("DATE(ended_at)", ">=", date1))
                                    .addSub(new WhereClause("DATE(started_at)", "<=", date2))
                            )
                            .addSub(new WhereClause("OR")
                                    .addSub(new WhereClause("status", "=", ReservationStatus.CHECKED_IN.toInt()))
                                    .addSub(new WhereClause("DATE(checked_in_at)", ">=", date1))
                                    .addSub(new WhereClause("DATE(checked_in_at)", "<=", date2))
                            )
                            .addSub(new WhereClause("OR")
                                    .addSub(new WhereClause("status", "=", ReservationStatus.CHECKED_OUT.toInt()))
                                    .addSub(new WhereClause("DATE(checked_in_at)", ">=", date1))
                                    .addSub(new WhereClause("DATE(checked_out_at)", "<=", date2))
                            ),
                            date1 != null && date2 != null)
                    .addWhere(new WhereClause("b.user_id", userId), userId != null)
                    .addWhere(new WhereClause("b.room_id", roomId), roomId != null)
                    .toSql(QueryMethod.SELECT);

            Report.showReport("ConsumptionReport", sql);
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
    private ComboBoxInputGroup<RoomComboBoxItem> cbRoom;
    private ComboBoxInputGroup<ConsumptionComboBoxItem> cbConsumption;
    private DateInputGroup groupDate1;
    private DateInputGroup groupDate2;
    private Button cmdSearch;
    private Button cmdReset;
}
