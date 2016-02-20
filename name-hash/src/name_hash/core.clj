(ns name-hash.core
  (:use [clojure.string :only [join split]]))

(let [alpha (into #{} (concat (map char (range (int \a) (inc (int \z))))
                              (map char (range (int \A) (inc (int \Z))))))
      ;; This next expression works because the implementation of the set, #{}, creates a set in which the alphabetic
      ;; are stored **in alphabetic order independent of case**. In other words, the set has the following first few
      ;; items: #{\A \a \B \b \C \c \D \d ...}. Since this set has 52 elements organized into pairs of corresponding
      ;; uppercase and lowercase letters. Dropping 26 items results in mapping the pairs in the first half of the
      ;; alphabet to the pairs in the second half of the alphabet.
      rot13-map (zipmap alpha (take 52 (drop 26 (cycle alpha))))]
  (defn rot13 
    "Given an input string, produce the rot-13 version of the string.
     \"hello\" -> \"uryyb\""
    [s]
    (apply str (map #(get rot13-map % %) s))))

(defn name-hash [name]
  (apply str (map #(rot13 %) (split name #"\d"))))

(def name-hash-m (memoize name-hash))
