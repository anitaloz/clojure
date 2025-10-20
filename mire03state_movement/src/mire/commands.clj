(ns mire.commands ; Объявление пространства имен "mire.commands"
    (:use [mire rooms util]) ; Импорт модулей rooms и util из того же проекта
    (:use [clojure.contrib str-utils seq-utils])) ; Импорт библиотек для работы со строками и последовательностями

;; Command functions

(defn look "Get a description of the surrounding environs and its contents." ; Функция look с документацией
      [] ; Функция без параметров
      (str (:desc *current-room*) ; Получение описания текущей комнаты и преобразование в строку
           "\nExits: " (keys (:exits *current-room*)) ; Получение списка выходов из комнаты
           ".\n")) ; Завершение строки описания

(defn move ; Функция для перемещения между комнатами
      "\"♬ We gotta get out of this place... ♪\" Give a direction." ; Документация с музыкальной ссылкой
      [direction] ; Параметр - направление движения
      (let [target-name ((:exits *current-room*) (keyword direction)) ; Получение имени целевой комнаты по направлению
            target (rooms target-name)] ; Получение объекта целевой комнаты по имени
           (if target ; Проверка существует ли целевая комната
             (do (set-current-room target) ; Установка текущей комнаты в целевую
                 (look)) ; Вывод описания новой комнаты
             "You can't go that way."))) ; Сообщение об ошибке если направления нет

;; Command data

(def commands {"move" move, ; Словарь команд: команда "move" связана с функцией move
               "north" (fn [] (move :north)), ; Команда "north" вызывает move с направлением :north
               "south" (fn [] (move :south)), ; Команда "south" вызывает move с направлением :south
               "east" (fn [] (move :east)), ; Команда "east" вызывает move с направлением :east
               "west" (fn [] (move :west)), ; Команда "west" вызывает move с направлением :west
               "look" look}) ; Команда "look" связана с функцией look

;; Command handling

(defn execute ; Функция для выполнения команд
      "Execute a command that is passed to us." ; Документация функции
      [input] ; Параметр - входная строка от пользователя
      (let [input-words (re-split #"\s+" input) ; Разбивка входной строки на слова по пробелам
            command (first input-words) ; Первое слово - команда
            args (rest input-words)] ; Остальные слова - аргументы
           (apply (commands command) args))) ; Поиск команды в словаре и применение с аргументами