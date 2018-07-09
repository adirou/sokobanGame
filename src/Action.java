
/**
 * Created by USER on 5/13/2017.
 */

//represent an action of movement of box or player
public class Action {
    private int _x;
    private int _y;
    private Direction _dir;
    private BoxMan _boxMan;

    public Action(int x, int y, Direction oppDir, BoxMan boxMan) {
        _x = x;
        _y = y;
        _dir = oppDir;
        _boxMan = boxMan;
    }

    public int getX() {
        return _x;
    }

    public void setX(int x) {
        _x = x;
    }

    public int getY() {
        return _y;
    }

    public void setY(int y) {
        _y = y;
    }

    public Direction getDir() {
        return _dir;
    }

    public void setDir(Direction dir) {
        _dir = dir;
    }

    public BoxMan getBoxMan() {
        return _boxMan;
    }

    public void setBoxMan(BoxMan boxMan) {
        _boxMan = boxMan;
    }
}
