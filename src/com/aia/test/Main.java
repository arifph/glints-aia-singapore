package com.aia.test;

import java.util.Objects;
import java.util.Scanner;

public class Main {
	private static String command = null;

	public static void main(String[] args) {
		boolean isCanvasCreated = false;
		String[][] canvas = null;
		int height = 0;
		int width = 0;
		try {
			executeCommand(isCanvasCreated, canvas, height, width);
		} catch (Exception e) {
			System.out.print("An error occured: ");
			e.printStackTrace();
		}
	}

	public static void executeCommand(boolean isCanvasCreated, String[][] canvas, int height, int width) {
		Scanner cmdScanner = new Scanner(System.in);
		System.out.print("Enter command: ");
		command = cmdScanner.nextLine().trim();
		System.out.println();
		String[] arrayOfCommand = command.split(" "); // Split the command to array

		// Execute the command
		if (Objects.equals(arrayOfCommand[0], "Q")) {
			cmdScanner.close(); // Close scanner to prevent leaked resource
			System.exit(0);
		} else if (Objects.equals(arrayOfCommand[0], "C")) {
			isCanvasCreated = true;

			// Define canvas width and height
			width = Integer.parseInt(arrayOfCommand[1]) + 2;
			height = Integer.parseInt(arrayOfCommand[2]) + 2;

			// Create a canvas
			canvas = new String[height][width];
			canvas = sketchCanvas(canvas, height, width);
			draw(canvas, height, width);
		} else {
			if (isCanvasCreated == false) {
				System.out.println("To create a canvas, you need to write C w h command.");
				System.out.println("It will create a new canvas");
				System.out.println("Please create a canvas first!\n");
			} else {
				if (Objects.equals(arrayOfCommand[0], "L")) {
					// Check whether it is horizontal or vertical line
					if (Objects.equals(arrayOfCommand[1], arrayOfCommand[3])
							|| Objects.equals(arrayOfCommand[2], arrayOfCommand[4])) {
						// Initialize x1, y1, x2, y2
						int x1 = Integer.parseInt(arrayOfCommand[1]);
						int y1 = Integer.parseInt(arrayOfCommand[2]);
						int x2 = Integer.parseInt(arrayOfCommand[3]);
						int y2 = Integer.parseInt(arrayOfCommand[4]);
						
						// Draw Line
						if (isInsideCanvas(width, height, x1, y1) && isInsideCanvas(width, height, x2, y2)) {
							// Decide the start point and the end point
							if (x1 <= x2 && y1 <= y2) {
								canvas = sketchLine(canvas, x1, y1, x2, y2);
							} else if (x1 <= x2 && y1 > y2) {
								canvas = sketchLine(canvas, x1, y2, x2, y1);
							} else if (x1 > x2 && y1 <= y2) {
								canvas = sketchLine(canvas, x2, y1, x1, y2);
							} else if (x1 > x2 && y1 > y2) {
								canvas = sketchLine(canvas, x2, y2, x1, y1);
							}
							draw(canvas, height, width);
						} else {
							System.out.println("A point's coordinate is outside the canvas. Please check it again!\n");
						}
					} else {
						System.out.println("Only horizontal or vertical lines are supported.\n");
					}
				} else if (Objects.equals(arrayOfCommand[0], "R")) {
					// Initialize x1, y1, x2, y2
					int x1 = Integer.parseInt(arrayOfCommand[1]);
					int y1 = Integer.parseInt(arrayOfCommand[2]);
					int x2 = Integer.parseInt(arrayOfCommand[3]);
					int y2 = Integer.parseInt(arrayOfCommand[4]);
					
					if (isInsideCanvas(width, height, x1, y1) && isInsideCanvas(width, height, x2, y2)) {
						// Decide the start point and the end point
						if (x1 <= x2 && y1 <= y2) {
							canvas = sketchRectangle(canvas, x1, y1, x2, y2);
						} else if (x1 <= x2 && y1 > y2) {
							canvas = sketchRectangle(canvas, x1, y2, x2, y1);
						} else if (x1 > x2 && y1 <= y2) {
							canvas = sketchRectangle(canvas, x2, y1, x1, y2);
						} else if (x1 > x2 && y1 > y2) {
							canvas = sketchRectangle(canvas, x2, y2, x1, y1);
						}
						draw(canvas, height, width);
					} else {
						System.out.println("A point's coordinate is outside the canvas. Please check it again!\n");
					}
				} else if (Objects.equals(arrayOfCommand[0], "B")) {
					int x = Integer.parseInt(arrayOfCommand[1]);
					int y = Integer.parseInt(arrayOfCommand[2]);
					String c = arrayOfCommand[3];
					if (isInsideCanvas(width, height, x, y)) {
						canvas = fillRectangle(canvas, height, width, x, y, c);
						draw(canvas, height, width);
					} else {
						System.out.println("A point's coordinate is outside the canvas. Please check it again!\n");
					}
				} else {
					System.out.println("Unknown command.\n");
				}
			}
		}
		executeCommand(isCanvasCreated, canvas, height, width);
	}
	
