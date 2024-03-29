package wg.user.mobileimsdk.server.model.fs.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseManageUserInfo<M extends BaseManageUserInfo<M>> extends Model<M> implements IBean {

	public void setUserId(java.lang.Integer userId) {
		set("user_id", userId);
	}

	public java.lang.Integer getUserId() {
		return get("user_id");
	}

	public void setUserName(java.lang.String userName) {
		set("user_name", userName);
	}

	public java.lang.String getUserName() {
		return get("user_name");
	}

	public void setPassword(java.lang.String password) {
		set("password", password);
	}

	public java.lang.String getPassword() {
		return get("password");
	}

	public void setNickName(java.lang.String nickName) {
		set("nick_name", nickName);
	}

	public java.lang.String getNickName() {
		return get("nick_name");
	}

	public void setIcon(java.lang.String icon) {
		set("icon", icon);
	}

	public java.lang.String getIcon() {
		return get("icon");
	}

	public void setDepId(java.lang.Integer depId) {
		set("dep_id", depId);
	}

	public java.lang.Integer getDepId() {
		return get("dep_id");
	}

	public void setSex(java.lang.Integer sex) {
		set("sex", sex);
	}

	public java.lang.Integer getSex() {
		return get("sex");
	}

	public void setEmail(java.lang.String email) {
		set("email", email);
	}

	public java.lang.String getEmail() {
		return get("email");
	}

	public void setNation(java.lang.String nation) {
		set("nation", nation);
	}

	public java.lang.String getNation() {
		return get("nation");
	}

	public void setBirthdate(java.lang.String birthdate) {
		set("birthdate", birthdate);
	}

	public java.lang.String getBirthdate() {
		return get("birthdate");
	}

	public void setTel(java.lang.String tel) {
		set("tel", tel);
	}

	public java.lang.String getTel() {
		return get("tel");
	}

	public void setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
	}

	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	public void setStatus(java.lang.Integer status) {
		set("status", status);
	}

	public java.lang.Integer getStatus() {
		return get("status");
	}

	public void setToken(java.lang.String token) {
		set("token", token);
	}

	public java.lang.String getToken() {
		return get("token");
	}

	public void setLongitude(java.lang.Double longitude) {
		set("longitude", longitude);
	}

	public java.lang.Double getLongitude() {
		return get("longitude");
	}

	public void setLatitude(java.lang.Double latitude) {
		set("latitude", latitude);
	}

	public java.lang.Double getLatitude() {
		return get("latitude");
	}

	public void setPushToken(java.lang.String pushToken) {
		set("pushToken", pushToken);
	}

	public java.lang.String getPushToken() {
		return get("pushToken");
	}

	public void setRole(java.lang.Integer role) {
		set("role", role);
	}

	public java.lang.Integer getRole() {
		return get("role");
	}

	public void setUpdateTime(java.util.Date updateTime) {
		set("update_time", updateTime);
	}

	public java.util.Date getUpdateTime() {
		return get("update_time");
	}

}
