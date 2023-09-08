package Project;

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Map;
import java.util.HashMap;

public class Astar {
    public static int[][] way = new int[10009][2];
    static int index;
    PriorityQueue<Node> openList = new PriorityQueue<Node>((o1,o2)->Double.compare(o1.f, o2.f));
    ArrayList<Integer> closeList = new ArrayList<Integer>();
    Map<Integer, Node> openListMap = new HashMap<>();

    public static class Node{
        int id,x,y;
        int g;
        double f;
        Node parent;

        Node(int xn,int yn,int g,boolean flag,Node parent){
            this.id = xn*Main.m+yn;
            this.x = xn;
            this.y = yn;
            this.g = g;
            this.f = g+Main.e*Math.sqrt( (Math.pow(Main.n-1 - xn, 2)) + Math.pow(Main.m-1 - yn, 2) );
            if(flag==true) this.f=0;
            this.parent = parent;
        }
    }
    
    public Astar(int xnow,int ynow){
        for(int i=1;i<Main.n*Main.m;i++){
            //System.out.println("Main.n*Main.m:"+Main.n*Main.m );
            way[i][0] = 0;
            way[i][1] = 0;
        }
        Node start = new Node(xnow,ynow,0,true,null);
        openList.clear();
        openList.add(start);
        while(!openList.isEmpty()){
            
            PriorityQueue<Node> copy = new PriorityQueue<>(openList);
            while (!copy.isEmpty()) {
                Node node = copy.poll();
                //if(Main.e>=11)System.out.print(node.x + " " + node.y+" "+node.f+" ,   ");
            }
            //if(Main.e>=11) System.out.println();
            
            Node currerNode = openList.poll();
            


            if(currerNode.id == Main.n*Main.m-1){   
                //System.out.println("find the way");
                index=0;//way数组的下标
                while(currerNode.parent!=null){
                    way[index][0] = currerNode.id/Main.m;
                    way[index][1] = currerNode.id%Main.m;
                    currerNode = currerNode.parent;
                    index++;
                }
                way[index][0] = currerNode.id/Main.m;
                way[index][1] = currerNode.id%Main.m;
                currerNode = currerNode.parent;
                break;
            }

            //openList.remove(currerNode);
            
            closeList.add(currerNode.id);

//右下
            if(currerNode.y<Main.m-1 && currerNode.x<Main.n-1 && (closeList.contains(currerNode.id+1+Main.m)==false) 
            && Main.graph[currerNode.x+1][currerNode.y+1]==0){
                Node rightNode = new Node(currerNode.x+1,currerNode.y+1,currerNode.g+1,false,currerNode);
                if(openListMap.containsKey(rightNode.id)){
                    Node existingNode = openListMap.get(rightNode.id);
                    if(rightNode.g < existingNode.g){
                        // 如果新节点的g值更小，更新节点信息，并在openList中更新位置
                        existingNode.parent = rightNode.parent;
                        existingNode.g = rightNode.g;
                        existingNode.f = rightNode.f;
                        openList.remove(existingNode);
                        openList.add(existingNode);
                    }
                } else {
                    openList.add(rightNode);
                    openListMap.put(rightNode.id, rightNode);
                }
            }           

          //left下
if(currerNode.x<Main.n-1 && currerNode.y>0 && (closeList.contains(currerNode.id+Main.m-1)==false) 
&& Main.graph[currerNode.x+1][currerNode.y-1]==0){
    Node downNode = new Node(currerNode.x+1,currerNode.y-1,currerNode.g+1,false,currerNode);
    if(openListMap.containsKey(downNode.id)){
        Node existingNode = openListMap.get(downNode.id);
        if(downNode.g < existingNode.g){
            // 如果新节点的g值更小，更新节点信息，并在openList中更新位置
            existingNode.parent = downNode.parent;
            existingNode.g = downNode.g;
            existingNode.f = downNode.f;
            openList.remove(existingNode);
            openList.add(existingNode);
        }
    } else {
        openList.add(downNode);
        openListMap.put(downNode.id, downNode);
    }
}


// 右
if(currerNode.y<Main.m-1 && (closeList.contains(currerNode.id+1)==false) && Main.graph[currerNode.x][currerNode.y+1]==0){
    Node rightNode = new Node(currerNode.x,currerNode.y+1,currerNode.g+1,false,currerNode);
    if(openListMap.containsKey(rightNode.id)){
        Node existingNode = openListMap.get(rightNode.id);
        if(rightNode.g < existingNode.g){
            // 如果新节点的g值更小，更新节点信息，并在openList中更新位置
            existingNode.parent = rightNode.parent;
            existingNode.g = rightNode.g;
            existingNode.f = rightNode.f;
            openList.remove(existingNode);
            openList.add(existingNode);
        }
    } else {
        openList.add(rightNode);
        openListMap.put(rightNode.id, rightNode);
    }
}

// 下
if(currerNode.x<Main.n-1 && (closeList.contains(currerNode.id+Main.m)==false) && Main.graph[currerNode.x+1][currerNode.y]==0){
    Node downNode = new Node(currerNode.x+1, currerNode.y, currerNode.g+1,false,currerNode);
    if(openListMap.containsKey(downNode.id)){
        Node existingNode = openListMap.get(downNode.id);
        if(downNode.g < existingNode.g){
            // 如果新节点的g值更小，更新节点信息，并在openList中更新位置
            existingNode.parent = downNode.parent;
            existingNode.g = downNode.g;
            existingNode.f = downNode.f;
            openList.remove(existingNode);
            openList.add(existingNode);
        }
    } else {
        openList.add(downNode);
        openListMap.put(downNode.id, downNode);
    }
}

// 右上
if(currerNode.y<Main.m-1 && currerNode.x>0 && (closeList.contains(currerNode.id+1-Main.m)==false) 
&& Main.graph[currerNode.x-1][currerNode.y+1]==0){
    Node upRightNode = new Node(currerNode.x-1, currerNode.y+1,currerNode.g+1,false,currerNode);
    if(openListMap.containsKey(upRightNode.id)){
        Node existingNode = openListMap.get(upRightNode.id);
        if(upRightNode.g < existingNode.g){
            // 如果新节点的g值更小，更新节点信息，并在openList中更新位置
            existingNode.parent = upRightNode.parent;
            existingNode.g = upRightNode.g;
            existingNode.f = upRightNode.f;
            openList.remove(existingNode);
            openList.add(existingNode);
        }
    } else {
        openList.add(upRightNode);
        openListMap.put(upRightNode.id, upRightNode);
    }
}

// 上
if(currerNode.x>0 && (closeList.contains(currerNode.id-Main.m)==false) && Main.graph[currerNode.x-1][currerNode.y]==0){
    Node upNode = new Node(currerNode.x-1, currerNode.y, currerNode.g+1,false,currerNode);
    if(openListMap.containsKey(upNode.id)){
        Node existingNode = openListMap.get(upNode.id);
        if(upNode.g < existingNode.g){
            // 如果新节点的g值更小，更新节点信息，并在openList中更新位置
            existingNode.parent = upNode.parent;
            existingNode.g = upNode.g;
            existingNode.f = upNode.f;
            openList.remove(existingNode);
            openList.add(existingNode);
        }
    } else {
        openList.add(upNode);
        openListMap.put(upNode.id, upNode);
    }
}

// 左
if(currerNode.y>0 && (closeList.contains(currerNode.id-1)==false) && Main.graph[currerNode.x][currerNode.y-1]==0){
    Node leftNode = new Node(currerNode.x, currerNode.y-1, currerNode.g+1,false,currerNode);
    if(openListMap.containsKey(leftNode.id)){
        Node existingNode = openListMap.get(leftNode.id);
        if(leftNode.g < existingNode.g){
            // 如果新节点的g值更小，更新节点信息，并在openList中更新位置
            existingNode.parent = leftNode.parent;
            existingNode.g = leftNode.g;
            existingNode.f = leftNode.f;
            openList.remove(existingNode);
            openList.add(existingNode);
        }
    } else {
        openList.add(leftNode);
        openListMap.put(leftNode.id, leftNode);
    }
}

// 左上
if(currerNode.x>0 && currerNode.y>0 && (closeList.contains(currerNode.id-Main.m-1)==false) 
&& Main.graph[currerNode.x-1][currerNode.y-1]==0){
    Node upLeftNode = new Node(currerNode.x-1, currerNode.y-1, currerNode.g+1,false,currerNode);
    if(openListMap.containsKey(upLeftNode.id)){
        Node existingNode = openListMap.get(upLeftNode.id);
        if(upLeftNode.g < existingNode.g){
            // 如果新节点的g值更小，更新节点信息，并在openList中更新位置
            existingNode.parent = upLeftNode.parent;
            existingNode.g = upLeftNode.g;
            existingNode.f = upLeftNode.f;
            openList.remove(existingNode);
            openList.add(existingNode);
        }
    } else {
        openList.add(upLeftNode);
        openListMap.put(upLeftNode.id, upLeftNode);
    }
}


        }
    
    } 

    public static void printWay(){
        int indexi=index+1;
        StdOut.println(indexi);
        //System.out.print("way: ");
        for(int i=index;i>0;i--){
            StdOut.print(way[i][0]);
            StdOut.print(" ");
            StdOut.print(way[i][1]);
           // System.out.print(",");
            StdOut.print(" ");
        }
       
        StdOut.print(way[0][0]);
        StdOut.print(" ");
        StdOut.println(way[0][1]);
    }
    
 
    public static int[] getway(){
        int[] result = new int[2];
        result[0] = way[index-1][0];
        result[1] = way[index-1][1];
        return result;
    }




}


