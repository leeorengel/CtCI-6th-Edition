(ns chapter-3.chapter-3-q3-test
  (:require [clojure.test :refer :all]
            [data-structures.stack :refer :all]
            [chapter-3.chapter-3-q3 :refer :all]))

(deftest stack-set-test
  (testing "threshold 1"
    (let [stack-set (create-stack-set :A 1)]
      (is (not (stack-set-empty? stack-set)))
      (stack-set-push stack-set :B)
      (is (= :B (stack-set-peek stack-set)))
      (= 2 (stack-set-size stack-set))
      (stack-set-push stack-set :C)
      (= 3 (stack-set-size stack-set))
      (is (= :C (stack-set-pop stack-set)))
      (= 2 (stack-set-size stack-set))
      (is (= :B (stack-set-peek stack-set)))
      (is (= :B (stack-set-pop stack-set)))
      (= 1 (stack-set-size stack-set))
      (is (= :A (stack-set-pop stack-set)))
      (= 0 (stack-set-size stack-set))
      (stack-set-push stack-set :D)
      (= 1 (stack-set-size stack-set))
      (is (= :D (stack-set-peek stack-set)))
      ))

  (testing "threshold 2"
    (let [stack-set (create-stack-set :A 2)]
      (is (not (stack-set-empty? stack-set)))
      (stack-set-push stack-set :B)
      (is (= :B (stack-set-peek stack-set)))
      (= 1 (stack-set-size stack-set))
      (= 2 (stack-total-size stack-set))
      (stack-set-push stack-set :C)
      (= 2 (stack-set-size stack-set))
      (= 3 (stack-total-size stack-set))
      (is (= :C (stack-set-pop stack-set)))
      (= 1 (stack-set-size stack-set))
      (= 2 (stack-total-size stack-set))
      (is (= :B (stack-set-peek stack-set)))
      (is (= :B (stack-set-pop stack-set)))
      (= 1 (stack-total-size stack-set))
      (= 1 (stack-set-size stack-set))
      (is (= :A (stack-set-pop stack-set)))
      (= 0 (stack-set-size stack-set))
      (= 0 (stack-total-size stack-set))
      (is (stack-set-empty? stack-set))
      (stack-set-push stack-set :D)
      (= 1 (stack-set-size stack-set))
      (is (= :D (stack-set-peek stack-set)))
      ))

  (testing "threshold 3"
    (let [stack-set (create-stack-set :A 3)]
      (is (not (stack-set-empty? stack-set)))
      (stack-set-push stack-set :B)
      (= 1 (stack-set-size stack-set))
      (= 2 (stack-total-size stack-set))
      (stack-set-push stack-set :C)
      (= 1 (stack-set-size stack-set))
      (= 3 (stack-total-size stack-set))
      (stack-set-push stack-set :D)
      (= 2 (stack-set-size stack-set))
      (= 4 (stack-total-size stack-set))
      (stack-set-push stack-set :E)
      (= 2 (stack-set-size stack-set))
      (= 5 (stack-total-size stack-set))
      (stack-set-push stack-set :F)
      (= 2 (stack-set-size stack-set))
      (= 6 (stack-total-size stack-set))
      (stack-set-push stack-set :G)
      (= 3 (stack-set-size stack-set))
      (= 7 (stack-total-size stack-set))

      (is (= :G (stack-set-pop stack-set)))
      (= 2 (stack-set-size stack-set)))))

(run-tests)