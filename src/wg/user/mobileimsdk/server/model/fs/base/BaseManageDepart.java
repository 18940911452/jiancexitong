package wg.user.mobileimsdk.server.model.fs.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseManageDepart<M extends BaseManageDepart<M>> extends Model<M> implements IBean {

	public void setDepId(java.lang.Integer depId) {
		set("dep_id", depId);
	}

	public java.lang.Integer getDepId() {
		return get("dep_id");
	}

	public void setDepName(java.lang.String depName) {
		set("dep_name", depName);
	}

	public java.lang.String getDepName() {
		return get("dep_name");
	}

	public void setStatus(java.lang.Integer status) {
		set("status", status);
	}

	public java.lang.Integer getStatus() {
		return get("status");
	}

}
