import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by USER on 5/16/2017.
 */
public class BoardGui extends JPanel implements KeyListener{
    private BoardData board;
    private CellImage[][] cells;
    private int height;
    private int width;
    private int[] playerPos;
    private MainWindow mainWindow;
    public Timer timer;
    private int steps;

    public BoardGui(BoardData boardData, MainWindow mainWindow) {
		super(new GridLayout(boardData.getHeight(), boardData.getWidth()));
		steps=0;
		this.mainWindow=mainWindow;
		board = boardData;

    	 
        height = board.getHeight();
        width = board.getWidth();
        cells = new CellImage[height][width];
        
        //build the grid
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                CellImage cell = new CellImage(imageToCell(board.getCell(i, j)));
                cells[i][j] = cell;
                add(cell);
            }
        
        setFocusable(true);

        addKeyListener(this);
        playerPos = board.getPlayerPos();

        //timer of the player icon
        timer = new Timer(150,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cells[playerPos[1]][playerPos[0]].setIcon(CellImage.Player);
            }
        });
        timer.setRepeats(true);
        timer.start();
    }




        //key listener of the arrows
        @Override
    	public void keyPressed(KeyEvent e) {
    	   playerPos = board.getPlayerPos();
    	   Direction dir=null;
    		if (e.getKeyCode() == KeyEvent.VK_LEFT)
    			dir= Direction.Left;
    		
    		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
    			dir= Direction.Right;
    		
    		if (e.getKeyCode() == KeyEvent.VK_UP)
    			dir= Direction.Up;
    		
    		if (e.getKeyCode() == KeyEvent.VK_DOWN)
    			dir= Direction.Down;
    		if(dir!=null){
    			if(board.tryMoveMan(playerPos[0], playerPos[1], dir,false)){
    				paintBoard();
    				steps++;
    				MenuPanel.setSteps(steps);
    				if(board.isGameOver())
    					mainWindow.getMenu().setGameOver(true);
    			}
    		}
    		
    		
    	}
    	@Override
    	public void keyReleased(KeyEvent arg0) {	
    	}
    	@Override
    	public void keyTyped(KeyEvent arg0) {	
    	}
    
    

    //after any action perform this method in order to update the board
    public void paintBoard() {
    	height = board.getHeight();
        width = board.getWidth();
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                cells[i][j].setIcon(imageToCell(board.getCell(i, j)));
            }
    }

    //perform undo and update stepslabel
    public void undo() {
    	if(board.undo()){
    		paintBoard();
    		steps--;
    		MenuPanel.setSteps(steps);
    	}
    }

    //method that return the right string status of the cell
    public String imageToCell(Cell cell) {
        if (cell.hasPlayer())
            return CellImage.Player;
        if (cell.hasBox()){
        	if(cell.isStorage())
        		return CellImage.BoxStorage;
        	else
        		return CellImage.Box;
        }
        if (!cell.isFloor())
            return CellImage.Wall;
        if (cell.isStorage())
            return CellImage.Storage;

        return CellImage.Empty;
    }
}
