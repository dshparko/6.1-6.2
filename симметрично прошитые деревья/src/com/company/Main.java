package com.company;

//Бинарное дерево поиска с симметричной прошивкой
import java.util.LinkedHashSet;
import java.util.Random;

public class Main {
    public static void main(String[] args){
        Random rand = new Random();//Объект для генерации случайных чисел
        //LinkedHashSet для исключения дубликатов ключей. Также сохраняет порядок вставки
        LinkedHashSet<Integer> set = new LinkedHashSet<>();

        Tree tree = new Tree();//Создаем двоичное дерево поиска
        System.out.println("\n");


        //Создаем 10 ключей и кладем их во множество
        for (int i = 0; i < 11; i++)
            set.add(rand.nextInt(100));
        //Проходимся по множеству и каждый его элемент вставляем в дерево двоичного поиска
        System.out.println("Порядок вставки элементов в двоичное дерево поиска: ");
        for (Integer it : set){
            System.out.print(it + " ");
            tree.insert(it);
        }
        System.out.println("\n");

        //Симметричный обход
        tree.getInOrder();

        //Делаем прошивку
        tree.makeSymmetricallyThreaded();

        //Выводим после прошивки
        tree.threadedInOrderTraverse();
        tree.threadedInOrderTraverseReverse();

        //Вставка ключей в прошитое
        tree.insertToSymmetricTree(-1);
        tree.insertToSymmetricTree(-52);
        tree.insertToSymmetricTree(-25);
        tree.insertToSymmetricTree(12);
        tree.insertToSymmetricTree(17);
        tree.insertToSymmetricTree(13);
        tree.insertToSymmetricTree(15);
        tree.insertToSymmetricTree(19);
        tree.insertToSymmetricTree(41);
        tree.insertToSymmetricTree(-96);

        System.out.println("\n");
        System.out.println("После вставки новых ключей:");
        tree.threadedInOrderTraverse();
        System.out.println("\n");

        //Попытки удалить несуществующие ключи
        /*System.out.println("Попытки удалить несуществующие ключи:");
        tree.deleteFromSymmetricTree(-1000000);
        tree.deleteFromSymmetricTree(1000000);*/


        //Удаляем первые 5 эл-тов из тех, которые вставили
        int N = set.size() / 2;
        for (Integer it : set){
            tree.deleteFromSymmetricTree(it);
            N--;
            if (N == 0) break;
        }

        System.out.println("\n");
        //После удаления
        System.out.println("После удаления:");
        tree.threadedInOrderTraverse();
    }
}