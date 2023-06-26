/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import main.application.enums.ReservationStatus;
import main.model.Reservation;
import main.util.query.QueryRaw;
import main.util.query.clause.WhereClause;

/**
 *
 * @author aldes
 */
public class DashboardService {

    public String todayReservationCount() {
        try {
            Reservation model = new Reservation();

            model.query()
                    .addWhere(new WhereClause("OR")
                            .addSub(new WhereClause("DATE(started_at)", new QueryRaw("DATE(NOW())")))
                            .addSub(new WhereClause("DATE(checked_in_at)", new QueryRaw("DATE(NOW())")))
                    );

            Long total = new Reservation().count();

            return total != null ? total.toString() : "0";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "0";
    }

    public String thisMonthReservationCount() {
        try {
            Reservation model = new Reservation();

            model.query()
                    .addWhere(new WhereClause("OR")
                            .addSub(new WhereClause("DATE_FORMAT(started_at, '%Y%m')", new QueryRaw("DATE_FORMAT(NOW(), '%Y%m')")))
                            .addSub(new WhereClause("DATE_FORMAT(checked_in_at, '%Y%m')", new QueryRaw("DATE_FORMAT(NOW(), '%Y%m')")))
                    );

            Long total = new Reservation().count();

            return total != null ? total.toString() : "0";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "0";
    }

    public String thisYearReservationCount() {
        try {
            Reservation model = new Reservation();

            model.query()
                    .addWhere(new WhereClause("OR")
                            .addSub(new WhereClause("YEAR(started_at)", new QueryRaw("YEAR(NOW())")))
                            .addSub(new WhereClause("YEAR(checked_in_at)", new QueryRaw("YEAR(NOW())")))
                    );

            Long total = new Reservation().count();

            return total != null ? total.toString() : "0";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "0";
    }

    public List<Reservation> getReservations(Date date) {
        List<Reservation> reservations = new ArrayList<>();

        try {
            Reservation model = new Reservation();

            model.query()
                    .addWhere(new WhereClause()
                            .addSub(new WhereClause("OR")
                                    .addSub(new WhereClause("status", ReservationStatus.BOOKED.toInt()))
                                    .addSub(new WhereClause("DATE(reservations.started_at)", "<=", date))
                                    .addSub(new WhereClause("DATE(reservations.ended_at)", ">=", date))
                            )
                            .addSub(new WhereClause("OR")
                                    .addSub(new WhereClause("status", "=", ReservationStatus.CHECKED_IN.toInt()))
                                    .addSub(new WhereClause("DATE(reservations.checked_in_at)", "<=", date))
                            )
                            .addSub(new WhereClause("OR")
                                    .addSub(new WhereClause("status", "=", ReservationStatus.CHECKED_OUT.toInt()))
                                    .addSub(new WhereClause("DATE(reservations.checked_in_at)", "<=", date))
                                    .addSub(new WhereClause("DATE(reservations.checked_out_at)", ">=", date))
                            )
                            .addSub(new WhereClause("OR")
                                    .addSub(new WhereClause("status", "=", ReservationStatus.CANCELED.toInt()))
                                    .addSub(new WhereClause("DATE(reservations.updated_at)", date))
                            )
                    );

            return model.all();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reservations;
    }
}
