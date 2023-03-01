package prereqchecker;

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
 * AdjListOutputFile name is passed through the command line as args[1]
 * Output to AdjListOutputFile with the format:
 * 1. c lines, each starting with a different course ID, then 
 *    listing all of that course's prerequisites (space separated)
 */
public class AdjList {
    public static void main(String[] args) {

        if ( args.length < 2 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.AdjList <adjacency list INput file> <adjacency list OUTput file>");
            return;
        }

        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

        int numVertices = StdIn.readInt();
        DirectedGraph<String> graph = new DirectedGraph<String>(numVertices);

        for(int i = numVertices; i > 0; i --){
            graph.addVertex(StdIn.readString());
        }

        int numEdges = StdIn.readInt();

        for(int i = numEdges; i > 0; i --){
            graph.addEdge(StdIn.readString(), StdIn.readString());
        }
        
        for(int i = numVertices, j = 0; i > 0; i --, j ++){
            StdOut.print(graph.infoOfVertex(j) + " ");
            try{
                int v = graph.firstNeighbor(j);
                while(v != -1){
                    StdOut.print(graph.infoOfVertex(v) + " ");
                    v = graph.nextNeighbor(j);
                }
            }
            catch(NullPointerException e){}
            if(i == 1)
                break;
            StdOut.println();
        }
    }
}
