(ns mire.commands
    (:use [mire rooms util])
    (:use [clojure.contrib str-utils seq-utils]))
;; Объявление пространства имен mire.commands
;; Подключение модулей rooms и util из текущего проекта
;; Подключение библиотек для работы со строками и последовательностями

;; Command functions

(defn look "Get a description of the surrounding environs and its contents."
      []
      (str (:desc *current-room*)
           "\nExits: " (keys (:exits *current-room*))
           ".\n"))
;; Функция look - показывает описание текущей комнаты и доступные выходы
;; Возвращает строку с описанием комнаты и списком направлений выходов

(defn move
      "\"♬ We gotta get out of this place... ♪\" Give a direction."
      [direction]
      (let [target-name ((:exits *current-room*) (keyword direction))
            target (rooms target-name)]
           ;; Получаем имя целевой комнаты из exits текущей комнаты по направлению
           ;; Затем получаем саму комнату из глобальной карты rooms
           (if target
             (do (set! *current-room* target)
                 (look))
             "You can't go that way.")))
;; Функция move - перемещение в указанном направлении
;; Если выход существует - меняет текущую комнату и показывает новую
;; Если выхода нет - возвращает сообщение об ошибке

;; Command data

(def commands {"move" move,
               "north" (fn [] (move :north)),
               "south" (fn [] (move :south)),
               "east" (fn [] (move :east)),
               "west" (fn [] (move :west)),
               "look" look})
;; Карта команд, доступных игроку:
;; - "move" - функция move с аргументом
;; - направления (north/south/east/west) - анонимные функции, вызывающие move с соответствующим направлением
;; - "look" - функция look

;; Command handling

(defn execute
      "Execute a command that is passed to us."
      [input]
      (let [input-words (re-split #"\s+" input)
            command (first input-words)
            args (rest input-words)]
           ;; Разбиваем входную строку на слова по пробелам
           ;; Первое слово - команда, остальные - аргументы
           (apply (commands command) args)))
;; Функция execute - выполняет команду, введенную пользователем
;; Ищет команду в карте commands и применяет ее к аргументам