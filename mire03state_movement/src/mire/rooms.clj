(ns mire.rooms ; Объявление пространства имен "mire.rooms"
    (:use [clojure.contrib str-utils])) ; Импорт библиотеки для работы со строками

(def rooms ; Определение словаря комнат
  {:start {:desc "You find yourself in a round room with a pillar in the middle." ; Комната :start с описанием
           :exits {:north :closet}} ; Выходы из :start - на север в :closet
   :closet {:desc "You are in a cramped closet." ; Комната :closet с описанием
            :exits {:south :start}}}) ; Выходы из :closet - на юг в :start

(def *current-room* (rooms :start)) ; Определение глобальной переменной текущей комнаты, инициализированной комнатой :start

(defn set-current-room [target] ; Определение функции для установки текущей комнаты
      (def *current-room* target)) ; Изменение значения глобальной переменной *current-room* на целевую комнату