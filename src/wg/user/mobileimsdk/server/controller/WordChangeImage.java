package wg.user.mobileimsdk.server.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;

import com.aspose.words.Document;
import com.aspose.words.ImageSaveOptions;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

public class WordChangeImage {
	
	
	/**
	 * licence 验证
	 * @return
	 * @throws Exception
	 */
	public static boolean getLicense() throws Exception {
	    boolean result = false;
	    try {
	        InputStream is = com.aspose.words.Document.class.getResourceAsStream("/com.aspose.words.lic_2999.xml");
	        License aposeLic = new License();
	        aposeLic.setLicense(is);
	        result = true;
	        is.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    }
	    return result;
	}
	/**
	 *  文档转图片
	 * [url=home.php?mod=space&uid=952169]@Param[/url] inPath 传入文档地址
	 * @param outDir 输出的图片文件夹地址
	 */
	public static String  doc2Img(String inPath, String outDir){
		String imgpathStr="";
	    try {
	        /*if (!getLicense()) {
	            throw new Exception("com.aspose.words lic ERROR!");
	        }*/
	        System.out.println(inPath + " -> " + outDir);
	        long old = System.currentTimeMillis();
	        // word文档
	        Document doc = new Document(inPath);
	        // 支持RTF HTML,OpenDocument, PDF,EPUB, XPS转换
	        ImageSaveOptions options = new ImageSaveOptions(SaveFormat.PNG);
	        int pageCount = doc.getPageCount();
	        for (int i = 0; i < pageCount; i++) {
	        	String imgpath=UUID.randomUUID().toString()+".png";
	        	imgpathStr=imgpathStr+imgpath+";";
	            File file = new File(outDir+"/"+imgpath);
	            System.out.println(outDir+"/"+i+".png");
	            FileOutputStream os = new FileOutputStream(file);
	            options.setPageIndex(i);
	            doc.save(os, options);
	            os.close();
	            
	        }
	        long now = System.currentTimeMillis();
	        System.out.println("convert OK! " + ((now - old) / 1000.0) + "秒");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    imgpathStr = imgpathStr.substring(0,imgpathStr.length() - 1);
	    return imgpathStr;
	}

	
	public  static void main(String ars[]){
		String inpath= "I:\\test/2.doc";
		String outDir="I:\\test";
		doc2Img(inpath,outDir);
	}
}
