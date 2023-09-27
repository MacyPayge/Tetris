/*
 * L Shaped Brick Class
 *     X      1
 *     X      2
 *     X X    3 4
 * Macy Busby
 * 2/28/22
 */

public class ElBrick extends TetrisBrick{
    
    public ElBrick(){
        colorNum = 2;
    }
    
    public void initPosition(int mid){
        position = new int[][] {{mid, 0},{mid,1},{mid,TWO},{mid+1,TWO}};
    }
    
    public void rotate(){
        
        switch(orientation){
            case 0:
                
                position[0][0] += 1;
                position[0][1] += 1;
                position[TWO][0] -= 1;
                position[TWO][1] -= 1;
                position[LAST_SEG][0] -= TWO;
                
                orientation++;
                break;
            case 1:
                position[0][0] -= 1;
                position[0][1] += 1;
                position[TWO][0] += 1;
                position[TWO][1] -= 1;
                position[LAST_SEG][1] -= TWO;
                
                orientation++;
                break;
            case 2:
                position[0][0] -= 1;
                position[0][1] -= 1;
                position[TWO][0] += 1;
                position[TWO][1] += 1;
                position[LAST_SEG][0] += TWO;
                
                orientation++;
                break;
            case 3:
                position[0][0] += 1;
                position[0][1] -= 1;
                position[TWO][0] -= 1;
                position[TWO][1] += 1;
                position[LAST_SEG][1] += TWO;
                
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
