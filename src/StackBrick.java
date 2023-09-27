/*
 * Stack  Brick Class
 *     X       1
 *   X X X  2  3  4
 * Macy Busby
 * 2/28/22
 */

public class StackBrick extends TetrisBrick{
    
    public StackBrick(){
        colorNum = 5;
    }
    
    public void initPosition(int mid){
        position = new int[][] {{mid+1, 0},{mid,1},{mid+1,1},{mid+TWO,1}};
    }
    
    public void rotate(){
        
        switch(orientation){
            case 0:
                position[0][0] += 1;
                position[0][1] += 1;
                position[1][0] += 1;
                position[1][1] -= 1;
                position[LAST_SEG][0] -= 1;
                position[LAST_SEG][1] += 1;
                
                orientation++;
                break;
            case 1:
                position[0][0] -= 1;
                position[0][1] += 1;
                position[1][0] += 1;
                position[1][1] += 1;
                position[LAST_SEG][0] -= 1;
                position[LAST_SEG][1] -= 1;
                
                orientation++;
                break;
            case 2:
                position[0][0] -= 1;
                position[0][1] -= 1;
                position[1][0] -= 1;
                position[1][1] += 1;
                position[LAST_SEG][0] += 1;
                position[LAST_SEG][1] -= 1;
                
                orientation++;
                break;
            case 3:
                position[0][0] += 1;
                position[0][1] -= 1;
                position[1][0] -= 1;
                position[1][1] -= 1;
                position[LAST_SEG][0] += 1;
                position[LAST_SEG][1] += 1;
                
                orientation = 0;
                break;
        }
    }
    
    public void unrotate(){
        rotate();
        rotate();
        rotate();
    }
}
