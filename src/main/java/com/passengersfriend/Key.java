package com.passengersfriend;

import java.sql.Timestamp;

public class Key {
    String flight_icao_code;
    String flight_number;

    public Key(String flight_icao_code, String flight_number) {
        this.flight_icao_code = flight_icao_code;
        this.flight_number = flight_number;
    }

    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("flight_icao_code=").append(flight_icao_code)
                .append(" flight_number=").append(flight_number);
        return sb.toString();
    }
}
