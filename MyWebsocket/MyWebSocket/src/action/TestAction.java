package action;

import com.opensymphony.xwork2.ActionSupport;

public class TestAction extends ActionSupport{
	public String execute() {
		return SUCCESS;
	}
	
	public String Test(){
		System.out.println("struts ����ת�ɹ�!");
		return "true";
	}
}
