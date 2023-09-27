/*
 * Long Brick Class
 *     X X X X   1 2 3 4
 * Macy Busby
 * 2/28/22
 */
public class LongBrick extends TetrisBrick{
    
    public LongBrick(){
        colorNum = 7;
    }
    
    public void initPosition(int mid){
        position = new int[][] {{mid-1, 0},{mid,0},{mid+1,0},{mid+TWO,0}};
    }
    
    public void rotate(){
        
        switch(orientation){
            case 0:
                position[0][0] += TWO;
                position[0][1] -= 1;
                position[1][0] += 1;
                position[TWO][1] += 1;
                position[LAST_SEG][0] -= 1;
                position[LAST_SEG][1] += TWO;
                
                orientation++;
                break;
            case 1:
                position[0][0] += 1;
                position[0][1] += TWO;
                position[1][1] += 1;
                position[TWO][0] -= 1;
                position[LAST_SEG][0] -= TWO;
                position[LAST_SEG][1] -= 1;
                
                orientation++;
                break;
            case 2:
                position[0][0] -= TWO;
                position[0][1] += 1;
                position[1][0] -= 1;
                position[TWO][1] -= 1;
                position[LAST_SEG][0] += 1;
                position[LAST_SEG][1] -= TWO;
                
                orientation++;
                break;
            case 3:
                position[0][0] -= 1;
                position[0][1] -= TWO;
                position[1][1] -= 1;
                position[TWO][0] += 1;
                position[LAST_SEG][0] += TWO;
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
