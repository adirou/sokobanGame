/**
 * Created by USER on 5/13/2017.
 */
public enum Direction {
    Up,Down,Left,Right;
    public Direction getOpposite(){
        switch (this) {
            case Up : return Down;
            case Down: return Up;
            case Left : return Right;
            case Right : return Left;
        }
        return null;
    }
}