	// A method to draw the canvas and everything in it
	public static void draw(String[][] canvas, int height, int width) {
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				System.out.print(canvas[h][w]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static String[][] sketchCanvas(String[][] canvas, int height, int width) {
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
		return canvas;
	}
	
	public static boolean isInsideCanvas(int width, int height, int x, int y) {
		if (x >= width - 1 || y >= height - 1) {
			return false;
		} else {
			return true;
		}
	}
	
	public static String[][] sketchLine(String[][] canvas, int xStart, int yStart, int xEnd, int yEnd) {
		for (int h = yStart; h <= yEnd; h++) {
			for (int w = xStart; w <= xEnd; w++) {
				canvas[h][w] = "x";
			}
		}
		return canvas;
	}
	
	public static String[][] sketchRectangle(String[][] canvas, int xStart, int yStart, int xEnd, int yEnd) {
		for (int h = yStart; h <= yEnd; h++) {
			for (int w = xStart; w <= xEnd; w++) {
				if (w == xStart || w == xEnd || h == yStart || h == yEnd) {
					canvas[h][w] = "x";
				}
			}
		}
		return canvas;
	}
	
	// A method to fill a rectangle
	// P.S: I haven't finished the bucket fill method yet because I don't know how
	// so I can only fill a rectangle
	public static String[][] fillRectangle(String[][] canvas, int height, int width, int x, int y, String colour) {
		if (!Objects.equals(canvas[y][x], "x")) {
			fillToBottomRight:
			for (int h = y; h < height - 1; h++) {
				for (int w = x; w < width - 1; w++) {
					if (Objects.equals(canvas[h][w], "x")) {
						if (Objects.equals(canvas[h-1][w], "x") && Objects.equals(canvas[h][w-1], "x")) {
							break fillToBottomRight;
						} else if (Objects.equals(canvas[h][w-1], "x")) {
							continue;
						} else if (Objects.equals(canvas[h-1][w], "x")) {
							break;
						}
					} else {
						canvas[h][w] = colour;
					}
				}
			}

			fillToTopRight:
			for (int h = y; h > 0; h--) {
				for (int w = x; w < width - 1; w++) {
					if (Objects.equals(canvas[h][w], "x")) {
						if (Objects.equals(canvas[h+1][w], "x") && Objects.equals(canvas[h][w-1], "x")) {
							break fillToTopRight;
						} else if (Objects.equals(canvas[h][w-1], "x")) {
							continue;
						} else if (Objects.equals(canvas[h+1][w], "x")) {
							break;
						}
					} else {
						canvas[h][w] = colour;
					}
				}
			}
			
			fillToBottomLeft:
			for (int h = y; h < height - 1; h++) {
				for (int w = x; w > 0; w--) {
					if(Objects.equals(canvas[h][w], "x")) {
						if (Objects.equals(canvas[h-1][w], "x") && Objects.equals(canvas[h][w+1], "x")) {
							break fillToBottomLeft;
						} else if (Objects.equals(canvas[h][w+1], "x")) {
							continue;
						} else if (Objects.equals(canvas[h-1][w], "x")) {
							break;
						}
					} else {
						canvas[h][w] = colour;
					}
				}
			}
			
			fillToTopLeft:
			for (int h = y; h > 0; h--) {
				for (int w = x; w > 0; w--) {
					if (Objects.equals(canvas[h][w], "x")) {
						if (Objects.equals(canvas[h+1][w], "x") && Objects.equals(canvas[h][w+1], "x")) {
							break fillToTopLeft;
						} else if (Objects.equals(canvas[h][w+1], "x")) {
							continue;
						} else if (Objects.equals(canvas[h+1][w], "x")) {
							break;
						}
					} else {
						canvas[h][w] = colour;
					}
				}
			}
		}
		return canvas;
	}

}
