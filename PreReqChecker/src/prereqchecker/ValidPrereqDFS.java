package prereqchecker;

public class ValidPrereqDFS {  //DFS algorithm to be used with ValidPrereq.java
    public static boolean[] visited;

    public static void explore(DirectedGraph<String> graph, int startVertex){
        visited[startVertex] = true;
        try{
            int nextVertex = graph.firstNeighbor(startVertex);
            while(nextVertex != -1){
                if(!visited[nextVertex]){
                    explore(graph, nextVertex);
                }
                nextVertex = graph.nextNeighbor(startVertex);
            }
        }
        catch(NullPointerException e){}
    }

    public static boolean dfs(DirectedGraph<String> graph, int startVertex, String course1){
        visited = new boolean[graph.numberOfVertices()];
        explore(graph, startVertex);
        int v = graph.indexOfVertex(course1);
        if(visited[v]){
            return false;
        }
        else{
            return true;
        }
    }
}
