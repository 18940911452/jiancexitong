package wg.user.mobileimsdk.server.controller;

import java.util.List;

import com.jfinal.core.Controller;

import wg.user.mobileimsdk.server.model.fs.ManageUserInfo;
import wg.user.mobileimsdk.server.model.fs.News;
import wg.user.mobileimsdk.server.model.fs.Userinfo;
import wg.user.mobileimsdk.server.model.vo.ManageUserVo;
import wg.user.mobileimsdk.server.model.vo.MonitorConfigSearchVo;
import wg.user.mobileimsdk.server.model.vo.SearchVo;
import wg.user.mobileimsdk.server.service.NewsService;
import wg.user.mobileimsdk.server.service.UserInfoService;
import wg.user.mobileimsdk.server.service.UserService;
/**
 * 
 * 类名称：NewsController 
 * 类描述：文本类别 
 * 创建时间：2018年8月3日 上午11:51:32 
 * @author gongxiang.pang
 * @version V1.0
 */
public class UserInfoController extends Controller{

	public void index(){
		render("page/userinfo/main.html");
	} 
	
	/**
	 * 
	 * 名称：getNewsListInfo 
	 * 描述：获取人员列表 
	 * 日期：2018年8月3日-上午11:53:14
	 * @author gongxiang.pang
	 */
	public void getListInfo(){
		
		Integer pageNo = getParaToInt("pageNo",1);
		Integer pageSize = getParaToInt("pageSize",5);
		String name = getPara("name");
		String path = getPara("path");
		SearchVo search = new SearchVo();
		search.setName(name);
		search.setPageNo(pageNo);
		search.setPageSize(pageSize);
		Object[] res = UserInfoService.ser.getInfo( search);
		if(null != res[0]){
			List<Userinfo> list = (List<Userinfo>) res[0];
			setAttr("list",list);
		}
		if(null != res[1]){
			Integer total = (Integer)res[1];
			setAttr("total",total);
		}
		
		render(path);
		
	}
	
	/**
	 * 
	 * 名称：getInfoByid 
	 * 描述：根据id获取信息
	 * 日期：2018年8月14日-上午3:23:57
	 * @author gongxiang.pang
	 */
	public void getInfoByid(){
		Integer id=getParaToInt("id");
		//String path=getPara("path");
		 Userinfo news = UserInfoService.ser.getInfoById(id);
		//setAttr("news", news);
		//render("/pages/hbjs/detail.html");
		renderJson(news);
	}
	
	/**
	 * 删除用户
	 */
	public void deleteNews(){
		String id = getPara("id");
		ManageUserInfo user = (ManageUserInfo) getSession().getAttribute("login_user");
		String result = null;
		result = UserInfoService.ser.deleteNews(id, user);
		if(null == result){
			result = "error";
		}
		renderText(result);
	}
	
	/**
	 * 编辑用户信息
	 */
	public void editNews(){
		String param = getPara("param");
		String result = null;
		result = NewsService.ser.editUser(param);
		if(null == result){
			result = "error";
		}
		renderText(result);
	}
	
}
