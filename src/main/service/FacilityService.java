/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import main.model.Facility;
import java.sql.SQLException;
import java.sql.ResultSet;
import main.application.Application;
import main.model.ActivityLog;
import main.service.other.Response;
import main.util.query.QueryBuilder;
import main.util.query.clause.WhereClause;

/**
 *
 * @author aldes
 */
public class FacilityService extends BaseService {

    public FacilityService() {
        super(new Facility());
    }

    @Override
    public Facility getModel() {
        return (Facility) this.model;
    }

    public Response save(Facility facility) {
        QueryBuilder.getInstance().beginTransaction();

        try {
            boolean isInsert = facility.getId() == null;

            facility.save();

            if (Application.isAuthenticated()) {
                new ActivityLog(
                        null,
                        Application.getAuthUser().getId(),
                        facility.getClass().getName(),
                        facility.getId(),
                        facility.toString(),
                        String.format("%s data Fasilitas [%s]",
                                isInsert ? "Tambah" : "Ubah",
                                facility.getName()
                        )
                ).save();
            }

            QueryBuilder.getInstance().commit();

            return new Response(true, "Data berhasil disimpan");
        } catch (Exception ex) {
            QueryBuilder.getInstance().rollBack();

            ex.printStackTrace();

            return new Response(false, "Data gagal disimpan");
        } finally {
            QueryBuilder.getInstance().endTransaction();
        }
    }

    public Response delete(Facility facility) {
        QueryBuilder.getInstance().beginTransaction();

        try {
            if (!isDeletable(facility.getId())) {
                return new Response(false, "Data tidak bisa dihapus karena terikat dengan Gedung/Ruangan");
            }

            if (Application.isAuthenticated()) {
                new ActivityLog(
                        null,
                        Application.getAuthUser().getId(),
                        null,
                        null,
                        facility.toString(),
                        String.format("Hapus data Fasilitas [%s]", facility.getName())
                ).save();
            }

            facility.delete();

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

    public boolean isDeletable(Long id) throws SQLException {
        ResultSet rs = QueryBuilder.getInstance()
                .addSelect("COUNT(*) AS jml")
                .setFrom("facility_room")
                .addWhere(new WhereClause("facility_id", id))
                .fetch()
                .get();

        if (rs.next()) {
            Long count = rs.getLong("jml");

            return count.equals(Long.valueOf("0"));
        }

        return false;
    }
}
