
Лабараторная работа №3 (JavaFx).

Выпонила: Зубрицкая В.Г., группа 821701.
------------------------------
Вариант 25
а) f(x)=5x + 3
б) f(x) задается по следующему правилу: программой генерируются одномерные числовые массивы размером от 2 до n элементов. К каждому массиву применяется алгоритм сортировки естественного слияния. В таблицу заносятся количество элементов и среднее время сортировки массива. Для получение среднего времени необходимо отсортировать k массивов одного размера. Шаг увеличения размера массива 1. Под шагом понимается, что сначала программа отсортирует k массивов размером 2, затем k массивов размером 3, и т.д.
------------------------------
Главное окно приложения:
-слева: форма для задания параметров функции с двумя кнопками(start & stop) и таблица вычисленых значений функции б.
-справа: график для функциий а и б, кнопки для увеличения/уменьшения масштаба.
Компонент для рисования графиков должен обладать следующими возможностями:
1)	Компонент должен поддерживать рисование нескольких графиков одновременно. Обновление графика происходит после вычисления каждой последующей точки. Например, вычислилось 1-е значение функции - появилось на графике, вычислилось второе значение функции - появилось на графике и т.д.
2)	Компонент должен рисовать оси координат с подписями на стрелках
3)	Подписями должны быть отмечены начало координат и деления на осях.
4)	Если график не помещается на компонент, то должны появляться вертикальные и горизонтальные полосы прокрутки. 
5)	Компонент должен иметь две кнопки позволяющие увеличивать или уменьшать масштаб графика. Также масштаб можно изменить при помощи зажатой клавиши Ctrl и крутить колесо мыши на графике.
6)	При зажатой на графике только левой клавише мыши и ее перетягивании должен происходить сдвиг отображаемой области графика.
7)	Под графиком располагаться группа элементов, которая показывает текущий режим отображения графика. Единичный отрезок, текущий масштаб. Цветовая полоска с некоторым описанием функции. 
