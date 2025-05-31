import java.util.*;

/**
 * Vim commands
 *
 * gg-> goes to the beginning of file. V block mode, G goes EOF, +y copy selection
 * cat file | pbcopy (mac)
 *
 * :Vsplit :vsp (vertical split) | Ctrl-w h (left) Ctrl l (right) Ctrl-w (cycle)
 * */

class MyFixedArray {
    private int[] data;
    private int size;

    public MyFixedArray(int capacity){
        data = new int[capacity];
        size = 0;
    }

    public void set(int index, int value) {
        if(index < 0 || index >= data.length)
            throw new IndexOutOfBoundsException("Invalid index " + index);
        data[index] = value;
        if(index >= size) size = index + 1;
    }

    public int get(int index){
        if(index < 0 || index >= data.length)
            throw new IndexOutOfBoundsException("Invalid index " + index);
        return data[index];
    }

    public int length(){
        return data.length;
    }

    public void print(){
        for(int i = 0; i < data.length; i++)
            System.out.println("Index %d : value %d".formatted(i, data[i]));
    }

    public void reverse(){
        int left = 0;
        int right = size - 1;

        while(left < right){
            int tmp = data[left];
            data[left] = data[right];
            data[right] = tmp;
            left++;
            right--;
        }
    }

    public static void main(String[] args){
        MyFixedArray arr = new MyFixedArray(4);
        arr.set(0,10);
        arr.set(1,20);
        arr.set(2,30);
        arr.set(3,40);

        System.out.println("VAlue at index 2: %d".formatted(arr.get(2)));
        
        System.out.println("Before reversing ...");
        arr.print();
        System.out.println("After reversing ...");
        arr.reverse();
        arr.print();
    }
}
