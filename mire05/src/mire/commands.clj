(ns mire.commands
  (:use [mire rooms])
  (:use [clojure.contrib str-utils seq-utils]))

;; Command functions

(defn look "Get a description of the surrounding environs and its contents."
  []
  (str (:desc @*current-room*)
       "\nExits: " (keys (:exits @*current-room*))
       ".\n"))

(defn move
  "\"♬ We gotta get out of this place... ♪\" Give a direction."
  [direction]
  (dosync
   (let [target-name ((:exits @*current-room*) (keyword direction))
         target (rooms target-name)]
     (if target
       (do (alter (:inhabitants @*current-room*) disj player-name)
           (alter (:inhabitants target) conj player-name)
           (ref-set *current-room* target)
           (look))
       "You can't go that way."))))

;; Command
(defn players-here
      "Players in current room"
      []
      (let [players @(:inhabitants @*current-room*)]
           (if (<= (count players) 1)
             "All I needed was the last thing I wanted \n To be alone in a room, alone in a room"
             (str "Players in this room: " (apply str (interpose ", " players))))))
(def commands {"move" move,
               "north" (fn [] (move :north)),
               "south" (fn [] (move :south)),
               "east" (fn [] (move :east)),
               "west" (fn [] (move :west)),
               "look" look,
               "players-here" players-here})

;; Command handling

(defn execute
  "Execute a command that is passed to us."
  [input]
  (let [input-words (re-split #"\s+" input)
        command (first input-words)
        args (rest input-words)]
    (apply (commands command) args)))