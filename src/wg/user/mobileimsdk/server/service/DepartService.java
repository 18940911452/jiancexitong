package wg.user.mobileimsdk.server.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;

import wg.user.mobileimsdk.server.model.fs.ManageDepart;
import wg.user.mobileimsdk.server.model.fs.ManageUserInfo;


public class DepartService {

	public static DepartService ser =new DepartService();
	

	Logger logger = LoggerFactory.getLogger(getClass());

	
   /**
    * 查询所有的部门
    * @param pageNo
    * @param pageSize
 * @param name 
    * @return
    */
	public Object[] departAll(Integer pageNo, Integer pageSize, String name) {
		// TODO Auto-generated method stub
		Object[] res = new Object[2];
		String sql = "from manage_depart where status = 0 ";
		if(!StringUtils.isBlank(name)) {
			sql += "and dep_name like '%"+name+"%'";
		}
		Page<ManageDepart> paginate = ManageDepart.dao.paginate(pageNo, pageSize, "select  * ",sql );
		Long total = 0l;
		if(paginate!=null){
			List<ManageDepart> list = paginate.getList();
			Integer totalRow = paginate.getTotalRow();
			total = Long.parseLong(totalRow.toString());
			res[0]=list;
			res[1]=total;
		}
		return res;
	}
	/**
	 * 回显部门信息
	 * @param id
	 * @return
	 */
    public JSONObject echoDepart(String id) {
	// TODO Auto-generated method stub
    	ManageDepart departInfo = ManageDepart.dao.findById(id);
		JSONObject jsonObj = new JSONObject();
		if(departInfo != null){
			jsonObj.put("depName", departInfo.getDepName());
			jsonObj.put("id", departInfo.getDepId());
			jsonObj.put("status", departInfo.getStatus());
		}
		return jsonObj;
   }
  
    /**
	 * 添加部门
	 * @param param
	 * @param user
	 * @return
	 */
	public String addDepart(String param){
		boolean fianlStatus = true;
		final JSONObject json = JSONObject.parseObject(param);
		try {
			final String depName = json.getString("depname");
			// 排重（对有效用户）
			String sql = "select * from manage_depart where status = 0 and dep_name = '"+depName+"' ";
			System.out.println(sql);
			List<ManageDepart> list = ManageDepart.dao.find(sql);
			
			if(list != null && list.size()>0){
				return "exist";// 该用户已存在--用户名判断
			}else{
				fianlStatus = Db.tx(new IAtom() {
					@Override
					public boolean run() throws SQLException {
						boolean result = true;
						// 存入部门表信息
						ManageDepart departInfo = new ManageDepart();
						departInfo.setDepName(depName);
						// 保存添加部门
						result = departInfo.save();
						return result;
					}
				});
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
		if(fianlStatus == true){// 结果返回
			return "success";
		}else{
			return "fail";
		}
	}
   
	
	/**
	 * 删除部门
	 * @param id
	 * @param user
	 * @return
	 */
	public String deleteDepart(String id){
		if(id!=null && !"".equals(id)){
			final String uid = id;
			boolean result = true;
			ManageDepart depart = ManageDepart.dao.findById(uid);
			depart.setStatus(-1);
					result = depart.update();
			if(result == true){
				return "ok";
			}else{
				return "error";
			}
		}else{
			return "error";
		}
		
	}
	public String editDepart(String param) {
		// TODO Auto-generated method stub
		final JSONObject json = JSONObject.parseObject(param);
		 String depName = json.getString("depart");
		 Integer id = json.getInteger("id");
		String sql = "select * from manage_depart where status = 0 and dep_name = '"+depName+"' and dep_id not in ("+id+")";
		System.out.println(sql);
		List<ManageDepart> list = ManageDepart.dao.find(sql);
		
		if(list != null && list.size()>0){
			return "exist";// 该用户已存在--用户名判断
		}else{
					boolean result = true;
					ManageDepart departInfo = new ManageDepart();
					departInfo.setDepId(id);
					departInfo.setDepName(depName);
					result = departInfo.update();
					if(result == true){
						return "ok";
					}else{
						return "error";
					}
				}
	}
	public JSONArray loadDepart() {
		// TODO Auto-generated method stub
		JSONArray JSON = new JSONArray();
		String sql = "select * from manage_depart where status = 0 ";
		List<ManageDepart> list = ManageDepart.dao.find(sql);
		JSONObject obj = null;
		for (ManageDepart manageDepart : list) {
			 obj = new JSONObject();
			 obj.put("id", manageDepart.getDepId());
			 obj.put("depname", manageDepart.getDepName());
			 JSON.add(obj);
		}
		return JSON;
	}
	
}
