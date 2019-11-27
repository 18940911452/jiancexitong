package wg.user.mobileimsdk.server.controller;

import java.util.List;

import com.jfinal.core.Controller;

import wg.user.mobileimsdk.server.model.fs.ManageUserInfo;
import wg.user.mobileimsdk.server.model.fs.News;
import wg.user.mobileimsdk.server.model.vo.ManageUserVo;
import wg.user.mobileimsdk.server.model.vo.MonitorConfigSearchVo;
import wg.user.mobileimsdk.server.model.vo.SearchVo;
import wg.user.mobileimsdk.server.service.NewsService;
import wg.user.mobileimsdk.server.service.UserService;
/**
 * 
 * 类名称：NewsController 
 * 类描述：文本类别 
 * 创建时间：2018年8月3日 上午11:51:32 
 * @author gongxiang.pang
 * @version V1.0
 */
public class NewsController extends Controller{

	public void index(){
		render("page/news/main.html");
	} 
	
	/**
	 * 
	 * 名称：getNewsListInfo 
	 * 描述：获取文章列表 
	 * 日期：2018年8月3日-上午11:53:14
	 * @author gongxiang.pang
	 */
	public void getNewsListInfo(){
		
		Integer pageNo = getParaToInt("pageNo");
		Integer pageSize = getParaToInt("pageSize");
		String name = getPara("name");
		String path = getPara("path");
		Integer type=getParaToInt("type");
		SearchVo search = new SearchVo();
		search.setName(name);
		search.setPageNo(pageNo);
		search.setPageSize(pageSize);
		search.setType(type);//1.新闻咨询    2常见问题   3学习教程   4求职技巧   5咨询信息
		ManageUserInfo user = null;
		Object[] res = NewsService.ser.getInfo( search);
		if(null != res[0]){
			List<News> list = (List<News>) res[0];
			setAttr("list",list);
		}
		if(null != res[1]){
			Integer total = (Integer)res[1];
			setAttr("total",total);
		}
		
		render(path);
		
	}
	
	public void addNews(){
		String param = getPara("param");
		// 判断用户
		ManageUserInfo user = (ManageUserInfo) getSession().getAttribute("login_user");
		String result = null;
		result = NewsService.ser.addNews(param, user);
		if(null == result){
			result = "error";
		}
		
		renderText(result);
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
		News news = NewsService.ser.getInfoById(id);
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
		result = NewsService.ser.deleteNews(id, user);
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
