package wg.user.mobileimsdk.server.model.vo;

import java.io.Serializable;

public class ManageGroupVo implements Serializable{
	private static final long serialVersionUID = 2952290788072746988L;
	private Integer usertotal;
	private Integer infototal;
	private String eTime;
	private String sTime;
	private String groupId;
	private String groupName;
	private String icon;
	private String createTime;
	private Integer status;
	private Integer groupType;
	private String breaker;
	public Integer getUsertotal() {
		return usertotal;
	}
	public void setUsertotal(Integer usertotal) {
		this.usertotal = usertotal;
	}

	public Integer getInfototal() {
		return infototal;
	}
	public void setInfototal(Integer infototal) {
		this.infototal = infototal;
	}
	public String geteTime() {
		return eTime;
	}
	public void seteTime(String eTime) {
		this.eTime = eTime;
	}
	public String getsTime() {
		return sTime;
	}
	public void setsTime(String sTime) {
		this.sTime = sTime;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getGroupType() {
		return groupType;
	}
	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}
	public String getBreaker() {
		return breaker;
	}
	public void setBreaker(String breaker) {
		this.breaker = breaker;
	}
	
	
	
}
