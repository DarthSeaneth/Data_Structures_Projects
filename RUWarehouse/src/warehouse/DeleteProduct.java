package warehouse;

/*
 * Use this class to test the deleteProduct method.
 */ 
public class DeleteProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

	// Use this file to test deleteProduct
    Warehouse w = new Warehouse();
    int numQueries = StdIn.readInt();
    while(numQueries > 0){
        if(StdIn.readString().equals("add")){
            int day = StdIn.readInt();
            int id = StdIn.readInt();
            String name = StdIn.readString();
            int stock = StdIn.readInt();
            int demand = StdIn.readInt();
            w.addProduct(id, name, stock, day, demand);
        }
        else if(StdIn.readString().equals("delete")){
            int id = StdIn.readInt();
            w.deleteProduct(id);
        }
        numQueries --;
    }
    StdOut.println(w);
    }
}
