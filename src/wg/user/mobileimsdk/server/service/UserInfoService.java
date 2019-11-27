package wg.user.mobileimsdk.server.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;

import wg.user.mobileimsdk.server.model.fs.ManageUserInfo;
import wg.user.mobileimsdk.server.model.fs.News;
import wg.user.mobileimsdk.server.model.fs.Userinfo;
import wg.user.mobileimsdk.server.model.vo.ManageUserVo;
import wg.user.mobileimsdk.server.model.vo.SearchVo;
import wg.user.mobileimsdk.server.util.CreateNamePicture;
import wg.user.mobileimsdk.server.util.MD5Util;

public class UserInfoService {
	public static UserInfoService ser=new UserInfoService();
	
	
	public Object[] getInfo(SearchVo search){

		Object[] res = new Object[2];
		String sql = "select * from userinfo where status=1  ";
		String countSql = "select * from userinfo where  status=1  " ;
		String name=search.getName();
		if(name != null && !"".equals(name)){
			if(!"".equals(name.trim())){
				sql += " AND name LIKE '%"+name+"%' ";
				countSql += " AND name LIKE '%"+name+"%' ";
			}
		}
		sql += " ORDER BY update_time DESC LIMIT " +(search.getPageNo()-1)*search.getPageSize()+","+search.getPageSize();
		countSql += " ORDER BY update_time DESC";
		// 查询
		List<Userinfo> list = Userinfo.dao.find(sql);
		List<Userinfo> countList = Userinfo.dao.find(countSql);
		
		// 用户信息设置
		res[0] = list;
		res[1] = countList.size();
		return res;
	}
	
	public String addNews(String param,ManageUserInfo user ){
		boolean fianlStatus = true;
		final JSONObject json = JSONObject.parseObject(param);
		String headAddress="";
		try {
			final String title = json.getString("title");
			final String content = json.getString("content");
			final String author = json.getString("author");
			final String source = json.getString("source");
			final Integer type = json.getInteger("type");
			final String preimage = json.getString("preimage");
			
			fianlStatus = Db.tx(new IAtom() {
				@Override
				public boolean run() throws SQLException {
					boolean result = true;
					News news=new News();
					news.setTitle(title);
					byte [] img = null;
					try {
					    img = content.getBytes("utf-8");
					} catch (UnsupportedEncodingException e) {
					    //TODO
					}
					news.setContent(img);
					news.setAuthor(author);
					news.setSource(source);
					news.setType(type);
					news.setStatus(1);
					news.setPreimage(preimage);
					result = news.save();
					return result;
				}
			});
		
			
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
		if(fianlStatus == true){// 结果返回
			return "success";
		}else{
			return "fail";
		}
	}
	/**
	 * 
	 * 根据id获取信息
	 * @param id
	 * @return
	 */
	public Userinfo getInfoById(Integer id){
		String sql="select * from userinfo where  id="+id ;
		Userinfo find = Userinfo.dao.findFirst(sql);
		return find;
	}
	
	
	public String deleteNews(String id,ManageUserInfo user){
		if(id!=null && !"".equals(id)){
			final String uid = id;
			boolean result = true;
			result = Db.tx(new IAtom() {
				@Override
				public boolean run() throws SQLException {
					boolean res = true;
					Userinfo userInfo = Userinfo.dao.findById(uid);
					userInfo.setStatus(0);
					res = userInfo.update();
					return res;
				}
			});
			if(result == true){
				return "ok";
			}else{
				return "error";
			}
		}else{
			return "error";
		}
		
	}
	
	public String editUser(String param) {
		// TODO Auto-generated method stub
		final JSONObject json = JSONObject.parseObject(param);
				News news =new News();
				news.setId(json.getInteger("id"));
				news.setType(json.getInteger("type"));
				news.setTitle(json.getString("title"));
				news.setAuthor(json.getString("author"));
				if(!"".equals(json.getString("preimage")) &&null!=json.getString("preimage")){
					news.setPreimage(json.getString("preimage"));
				}
				news.setSource(json.getString("source"));
				String content=json.getString("content");
				byte [] img = null;
				try {
				    img = content.getBytes("utf-8");
				} catch (UnsupportedEncodingException e) {
				    //TODO
				}
				news.setContent(img);
				boolean result =  news.update();
                if(result) {
                	   return "ok";
                	}else {
                		 return "error";
                		}
               }
}
