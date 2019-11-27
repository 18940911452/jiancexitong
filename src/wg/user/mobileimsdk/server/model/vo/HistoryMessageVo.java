package wg.user.mobileimsdk.server.model.vo;

import java.io.Serializable;

public class HistoryMessageVo implements Serializable{

	private static final long serialVersionUID = 238170948478744976L;
	private String id;//内容位置
	private String fingerPrint;//指纹码，保证消息唯一避免重复
	private String fromUserId;//来源用户id
	private String toUserId;//接收者id
	private String content;//
	private Integer ctype;//聊天类型：1单聊 ， 2群聊
	private Integer utype;//聊天内容类型：单聊：1文本  2.图片 3.语音 4.视频 5 文件
	private Integer sendStatus;//1离线消息，0历史消息
	private String sTime;//
	private String eTime;//
	private String fromUserName;//发布消息人名字
	private String pubtime;//
	private Boolean isMarkerRed=true;  //true 标红  false  不标红
	private String keywords;
	private String userImage;
	private String relCount;
	private String groupUserId;//用户#群组
	
	public String getGroupUserId() {
		return groupUserId;
	}
	public void setGroupUserId(String groupUserId) {
		this.groupUserId = groupUserId;
	}
	public String getRelCount() {
		return relCount;
	}
	public void setRelCount(String relCount) {
		this.relCount = relCount;
	}
	public String getUserImage() {
		return userImage;
	}
	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public Boolean getIsMarkerRed() {
		return isMarkerRed;
	}
	public void setIsMarkerRed(Boolean isMarkerRed) {
		this.isMarkerRed = isMarkerRed;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFingerPrint() {
		return fingerPrint;
	}
	public void setFingerPrint(String fingerPrint) {
		this.fingerPrint = fingerPrint;
	}
	public String getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getToUserId() {
		return toUserId;
	}
	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getCtype() {
		return ctype;
	}
	public void setCtype(Integer ctype) {
		this.ctype = ctype;
	}
	public Integer getUtype() {
		return utype;
	}
	public void setUtype(Integer utype) {
		this.utype = utype;
	}
	public Integer getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}
	public String getsTime() {
		return sTime;
	}
	public void setsTime(String sTime) {
		this.sTime = sTime;
	}
	public String geteTime() {
		return eTime;
	}
	public void seteTime(String eTime) {
		this.eTime = eTime;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getPubtime() {
		return pubtime;
	}
	public void setPubtime(String pubtime) {
		this.pubtime = pubtime;
	}
	
}
