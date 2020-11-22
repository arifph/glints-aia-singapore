package com.aia.test;

import java.util.Objects;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		String command = null;
		boolean isCanvasCreated = false;
		String[][] canvas = null;
		int height = 0;
		int width = 0;
		try {
			executeCommand(command, isCanvasCreated, canvas, height, width);
		} catch (Exception e) {
			System.out.print("An error occured: ");
			e.printStackTrace();
		}
	}

	public static void executeCommand(String command, boolean isCanvasCreated, String[][] canvas, int height, int width) {
		Scanner cmdScanner = new Scanner(System.in);
		System.out.print("Enter command: ");
		command = cmdScanner.nextLine().trim();
		System.out.println();
		String[] arrayOfCommand = command.split(" "); // Split the command to array

		// Draw something
		if (Objects.equals(arrayOfCommand[0], "Q")) {
			System.exit(0);
		} else if (Objects.equals(arrayOfCommand[0], "C")) {
			isCanvasCreated = true;

			// Define canvas width and height
			width = Integer.parseInt(arrayOfCommand[1]) + 2;
			height = Integer.parseInt(arrayOfCommand[2]) + 2;

			// Create canvas
			canvas = new String[height][width];
			for (int h = 0; h < height; h++) {
				for (int w = 0; w < width; w++) {
					if (h == 0 || h == height - 1) {
						canvas[h][w] = "-";
					} else if (w == 0 || w == width - 1) {
						canvas[h][w] = "|";
					} else {
						canvas[h][w] = " ";
					}
				}
			}
			draw(canvas, height, width);
		} else {
			if (isCanvasCreated == false) {
				System.out.println("To create a canvas, you need to write C w h command.");
				System.out.println("It will create a new canvas");
				System.out.println("Please create a canvas first!\n");
			} else {
				if (Objects.equals(arrayOfCommand[0], "L")) {
					if (Objects.equals(arrayOfCommand[1], arrayOfCommand[3])
							|| Objects.equals(arrayOfCommand[2], arrayOfCommand[4])) {
						int x1 = Integer.parseInt(arrayOfCommand[1]);
						int y1 = Integer.parseInt(arrayOfCommand[2]);
						int x2 = Integer.parseInt(arrayOfCommand[3]);
						int y2 = Integer.parseInt(arrayOfCommand[4]);
						
						for (int h = y1; h <= y2; h++) {
							for (int w = x1; w <= x2; w++) {
								canvas[h][w] = "x";
							}
						}
						draw(canvas, height, width);
					} else {
						System.out.println("Only horizontal or vertical lines are supported.\n");
					}
				} else if (Objects.equals(arrayOfCommand[0], "R")) {
					int x1 = Integer.parseInt(arrayOfCommand[1]);
					int y1 = Integer.parseInt(arrayOfCommand[2]);
					int x2 = Integer.parseInt(arrayOfCommand[3]);
					int y2 = Integer.parseInt(arrayOfCommand[4]);
					
					for (int h = y1; h <= y2; h++) {
						for (int w = x1; w <= x2; w++) {
							if (w == x1 || w == x2 || h == y1 || h == y2) {
								canvas[h][w] = "x";
							}
						}
					}
					draw(canvas, height, width);
				} else if (Objects.equals(arrayOfCommand[0], "B")) {
					
				} else {
					System.out.println("Unknown command.\n");
				}
			}
		}
		executeCommand(null, isCanvasCreated, canvas, height, width);
		cmdScanner.close();
	}
	
	public static void draw(String[][] canvas, int height, int width) {
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				System.out.print(canvas[h][w]);
			}
			System.out.println();
		}
		System.out.println();
	}

}
