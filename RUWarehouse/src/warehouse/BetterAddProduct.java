package warehouse;

/*
 * Use this class to test the betterAddProduct method.
 */ 
public class BetterAddProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        
        // Use this file to test betterAddProduct
        Warehouse w = new Warehouse();
        int n = StdIn.readInt();
        while(n > 0){
            int day = StdIn.readInt();
            int id = StdIn.readInt();
            String name = StdIn.readString();
            int stock = StdIn.readInt();
            int demand = StdIn.readInt();
            w.betterAddProduct(id, name, stock, day, demand);
            n --;
        }
        StdOut.println(w);
    }
}
