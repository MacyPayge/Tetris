/*
 * This is the display class (paint)
 * Macy Busby
 * 2/28/22
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TetrisDisplay extends JPanel{
    
    private int start_x = 30;
    private int start_y = 40;
    private int cell_size = 20;
    private int speed = 300;
    
    private int scoreHeight = 30;
    
    private TetrisGame game;
    private TetrisWindow window;
    
    private Timer timer;
    
    private Color[] colors = {Color.white, Color.black,
        new Color(204,153,201), new Color(158,193,207), new Color(158,224,158),
        new Color(253,253,151), new Color(254,177,68), new Color(255,102,99), 
        new Color(97,94,134)};
    
    public TetrisDisplay(TetrisGame gam, TetrisWindow win){
        game = gam;
        window = win;
        
        int rows = game.getRows();
        int cols = game.getCols();
        int winWidth = (cols+2)*cell_size+start_x*3;
        int winHeight = (rows+1)*cell_size+start_y*3+scoreHeight;
        
        win.setWin_width(winWidth);
        win.setWin_height(winHeight);
        
        timer = new Timer(speed, new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                cycleMove();
            } 
        });
        timer.start();
        
        this.addKeyListener( new KeyAdapter(){
            public void keyPressed(KeyEvent ke){
                translateKey(ke);
            }
        });
        
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
    }
    
    public void cycleMove(){
        if (game.getState() == game.GAME_OVER){
            timer.stop();
            window.initEndScreen();
        }
        else
            game.makeMove('D');
        repaint();
    }
    
    public void translateKey(KeyEvent ke){
        int code = ke.getKeyCode();
        char movement = ' ';
        
        final int KEY_LEFT = 37;
        final int KEY_RIGHT = 39;
        final int KEY_DOWN = 40;
        final int KEY_A = 65;
        final int KEY_D = 68;
        final int KEY_S = 83;
        
        final int KEY_R = 82;
        final int KEY_UP = 38;
        
        final int KEY_SPACE = 32;
        final int KEY_P = 80;
        final int KEY_N = 78;
        
            switch(code){
                case KEY_UP:
                case KEY_R:
                    movement = 'T';
                    break;
                case KEY_RIGHT:
                case KEY_D:
                    movement = 'R';
                    break;
                case KEY_LEFT:
                case KEY_A:
                    movement = 'L';
                    break;
                case KEY_DOWN:
                case KEY_S:
                    movement = 'D';
                    break;
                case KEY_SPACE:
                case KEY_P:
                    game.togglePause(timer);
                    break;
                case KEY_N:
                    game.newGame();
                    timer.start();
                    break;
            }
        game.makeMove(movement);
        repaint();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawScore(g);
        drawWell(g);
        drawBackground(g);
        
        if (game.getState() != game.GAME_OVER)
            drawBrick(g);
    }
    
    public void drawScore(Graphics g){
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, scoreHeight));
        g.drawString("Score: "+game.score, start_x, start_y);
    }
    
    public void drawWell(Graphics g){
        int rows = game.getRows();
        int cols = game.getCols();
        
        g.setColor(colors[1]);
        g.fillRect(start_x, start_y+scoreHeight,
                cell_size, cell_size*rows);
        g.fillRect(start_x+cell_size*(cols+1), start_y+scoreHeight,
                cell_size, cell_size*rows);
        g.fillRect(start_x, start_y+scoreHeight+cell_size*rows,
                cell_size*(cols+2), cell_size);
    }
    
    public void drawBackground(Graphics g){
        int back_y = start_y+scoreHeight;
        
        for (int row = 0; row < game.getRows(); row++){
            int back_x = start_x + cell_size;
            for (int col = 0; col < game.getCols(); col++){
                
                g.setColor(colors[ game.FetchBoardPosition(row, col) ]);
                g.fillRect(back_x, back_y, cell_size, cell_size);
                
                if (g.getColor() != colors[0]){
                    g.setColor(colors[1]);
                    g.drawRect(back_x, back_y, cell_size, cell_size);
                }
                back_x += cell_size;
                
            }
            back_y += cell_size;
        }
    }
    
    public void drawBrick(Graphics g){
        for (int segment = 0; segment < game.getBrickNumSegments(); segment++){
            
            int row = game.getSegRow(segment);
            int col = game.getSegCol(segment);
            
            int segment_x = start_x + cell_size*(col + 1);
            int segment_y = start_y + scoreHeight + cell_size*row;

            g.setColor(colors[game.getBrickColor()]);
            g.fillRect(segment_x, segment_y, cell_size, cell_size);

            g.setColor(colors[1]);
            g.drawRect(segment_x, segment_y, cell_size, cell_size);
        }
    }
}
