package com.company;

public class Tree {
    private Node root;                              //Корень дерева
    private Node head;                              //Голова дерева
    private final boolean symThreadingDone;         //Истина, когда дерево прошили

    //Конструктор для инициализации
    public Tree() {
        root = null;
        symThreadingDone = false;
        head = null;
    }

    //Вставка нового ключа в дерево
    public void insert(int key) {
        if (symThreadingDone) {
            System.out.println("Эта версия вставки более не поддерживается");
        } else {
            Node newNode = new Node(key);//Создаем новый узел
            if (root == null) {//если корень пустой, Делаем новый узел корневым
                root = newNode;
            } else {
                //Становимся в начало дерево
                Node curr = root, prev;

                while (true) {
                    //Запоминаем предыдущее положение
                    prev = curr;
                    //Если то, что мы хотим вставить, меньше того, что находится в текущем узле
                    if (key < curr.key) {
                        //То идем влево
                        curr = curr.left;
                        //Нашли место вставки - запоминаем ссылку
                        if (curr == null) {
                            prev.left = newNode;
                            return;
                        }
                    } else {
                        //Иначе идем вправо
                        curr = curr.right;
                        //Нашли место вставки - запоминаем ссылку
                        if (curr == null) {
                            prev.right = newNode;
                            return;
                        }
                    }
                }
            }
        }
    }

    public void deleteFromSymmetricTree(int key) {                                          //Основной метод для удаления. key - удаляемый ключ
        Node prev = null;                                                                   //Предыдущий узел (будем запоминать)
        Node curr = root;                                                                  //Становимся в корень дерева
        boolean found = false;                                                              //Пока узел не нашли

        while (curr != null) {
            if (key == curr.key) {
                found = true;
                break;
            }
            prev = curr;                                                                    //Запоминаем предыдущего
            if (key < curr.key) {                                                         //Идем в левое поддерево
                if (curr.hasLeftThread)
                    curr = curr.left;
                else
                    break;                                                                  //Останавливаемся, если ссылка на левое поддерево - НИТЬ
            } else {
                if (curr.hasRightThread)
                    curr = curr.right;
                else
                    break;                                                                  //Останавливаемся, если ссылка на правое поддерево - НИТЬ
            }
        }

        if (!found)
            System.out.println("Такого ключа нет в дереве!");                               //Такого ключа нет
        else if (curr.hasLeftThread && curr.hasRightThread)
            deleteNodeWithTwoChildren(prev, curr);                                          //Удаляем узел, у которого два потомка
        else if (curr.hasLeftThread)
            deleteNodeWithOneChild(prev, curr);                                             //Удаляем узел, у которого один потомок (левый)
        else if (curr.hasRightThread)
            deleteNodeWithOneChild(prev, curr);                                             //Удаляем узел, у которого один потомок (правый)
        else
            deleteLeaf(prev, curr);                                                         //Удаляем узел-лист
    }

    private void deleteLeaf(Node prev, Node curr) {                                          //Метод для удаления узла-листа
        if (prev == null)                                                                   //Удаляемый узел - корень
            root = null;
        else if (curr == prev.left) {                                                       //Удаляемый узел является левым потомком своего родителя
            prev.hasLeftThread = false;
            prev.left = curr.left;                                                          //Переносим ссылку. Теперь родитель будет ссылаться на голову / узел со следующим меньшим ключом
        } else {
            prev.hasRightThread = false;
            prev.right = curr.right;                                                        //Переносим ссылку. Теперь родитель будет ссылаться на голову / узел со следующим большим ключом
        }
    }

    private void deleteNodeWithOneChild(Node prev, Node curr) {                              //Метод для удаления узла с одним потомком
        Node child;

        if (curr.hasLeftThread)
            child = curr.left;                                                              //Потомок у удаляемого узла - левый
        else
            child = curr.right;                                                             //Потомок у удаляемого узла - правый

        if (prev == null)                                                                   //Переносим корень дерева на единственного потомка
            root = child;
        else if (curr == prev.left)                                                         //Переносим потомка под ответственность родителю
            prev.left = child;
        else
            prev.right = child;

        Node s = inSuccessor(curr);                                                         //Находим преемника (узел со следующим бОльшим ключом)
        Node p = inPredecessor(curr);                                                       //Находим предшественника (узел со следующим мЕньшим ключом)

        if (curr.hasLeftThread)                                                              //Меняем ссылки
            p.right = s;
        else {
            if (curr.hasRightThread)
                s.left = p;
        }
    }

    private void deleteNodeWithTwoChildren(Node prev, Node curr) {                           //Метод для удаления узла с двумя потомками
        Node prevSucc = curr;                                                               //Запоминаем предыдущего
        Node succ = curr.right;                                                             //Наш преемник

        while (succ.hasLeftThread) {                                                         //Покуда у преемника актуальная ссылка на
            prevSucc = succ;                                                                //левое поддерево, идём влево
            succ = succ.left;
        }
        curr.key = succ.key;                                                              //Заменяем ключ
        if (!succ.hasLeftThread && !succ.hasRightThread)                                       //Преемник - лист. Удаляем его как лист
            deleteLeaf(prevSucc, succ);
        else
            deleteNodeWithOneChild(prevSucc, succ);                                         //У преемника есть хоть один потомок (другого не дано). Удаляем надлежащим образом
    }

    private Node inSuccessor(Node ptr) {                                                    //Метод для поиска преемника
        if (!ptr.hasRightThread)                                                              //Правая ссылка - это нить. Возвращаем её сразу
            return ptr.right;

        ptr = ptr.right;                                                                    //Ищем подходящую ссылку
        while (ptr.hasLeftThread)
            ptr = ptr.left;
        return ptr;
    }

