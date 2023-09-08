package Project;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;


public class ARAStar {

    int i = 0;
    int j = 2;
    //g函数代表该点到起点的距离，需要在这个点被访问时更新值
    //h函数代表改点到终点的距离，一般使用哈密顿距离进行计算
    //f函数代表h+g
    int epsilon;
    int N;//获取图的顶点数
    double[] heuristic;
    char[][] chessBoard;
    Graph graph;
    int S;
    //int[] g;
    int E;
    //PriorityQueue<Node> openSet;
    //boolean[] closed;
    Queue<Integer> place_of_wall;
    Queue<Integer> value_of_e;
    ArrayList<Integer> arrayList = new ArrayList<>();
    boolean isFound = false;
    int row;
    int col;
    int l = 0;
    int[] data = new int[4];
    //链表用来存储走过来的路径，便于回溯private final LinkedList<Integer> path = new LinkedList<>();//链表用来存储走过来的路径，便于回溯
    boolean finishFind = false;
    LinkedList<Integer> path = new LinkedList<>();
    LinkedList<Integer> path1 = new LinkedList<>();
    LinkedList<Integer>[] pathAll = new LinkedList[Main.value_of_e1.size()];

    public ARAStar(char[][] chessBoard, int start, int end, double[] heuristic, int e, Queue<Integer> place_of_wall, Queue<Integer> value_of_e) {

        this.chessBoard = chessBoard;
        this.graph = this.buildGraph(chessBoard);
        this.S = start;
        this.E = end;
        this.epsilon = e;
        this.N = graph.size();

        this.place_of_wall = place_of_wall;
        this.value_of_e = value_of_e;
        //this.pathAll = new LinkedList[value_of_e.size()];
        this.heuristic = heuristic;

        call();
    }

