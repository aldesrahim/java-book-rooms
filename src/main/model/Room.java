package main.model;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.model.helper.HandleImageUpload;
import main.util.query.QueryFetch;
import main.util.query.QueryUpdate;
import main.util.query.clause.JoinClause;
import main.util.query.clause.JoinOnClause;
import main.util.query.clause.OrderByClause;
import main.util.query.clause.SetClause;
import main.util.query.clause.WhereClause;

public class Room extends Model {

    private Long id;
    private Long typeId;
    private String name;
    private Integer capacity;
    private String description;
    private Type type;
    private Integer facilityCount;
    private String imagePath;
    private File imageFile;
    private List<FacilityRoom> facilityRoom = new ArrayList<>();

    public Room() {
    }

    public Room(Long id, Long typeId, String name, Integer capacity, String description) {
        this.id = id;
        this.typeId = typeId;
        this.name = name;
        this.capacity = capacity;
        this.description = description;
    }

    public Room(Long id, Long typeId, String name, Integer capacity, String description, Type type) {
        this.id = id;
        this.typeId = typeId;
        this.name = name;
        this.capacity = capacity;
        this.description = description;
        this.type = type;
    }

    public Room(Long id, Long typeId, String name, Integer capacity, String description, Type type, Integer facilityCount) {
        this.id = id;
        this.typeId = typeId;
        this.name = name;
        this.capacity = capacity;
        this.description = description;
        this.type = type;
        this.facilityCount = facilityCount;
    }

    public Room(Long id, Long typeId, String name, Integer capacity, String description, Type type, Integer facilityCount, List<FacilityRoom> facilityRoom) {
        this.id = id;
        this.typeId = typeId;
        this.name = name;
        this.capacity = capacity;
        this.description = description;
        this.type = type;
        this.facilityCount = facilityCount;
        this.facilityRoom = facilityRoom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Integer getFacilityCount() {
        return facilityCount;
    }

    public List<FacilityRoom> getFacilityRoom() {
        return facilityRoom;
    }

    public void setFacilityRoom(List<FacilityRoom> facilityRoom) {
        this.facilityRoom = facilityRoom;
    }

    public void addFacilityRoom(FacilityRoom facilityRoom) {
        this.facilityRoom.add(facilityRoom);
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
        return "rooms";
    }

    @Override
    public Room fromResultSet(ResultSet rs) throws SQLException {
        Type _type = new Type(
                rs.getLong("type_id"),
                rs.getString("type_name")
        );

        Room room = new Room(
                rs.getLong("id"),
                rs.getLong("type_id"),
                rs.getString("name"),
                rs.getInt("capacity"),
                rs.getString("description"),
                _type,
                rs.getInt("facility_count")
        );

        room.setImagePath(rs.getString("image_path"), true);

        return room;
    }

    @Override
    public QueryUpdate save() throws SQLException {
        QueryUpdate queryUpdate;

        query()
                .addSet(new SetClause("type_id", getTypeId()))
                .addSet(new SetClause("name", getName()))
                .addSet(new SetClause("capacity", getCapacity()))
                .addSet(new SetClause("description", getDescription()))
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
                        query()
                                .addSet(new SetClause("image_path", uploadedPath))
                                .addWhere(new WhereClause(getPrimaryKey(), getId()))
                                .update();
                        
                        setImagePath(uploadedPath, true);
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

    public void relationshipTypes() {
        query()
                .addSelect("types.name AS type_name")
                .addSelect("(SELECT COUNT(1) FROM facility_room pivot WHERE pivot.room_id = rooms.id) AS facility_count")
                .addJoin(new JoinClause((new Type()).getTable(), new JoinOnClause("types.id", "rooms.type_id")));
    }

    @Override
    public Room find(Object id) throws SQLException {
        relationshipTypes();

        QueryFetch fetch = query()
                .addSelect("rooms.*")
                .addWhere(new WhereClause(getPrimaryKey(), id))
                .fetch();

        ResultSet rs = fetch.get();

        if (!rs.next()) {
            return null;
        }

        Room item = fromResultSet(rs);

        FacilityRoom pivot = new FacilityRoom();
        ResultSet rsPivot = pivot.query()
                .addWhere(new WhereClause("room_id", id))
                .fetch()
                .get();

        while (rsPivot.next()) {
            item.addFacilityRoom(pivot.fromResultSet(rsPivot));
        }

        return item;
    }

    @Override
    public List all() throws SQLException {
        relationshipTypes();

        query()
                .addSelect("rooms.*")
                .addOrderBy(new OrderByClause("rooms.created_at", "desc"));

        return super.all();
    }

    @Override
    public Room scopeSearch(String term) {
        if (term == null || term.isEmpty()) {
            return this;
        }

        query()
                .addWhere(new WhereClause()
                        .addSub(new WhereClause("rooms.name", "like", "%" + term + "%", "OR"))
                        .addSub(new WhereClause("types.name", "like", "%" + term + "%", "OR"))
                        .addSub(new WhereClause("capacity", "like", term + "%", "OR"))
                        .addSub(new WhereClause("description", "like", "%" + term + "%", "OR")));

        return this;
    }

    @Override
    public String toString() {
        return "Room{" + "id=" + id + ", typeId=" + typeId + ", name=" + name + ", capacity=" + capacity + ", description=" + description + '}';
    }

}
