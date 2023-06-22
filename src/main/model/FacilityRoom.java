/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import main.util.query.QueryUpdate;
import main.util.query.clause.SetClause;
import main.util.query.clause.WhereClause;

/**
 *
 * @author aldes
 */
public class FacilityRoom extends Model {

    private Long facilityId;
    private Long roomId;
    private Integer qty;
    private Facility facility;
    private Room room;

    public FacilityRoom() {
    }

    public FacilityRoom(Long facilityId, Long roomId, Integer qty) {
        this.facilityId = facilityId;
        this.roomId = roomId;
        this.qty = qty;
    }

    public FacilityRoom(Long facilityId, Long roomId, Integer qty, Facility facility, Room room) {
        this.facilityId = facilityId;
        this.roomId = roomId;
        this.qty = qty;
        this.facility = facility;
        this.room = room;
    }

    public Long getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
    
    @Override
    public String getTable() {
        return "facility_room";
    }

    @Override
    public FacilityRoom fromResultSet(ResultSet rs) throws SQLException {
        return new FacilityRoom(
                rs.getLong("facility_id"), 
                rs.getLong("room_id"),
                rs.getInt("qty")
        );
    }

    @Override
    public QueryUpdate save() throws SQLException {
        return query()
                .addSet(new SetClause("facility_id", getFacilityId()))
                .addSet(new SetClause("room_id", getRoomId()))
                .addSet(new SetClause("qty", getQty()))
                .insert();
    }

    @Override
    public QueryUpdate delete() throws SQLException {
        return query()
                .addWhere(new WhereClause("facility_id", getFacilityId()))
                .addWhere(new WhereClause("room_id", getRoomId()))
                .delete();
    }

    @Override
    public String toString() {
        return "FacilityRoom{" + "facilityId=" + facilityId + ", roomId=" + roomId + ", qty=" + qty + '}';
    }
    
}
