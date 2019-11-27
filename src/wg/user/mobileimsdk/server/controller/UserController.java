package wg.user.mobileimsdk.server.controller;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;

import wg.user.mobileimsdk.server.model.fs.ManageUserInfo;
import wg.user.mobileimsdk.server.model.vo.ManageUserVo;
import wg.user.mobileimsdk.server.model.vo.MonitorConfigSearchVo;
import wg.user.mobileimsdk.server.service.UserService;


public class UserController extends Controller {
	Logger logger = LoggerFactory.getLogger(getClass());
	public static String  headAdress=PropKit.get("headAdress");
	public static String  localHeadAdress=PropKit.get("localHeadAdress");
	
	public void index() {
		ManageUserInfo user = (ManageUserInfo) getSession().getAttribute("login_user");
		if(user.getDepId()!=1) {
			render("permission.html");
		}else {
			render("page/user/main.html");
		}
	}

	public void renPath(String path) {
		render(path);
	}
	
	public void loadHtml() {
		String path=getPara("path");
		render(path);
	}
	
	/**
	 * 编辑用户信息
	 */
	public void editUser(){
		String param = getPara("param");
		String result = null;
		result = UserService.ser.editUser(param);
		if(null == result){
			result = "error";
		}
		
		renderText(result);
	}
	
	/**
	 * 回显用户
	 */
	public void echoUser(){
		String id = getPara("id");
		JSONObject obj = null;
		obj = UserService.ser.echoUser(id);
		renderJson(obj); 
	}
	
	/**
	 * 删除用户
	 */
	public void deleteUser(){
		String id = getPara("id");
		ManageUserInfo user = (ManageUserInfo) getSession().getAttribute("login_user");
		String result = null;
		result = UserService.ser.deleteUser(id, user);
		if(null == result){
			result = "error";
		}
		renderText(result);
	}
	
	/**
	 * 添加用户信息
	 */
	public void addUser(){
		String param = getPara("param");
		// 判断用户
		ManageUserInfo user = (ManageUserInfo) getSession().getAttribute("login_user");
		String result = null;
		//String headPath=getRequest().getSession().getServletContext().getRealPath("/")+File.separator+"images"+File.separator+"headicon";//上传的路径
		String   headPath=localHeadAdress+"headicon";
		String   basePath = "headicon/";
		result = UserService.ser.addUser(param, user,headPath,basePath);
		if(null == result){
			result = "error";
		}
		
		renderText(result);
	}

	/**
	 * 显示用户信息
	 */
	public void loadUser(){
		
		Integer pageNo = getParaToInt("pageNo");
		Integer pageSize = getParaToInt("pageSize");
		String name = getPara("username");
		String path = getPara("path");
		
		MonitorConfigSearchVo search = new MonitorConfigSearchVo();
		search.setPageNo(pageNo);
		search.setPageSize(pageSize);
		
		ManageUserInfo user = null;
		Object[] res = UserService.ser.loadUser1(user, search, name);
		if(null != res[0]){
			List<ManageUserVo> list = (List<ManageUserVo>) res[0];
			setAttr("list",list);
		}
		if(null != res[1]){
			Integer total = (Integer)res[1];
			setAttr("total",total);
		}
		
		render(path);
		
	}
	 /**
		 * 修改密码
		 * 名称：updatePassword
		 * @author gongxiang.pang
		 * @parameter
		 * @return
		 */
		public void updatePassword(){
			String uid=getPara("id");
			JSONObject jo=new JSONObject();
			String data;
			if(uid!=null ){
				 jo =  UserService.ser.updatePassword( uid);
				 data = "true";
			}else{
				 data = "false";
			}
			renderText(data);
		}

}
