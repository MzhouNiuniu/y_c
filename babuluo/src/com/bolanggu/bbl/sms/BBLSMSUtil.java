package com.bolanggu.bbl.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import com.bolanggu.bbl.lottery.HttpClientUtil;
import com.pabula.common.util.RandomNum;
import com.task.job.ShopOrderJob;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BBLSMSUtil {

    //使用StringBuffer的append获得xml形式的字符串
    StringBuffer sub=new StringBuffer();
    BufferedReader br=null;
    URL url=null;
    HttpURLConnection con;
    String line;
    private Logger log = LoggerFactory.getLogger(BBLSMSUtil.class);

    public String reg(String phone){
        RandomNum randomNum = new RandomNum();
        randomNum.setRange(100000, 999999);
        String code = "";
        try {
            randomNum.generateRandomObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String randomStr = randomNum.getRandom().toString();




        String smsContent = "【云厨1站】 您的验证码：" + randomStr;

        SendMessage(phone, smsContent);

        return smsContent;
    }



    public static void main(String[] args) {



        xmlEntity xmlentity=new xmlEntity();
        String xml=null;
        BBLSMSUtil t=new BBLSMSUtil();
//
//        //查询调用方法
//        xml=t.SelSum("", "jksc228", "jksc22833").toString();
//        //输出xml形式的字符串
//        System.out.println("输出xml"+xml);
//
//        //赋值给xmlEntity实体类
//        xmlentity.setReturnstatus("returnstatus");
//        xmlentity.setMessage("message");
//        xmlentity.setPayinfo("payinfo");
//        xmlentity.setOverage("overage");
//        xmlentity.setSendTotal("sendTotal");
//        //调用XML字符串解析通用方法
//        xmlentity=t.readStringXmlCommen(xmlentity, xml);
//        System.out.println("返回状态为:"+xmlentity.getReturnstatus()+"\n"+"返回付费方式:"+xmlentity.getPayinfo());
//
//	    int zong=Integer.parseInt(xmlentity.getSendTotal());
//	    int yong=Integer.parseInt(xmlentity.getOverage());
//	    int sheng=zong-yong;
//	    System.out.println("总条数为："+zong);
//	    System.out.println("已使用："+yong);
//	    System.out.println("剩余：："+sheng);

	    //发送调用
		xml=t.SendMessage("", "jksc228", "jksc22833", "18255162781", "【云厨1站】 您的验证码：888888", "").toString();
		System.out.println(xml);
		t.readStringXml(xml);
//        xmlentity.setReturnstatus("returnstatus");
//        xmlentity.setMessage("message");
//        xmlentity.setRemainpoint("remainpoint");
//        xmlentity.setTaskID("taskID");
//        xmlentity.setSuccessCounts("successCounts");
//        //xmlentity=t.readStringXmlCommen(xmlentity, xml);
//        System.out.println("状态"+xmlentity.getReturnstatus()+"返回信息"+xmlentity.getMessage()+"成功条数"+xmlentity.getSuccessCounts());

//
//        //状态报告
//		xml=t.StatusReport("1111", "qqqq", "mima").toString();
//		System.out.println(xml);
//		xmlentity.setStatusbox("statusbox");
//		xmlentity.setMobile("mobile");
//		xmlentity.setTaskid("taskid");
//		xmlentity.setStatus("status");
//		xmlentity.setReceivetime("receivetime");
//		xmlentity.setErrorstatus("errorstatus");
//		xmlentity.setError("error");
//		xmlentity.setRemark("remark");
//		xmlentity=t.readStringXmlCommen(xmlentity, xml);
//		System.out.println("对应手机号："+xmlentity.getMobile()+"对应状态"+xmlentity.getStatus()+"对应接收时间"+xmlentity.getReceivetime());
//		System.out.println("错误代码："+xmlentity.getError());

    }


    //查询余额
    public StringBuffer SelSum(String userid,String account,String password)
    {
        try {
            url=new URL("http://sh2.ipyy.com/sms.aspx?action=overage&userid="+userid+"&account="+account+"&password="+password+"");
            con = (HttpURLConnection)url.openConnection();
            //br=new BufferedReader(new InputStreamReader(url.openStream()));
            br=new  BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));

            while((line=br.readLine())!=null)
            {
                //追加字符串获得XML形式的字符串
                sub.append(line+"");
                //System.out.println("提取数据 :  "+line);
            }
            br.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            return sub;
        }

    }


    //发送短信
    public StringBuffer SendMessage(String mobile,String content){
        return SendMessage("", "jksc228", "jksc22833", mobile, content, "");
    }

    //发送短信
    public StringBuffer SendMessage(String userid,String account,String password,String mobile,String content,String sendTime)
    {

        try {
            //设置发送内容的编码方式
            String send_content=URLEncoder.encode(content.replaceAll("<br/>", " "), "UTF-8");//发送内容

            //url=new URL("http://sh2.cshxsp.com/smsJson.aspx?action=send&userid="+userid+"&account="+account+"&password="+password+"&mobile="+mobile+"&content="+send_content+"&sendTime="+sendTime+"");
            //url=new URL("http://sh2.ipyy.com/sms.aspx?action=send&userid="+userid+"&account="+account+"&password="+password+"&mobile="+mobile+"&content="+send_content+"&sendTime="+sendTime+"");
            //url=new URL("https://sh2.ipyy.com/sms.aspx?action=send&userid="+userid+"&account="+account+"&password="+password+"&mobile="+mobile+"&content="+send_content+"&sendTime="+sendTime+"");
            System.out.println(url);
            //con = (HttpURLConnection)url.openConnection();
            //br=new  BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
            //br=new BufferedReader(new InputStreamReader(url.openStream()));
            /*while((line=br.readLine())!=null)
            {
                //追加字符串获得XML形式的字符串
                sub.append(line+"");
                //System.out.println("提取数据 :  "+line);
            }*/
            //https方式
            String url = "https://sh2.ipyy.com/sms.aspx";
            String params = "action=send&userid="+userid+"&account="+account+"&password="+password+"&mobile="+mobile+"&content="+send_content+"&sendTime="+sendTime;

            line = HttpClientUtil.sendPostRequest("https://sh2.ipyy.com/sms.aspx", params, "UTF-8");
            sub.append(line);
            log.info("mobile="+mobile+"&content="+send_content);
            readStringXml(line);
            br.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            return sub;
        }
    }

    //状态报告接口
    public StringBuffer StatusReport(String userid,String account,String password )
    {
        try {
            url=new URL("http://sh2.ipyy.com/statusApi.aspx?action=query&userid="+userid+"&account="+account+"&password="+password+"");
            con = (HttpURLConnection)url.openConnection();

            //br=new BufferedReader(new InputStreamReader(url.openStream()));

            br=new  BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));

            while((line=br.readLine())!=null)
            {
                //追加字符串获得XML形式的字符串
                sub.append(line+"");
                //	System.out.println("提取数据 :  "+line);
            }
            br.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            return sub;
        }
    }


    //解析xml字符串
    public void readStringXml(String xml)
    {
        Document doc=null;

        try {
            //将字符转化为XML
            doc=DocumentHelper.parseText(xml);
            //获取根节点
            Element rootElt=doc.getRootElement();

            //拿到根节点的名称
            //System.out.println("根节点名称："+rootElt.getName());

         // 获取根节点下的子节点的值
		String returnstatus = rootElt.elementText("returnstatus").trim();
		String message = rootElt.elementText("message").trim();
		String remainpoint = rootElt.elementText("remainpoint").trim();
		String taskID = rootElt.elementText("taskID").trim();
		String successCounts = rootElt.elementText("successCounts").trim();

		log.info("返回状态为：" + returnstatus);
		log.info("返回信息提示：" + message);
		log.info("返回余额：" + remainpoint);
		log.info("返回任务批次：" + taskID); 
		log.info("返回成功条数：" + successCounts);

        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    //XML字符串解析通用方法
    public xmlEntity readStringXmlCommen(xmlEntity xmlentity,String xml)
    {
        xmlEntity xe=new xmlEntity();

        Document doc=null;

        try {
            //将字符转化为XML
            doc=DocumentHelper.parseText(xml);
            //获取根节点
            Element rootElt=doc.getRootElement();
            //拿到根节点的名称
            //System.out.println("根节点：" + rootElt.getName());

            //获取根节点下的子节点的值
            if(xmlentity.getReturnstatus()!=null)
            {
                xe.setReturnstatus(rootElt.elementText(xmlentity.getReturnstatus()).trim());
            }
            if(xmlentity.getMessage()!=null)
            {
                xe.setMessage(rootElt.elementText(xmlentity.getMessage()).trim());
            }
            if(xmlentity.getRemainpoint()!=null)
            {
                xe.setRemainpoint(rootElt.elementText(xmlentity.getRemainpoint()).trim());
            }
            if(xmlentity.getTaskID()!=null)
            {
                xe.setTaskID(rootElt.elementText(xmlentity.getTaskID()).trim());
            }
            if(xmlentity.getSuccessCounts()!=null)
            {
                xe.setSuccessCounts(rootElt.elementText(xmlentity.getSuccessCounts()).trim());
            }
            if(xmlentity.getPayinfo()!=null)
            {
                xe.setPayinfo(rootElt.elementText(xmlentity.getPayinfo()).trim());
            }
            if(xmlentity.getOverage()!=null)
            {
                xe.setOverage(rootElt.elementText(xmlentity.getOverage()).trim());
            }
            if(xmlentity.getSendTotal()!=null)
            {
                xe.setSendTotal(rootElt.elementText(xmlentity.getSendTotal()).trim());
            }
            //接收状态返回的报告
            if(rootElt.hasMixedContent()==false)
            {
                System.out.println("无返回状态！");
            }
            else
            {
                for (int i = 1; i <= rootElt.elements().size(); i++) {
                    if(xmlentity.getStatusbox()!=null)
                    {
                        System.out.println("状态"+i+":");
                        //获取根节点下的子节点statusbox
                        Iterator iter = rootElt.elementIterator(xmlentity.getStatusbox());
                        // 遍历statusbox节点
                        while(iter.hasNext())
                        {
                            Element recordEle = (Element) iter.next();
                            xe.setMobile(recordEle.elementText("mobile").trim());
                            xe.setTaskid(recordEle.elementText("taskid").trim());
                            xe.setStatus(recordEle.elementText("status").trim());
                            xe.setReceivetime(recordEle.elementText("receivetime").trim());
                            System.out.println("对应手机号："+xe.getMobile());
                            System.out.println("同一批任务ID："+xe.getTaskid());
                            System.out.println("状态报告----10：发送成功，20：发送失败："+xe.getStatus());
                            System.out.println("接收时间："+xe.getReceivetime());
                        }
                    }

                }

            }

            //错误返回的报告
            if(xmlentity.getErrorstatus()!=null)
            {
                //获取根节点下的子节点errorstatus
                Iterator itererr = rootElt.elementIterator(xmlentity.getErrorstatus());
                // 遍历errorstatus节点
                while(itererr.hasNext())
                {
                    Element recordElerr = (Element) itererr.next();
                    xe.setError(recordElerr.elementText("error").trim());
                    xe.setRemark(recordElerr.elementText("remark").trim());
                    System.out.println("错误代码："+xe.getError());
                    System.out.println("错误描述："+xe.getRemark());
                }
            }

			if(xmlentity.getCallbox()!=null)
			{
				//获取根节点下的子节点errorstatus
				Iterator itercallbox = rootElt.elementIterator("errorstatus");
				// 遍历errorstatus节点
				while(itercallbox.hasNext())
				{
					Element recordcallbox = (Element) itercallbox.next();
					String content=recordcallbox.elementText("content").trim();
					String receivetime=recordcallbox.elementText("receivetime").trim();
					String mobile=recordcallbox.elementText("mobile").trim();
					String taskid=recordcallbox.elementText("taskid").trim();

				}
			}

        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return xe;
    }
}
