package prereqchecker;

public class DLL<T> {
    private Node<T> first;
    private Node<T> last;
    private int count;
    private Node<T> current;

    public DLL(){
        this.first = null;
        this.last = null;
        this.count = 0;
    }

    public T getFirst(){
        if(this.first != null){
            this.current = this.first.getNext();
            return this.first.getData();
        }
        else{
            this.current = null;
            return null;
        }
    }

    public T getNext(){
        if(this.current != null){
            T data = this.current.getData();
            this.current = this.current.getNext();
            return data;
        }
        return null;
    }

    public T getLast(){
        if(this.last != null){
            return this.last.getData();
        }
        return null;
    }

    public void addFirst(T data){
        Node<T> newNode = new Node<T>(data, null, this.first);
        if(this.first != null){
            this.first.setPrevious(newNode);
        }
        else{
            this.last = newNode;
        }
        this.first = newNode;
        this.count ++;
    }

    public void addLast(T data){
        Node<T> newNode = new Node<T>(data, this.last, null);
        if(this.last != null){
            this.last.setNext(newNode);
        }
        else{
            this.first = newNode;
        }
        this.last = newNode;
        this.count ++;
    }

    public void deleteFirst(){
        if(this.first != null){
            Node<T> newFirst = this.first.getNext();
            this.first = newFirst;
            if(newFirst != null){
                newFirst.setPrevious(null);
            }
            else{
                this.last = null;
            }
            this.count --;
        }
    }

    public void deleteLast(){
        if(this.last != null){
            Node<T> newLast = this.last.getPrevious();
            this.last = newLast;
            if(newLast != null){
                newLast.setNext(null);
            }
            else{
                this.first = null;
            }
            this.count --;
        }
    }

    public void traverse(){
        this.current = this.first;
        while(this.current != null){
            System.out.println(this.current.getData());
            this.current = this.current.getNext();
        }
    }

    public int size(){
        return this.count;
    }

    public String toString(){
        String ret = "";
        this.current = this.first;
        while(this.current != null){
            ret += "+" + this.current.getData();
            this.current = this.current.getNext();
        }
        return ret;
    }
}
