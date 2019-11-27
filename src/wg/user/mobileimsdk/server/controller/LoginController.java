package wg.user.mobileimsdk.server.controller;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;

import ibasic.web.login.LoginUtil;
import wg.user.mobileimsdk.server.model.fs.ManageUserInfo;
import wg.user.mobileimsdk.server.service.LoginService;
import wg.user.mobileimsdk.server.util.SendPost;
public class LoginController extends Controller{
	public static String  appServerIpPort=PropKit.get("appServerIpPort");
	public static String  headAdress=PropKit.get("headAdress");
	@Clear
	public void index(){
		if (getSessionAttr(LoginUtil.SESSION_USER_TAG) != null) {
			ManageUserInfo user = (ManageUserInfo) getSession().getAttribute("login_user");
			setAttr("user", user);
			redirect("/");
		} else {
			render("/login.html");
		}
	}

	@Clear
	public void login() {
		String login_username = getPara("username");
		String login_password = getPara("password");
		if (login_password != null && login_password.trim()!="" && login_username != null && login_username.trim() != "") {
			Map<String, String> map=new HashMap<String, String>();
			map.put("name", login_username);
			map.put("password", login_password);
			JSONObject jo=LoginService.ser.Loginvalidate(login_username, login_password, appServerIpPort);
			String code=jo.getInteger("code").toString();
			
			if(code.equals("0")){
				JSONObject jouser=(JSONObject) jo.get("result");
				ManageUserInfo user =new ManageUserInfo();
				user.setToken(jouser.getString("token"));
				user.setNickName(jouser.getString("nickName"));
				user.setTel(jouser.getString("tel"));
				user.setUserId(jouser.getInteger("userId"));
				String icon=headAdress+jouser.getString("userIcon");
				user.setIcon(icon);
				user.setUserName(jouser.getString("userName"));
				user.setLongitude(jouser.getDouble("longitude"));
				user.setDepId(jouser.getInteger("depId"));
				setSessionAttr(LoginUtil.SESSION_USER_TAG, user);
				setSessionAttr(LoginUtil.SESSION_LOGOUT_URL_TAG, "login/logout");
				setSessionAttr("username",user.getNickName());		
				/* 登录日志 */
				try{
					
				}catch(Exception e){
					e.printStackTrace();
				}
				renderText(LoginUtil.LOGIN_OK);
			}else{
				if(code.equals("1026")){
					renderText(LoginUtil.LOGIN_ERROR);	//无此用户
				}else if(code.equals("1027")){
					renderText(LoginUtil.LOGIN_ERROR);	//密码错误
				}
			}
			
		}else{
			renderText(LoginUtil.LOGIN_ERROR);	//用户名密码为空
		}
	}
	
	public void logout(){
		removeSessionAttr(LoginUtil.SESSION_USER_TAG);
		redirect("/");
	}
	
}
