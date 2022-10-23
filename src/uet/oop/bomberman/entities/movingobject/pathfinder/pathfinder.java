package uet.oop.bomberman.entities.movingobject.pathfinder;

import uet.oop.bomberman.BombermanGame;

import java.util.ArrayList;
import java.util.List;

public class pathfinder {
    int[][] Map;
    Node[][] nodes;
    List<Node> openlist = new ArrayList<>();
    public List<Node> closedlist = new ArrayList<>() ;
    Node Start,End,Current;
    public int step = 0;
    public int count = 0;
    boolean finished = false;

    public pathfinder(int[][] Map) {
        this.Map = Map;
        initialize();
    }
    public void initialize() {
        nodes = new Node[BombermanGame.HEIGHT][BombermanGame.WIDTH];
        for(int i = 0; i < BombermanGame.HEIGHT; i++) {
            for(int j = 0; j < BombermanGame.WIDTH; j++) {
                nodes[i][j] = new Node(i,j);
            }
        }
    }
    public void reset() {
        for(int i = 0; i < BombermanGame.HEIGHT; i++) {
            for(int j = 0; j < BombermanGame.WIDTH; j++) {
                nodes[i][j].checked = false;
                nodes[i][j].open = false;
                nodes[i][j].solid = false;
            }
        }
        finished = false;
        openlist.clear();
        closedlist.clear();
        step = 0;
        count = 0;
    }
    public void setup(int startRow, int startCol, int endRow, int endCol) {
        reset();
        Start = nodes[startRow][startCol];
        End = nodes[endRow][endCol];
        Current = Start;
        openlist.add(Current);
        for(int i = 0; i < BombermanGame.HEIGHT; i++) {
            for(int j = 0; j < BombermanGame.WIDTH; j++) {
                if(Map[i][j]!= 1) {
                    nodes[i][j].solid = true;

                }
                getCost(nodes[i][j]);
            }
        }
    }
    public void getCost(Node node) {
        int dx = Math.abs(node.row- Start.row);
        int dy = Math.abs(node.col - Start.col);
        node.g = dx + dy;
        dx = Math.abs(node.row - End.row);
        dy = Math.abs(node.col - End.col);
        node.h = dx + dy;
        node.f = node.g +node. h;
    }
    public boolean search(){
        while(!finished && count < 300){
            int row  = Current.row;
            int col = Current.col;
            Current.checked = true;
            openlist.remove(Current);
            if(End.solid){
                return false;
            }
            if(row - 1 > 0){
                Opennode(nodes[row-1][col]);
            }
            if(col - 1 > 0){
                Opennode(nodes[row][col-1]);
            }
            if(row + 1 < BombermanGame.HEIGHT){
                Opennode(nodes[row+1][col]);
            }
            if(col + 1  < BombermanGame.WIDTH){
                Opennode(nodes[row][col+1]);
            }
            int bestIndex = 0;
            int bestCost = 500;
            for(int i = 0; i <openlist.size(); i++) {
                if(openlist.get(i).f < bestCost) {
                    bestCost = openlist.get(i).f;
                    bestIndex = i;
                } else if(openlist.get(i).f == bestCost) {
                    if(openlist.get(i).g < openlist.get(bestIndex).g)
                        bestIndex = i;
                }
            }
            if(openlist.isEmpty()) break;
            Current = openlist.get(bestIndex);
            if(Current == End) {
                finished = true;
                tracking();
            }
            count++;
        }
        return finished;
    }
    public void Opennode(Node node) {
        if(!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = Current;
            openlist.add(node);

        }
    }
    public void tracking() {
        Node cur = End;
        while(cur != Start) {
            step++;
            closedlist.add(0,cur);
            cur = cur.parent;
        }
    }
}
