package main.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.util.query.QueryFetch;
import main.util.query.QueryUpdate;
import main.util.query.clause.JoinClause;
import main.util.query.clause.JoinOnClause;
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
        
        return new Room(
                rs.getLong("id"),
                rs.getLong("type_id"),
                rs.getString("name"),
                rs.getInt("capacity"),
                rs.getString("description"),
                _type,
                rs.getInt("facility_count")
        );
    }
    
    @Override
    public QueryUpdate save() throws SQLException {
        QueryUpdate queryUpdate;
        
        query()
                .addSet(new SetClause("type_id", getTypeId()))
                .addSet(new SetClause("name", getName()))
                .addSet(new SetClause("capacity", getCapacity()))
                .addSet(new SetClause("description", getDescription()));
        
        if (getId() == null) {
            queryUpdate = query()
                    .insert();
            
            setId(queryUpdate.getInsertedId());
        } else {
            queryUpdate = query()
                    .addWhere(new WhereClause(getPrimaryKey(), getId()))
                    .update();
        }
        
        return queryUpdate;
    }
    
    @Override
    public QueryUpdate delete() throws SQLException {
        return delete(getId());
    }
    
    @Override
    public Room find(Object id) throws SQLException {
        QueryFetch fetch = query()
                .addSelect("rooms.*")
                .addSelect("types.name AS type_name")
                .addSelect("(SELECT COUNT(1) FROM facility_room pivot WHERE pivot.room_id = rooms.id) AS facility_count")
                .addJoin(new JoinClause((new Type()).getTable(), new JoinOnClause("types.id", "rooms.type_id")))
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
        List<Room> rows = new ArrayList<>();
        
        ResultSet rs = query()
                .addSelect("rooms.*")
                .addSelect("types.name AS type_name")
                .addSelect("(SELECT COUNT(*) FROM facility_room pivot WHERE pivot.room_id = rooms.id) AS facility_count")
                .addJoin(new JoinClause((new Type()).getTable(), new JoinOnClause("types.id", "rooms.type_id"), "LEFT JOIN"))
                .fetch()
                .get();
        
        while (rs.next()) {
            rows.add(fromResultSet(rs));
        }
        
        return rows;
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
