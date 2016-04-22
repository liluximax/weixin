package com.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.weixin.model.token.AccessToken;
import com.weixin.util.WeixinUtil;

@Repository("access_token_dao")
public class AccessTokenDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public AccessToken getaccess_token(){
		
		String sql = " select * from access_token order by id desc limit 1 ";
		
		AccessToken access_token = new AccessToken();
		
		jdbcTemplate.query(sql, new RowCallbackHandler(){

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				access_token.setToken(rs.getString("token"));
				access_token.setCreate_time(rs.getLong("create_time"));
				access_token.setExpire_time(rs.getLong("expire_time"));
			}
			
		});
		return access_token;
	}
	
	public void insertAccessToken(String token){
		long create_time = System.currentTimeMillis()/1000;
		long expire_time = create_time + 3600;
		String sql = " insert into access_token (token,create_time,expire_time) values(:t,:c,:e)";
		MapSqlParameterSource source = new MapSqlParameterSource().addValue("t", token)
																   .addValue("c", create_time)
																   .addValue("e", expire_time);
		namedParameterJdbcTemplate.update(sql, source);
	}
	
	public AccessToken getaccess_token_u(){
		
		String sql = " select * from access_token_u order by id desc limit 1 ";
		
		AccessToken access_token = new AccessToken();
		
		jdbcTemplate.query(sql, new RowCallbackHandler(){

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				access_token.setToken(rs.getString("token"));
				access_token.setCreate_time(rs.getLong("create_time"));
				access_token.setExpire_time(rs.getLong("expire_time"));
			}
			
		});
		return access_token;
	}
	
	public void insertAccessToken_u(String token){
		long create_time = System.currentTimeMillis()/1000;
		long expire_time = create_time + 3600;
		String sql = " insert into access_token_u (token,create_time,expire_time) values(:t,:c,:e)";
		MapSqlParameterSource source = new MapSqlParameterSource().addValue("t", token)
																   .addValue("c", create_time)
																   .addValue("e", expire_time);
		namedParameterJdbcTemplate.update(sql, source);
	}
}
