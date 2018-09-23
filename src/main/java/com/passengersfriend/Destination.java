package com.passengersfriend;

import java.sql.Timestamp;

public class Destination {
    private Long id;
    private String adep = "";
    private String ades ="";
    private String flight_code = "";
    private String flight_number = "";
    private String carrier_code = "";
    private String carrier_number = "";
    private String status_info = "";
    private java.sql.Timestamp schd_dep_lt;
    private java.sql.Timestamp schd_arr_lt;
    private java.sql.Timestamp est_dep_lt;
    private java.sql.Timestamp est_arr_lt;
    private java.sql.Timestamp act_dep_lt;
    private java.sql.Timestamp act_arr_lt;
    private int flt_leg_seq_no;
    private String aircraft_name_scheduled = "";
    private String baggage_info = "";//final record should contain comma-separated aggregation of previous values without duplicates (LIFO)
    private String counter = ""; //final record should contain comma-separated aggregation of previous values without duplicates (LIFO)
    private String gate_info = ""; //final record should contain comma-separated aggregation of previous values without duplicates (LIFO)
    private String lounge_info = "";//final record should contain comma-separated aggregation of previous values without duplicates (LIFO)
    private String terminal_info = "";
    private String arr_terminal_info = "";//final record should contain comma-separated aggregation of previous values without duplicates (LIFO)
    private String source_data = "";
    private java.sql.Timestamp created_at;
    private Key key;

    public Key getKey(){
        if (key==null) {
            key = new Key(flight_code, flight_number);
        }
        return key;
    }

    private String merge(String prev, String next){
        if (prev.length()>0) {
            if (prev.startsWith(next)){
                return prev;
            } else {
                return next+","+prev;
            }
        } else {
            return next;
        }
    }

    public Destination update(Destination d){


        if(!d.adep.isEmpty()) {
            this.adep = d.adep;
        }

        if(!d.ades.isEmpty()) {
            this.ades = d.ades;
        }

        if(!d.flight_code.isEmpty()) {
            this.flight_code = d.flight_code;
        }

        if(!d.flight_number.isEmpty()) {
            this.flight_number = d.flight_number;
        }

        if(!d.carrier_code.isEmpty()) {
            this.carrier_code = d.carrier_code;
        }

        if(!d.carrier_number.isEmpty()) {
            this.carrier_number = d.carrier_number;
        }

        if(!d.status_info.isEmpty()) {
            this.status_info = d.status_info;
        }

        if(d.schd_dep_lt!=null) {
            this.schd_dep_lt = d.schd_dep_lt;
        }

        if(d.schd_arr_lt!=null) {
            this.schd_arr_lt = d.schd_arr_lt;
        }

        if(d.est_dep_lt!=null){
            this.est_dep_lt = d.est_dep_lt;
        }

        if(d.est_arr_lt!=null) {
            this.est_arr_lt = d.est_arr_lt;
        }

        if(d.act_dep_lt!=null) {
            this.act_dep_lt = d.act_dep_lt;
        }

        if(d.act_arr_lt!=null) {
            this.act_arr_lt = d.act_arr_lt;
        }

        this.flt_leg_seq_no = d.flt_leg_seq_no;

        if(!d.aircraft_name_scheduled.isEmpty()) {
            this.aircraft_name_scheduled = d.aircraft_name_scheduled;
        }

        if(!d.baggage_info.isEmpty()) {
            this.baggage_info = merge(this.baggage_info, d.baggage_info);//final record should contain comma-separated aggregation of previous values without duplicates (LIFO)
        }

        if(!d.counter.isEmpty()) {
            this.counter = merge(this.counter, d.counter); //final record should contain comma-separated aggregation of previous values without duplicates (LIFO)
        }

        if(!d.gate_info.isEmpty()) {
            this.gate_info = merge(this.gate_info, d.gate_info); //final record should contain comma-separated aggregation of previous values without duplicates (LIFO)
        }

        if(!d.lounge_info.isEmpty()) {
            this.lounge_info = merge(this.lounge_info, d.lounge_info);//final record should contain comma-separated aggregation of previous values without duplicates (LIFO)
        }

        if(!d.terminal_info.isEmpty()) {
            this.terminal_info = d.terminal_info;
        }

        if(!d.arr_terminal_info.isEmpty()) {
            this.arr_terminal_info = d.arr_terminal_info;//final record should contain comma-separated aggregation of previous values without duplicates (LIFO)
        }

        if(!d.source_data.isEmpty()) {
            this.source_data = d.source_data;
        }

        this.created_at = d.created_at;

        return this;
    }

    public Destination(){

    }

