package com.ideabobo.wechat;


import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;


public class MainHandle {
	
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		
		try {
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			
			String time = new Date().getTime()+"";
			// 默认回复此文本消息
			TextMessage textMessage = new TextMessage();
			NewsMessage picmsg = new NewsMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setContent("欢迎关注!");
			if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){
				
			}else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {

			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)){
				String event = requestMap.get("Event");
				if(event.equals("subscribe")){
					textMessage.setContent("欢迎关注!您现在可以点菜订座啦!");
					respMessage = MessageUtil.textMessageToXml(textMessage);;
				}else if(event.equals("CLICK")){
					String eventKey = requestMap.get("EventKey");
					if(eventKey.equals("v_diancan")){						
						picmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
						picmsg.setFromUserName(toUserName);
						picmsg.setToUserName(fromUserName);
						Article a = new Article();
						a.setDescription("微信点餐方便快捷!");
						a.setPicUrl("http://ideabobo.com/web/1.jpg");
						a.setTitle("微信点餐>>>>");
						a.setUrl("http://dealbb.los12.ectomcat.com/wehall/client/wehall.html?openid="+fromUserName);
						ArrayList<Article> al = new ArrayList<Article>();
						al.add(a);
						picmsg.setCreateTime(new Date().getTime());
						picmsg.setArticles(al);
						picmsg.setArticleCount(1);
						respMessage = MessageUtil.newsMessageToXml(picmsg);
					}else if(eventKey.equals("v_dingzuo")){
						picmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
						picmsg.setFromUserName(toUserName);
						picmsg.setToUserName(fromUserName);
						picmsg.setCreateTime(new Date().getTime());
						picmsg.setArticleCount(1);
						Article a = new Article();
						a.setDescription("微信订座方便快捷!");
						a.setPicUrl("http://ideabobo.com/web/1.jpg");
						a.setTitle("微信订座>>>>");
						a.setUrl("http://dealbb.los12.ectomcat.com/wehall/client/wehall.html?openid="+fromUserName);
						ArrayList<Article> al = new ArrayList<Article>();
						al.add(a);
						picmsg.setArticles(al);
						picmsg.setArticleCount(1);
						respMessage = MessageUtil.newsMessageToXml(picmsg);
					}else if(eventKey.equals("v_contact")){
						textMessage.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_TEXT);
						textMessage.setContent("电话:15123384884");
						respMessage = MessageUtil.textMessageToXml(textMessage);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(respMessage);
		return respMessage;
	}

	/**
	 * emoji表情转换(hex -> utf-16)
	 * 
	 * @param hexEmoji
	 * @return
	 */
	public static String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}
}