package wg.user.mobileimsdk.server.controller;

import static java.lang.System.out;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.upload.UploadFile;

import wg.user.mobileimsdk.server.service.ExportDocService;
import wg.user.mobileimsdk.server.service.UploadFileService;
/**
 * 

 * @author DELL
 *
 */
public class UploadFileController extends Controller{
	public static final int BUFSIZE = 1024 * 1;
	public void index(){
		render("/loadtest.html");
	}
	
	/**
	 * 
	 * @method:uploadImg 
	 * @describe:上传文件，图片，文档，语音 带有后缀的
	 * @author:  gongxiangPang 
	 * @param type    1图片  2语音   3文件
	 * @param :TODO
	 */
	
	public void uploadFile(){
		String result="";
		UploadFile uploadFile=getFile();//这句已经完成上传，一下是从上传的文件进行重新上传到
        String fileName=uploadFile.getOriginalFileName();
        File file=uploadFile.getFile();
        String filename=UploadFileService.ser.newName(fileName);
        
        String dir=getRequest().getSession().getServletContext().getRealPath("/")+File.separator+"upload"+File.separator+"word"+File.separator+filename;//上传的路径
        String bpath = getRequest().getScheme()+"://"+getRequest().getServerName()+":"+getRequest().getServerPort()+getRequest().getContextPath()+"/"+"upload"+File.separator+"word"+File.separator+filename;
       //word转图片的路径
        String imagespath=getRequest().getSession().getServletContext().getRealPath("/")+File.separator+"upload"+File.separator+"word"+File.separator+"images";//
        // String bpath="upload/images/"+filename;;
        File to=new File(dir);
        try {
            to.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        boolean re = UploadFileService.ser.fileChannelCopy(file, to);
        //上次成功后，开始解析文件
        if(re){
        	
        }
        //word转化成图片
        if(re){
        	WordChangeImage  wi=new WordChangeImage();
        	String imagepath=wi.doc2Img(dir,imagespath);
        	ExportDocService doc=new ExportDocService();
        	doc.alysisWord(dir,imagepath);
        }
        file.delete();
        if(re){
        	result=bpath;
        }else{
        	result="false";
        }
        JSONObject jo=new JSONObject();
        jo.put("result", result);	
        renderJson(jo);
    }
	
	
	public void uploadFile_jsonp(){
		HttpServletResponse response = getResponse();
		String path = PropKit.get("localHeadAdress");
		response.setHeader("Access-Control-Allow-Origin", "*");//允许跨域访问的域，可以是通配符”*”；
		response.setHeader("Access-Control-Allow-Methods", "POST, GET");
		response.setHeader("Access-Control-Max-Age", "1800");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		String result="";
		UploadFile uploadFile=getFile();//这句已经完成上传，一下是从上传的文件进行重新上传到
        String fileName=uploadFile.getOriginalFileName();
        File file=uploadFile.getFile();
        long length = uploadFile.getFile().length();//
        String sizeStr=getPrintSize(length);
        String type=getPara("typeu","");
        String typepath="";//图片路径
        if(type.equals("12")||type=="12"||type.equals("22")||type.equals("22")){
        	typepath="image";//图片路径
        }else if(type.equals("13")||type=="13"||type.equals("23")||type.equals("23")){
        	typepath="voice";//语音存储
        }else if(type.equals("14")||type=="14"||type.equals("24")||type.equals("24")){
        	typepath="video";//语音存储
        }else if(type.equals("25")||type=="25"||type.equals("15")||type.equals("15")){
        	typepath="file";//语音存储
        }else{
        	typepath="file";//文件存储
        }
       
        String filename=UploadFileService.ser.newName(fileName);
        
       String dir=path+File.separator+"chatsource"+"/"+typepath+"/"+filename;//上传的路径
        //String basePath = getRequest().getScheme()+"://"+getRequest().getServerName()+":"+getRequest().getServerPort()+getRequest().getContextPath()+"/"+"chatsource"+File.separator+typepath+File.separator+filename;;
        File to=new File(dir);
        try {
            to.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        boolean re = UploadFileService.ser.fileChannelCopy(file, to);
        file.delete();
        if(re){
        	result="chatsource"+File.separator+typepath+File.separator+filename;
        }else{
        	result="false";
        }
        JSONObject jo=new JSONObject();
        jo.put("path", "chatsource"+"/"+typepath+"/"+filename);	
        jo.put("filename", fileName);	
        jo.put("size", sizeStr);
        jo.put("typeu", type);
       // renderJson(jo);
        renderJson(jo.toJSONString());
    }
	
	public  String getPrintSize_detail(long size) {  
	    //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义  
	    if (size < 1024) {  
	        return String.valueOf(size) + "B"; 
	    } else {  
	        size = size / 1024;  
	    }  
	    //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位  
	    //因为还没有到达要使用另一个单位的时候  
	    //接下去以此类推  
	    if (size < 1024) {  
	        return String.valueOf(size) + "KB";  
	    } else {  
	        size = size / 1024;  
	    }  
	    if (size < 1024) {  
	        //因为如果以MB为单位的话，要保留最后1位小数，  
	        //因此，把此数乘以100之后再取余  
	        size = size * 100;  
	        return String.valueOf((size / 100)) + "."+ String.valueOf((size % 100)) + "MB";  
	    } else {  
	        //否则如果要以GB为单位的，先除于1024再作同样的处理  
	        size = size * 100 / 1024;  
	        return String.valueOf((size / 100)) + "."  
	                + String.valueOf((size % 100)) + "GB";  
	    }  
	}  
	public  String getPrintSize(long size) {  
	    //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义  
	    if (size < 1024) {  
	        return  "1KB"; 
	    } else { 
	        size = size / 1024;  
	    }  
	    //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位  
	    //因为还没有到达要使用另一个单位的时候  
	    //接下去以此类推  
	    if (size < 1024) {  
	    	if(size % 1024>0){
	    		 return String.valueOf(size+1) + "KB";  
	    	}else{
	    		 return String.valueOf(size) + "KB";  
	    	}
	       
	    } else {  
	        size = size / 1024;  
	    }  
	    if (size < 1024) {  
	        //因为如果以MB为单位的话，要保留最后1位小数，  
	        //因此，把此数乘以100之后再取余  
	        size = size * 100;  
	        return String.valueOf((size / 100)) + "."+ String.valueOf((size % 100)) + "MB";  
	    } else {  
	        //否则如果要以GB为单位的，先除于1024再作同样的处理  
	        size = size * 100 / 1024;  
	        return String.valueOf((size / 100)) + "."  
	                + String.valueOf((size % 100)) + "GB";  
	    }  
	}  
	
}