    private Node inPredecessor(Node ptr) {                                                  //Метод для поиска предшественника
        if (!ptr.hasLeftThread)                                                              //Левая ссылка - это нить. Возвращаем её сразу
            return ptr.left;

        ptr = ptr.left;                                                                     //Ищем подходящую ссылку
        while (ptr.hasRightThread)
            ptr = ptr.right;
        return ptr;
    }


    //Метод для вставки ключа в уже СИММЕТРИЧНО ПРОШИТОЕ ДЕРЕВО


    public void insertToSymmetricTree(int key) {
        Node curr = root;
        Node prev = null;
        while (curr != null) {
            prev = curr;
            if (key < curr.key) {
                if (curr.hasLeftThread)
                    curr = curr.left;
                else
                    break;
            } else {
                if (curr.hasRightThread)
                    curr = curr.right;
                else
                    break;
            }
        }

        Node newNode = new Node(key);
        newNode.hasLeftThread = false;
        newNode.hasRightThread = false;

        if (prev == null) {
            root = newNode;
            newNode.left = null;
            newNode.right = null;
        } else if (key < (prev.key)) {
            newNode.left = prev.left;
            newNode.right = prev;
            prev.hasLeftThread = true;
            prev.left = newNode;
        } else {
            newNode.left = prev;
            newNode.right = prev.right;
            prev.hasRightThread = true;
            prev.right = newNode;
        }
    }


    //Метод для начала прошивания обычного бинарного дерева поиска

    Node y;//Глобальный указатель

    public void makeSymmetricallyThreaded() {
        if (symThreadingDone) {
            System.out.println("Невозможно создать дерево с симметричным прошиванием! Оно уже имеет симметричное прошивание!");
        } else {
            head = new Node(-1);
            head.left = root;
            head.right = head;

            y = head;

            recursiveSymmetricalThreadingR(root);//Вызываем рекурсивный метод для ПРАВОСТОРОННЕЙ ПРОШИВКИ
            y.right = head;
            y.hasRightThread = false;//Самый крайний правый лист должен ссылаться на голову

            y = head;

            recursiveSymmetricalThreadingL(root);//Вызываем рекурсивный метод для ЛЕВОСТОРОННЕЙ ПРОШИВКИ
            y.left = head;
            y.hasLeftThread = false;//Самый крайний левый лист должен ссылаться на голову
        }
    }


    //Методы для ПРАВОСТОРОННЕЙ ПРОШИВКИ


    private void recursiveSymmetricalThreadingR(Node x) {
        if (x != null) {
            recursiveSymmetricalThreadingR(x.left);
            doSymmetricalThreadingR(x);
            recursiveSymmetricalThreadingR(x.right);
        }
    }

    private void doSymmetricalThreadingR(Node p) {
        if (y != null) {
            if (y.right == null) {
                y.hasRightThread = false;
                y.right = p;
            } else {
                y.hasRightThread = true;
            }
        }
        y = p;
    }



    //МЕТОДЫ ДЛЯ ЛЕВОСТОРОННЕЙ ПРОШИВКИ


    private void recursiveSymmetricalThreadingL(Node x) {
        if (x != null) {
            if (x.hasRightThread) recursiveSymmetricalThreadingL(x.right);
            doSymmetricalThreadingL(x);
            recursiveSymmetricalThreadingL(x.left);
        }
    }

    private void doSymmetricalThreadingL(Node p) {
        if (y != null) {
            if (y.left == null) {
                y.hasLeftThread = false;
                y.left = p;
            } else {
                y.hasLeftThread = true;
            }
        }
        y = p;
    }


    //МЕТОДЫ ДЛЯ ОБХОДА ПРОШИТОГО ДЕРЕВА В ПОРЯДКЕ ВОЗРАСТАНИЯ И УБЫВАНИЯ КЛЮЧЕЙ


    public void threadedInOrderTraverse() {
        Node curr = root;
        if (root == null) {
            System.out.println("Дерево пусто!");
            return;
        }
        System.out.println("Симметричный обход дерева при помощи метода прошивки:");
        while (curr != head) {
            while (curr.left != null && curr.hasLeftThread) curr = curr.left;
            curr.show();
            while (!curr.hasRightThread && curr.right != null) {
                curr = curr.right;
                if (curr == head) {
                    System.out.println();
                    return;
                }
                curr.show();
            }
            curr = curr.right;
        }
        System.out.println();
    }

    public void threadedInOrderTraverseReverse() {
        Node curr = root;
        if (root == null) {
            System.out.println("Дерево пусто!");
            return;
        }
        System.out.println("Симметричный обход дерева при помощи метода прошивки (по убыванию ключа):");
        while (curr != head) {
            while (curr.right != null && curr.hasRightThread) curr = curr.right;
            curr.show();
            while (!curr.hasLeftThread) {
                curr = curr.left;
                if (curr == head) {
                    System.out.println();
                    return;
                }
                curr.show();
            }
            curr = curr.left;
        }
        System.out.println();
    }


    //Рекурсивный симметричный обход дерева.
    private void inOrder(Node root) {
        if (root != null) {
            inOrder(root.left);
            root.show();
            inOrder(root.right);
        }
    }

    //Вспомогательный метод для вызова симмметричного обхода дерева.
    public void getInOrder() {
        if (root == null) {
            System.out.println("Дерево пусто!");
            return;
        }
        System.out.println("Симметричный обход дерева:");
        inOrder(root);
        System.out.println();
    }

}
