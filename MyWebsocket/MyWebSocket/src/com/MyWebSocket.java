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
	//�洢�����û����ǳ�
	private static Map<String,String> userName=new HashMap<String,String>();
	
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
		String message = "lgout<li class='clearfix' key='"+session.toString().substring(session.toString().indexOf("@")+1)+"'>"+
								"<div class='message-data align-right'>"+
									"<span class='message-data-time'>"+date+"</span>"+
								"</div>"+
								"<div class='message logout-message float-right' style='height:80px;width=80px;'>�û�"+userName.get(session.toString().substring(session.toString().indexOf("@")+1))+
								"�˳���	��ǰ��������Ϊ"+getOnlineCount()+
								"</div>"+
							"</li>";
		
		userName.remove(session.toString().substring(session.toString().indexOf("@")+1));
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
		//��name��ӵ�map�У���ȡ�Լ��� user�б�
		//ˢ�±��˵��û��б�
		if(message.equals("#allUser")||(message.length()>5 && message.substring(0, 5).equals("#name"))){
			userName.put(session.toString().substring(session.toString().indexOf("@")+1),message.substring(5));
			getUserListAndUpdate(session);
			return;
		}		
		// Ⱥ����Ϣ
		SimpleDateFormat format = new SimpleDateFormat("yyyy��MM��dd�� H:m:s");	
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
					item.sendMessage(message);			
				}
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}

		}
	}
	
	/**
	 * ��¼���û���ȡ���е��û��б�
	 * �Ѿ���¼���û������û��б�
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
							"<div class='name'>��</div>"+				
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
					SimpleDateFormat format = new SimpleDateFormat("yyyy��MM��dd�� H:m:s");	
					String date=format.format(new Date());
					String message = "login<li class='clearfix' key='"+session.toString().substring(session.toString().indexOf('@')+1)+"'>"+
											"<div class='message-data align-right'>"+
												" <span class='message-data-time'>"+date+"</span>"+
											"</div>"+
											"<div class='message login-message float-right' style='height:80px;width=80px;'>���û�"+userName.get(session.toString().substring(session.toString().indexOf("@")+1))+
											"���룡	��ǰ��������Ϊ:" + getOnlineCount()+
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
				if(item.session==session){//����Ϣ���Լ�
					item.sendMessage(allUser);				
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