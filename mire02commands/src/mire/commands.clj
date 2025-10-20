(ns mire.commands ; Объявление пространства имен "mire.commands"
    (:use [clojure.contrib str-utils])) ; Импорт библиотеки для работы со строками

(defn current-time [] ; Определение функции для получения текущего времени
      (str "It is now "(java.util.Date.))) ; Создание строки с текущей датой и временем

(def commands {"time" current-time, ; Создание словаря команд: ключ "time" связан с функцией current-time
               "look" (fn [] "You see an empty room, waiting to be filled.")}) ; Ключ "look" связан с анонимной функцией

(defn execute ; Определение функции execute для выполнения команд
      "Execute a command that is passed to us." ; Строка документации для функции
      [input] ; Параметр input - строка с вводом пользователя
      (let [input-words (re-split #"\s+" input) ; Разбивка входной строки на слова по пробелам
            command (first input-words) ; Извлечение первого слова как команды
            args (rest input-words)] ; Остальные слова как аргументы команды
           (apply (commands command) args))) ; Поиск команды в словаре и применение с аргументами