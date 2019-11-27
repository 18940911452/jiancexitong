package wg.user.mobileimsdk.server.service;



import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;

import wg.user.mobileimsdk.server.model.fs.News;
import wg.user.mobileimsdk.server.model.fs.Userinfo;
 
/**
 * 
 * 读取word文档中表格数据，支持doc、docx
 * @author Fise19
 * 
 */
public class ExportDocService {
	//public static ExportDocService doc = new ExportDocService();
	
	
	public static void main(String[] args) {
		ExportDocService test = new ExportDocService();
		String filePath = "I:\\1.doc";
//		String filePath = "D:\\new\\测试.doc";
		test.alysisWord(filePath,"");
		 /*for (Map.Entry<String, String> entry : map.entrySet()) {
	            //System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
	     }*/
	}
	
	/**
	 * 读取文档中表格
	 * @param filePath
	 */
	public boolean alysisWord(String filePath,String imagepath){
	 	Map<String, String> map=new HashMap<String, String> ();
	 	boolean fianlStatus = true;
	 	 int rownum=0;
		try{
			FileInputStream in = new FileInputStream(filePath);//载入文档
			// 处理docx格式 即office2007以后版本
			if(filePath.toLowerCase().endsWith("docx")){
				//word 2007 图片不会被读取， 表格中的数据会被放在字符串的最后   
				XWPFDocument xwpf = new XWPFDocument(in);//得到word文档的信息
				Iterator<XWPFTable> it = xwpf.getTablesIterator();//得到word中的表格
				// 设置需要读取的表格  set是设置需要读取的第几个表格，total是文件中表格的总数
				int set = 2, total = 4;
				int num = set;
				// 过滤前面不需要的表格
				for (int i = 0; i < set-1; i++) {
					it.hasNext();
					it.next();
				}
				while(it.hasNext()){
					XWPFTable table = it.next();
					System.out.println("这是第" + num + "个表的数据");
					List<XWPFTableRow> rows = table.getRows(); 
					//读取每一行数据
					for (int i = 0; i < rows.size(); i++) {
						XWPFTableRow  row = rows.get(i);
						//读取每一列数据
						List<XWPFTableCell> cells = row.getTableCells(); 
						for (int j = 0; j < cells.size(); j++) {
							XWPFTableCell cell = cells.get(j);
							//输出当前的单元格的数据
							System.out.print(cell.getText() + "\t");
						}
						System.out.println();
					}
					// 过滤多余的表格
					while (num < total) {
						it.hasNext();
						//it.next();
						num += 1;
					}
				}
			}else{
				// 处理doc格式 即office2003版本
				POIFSFileSystem pfs = new POIFSFileSystem(in);   
				HWPFDocument hwpf = new HWPFDocument(pfs);   
				Range range = hwpf.getRange();//得到文档的读取范围
				TableIterator it = new TableIterator(range);
				// 迭代文档中的表格
				// 如果有多个表格只读取需要的一个 set是设置需要读取的第几个表格，total是文件中表格的总数
				int set = 1, total = 2;
				int num = set;
				/*for (int i = 0; i < set-1; i++) {
					it.hasNext();
					it.next();
				}*/
				while (it.hasNext()) {   
					Table tb = (Table) it.next();   
					System.out.println("@@@@@@@@@@@@@@@@@@@@@这是第" + num + "个表的数据@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+"/r");
					//迭代行，默认从0开始,可以依据需要设置i的值,改变起始行数，也可设置读取到那行，只需修改循环的判断条件即可
					
					for (int i = 0; i < tb.numRows(); i++) {
						System.out.println("-------------------------这是第"+i+"行--------------------------------------");
						TableRow tr = tb.getRow(i);   
						rownum++;
						//迭代列，默认从0开始
						for (int j = 0; j < tr.numCells(); j++) {   
							TableCell td = tr.getCell(j);//取得单元格
							//取得单元格的内容
								String s="";
							for(int k = 0; k < td.numParagraphs(); k++){   
								Paragraph para = td.getParagraph(k); 
								 s = s+para.text();
								//去除后 。面的特殊符号
								if(null != s && !"".equals(s)){
									s = s.substring(0, s.length()-1);
								}
							}
							
							String cell="_"+rownum+"_"+j;
							System.out.print("第"+rownum+"_"+j+"列|"+s + "\t");
							
							map.put(cell, s);
						}
						System.out.println();
					} 
					// 过滤多余的表格
					while (num < total) {
						it.hasNext();
						//it.next();
						num += 1;
					}
				}
			}
			
			
		//将解析好数据保存数据库
			
				fianlStatus = Db.tx(new IAtom() {
					@Override
					public boolean run() throws SQLException {
						boolean result = true;
						Userinfo  info=new Userinfo();
						info.setImagePath(imagepath);
						info.setName(map.get("_1_1"));
						info.setSex(map.get("_1_3"));
						info.setXianrenzhiwu(map.get("_7_1"));
						info.setBirthday(map.get("_1_5"));
						info.setMinzu(map.get("_2_1"));
						info.setJiguan(map.get("_2_3"));
						info.setChushengdi(map.get("_2_5"));
						info.setRudangshijian(map.get("_3_1"));
						info.setJiankangzhuangkang(map.get("_3_5"));
						info.setGongzuoshijian(map.get("_3_3"));
						info.setZhuanyejishuzhuwu(map.get("_4_1"));
						info.setYouhetechang(map.get("_4_3"));
						info.setQuanrizhijiaoyu(map.get("_5_2"));
						info.setQuanrizhibiyeyuanxiao(map.get("_5_4"));
						info.setZaizhijiaoyu(map.get("_6_2"));
						info.setZaizhibiyeyuanxiao(map.get("_6_4"));
						info.setXianrenzhiwu(map.get("_7_1"));
						info.setNirenzhiwu(map.get("_8_1"));
						info.setNimianzhiwu(map.get("_9_1"));
						info.setJianli(map.get("_10_1"));
						info.setChengjiangqingkuang(map.get("_11_1_"));
						info.setNiandukaohe(map.get("_12_1"));
						info.setRenmianliyou(map.get("_13_1"));
						
						result = info.save();
							if(result) {
								System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@保存成功@@@@@@@@@@@@@@@@@@@@!");
							}
						
						return result;
					}
				});
				
				
				
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return fianlStatus;
		
	}
	
	/**
			-------------------------这是第0行--------------------------------------
		第1_0列|姓 名	第1_1列|张爱国	第1_2列|性 别	第1_3列|男	第1_4列|出生年月(   岁)	第1_5列|19	第1_6列|	
		-------------------------这是第1行--------------------------------------
		第2_0列|民 族	第2_1列|汉	第2_2列|籍 贯	第2_3列|皖	第2_4列|出生地	第2_5列|安徽	第2_6列|	
		-------------------------这是第2行--------------------------------------
		第3_0列|入 党时 间	第3_1列|2001年九月	第3_2列|参加工作时间	第3_3列|3年	第3_4列|健康状况	第3_5列|良好	第3_6列|	
		-------------------------这是第3行--------------------------------------
		第4_0列|专业技术职务	第4_1列|宏观经济学专家	第4_2列|熟悉专业有何专长	第4_3列|腿特长	
		-------------------------这是第4行--------------------------------------
		第5_0列|学 历	第5_1列|全日制教  育	第5_2列|硕士	第5_3列|毕业院校系及专业	第5_4列|北京大学	
		-------------------------这是第5行--------------------------------------
		第6_0列|	第6_1列|在  职教  育	第6_2列|博士	第6_3列|毕业院校系及专业	第6_4列|金融学	
		-------------------------这是第6行--------------------------------------
		第7_0列|现任职务	第7_1列|科员	
		-------------------------这是第7行--------------------------------------
		第8_0列|拟任职务	第8_1列|科长	
		-------------------------这是第8行--------------------------------------
		第9_0列|拟免职务	第9_1列|科员	
		-------------------------这是第9行--------------------------------------
		第10_0列|简   历	第10_1列|1981.09——1986.07，清华大学土木与环境工程系环境工程专业学习，获理学学士学位；1986.07——1988.10， HYPERLINK "https://baike.baidu.com/item/%E6%B8%85%E5%8D%8E%E5%A4%A7%E5%AD%A6" \t "https://baike.baidu.com/item/%E9%99%88%E5%90%89%E5%AE%81/_blank" 清华大学环境工程系环境工程专业硕士研究生学习，获理学硕士学位；1988.10——1989.07，英国布鲁耐尔大学生物化学系攻读博士学位；1989.07——1992.11，英国帝国理工医学院土木系环境系统分析专业攻读博士学位，获理学博士学位；1992.11——1994.12，英国帝国理工医学院博士后；1994.12——1998.03，英国帝国理工医学院助理研究员；	
		@@@@@@@@@@@@@@@@@@@@@这是第2个表的数据@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@/r
		-------------------------这是第0行--------------------------------------
		第11_0列|奖 惩情况	第11_1列|无	
		-------------------------这是第1行--------------------------------------
		第12_0列|年  度考  核结  果	第12_1列|无	
		-------------------------这是第2行--------------------------------------
		第13_0列|任  免理  由	第13_1列|承担了多项国家重大专项、“973”攻关和省部级及国际合作项目，指导了20多名博士	
		-------------------------这是第3行--------------------------------------
		第14_0列|家  庭主  要成  员及  其重  要社  会关  系	第14_1列|称 谓	第14_2列|姓 名	第14_3列|出生年月	第14_4列|政治面貌	第14_5列|工作单位及职务	
		-------------------------这是第4行--------------------------------------
		第15_0列|	第15_1列|妻子	第15_2列|张美丽	第15_3列|1963.03.10	第15_4列|党员	第15_5列|医生	
		-------------------------这是第5行--------------------------------------
		第16_0列|	第16_1列|女儿	第16_2列|张建设	第16_3列|2000.03.10	第16_4列|团员	第16_5列|学生	
		-------------------------这是第6行--------------------------------------
		第17_0列|	第17_1列|	第17_2列|	第17_3列|	第17_4列|	第17_5列|	
		-------------------------这是第7行--------------------------------------
		第18_0列|	第18_1列|	第18_2列|	第18_3列|	第18_4列|	第18_5列|	
		-------------------------这是第8行--------------------------------------
		第19_0列|	第19_1列|	第19_2列|	第19_3列|	第19_4列|	第19_5列|	
		-------------------------这是第9行--------------------------------------
		第20_0列|	第20_1列|	第20_2列|	第20_3列|	第20_4列|	第20_5列|	
		-------------------------这是第10行--------------------------------------
		第21_0列|	第21_1列|	第21_2列|	第21_3列|	第21_4列|	第21_5列|	
		-------------------------这是第11行--------------------------------------
		第22_0列|呈 报单 位意 见	第22_1列|同意                                 ( 盖 章 )                                 年   月   日	
		-------------------------这是第12行--------------------------------------
		第23_0列|审 批机 关意 见	第23_1列|同意                               ( 盖 章 )                                年   月   日	
	*
	**/
}