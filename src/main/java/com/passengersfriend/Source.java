package com.passengersfriend;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class Source {

  private Long id;
  private String act_arr_date_time_lt;
  private String aircraft_name_scheduled;
  private String arr_apt_name_es;
  private String arr_apt_code_iata;
  private String baggage_info;
  private String carrier_airline_name_en;
  private String carrier_icao_code;
    private String carrier_number;
    private String counter;
    private String dep_apt_name_es;
    private String dep_apt_code_iata;
    private String est_arr_date_time_lt;
    private String est_dep_date_time_lt;
    private String flight_airline_name_en;
    private String flight_airline_name;
    private String flight_icao_code;
    private String flight_number;
    private String flt_leg_seq_no;
    private String gate_info;
    private String lounge_info;
    private String schd_arr_only_date_lt;
    private String schd_arr_only_time_lt;
    private String source_data;
    private String status_info;
    private String terminal_info;
    private String arr_terminal_info;
    private Long created_at;
    private String act_dep_date_time_lt;
    private String schd_dep_only_date_lt;
    private String schd_dep_only_time_lt;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm");
    //SimpleDateFormat sdft = new SimpleDateFormat("hh:mm");

    public Source(Long id, String act_arr_date_time_lt, String aircraft_name_scheduled, String arr_apt_name_es, String arr_apt_code_iata, String baggage_info, String carrier_airline_name_en, String carrier_icao_code, String carrier_number, String counter, String dep_apt_name_es, String dep_apt_code_iata, String est_arr_date_time_lt, String est_dep_date_time_lt, String flight_airline_name_en, String flight_airline_name, String flight_icao_code, String flight_number, String flt_leg_seq_no, String gate_info, String lounge_info, String schd_arr_only_date_lt, String schd_arr_only_time_lt, String source_data, String status_info, String terminal_info, String arr_terminal_info, Long created_at, String act_dep_date_time_lt, String schd_dep_only_date_lt, String schd_dep_only_time_lt) {
        this.id = id;
        this.act_arr_date_time_lt = act_arr_date_time_lt;
        this.aircraft_name_scheduled = aircraft_name_scheduled;
        this.arr_apt_name_es = arr_apt_name_es;
        this.arr_apt_code_iata = arr_apt_code_iata;
        this.baggage_info = baggage_info;
        this.carrier_airline_name_en = carrier_airline_name_en;
        this.carrier_icao_code = carrier_icao_code;
        this.carrier_number = carrier_number;
        this.counter = counter;
        this.dep_apt_name_es = dep_apt_name_es;
        this.dep_apt_code_iata = dep_apt_code_iata;
        this.est_arr_date_time_lt = est_arr_date_time_lt;
        this.est_dep_date_time_lt = est_dep_date_time_lt;
        this.flight_airline_name_en = flight_airline_name_en;
        this.flight_airline_name = flight_airline_name;
        this.flight_icao_code = flight_icao_code;
        this.flight_number = flight_number;
        this.flt_leg_seq_no = flt_leg_seq_no;
        this.gate_info = gate_info;
        this.lounge_info = lounge_info;
        this.schd_arr_only_date_lt = schd_arr_only_date_lt;
        this.schd_arr_only_time_lt = schd_arr_only_time_lt;
        this.source_data = source_data;
        this.status_info = status_info;
        this.terminal_info = terminal_info;
        this.arr_terminal_info = arr_terminal_info;
        this.created_at = created_at;
        this.act_dep_date_time_lt = act_dep_date_time_lt;
        this.schd_dep_only_date_lt = schd_dep_only_date_lt;
        this.schd_dep_only_time_lt = schd_dep_only_time_lt;
    }

    public long getId(){
        return id;
    }

    public String toString(){
        return "id="+id + " flight_icao_code=" + flight_icao_code + " flight_number=" + flight_number + " schd_dep_only_date_lt="+schd_dep_only_date_lt;
    }

    public Destination toDestination() {




        Destination d = new Destination();
        d.setId(id);
        if((act_arr_date_time_lt!=null)&&(!act_arr_date_time_lt.isEmpty())) {
            try {
                d.setAct_arr_lt(new Timestamp(sdf.parse(act_arr_date_time_lt).getTime()));
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        d.setAircraft_name_scheduled(aircraft_name_scheduled);

        d.setAdes(arr_apt_code_iata);
        d.setBaggage_info(baggage_info);

        d.setCarrier_code(carrier_icao_code);
        d.setCarrier_number(carrier_number);

        d.setAdep(dep_apt_code_iata);

        if(!est_arr_date_time_lt.isEmpty()) {
            try {
                d.setEst_arr_lt(new Timestamp(sdf.parse(est_arr_date_time_lt).getTime()));
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        if(!est_dep_date_time_lt.isEmpty()) {
            try {
                d.setEst_dep_lt(new Timestamp(sdf.parse(est_dep_date_time_lt).getTime()));

            } catch (Exception e){
                e.printStackTrace();
                System.out.println(est_dep_date_time_lt);
            }
        }

        d.setFlight_code(flight_icao_code);
        d.setFlight_number(flight_number);
        d.setFlt_leg_seq_no(Integer.parseInt(flt_leg_seq_no));
        d.setGate_info(gate_info);
        d.setLounge_info(lounge_info);

        if (!schd_arr_only_time_lt.isEmpty()) {
            try {
                d.setSchd_arr_lt(new Timestamp(sdf.parse(schd_arr_only_date_lt+" "+ schd_arr_only_time_lt).getTime()));
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        d.setSource_data(source_data);
        d.setStatus_info(status_info);
        d.setTerminal_info(terminal_info);
        d.setArr_terminal_info(arr_terminal_info);

        if (!act_dep_date_time_lt.isEmpty()) {
            try {
                d.setAct_dep_lt(new Timestamp(sdf.parse(act_dep_date_time_lt).getTime()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if ((!schd_dep_only_date_lt.isEmpty())&&(!schd_dep_only_time_lt.isEmpty())){
            try {
                d.setSchd_dep_lt(new Timestamp(sdf.parse(schd_dep_only_date_lt + " " + schd_dep_only_time_lt).getTime()));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("id="+id+" schd_dep_only_date_lt="+schd_dep_only_date_lt+" schd_dep_only_time_lt="+schd_dep_only_time_lt);
            }
        }
        d.setCreated_at(new Timestamp(created_at));
/*
        if ((flight_icao_code.equals("IBK"))&& (flight_number.equals("6071"))) {
            if (d.getSchd_dep_lt()==null){
                System.out.println("null");
            } else {
                System.out.println(sdf.format(d.getSchd_dep_lt()));
            }
        }
*/
        return d;
    }


}
