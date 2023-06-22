/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import main.application.Application;
import main.model.ActivityLog;
import main.model.ConsumptionReservation;
import main.model.Reservation;
import main.service.other.Response;
import main.util.query.QueryBuilder;
import main.util.query.QueryRaw;
import main.util.query.clause.WhereClause;
import main.util.query.clause.WhereInClause;

/**
 *
 * @author aldes
 */
public class ReservationService extends BaseService {

    public ReservationService() {
        super(new Reservation());
    }

    @Override
    public Reservation getModel() {
        return (Reservation) this.model;
    }

    public Response save(Reservation reservation, List<ConsumptionReservation> consumptionReservation) {
        QueryBuilder.getInstance().beginTransaction();

        try {
            boolean isInsert = reservation.getId() == null;

            reservation.save();

            if (Application.isAuthenticated()) {
                new ActivityLog(
                        null,
                        Application.getAuthUser().getId(),
                        reservation.getClass().getName(),
                        reservation.getId(),
                        reservation.toString(),
                        String.format("%s data Reservasi [%s]",
                                isInsert ? "Tambah" : "Ubah",
                                reservation.getName()
                        )
                ).save();
            }

            QueryBuilder.getInstance()
                    .setFrom((new ConsumptionReservation()).getTable())
                    .addWhere(new WhereClause("reservation_id", reservation.getId()))
                    .delete();

            for (ConsumptionReservation item : consumptionReservation) {
                item.setReservationId(reservation.getId());
                item.save();
            }

            QueryBuilder.getInstance().commit();

            return new Response(true, "Data berhasil disimpan");
        } catch (SQLException ex) {
            QueryBuilder.getInstance().rollBack();

            ex.printStackTrace();

            return new Response(false, "Data gagal disimpan");
        }
    }

    public Response delete(Reservation reservation) {
        QueryBuilder.getInstance().beginTransaction();

        try {
            if (Application.isAuthenticated()) {
                new ActivityLog(
                        null,
                        Application.getAuthUser().getId(),
                        null,
                        null,
                        reservation.toString(),
                        String.format("Hapus data Reservasi [%s]", reservation.getName())
                ).save();
            }

            reservation.delete();

            QueryBuilder.getInstance().commit();

            return new Response(true, "Data berhasil dihapus");
        } catch (SQLException ex) {
            QueryBuilder.getInstance().rollBack();

            ex.printStackTrace();

            return new Response(false, "Data gagal dihapus");
        } finally {
            QueryBuilder.getInstance().endTransaction();
        }
    }

    public boolean isDateAvailable(Long roomId, String startDate, String endDate) throws SQLException {

        ResultSet rs = QueryBuilder.getInstance()
                .setDebug(true)
                .addSelect("COUNT(*) AS jml")
                .setFrom((new Reservation()).getTable())
                .addWhere(new WhereClause()
                        .addSub(
                                new WhereClause("OR")
                                        .addSub(new WhereClause("status", Reservation.Status.BOOKED.toInt()))
                                        .addSub(new WhereClause("'" + startDate + "'", "<=", new QueryRaw("ended_at")))
                                        .addSub(new WhereClause("'" + endDate + "'", ">=", new QueryRaw("started_at")))
                        )
                        .addSub(new WhereClause("status", "=", Reservation.Status.CHECKED_IN.toInt(), "OR"))
                )
                .addWhere(new WhereClause("room_id", roomId))
                .fetch()
                .get();

        if (rs.next()) {
            Long count = rs.getLong("jml");
            
            return count.equals(Long.valueOf("0"));
        }

        return !rs.next();
    }
}
