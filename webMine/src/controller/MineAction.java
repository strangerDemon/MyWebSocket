package controller;

import global.Global;

/**
 * 地雷动作的操作
 * 
 * @author demo
 *
 */
public class MineAction {
	// 等级
	public int type;
	// 点击的位置
	public int x;
	public int y;
	// 记录实际的地雷状态
	public int[][] mines;
	// 记录现在的地雷状态
	public int[][] nowmines = new int[9][9];

	public String init() {
		int width = 0;
		int high = 0;
		int mine = 0;
		if (type == 1) {
			width = high = Global.lowLevelwidth;
			mine = Global.lowLevelBooms;
		} else if (type == 2) {
			width = high = Global.junorLevelwidth;
			mine = Global.junorLevelBooms;
		} else {
			width = Global.seniorLevelwidth;
			high = Global.seniorLevelhigh;
			mine = Global.seniorLevelBooms;
		}
		InitBoom initBoom = new InitBoom(width, high, mine);
		mines = initBoom.getBooms();
		nowmines = new int[width][high];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < high; j++) {
				nowmines[i][j] = Global.isUnClick;
			}
		}
		return "true";
	}

	public String click(){
		return null;
	}
	
	public int getType() {
		return type;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int[][] getNowmines() {
		return nowmines;
	}

}
