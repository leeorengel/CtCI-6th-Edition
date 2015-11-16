(ns chapter-3.chapter-3-q6-test
  (:require [clojure.test :refer :all]
            [data-structures.stack :refer :all]
            [chapter-3.chapter-3-q6 :refer :all]))


(deftest animal-shelter-test
  (testing "basic tests"
    (let [shelter (create-animal-shelter)]
      (enqueue shelter :cat)
      (is (= (->Animal :cat 0) (dequeue-any shelter)))
      (enqueue shelter :cat)
      (is (= (->Animal :cat 0) (dequeue-cat shelter)))
      (enqueue shelter :dog)
      (is (= (->Animal :dog 0) (dequeue-any shelter)))
      (enqueue shelter :dog)
      (is (= (->Animal :dog 0) (dequeue-dog shelter)))

      (enqueue shelter :dog)
      (enqueue shelter :cat)

      (is (= (->Animal :dog 0) (dequeue-any shelter)))
      (is (= (->Animal :cat 1) (dequeue-any shelter)))

      (enqueue shelter :dog)
      (enqueue shelter :cat)
      (is (= (->Animal :dog 0) (dequeue-dog shelter)))
      (is (= (->Animal :cat 1) (dequeue-cat shelter)))

      (enqueue shelter :dog)
      (enqueue shelter :dog)
      (enqueue shelter :cat)
      (enqueue shelter :cat)
      (enqueue shelter :cat)

      (is (= (->Animal :dog 0) (dequeue-dog shelter)))
      (is (= (->Animal :dog 1) (dequeue-any shelter)))
      (is (= (->Animal :cat 2) (dequeue-cat shelter)))
      (is (= (->Animal :cat 3) (dequeue-any shelter)))
      (is (= (->Animal :cat 4) (dequeue-any shelter)))))

  (testing "Oldest cat, rest dogs"
    (let [shelter (create-animal-shelter)]
      (enqueue shelter :cat)
      (enqueue shelter :dog)
      (enqueue shelter :dog)
      (enqueue shelter :dog)

      (is (= (->Animal :cat 0) (dequeue-cat shelter)))
      (is (= (->Animal :dog 1) (dequeue-dog shelter)))
      (is (= (->Animal :dog 2) (dequeue-any shelter)))
      (is (= nil (dequeue-cat shelter)))
      (is (= (->Animal :dog 3) (dequeue-any shelter)))
      (is (= nil (dequeue-dog shelter)))
      (is (= nil (dequeue-any shelter)))
      ))

  (testing "Using overflow stacks"
    (let [shelter (create-animal-shelter)]
      (enqueue shelter :cat)
      (enqueue shelter :dog)
      (enqueue shelter :dog)
      (enqueue shelter :cat)
      (enqueue shelter :cat)
      (enqueue shelter :dog)
      (enqueue shelter :cat)
      (enqueue shelter :dog)

      (is (= (->Animal :dog 1) (dequeue-dog shelter)))
      ;; cat 0 is now on overflow queue
      (enqueue shelter :cat)
      (is (= (->Animal :dog 2) (dequeue-dog shelter)))

      (enqueue shelter :dog)
      (enqueue shelter :dog)

      (is (= (->Animal :cat 0) (dequeue-cat shelter)))
      (is (= (->Animal :cat 3) (dequeue-cat shelter))))))

(run-tests)
