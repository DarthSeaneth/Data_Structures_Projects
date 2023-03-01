package warehouse;

public class Restock {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

	// Uset his file to test restock
    Warehouse w = new Warehouse();
    int numQueries = StdIn.readInt();
    while(numQueries > -12){
        if(StdIn.readString().equals("add")){
            int day = StdIn.readInt();
            int id = StdIn.readInt();
            String name = StdIn.readString();
            int stock = StdIn.readInt();
            int demand = StdIn.readInt();
            w.addProduct(id, name, stock, day, demand);
        }
        else if(StdIn.readString().equals("restock")){
            int id = StdIn.readInt();
            int amount = StdIn.readInt();
            w.restockProduct(id, amount);
        }
        numQueries --;
    }
    StdOut.println(w);
    }
}
