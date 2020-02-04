package first;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GUI{		//flags on left, timer on right
	
	private JFrame frame;
	private JButton easy;
	private JButton normal;
	private JButton expert;
	private JMenuBar bar;
	private JMenu game;
	private JMenuItem newGame;	//takes you to initial page
	private JMenuItem highScores;
	private JPanel startPanel;
	private JPanel panel1;
	private JButton flags;
	private JButton restart;
	private JButton timer;
	private JPanel panel2;
	private JPanel highScorePanel;
	private JButton score1;
	private JButton score2;
	private JButton score3;
	private HighScores hS;
	private Board board;
	private int currentGame;
	
	public GUI() {
		hS = new HighScores();
		hS = HighScores.loadData();
		score1 = new JButton();
		score2 = new JButton();
		score3 = new JButton();
		score1.setFont(new Font("Arial", Font.PLAIN, 20));
		score2.setFont(new Font("Arial", Font.PLAIN, 20));
		score3.setFont(new Font("Arial", Font.PLAIN, 20));
		score1.setBorder(BorderFactory.createLineBorder(new Color(204,204,0), 4));
		score2.setBorder(BorderFactory.createLineBorder(new Color(192,192,192), 4));
		score3.setBorder(BorderFactory.createLineBorder(new Color(204,102,0), 4));
		highScorePanel = new JPanel();
		highScorePanel.setLayout(null);
		highScorePanel.add(score1);
		highScorePanel.add(score2);
		highScorePanel.add(score3);
		highScorePanel.setBackground(new Color(50,50,50));
		
		
		frame = new JFrame();
		frame.setBounds(0, 0, 400, 400);
		frame.setLayout(null);
		frame.setTitle("MineSweeper");
		frame.add(highScorePanel);
		highScorePanel.setBounds(0, 0, 400, 400);
		score1.setBounds(45, 30, 300, 75);
		score2.setBounds(45, 125, 300, 75);
		score3.setBounds(45, 220, 300, 75);
		highScorePanel.setVisible(false);								//High scores
		flags = new JButton();
		flags.setBackground(new Color(64,64,64));
		flags.setForeground(new Color(255,0,0));
		restart = new JButton("Restart");
		restart.setBackground(new Color(255,255,204));
		restart.addActionListener(new frontPageListener());
		timer = new JButton();
		timer.setBackground(new Color(64,64,64));
		timer.setForeground(new Color(255,0,0));
		panel1 = new JPanel();
		panel1.setLayout(new GridLayout(1,0));
		panel1.add(flags);
		panel1.add(restart);
		panel1.add(timer);
		panel1.setBackground(new Color(224,224,224));
		frame.add(panel1);
		panel1.setBounds(0, 0, 400, 60);
		panel1.setVisible(false);										//Panel1
		
		panel2 = new JPanel();
		panel2.setBackground(new Color(64,64,64));
		frame.add(panel2);
		panel2.setBounds(0, 60, 400, 340);
		panel2.setVisible(false);
		frame.setBackground(new Color(96,96,96));
		frame.getRootPane().setBorder(BorderFactory.createLineBorder( new Color(96,96,96)));
		startPanel = new JPanel();
		startPanel.setLayout(null);
		startPanel.setBackground(new Color(50,50,50));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0,0,400,400);
		frame.setVisible(true);
		frame.add(startPanel);
		startPanel.setBounds(0,0,400,400);
		startPanel.setVisible(true);									//start panel
		bar = new JMenuBar();
		game = new JMenu("Game");
		newGame = new JMenuItem("New Game");
		newGame.addActionListener(new menuListener());
		highScores = new JMenuItem("High Scores");
		highScores.addActionListener(new menuListener());
		game.add(newGame);
		game.add(highScores);
		bar.add(game);
		frame.setJMenuBar(bar);
		
		easy = new JButton("<html>Easy");
		easy.setBackground(new Color(255,204,204));
		easy.addActionListener(new frontPageListener());
		normal = new JButton("<html>Normal");
		normal.setBackground(new Color(255,153,153));
		normal.addActionListener(new frontPageListener());
		expert = new JButton("<html>Expert");
		expert.setBackground(new Color(255,102,102));
		expert.addActionListener(new frontPageListener());
		startPanel.add(easy);
		startPanel.add(normal);
		startPanel.add(expert);
		easy.setBounds(88, 30,200,75);
		easy.setBorder(BorderFactory.createRaisedBevelBorder());
		normal.setBounds(88,125,200,75);
		normal.setBorder(BorderFactory.createRaisedBevelBorder());
		expert.setBounds(88,220,200,75);
		expert.setBorder(BorderFactory.createRaisedBevelBorder());
		
		firstScreen();
		
	}
	
	public void firstScreen() {
		
		frame.setBounds((int)frame.getLocation().getX(), (int)frame.getLocation().getY(), 400, 400);
	}
	
	private class frontPageListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			JButton source = (JButton)(e.getSource());
			
			if(source.equals(easy)) {
				createGame(10,10);
			}
			else if(source.equals(normal)) {
				createGame(16,16);
			}
			else if(source.equals(restart)) {
				if(currentGame == 1) {
					createGame(10,10);
				}
				else if(currentGame ==2) {
					createGame(16,16);
				}
				else {
					createGame(20,20);
				}
			}
			else {
				createGame(20,20);
			}
			
		}
		
		public void createGame(int x, int y) {				//////////////////////
			highScorePanel.setVisible(false);
			startPanel.setVisible(false);
			panel1.setVisible(true);
			panel2.setVisible(true);
			if(x == 10) {
				board = new Board("easy");
				currentGame =1;
				frame.setBounds((int)frame.getLocation().getX(), (int)frame.getLocation().getY(), 300, 400);
			}
			else if(x == 16) {
				board = new Board("normal");
				currentGame =2;
				frame.setBounds((int)frame.getLocation().getX(), (int)frame.getLocation().getY(), 500, 500);
			}
			else {
				board = new Board("expert");
				currentGame = 3;
				frame.setBounds((int)frame.getLocation().getX(), (int)frame.getLocation().getY(), 750, 600);
			}
			((JButton)panel1.getComponent(0)).setText(Integer.toString(board.getFlagsRemaining()));
			panel2.removeAll();
			panel1.setBounds(0, 0, frame.getSize().width-15, 60);
			((JButton)panel1.getComponent(2)).setText("0");
			panel1.getComponent(0).setFont(new Font("Arial", Font.ITALIC + Font.BOLD, (frame.getSize().height)/20));
			panel1.getComponent(1).setFont(new Font("Arial", Font.ITALIC + Font.BOLD, (frame.getSize().height)/30));
			panel1.getComponent(2).setFont(new Font("Arial", Font.ITALIC + Font.BOLD, (frame.getSize().height)/20));
			panel2.setBounds(0, 60, (frame.getSize().width-15), frame.getSize().height-122);
			panel2.setLayout(new GridLayout(x,y));
			
			for(int i = 0; i < x; i++) {
				for(int j = 0; j < y; j++) {
					final int indexX = i;
					final int indexY = j;
					JButton a = new JButton();
					a.setMargin(new Insets(3, 3, 3, 3));
					a.addMouseListener(new MouseListener() {

						@Override
						public void mouseClicked(MouseEvent e) {
							boolean [] leftClickReturn;
							if((SwingUtilities.isRightMouseButton(e)) && (board.gameOver == false)) {									//right click	
								((JButton)panel1.getComponent(2)).setText(Integer.toString(board.timeSurpassed()));
								if(board.rightClickAction(new Coordinates(indexX, indexY))) {			//if valid flag spot
									a.setText("?");
									a.setForeground(new Color(255,0,0));
									((JButton)panel1.getComponent(0)).setText(Integer.toString(board.getFlagsRemaining()));
									
									if(board.gameOver == true) {
										String name = JOptionPane.showInputDialog("Victory! Enter Your Name:");
										if(hS.newScore(name, board.winningTime)) {
											JOptionPane.showMessageDialog(null, "New High Score!");
										}
									}
									
								}
							}
							else if(board.gameOver == false){											//left click
								((JButton)panel1.getComponent(2)).setText(Integer.toString(board.timeSurpassed()));
								leftClickReturn = board.leftClickAction(new Coordinates(indexX, indexY), panel2);
								board.getSquaresRemaining();
								if(leftClickReturn[0]) {												//didn't click on a bomb
									if(leftClickReturn[1]) {
										((JButton)panel1.getComponent(0)).setText(Integer.toString(board.getFlagsRemaining()));
										a.setText("");
									}
									else {
										a.setBackground(new Color(160,160,160));
										a.setForeground(new Color(0,0,255));
										if(board.getBoard()[indexX][indexY]%100 != 0) {
											a.setText(Integer.toString(board.getBoard()[indexX][indexY]%100));
										}
										if(board.gameOver == true) {
											if(currentGame == 2) {
												String name = JOptionPane.showInputDialog("Victory! Enter Your Name:");
												if(hS.newScore(name, board.winningTime)) {
												JOptionPane.showMessageDialog(null, "New High Score!");
											}
											}
											else {
												JOptionPane.showMessageDialog(null, "Victory!");
											}
										}
										
									}
								}
								else {																	//clicked on a bomb
									a.setForeground(new Color(0,0,0));
									a.setText("X");
								}
							}
							
						}

						@Override
						public void mousePressed(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mouseReleased(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mouseEntered(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mouseExited(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						
						
					});
					panel2.add(a);
				}
			}
		}
		
	}
	
	
	private class menuListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JMenuItem source = (JMenuItem)(e.getSource());
			
			if(source.equals(newGame)) {
				
				startPanel.setVisible(true);
				highScorePanel.setVisible(false);
				panel1.setVisible(false);
				panel2.setVisible(false);
				firstScreen();
			}
			else {									//high scores
				
				startPanel.setVisible(false);
				panel1.setVisible(false);
				panel2.setVisible(false);
				showHighScores();
				
			}
		}
		
		public void showHighScores() {
			
			frame.setBounds((int)frame.getLocation().getX(), (int)frame.getLocation().getY(), 400, 400);
			hS = HighScores.loadData();
			score1.setText(hS.getNames()[0] + " - " + hS.getTimes()[0]);
			score2.setText(hS.getNames()[1] + " - " + hS.getTimes()[1]);
			score3.setText(hS.getNames()[2] + " - " + hS.getTimes()[2]);
			highScorePanel.setVisible(true);
			
		}
		
	}

}
