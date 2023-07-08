/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import main.application.Application;
import main.application.enums.ReservationStatus;
import main.model.ActivityLog;
import main.model.ConsumptionReservation;
import main.model.Reservation;
import main.service.other.Response;
import main.util.query.QueryBuilder;
import main.util.query.clause.WhereClause;

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
        } catch (Exception ex) {
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
        } catch (Exception ex) {
            QueryBuilder.getInstance().rollBack();

            ex.printStackTrace();

            return new Response(false, "Data gagal dihapus");
        } finally {
            QueryBuilder.getInstance().endTransaction();
        }
    }

    public boolean isDateAvailable(Long roomId, String startDate, String endDate) throws SQLException {
        return isDateAvailable(roomId, startDate, endDate, null);
    }

    public boolean isDateAvailable(Long roomId, String startDate, String endDate, Long exceptId) throws SQLException {

        ResultSet rs = QueryBuilder.getInstance()
                .addSelect("COUNT(*) AS jml")
                .setFrom((new Reservation()).getTable())
                .addWhere(new WhereClause()
                        .addSub(new WhereClause("OR")
                                .addSub(new WhereClause("status", ReservationStatus.BOOKED.toInt()))
                                .addSub(new WhereClause("ended_at", ">=", startDate))
                                .addSub(new WhereClause("started_at", "<=", endDate))
                        )
                        .addSub(new WhereClause("OR")
                                .addSub(new WhereClause("status", "=", ReservationStatus.CHECKED_IN.toInt()))
                                .addSub(new WhereClause("checked_in_at", ">=", startDate))
                                .addSub(new WhereClause("checked_in_at", "<=", endDate))
                        )
                        .addSub(new WhereClause("OR")
                                .addSub(new WhereClause("status", "=", ReservationStatus.CHECKED_OUT.toInt()))
                                .addSub(new WhereClause("checked_out_at", ">=", startDate))
                                .addSub(new WhereClause("checked_in_at", "<=", endDate))
                        )
                )
                .addWhere(new WhereClause("room_id", roomId))
                .addWhere(new WhereClause("id", "<>", exceptId), exceptId != null)
                .fetch()
                .get();

        if (rs.next()) {
            Long count = rs.getLong("jml");

            return count.equals(Long.valueOf("0"));
        }

        return !rs.next();
    }

    public Response cancel(Reservation reservation) {
        QueryBuilder.getInstance().beginTransaction();

        try {
            reservation.save();

            if (Application.isAuthenticated()) {
                new ActivityLog(
                        null,
                        Application.getAuthUser().getId(),
                        reservation.getClass().getName(),
                        reservation.getId(),
                        reservation.toString(),
                        String.format("%s Reservasi [%s]",
                                "Membatalkan",
                                reservation.getName()
                        )
                ).save();
            }

            QueryBuilder.getInstance().commit();

            return new Response(true, "Reservasi berhasil dibatalkan");
        } catch (Exception ex) {
            QueryBuilder.getInstance().rollBack();

            ex.printStackTrace();

            return new Response(false, "Reservasi gagal dibatalkan");
        }
    }

    public Response checkIn(Reservation reservation) {
        QueryBuilder.getInstance().beginTransaction();

        try {
            ResultSet rs = QueryBuilder.getInstance()
                    .addSelect("COUNT(*) AS jml")
                    .setFrom(new Reservation().getTable())
                    .addWhere(new WhereClause("id", "<>", reservation.getId()))
                    .addWhere(new WhereClause("room_id", reservation.getRoomId()))
                    .addWhere(new WhereClause()
                            .addSub(new WhereClause("OR")
                                    .addSub(new WhereClause("status", "=", ReservationStatus.CHECKED_IN.toInt()))
                                    .addSub(new WhereClause("checked_in_at", "<=", reservation.getCheckedInAt()))
                            )
                            .addSub(new WhereClause("OR")
                                    .addSub(new WhereClause("status", "=", ReservationStatus.BOOKED.toInt()))
                                    .addSub(new WhereClause("started_at", "<=", reservation.getCheckedInAt()))
                            )
                    )
                    .fetch()
                    .get();

            if (rs.next()) {
                Long count = rs.getLong("jml");

                if (!count.equals(Long.valueOf("0"))) {
                    throw new Exception("Gedung/Ruangan ini tidak tersedia untuk tanggal check in yang dipilih");
                }
            }

            reservation.save();

            if (Application.isAuthenticated()) {
                new ActivityLog(
                        null,
                        Application.getAuthUser().getId(),
                        reservation.getClass().getName(),
                        reservation.getId(),
                        reservation.toString(),
                        String.format("%s Reservasi [%s]",
                                "Check In",
                                reservation.getName()
                        )
                ).save();
            }

            QueryBuilder.getInstance().commit();

            return new Response(true, "Check in berhasil");
        } catch (Exception ex) {
            QueryBuilder.getInstance().rollBack();

            ex.printStackTrace();

            return new Response(false, "Check in gagal");
        }
    }

    public Response checkOut(Reservation reservation) {
        QueryBuilder.getInstance().beginTransaction();

        try {

            reservation.save();

            if (Application.isAuthenticated()) {
                new ActivityLog(
                        null,
                        Application.getAuthUser().getId(),
                        reservation.getClass().getName(),
                        reservation.getId(),
                        reservation.toString(),
                        String.format("%s Reservasi [%s]",
                                "Check Out",
                                reservation.getName()
                        )
                ).save();
            }

            QueryBuilder.getInstance().commit();

            return new Response(true, "Check out berhasil");
        } catch (Exception ex) {
            QueryBuilder.getInstance().rollBack();

            ex.printStackTrace();

            return new Response(false, "Check out gagal");
        }
    }
}
