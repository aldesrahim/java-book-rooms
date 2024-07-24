package main.application.forms;

/**
 *
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
