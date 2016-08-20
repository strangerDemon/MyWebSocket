package com;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy��MM��dd�� H:m:s");	
		String date=format.format(new Date());
		String message = "<li class='clearfix'>"+
								"<div class='message-data align-right'>"+
									" <span class='message-data-name'>"+date+"</span>"+
									"<i class='fa fa-circle me'></i>"+
								"</div>"+
								"<div class='message login-message float-right'>���û�"+session.toString().substring(session.toString().indexOf("@")+1)+
								"���룡	��ǰ��������Ϊ:" + getOnlineCount()+
								"</div>"+
							"</li>";
		sendAllUser(message,session,1);
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

		SimpleDateFormat format = new SimpleDateFormat("yyyy��MM��dd�� H:m:s");	
		String date=format.format(new Date());
		String message = "<li class='clearfix'>"+
								"<div class='message-data align-right'>"+
									" <span class='message-data-name'>"+date+"</span>"+
									"<i class='fa fa-circle me'></i>"+
								"</div>"+
								"<div class='message logout-message float-right'>�û�"+session.toString().substring(session.toString().indexOf("@")+1)+
								"�˳���	��ǰ��������Ϊ"+getOnlineCount()+
								"</div>"+
							"</li>";
		// Ⱥ����Ϣ
		sendAllUser(message,session,3);

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
		SimpleDateFormat format = new SimpleDateFormat("yyyy��MM��dd�� H:m:s");	
		String date=format.format(new Date());
		String message2 = "<li class='clearfix'>"+
								"<div class='message-data align-right'>"+
									"<span class='message-data-time' style='font-size:20px'>"+session.toString().substring(session.toString().indexOf("@")+1)+"</span>"+
									" <span class='message-data-name'>"+date+"</span>"+
									"<i class='fa fa-circle me'></i>"+
								"</div>"+
								"<div class='message other-message float-right'>"+
									message+
								"</div>"+
							"</li>";
		sendAllUser(message2,session,2);
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
						item.sendMessage(message);
					}else{
						item.sendMessage(message);
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