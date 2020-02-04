package first;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Board {
	
	private int [][] board;
	private ArrayList<Coordinates> coords;
	private int flagsRemaining;
	private long startTime;
	private int squaresRemaining;
	private int maxX;
	private int maxY;
	public boolean gameOver;
	public int winningTime;
	
	public Board(String size) {
		coords = new ArrayList<Coordinates>();
		gameOver = false;
		switch(size) {
		
			case "easy" :
			
				board = new int [10][10];
				flagsRemaining = 10;
				squaresRemaining = 100;
				setupBoard("easy");
				break;
			
			case "expert" :
			
				board = new int [20][20];
				flagsRemaining = 75;
				squaresRemaining = 400;
				setupBoard("expert");
				break;
			
			default :
			
				board = new int [16][16];
				flagsRemaining = 40;
				squaresRemaining = 256;
				setupBoard("normal");
				break;
		}
	}
	
	
	
	
	public int[][] getBoard(){
		return board;
	}
	
	public int getFlagsRemaining() {
		return flagsRemaining;
	}
	
	public int getSquaresRemaining() {
		return squaresRemaining;
	}
	

	public void setupBoard(String size) {
		
		int bombsLeft;
		int x,y;
		Random rand = new Random();
		
		switch(size) {
		
		case "easy" :
			
			bombsLeft = 10;
			maxX = 9;
			maxY = 9;
			break;
			
		case "expert" :
			
			bombsLeft = 75;
			maxX = 19;
			maxY = 19;
			break;
			
		default : // "normal"
			
			bombsLeft = 40;
			maxX = 15;
			maxY = 15;
			break;
		}
		
		while(bombsLeft > 0) {				//Place Bombs
			x = rand.nextInt(maxX);
			y = rand.nextInt(maxY);
			if(board[x][y] < 10) {
				bombsLeft--;
				board[x][y] = 10;
				coords.add(new Coordinates(x,y));
				if(y != maxY) {
					board[x][y+1]++;
				}
				if(y != 0) {
					board[x][y-1]++;
				}
				if(x != 0) { 				//add 1 to surrounding squares (OPTIMIZE)
					board[x-1][y]++;
					if(y != 0) {
						board[x-1][y-1]++;
					}
					if(y != maxY) {
						board[x-1][y+1]++;
					}
				}
				if(x != maxX) {
					board[x+1][y]++;
					if(y != 0) {
						board[x+1][y-1]++;
					}
					if(y != maxY) {
						board[x+1][y+1]++;
					}
				}
			}
		}
		startTime = System.currentTimeMillis();
	}	// End of setupBoard
	
	
	
	
	
	public void printBoard() {
		
		for(int i = 0;i < board.length; i++) {
			System.out.print("| ");
			for(int j = 0; j < board[0].length;j++) {
				System.out.print(board[i][j] + "    ");
			}
			System.out.print("|\n");
		}
		
	}
	
	
	
	public int timeSurpassed() {
		
		long x = System.currentTimeMillis() - startTime;
		x = x/1000;
		return (int)x;
		
	}
	
	
	
	public boolean[] leftClickAction(Coordinates x, JPanel y) {	//returns false if clicked on a bomb
		
		boolean[] z = new boolean[2];											//boolean[0] tells if a bomb has clicked, boolean[1] if a flag is removed
		z[0] = true;
		z[1] = false;
		
		if((board[x.getX()][x.getY()] > 9) && (board[x.getX()][x.getY()] < 100)){
			z[0] = false;
			gameOver = true;
			return z;
		}
		else if(board[x.getX()][x.getY()] < 0) {								//if you left click on a flag
			board[x.getX()][x.getY()] = board[x.getX()][x.getY()] + 20;
			squaresRemaining++;
			flagsRemaining++;
			z[1] = true;
			return z;
		}
		else if(board[x.getX()][x.getY()] > 100) {							//if you click on a revealed square
	
		}
		else {
			board[x.getX()][x.getY()] = board[x.getX()][x.getY()] + 100;
			if(board[x.getX()][x.getY()] == 100) {
				revealSquares(x, y);
			}
			else {
				squaresRemaining--;
			}
			if(squaresRemaining < 1) {
				gameOver = true;
				winningTime = timeSurpassed();
			}
		}
		return z;
	}
	
	
	
	public boolean rightClickAction(Coordinates x) {	//if i can place a flag, returns true so gui can show it
		
		if(flagsRemaining == 0) {
			return false;
		}
		
		if(board[x.getX()][x.getY()] < 0) {
			return false;
		}
		if(board[x.getX()][x.getY()] > 99) {
			return false;
		}
		squaresRemaining--;
		flagsRemaining--;
		board[x.getX()][x.getY()] = board[x.getX()][x.getY()] -20;
		if(squaresRemaining < 1) {
			gameOver = true;
			winningTime = timeSurpassed();
		}
		return true;
		
}
	
	
	public void revealSquares(Coordinates x, JPanel y) {	
		
		squaresRemaining--;
		
		///////////////////////////////////////		Tell panel to draw revealed squares
		((JButton)y.getComponent((x.getX())*board.length + x.getY())).setBackground(new Color(160,160,160));
		((JButton)y.getComponent(x.getX()*board.length + x.getY())).setForeground(new Color(0,0,255));
//		if(board[x.getX()][x.getY()]%100 != 0) {
//			((JButton)y.getComponent(x.getX()*board.length + x.getY())).setText(Integer.toString(board[x.getX()][x.getY()]%100));
//		}
		/////////////////////////////////////////
		
		//recursively reveal all necessary squares
		
		if(x.getY() != maxY) {
			if((board[x.getX()][x.getY()+1] < 9) && (board[x.getX()][x.getY()+1] > -1)) {
				board[x.getX()][x.getY()+1] +=100;
				if(board[x.getX()][x.getY()+1]%100 == 0) {
					revealSquares(new Coordinates(x.getX(), x.getY() + 1), y);
				}
				else {
					squaresRemaining--;
					((JButton)y.getComponent((x.getX())*board.length + x.getY()+1)).setBackground(new Color(160,160,160));
					((JButton)y.getComponent((x.getX())*board.length + x.getY()+1)).setForeground(new Color(0,0,255));
					((JButton)y.getComponent((x.getX())*board.length + x.getY()+1)).setText(Integer.toString(board[x.getX()][x.getY()+1]%100));
				}
			}
		}
		if(x.getY() != 0) {
			
			if((board[x.getX()][x.getY()-1] < 9) && (board[x.getX()][x.getY()-1] > -1)) {
				board[x.getX()][x.getY()-1] +=100;
				if(board[x.getX()][x.getY()-1]%100 == 0) {
					revealSquares(new Coordinates(x.getX(), x.getY() - 1), y);
				}
				else {
					squaresRemaining--;
					((JButton)y.getComponent((x.getX())*board.length + x.getY()-1)).setBackground(new Color(160,160,160));
					((JButton)y.getComponent((x.getX())*board.length + x.getY()-1)).setForeground(new Color(0,0,255));
					((JButton)y.getComponent((x.getX())*board.length + x.getY()-1)).setText(Integer.toString(board[x.getX()][x.getY()-1]%100));
				}
			}
		}
		if(x.getX() != 0) { 				
			if((board[x.getX()-1][x.getY()] < 9) && (board[x.getX()-1][x.getY()] > -1)) {
				board[x.getX()-1][x.getY()] +=100;
				if(board[x.getX()-1][x.getY()]%100 == 0) {
					revealSquares(new Coordinates(x.getX()-1, x.getY()), y);
				}
				else {
					squaresRemaining--;
					((JButton)y.getComponent((x.getX()-1)*board.length + x.getY())).setBackground(new Color(160,160,160));
					((JButton)y.getComponent((x.getX()-1)*board.length + x.getY())).setForeground(new Color(0,0,255));
					((JButton)y.getComponent((x.getX()-1)*board.length + x.getY())).setText(Integer.toString(board[x.getX()-1][x.getY()]%100));
				}
			}
			if(x.getY() != 0) {
				if((board[x.getX()-1][x.getY()-1] < 9) && (board[x.getX()-1][x.getY()-1] > -1)) {
					board[x.getX()-1][x.getY()-1] +=100;
					if(board[x.getX()-1][x.getY()-1]%100 == 0) {
						revealSquares(new Coordinates(x.getX()-1, x.getY()-1), y);
					}
					else {
						squaresRemaining--;
						((JButton)y.getComponent((x.getX()-1)*board.length + x.getY()-1)).setBackground(new Color(160,160,160));
						((JButton)y.getComponent((x.getX()-1)*board.length + x.getY()-1)).setForeground(new Color(0,0,255));
						((JButton)y.getComponent((x.getX()-1)*board.length + x.getY()-1)).setText(Integer.toString(board[x.getX()-1][x.getY()-1]%100));
					}
				}
			}
			if(x.getY() != maxY) {
				if((board[x.getX()-1][x.getY()+1] < 9) && (board[x.getX()-1][x.getY()+1] > -1)) {
					board[x.getX()-1][x.getY()+1] +=100;
					if(board[x.getX()-1][x.getY()+1]%100 == 0) {
						revealSquares(new Coordinates(x.getX()-1, x.getY()+1), y);
					}
					else {
						squaresRemaining--;
						((JButton)y.getComponent((x.getX()-1)*board.length + x.getY()+1)).setBackground(new Color(160,160,160));
						((JButton)y.getComponent((x.getX()-1)*board.length + x.getY()+1)).setForeground(new Color(0,0,255));
						((JButton)y.getComponent((x.getX()-1)*board.length + x.getY()+1)).setText(Integer.toString(board[x.getX()-1][x.getY()+1]%100));
					}
				}
			}
		}
		if(x.getX() != maxX) {
			if((board[x.getX()+1][x.getY()] < 9) && (board[x.getX()+1][x.getY()] > -1)) {
				board[x.getX()+1][x.getY()] +=100;
				if(board[x.getX()+1][x.getY()]%100 == 0) {
					revealSquares(new Coordinates(x.getX()+1, x.getY()), y);
				}
				else {
					squaresRemaining--;
					((JButton)y.getComponent((x.getX()+1)*board.length + x.getY())).setBackground(new Color(160,160,160));
					((JButton)y.getComponent((x.getX()+1)*board.length + x.getY())).setForeground(new Color(0,0,255));
					((JButton)y.getComponent((x.getX()+1)*board.length + x.getY())).setText(Integer.toString(board[x.getX()+1][x.getY()]%100));
				}
			}
			if(x.getY() != 0) {
				if((board[x.getX()+1][x.getY()-1] < 9) && (board[x.getX()+1][x.getY()-1] > -1)) {
					board[x.getX()+1][x.getY()-1] +=100;
					if(board[x.getX()+1][x.getY()-1]%100 == 0) {
						revealSquares(new Coordinates(x.getX()+1, x.getY()-1), y);
					}
					else {
						squaresRemaining--;
						((JButton)y.getComponent((x.getX()+1)*board.length + x.getY()-1)).setBackground(new Color(160,160,160));
						((JButton)y.getComponent((x.getX()+1)*board.length + x.getY()-1)).setForeground(new Color(0,0,255));
						((JButton)y.getComponent((x.getX()+1)*board.length + x.getY()-1)).setText(Integer.toString(board[x.getX()+1][x.getY()-1]%100));
					}
				}
			}
			if(x.getY() != maxY) {
				if((board[x.getX()+1][x.getY()+1] < 9) && (board[x.getX()+1][x.getY()+1] > -1)) {
					board[x.getX()+1][x.getY()+1] +=100;
					if(board[x.getX()+1][x.getY()+1]%100 == 0) {
						revealSquares(new Coordinates(x.getX()+1, x.getY()+1), y);
					}
					else {
						squaresRemaining--;
						((JButton)y.getComponent((x.getX()+1)*board.length + x.getY()+1)).setBackground(new Color(160,160,160));
						((JButton)y.getComponent((x.getX()+1)*board.length + x.getY()+1)).setForeground(new Color(0,0,255));
						((JButton)y.getComponent((x.getX()+1)*board.length + x.getY()+1)).setText(Integer.toString(board[x.getX()+1][x.getY()+1]%100));
					}
				}
			}
		}
		
	}
	
//	public boolean gameOver() {
//		if(squaresRemaining < 1) {
//			return true;
//		}
//		return false;
//	}
	
//	public boolean gameWon() {									
//		
//		for(int i = 0; i < board.length; i++) {
//			for(int j = 0; j < board[i].length;j++) {
//				if((board[i][j] < 100) && (board[i][j] > -1)) {
//					return false;
//				}
//			}
//		}
//		
//		return true;
//	}
	
}
