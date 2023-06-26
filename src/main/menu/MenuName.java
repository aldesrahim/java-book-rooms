/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package main.menu;

import java.awt.Component;
import main.application.forms.masterconsumption.MasterConsumptionForm;
import main.application.forms.masterfacility.MasterFacilityForm;
import main.application.forms.masterroom.MasterRoomForm;
import main.application.forms.mastertype.MasterTypeForm;
import main.application.forms.masteruser.MasterUserForm;
import main.application.forms.other.DashboardForm;
import main.application.forms.reservation.ReservationForm;

/**
 *
 * @author aldes
 */
public enum MenuName {
    DASHBOARD, MASTER_TYPE, MASTER_FACILITY, MASTER_ROOM, MASTER_CONSUMPTION, MASTER_USER, RESERVATION, REPORT_RESERVATION, REPORT_ROOM, REPORT_CONSUMPTION, REPORT_ACTIVITY_LOG;

    public Component getForm() {
        return switch (this) {
            case DASHBOARD ->
                new DashboardForm();
            case MASTER_TYPE ->
                new MasterTypeForm();
            case MASTER_FACILITY ->
                new MasterFacilityForm();
            case MASTER_ROOM ->
                new MasterRoomForm();
            case MASTER_CONSUMPTION ->
                new MasterConsumptionForm();
            case MASTER_USER ->
                new MasterUserForm();
            case RESERVATION ->
                new ReservationForm();
//            case REPORT_RESERVATION -> new ;
//            case REPORT_ROOM -> new ;
//            case REPORT_CONSUMPTION -> new ;
//            case REPORT_ACTIVITY_LOG -> new ;

            default ->
                null;
        };
    }

}
