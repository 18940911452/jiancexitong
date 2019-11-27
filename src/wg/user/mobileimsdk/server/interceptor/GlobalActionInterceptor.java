
package wg.user.mobileimsdk.server.interceptor;



import wg.user.mobileimsdk.server.model.fs.ManageUserInfo;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;




import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

import ibasic.web.login.LoginUtil;


public class GlobalActionInterceptor extends Controller implements Interceptor  {
	private static final Logger log = Logger.getLogger(GlobalActionInterceptor.class);

	
	@Override
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		try {
			HttpServletRequest   request=(inv.getController()).getRequest();
			String actionurl = request.getQueryString();
			if (controller.getSessionAttr(LoginUtil.SESSION_USER_TAG) != null ) {
				ManageUserInfo user = controller.getSessionAttr(LoginUtil.SESSION_USER_TAG);
				controller.setAttr(LoginUtil.SESSION_USER_TAG, user);
				inv.invoke();
			} else {
				controller.setAttr(LoginUtil.SESSION_USER_TAG, null);
				controller.redirect("/login");
			}
		} catch (Exception e) {
			Writer result = new StringWriter();
			PrintWriter printWriter = new PrintWriter(result);
			e.printStackTrace(printWriter);
			log.error(result.toString());
			controller.setAttr("exception", e.getMessage());
			controller.render("/error.html");
		}
	}
	
	
	
}
