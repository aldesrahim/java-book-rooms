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
public class ConsumptionReservation extends Model {
    private Long consumptionId;
    private Long reservationId;
    private Integer qty;

    public ConsumptionReservation() {
    }

    public ConsumptionReservation(Long consumptionId, Long reservationId, Integer qty) {
        this.consumptionId = consumptionId;
        this.reservationId = reservationId;
        this.qty = qty;
    }

    public Long getConsumptionId() {
        return consumptionId;
    }

    public void setConsumptionId(Long consumptionId) {
        this.consumptionId = consumptionId;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    @Override
    public String getTable() {
        return "consumption_reservation";
    }

    @Override
    public ConsumptionReservation fromResultSet(ResultSet rs) throws SQLException {
        return new ConsumptionReservation(
                rs.getLong("consumption_id"),
                rs.getLong("reservation_id"), 
                rs.getInt("qty")
        );
    }

    @Override
    public QueryUpdate save() throws SQLException {
        return query()
                .addSet(new SetClause("consumption_id", getConsumptionId()))
                .addSet(new SetClause("reservation_id", getReservationId()))
                .addSet(new SetClause("qty", getQty()))
                .insert();
    }

    @Override
    public QueryUpdate delete() throws SQLException {
        return query()
                .addWhere(new WhereClause("consumption_id", getConsumptionId()))
                .addWhere(new WhereClause("reservation_id", getReservationId()))
                .delete();
    }

    @Override
    public String toString() {
        return "ConsumptionReservation{" + "consumptionId=" + consumptionId + ", reservationId=" + reservationId + ", qty=" + qty + '}';
    }
}
