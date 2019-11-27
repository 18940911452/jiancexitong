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
import wg.user.mobileimsdk.server.model.vo.ManageUserVo;
import wg.user.mobileimsdk.server.model.vo.SearchVo;
import wg.user.mobileimsdk.server.util.CreateNamePicture;
import wg.user.mobileimsdk.server.util.MD5Util;

public class NewsService {
	public static NewsService ser=new NewsService();
	
	
	public Object[] getInfo(SearchVo search){

		Object[] res = new Object[2];
		String sql = "select * from news where status=1  and type="+search.getType();
		String countSql = "select * from news where  status=1  and  type="+search.getType() ;
		String name=search.getName();
		if(name != null && !"".equals(name)){
			if(!"".equals(name.trim())){
				sql += " AND title LIKE '%"+name+"%' ";
				countSql += " AND title LIKE '%"+name+"%' ";
			}
		}
		sql += " ORDER BY time DESC LIMIT " +(search.getPageNo()-1)*search.getPageSize()+","+search.getPageSize();
		countSql += " ORDER BY time DESC";
		// 查询
		List<News> list = News.dao.find(sql);
		List<News> countList = News.dao.find(countSql);
		for (int i = 0; i < list.size(); i++) {
			byte[] bytess = list.get(i).getBytes("content");
			try {
				String content = new String(bytess, "utf-8");
				//list.remove(i).get("content");
				content=delHtmlTag(content);
				list.get(i).put("content_", content);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		// 用户信息设置
		res[0] = list;
		res[1] = countList.size();
		return res;
	}
	
	public static String delHtmlTag(String str){ 
	    String newstr = ""; 
	    newstr = str.replaceAll("<[.[^>]]*>","");
	    newstr = newstr.replaceAll(" ", ""); 
	    return newstr;
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
	public News getInfoById(Integer id){
		String sql="select * from news where  id="+id ;
		News find = News.dao.findFirst(sql);
		byte[] bytess = find.getBytes("content");
		try {
			String content = new String(bytess, "utf-8");
			//list.remove(i).get("content");
			find.put("content_", content);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
					News userInfo = News.dao.findById(uid);
					userInfo.setStatus(-1);
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
