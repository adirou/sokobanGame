import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by USER on 5/16/2017.
 */
public class MenuPanel extends JPanel implements ActionListener {
    private static JLabel steps;
    private JButton restart;
    private JButton undo;
    private JComboBox<Integer> levels;
    private  JLabel currentLevel;
    private BoardData boardData;
    private MainWindow mainWindow;
    private  JLabel gameOver;

    //build all the properties of the menu with their actions
    public MenuPanel(BoardData boardData, MainWindow mainWindow){
         super();
         setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));

         this.mainWindow = mainWindow;
         this.boardData=boardData;

         setPreferredSize(new Dimension(150,200));


         currentLevel= new JLabel("Level: "+1);
         currentLevel.setAlignmentX(CENTER_ALIGNMENT);
         currentLevel.setFont(new Font("Georgia", Font.BOLD ,14));

         steps = new JLabel("Steps: "+0);
         steps.setAlignmentX(CENTER_ALIGNMENT);
         steps.setFont(new Font("Georgia", Font.BOLD ,14));


         Integer[] lvls = new Integer[boardData.getCountLevels()];
         for(int i=0; i<lvls.length; i++)
        	 lvls[i]=i+1;

         levels= new JComboBox<Integer>(lvls);
         levels.setSelectedIndex(0);
         levels.addActionListener(this);
         levels.setMaximumSize(new Dimension(150, 25));
         levels.setMinimumSize(new Dimension(150, 25));
         levels.setAlignmentX(CENTER_ALIGNMENT);

         restart=new JButton("Restart");
         restart.setMinimumSize(new Dimension(150,40));
         restart.setMaximumSize(new Dimension(150,40));
         restart.setAlignmentX(CENTER_ALIGNMENT);
         restart.addActionListener(this);


         undo=new JButton("Undo");
         undo.setMinimumSize(new Dimension(150,40));
         undo.setMaximumSize(new Dimension(150,40));
         undo.setAlignmentX(CENTER_ALIGNMENT);
         undo.addActionListener(this);


        gameOver = new JLabel("Game over!");
         gameOver.setFont(new Font("Georgia", Font.BOLD, 14));
         gameOver.setPreferredSize(new Dimension(150,200));
         gameOver.setAlignmentX(CENTER_ALIGNMENT);

        add(Box.createRigidArea(new Dimension(5,20)));

         add(currentLevel);
        add(Box.createRigidArea(new Dimension(5,10)));
         add(steps);
        add(Box.createRigidArea(new Dimension(5,10)));
         add(levels);
        add(Box.createRigidArea(new Dimension(5,10)));
         add(restart);
         add(Box.createRigidArea(new Dimension(5,10)));
         add(undo);
        add(Box.createRigidArea(new Dimension(5,10)));
         add(gameOver);


         gameOver.setVisible(false);
         levels.setFocusable(false);
         restart.setFocusable(false);
         undo.setFocusable(false);
    }
    public static void setSteps(int step){

        steps.setText("Steps: "+step);
    }
    //take care of all the press actions (restart,undo, level choosing)
    public void actionPerformed(ActionEvent e) {
    	if(e.getSource()==levels){
    		 int level = (Integer)levels.getSelectedItem();
        boardData.setCurrentLvl(level-1);
        mainWindow.newGame();
        setGameOver(false);
        boardData.set_isGameOver(false);
        currentLevel.setText("Level: "+level);
        steps.setText("Step: "+0);
    	}

        if(e.getSource()==restart)
        	restart();
        if(e.getSource()==undo)
        	undo();
    }
    public void restart(){
    	int level = (Integer)levels.getSelectedItem();
        boardData.setCurrentLvl(level-1);
        mainWindow.newGame();
        steps.setText("Step: "+0);
        setGameOver(false);
        boardData.set_isGameOver(false);
    }
    public void undo(){
    	mainWindow.boardGuiCurr.undo();
    }
    public void setGameOver(boolean isGameOver){

        gameOver.setVisible(isGameOver);
    }  
    
    
}
