package wg.user.mobileimsdk.server.model.fs.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseNews<M extends BaseNews<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setType(java.lang.Integer type) {
		set("type", type);
	}

	public java.lang.Integer getType() {
		return get("type");
	}

	public void setTitle(java.lang.String title) {
		set("title", title);
	}

	public java.lang.String getTitle() {
		return get("title");
	}

	public void setContent(byte[] content) {
		set("content", content);
	}

	public byte[] getContent() {
		return get("content");
	}

	public void setPreimage(java.lang.String preimage) {
		set("preimage", preimage);
	}

	public java.lang.String getPreimage() {
		return get("preimage");
	}

	public void setAuthor(java.lang.String author) {
		set("author", author);
	}

	public java.lang.String getAuthor() {
		return get("author");
	}

	public void setTime(java.util.Date time) {
		set("time", time);
	}

	public java.util.Date getTime() {
		return get("time");
	}

	public void setSource(java.lang.String source) {
		set("source", source);
	}

	public java.lang.String getSource() {
		return get("source");
	}

	public void setStatus(java.lang.Integer status) {
		set("status", status);
	}

	public java.lang.Integer getStatus() {
		return get("status");
	}

	public void setAddfield(java.lang.String addfield) {
		set("addfield", addfield);
	}

	public java.lang.String getAddfield() {
		return get("addfield");
	}

	public void setNum(java.lang.Integer num) {
		set("num", num);
	}

	public java.lang.Integer getNum() {
		return get("num");
	}

}
