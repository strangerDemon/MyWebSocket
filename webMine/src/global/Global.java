package global;

/**
 * 全局变量
 * 
 * @author demo
 *
 */
public class Global {
	/*
	 * 地图的雷数
	 */
	// 初级雷数
	public static int lowLevelBooms = 9;
	// 中级雷数
	public static int junorLevelBooms = 30;
	// 高级雷数
	public static int seniorLevelBooms = 99;
	/*
	 * 地图的长宽
	 */
	// 初级长宽
	public static int lowLevelwidth = 9;
	// 中级长宽
	public static int junorLevelwidth = 16;
	// 高级长
	public static int seniorLevelwidth = 16;
	// 高级宽
	public static int seniorLevelhigh = 30;
	/*
	 * 空格数
	 */
	// 初级的空格数
	public static int lowLevelSpaceNum = 72;
	// 中级的空格数
	public static int junorLevelSpaceNum = 226;
	// 高级的空格数
	public static int seniorLevelSpaceNum = 221;
	/*
	 * 数字代表的状态
	 */
	// 雷的状态
	public static int isMine = -1;
	// 周围雷数的状态
	public static int isNum0 = 0;
	public static int isNum1 = 1;
	public static int isNum2 = 2;
	public static int isNum3 = 3;
	public static int isNum4 = 4;
	public static int isNum5 = 5;
	public static int isNum6 = 6;
	public static int isNum7 = 7;
	public static int isNum8 = 8;
	// 没有点击状态
	public static int isUnClick = 9;
	public static int isFlag = 10;
	public static int isUnKnow = 11;

	/*
	 * 地雷图片地址
	 */
	// 地雷样式1地址
	public static String boomAddress1 = "iamge/icon.gif";
	// 地雷样式2地址
	public static String boomAddress2 = "";
	/*
	 * 地雷数图片地址
	 */
	// 数字0的地址
	public static String num0 = "image/0.gif";
	// 数字1的地址
	public static String num1 = "image/1.gif";
	// 数字2的地址
	public static String num2 = "image/2.gif";
	// 数字3的地址
	public static String num3 = "image/3.gif";
	// 数字4的地址
	public static String num4 = "image/4.gif";
	// 数字5的地址
	public static String num5 = "image/5.gif";
	// 数字6的地址
	public static String num6 = "image/6.gif";
	// 数字7的地址
	public static String num7 = "image/7.gif";
	// 数字8的地址
	public static String num8 = "image/8.gif";
	/*
	 * 状态图片地址
	 */
	// 没有点击时的图片地址
	public static String unClick = "image/blank.gif";
	// 点击爆炸的地址
	public static String boom = "image/mime3.gif";
	// 旗子的地址
	public static String flag = "image/flag.gif";
	// 疑问的地址
	public static String ask = "image/ask.gif";
	/*
	 * 时间图片地址
	 */
	// 时间0的地址
	public static String time0 = "image/d0.gif";
	// 时间1的地址
	public static String time1 = "image/d1.gif";
	// 时间2的地址
	public static String time2 = "image/d2.gif";
	// 时间3的地址
	public static String time3 = "image/d3.gif";
	// 时间4的地址
	public static String time4 = "image/d4.gif";
	// 时间5的地址
	public static String time5 = "image/d5.gif";
	// 时间6的地址
	public static String time6 = "image/d6.gif";
	// 时间7的地址
	public static String time7 = "image/d7.gif";
	// 时间8的地址
	public static String time8 = "image/d8.gif";
	// 时间9的地址
	public static String time9 = "image/d9.gif";
}
