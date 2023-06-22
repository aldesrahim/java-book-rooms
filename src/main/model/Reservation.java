/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import main.util.query.QueryUpdate;
import main.util.query.clause.JoinClause;
import main.util.query.clause.JoinOnClause;
import main.util.query.clause.SetClause;
import main.util.query.clause.WhereClause;

/**
 *
 * @author aldes
 */
public class Reservation extends Model {

    private Long id;
    private Long roomId;
    private String name;
    private String phoneNumber;
    private Integer attendance;
    private String subject;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private LocalDateTime checkedInAt;
    private LocalDateTime checkedOutAt;
    private Status status;
    private Room room;
    private Integer consumptionCount;
    private List<ConsumptionReservation> consumptionRoom = new ArrayList<>();

    public Reservation() {
    }

    public Reservation(
            Long id,
            Long roomId,
            String name,
            String phoneNumber,
            Integer attendance,
            String subject,
            LocalDateTime started_at,
            LocalDateTime ended_at,
            LocalDateTime checked_in_at,
            LocalDateTime checked_out_at,
            Status status
    ) {
        this.id = id;
        this.roomId = roomId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.attendance = attendance;
        this.subject = subject;
        this.startedAt = started_at;
        this.endedAt = ended_at;
        this.checkedInAt = checked_in_at;
        this.checkedOutAt = checked_out_at;
        this.status = status;
    }

    public Reservation(
            Long id,
            Long roomId,
            String name,
            String phoneNumber,
            Integer attendance,
            String subject,
            LocalDateTime started_at,
            LocalDateTime ended_at,
            LocalDateTime checked_in_at,
            LocalDateTime checked_out_at,
            Status status,
            Room room
    ) {
        this.id = id;
        this.roomId = roomId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.attendance = attendance;
        this.subject = subject;
        this.startedAt = started_at;
        this.endedAt = ended_at;
        this.checkedInAt = checked_in_at;
        this.checkedOutAt = checked_out_at;
        this.status = status;
        this.room = room;
    }

    public Reservation(
            Long id,
            Long roomId,
            String name,
            String phoneNumber,
            Integer attendance,
            String subject,
            LocalDateTime started_at,
            LocalDateTime ended_at,
            LocalDateTime checked_in_at,
            LocalDateTime checked_out_at,
            Status status,
            Room room,
            Integer consumptionCount
    ) {
        this.id = id;
        this.roomId = roomId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.attendance = attendance;
        this.subject = subject;
        this.startedAt = started_at;
        this.endedAt = ended_at;
        this.checkedInAt = checked_in_at;
        this.checkedOutAt = checked_out_at;
        this.status = status;
        this.room = room;
        this.consumptionCount = consumptionCount;
    }

    public Reservation(
            Long id,
            Long roomId,
            String name,
            String phoneNumber,
            Integer attendance,
            String subject,
            LocalDateTime started_at,
            LocalDateTime ended_at,
            LocalDateTime checked_in_at,
            LocalDateTime checked_out_at,
            Status status,
            Room room,
            Integer consumptionCount,
            List<ConsumptionReservation> consumptionRoom
    ) {
        this.id = id;
        this.roomId = roomId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.attendance = attendance;
        this.subject = subject;
        this.startedAt = started_at;
        this.endedAt = ended_at;
        this.checkedInAt = checked_in_at;
        this.checkedOutAt = checked_out_at;
        this.status = status;
        this.room = room;
        this.consumptionCount = consumptionCount;
        this.consumptionRoom = consumptionRoom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }

    public LocalDateTime getCheckedInAt() {
        return checkedInAt;
    }

    public void setCheckedInAt(LocalDateTime checkedInAt) {
        this.checkedInAt = checkedInAt;
    }

    public LocalDateTime getCheckedOutAt() {
        return checkedOutAt;
    }

