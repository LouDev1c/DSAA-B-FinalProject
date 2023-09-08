package Project;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Main {
    public static int n, m, e; // n行m列, e为初始权值，如想调用n直接用Main.n
    public static int p, k; // p为改变次数，k为询问次数
    public static int[][] graph; // 二维数组，graph[i][j]为M_ij的值（0为通路，1为墙）
    public static int[][] change;
    // 二维数组，change[i][0]:time, change[i][1]:x, change[i][2]:y
    public static int[] ask; // 一维数组，ask[i]为第i次询问的时间



    static Queue<Integer> place_of_wall1 = new Queue<>();
    static Queue<Integer> value_of_e1 = new Queue<>();
    static Queue<Integer> value_of_e1_A = new Queue<>();
    static int a ;

    static int findIndex(char[][] maze, char find) {
        int height = maze.length;
        int width = maze[0].length;

        for (int h = 0; h < height; h++)
            for (int w = 0; w < width; w++)
                if (maze[h][w] == find)
                    return h * width + w;
        return -1;
    }

    private static double[] heuristicEuclid(int width, int height, int end) {//计算曼哈顿距离存到一维数组中
        int endW = end % width;
        int endH = end / width;

        double[] heuristic = new double[width * height];

        for (int h = 0; h < height; h++)
            for (int w = 0; w < width; w++)
                heuristic[h * width + w] =  Math.sqrt(Math.pow(Math.abs(endW - w),2)+Math.pow(Math.abs(endH - h),2));
                //heuristic[h * width + w] =  Math.abs(endW - w) + Math.abs(endH - h);

        return heuristic;
    }

    private static double[] heuristicManhattan(int width, int height, int end) {//计算曼哈顿距离存到一维数组中
        int endW = end % width;
        int endH = end / width;

        double[] heuristic = new double[width * height];

        for (int h = 0; h < height; h++){
            for (int w = 0; w < width; w++){
                heuristic[h * width + w] =  Math.abs(endW - w) + Math.abs(endH - h);
            }
        }

        return heuristic;
    }
    public static char[][] oneToTwo(int[] array,int numOfRow,int numOfCol){
        char[][] result = new char[numOfRow][numOfCol];
        for (int i = 0; i < numOfRow; i++) {
            for (int j = 0; j < numOfCol; j++) {
                if(i==0&&j==0){
                    result[i][j] = 'S';
                }else if(i==numOfRow-1&&j==numOfCol-1){
                    result[i][j] = 'E';
                }else if(array[j + i*numOfCol]==0){
                    result[i][j] = 'R';
                }else{
                    result[i][j] = 'W';
                }

            }
        }
        return result;
    }

    public static void main(String[] args) {

        if(args[0].equals("gui")){
            int rows = StdIn.readInt(), cols = StdIn.readInt(), weight = StdIn.readInt();//用来读取行列和权值
            int[] boa = new int[rows * cols];
            for (int i = 0; i < rows * cols; i++) {
                boa[i] = StdIn.readInt();//用来读取迷宫的分布图
            }

            char[][] chessBoard = oneToTwo(boa,rows,cols);//将一维数组转化为二位数组，棋盘
            char[][] chessBoard1 = oneToTwo(boa,rows,cols);//将一维数组转化为二位数组，棋盘

            int cast_magic_number = StdIn.readInt();//用来读取魔法师施展魔法的次数
            Queue<Integer> place_of_wall = new Queue<>();

            for (int i = 0; i < 3*cast_magic_number; i++) {
                int b = StdIn.readInt();
                place_of_wall.enqueue(b);
                place_of_wall1.enqueue(b);
            }

            int inquiry_number = StdIn.readInt();//用来读取坤坤询问小妮可的次数
            Queue<Integer> value_of_e = new Queue<>();

            for (int i = 0; i < inquiry_number ; i++) {
                int a = StdIn.readInt();
                value_of_e.enqueue(a);
                value_of_e1.enqueue(a);
                value_of_e1_A.enqueue(a);
            }
            a = weight;

            int start = findIndex(chessBoard, 'S');
            int end = findIndex(chessBoard, 'E');
            double[] h = heuristicEuclid(chessBoard[0].length, chessBoard.length, end);//欧几里得距离
            double[] h1 = heuristicManhattan(chessBoard[0].length, chessBoard.length, end);//曼哈顿距离
            ARAStar aStar = new ARAStar(chessBoard,start, end, h, weight,place_of_wall,value_of_e);
            MainJFrame mainJFrame = new MainJFrame(aStar, 900, 900, chessBoard1, chessBoard.length, chessBoard[0].length);
        } else if(args[0].equals("terminal")){
                input();
                int changeIndex = 0, askIndex = 0, finalIndex = 1;
                int x = 0, y = 0;
                int[][] finalWay = new int[e + 1][2];
                finalWay[0][0] = 0;
                finalWay[0][1] = 0;
                for (; e > 0 && (x != n - 1 || y != m - 1); e--) {

                    if (e == change[changeIndex][0] && changeIndex < p && (!(change[changeIndex][1] == x && change[changeIndex][2] == y))) {
                        //System.out.println("change:"+change[changeIndex][1]+"  "+change[changeIndex][2]); ///////////
                        graph[change[changeIndex][1]][change[changeIndex][2]] = 1;
                        changeIndex++;
                    }


                    //System.out.println();

                    Astar astar = new Astar(x, y);
                    int[] next = new int[2];


                    if (e == ask[askIndex] && askIndex < k && askIndex < k) {
                        //System.out.println("e:"+e); ///////////debug
                        astar.printWay();
                        askIndex++;
                    }

                    next = astar.getway();
                    x = next[0];
                    y = next[1];
                    finalWay[finalIndex][0] = next[0];
                    finalWay[finalIndex][1] = next[1];
                    finalIndex++;
                    //System.out.println("x:"+x+"  y:"+y); ///////////debug
                    //System.out.println("")
                }
                StdOut.println(finalIndex );
                for (int i = 0; i < finalIndex; i++) {
                    StdOut.print(finalWay[i][0]);
                    StdOut.print(" ");
                    StdOut.print(finalWay[i][1]);
                    if (i != finalIndex - 1) StdOut.print(" ");
                }
            }
    }
    //Part1: input and make the graph
    public static void input() {
        //System.out.println("input");
        n = StdIn.readInt();
        m = StdIn.readInt();
        e = StdIn.readInt();

        graph = new int[n + 1][m + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                graph[i][j] = StdIn.readInt();
            }
        }
//System.out.println("graph[0][0]:"+graph[0][0]);
        p = StdIn.readInt();
        change = new int[p + 1][3];
        for (int i = 0; i < p; i++) {
            change[i][0] = StdIn.readInt();
            change[i][1] = StdIn.readInt();
            change[i][2] = StdIn.readInt();
        }
//System.out.println("change[0][0]:"+change[0][0]);
        k = StdIn.readInt();
        ask = new int[k + 1];
        for (int i = 0; i < k; i++) {
            ask[i] = StdIn.readInt();
        }

    }
}