    public Destination(Long id, String adep, String ades, String flight_code, String flight_number, String carrier_code, String carrier_number, String status_info, Timestamp schd_dep_lt, Timestamp schd_arr_lt, Timestamp est_dep_lt, Timestamp est_arr_lt, Timestamp act_dep_lt, Timestamp act_arr_lt, int flt_leg_seq_no, String aircraft_name_scheduled, String baggage_info, String counter, String gate_info, String lounge_info, String terminal_info, String arr_terminal_info, String source_data, Timestamp created_at) {
        this.id = id;
        this.adep = adep;
        this.ades = ades;
        this.flight_code = flight_code;
        this.flight_number = flight_number;
        this.carrier_code = carrier_code;
        this.carrier_number = carrier_number;
        this.status_info = status_info;
        this.schd_dep_lt = schd_dep_lt;
        this.schd_arr_lt = schd_arr_lt;
        this.est_dep_lt = est_dep_lt;
        this.est_arr_lt = est_arr_lt;
        this.act_dep_lt = act_dep_lt;
        this.act_arr_lt = act_arr_lt;
        this.flt_leg_seq_no = flt_leg_seq_no;
        this.aircraft_name_scheduled = aircraft_name_scheduled;
        this.baggage_info = baggage_info;
        this.counter = counter;
        this.gate_info = gate_info;
        this.lounge_info = lounge_info;
        this.terminal_info = terminal_info;
        this.arr_terminal_info = arr_terminal_info;
        this.source_data = source_data;
        this.created_at = created_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdep() {
        return adep;
    }

    public void setAdep(String adep) {
        this.adep = adep;
    }

    public String getAdes() {
        return ades;
    }

    public void setAdes(String ades) {
        this.ades = ades;
    }

    public String getFlight_code() {
        return flight_code;
    }

    public void setFlight_code(String flight_code) {
        this.flight_code = flight_code;
    }

    public String getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(String flight_number) {
        this.flight_number = flight_number;
    }

    public String getCarrier_code() {
        return carrier_code;
    }

    public void setCarrier_code(String carrier_code) {
        this.carrier_code = carrier_code;
    }

    public String getCarrier_number() {
        return carrier_number;
    }

    public void setCarrier_number(String carrier_number) {
        this.carrier_number = carrier_number;
    }

    public String getStatus_info() {
        return status_info;
    }

    public void setStatus_info(String status_info) {
        this.status_info = status_info;
    }

    public Timestamp getSchd_dep_lt() {
        return schd_dep_lt;
    }

    public void setSchd_dep_lt(Timestamp schd_dep_lt) {
        if(schd_dep_lt!=null) {
            this.schd_dep_lt = schd_dep_lt;
        }
    }

    public Timestamp getSchd_arr_lt() {
        return schd_arr_lt;
    }

    public void setSchd_arr_lt(Timestamp schd_arr_lt) {
        if(schd_arr_lt!=null) {
            this.schd_arr_lt = schd_arr_lt;
        }
    }

    public Timestamp getEst_dep_lt() {
        return est_dep_lt;
    }

    public void setEst_dep_lt(Timestamp est_dep_lt) {
        this.est_dep_lt = est_dep_lt;
    }

    public Timestamp getEst_arr_lt() {
        return est_arr_lt;
    }

    public void setEst_arr_lt(Timestamp est_arr_lt) {
        this.est_arr_lt = est_arr_lt;
    }

    public Timestamp getAct_dep_lt() {
        return act_dep_lt;
    }

    public void setAct_dep_lt(Timestamp act_dep_lt) {
        this.act_dep_lt = act_dep_lt;
    }

    public Timestamp getAct_arr_lt() {
        return act_arr_lt;
    }

    public void setAct_arr_lt(Timestamp act_arr_lt) {
        this.act_arr_lt = act_arr_lt;
    }

    public int getFlt_leg_seq_no() {
        return flt_leg_seq_no;
    }

    public void setFlt_leg_seq_no(int flt_leg_seq_no) {
        this.flt_leg_seq_no = flt_leg_seq_no;
    }

    public String getAircraft_name_scheduled() {
        return aircraft_name_scheduled;
    }

    public void setAircraft_name_scheduled(String aircraft_name_scheduled) {
        this.aircraft_name_scheduled = aircraft_name_scheduled;
    }

    public String getBaggage_info() {
        return baggage_info;
    }

    public void setBaggage_info(String baggage_info) {
        this.baggage_info = baggage_info;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getGate_info() {
        return gate_info;
    }

    public void setGate_info(String gate_info) {
        this.gate_info = gate_info;
    }

    public String getLounge_info() {
        return lounge_info;
    }

    public void setLounge_info(String lounge_info) {
        this.lounge_info = lounge_info;
    }

    public String getTerminal_info() {
        return terminal_info;
    }

    public void setTerminal_info(String terminal_info) {
        this.terminal_info = terminal_info;
    }

    public String getArr_terminal_info() {
        return arr_terminal_info;
    }

    public void setArr_terminal_info(String arr_terminal_info) {
        this.arr_terminal_info = arr_terminal_info;
    }

    public String getSource_data() {
        return source_data;
    }

    public void setSource_data(String source_data) {
        this.source_data = source_data;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }


}