    public void setCheckedOutAt(LocalDateTime checkedOutAt) {
        this.checkedOutAt = checkedOutAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Integer getConsumptionCount() {
        return consumptionCount;
    }

    public List<ConsumptionReservation> getConsumptionReservation() {
        return consumptionRoom;
    }

    public void setConsumptionReservation(List<ConsumptionReservation> consumptionRoom) {
        this.consumptionRoom = consumptionRoom;
    }

    public void addConsumptionReservation(ConsumptionReservation consumptionRoom) {
        this.consumptionRoom.add(consumptionRoom);
    }

    @Override
    public String getTable() {
        return "reservations";
    }

    @Override
    public Object fromResultSet(ResultSet rs) throws SQLException {
        return new Reservation(
                rs.getLong("id"),
                rs.getLong("room_id"),
                rs.getString("name"),
                rs.getString("phone_number"),
                rs.getInt("attendance"),
                rs.getString("subject"),
                rs.getTimestamp("started_at").toLocalDateTime(),
                rs.getTimestamp("ended_at").toLocalDateTime(),
                rs.getTimestamp("checked_in_at").toLocalDateTime(),
                rs.getTimestamp("checked_out_at").toLocalDateTime(),
                Status.parse(rs.getInt("status")),
                new Room(
                        rs.getLong("room_id"),
                        rs.getLong("room_type_id"),
                        rs.getString("room_name"),
                        rs.getInt("room_capacity"),
                        rs.getString("room_description"),
                        new Type(
                                rs.getLong("room_type_id"),
                                rs.getString("type_name")
                        )
                )
        );
    }

    @Override
    public Reservation scopeSearch(String term) {
        if (term == null || term.isEmpty()) {
            return this;
        }

        Integer searchStatus = null;
        for (Status item : Status.values()) {
            if (term.equalsIgnoreCase(item.toString())) {
                searchStatus = item.toInt();
            }
        }

        query()
                .addWhere(new WhereClause(
                        new WhereClause("name", "like", "%" + term + "%", "OR")
                                .addSub(new WhereClause("phone_number", "like", "%" + term + "%", "OR"))
                                .addSub(new WhereClause("attendance", "like", "%" + term + "%", "OR"))
                                .addSub(new WhereClause("subject", "like", "%" + term + "%", "OR"))
                                .addSub(new WhereClause("status", (int) searchStatus), searchStatus != null)
                ));

        return this;
    }

    @Override
    public QueryUpdate save() throws SQLException {
        QueryUpdate queryUpdate;

        query()
                .addSet(new SetClause("room_id", getRoomId()))
                .addSet(new SetClause("name", getName()))
                .addSet(new SetClause("phone_number", getPhoneNumber()))
                .addSet(new SetClause("attendance", getAttendance()))
                .addSet(new SetClause("subject", getSubject()))
                .addSet(new SetClause("started_at", getStartedAt()))
                .addSet(new SetClause("ended_at", getEndedAt()))
                .addSet(new SetClause("checked_in_at", getCheckedInAt()))
                .addSet(new SetClause("checked_out_at", getCheckedOutAt()))
                .addSet(new SetClause("status", getStatus().toInt()));

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

    public void relationshipRoom() {
        query()
                .addSelect("reservations.*")
                .addSelect("rooms.name AS room_name")
                .addSelect("rooms.type_id AS room_type_id")
                .addSelect("rooms.capacity AS room_capacity")
                .addSelect("rooms.description AS room_description")
                .addSelect("types.name AS type_name")
                .addJoin(new JoinClause((new Room()).getTable(), new JoinOnClause("rooms.id", "reservations.room_id")))
                .addJoin(new JoinClause((new Type()).getTable(), new JoinOnClause("types.id", "rooms.type_id")));
    }

    @Override
    public Reservation find(Object id) throws SQLException {
        relationshipRoom();

        return (Reservation) super.find(id);
    }

    @Override
    public List all() throws SQLException {
        relationshipRoom();

        return super.all();
    }

    public enum Status {
        BOOKED, CHECKED_IN, CHECKED_OUT, CANCELED;

        @Override
        public String toString() {
            return switch (this) {
                case BOOKED ->
                    "Dipesan";
                case CHECKED_IN ->
                    "Check In";
                case CHECKED_OUT ->
                    "Check Out";
                default ->
                    "Dibatalkan";
            };
        }

        public Integer toInt() {
            return switch (this) {
                case BOOKED ->
                    0;
                case CHECKED_IN ->
                    1;
                case CHECKED_OUT ->
                    2;
                default ->
                    3;
            };
        }

        public static Status parse(int status) {
            return switch (status) {
                case 0 ->
                    BOOKED;
                case 1 ->
                    CHECKED_IN;
                case 2 ->
                    CHECKED_OUT;
                default ->
                    CANCELED;
            };

        }

    }
}
