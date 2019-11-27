package wg.user.mobileimsdk.server.controller;

import com.jfinal.core.Controller;

import wg.user.mobileimsdk.server.model.fs.ManageUserInfo;

public class IndexController extends Controller{
	
	public void index(){
		ManageUserInfo user = (ManageUserInfo) getSession().getAttribute("login_user");
		setAttr("user", user);
		render("_layout.html");
	}
	public void test(){
		System.out.println("##########");
	}
}
