/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import main.model.User;
import java.sql.SQLException;
import main.application.Application;
import main.model.ActivityLog;
import main.service.other.Response;
import main.util.query.QueryBuilder;

/**
 *
 * @author aldes
 */
public class UserService extends BaseService {

    public UserService() {
        super(new User());
    }

    @Override
    public User getModel() {
        return (User) this.model;
    }

    public Response save(User user) {
        QueryBuilder.getInstance().beginTransaction();

        try {
            boolean isInsert = user.getId() == null;

            user.save();

            if (Application.isAuthenticated()) {
                new ActivityLog(
                        null,
                        Application.getAuthUser().getId(),
                        user.getClass().getName(),
                        user.getId(),
                        user.toString(),
                        String.format("%s data Pengguna [%s]",
                                isInsert ? "Tambah" : "Ubah",
                                user.getName()
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

    public Response delete(User user) {
        QueryBuilder.getInstance().beginTransaction();

        try {

            if (Application.isAuthenticated()) {
                new ActivityLog(
                        null,
                        Application.getAuthUser().getId(),
                        null,
                        null,
                        user.toString(),
                        String.format("Hapus data Pengguna [%s]", user.getName())
                ).save();
            }

            user.delete();

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
}
