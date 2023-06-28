/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import java.sql.ResultSet;
import main.model.Consumption;
import java.sql.SQLException;
import main.application.Application;
import main.model.ActivityLog;
import main.service.other.Response;
import main.util.query.QueryBuilder;
import main.util.query.clause.WhereClause;

/**
 *
 * @author aldes
 */
public class ConsumptionService extends BaseService {

    public ConsumptionService() {
        super(new Consumption());
    }

    @Override
    public Consumption getModel() {
        return (Consumption) this.model;
    }

    public Response save(Consumption consumption) {
        QueryBuilder.getInstance().beginTransaction();
        
        try {
            boolean isInsert = consumption.getId() == null;

            consumption.save();

            if (Application.isAuthenticated()) {
                new ActivityLog(
                        null,
                        Application.getAuthUser().getId(),
                        consumption.getClass().getName(),
                        consumption.getId(),
                        consumption.toString(),
                        String.format("%s data Konsumsi [%s]",
                                isInsert ? "Tambah" : "Ubah",
                                consumption.getName()
                        )
                ).save();
            }
            
            QueryBuilder.getInstance().commit();

            return new Response(true, "Data berhasil disimpan");
        } catch (SQLException ex) {
            QueryBuilder.getInstance().rollBack();
            
            ex.printStackTrace();

            return new Response(false, "Data gagal disimpan");
        } finally {
            QueryBuilder.getInstance().endTransaction();
        }
    }

    public Response delete(Consumption consumption) {
        QueryBuilder.getInstance().beginTransaction();
        
        try {
            if (!isDeletable(consumption.getId())) {
                return new Response(false, "Data tidak bisa dihapus karena terikat dengan Reservasi");
            }

            if (Application.isAuthenticated()) {
                new ActivityLog(
                        null,
                        Application.getAuthUser().getId(),
                        null,
                        null,
                        consumption.toString(),
                        String.format("Hapus data Konsumsi [%s]", consumption.getName())
                ).save();
            }

            consumption.delete();
            
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

    public boolean isDeletable(Long id) throws SQLException {
        ResultSet rs = QueryBuilder.getInstance()
                .addSelect("COUNT(*) AS jml")
                .setFrom("consumption_reservation")
                .addWhere(new WhereClause("consumption_id", id))
                .fetch()
                .get();

        if (rs.next()) {
            Long count = rs.getLong("jml");

            return count.equals(Long.valueOf("0"));
        }

        return false;
    }
}
