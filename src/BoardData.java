import java.util.Stack;

/**
 * Created by USER on 5/13/2017.
 */


public class BoardData {

    private Cell[][] _board;
    private Stack<Action> _undoActions;
    private LevelLoader _levelLoader;
    private int[] _playerPos;
    private int _boxLeft;
    private boolean _isGameOver;


    //initialize the model of the levels and the current board
    public BoardData() {
        _isGameOver=false;
        _boxLeft=0;
        _playerPos=new int[2];
        _levelLoader = new LevelLoader();
        try {
            _levelLoader.load("levels.txt");
            _undoActions = new Stack<Action>();
            setCurrentLvl(0);
        }
        catch (Exception e){

        }

    }
    //Cons with a specific level
    public BoardData(int lvl) {
        this();
        setCurrentLvl(lvl);
    }

    //set board to a specific level
    public void setCurrentLvl(int lvl) {

        _boxLeft=0;
        Cell[][] boardToCopy;
        //copy the board level from level loader
        if (_levelLoader.getLevelsCount() > lvl) {


            boardToCopy = _levelLoader.get(lvl);
            _board = new Cell[boardToCopy[0].length][boardToCopy.length];
            for (int i = 0; i < boardToCopy.length; i++)
                for (int j = 0; j < boardToCopy[i].length; j++) {
                    _board[j][i] = boardToCopy[i][j].clone();


                    //by the way update the position of player
                    if(_board[j][i].hasPlayer()) {
                        _playerPos[0] =i;
                        _playerPos[1] =j;
                    }
                    //and the  number of the left boxes that should be moved to a storage
                    if(_board[j][i].hasBox()&&!_board[j][i].isStorage()){
                        System.out.println("+");
                        _boxLeft++;
                    }
                }
                //init undo stack
            _undoActions = new Stack<Action>();
        }

    }

    //method that is called by keyListener or by the undo
    // moves the player if its possible and update the undo stack  if needed
    public boolean tryMoveMan(int x, int y, Direction dir, boolean isUndo) {
        if(_isGameOver)
            return false;

        int nextX = x;
        int nextY = y;

        switch (dir) {
            case Up:
                nextY -= 1;
                break;
            case Down:
                nextY += 1;
                break;
            case Left:
                nextX -= 1;
                break;
            case Right:
                nextX += 1;
                break;
        }
        Cell nextPos = _board[nextY][nextX];
        if (!nextPos.isFloor())
            return false;

        else {
            if (isUndo||(!nextPos.hasBox() || tryMoveBox(nextX, nextY, dir,false))) {
                _board[y][x].set_hasPlayer(false);
                nextPos.set_hasPlayer(true);
                if(!isUndo)
                     _undoActions.push(new Action(nextX, nextY, dir.getOpposite(), BoxMan.Man));
                _playerPos[0]=nextX;
                _playerPos[1]=nextY;
                if(_boxLeft==0)
                    _isGameOver=true;
                System.out.println(_boxLeft);
                return true;
                
            }
        }
        return false;
    }

    //method that is called by keyListner or by the undo
    // moves the BOX if its possible and update the undo stack if needed
    public boolean tryMoveBox(int x, int y, Direction dir, boolean isUndo) {
        int nextX = x;
        int nextY = y;

        switch (dir) {
            case Up:
                nextY -= 1;
                break;
            case Down:
                nextY += 1;
                break;
            case Left:
                nextX -= 1;
                break;
            case Right:
                nextX += 1;
                break;
        }
        Cell nextPos = _board[nextY][nextX];
        if (!nextPos.isFloor())
            return false;

        if (isUndo||!nextPos.hasBox()) {
            _board[y][x].set_hasBox(false);
            nextPos.set_hasBox(true);
            if(_board[y][x].isStorage())
                _boxLeft++;
            if(nextPos.isStorage())
                _boxLeft--;
            if(!isUndo)
            	_undoActions.push(new Action(nextX, nextY, dir.getOpposite(), BoxMan.Box));
            return true;
        }

        return false;
    }

    //method that is called in order to apply the undo action
    public boolean undo()
    {
    	if(_undoActions.empty()|_isGameOver)
            return false;
        Action act = _undoActions.pop();
        tryMoveMan(act.getX(),act.getY(),act.getDir(),true);
        
        if(!_undoActions.empty()) {
            act = _undoActions.peek();
            if(act.getBoxMan()== BoxMan.Box){
            	act = _undoActions.pop();
            	tryMoveBox(act.getX(),act.getY(),act.getDir(),true);
            }
        }
        return true;
    }

    public void set_isGameOver(boolean _isGameOver) {

        this._isGameOver = _isGameOver;
    }

    public boolean isGameOver() {
        return _isGameOver;
    }
    public int getCountLevels() {
        return _levelLoader.getLevelsCount();}
    public int getWidth(){
        return _board[0].length;}
    public int getHeight(){
        return _board.length;}
    public Cell getCell(int y, int x) {
             return _board[y][x];}

    public int[] getPlayerPos() {
        return _playerPos;
    }

    public String toString(){
        String str="";
        for(int i=0;i<_board.length;i++){
             for (int j = 0; j < _board[0].length; j++)
                     str += _board[i][j].toString();
            str+="\n";
        }
        return str;

    }

    public int getBoxLeft() {
        return _boxLeft;
    }
}
