package controller;

import global.Global;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

public class MineAction {
	public int type;
	public int x;
	public int y;
	public int[][] mines;
	public int[][] nowmines = new int[9][9];
	HttpSession httpSession;
	// 进度条
	public int clickSpace = 0;
	public int allSpace = 72;
	public int percent = 0;
	public int minesNum = 9;

	public String init() {
		int width = 0;
		int high = 0;
		if (this.type == 1) {
			width = high = Global.lowLevelwidth;
			this.minesNum = Global.lowLevelBooms;
			this.allSpace = Global.lowLevelSpaceNum;
		} else if (this.type == 2) {
			width = high = Global.junorLevelwidth;
			this.minesNum = Global.junorLevelBooms;
			this.allSpace = Global.junorLevelSpaceNum;
		} else {
			width = Global.seniorLevelwidth;
			high = Global.seniorLevelhigh;
			this.minesNum = Global.seniorLevelBooms;
			this.allSpace = Global.seniorLevelSpaceNum;
		}
		// 进度条的初始化
		this.clickSpace = (this.percent = 0);
		saveBar("clickSpace", this.clickSpace);
		saveBar("minesNum", this.minesNum);
		saveBar("allSpace", this.allSpace);
		// 雷图的初始化
		InitBoom initBoom = new InitBoom(width, high, this.minesNum);
		this.mines = initBoom.getBooms();
		this.nowmines = new int[width][high];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < high; j++) {
				this.nowmines[i][j] = Global.isUnClick;
			}
		}
		saveMines("mines", this.mines);
		saveMines("nowmines", this.nowmines);
		test();
		return "true";
	}

	//test
	private void test(){
		for (int i = 0; i < mines.length; i++) {
			for (int j = 0; j < mines[i].length; j++) {
				System.out.print(mines[i][j]+"\t");
				
			}
			System.out.println();
		}
		
		for (int i = 0; i < nowmines.length; i++) {
			for (int j = 0; j < nowmines[i].length; j++) {
				System.out.print(nowmines[i][j]+"\t");
				
			}
			System.out.println();
		}
		
	}
	
	// 保存进度条数据到session
	private void saveBar(String bar, int data) {
		this.httpSession = ServletActionContext.getRequest().getSession();
		this.httpSession.setAttribute(bar, Integer.valueOf(data));
	}

	private int getBarData(String bar) {
		this.httpSession = ServletActionContext.getRequest().getSession();
		return ((Integer) this.httpSession.getAttribute(bar)).intValue();
	}

	private void saveMines(String minesName, int[][] mines) {
		this.httpSession = ServletActionContext.getRequest().getSession();
		this.httpSession.setAttribute(minesName, mines);
	}

	private int[][] getMines(String minesName) {
		this.httpSession = ServletActionContext.getRequest().getSession();
		return (int[][]) this.httpSession.getAttribute(minesName);
	}

	// 点击操作
	public String click() {
		this.mines = getMines("mines");
		this.nowmines = getMines("nowmines");
		// 点击的不是空格
		if (!isClickSpace()) {
			updateBarData(0);
			return "true";
		}
		// 点击的是雷
		if (isMine()) {
			this.nowmines = this.mines;
			saveMines("nowmines", this.nowmines);
			updateBarData(0);
			return "false";
		}

		this.nowmines[this.x][this.y] = this.mines[this.x][this.y];
		saveMines("nowmines", this.nowmines);
		updateBarData(1);
		return "true";
	}

	// 更新进度条的数据
	private void updateBarData(int num) {
		this.clickSpace = (getBarData("clickSpace") + num);
		this.allSpace = getBarData("allSpace");
		this.percent = (this.clickSpace * 100 / this.allSpace);
		saveBar("clickSpace", this.clickSpace);
	}

	private boolean isMine() {
		if (this.mines[this.x][this.y] == -1)
			return true;
		return false;
	}

	private boolean isClickSpace() {
		if (this.nowmines[this.x][this.y] != 9)
			return false;
		return true;
	}

	public int getType() {
		return this.type;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int[][] getNowmines() {
		return this.nowmines;
	}

	public int getClickSpace() {
		return this.clickSpace;
	}

	public int getPercent() {
		return this.percent;
	}

	public int getMinesNum() {
		return this.minesNum;
	}

}