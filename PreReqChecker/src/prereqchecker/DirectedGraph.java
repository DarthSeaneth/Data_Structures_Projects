package prereqchecker;

public class DirectedGraph<T> {  //Implementation of directed graph data structure
    private Object[] vertices;
    private Object[] adjList;
    private int n, capacity;

    public DirectedGraph(int capacity){
        this.capacity = capacity;
        this.n = 0;
        this.vertices = new Object[capacity];
        this.adjList = new Object[capacity];
        for(int i = 0; i < this.capacity; i ++){
            DLL<Integer> myList = new DLL<Integer>();
            this.adjList[i] = myList;
        }
    }

    public DirectedGraph(){
        this(100);
    }

    public int indexOfVertex(T vertex){  //O(n) running time
        //find vertex, return -1 if not found
        int i = 0;
        while(i < this.n && (!vertex.equals(this.vertices[i]))){
            i ++;
        }
        if(i < this.n){
            return i;
        }
        else{
            return -1;
        }
    }

    public T infoOfVertex(int v){  //O(1) running time
        @SuppressWarnings("unchecked")
        T vertex = (T) this.vertices[v];
        return vertex;
    }

    public int addVertex(T vertex){  //O(n) running time
        int i = indexOfVertex(vertex);  //find out if vertex already exists
        if(i == -1){
            if(this.n + 1 <= this.capacity){
                this.n ++;
                this.vertices[this.n - 1] = (Object) vertex;
            }
            else{
                throw new IndexOutOfBoundsException();
            }
            return this.n - 1;
        }
        return i;
    }

    public void addEdge(int v1, int v2){  //O(n) running time
        @SuppressWarnings("unchecked")
        DLL<Integer> myList = (DLL<Integer>) this.adjList[v1];
        //traverse adjacency list to see if v2 is already present
        Integer v = myList.getFirst();
        while(v != null && (!v.equals(v2))){
            v = myList.getNext();
        }
        if(v == null){
            myList.addLast(v2);
        }  //else do nothing, edge already exists
    }

    public void addEdge(T vertex1, T vertex2){  //O(n) running time
        int v1 = indexOfVertex(vertex1);
        int v2 = indexOfVertex(vertex2);
        if(v1 == -1 || v2 == -1){  //if one vertex or both vertices do not exist
            throw new IndexOutOfBoundsException();
        }
        addEdge(v1, v2);
    }

    public boolean containsEdge(int v1, int v2){  //O(n) running time
        @SuppressWarnings("unchecked")
        DLL<Integer> myList = (DLL<Integer>) this.adjList[v1];
        Integer v = myList.getFirst();
        while(v != null && (!v.equals(v2))){  //traverse adjacency list
            v = myList.getNext();
        }
        return v != null;  //edge exists if v is not null
    }

    public int firstNeighbor(int v){  //O(1) running time
        @SuppressWarnings("unchecked")
        DLL<Integer> myList = (DLL<Integer>) this.adjList[v];
        return myList.getFirst();
    }

    public int nextNeighbor(int v){  //O(1) running time
        @SuppressWarnings("unchecked")
        DLL<Integer> myList = (DLL<Integer>) this.adjList[v];
        Integer v2 = myList.getNext();
        if(v2 != null){  //edge exists if v2 is not null
            return v2;
        }
        else{
            return -1;
        }
    }

    public int numberOfVertices(){  //O(1) running time
        return this.n;
    }
}
