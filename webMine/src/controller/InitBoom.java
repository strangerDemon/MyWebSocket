package controller;

import java.util.Random;

/**
 * ��ʼ����
 * 
 * @author demo
 *
 */
public class InitBoom {
	/*
	 * ��ʼ���ף��������м����߼� ������9*9,9���� �м���16*16,30���� �߼���30*16,99���� �Զ��壺�ݲ��ṩ num������
	 */
	// ��ʼ����ʱ��Ϊ��ά���������һ���߿������Ӽ�����Χ������ʱ����ٹ�����
	public int booms[][];

	public InitBoom(int width, int high, int num) {
		booms = new int[width + 2][high + 2];
		Random random = new Random();
		// ��ʼ��������Ϊ0
		for (int i = 0; i < width + 2; i++) {
			for (int j = 0; j < high + 2; j++) {
				booms[i][j] = 0;
			}
		}
		// ��ʼ���׵�λ��
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
		// ��ʼ�������ܱ�����
		for (int i = 1; i <= width; i++) {
			for (int j = 1; j <= high; j++) {
				// ֱ�����׵Ĳ�����
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
