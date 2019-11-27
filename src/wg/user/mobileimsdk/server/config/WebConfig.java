package wg.user.mobileimsdk.server.config;


import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.Const;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.UrlSkipHandler;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;

import wg.user.mobileimsdk.server.controller.IndexController;
import wg.user.mobileimsdk.server.controller.LoginController;
import wg.user.mobileimsdk.server.controller.NewsController;
import wg.user.mobileimsdk.server.controller.UploadFileController;
import wg.user.mobileimsdk.server.controller.UserController;
import wg.user.mobileimsdk.server.controller.UserInfoController;
import wg.user.mobileimsdk.server.interceptor.GlobalActionInterceptor;
import wg.user.mobileimsdk.server.interceptor.ServicesContext;
import wg.user.mobileimsdk.server.model.fs.News;


/**
 * API引导式配置
 */
public class WebConfig extends JFinalConfig {

	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		PropKit.use("web_config.txt"); 
		me.setDevMode(PropKit.getBoolean("devMode", false));
		me.setEncoding("UTF-8");
		
		//默认10M,此处设置为最大500M  
        me.setMaxPostSize(50*Const.DEFAULT_MAX_POST_SIZE);
        
        me.setViewType(ViewType.FREE_MARKER);//理论上jfinal返回的是freemarker页面，但是3.0版本貌似不是，需要手动设置
        //InitDb.init();
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		
		me.add("/", IndexController.class,"/");
		me.add("/login", LoginController.class,"/");
		me.add("/user", UserController.class,"/");
		me.add("/userinfo", UserInfoController.class,"/");
		
		
		me.add("/news", NewsController.class,"/");
		me.add("/upload", UploadFileController.class,"/");
		
		
		
		
	}

	/**
	 * 初始化数据库
	 * @return
	 */
	public static C3p0Plugin createC3p0Plugin_wjb_data() {
		return new C3p0Plugin(PropKit.get("jdbcUrl_fs_data"),PropKit.get("user_fs_data"), PropKit.get("password_fs_data").trim());
	}
	
	
	/**
	 * 配置插件-
	 */
	public void configPlugin(Plugins me) {
		//** 公共数据库 **//*
		C3p0Plugin c3_fsdata = createC3p0Plugin_wjb_data();
		me.add(c3_fsdata);
		ActiveRecordPlugin arp_fsdata = new ActiveRecordPlugin("wg_fs_common",c3_fsdata);
		me.add(arp_fsdata);
		wg.user.mobileimsdk.server.model.fs._MappingKit.mapping(arp_fsdata);
		
	}
	

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		me.addGlobalActionInterceptor(new GlobalActionInterceptor());
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		
	}
	@Override
	public void afterJFinalStart() {
		//new SpringLoader();
		
	}
	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目 运行此 main
	 * 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此	
	 */
	public static void main(String[] args) {
		JFinal.start("WebRoot", 8081, "/");
	}

	@Override
	public void configEngine(Engine arg0) {
		// TODO Auto-generated method stub
		
	}
}
