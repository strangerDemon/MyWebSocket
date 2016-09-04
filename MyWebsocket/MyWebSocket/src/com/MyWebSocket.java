package com;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

//页面端连接的服务器websocket
@ServerEndpoint("/websocket")
public class MyWebSocket {

	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;

	// concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
	// 若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
	private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();
	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;
	//存储所有用户的昵称
	private static Map<String,String> userName=new HashMap<String,String>();
	
	/**
	 * 
	 * 连接建立成功调用的方法
	 * 
	 * @param session
	 *            可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */

	@OnOpen
	public void onOpen(Session session) {

		this.session = session;

		webSocketSet.add(this); // 加入set中
		
		
		addOnlineCount(); // 在线数加1

		System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
		
	}

	/**
	 * 
	 * 连接关闭调用的方法
	 */

	@OnClose
	public void onClose() {

		webSocketSet.remove(this); // 从set中删除
		
		subOnlineCount(); // 在线数减1

		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());

		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 H:m:s");	
		String date=format.format(new Date());
		String message = "lgout<li class='clearfix' key='"+session.toString().substring(session.toString().indexOf("@")+1)+"'>"+
								"<div class='message-data align-right'>"+
									"<span class='message-data-time'>"+date+"</span>"+
								"</div>"+
								"<div class='message logout-message float-right' style='height:80px;width=80px;'>用户"+userName.get(session.toString().substring(session.toString().indexOf("@")+1))+
								"退出！	当前在线人数为"+getOnlineCount()+
								"</div>"+
							"</li>";
		
		userName.remove(session.toString().substring(session.toString().indexOf("@")+1));
		// 群发消息
		sendAllUser(message,session,3);

	}

	/***
	 * 收到客户端消息后调用的方法 websocket主要方法，用户websocket的消息传递
	 * 
	 * @param message 客户端发送过来的消息
	 * @param session
	 */

	@OnMessage
	public void onMessage(String message, Session session) {
		
		System.out.println("来自客户端的消息:" + message+"--session"+session);
		//把name添加到map中，获取自己的 user列表
		//刷新别人的用户列表
		if(message.equals("#allUser")||(message.length()>5 && message.substring(0, 5).equals("#name"))){
			userName.put(session.toString().substring(session.toString().indexOf("@")+1),message.substring(5));
			getUserListAndUpdate(session);
			return;
		}		
		// 群发消息
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 H:m:s");	
		String date=format.format(new Date());
		String message2 = "<li class='clearfix'>"+
								"<div class='message-data align-right'>"+
									"<span class='message-data-time'>"+date+"</span>"+
									" <span class='message-data-name' style='font-size:20px'>"+userName.get(session.toString().substring(session.toString().indexOf("@")+1))+"</span>"+
								"</div>"+
								"<div class='message other-message float-right'>"+
									message+
								"</div>"+
							"</li>";
		sendAllUser(message2,session,2);
	}

	/**
	 * 发生错误时调用
	 * 
	 * @param session
	 * @param error
	 */

	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}

	/**
	 * 具体的发送一条消息
	 */
	public void sendMessage(String message) throws IOException {
		//System.out.println(message);
		this.session.getBasicRemote().sendText(message);
	}
	
	/**
	 * 遍历给所有用户发消息
	 * @return
	 */
	public void sendAllUser(String message, Session session,int type){
		
		for (MyWebSocket item : webSocketSet) {
			try {
				if(item.session!=session){//不发消息给自己
					item.sendMessage(message);			
				}
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}

		}
	}
	
	/**
	 * 登录的用户获取所有的用户列表
	 * 已经登录的用户增加用户列表
	 * @param session
	 */
	public void getUserListAndUpdate(Session session){
		String allUser="";
		String user="";
		for (MyWebSocket item : webSocketSet) {
			if(item.session==session){
				user="allUs<li class='clearfix' key='"+session.toString().substring(session.toString().indexOf("@")+1)+"'>"+
						"<img src='images/faceImage/1.jpg' alt='avatar' />"+
						"<div class='about'>"+
							"<div class='name'>我</div>"+				
						"</div></li>";	
				allUser=user+allUser;
			}else{
				user="<li class='clearfix' key='"+item.session.toString().substring(session.toString().indexOf("@")+1)+"'>"+
						"<img src='images/faceImage/3.jpg' alt='avatar' />"+
						"<div class='about'>"+
							"<div class='name'>"+userName.get(item.session.toString().substring(session.toString().indexOf("@")+1))+"</div>"+				
						"</div></li>";	
				allUser+=user;
				
				user="login<li class='clearfix' key='"+session.toString().substring(session.toString().indexOf("@")+1)+"'>"+
						"<img src='images/faceImage/3.jpg' alt='avatar' />"+
						"<div class='about'>"+
							"<div class='name'>"+userName.get(session.toString().substring(session.toString().indexOf("@")+1))+"</div>"+				
						"</div></li>";	
				
				try{
					SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 H:m:s");	
					String date=format.format(new Date());
					String message = "login<li class='clearfix' key='"+session.toString().substring(session.toString().indexOf('@')+1)+"'>"+
											"<div class='message-data align-right'>"+
												" <span class='message-data-time'>"+date+"</span>"+
											"</div>"+
											"<div class='message login-message float-right' style='height:80px;width=80px;'>新用户"+userName.get(session.toString().substring(session.toString().indexOf("@")+1))+
											"加入！	当前在线人数为:" + getOnlineCount()+
											"</div>"+
										"</li>";
					item.sendMessage(message);
				}catch (IOException e) {
					e.printStackTrace();
					continue;
				}
				
			}
		}
		for (MyWebSocket item : webSocketSet) {
			try {
				if(item.session==session){//发消息给自己
					item.sendMessage(allUser);				
				}
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}

		}
	}
	/**
	 * 发送给指点的人
	 * @param message 信息
	 * @param name	用户名
	 * @param session 自己
	 * @param type
	 */
	public void sendToSomeOne(String message,String name,Session session){
		
	}
	
	public static synchronized int getOnlineCount() {

		return onlineCount;

	}

	public static synchronized void addOnlineCount() {

		MyWebSocket.onlineCount++;

	}

	public static synchronized void subOnlineCount() {

		MyWebSocket.onlineCount--;

	}

}