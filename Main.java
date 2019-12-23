package com.company;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        int[][] timeToImmun = new int[22][22]; // хранит для каждой клетки время до наступления иммунитета
        int[][] timeToLossImunn = new int[22][22]; // хранит для каждой клетки время до потери иммунитета
        int[][] skin = new int[22][22];           // это и есть клетки
        int[][] skinDublicate = skin.clone();     // дубликат клеток
        Random myRandom = new Random();
        boolean infected;                         // с вероятностью 0.5 выясняет, будет ли заражена клетка

        for (int i = 0; i < 22; i++)           // заполняем края пятеркам(любой другой цифрой кроме 0,1,2)
            for (int j = 0; j < 22; j++)       // чтобы не было обращения к несуществующим элементам массива
                skin[i][j] = 5;

        for (int i = 1; i < 21; i++)
            for (int j = 1; j < 21; j++)
                skin[i][j] = 0;

            skin[5][8] = 1;                    // зараженные клетки из условия и время до наступления иммунитета
             timeToImmun[5][8] = 6;
            skin[12][15] = 1;
             timeToImmun[12][15] = 6;
            skin [3][7] = 1;
             timeToImmun[3][7] = 6;

     /*        for (int i = 0; i < 22; i++) {  // вывод всех клеток
            for (int j = 0; j < 22; j++)
                System.out.print(skin[i][j]);
            System.out.println();
        }

      */
            int a = 0; // переменные отвечающие за сдвиг индексов i и j
            int b = 0;
            int k = 0; // переменная для выхода из while при зацикливании
            int time = 0;
            int count = 0;   // кол-во зараженных клеток
            boolean allInfected = false;   // все ли клетки заражены

        while(!allInfected) {
            time++;
            for (int i = 1; i < 21; i++)             // для все клеток проверяем
                for (int j = 1; j < 21; j++)
                    if (skin[i][j] == 1) {            // если она заражена
                        timeToImmun[i][j]--;           // то уменьшается время до наступления иммунитета
                         if (timeToImmun[i][j] == 0 ) {     // если иммунитет настал
                             skinDublicate[i][j] = 2;       // то клетке значение 2
                             timeToLossImunn[i][j] = 4;      // и время до восстановления = 4
                         }
                        infected = myRandom.nextBoolean();         // вероятность заражения = 0.5
                         if (infected) {
                             k = 0;
                             while (((a == 0) && (b == 0)) || (skin[i+a][j+b]!=0)) { // пока сдвиг обоих индексов одновременно равен 0
                                                                                  // либо пока не найдется здоровая клетка
                                 a = (int) (Math.random()*3) - 1;           // генерируем сдвиг индекса i
                                 b = (int) (Math.random()*3) - 1;           // генерируем сдвиг индекса j
                                 k++;
                                 if (k == 100) break;       // если зациклилось, то выходим из цикла
                             }
                             /* System.out.println("Time " + time);     // вывод времени и координаты зараженной соседней клетки
                             System.out.println("i "+ (i+a) + "  " + "j " + (j+b)); */
                             if (k != 100) {                            // если была найдена здоровая соседняя клетка
                                 skinDublicate[i + a][j + b] = 1;        // заражаем её
                                 timeToImmun[i+a][j+b] = 6;              // и устанавливаем ей время до наступления иммунитета
                             }
                         }
                    } else if (skin[i][j] == 2) {                     // если клетка имеет иммунитет
                        timeToLossImunn[i][j]--;                      // уменьшаем время до окончания иммунитета
                         if (timeToLossImunn[i][j] == 0)              // если иммунитет окончился
                             skinDublicate[i][j] = 0;                 // то делаем клетку здоровой
                    }

            count = 0;
            skin = skinDublicate.clone();                      // копируем дубликат в основной
            System.out.println("Состояние кожи после " + time + " единицы времени");
            for (int i = 1; i < 21; i++) {  // вывод всех клеток
                for (int j = 1; j < 21; j++) {
                    System.out.print(skin[i][j]);
                    if (skin[i][j] == 1) count++;   // подсчет количества
                }
                System.out.println();
            }
            if (count == 400)                       // и проверка на то, что все клетки заражены
                allInfected = true;                 // если всё заражены , то цикл while не запустится
        }
    }
}
