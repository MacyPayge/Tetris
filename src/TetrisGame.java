/*
 * Tetris Game class for Game Logic
 * Macy Busby
 * 2/28/22
 */

import java.util.*;
import java.io.*;
import javax.swing.JOptionPane;

public class TetrisGame {
    
    public int PAUSED = 0;
    public int RUNNING = 1;
    public int GAME_OVER = 2;
    
    public int leaderBoardMin;
    public String[][] board;
    
    public int score;
    public int[] pointIncr = {100, 300, 600, 1200};
    
    private int numBricks = 7;
    private int[][] background;
    private int state = RUNNING;
    
    private TetrisBrick fallingBrick;
    private Random randomGen = new Random();
    
    private String fileName = "TetrisLeaderBoard.txt";
    private File leaderBoard = new File(fileName);
    
    public TetrisGame(int rows, int cols){
        background = new int[rows][cols];
        spawnBrick(randomGen.nextInt(numBricks));
        board = readLeaderBoard();
    }
    
    public void newGame(){
        initBoard();
        spawnBrick(randomGen.nextInt(numBricks));
        score = 0;
        state = RUNNING;
    }
    
    public void togglePause(javax.swing.Timer timer){
        if (state == RUNNING){
            state = PAUSED;
            timer.stop();
        }
        else if (state == PAUSED){
            state = RUNNING;
            timer.restart();
        }
    }
    
    public void initBoard(){
        for (int row = 0; row < background.length; row++){
            for (int col = 0; col < background[0].length; col ++){
                background[row][col] = 0;
            }
        }
    }
    
    public void spawnBrick(int type){
        switch(type){
            case 0:
                fallingBrick = new ElBrick();
                break;
            case 1:
                fallingBrick = new JayBrick();
                break;
            case 2:
                fallingBrick = new EssBrick();
                break;
            case 3:
                fallingBrick = new ZeeBrick();
                break;
            case 4:
                fallingBrick = new StackBrick();
                break;
            case 5:
                fallingBrick = new SquareBrick();
                break;
            case 6:
                fallingBrick = new LongBrick();
                break;
        }
        fallingBrick.initPosition((background[0].length-1)/2);
    }
    
    public void makeMove(char direction){
        if (state != RUNNING)
            return;
        switch (direction){
            case 'D':
                fallingBrick.moveDown();
                if(!validateMove()){
                    fallingBrick.moveUp();
                    
                    transferColor();
                    removeFullRows();
                    
                    spawnBrick(randomGen.nextInt(numBricks));
                    if(!validateMove())
                        state = GAME_OVER;
                }
                break;
            case 'T':
                fallingBrick.rotate();
                if(!validateMove())
                    fallingBrick.unrotate();
                break;
            case 'R':
                fallingBrick.moveRight();
                if(!validateMove())
                    fallingBrick.moveLeft();
                break;
            case 'L':
                fallingBrick.moveLeft();
                if(!validateMove())
                    fallingBrick.moveRight();
                break;
        }
    }
    
    public Boolean validateMove(){
        int[][] brickPos = fallingBrick.position;
        for(int seg = 0; seg < fallingBrick.numSegments; seg++){
            //Right
            if(brickPos[seg][0] >= this.getCols())
                return false;
            //Left
            if(brickPos[seg][0] < 0)
                return false;
            //Down
            if(brickPos[seg][1] >= this.getRows())
                return false;
            //Up
            if (brickPos[seg][1] < 0 )
                return false;
            //Overlapping
            if(background[ brickPos[seg][1] ][ brickPos[seg][0]] != 0)
                return false;
        }
        return true;
    }

    public void transferColor(){
        int[][] brickPos = fallingBrick.getPosition();
        for (int seg = 0; seg < brickPos.length; seg++){
            int row = brickPos[seg][1];
            int col = brickPos[seg][0];
            background[row][col] = fallingBrick.colorNum;
        }
    }
    
    public void removeFullRows(){
        int rowsRemoved = 0;
        
        for (int row = 0; row < background.length; row ++){
            Boolean rowFull = true;
            for (int col = 0; col < background[0].length; col ++){
                if (background[row][col] == 0)
                    rowFull = false;
            }
            if (rowFull){
                for (int ro = row; ro > 0; ro--){
                    for (int co = 0; co < background[0].length; co++)
                        background[ro][co] = background[ro-1][co];
                }
                for (int co = 0; co < background[0].length; co++)
                    background[0][co] = 0;
                rowsRemoved++;
            }
        }
        
        if (rowsRemoved != 0)
            score += pointIncr[rowsRemoved-1];
    }
    
