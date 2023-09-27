/*
 * Square Shaped Brick Class
 *     X X   1 2
 *     X X   3 4
 * Macy Busby
 * 2/28/22
 */

public class SquareBrick extends TetrisBrick{
    
    public SquareBrick(){
        colorNum = 6;
    }
    
    public void initPosition(int mid){
        position = new int[][] {{mid, 0},{mid+1,0},{mid,1},{mid+1,1}};
    }
    
    public void rotate(){
    }
    
    public void unrotate(){
    }
}
