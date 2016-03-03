package controller;

import global.Global;
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
		return "true";
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

	// 左键点击操作
	public String leftClick() {
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
		// 点击出的不是0
		if (this.mines[this.x][this.y] != 0) {
			this.nowmines[this.x][this.y] = this.mines[this.x][this.y];
			updateBarData(1);
		} else {
			clickIsNum0(x, y);
			updateBarData(countBar());
		}
		saveMines("nowmines", this.nowmines);
		return "true";
	}

	// 计算已经点击出的非雷
	private int countBar() {
		int count = 0;
		for (int i = 0; i < nowmines.length; i++) {
			for (int j = 0; j < nowmines[i].length; j++) {
				if ((nowmines[i][j] != 9) && (nowmines[i][j] != 10) && (nowmines[i][j] != 11)) {
					count++;
				}
			}
		}
		return count;

	}

	// 右键点击事件
	public String rightClick() {
		// 获取雷图
		this.mines = getMines("mines");
		this.nowmines = getMines("nowmines");
		// 点击的不是空格
		if (nowmines[x][y] == Global.isFlag || // 旗子状态,10
				nowmines[x][y] == Global.isUnClick || // 未点击状态状态,9
				nowmines[x][y] == Global.isUnKnow) {// 疑问状态,11
			nowmines[x][y] = (nowmines[x][y] - 8) % 3 + 9;
			updateBarData(0);// 进度条不变
			saveMines("nowmines", this.nowmines);
			return "true";
		}
		return "false";
	}

	// 点击空事件
	public void clickIsNum0(int x, int y) {
		if (this.mines[x][y] != 0) {// 不是空格
			nowmines[x][y] = mines[x][y];
			return;
		}
		if (this.nowmines[x][y] != 9) {// 不是未点击
			nowmines[x][y] = mines[x][y];
			return;
		}
		// 下面对四个方向的格子进行自动点开
		// 你需要计算四向的格子位置，无效的直接返回
		nowmines[x][y] = mines[x][y];
		if (x == 0) {
			if (y == 0) {
				clickIsNum0(x, y + 1);
				clickIsNum0(x + 1, y + 1);
			} else if (y == nowmines[0].length - 1) {
				clickIsNum0(x, y - 1);
				clickIsNum0(x + 1, y - 1);
			} else {
				clickIsNum0(x, y + 1);
				clickIsNum0(x + 1, y + 1);
				clickIsNum0(x, y - 1);
				clickIsNum0(x + 1, y - 1);
			}
			clickIsNum0(x + 1, y);
		} else if (x == nowmines.length - 1) {
			if (y == 0) {
				clickIsNum0(x, y + 1);
				clickIsNum0(x - 1, y + 1);
			} else if (y == nowmines[0].length - 1) {
				clickIsNum0(x, y - 1);
				clickIsNum0(x - 1, y - 1);
			} else {
				clickIsNum0(x, y + 1);
				clickIsNum0(x - 1, y + 1);
				clickIsNum0(x, y - 1);
				clickIsNum0(x - 1, y - 1);
			}
			clickIsNum0(x - 1, y);
		} else {
			if (y == 0) {
				clickIsNum0(x - 1, y + 1);
				clickIsNum0(x, y + 1);
				clickIsNum0(x + 1, y + 1);
			} else if (y == nowmines[0].length - 1) {
				clickIsNum0(x, y - 1);
				clickIsNum0(x - 1, y - 1);
				clickIsNum0(x + 1, y - 1);
			} else {
				clickIsNum0(x, y - 1);
				clickIsNum0(x - 1, y - 1);
				clickIsNum0(x - 1, y + 1);
				clickIsNum0(x, y + 1);
				clickIsNum0(x + 1, y - 1);
				clickIsNum0(x + 1, y + 1);
			}
			clickIsNum0(x - 1, y);
			clickIsNum0(x + 1, y);
		}
	}

	// 更新进度条的数据
	private void updateBarData(int num) {
		if (num < 2) {
			this.clickSpace = (getBarData("clickSpace") + num);
		} else {
			this.clickSpace = num;
		}
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