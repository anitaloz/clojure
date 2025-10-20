(ns mire.rooms
    (:use [clojure.contrib str-utils]))
;; Объявление пространства имен mire.rooms
;; Подключение библиотеки str-utils для работы со строками

(def rooms
  {:start {:desc "You find yourself in a round room with a pillar in the middle."
           :exits {:north :closet}}
   :closet {:desc "You are in a cramped closet."
            :exits {:south :start}}})
;; Определение карты (map) комнат игры
;; rooms - это хэш-мап, где ключи - имена комнат, значения - их описания
;; :start - начальная комната с описанием и выходом на север в :closet
;; :closet - комната-кладовка с описанием и выходом на юг в :start

(def *current-room*)
;; Объявление динамической переменной для хранения текущей комнаты игрока
;; Эта переменная будет связана (bound) при подключении каждого клиента
;; для отслеживания их текущего местоположения в игровом мире