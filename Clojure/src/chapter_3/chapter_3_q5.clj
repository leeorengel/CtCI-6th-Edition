(ns chapter-3.chapter-3-q5
  (:require [data-structures.stack :refer :all])
  (:import (data_structures.stack SimpleStack)))

;; Algorithm: O(n^2)
;; find minimum element, the sort is done when the minimum element is on the top of initial stack
;; 1. perform compare-and-swap based on having min as top, then pop that min off and put on temp stack
;; 2. keep going until you have one element left in the stack (this is the max element)
;; 3. now perform the opposite operation on the temp stack using max element
;; 4. if the initial stack has the minimum element at top then stop

(defn- compare-and-swap
  ([^SimpleStack stack cmp-fn]
   (compare-and-swap stack (stack-pop stack) cmp-fn))
  ([^SimpleStack stack x cmp-fn]
   "Adds x, and compares x to stack's top and performs a swap such that top is in the result of applying the comparison function
   cmp-fn"
   (if (zero? (stack-size stack))
     (stack-push stack x)
     (if (cmp-fn (stack-peek stack) x)
       (let [old-top-data (stack-pop stack)]
         (stack-push stack x)
         (stack-push stack old-top-data))
       (stack-push stack x)))))

(defn- swap-pass [^SimpleStack s1 ^SimpleStack s2 cmp-fn]
  "Performs a compare-and-swap pass on s1 using cmp-fn and returns the min element based on comp-fn"
  (loop [min nil]
    (if (stack-empty? s1)
      min
      (do (compare-and-swap s1 cmp-fn)
          (stack-push s2 (stack-pop s1))
          (let [new-min (if (nil? min)
                          (stack-peek s2)
                          (if (<= min (stack-peek s2))
                            min
                            (stack-peek s2)))]
            (compare-and-swap s2 (complement cmp-fn))
            (recur new-min))))))

(defn sort-stack [^SimpleStack stack]
  (let [temp (create-stack)]
    (loop [min nil
           s stack
           t temp
           cmp-fn <]
      (if (and (some? min) (pos? (stack-size stack)) (= min (stack-peek stack)))
        stack
        (let [new-min (swap-pass s t cmp-fn)]
          (recur
            (if (nil? min) new-min min)
            t
            s
            (partial (complement cmp-fn))))))))