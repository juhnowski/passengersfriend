package com.passengersfriend;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.util.ArrayList;
import java.util.List;

public class Task implements Runnable{
    private int idxStart;
    private int idxEnd;
    private List<Object[]> batch = new ArrayList<>();
    private static String batchSQL = "INSERT INTO destination_data (id, adep, ades, flight_code, flight_number, carrier_code, carrier_number, status_info, schd_dep_lt, schd_arr_lt, est_dep_lt, est_arr_lt, act_dep_lt, act_arr_lt, flt_leg_seq_no, aircraft_name_scheduled, baggage_info, counter, gate_info, lounge_info, terminal_info, arr_terminal_info, source_data, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private List<Destination> result;
    SimpleDriverDataSource dataSource;

    @Override
    public void run() {
        System.out.println("Started "+idxStart+" .. "+ idxEnd);
        JdbcTemplate jdbcTempl = new JdbcTemplate(dataSource);
        for (int i = idxStart; i<idxEnd; i++){
            Destination v = result.get(i);
            if((v.getSchd_dep_lt()!=null)&&(v.getSchd_arr_lt()!=null)) {
                batch.add(new Object[] {v.getId(), v.getAdep(), v.getAdes(), v.getFlight_code(), v.getFlight_number(), v.getCarrier_code(), v.getCarrier_number(), v.getStatus_info(), v.getSchd_dep_lt(), v.getSchd_arr_lt(), v.getEst_dep_lt(), v.getEst_arr_lt(), v.getAct_dep_lt(), v.getAct_arr_lt(), v.getFlt_leg_seq_no(), v.getAircraft_name_scheduled(), v.getBaggage_info(), v.getCounter(), v.getGate_info(), v.getLounge_info(), v.getTerminal_info(), v.getArr_terminal_info(), v.getSource_data(), v.getCreated_at()});
            }
        }
        jdbcTempl.batchUpdate(batchSQL,batch);
        System.out.println("Finished "+idxStart+" .. "+ idxEnd);
    }

    public Task(int startIndex, int endIndex, List<Destination> result, SimpleDriverDataSource dataSource){
        this.idxStart = startIndex;
        this.idxEnd = endIndex;
        this.result = result;
        this.dataSource = dataSource;
    }
}
