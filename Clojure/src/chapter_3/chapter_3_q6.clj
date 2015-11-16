(ns chapter-3.chapter-3-q6
  (:require [data-structures.stack :refer :all])
  (import java.util.LinkedList))

(defrecord Animal [type age])

(defprotocol AnimalShelter
  (enqueue [this type])
  (dequeue-any [this])
  (dequeue-dog [this])
  (dequeue-cat [this]))

(defrecord AnimalShelterQueues [dog-q cat-q]
  AnimalShelter

  (enqueue [this type]
    (let [animal (->Animal type (+ (.size dog-q) (.size cat-q)))]
      (if (= type :cat)
        (.offer cat-q animal)
        (.offer dog-q animal)
        )))

  (dequeue-any [this]
    (if (or (.isEmpty dog-q) (.isEmpty cat-q))
      (if (.isEmpty dog-q)
        (.poll cat-q)
        (.poll dog-q))
      (let [oldest-dog (.peek dog-q)
            oldest-cat (.peek cat-q)]
        (if (< (:age oldest-dog) (:age oldest-cat))
          (.poll dog-q)
          (.poll cat-q)))))

  (dequeue-dog [this]
    (.poll dog-q))

  (dequeue-cat [this]
    (.poll cat-q)))

(defn create-animal-shelter []
  (->AnimalShelterQueues (new LinkedList) (new LinkedList)))