package wg.user.mobileimsdk.server.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;

import ibasic.web.login.LoginUtil;
import wg.user.mobileimsdk.server.model.fs.ManageDepart;
import wg.user.mobileimsdk.server.model.fs.ManageUserInfo;
import wg.user.mobileimsdk.server.model.vo.ManageUserVo;
import wg.user.mobileimsdk.server.model.vo.MonitorConfigSearchVo;
import wg.user.mobileimsdk.server.util.CreateNamePicture;
import wg.user.mobileimsdk.server.util.MD5Util;

public class UserService {

	public static UserService ser =new UserService();
	public static String  headAdress=PropKit.get("headAdress");
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 回显用户信息
	 * @param id
	 * @return
	 */
	public JSONObject echoUser(String id){
		ManageUserInfo userInfo = ManageUserInfo.dao.findById(id);
		JSONObject jsonObj = new JSONObject();
		if(userInfo != null){
			jsonObj.put("uid", userInfo.getUserId());
			jsonObj.put("username", userInfo.getUserName());
			jsonObj.put("nickname", userInfo.getNickName());
			jsonObj.put("password",userInfo.getPassword());
			jsonObj.put("tel", userInfo.getTel());
			jsonObj.put("email", userInfo.getEmail());
			jsonObj.put("deptId", userInfo.getDepId());
			jsonObj.put("sex", userInfo.getSex());
			jsonObj.put("nation", userInfo.getNation());
			String icon=headAdress+userInfo.getIcon();
			jsonObj.put("icon", icon);
			jsonObj.put("birthdate", userInfo.getBirthdate());
			jsonObj.put("sex", userInfo.getSex());
			if(userInfo.getDepId() != null){
		        ManageDepart dept = ManageDepart.dao.findById(userInfo.getDepId());
		        if(dept != null){
		        	jsonObj.put("deptname", dept.getDepName());
		        }
			}
			

		}
		return jsonObj;
	}
	
	
	/**
	 * 查询用户信息
	 * @param userName
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Object[] loadUser1(ManageUserInfo user,MonitorConfigSearchVo search,String name){
		Object[] res = new Object[2];
		String sql = "SELECT user.*,dept.dep_name as depname FROM manage_user_info user  left join manage_depart dept on user.dep_id = dept.dep_id WHERE user.status = 1";
		String countSql = "SELECT user.*,dept.dep_name as depname FROM manage_user_info user left join manage_depart dept on user.dep_id = dept.dep_id WHERE user.status = 1 ";
		if(name != null && !"".equals(name)){
			if(!"".equals(name.trim())){
				sql += " AND user.user_name LIKE '%"+name+"%' ";
				countSql += " AND user.user_name LIKE '%"+name+"%' ";
			}
		}
		sql += " ORDER BY user.create_time DESC LIMIT " +(search.getPageNo()-1)*search.getPageSize()+","+search.getPageSize();
		countSql += " ORDER BY user.create_time DESC";
		// 查询
		List<ManageUserInfo> list = ManageUserInfo.dao.find(sql);
		List<ManageUserInfo> countList = ManageUserInfo.dao.find(countSql);
		List<ManageUserVo> vos = new ArrayList<>();
		// 用户信息设置
		for(ManageUserInfo userInfo : list){
			ManageUserVo vo = new ManageUserVo();
			vo.setId(Integer.parseInt(userInfo.get("user_id").toString()));
			vo.setUserName(userInfo.get("user_name").toString());
		    vo.setNickName(userInfo.get("nick_name").toString());
		    if(userInfo.get("icon") != null ){
		    	vo.setIcon(userInfo.get("icon").toString());
		    }
		    if(userInfo.get("depname") != null ){
		    	vo.setDeptName(userInfo.get("depname").toString());
		    }
		    //vo.setIcon(userInfo.get("icon").toString());
		    vo.setDeptId(Integer.parseInt(userInfo.get("dep_id").toString()));
		    if(userInfo.get("email") != null){
		    	vo.setEmail(userInfo.get("email").toString());
		    }
		    if(userInfo.get("sex") != null){
		    	vo.setSex((Integer) userInfo.get("sex"));
		    }
		    if(userInfo.get("nation") != null){
		    	vo.setNation( userInfo.get("nation").toString());
		    }
		    if(userInfo.get("icon") != null){
		    	String icon=headAdress+userInfo.getIcon().toString();
		    	vo.setIcon(icon);
		    }
		    if(userInfo.get("birthdate") != null){
		    	vo.setBirthdate( userInfo.get("birthdate").toString());
		    }
		    //vo.setEmail(userInfo.get("email").toString());
		    vo.setTel(userInfo.get("tel").toString());
		    vo.setCreateTime(userInfo.get("create_time").toString());
		    vo.setStatus(Integer.parseInt(userInfo.get("status").toString()));
		    if(userInfo.get("update_time") != null){
		    	vo.setUpdateTime(userInfo.get("update_time").toString());
		    }
		    //vo.setUpdateTime(userInfo.get("update_time").toString());
		
		    vos.add(vo);// 添加至返回
		}
		
		res[0] = vos;
		res[1] = countList.size();
		return res;
	}
	
	/**
	 * 添加用户
	 * @param param
	 * @param user
	 * @return
	 */
	public String addUser(String param,ManageUserInfo user,String headPath,String basePath ){
		boolean fianlStatus = true;
		final JSONObject json = JSONObject.parseObject(param);
		String headAddress="";
		try {
			final String username = json.getString("username");
			final String passwd = json.getString("password");
			final String nickname = json.getString("nickname");
			//final String email = json.getString("email");
			final String deptId = json.getString("dep_id");
			final Integer sex = json.getInteger("sex");
			final String nation = json.getString("nation");
			final String birthdate = json.getString("birthdate");
			//用户
			String timename = new SimpleDateFormat("yyyyMMddHHmmssSSS") .format(new Date() );
			
			/*if(nickname!=null && !nickname.isEmpty()){
				try {
					boolean generateImg = CreateNamePicture.generateImg(nickname,headPath, timename);//创建头像
					
					if(generateImg){
						headAddress=basePath+timename+".jpg";
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
			final String icon=headAddress;
			// 排重（对有效用户）
			String sql = "select * from manage_user_info where status = 1 and user_name = '"+username+"' ";
			System.out.println(sql);
			List<ManageUserInfo> list = ManageUserInfo.dao.find(sql);
			
			if(list != null && list.size()>0){
				return "exist";// 该用户已存在--用户名判断
			}else{
				fianlStatus = Db.tx(new IAtom() {
					@Override
					public boolean run() throws SQLException {
						boolean result = true;
						// 存入用户表信息
						ManageUserInfo userInfo = new ManageUserInfo();
						userInfo.setUserName(username);
						userInfo.setPassword(MD5Util.string2MD5(passwd));
						userInfo.setNickName(nickname);
						userInfo.setSex(sex);
						userInfo.setNation(nation);
						userInfo.setIcon(icon);
						userInfo.setBirthdate(birthdate);
						if(json.getString("email")!=null){//邮箱
							userInfo.setEmail(json.getString("email").toString());
						}
						if(json.getString("tel")!=null){//电话
							userInfo.setTel(json.getString("tel").toString());
						}
						if(json.getString("dep_id")!=null){//部门id
							System.out.println("deptId:"+deptId);
							userInfo.setDepId(Integer.parseInt(deptId));
						}
						userInfo.setCreateTime(new Date());
						// 保存添加用户
						result = userInfo.save();
					
						
						return result;
					}
				});
			}
			
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
	 * 删除用户
	 * @param id
	 * @param user
	 * @return
	 */
	public String deleteUser(String id,ManageUserInfo user){
		if(id!=null && !"".equals(id)){
			final String uid = id;
			boolean result = true;
			result = Db.tx(new IAtom() {
				@Override
				public boolean run() throws SQLException {
					boolean res = true;
					ManageUserInfo userInfo = ManageUserInfo.dao.findById(uid);
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
		
					ManageUserInfo userInfo = new ManageUserInfo();
					userInfo.setUserId(json.getInteger("id"));
					userInfo.setUserName(json.getString("username"));
					userInfo.setSex(json.getInteger("sex"));//0是女  1男   2是未知
					userInfo.setNation(json.getString("nation"));//
					userInfo.setBirthdate(json.getString("birthdate"));//
					if(!"".equals(json.getString("nickname"))) {
						userInfo.setNickName(json.getString("nickname"));
					}
					if(!"".equals(json.getString("email"))) {
						userInfo.setEmail(json.getString("email"));
					}
					if(!"".equals(json.getString("tel"))) {
						userInfo.setTel(json.getString("tel"));
					}
						userInfo.setDepId(json.getInteger("dep_id"));
					boolean result =  userInfo.update();
                    if(result) {
                    	   return "ok";
                    	}else {
                    		 return "error";
                    		}
                    	}
	/**
	 * 
	 * 名称：updatePassword 
	 * 描述：更新密码  UserService
	 * @author gongxiang.pang
	 * @parameter
	 * @return 
	 * @return
	 */
	 public JSONObject updatePassword(String uid){
		 JSONObject result=new JSONObject();
			ManageUserInfo userInfo = ManageUserInfo.dao.findById(uid);
					String newconvertMD5 = MD5Util.string2MD5(userInfo.getUserName());
						 int re=Db.use("wg_fs_common").update("update manage_user_info set  password= '" + newconvertMD5 + "' where user_id = ?",uid);
					if(re>0) {
						    result.put("data", "true");
							result.put("status", "1");
							result.put("message", "重置密码成功");
					}else {
						    result.put("data", "error");
							result.put("status", "-1");
							result.put("message", "重置密码失败");
					}
						
         			return result;
		 
	 }
	 
	
               
}
