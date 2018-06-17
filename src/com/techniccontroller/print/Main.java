package com.techniccontroller.print;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.remote.RemoteMotor;
import lejos.util.Delay;

public class Main implements Serializable {

	private static ArrayList<Objekt> stock;
	private static RemoteMotor ZMotor;
	private static RemoteMotor YMotor;
	private static RemoteMotor XMotor;
	private static TouchSensor Ztouch;
	private static TouchSensor Xtouch;
	private static double degree = 0.5;
	private static boolean escape = false;
	private static ReadExcel excelReader;

	public static void init() throws IOException {
		Ztouch = new TouchSensor(SensorPort.S3);
		Xtouch = new TouchSensor(SensorPort.S4);
		XMotor = Motor.B;
		YMotor = Motor.A;
		ZMotor = Motor.C;
		stock = null;
		excelReader = new ReadExcel();
		excelReader.setInputFile("code.xls");
		excelReader.read();
		stock = excelReader.getObjekts();
		System.out.println(stock.size());

	}

	public static void startPosition() {
		YMotor.setSpeed(80);
		YMotor.resetTachoCount();
		ZMotor.setSpeed(180);
		ZMotor.forward();
		while (!Ztouch.isPressed())
			;
		ZMotor.stop();
		ZMotor.resetTachoCount();
		ZMotor.rotateTo(-490);
		ZMotor.rotateTo(-420);
		XMotor.setSpeed(700);
		XMotor.backward();
		while (!Xtouch.isPressed())
			;
		XMotor.stop();
		XMotor.resetTachoCount();
		XMotor.rotateTo(3000);
		Delay.msDelay(2000);

	}

	public static void write2(String ausgabe) {
		ausgabe = ausgabe.toUpperCase();
		int backlash = 130;
		boolean upperPosition = true;
		boolean xpos = true;
		int xMove = 0;
		int yMove = 0;
		for (int stelle = 0; stelle < ausgabe.length() && !escape; stelle++) {
			for (int u = 0; u < stock.size() && !escape; u++) {
				if (stock.get(u).getName().charAt(0) == ausgabe.charAt(stelle)) {
					System.out.println(stock.get(u).getName());
					for (int i = 0; i < stock.get(u).getCode().size() && !escape; i++) {						
						if (stock.get(u).getCode().get(i)[4] == 1 && upperPosition) {
							down();
							upperPosition = false;
						} else if (stock.get(u).getCode().get(i)[4] == 0 && !upperPosition) {
							up();
							upperPosition = true;
						}
						if (upperPosition) {
							XMotor.setSpeed(700);
							YMotor.setSpeed(130);
						}
						else {
							XMotor.setSpeed(200);
							YMotor.setSpeed(80);
						}
						xMove = stock.get(u).getCode().get(i)[2] - stock.get(u).getCode().get(i)[0];
						yMove = stock.get(u).getCode().get(i)[3] - stock.get(u).getCode().get(i)[1];
						System.out.println("xMove: " + xMove);
						System.out.println("yMove: " + yMove);
						
						if(xMove < 0 && !xpos){
							xpos = true;
							XMotor.rotateTo(XMotor.getTachoCount() + backlash);
						}else if(xMove > 0 && xpos){
							xpos = false;
							XMotor.rotateTo(XMotor.getTachoCount() - backlash);
						}
						
						XMotor.rotateTo((int)(XMotor.getTachoCount() - xMove*degree), true);
						YMotor.rotateTo((int)(YMotor.getTachoCount() + yMove*degree), true);
						while(XMotor.isMoving() || YMotor.isMoving());
						Delay.msDelay(200);
						if (Button.ESCAPE.isDown()
								|| Xtouch.isPressed()
								|| XMotor.getTachoCount() < 400) {
							escape = true;
							break;
						}
					}
				}

			}
		}
	}

	public static void down() {
		ZMotor.rotateTo(-490);

	}

	public static void up() {
		ZMotor.rotateTo(-420);
	}	
	
	public static void main(String[] args) throws IOException {
		init();
		startPosition();
		GUI frame = new GUI();
		frame.setVisible(true);
	}
}