    public Graph buildGraph(char[][] maze) {
        int height = maze.length;
        int width = maze[0].length;
        Graph graph = new GraphAdjList(height * width);
        int[] dh = new int[]{-1, 1, 0, 0, -1, -1, 1, 1};//定义一个节点临近的八个节点上、下、左、右
        int[] dw = new int[]{0, 0, -1, 1, -1, 1, -1, 1};//左上、右上、左下、右下

        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                if (maze[h][w] == 'W')
                    continue;
                for (int i = 0; i < 8; i++) {
                    int h2 = h + dh[i];
                    int w2 = w + dw[i];
                    if (h2 < 0 || h2 >= height || w2 < 0 || w2 >= width || maze[h2][w2] == 'W')
                        continue;
                    graph.addEdge(h * width + w, h2 * width + w2);
                }
            }
        }
        return graph;
    }

    public void call() {//一次计算出所有的结果

        while (!value_of_e.isEmpty()) {

            if (i == 0) {
                int a = value_of_e.peek();//把询问的e的值取出
                if (a == epsilon) {//说明第一次就需要访问路径
                    value_of_e.dequeue();
                    data = Find(0, -1, -1);

                    LinkedList<Integer> path_temp = path;//生成临时链表，防止被更改
                    pathAll[l] = path_temp;l++;//添加路径
                } else {//说明第一次就不需要访问路径
                    data = Find(0, -1, -1);
                }
            } else {//除去第一次的情况
                if (data[1] != -1 || data[2] != -1) {//有墙

                    if (data[0] == 1) {//由于
                        value_of_e.dequeue();
                    }

                    if (path.contains(row * chessBoard[0].length + col)) {//有墙在路径
                        int a = data[0];
                        int[] temp = findLocation(chessBoard,path1.get(i-1));
                        if(temp[0]==row&&temp[1]==col){
                            path.remove(0);
                            path1.add(path.get(1));
                            epsilon--;
                            /*****这部分主要用来为下一次的情形做判断*****/
                            if (place_of_wall.size() != 0 &&place_of_wall.peek() == epsilon) {
                                place_of_wall.dequeue();
                                int row = place_of_wall.dequeue();
                                int col = place_of_wall.dequeue();
                                data[1] = row;
                                data[2] = col;
                            } else {
                                data[1] = -1;
                                data[2] = -1;
                            }
                            if (value_of_e.size() != 0 && epsilon == value_of_e.peek()) {
                                value_of_e.peek();
                                data[0] = 1;
                            } else {
                                data[0] = 0;
                            }
                            /*******/

                        }else{
                            path.clear();
                            data = Find(path1.get(i - 1), row, col);
                        }


                        if (a == 1) {//访问

                            LinkedList<Integer> path_temp = new LinkedList<>();//生成临时链表，防止被更改
                            for (int k = 0; k < path.size(); k++) {
                                path_temp.add(k,path.get(k));
                            }
                            pathAll[l] = path_temp;l++;//添加路径

                        } else {//不访问
                            //path.remove(0);
                        }

                    } else {//有墙不在路径
                        if (row != -1 && col != -1) {
                            chessBoard[row][col] = 'W';
                            graph = buildGraph(chessBoard);
                        }
                        if(path.size()<3){

                        }else{
                            path1.add(path.get(j));
                            path.remove(0);
                        }

                        if (data[0] == 1) {//访问

                            LinkedList<Integer> path_temp = new LinkedList<>();//生成临时链表，防止被更改
                            for (int k = 0; k < path.size(); k++) {
                                path_temp.add(k,path.get(k));
                            }
                            pathAll[l] = path_temp;l++;//添加路径

                        } else {//不访问

                        }

                        epsilon--;
                        /*****这部分主要用来为下一次的情形做判断*****/
                        if (place_of_wall.size() != 0 &&place_of_wall.peek() == epsilon) {
                            place_of_wall.dequeue();
                            int row = place_of_wall.dequeue();
                            int col = place_of_wall.dequeue();
                            data[1] = row;
                            data[2] = col;
                        } else {
                            data[1] = -1;
                            data[2] = -1;
                        }
                        if (value_of_e.size() != 0 && epsilon == value_of_e.peek()) {
                            value_of_e.peek();
                            data[0] = 1;
                        } else {
                            data[0] = 0;
                        }
                        /*******/

                    }



                } else {//无墙
/*                    System.out.println();
                    System.out.println(epsilon);
                    System.out.println(path.size());
                    System.out.println(path1.size());*/
                    if(path.size()<3){

                    }else{
                        path1.add(path.get(j));
                        path.remove(0);
                    }


                    //path.remove(0);
                    if (data[0] == 1) {//访问
                        value_of_e.dequeue();

                        LinkedList<Integer> path_temp = new LinkedList<>();//生成临时链表，防止被更改
                        for (int k = 0; k < path.size(); k++) {
                            path_temp.add(k,path.get(k));
                        }
                        pathAll[l] = path_temp;l++;//添加路径
                    } else {//不访问

                    }

                    epsilon--;
                    /*****这部分主要用来为下一次的情形做判断*****/
                    if (place_of_wall.size() != 0 &&place_of_wall.peek() == epsilon) {
                        place_of_wall.dequeue();
                        int row = place_of_wall.dequeue();
                        int col = place_of_wall.dequeue();
                        data[1] = row;
                        data[2] = col;
                    } else {
                        data[1] = -1;
                        data[2] = -1;
                    }
                    if (value_of_e.size() != 0 && epsilon == value_of_e.peek()) {
                        value_of_e.peek();
                        data[0] = 1;
                    } else {
                        data[0] = 0;
                    }
                    /*******/
                }
            }

            row = data[1];col = data[2];//每次循环均需要更新row和col的值，方便下次使用
            i++;
        }

        for (; j < path.size(); j++) {//添加后续没有添加的路径来形成最终路径
            path1.add(path.get(j));
        }
        finishFind = true;//标志计算过程已完成，未完成就会弹出弹框，请等待
    }

    public int[] Find(int id, int row, int col) {
        int[] arr = {0, -1, -1, -1};//该数组用来传递情况 第一个数代表是否是访问的情况0代表不访问，1代表访问，二、三数代表下次是否添加墙

        if (row != -1 && col != -1) {
            chessBoard[row][col] = 'W';
            graph = buildGraph(chessBoard);
        }


        double[] g = new double[N];
        Arrays.fill(g, Integer.MAX_VALUE);//定义个g函数，初始时，里面的值都很大
        g[id] = 0;//更新起点
        PriorityQueue<Node> openSet = new PriorityQueue<>(N - 1);
        boolean[] closed = new boolean[N];

        openSet.add(new Node(id, 0, heuristic[id], null));//添加进去起点

        //第一个是当前所在位置，第二第三个是墙的坐标，第四个是是否该展示路线 0展示，-1不展示
        while (!openSet.isEmpty()) {
            /*if(epsilon==1282){

            }*/
            //System.out.println(openSet.size());
            Node s = openSet.poll();
            closed[s.id] = true;
            if (s.id == E) {
                LinkedList<Integer> path2 = new LinkedList<>();
                for (Node n = s; n != null; n = n.previous) {
                    path2.addFirst(n.id);
                    path.addFirst(n.id);
                }
                //System.out.println(path.size());
/*                if(i==0){
                    path1.add(path2.get(1));
                }else{
                    path1.add(path2.get(2));
                }*/
                path1.add(path2.get(1));
                break;
            }

            for (Integer s1 : graph.adjacency(s.id)) {//s1是s相邻节点，也会对其更新g函数的值
                if (closed[s1]) {
                    continue;
                }
                if (g[s1] > s.g + 1) {
                    g[s1] = s.g + 1;
                    openSet.add(new Node(s1, g[s1], g[s1] + epsilon * heuristic[s1], s));//这里添加了膨胀式A*算法的系数
                }
            }
        }

        epsilon--;
        if (place_of_wall.size() != 0 &&place_of_wall.peek() == epsilon) {
            place_of_wall.dequeue();
            int row1 = place_of_wall.dequeue();
            int col1 = place_of_wall.dequeue();
            arr[1] = row1;
            arr[2] = col1;
        }
        if (value_of_e.size() != 0 && epsilon == value_of_e.peek()) {
            value_of_e.peek();
            arr[0] = 1;
        }

        return arr;
    }


    public void Terminal(){
        path1.addFirst(0);
        for (int k = 0; k < pathAll.length; k++) {
            StdOut.println(pathAll[k].size());
            for (int m = 0; m < pathAll[k].size(); m++) {
                int[] location = findLocation(chessBoard, pathAll[k].get(m));
                StdOut.print(location[0]+" "+location[1]+" ");
            }
            StdOut.println();
        }
        StdOut.println(path1.size());
        for (int k = 0; k < path1.size(); k++) {
            int[] location = findLocation(chessBoard, path1.get(k));
            StdOut.print(location[0]+" "+location[1]+" ");
        }
        StdOut.println();
    }

    public static int[] findLocation(char[][] chessBoard, int index){//该方法用来将索引值转化为坐标
        int[] arr = new int[2];
        int row = chessBoard.length;
        int col = chessBoard[0].length;

        int rowLocation = index / col;
        int colLocaiton = index % col;

        arr[0] = rowLocation;
        arr[1] = colLocaiton;
        return arr;
    }

    private static class Node implements Comparable<Node> {
        final int id;
        final double g;
        final double f;

        final Node previous;

        Node(int id, double g, double f, Node previous) {
            this.id = id;
            this.g = g;
            this.f = f;
            this.previous = previous;
        }

        @Override
        public int compareTo(Node o) {
            return Double.compare(f, o.f);
        }
    }

}
