package com;

import java.io.IOException;

import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

//ҳ������ӵķ�����websocket
@ServerEndpoint("/websocket")
public class MyWebSocket {

	// ��̬������������¼��ǰ������������Ӧ�ð�����Ƴ��̰߳�ȫ�ġ�
	private static int onlineCount = 0;

	// concurrent�����̰߳�ȫSet���������ÿ���ͻ��˶�Ӧ��MyWebSocket����
	// ��Ҫʵ�ַ�����뵥һ�ͻ���ͨ�ŵĻ�������ʹ��Map����ţ�����Key����Ϊ�û���ʶ
	private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();
	// ��ĳ���ͻ��˵����ӻỰ����Ҫͨ���������ͻ��˷�������
	private Session session;

	/**
	 * 
	 * ���ӽ����ɹ����õķ���
	 * 
	 * @param session
	 *            ��ѡ�Ĳ�����sessionΪ��ĳ���ͻ��˵����ӻỰ����Ҫͨ���������ͻ��˷�������
	 */

	@OnOpen
	public void onOpen(Session session) {

		this.session = session;

		webSocketSet.add(this); // ����set��

		addOnlineCount(); // ��������1

		System.out.println("�������Ӽ��룡��ǰ��������Ϊ" + getOnlineCount());
		
		// Ⱥ����Ϣ
		sendAllUser("���û�<h3>"+session.toString().substring(session.toString().indexOf("@"))+"</h3>���룡	��ǰ��������Ϊ:" + getOnlineCount(),session,1);

	}

	/**
	 * 
	 * ���ӹرյ��õķ���
	 */

	@OnClose
	public void onClose() {

		webSocketSet.remove(this); // ��set��ɾ��

		subOnlineCount(); // ��������1

		System.out.println("��һ���ӹرգ���ǰ��������Ϊ" + getOnlineCount());
		
		// Ⱥ����Ϣ
		sendAllUser("�û�<h3>"+this.session.toString().substring(session.toString().indexOf("@"))+"</h3>�˳���	��ǰ��������Ϊ:" + getOnlineCount(),session,3);

	}

	/***
	 * �յ��ͻ�����Ϣ����õķ��� websocket��Ҫ�������û�websocket����Ϣ����
	 * 
	 * @param message �ͻ��˷��͹�������Ϣ
	 * @param session
	 */

	@OnMessage
	public void onMessage(String message, Session session) {

		System.out.println("���Կͻ��˵���Ϣ:" + message+"--session"+session);
		// Ⱥ����Ϣ
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
	 * ��������ʱ����
	 * 
	 * @param session
	 * @param error
	 */

	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("��������");
		error.printStackTrace();
	}

	/**
	 * ����ķ���һ����Ϣ
	 */
	public void sendMessage(String message) throws IOException {
		//System.out.println(message);
		this.session.getBasicRemote().sendText(message);
	}
	
	/**
	 * �����������û�����Ϣ
	 * @return
	 */
	public void sendAllUser(String message, Session session,int type){
		
		for (MyWebSocket item : webSocketSet) {
			try {
				if(item.session!=session){//������Ϣ���Լ�
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
	 * ���͸�ָ�����
	 * @param message ��Ϣ
	 * @param name	�û���
	 * @param session �Լ�
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