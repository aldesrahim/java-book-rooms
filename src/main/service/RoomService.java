/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.application.Application;
import main.model.ActivityLog;
import main.model.FacilityRoom;
import main.model.Reservation;
import main.model.Room;
import main.service.other.Response;
import main.util.query.QueryBuilder;
import main.util.query.clause.WhereClause;

/**
 *
 * @author aldes
 */
public class RoomService extends BaseService {

    public RoomService() {
        super(new Room());
    }

    @Override
    public Room getModel() {
        return (Room) this.model;
    }

    public Response save(Room room, List<FacilityRoom> facilityRoom) {
        QueryBuilder.getInstance().beginTransaction();

        try {
            boolean isInsert = room.getId() == null;

            room.save();

            if (Application.isAuthenticated()) {
                new ActivityLog(
                        null,
                        Application.getAuthUser().getId(),
                        room.getClass().getName(),
                        room.getId(),
                        room.toString(),
                        String.format("%s data Gedung dan Ruangan [%s]",
                                isInsert ? "Tambah" : "Ubah",
                                room.getName()
                        )
                ).save();
            }

            QueryBuilder.getInstance()
                    .setFrom((new FacilityRoom()).getTable())
                    .addWhere(new WhereClause("room_id", room.getId()))
                    .delete();

            for (FacilityRoom item : facilityRoom) {
                item.setRoomId(room.getId());
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

    public Response delete(Room room) {
        QueryBuilder.getInstance().beginTransaction();

        try {

            if (isRoomHasReservations(room)) {
                return new Response(false, "Tidak bisa dihapus, karena data terikat dengan reservasi");
            }
            
            if (Application.isAuthenticated()) {
                new ActivityLog(
                        null,
                        Application.getAuthUser().getId(),
                        null,
                        null,
                        room.toString(),
                        String.format("Hapus data Gedung dan Ruangan [%s]", room.getName())
                ).save();
            }

            room.delete();

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

    public boolean isRoomHasReservations(Room room) throws SQLException {
        Reservation reservation = new Reservation();

        reservation.query().addWhere(new WhereClause("room_id", room.getId()));

        return reservation.count() > 0;
    }
}
