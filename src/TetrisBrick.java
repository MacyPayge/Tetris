/*
 * Abstract Tetris brick class
 * Macy Busby
 * 2/28/22
 */

public abstract class TetrisBrick {
    
    protected int TWO = 2;
    protected int LAST_SEG = 3;
    
    protected int orientation = 0;
    protected int numSegments = 4;
    protected int colorNum;
    
    protected int[][] position;
    
    public void moveDown(){
        for (int dex = 0; dex < position.length; dex ++){
            position[dex][1] += 1;
        }
    }
    
    public void moveUp(){
        for (int dex = 0; dex < position.length; dex ++){
            position[dex][1] -= 1;
        }
    }
    
    public void moveRight(){
        for (int dex = 0; dex < position.length; dex ++){
            position[dex][0] += 1;
        }
    }
    
    public void moveLeft(){
        for (int dex = 0; dex < position.length; dex ++){
            position[dex][0] -= 1;
        }
    }
    
    public void remove(){
        for (int dex = 0; dex < position.length; dex ++){
            position[dex][0] = -1;
            position[dex][1] = -1;
        }
    }
    
    public abstract void rotate();
    public abstract void unrotate();
    public abstract void initPosition(int mid);
    
    public int[][] getPosition(){
        return position;
    }
}