    public String[][] readLeaderBoard(){
        int boardSize = 10;
        String[][] board = new String[boardSize][2];
        
        try{
            Scanner inScan = new Scanner(leaderBoard).useDelimiter(",|\n");
            
            for (int counter = 0; counter < board.length; counter ++){
                if (inScan.hasNext()){
                    String newName = inScan.next();
                    String newScore = inScan.next();

                    board[counter][0] = newName;
                    board[counter][1] = newScore;
                }
                else{
                    board[counter][0] = "";
                    board[counter][1] = "";
                }
            }
            
        }catch(IOException ioe){
            JOptionPane.showMessageDialog(null, 
                    "There was an error reading the leaderboard.\n"
                            + "The program will start with an empty leaderboard.", "Error", 1);
            String[][] emptyBoard = {{"",""},{"",""},{"",""},{"",""},
                 {"",""},{"",""},{"",""},{"",""},{"",""},{"",""}};
            board = emptyBoard;
        }
                
        String curNum;
        String curName;
        String nextNum;
        String nextName;
        int counter = 0;
        
        while (counter < board.length - 1){
            
            curNum = board[counter][1].strip();
            curName = board[counter][0];
            nextNum = board[counter+1][1].strip();
            nextName = board[counter+1][0];
            
            if(!curNum.equals("") && !nextNum.equals("")){
                if(Integer.parseInt(curNum) < Integer.parseInt(nextNum)){
                    board[counter][0] = nextName;
                    board[counter][1] = nextNum;
                    board[counter+1][0] = curName;
                    board[counter+1][1] = curNum;
                    counter = 0;
                }
                else
                    counter ++;
            }
            else
                counter++;
        }
        
        return board;
    }
    
    public String[][] updateLeaderBoard(String name, int score){
        
        for(int dex = 0; dex < board.length; dex++){
            String curNum = board[dex][1].strip();
            String curName = board[dex][0];
            
            if(curNum.equals(""))
                curNum = "0";
            
            if (Integer.parseInt(curNum) < score){
                for(int index = board.length - 1; index > dex; index --){
                    board[index][0] = board[index - 1][0];
                    board[index][1] = board[index - 1][1];
                }
                
                board[dex][0] = name;
                board[dex][1] = score+"";
                return board;
            }
        }
        
        return board;
    }
    
    public void saveLeaderBoard(){
        String leaderboardMess = "";
        for(int dex = 0; dex < 10; dex ++){
            leaderboardMess += board[dex][0]+","+board[dex][1]+"\n";
        }
        
        try{
            FileWriter writer = new FileWriter(leaderBoard);
            writer.write(leaderboardMess);
            writer.close();
            
        }catch(IOException ioe){
            JOptionPane.showMessageDialog(null, 
                    "There was an error saving the leaderboard.\n", "Error", 1);
        }
    }
    
    public void clearLeaderBoard(){
        try{
            FileWriter writer = new FileWriter(leaderBoard);
            writer.write("");
            writer.close();
            
        }catch(IOException ioe){
            JOptionPane.showMessageDialog(null, 
                    "There was an error clearing the leaderboard.\n", "Error", 1);
        }
    }
    
    public String saveGame(String fileName){
        String outputMess;
        String saveMess = "";
        saveMess += score+"\n";
        
        for(int row = 0; row < background.length; row ++){
            for (int col = 0; col < background[0].length; col ++)
                saveMess += background[row][col]+",";
        }
        
        saveMess += fallingBrick.colorNum + "\n";
        
        for(int seg = 0; seg < fallingBrick.numSegments; seg++){
            saveMess += fallingBrick.position[seg][0] + ",";
            saveMess += fallingBrick.position[seg][1] + "\n";
        }
        
        File saveFile = new File(fileName);
        try{
            if (saveFile.createNewFile()){
                outputMess = "File saved as \""+fileName+"\".";
                
                FileWriter writer = new FileWriter(saveFile);
                writer.write(saveMess);
                writer.close();
            }
            else{
                outputMess = "This file already exists.";
            }
        }catch (IOException e){
            outputMess = "There was an error saving the file.";
        }
        
        return outputMess;
    }
    
    public void loadGame(String fileName){
        File saveFile = new File(fileName);
        
        try{
            Scanner inScan = new Scanner(saveFile).useDelimiter(",|\n");
            
            score = inScan.nextInt();
            
            for(int row = 0; row < background.length; row ++){
                for (int col = 0; col < background[0].length; col ++){
                    background[row][col] = inScan.nextInt();
                }
            }
            
            switch(inScan.nextInt()){
                case 2:
                    fallingBrick = new ElBrick();
                    break;
                case 3:
                    fallingBrick = new JayBrick();
                    break;
                case 4:
                    fallingBrick = new EssBrick();
                    break;
                case 5:
                    fallingBrick = new StackBrick();
                    break;
                case 6:
                    fallingBrick = new SquareBrick();
                    break;
                case 7:
                    fallingBrick = new LongBrick();
                    break;
                case 8:
                    fallingBrick = new ZeeBrick();
                    break;
            }
            fallingBrick.initPosition((background[0].length-1)/2);
           
            for(int seg = 0; seg < fallingBrick.numSegments; seg++){
                fallingBrick.position[seg][0] = inScan.nextInt();
                fallingBrick.position[seg][1] = inScan.nextInt();
            }
            
        }catch(IOException ioe){
            JOptionPane.showMessageDialog(null, 
                    "There was an error loading the game: "+fileName+".\n", "Error", 1);
        }
    }
    
    public int getSegRow(int segment){
        int[][] brickPos = fallingBrick.position;
        return brickPos[segment][1];
    }
    
    public int getSegCol(int segment){
        int[][] brickPos = fallingBrick.position;
        return brickPos[segment][0];
    }
    
    public int getBrickColor(){
        return fallingBrick.colorNum;
    }
    
    public int getBrickNumSegments(){
        return fallingBrick.numSegments;
    }
    
    public int getCols(){
        return background[0].length;
    }
    
    public int getRows(){
        return background.length;
    }
    
    public int getState(){
        return state;
    }
    
    public int FetchBoardPosition(int row, int col){
        return background[row][col];
    }
    
    public int getScore(){
        return score;
    }
}