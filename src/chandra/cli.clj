(ns chandra.cli
  (:require [clojure.tools.cli :refer [parse-opts]]
            [clojure.string :as str]
            [korma.db :as db]
            [korma.core :as dbcore]
            [puget.printer :refer :all]
            )
  (:use [chandra.commandmodel])
  (:gen-class)
  )

(def cli-options
  ;; An option with a required argument
  [["-p" "--port PORT" "Port number"
    :default 80
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
   ;; A non-idempotent option
   ["-v" nil "Verbosity level"
    :id :verbosity :default 0
    :assoc-fn (fn [m k _] (update-in m [k] inc))]
   ;; A boolean option defaulting to nil
   ["-h" "--help"]])

(defn read-stdin []
  (line-seq (java.io.BufferedReader. *in*)))

(defn ask-line [f]
  (do
    (flush)
    (read-stdin)
    ))

(defn cli-parser [i]
  (map first (re-seq #"/\s*(\".+?\"|[^:\s])+((\s?:\s*(\".+?\"|[^\s])+)|)|(\".+?\"|[^\"\s])+" i)))

(declare command command-help)

(defn cli-start [args]
  ;  (print (parse-opts args cli-options))
  ;  (print args)
  (print "\n# ") (flush)
  (loop [i (read-line) ret []]
    (let [p (cli-parser i)
          c (parse-opts p cli-options)] ;(seq (str/split i #" "))
      (if (= i "quit")
        ret
        (do
          (print p)
          (command p c)
          (print "\n# ") (flush)
          (recur (read-line) (conj ret i))))
      )
    )
  )

(defn command [p c]
  ;  (let [
  (let [cm (str (first p))]
    (println "COMMAND: " cm)
    (cond
      (= cm "help") (command-help p c)
      (= cm "model") (command-model p c)
      :else (do
              (pprint "UNKOWN COMMAND!")))
    )
  )

(defn command-help [p c]
  (println "HELP!")
  )
