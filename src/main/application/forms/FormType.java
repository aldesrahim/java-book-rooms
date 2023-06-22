/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package main.application.forms;

/**
 *
 * @author aldes
 */
public enum FormType {
    CREATE, EDIT, VIEW;
    
    public String getTitle() {
        return switch (this) {
            case CREATE -> "Tambah";
            case EDIT -> "Ubah";
            case VIEW -> "Lihat";
        };
    }
    
    public String getTitle(String param) {
        return getTitle() + " " + param;
    }
}
