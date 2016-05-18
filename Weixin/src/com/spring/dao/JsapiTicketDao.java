package com.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.weixin.model.token.JsApiTicket;

@Repository
public class JsapiTicketDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	
	public JsApiTicket getJsApiTicket(){
		
		String sql = " select * from jsapi_ticket order by id desc limit 1 ";
		
		JsApiTicket jsApiTicket = new JsApiTicket();
		
		jdbcTemplate.query(sql, new RowCallbackHandler(){

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				jsApiTicket.setTicket(rs.getString("ticket"));
				jsApiTicket.setCreate_time(rs.getLong("create_time"));
				jsApiTicket.setExpire_time(rs.getLong("expire_time"));
			}
			
		});
		return jsApiTicket;
	}
	
	public void insertJsApiTicket(String ticket){
		long create_time = System.currentTimeMillis()/1000;
		long expire_time = create_time + 3600;
		String sql = " insert into jsapi_ticket (ticket,create_time,expire_time) values(:t,:c,:e)";
		MapSqlParameterSource source = new MapSqlParameterSource().addValue("t", ticket)
																   .addValue("c", create_time)
																   .addValue("e", expire_time);
		namedParameterJdbcTemplate.update(sql, source);
	}
	
	public JsApiTicket getJsApiTicket_u(){
		
		String sql = " select * from jsapi_ticket_u order by id desc limit 1 ";
		
		JsApiTicket jsApiTicket = new JsApiTicket();
		
		jdbcTemplate.query(sql, new RowCallbackHandler(){

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				jsApiTicket.setTicket(rs.getString("ticket"));
				jsApiTicket.setCreate_time(rs.getLong("create_time"));
				jsApiTicket.setExpire_time(rs.getLong("expire_time"));
			}
			
		});
		return jsApiTicket;
	}
	
	public void insertJsApiTicket_u(String ticket){
		long create_time = System.currentTimeMillis()/1000;
		long expire_time = create_time + 3600;
		String login_time = date.format(new Date());
		String sql = " insert into jsapi_ticket_u (ticket,create_time,expire_time,login_time) values(:t,:c,:e,:l)";
		MapSqlParameterSource source = new MapSqlParameterSource().addValue("t", ticket)
																   .addValue("c", create_time)
																   .addValue("e", expire_time)
																   .addValue("l", login_time);
		namedParameterJdbcTemplate.update(sql, source);
	}
}
