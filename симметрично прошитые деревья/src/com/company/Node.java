package com.company;

public class Node {
    Node left;
    Node right;
    int key;
    public boolean hasLeftThread;
    public boolean hasRightThread;

    public Node(int key){              //Конструтор для инициализации
        this.key = key;
        hasLeftThread = true;
        hasLeftThread = true;
        left  = null;
        right = null;
    }

    //Метод для вывода ключа
    public void show() {
        System.out.print(key + " ");
    }
}
