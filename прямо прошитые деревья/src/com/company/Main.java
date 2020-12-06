package com.company;

import java.util.LinkedHashSet;
import java.util.Random;

public class Main {
    public static void main(String[] args){
        //Объект для генерации случайных чисел
        Random rand = new Random();
        //LinkedHashSet для исключения дубликатов ключей. Также сохраняет порядок вставки
        LinkedHashSet<Integer> set = new LinkedHashSet<>();
        Tree tree = new Tree();
        System.out.println("\n");


        //Создаем 10 ключей и кладем их во множество
        for (int i = 0; i < 100; i++)
            set.add(rand.nextInt(10));
        //Проходимся по множеству и каждый его элемент вставляем в дерево двоичного поиска
        System.out.println("Порядок вставки элементов в двоичное дерево поиска: ");
        for (Integer it : set){
            System.out.print(it + " ");
            tree.insert(it);
        }
        System.out.println("\n");
        tree.printTree();

        //Делаем прошивку
        tree.doThreading();

        System.out.println("Проход по прошитому дереву в прямом порядке:");
        tree.doTraversing();

        //Делаем вставку элементов в прошитое дерево
        tree.insertToThreadedBinaryTree(-1);
        tree.insertToThreadedBinaryTree(-50);
        tree.insertToThreadedBinaryTree(20);
        tree.insertToThreadedBinaryTree(99);
        tree.insertToThreadedBinaryTree(-45);

        System.out.println("Проход по прошитому дереву в прямом порядке после вставки:");
        tree.doTraversing();

        //Удаляем примерно первую половину вставленных элементов
        int K = set.size() / 2;
        for (Integer it : set){
            tree.deleteFromThreadedBinaryTree(it);
            K--;
            if (K == 0) break;
        }

        System.out.println("Проход по прошитому дереву в прямом порядке после удаления:");
        tree.doTraversing();
    }
}