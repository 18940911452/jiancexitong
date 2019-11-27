package wg.user.mobileimsdk.server.service;

import java.security.NoSuchAlgorithmException;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;

import wg.user.mobileimsdk.server.model.fs.ManageUserInfo;
import wg.user.mobileimsdk.server.util.MD5Util;

public class LoginService {
	public static LoginService ser=new LoginService();
	
	/**
	 * 
	 * @method:Loginvalidate 
	 * @describe:验证登录是否争取
	 * @author:  gongxiangPang 
	 * @param :TODO
	 * @param name
	 * @param password
	 * 
	 * @return 0用户存在  1026 用户不存在 1027 密码错误
	 */
	public JSONObject Loginvalidate(String name,String password,String pth){
		JSONObject result=new JSONObject();
		int re=1026;
		String sql="select * from manage_user_info where user_name=? and status=1";
		ManageUserInfo manageUserInfo=ManageUserInfo.dao.findFirst(sql,new Object[]{name});
		if (manageUserInfo!=null) {
			String password2 = manageUserInfo.getPassword();
			if(password2!=null){
				String convertMD5 = MD5Util.string2MD5(password);
				if(password2.equals(convertMD5)){
					re=0;
					
					JSONObject temp=new JSONObject();
					temp.put("userId", manageUserInfo.get("user_id"));
					temp.put("userIcon", manageUserInfo.get("icon"));
					temp.put("depId", manageUserInfo.get("dep_id"));
					temp.put("tel", manageUserInfo.get("tel"));
					temp.put("nickName", manageUserInfo.get("nick_name"));
					temp.put("userName", manageUserInfo.get("user_name"));
					temp.put("longitude", manageUserInfo.getLongitude());
					temp.put("latitude", manageUserInfo.getLatitude());
					
					manageUserInfo.remove("password");
					result.put("result", temp);
				}else{
					re=1027;
					result.put("result", "密码错误");
				}
			}
		}else{
			re=1026;
			result.put("result", "用户名不存在");
		}
		result.put("code", re);
		return result;
	}
}
