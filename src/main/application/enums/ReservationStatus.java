package main.application.enums;

/**
 *
 */
public enum ReservationStatus {
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

    public static ReservationStatus parse(int status) {
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
