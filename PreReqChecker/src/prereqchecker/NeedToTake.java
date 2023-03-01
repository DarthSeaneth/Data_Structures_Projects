package prereqchecker;

import java.util.*;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java NeedToTake <adjacency list INput file> <need to take INput file> <need to take OUTput file>");
            return;
        }

        StdIn.setFile(args[0]);

        int numVertices = StdIn.readInt();
        DirectedGraph<String> graph = new DirectedGraph<String>(numVertices);

        for(int i = numVertices; i > 0; i --){
            graph.addVertex(StdIn.readString());
        }

        int numEdges = StdIn.readInt();

        for(int i = numEdges; i > 0; i --){
            graph.addEdge(StdIn.readString(), StdIn.readString());
        }

        StdIn.setFile(args[1]);
        StdOut.setFile(args[2]);

        String targetCourse = StdIn.readString();
        int targetCourseIndex = graph.indexOfVertex(targetCourse);

        int numCourses = StdIn.readInt();
        String[] courses = new String[numCourses];
        for(int i = numCourses, j = 0; i > 0; i --, j ++){
            courses[j] = StdIn.readString();
        }
        boolean[] visited = new boolean[graph.numberOfVertices()];
        for(int i = 0; i < numCourses; i ++){
            boolean[] temp = NeedToTakeDFS.dfs(graph, graph.indexOfVertex(courses[i]));
            for(int j = 0; j < graph.numberOfVertices(); j ++){
                if(temp[j]){
                    visited[j] = true;
                }
            }
        }
        
        int v = graph.firstNeighbor(targetCourseIndex);
        while(v != -1){
            if(!visited[v]){
                StdOut.println(graph.infoOfVertex(v));
                int temp = graph.firstNeighbor(v);
                while(temp != -1){
                    if(!visited[temp]){
                        StdOut.println(graph.infoOfVertex(temp));
                    }
                    temp = graph.nextNeighbor(v);
                }
                v = graph.nextNeighbor(targetCourseIndex);
            }
        }
    }
}
