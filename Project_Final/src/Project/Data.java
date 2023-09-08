package Project;

import Project.Astar;
import edu.princeton.cs.algs4.StdOut;

import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Random;
import java.io.IOException;
import java.util.Random;
import java.util.Arrays;

public class Data{

    Data(){
        try {
            PrintWriter writer = new PrintWriter("Data.txt", "UTF-8");
            Random random = new Random();

            int n = random.nextInt(100)+3;
            int m = random.nextInt(100)+3;
            int e = random.nextInt(47)+3+n+m;
            writer.println(n+" "+m+" "+e);

            int graph[][] = new int[n][m];
        //Mij矩阵
            for(int i=0;i<n;i++){
                for(int j=0;j<m;j++){
                    
                    if(random.nextInt(100)<50) graph[i][j]=0;
                    else graph[i][j]=1;
                    if(i==0 && j==0) graph[i][j]=0;
                    if(i==n-1 && j==m-1) graph[i][j]=0;
                    if(i==n-1 || j==0) graph[i][j]=0;
                    writer.print(graph[i][j]+" ");
                }
            }
            //制造通路
            
            //计算最短路，然后不在里面更新
            Astar astar = new Astar(0,0);
            int[][] way = astar.way;

        //魔法师施法
            int p = random.nextInt(e-3)+3;
            writer.println();
            writer.println(p);

            int[] t = new int[p];
            for(int i=0;i<p;i++)
                t[i] = random.nextInt(e-1)+1;
            Arrays.sort(t);
            for(int i=p-1;i>=0;i--){
                writer.print(t[i]+" ");
                int xchange=random.nextInt(n-2);
                int ychange=random.nextInt(m-2)+1;
                boolean flag=false;
                while(flag==false){
                    boolean contain = false;
                    for(int j=0;j<way.length;j++){
                        if(way[j][0]==xchange && way[j][1]==ychange){
                            contain=true;
                            break;
                        }
                    }
                    if(contain==true){
                        xchange=random.nextInt(n-2);
                        ychange=random.nextInt(m-2)+1;
                    }
                    else flag=true;
                }
                writer.print(xchange+" ");
                writer.print(ychange+" ");
            }
            // writer.print(t[0]+" ");
            // writer.print(random.nextInt(m-2)+1+" ");
            // writer.println(random.nextInt(n-2));

        //询问
            int[] alreadask = new int[e+100];
            int k = random.nextInt(e-3)+3;
            
            writer.println();
            writer.println(k);
            int[] a = new int[k];
            for(int i=0;i<k;i++){
                int l =random.nextInt(e-1)+1;
                while(alreadask[l]==1){
                    l = random.nextInt(e-1)+1;
                }
                a[i] = l;
                alreadask[l]=1;
            }
                
            Arrays.sort(a);
        
            for(int i=k-1;i>=0;i--){
                writer.print(a[i]+" ");
            }
            writer.close();
        } catch (IOException e) {
            StdOut.print("An error occurred while trying to write to the file.");
            e.printStackTrace();
        }
    }
}