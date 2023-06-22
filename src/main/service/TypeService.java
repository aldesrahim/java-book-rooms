/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import main.model.Type;
import java.sql.SQLException;
import main.application.Application;
import main.model.ActivityLog;
import main.service.other.Response;
import main.util.query.QueryBuilder;

/**
 *
 * @author aldes
 */
public class TypeService extends BaseService {

    public TypeService() {
        super(new Type());
    }

    @Override
    public Type getModel() {
        return (Type) this.model;
    }

    public Response save(Type type) {
        QueryBuilder.getInstance().beginTransaction();

        try {
            boolean isInsert = type.getId() == null;

            type.save();

            if (Application.isAuthenticated()) {
                new ActivityLog(
                        null,
                        Application.getAuthUser().getId(),
                        type.getClass().getName(),
                        type.getId(),
                        type.toString(),
                        String.format("%s data Tipe [%s]",
                                isInsert ? "Tambah" : "Ubah",
                                type.getName()
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

    public Response delete(Type type) {
        QueryBuilder.getInstance().beginTransaction();

        try {

            if (Application.isAuthenticated()) {
                new ActivityLog(
                        null,
                        Application.getAuthUser().getId(),
                        null,
                        null,
                        type.toString(),
                        String.format("Hapus data Tipe [%s]", type.getName())
                ).save();
            }

            type.delete();

            QueryBuilder.getInstance().commit();

            return new Response(true, "Data berhasil dihapus");
        } catch (SQLException ex) {
            QueryBuilder.getInstance().rollBack();

            ex.printStackTrace();

            return new Response(false, "Data gagal dihapus");
        }
    }
}
