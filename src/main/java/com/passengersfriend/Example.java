package com.passengersfriend;

import com.passengersfriend.*;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import java.sql.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Example {
    private  static Session session = new Session();
    public static void main(String[] argv) {
        Date start = new Date();

        Settings settings = new Settings();

        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.postgresql.Driver.class);
        dataSource.setUsername(settings.userName);
        dataSource.setUrl(settings.url);//"jdbc:postgresql://127.0.0.1:5432/test"
        dataSource.setPassword(settings.password);//"admin"
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        HashMap<Key,Destination> map = new HashMap<>();

        if (session.state==State.FINISH){
            long savedCount = session.count;
            getCount(jdbcTemplate);
            if (savedCount != session.count){
                session.state = State.STARTED;
            }
        }

        if ((session.state==State.FINISH) || (session.state==State.STARTED)){
            session.idx = 0;
        }

        try {
            while (session.state!=State.FINISH) {
                switch (session.state) {
                    case State.STARTED:
                        System.out.println("Started");
                        initSession(jdbcTemplate);
                        System.out.println("Inited");
                        break;
                    case State.INIT:
                        System.out.println("Start Extract and Transform");
                        extractAndTransform(jdbcTemplate, map);
                        System.out.println("End Extract and Transform");
                        break;
                    case State.TRANSFORM:
                        System.out.println("Start clean DB");
                        cleanDB(jdbcTemplate);
                        System.out.println("End clean DB");
                        break;
                    case State.CLEAN:
                        System.out.println("Start load");
                        load(dataSource, map);
                        System.out.println("End load");
                        break;
                    case State.LOAD:
                        System.out.println("Start finish");
                        session.state = State.FINISH;
                        System.out.println("End finish");
                        break;
                    default:
                        break;
                }
            }
        } finally {
            session.save();
        }
        Date end = new Date();
        System.out.println("Size:" + map.size());
        System.out.println("Done. \n" +
                "If you want to restart the application when the database is unchanged, reset the state parameter to 0");
        long t = end.getTime() - start.getTime();
        System.out.println("it takes: " + t);
    }


    public static void load(SimpleDriverDataSource dataSource, HashMap<Key,Destination> map){
        session.state = State.LOAD;
        List<Destination> result = map.values().stream().collect(Collectors.toList());

        ExecutorService executorService = Executors.newFixedThreadPool(10000);
        int delta = 10000;
        while (session.idx<result.size()) {

            int idxEnd = session.idx + delta;
            if (idxEnd > result.size()){
                idxEnd = result.size();
            }
            Task task = new Task(session.idx, idxEnd,result,dataSource);
            executorService.submit(task);
            if (idxEnd == result.size()) {
                session.idx = result.size() + 1;
            } else {
                session.idx = idxEnd;
            }
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void initSession(JdbcTemplate jdbcTemplate){
        session.state = State.INIT;

        String firstSql = "select * from aenaflight_2017_01 limit 1";

        jdbcTemplate.query(
                firstSql,
                new RowMapper<Source>() {
                    @Override
                    public Source mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new Source(
                                rs.getLong("id"),
                                rs.getString("act_arr_date_time_lt"),
                                rs.getString("aircraft_name_scheduled"),
                                rs.getString("arr_apt_name_es"),
                                rs.getString("arr_apt_code_iata"),
                                rs.getString("baggage_info"),
                                rs.getString("carrier_airline_name_en"),
                                rs.getString("carrier_icao_code"),
                                rs.getString("carrier_number"),
                                rs.getString("counter"),
                                rs.getString("dep_apt_name_es"),
                                rs.getString("dep_apt_code_iata"),
                                rs.getString("est_arr_date_time_lt"),
                                rs.getString("est_dep_date_time_lt"),
                                rs.getString("flight_airline_name_en"),
                                rs.getString("flight_airline_name"),
                                rs.getString("flight_icao_code"),
                                rs.getString("flight_number"),
                                rs.getString("flt_leg_seq_no"),
                                rs.getString("gate_info"),
                                rs.getString("lounge_info"),
                                rs.getString("schd_arr_only_date_lt"),
                                rs.getString("schd_arr_only_time_lt"),
                                rs.getString("source_data"),
                                rs.getString("status_info"),
                                rs.getString("terminal_info"),
                                rs.getString("arr_terminal_info"),
                                rs.getLong("created_at"),
                                rs.getString("act_dep_date_time_lt"),
                                rs.getString("schd_dep_only_date_lt"),
                                rs.getString("schd_dep_only_time_lt"));
                    }
                }).forEach((s)->{
            session.currentId = s.getId();
        });

        getCount(jdbcTemplate);
    }

    public static void getCount(JdbcTemplate jdbcTemplate){
        String sqlCount = "SELECT count(*) FROM aenaflight_2017_01;";
        jdbcTemplate.query(
                sqlCount,
                new RowMapper<Count>() {
                    @Override
                    public Count mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new Count(
                                rs.getLong("count"));

                    }
                }).forEach((s)->{
            session.count = s.getCoount();
            session.maxId = session.currentId+session.count;
        });
    }

    public static void cleanDB(JdbcTemplate jdbcTemplate){
        session.state = State.CLEAN;
        jdbcTemplate.update("DELETE FROM destination_data");
    }

    public static void extractAndTransform(JdbcTemplate jdbcTemplate, HashMap<Key,Destination> map){
        session.state = State.TRANSFORM;
        String sql = "SELECT * FROM aenaflight_2017_01 where id BETWEEN ? AND ?;";
        while (session.currentId < session.maxId) {
            jdbcTemplate.query(
                    sql,
                    new Object[]{session.currentId+1, session.currentId+100000}, //+1 !!!!!!!
                    new RowMapper<Source>() {
                        @Override
                        public Source mapRow(ResultSet rs, int rowNum) throws SQLException {
                            return new Source(
                                    rs.getLong("id"),
                                    rs.getString("act_arr_date_time_lt"),
                                    rs.getString("aircraft_name_scheduled"),
                                    rs.getString("arr_apt_name_es"),
                                    rs.getString("arr_apt_code_iata"),
                                    rs.getString("baggage_info"),
                                    rs.getString("carrier_airline_name_en"),
                                    rs.getString("carrier_icao_code"),
                                    rs.getString("carrier_number"),
                                    rs.getString("counter"),
                                    rs.getString("dep_apt_name_es"),
                                    rs.getString("dep_apt_code_iata"),
                                    rs.getString("est_arr_date_time_lt"),
                                    rs.getString("est_dep_date_time_lt"),
                                    rs.getString("flight_airline_name_en"),
                                    rs.getString("flight_airline_name"),
                                    rs.getString("flight_icao_code"),
                                    rs.getString("flight_number"),
                                    rs.getString("flt_leg_seq_no"),
                                    rs.getString("gate_info"),
                                    rs.getString("lounge_info"),
                                    rs.getString("schd_arr_only_date_lt"),
                                    rs.getString("schd_arr_only_time_lt"),
                                    rs.getString("source_data"),
                                    rs.getString("status_info"),
                                    rs.getString("terminal_info"),
                                    rs.getString("arr_terminal_info"),
                                    rs.getLong("created_at"),
                                    rs.getString("act_dep_date_time_lt"),
                                    rs.getString("schd_dep_only_date_lt"),
                                    rs.getString("schd_dep_only_time_lt"));
                        }
                    }).forEach((s) -> {
                session.currentId = s.getId();
                Destination next = s.toDestination();
                Key key = next.getKey();
                Destination prev = map.get(key);
                if (prev == null) {
                    map.put(key, next);
                } else {
                    map.put(key, prev.update(next));
                }
            });

            session.currentId += 100000;

        }

    }
}