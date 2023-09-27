/*
 * This is the window for user Interface and executable class
 * Macy Busby
 * 2/28/22
 */

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class TetrisWindow extends JFrame{
    
    private TetrisGame game;
    private TetrisDisplay display;
    
    private JMenuBar bar = new JMenuBar();
            
    private int win_width;
    private int win_height;
    private int game_rows = 20;
    private int game_cols = 12;
    
    public TetrisWindow(){
        game = new TetrisGame(game_rows, game_cols);
        display = new TetrisDisplay(game, this);
        
        this.add(display);
        initMenuBar();
        
        this.setTitle("My Tetris Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(win_width, win_height);
        
        this.setVisible(true);
    }
    
    public void initMenuBar(){
        this.setJMenuBar(bar);
        
        JMenu scores = new JMenu("Leaderboard");
        JMenu options = new JMenu("Options");
        
        JMenuItem leaderboard = new JMenuItem("Leaderboard");
        JMenuItem restartBoard = new JMenuItem("Restart Leaderboard");
        JMenuItem save = new JMenuItem("Save Game");
        JMenuItem load = new JMenuItem("Load Saved Game");
        JMenuItem newGame = new JMenuItem("New Game");
        
        leaderboard.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ac){
                initLeaderBoard();
            }
        });
        restartBoard.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ac){
                game.clearLeaderBoard();
                game.readLeaderBoard();
                JOptionPane.showMessageDialog(null, "Leaderboard Cleared.", "Clear Leaderboard", 1);
            }
        });
        save.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ac){
                initSaveScreen();
            }
        });
        load.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ac){
                initLoadScreen();
            }
        });
        newGame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ac){
                game.newGame();
                display.repaint();
            }
        });
        
        scores.add(leaderboard);
        scores.add(restartBoard);
        options.add(save);
        options.add(load);
        options.add(newGame);
        
        bar.add(scores);
        bar.add(options);
    }
    
    public void initEndScreen(){
        
        String message = "Game Over!\n";
        message += "Your score: "+game.score +"\n";
        if (game.score > game.leaderBoardMin){
            message += "Your score is in the top 10 scores!\n";
            message += "Please enter your name to\n";
            message += "add it to the leaderboard.";
        }
            
        String[] options = {"Leaderboard", "new Game", "Close"};
            
        if (game.score > game.leaderBoardMin){
            String name = JOptionPane.showInputDialog(null, message, "Game Over", 1);
            game.updateLeaderBoard(name, game.score);
            game.saveLeaderBoard();
            initLeaderBoard();
        }
        else{
            int option = JOptionPane.showOptionDialog(null,message, "Game Over", 1, 1, null, options, 0);
            if (option == 0)
                initLeaderBoard();
            else if(option == 1)
                game.newGame();
            else
                System.exit(0);
        }
    }
    
    public void initLeaderBoard(){
        String[][] board = game.board;
        String leaderboard = "";
        
        int nameLength = 0;
        
        for(int dex = 0; dex < board.length; dex ++){
            String nextName =(dex+1) + ": " + board[dex][0];
            
            if (nameLength < nextName.length())
                nameLength = nextName.length();
        }
        
        nameLength += 7;
        
        for(int dex = 0; dex < board.length; dex ++){
            String nextName =(dex+1) + ": " + board[dex][0];
            String nextScore = board[dex][1];
            
            String blank = "";
            if (!board[dex][0].equals(""))
                blank = ".".repeat(nameLength - nextName.length() - nextScore.length());
            
            leaderboard += "<html>" + nextName + blank + nextScore + "<br /><html>";
        }
        
        JLabel leaderBoardLabel = new JLabel(leaderboard);
        leaderBoardLabel.setFont(new Font("Monospaced", Font.BOLD, 15));
        JOptionPane.showMessageDialog(null, leaderBoardLabel, "Leaderboard", 1);
    }
    
    public void initSaveScreen(){
        String prompt = "Please enter a filename to save to.";
        String fileName = JOptionPane.showInputDialog(null, prompt, "Save Game", 1);
        File saveFile = new File(fileName);
        
        while(saveFile.exists()){
            prompt = fileName+" already exists.\nPlease enter a different filename to save to.";
            fileName = JOptionPane.showInputDialog(null, prompt, "Save Game", 1);
            saveFile = new File(fileName);
        }
            
        String outputMess = game.saveGame(fileName);
        JOptionPane.showMessageDialog(null, outputMess, "Save Game", 1);
    }
    
    
    public void initLoadScreen(){
        String prompt = "Please enter a filename to load.";
        String fileName = JOptionPane.showInputDialog(null, prompt, "Load Game", 1);
        File saveFile = new File(fileName);
        
        while(!saveFile.exists()){
            prompt = fileName+" does not exist.\nPlease enter a different filename to load.";
            fileName = JOptionPane.showInputDialog(null, prompt, "Load Game", 1);
            saveFile = new File(fileName);
        }
        game.loadGame(fileName);
        display.repaint();
    }
    
    public void setWin_width(int win_width) {
        this.win_width = win_width;
    }

    public void setWin_height(int win_height) {
        this.win_height = win_height;
    }
    
    public static void main(String[] args) {
        TetrisWindow win = new TetrisWindow();
    }
}
