/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import main.model.helper.HandleImageUpload;
import main.util.query.QueryUpdate;
import main.util.query.clause.OrderByClause;
import main.util.query.clause.SetClause;
import main.util.query.clause.WhereClause;

/**
 *
 * @author aldes
 */
public class Facility extends Model {

    private Long id;
    private String name;
    private String imagePath;
    private File imageFile;

    public Facility() {
    }

    public Facility(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setImagePath(String imagePath, boolean setFile) {
        this.imagePath = imagePath;

        if (setFile && imagePath != null) {
            this.imageFile = new File(imagePath);
        }
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    @Override
    public String getTable() {
        return "facilities";
    }

    @Override
    public Facility scopeSearch(String term) {
        if (term == null || term.isEmpty()) {
            return this;
        }

        query()
                .addWhere(new WhereClause(
                        new WhereClause("name", "like", "%" + term + "%", "OR")
                ));

        return this;
    }

    @Override
    public QueryUpdate save() throws SQLException {
        QueryUpdate queryUpdate;

        query()
                .addSet(new SetClause("name", getName()))
                .addSet(new SetClause("image_path", getImagePath()));

        if (getId() == null) {
            queryUpdate = query()
                    .insert();

            setId(queryUpdate.getInsertedId());
        } else {
            queryUpdate = query()
                    .addWhere(new WhereClause(getPrimaryKey(), getId()))
                    .update();
        }
        
        try {
            if (getImageFile() != null) {
                new HandleImageUpload(this, getImageFile(), (String uploadedPath) -> {
                    try {
                        String oldImagePath = getImagePath();
                        
                        query()
                                .addSet(new SetClause("image_path", uploadedPath))
                                .addWhere(new WhereClause(getPrimaryKey(), getId()))
                                .update();
                        
                        setImagePath(uploadedPath, true);
                        
                        if (oldImagePath != null) {
                            new File(oldImagePath).delete();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }).upload();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return queryUpdate;
    }

    @Override
    public QueryUpdate delete() throws SQLException {
        try {
            QueryUpdate status = delete(getId());
            
            deleteUploadedImage(false);
            
            return status;
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public void deleteUploadedImage() {
        deleteUploadedImage(true);
    }
    
    public void deleteUploadedImage(boolean withUpdate) {
        if (getImageFile() == null) {
            return;
        }

        try {
            if (withUpdate) {
                query()
                .addSet(new SetClause("image_path", null))
                .addWhere(new WhereClause(getPrimaryKey(), getId()))
                .update();
            }
        
            getImageFile().delete();
            setImageFile(null);
            setImagePath(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Facility fromResultSet(ResultSet rs) throws SQLException {
        Facility facility =  new Facility(
                rs.getLong("id"),
                rs.getString("name")
        );
        
        facility.setImagePath(rs.getString("image_path"), true);
        
        return facility;
    }

    @Override
    public List all() throws SQLException {
        query()
                .addOrderBy(new OrderByClause("facilities.created_at", "desc"));
        
        return super.all();
    }
    

    @Override
    public String toString() {
        return "Facility{" + "id=" + id + ", name=" + name + '}';
    }
}
