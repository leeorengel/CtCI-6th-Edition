(ns chapter-3.chapter-3-q6
  (:require [data-structures.stack :refer :all])
  (import java.util.LinkedList))

;; Algorithm:
;; - worst-case dequeueAny O(1)
;; - worst-case dequeueDog or Cat is O(n)
;; use 3 LinkedList instances
;; A - queue of all animals
;; B - pop queue of 1st animal type to be popped
;; C - pop queue of 2nd animal type to be popped
;; pop-order is B, C, A
;; maintain oldest dog and cat indices in A queue
;; this optimizes for worst case of oldest type being at end of queue
;; because you pop if off the end instead of the beginning
;; you also need a overflow stack for the last case

(defrecord Animal [type age])

(defprotocol AnimalShelter
  (enqueue [this type])
  (dequeue-any [this])
  (dequeue-dog [this])
  (dequeue-cat [this]))

;; TODO - Optimization keep track of oldest animal of each type and dequeue from opposite side
;; if its closer

(defn- get-oldest [shelter type]
  "Searches animal-q for oldest of provided type and returns it. All previous elements
  are dequeued and pushed onto a stack for their respective type."
  (let [animal-q (:animal-q shelter)
        type-overflow-q (:type-overflow-q shelter)]
    (loop [next-animal (.poll animal-q)]
      (if (nil? next-animal)
        nil
        (if (= type (:type next-animal))
          next-animal
          (do (.offer type-overflow-q next-animal)
              (recur (.poll animal-q))))))))

(defn- dequeue-type [shelter type]
  (let [type-overflow-q (:type-overflow-q shelter)]
    (cond (= type (:type (.peek type-overflow-q))) (.poll type-overflow-q)
          :else (get-oldest shelter type))))

(defrecord AnimalShelterQueues [animal-q type-overflow-q temp-q]
  AnimalShelter

  (enqueue [this type]
    (let [animal (->Animal type (.size animal-q))]
      (.offer animal-q animal)))

  (dequeue-any [this]
    (cond (not (.isEmpty type-overflow-q)) (.poll type-overflow-q)
          :else (.poll animal-q)))

  (dequeue-dog [this]
    (dequeue-type this :dog))

  (dequeue-cat [this]
    (dequeue-type this :cat)))

(defn create-animal-shelter []
  (->AnimalShelterQueues (new LinkedList) (new LinkedList) (new LinkedList)))