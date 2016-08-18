package com;

import java.io.IOException;

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
		
		// 群发消息
		sendAllUser("新用户<h3>"+session.toString().substring(session.toString().indexOf("@"))+"</h3>加入！	当前在线人数为:" + getOnlineCount(),session,1);

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
		
		// 群发消息
		sendAllUser("用户<h3>"+this.session.toString().substring(session.toString().indexOf("@"))+"</h3>退出！	当前在线人数为:" + getOnlineCount(),session,3);

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
		// 群发消息
<<<<<<< HEAD
		String date=new Date().toLocaleString();
		String message2 = "<li class='clearfix'>"+
								"<div class='message-data align-right'>"+
									"<span class='message-data-time'>"+date+"</span>"+
									"<span class='message-data-name'>"+session.toString().substring(session.toString().indexOf("@")+1)+"</span>"+
									"<i class='fa fa-circle me'></i>"+
								"</div>"+
								"<div class='message other-message float-right'>"+
									message+
								"</div>"+
							"</li>";
		System.out.println(message2);
		sendAllUser(message2,session,2);
=======
		sendAllUser(message,session,2);
		

>>>>>>> 7c66df6908cae9fff128ffeef55805c564601062
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
					if(type!=2){
						item.sendMessage("<tr class='danger' style='float:left'>"+message+"</tr><br/>");
					}else{
						item.sendMessage("<tr class='info' style='float:left'><h3>"+session.toString().substring(session.toString().indexOf("@"))+"</h3> :"+message+"</tr><br/>");
					}				
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