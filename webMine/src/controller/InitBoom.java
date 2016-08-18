package controller;

import java.util.Random;

/**
 * 初始化雷
 * 
 * @author demo
 *
 */
public class InitBoom {
	/*
	 * 初始化雷，初级，中级，高级 初级：9*9,9个雷 中级：16*16,30个雷 高级：30*16,99个雷 自定义：暂不提供 num：雷数
	 */
	// 初始化的时候为二维数组外面加一个边框，这样子计算周围雷数的时候减少工作量
	public int booms[][];

	public InitBoom(int width, int high, int num) {
		booms = new int[width + 2][high + 2];
		Random random = new Random();
		// 初始化都设置为0
		for (int i = 0; i < width + 2; i++) {
			for (int j = 0; j < high + 2; j++) {
				booms[i][j] = 0;
			}
		}
		// 初始化雷的位置
		int nowWidth = 0;
		int nowHigh = 0;
		for (int i = 0; i < num;) {
			nowWidth = random.nextInt(width) + 1;
			nowHigh = random.nextInt(high) + 1;
			if (booms[nowWidth][nowHigh] == 0) {
				booms[nowWidth][nowHigh] = -1;
				i++;
			}
		}
		// 初始化计算周边雷数
		for (int i = 1; i <= width; i++) {
			for (int j = 1; j <= high; j++) {
				// 直接是雷的不计算
				if (booms[i][j] == -1)
					continue;
				if (booms[i - 1][j - 1] == -1)
					booms[i][j] += 1;
				if (booms[i][j - 1] == -1)
					booms[i][j] += 1;
				if (booms[i + 1][j - 1] == -1)
					booms[i][j] += 1;

				if (booms[i - 1][j] == -1)
					booms[i][j] += 1;
				if (booms[i][j] == -1)
					booms[i][j] += 1;
				if (booms[i + 1][j] == -1)
					booms[i][j] += 1;

				if (booms[i - 1][j + 1] == -1)
					booms[i][j] += 1;
				if (booms[i][j + 1] == -1)
					booms[i][j] += 1;
				if (booms[i + 1][j + 1] == -1)
					booms[i][j] += 1;
			}
		}
		/*
		 * for (int i = 0; i < width+2; i++) { for (int j = 0; j < high+2; j++)
		 * { System.out.print(booms[i][j]+"\t"); } System.out.println(); }
		 */
	}

	public int[][] getBooms() {
		int[][] to = new int[booms.length - 2][];
		for (int i = 0; i < booms.length-2; i++) {
			to[i] = new int[booms[i + 1].length-2];
			for (int j = 0; j < to[i].length; j++) {
				to[i][j] = booms[i+1][j+1];			
			}
		}
		return to;
	}

}
