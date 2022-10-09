package uet.oop.bomberman.entities.movingobject.pathfinder;

public class Node {
    Node parent;
    public int row;
    public int col;
    int g;
    int h;
    int f;
    boolean solid;
    boolean checked;
    boolean open;
    public Node(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
