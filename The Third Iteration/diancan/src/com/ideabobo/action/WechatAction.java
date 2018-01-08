package com.ideabobo.action;

import java.io.PrintWriter;

import org.springframework.stereotype.Controller;

import com.ideabobo.util.IdeaAction;
import com.ideabobo.wechat.MainHandle;

@Controller
public class WechatAction extends IdeaAction {
	public void wechat(){
		//成为开发者
				/*response.setCharacterEncoding("UTF-8");
				request.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				String echoStr = request.getParameter("echostr");
				out.print(echoStr);*/
				// 调用核心业务类接收消息、处理消息
				String respMessage = MainHandle.processRequest(request);
				render(respMessage);
				// 响应消息
//				response.setContentType("text/html");
//				response.setCharacterEncoding("utf-8");
//				PrintWriter out = response.getWriter();		
//				out.print(respMessage);		
//				out.close();
//				String echoStr = request.getParameter("echostr");
//				render(echoStr);
	}
}
