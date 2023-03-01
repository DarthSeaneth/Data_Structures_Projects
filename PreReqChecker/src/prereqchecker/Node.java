package prereqchecker;

public class Node<T> {
    private T data;
    private Node<T> next;
    private Node<T> previous;

    public Node(T data, Node<T> previous, Node<T> next){
        this.data = data;
        this.previous = previous;
        this.next = next;
    }

    public T getData(){
        return this.data;
    }

    public Node<T> getPrevious(){
        return this.previous;
    }

    public Node<T> getNext(){
        return this.next;
    }

    public void setData(T data){
        this.data = data;
    }

    public void setPrevious(Node<T> previous){
        this.previous = previous;
    }

    public void setNext(Node<T> next){
        this.next = next;
    }
}
