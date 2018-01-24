package com.task.job;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bolanggu.bbl.sms.BBLSMSUtil;
import com.pabula.api.API;
import com.pabula.common.util.StrUtil;
import com.pabula.db.ConnectionHelper;

public class NewYearShoppingJob implements Job{
	
	private Logger log = LoggerFactory.getLogger(NewYearShoppingJob.class);
	
	public static String CONTENT = "【云厨1站】恭喜您获得云厨一站年货大集入场券一张，请在2018年1月10日之前至云厨一站任一门店领取并登记报名（过期自动失效）";


	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Connection conn=null;
        Statement stmt=null;
        ResultSet rs=null;
        try{
        	List<Map> list = new  ArrayList<Map>();
        	conn=ConnectionHelper.getConnection("andsell");
            stmt=conn.createStatement();
            StringBuffer return_sql = new StringBuffer();
            return_sql.append("SELECT SUM(a.CHANGE_VALUE) AS `SPEND`,a.USER_ID AS `USER_ID`");
            return_sql.append(" FROM finance_list a");
            return_sql.append(" WHERE (a.`EVENT`='线下退款' or a.`EVENT`='线上退款')");
            return_sql.append(" AND a.ADD_DATETIME > '2017-12-10 00:00:00' AND a.ADD_DATETIME < '2018-01-10 00:00:00'");
            //sql.append(" AND a.USER_ID NOT IN");
            //sql.append(" (SELECT b.USER_ID FROM member_coupon b WHERE b.COUPON_ID=3025 GROUP BY b.USER_ID HAVING COUNT(1) >= 1)");
            return_sql.append(" GROUP BY a.USER_ID");
            rs=stmt.executeQuery(return_sql.toString());
            
            log.info(return_sql.toString());
            
            Map return_map = new HashMap();
            while(rs.next()){
            	int userId = rs.getInt("USER_ID");
            	int spend = rs.getInt("SPEND");
            	if(userId > 0){
            		return_map.put(userId, spend);
            	}
            }
            
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT SUM(a.CHANGE_VALUE) AS `SPEND`,a.USER_ID AS `USER_ID`,c.MOBILE AS `MOBILE`");
            sql.append(" FROM finance_list a LEFT JOIN member c ON a.USER_ID=c.USER_ID");
            sql.append(" WHERE (a.`EVENT`='线下消费' OR a.`EVENT`='线上消费')");
            sql.append(" AND a.ADD_DATETIME > '2017-12-10 00:00:00' AND a.ADD_DATETIME < '2018-01-10 00:00:00'");
            //sql.append(" AND a.USER_ID NOT IN");
            //sql.append(" (SELECT b.USER_ID FROM member_coupon b WHERE b.COUPON_ID=3025 GROUP BY b.USER_ID HAVING COUNT(1) >= 1)");
            sql.append(" GROUP BY a.USER_ID HAVING `SPEND` >= 30000");
            
            log.info(sql.toString());
            
            rs=stmt.executeQuery(sql.toString());
            while(rs.next()){
            	int userId = rs.getInt("USER_ID");
            	String mobile = rs.getString("MOBILE");
            	if(userId >0){
                	int spend = rs.getInt("SPEND") - (return_map.get(userId) == null?0:Integer.valueOf(return_map.get(userId).toString()));
                	if(spend >= 30000){
                		Map map1 = new HashMap();
                		map1.put("USER_ID", userId);
                		map1.put("MOBILE", mobile);
                		map1.put("SPEND", spend);
                		list.add(map1);
                	}
            		
            	}
            }
            StringBuffer sql_tmp = new StringBuffer();
            sql_tmp.append("SELECT USER_ID,COUNT(1) AS `COUPON_COUNT` FROM member_coupon WHERE COUPON_ID = 3025 GROUP BY USER_ID");
            /*sql_tmp.append("SELECT SUM(a.CHANGE_VALUE) AS `SPEND`,a.USER_ID AS `USER_ID`,c.MOBILE AS `MOBILE`");
            sql_tmp.append(" FROM finance_list a LEFT JOIN member c ON a.USER_ID=c.USER_ID");
            sql_tmp.append(" WHERE a.CHANGE_TYPE='decrease'");
            sql_tmp.append(" AND a.ADD_DATETIME > '2017-12-10 00:00:00' AND a.ADD_DATETIME < '2018-01-10 00:00:00'");
            sql_tmp.append(" AND a.USER_ID NOT IN");
            sql_tmp.append(" (SELECT b.USER_ID FROM member_coupon b WHERE b.COUPON_ID=3025 GROUP BY b.USER_ID HAVING COUNT(1) >= 2)");
            sql_tmp.append(" GROUP BY a.USER_ID HAVING `SPEND` >= 50000");*/
            
            log.info(sql_tmp.toString());
            rs=stmt.executeQuery(sql_tmp.toString());
            Map map2 = new HashMap();
            while(rs.next()){
            	int userId = rs.getInt("USER_ID");
            	int couponCount = rs.getInt("COUPON_COUNT");
            	if(userId > 0){
            		/*Map map2 = new HashMap();
            		map2.put("USER_ID", userId);
            		map2.put("MOBILE", mobile);
            		list.add(map2);*/
            		map2.put(userId, couponCount);
            	}
            }
            List<Map> userList = new ArrayList<Map>();
            for(int i=0;i<list.size();i++){
            	Map map = new HashMap();
            	map = list.get(i);
            	int spend = Integer.valueOf(map.get("SPEND").toString());
            	int userId =  Integer.valueOf(map.get("USER_ID").toString());
            	int couponCount = map2.get(userId) == null ? 0:Integer.valueOf(map2.get(userId).toString());
            	if(spend>=30000 && couponCount <1){
            		userList.add(map);
            	}
            	if(spend >=50000 && couponCount <2){
            		userList.add(map);
            	} 
            	if(spend >=80000 && couponCount <3){
            		userList.add(map);
            	}
            	if(spend >=110000 && couponCount <4){
            		userList.add(map);
            	}
            	if(spend >=140000 && couponCount <5){
            		userList.add(map);
            	}
            	
            }
            API api = new API();
            Map map = new HashMap();
            map.put("MEMBER_COUPON.COUPON_ID", 3025);
            map.put("MEMBER_COUPON.EXPIRED_TIME", "2018-01-10 00:00:00");
            map.put("MEMBER_COUPON.SERVICE_ID", 1000);
            BBLSMSUtil sms=new BBLSMSUtil();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(new Date());
            
            if(userList.size() > 0){
            	for(Map user:userList){
                	map.put("MEMBER_COUPON.USER_ID", user.get("USER_ID"));
                	api.call("/member/coupon/add", map);
                	sms.SendMessage(user.get("MOBILE").toString(), CONTENT);
                }
            }
            
            
        }catch(Exception e){
        	log.error(e.toString());
        }finally{
			ConnectionHelper.close(rs);
            ConnectionHelper.close(stmt);
            ConnectionHelper.close(conn);
		}
		
	}

}