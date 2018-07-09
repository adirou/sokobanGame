/**
 * Created by USER on 5/16/2017.
 */

import javax.swing.*;

public class MainWindow extends JFrame {
    private BoardData boardData;
    public BoardGui boardGuiCurr;
    private JSplitPane splitPane;
    private MenuPanel menu;

    //constructor of the game logic and visual component
   public MainWindow(){
       super("Sokoban");
       setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       setResizable(false);
       boardData=new BoardData();
       menu=new MenuPanel(boardData, this);


       splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true);
       splitPane.setOneTouchExpandable(false);
       splitPane.setDividerLocation(150);
       splitPane.setDividerSize(0);
       splitPane.add(menu);
       boardGuiCurr = new BoardGui(boardData, this);
       splitPane.add(boardGuiCurr);
       getContentPane().add(splitPane);

       setVisible(true);
       pack();
   }
   
   
   public static void main(String[] args){
        MainWindow main=new MainWindow();
        
   }
   //initialize components .in any case of restart or loading new game.
   public void newGame(){
	   BoardGui boardGui = new BoardGui(boardData,this);
	   splitPane.remove(2);
	   splitPane.add(boardGui);
	   boardGuiCurr.timer.stop();
	   this.boardGuiCurr=boardGui;
	   boardGui.requestFocus();
	   pack();
   }
   public MenuPanel getMenu(){
	   return menu;
   }
 


}
