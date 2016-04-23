package com.spring.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

@Repository("stationDao")
public class StationListDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Map<String, String>> getStationByCity(String city){
		String sql = " select * from station_list where city = ? ";
		List<Map<String, String>> result = new LinkedList<>();
		
		jdbcTemplate.query(sql, new PreparedStatementSetter(){

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setString(1, city);
			}
			
		}, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, String> single = new LinkedHashMap<>();
				single.put("station_id", rs.getString("station_id"));
				single.put("latitude", rs.getString("lat"));
				single.put("longitude", rs.getString("lng"));
				single.put("name", rs.getString("name"));
				single.put("address", rs.getString("address"));
				result.add(single);
			}
		});
		return result;
	}	
	
	public void insert(){
		String sql = " insert into station_list (station_id,lat,lng,name,adress,city) values (1004,'116.3568','39.9557','北京师范大学','新街口外大街','北京市')";
		jdbcTemplate.update(sql);
	}
	
	public List<Map<String, String>> getCityList(){
		String sql = " select * from city_list ";
		List<Map<String, String>> result = new LinkedList<>();
		jdbcTemplate.query(sql, new RowCallbackHandler(){

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, String> row = new LinkedHashMap<>();
				row.put("city", rs.getString("city"));
				result.add(row);
			}
			
		});
		return result;
	}
}
