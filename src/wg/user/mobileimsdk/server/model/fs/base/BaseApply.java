package wg.user.mobileimsdk.server.model.fs.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseApply<M extends BaseApply<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public java.lang.String getName() {
		return get("name");
	}

	public void setPhone(java.lang.String phone) {
		set("phone", phone);
	}

	public java.lang.String getPhone() {
		return get("phone");
	}

	public void setAge(java.lang.Integer age) {
		set("age", age);
	}

	public java.lang.Integer getAge() {
		return get("age");
	}

	public void setTime(java.util.Date time) {
		set("time", time);
	}

	public java.util.Date getTime() {
		return get("time");
	}

	public void setEducation(java.lang.String education) {
		set("education", education);
	}

	public java.lang.String getEducation() {
		return get("education");
	}

	public void setLianxi(java.lang.Integer lianxi) {
		set("lianxi", lianxi);
	}

	public java.lang.Integer getLianxi() {
		return get("lianxi");
	}

	public void setBeizhu(java.lang.String beizhu) {
		set("beizhu", beizhu);
	}

	public java.lang.String getBeizhu() {
		return get("beizhu");
	}

	public void setStatus(java.lang.Integer status) {
		set("status", status);
	}

	public java.lang.Integer getStatus() {
		return get("status");
	}

}
