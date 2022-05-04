package jp.hishidama.robot;

import java.awt.AWTException;

public class RobotUtilTest {

	/**
	 * @param args
	 * @throws AWTException
	 */
	public static void main(String[] args) throws AWTException {

		RobotUtil robot = new RobotUtil();
		String str = robot.copyString();
		System.out.println(str);
	}

}
