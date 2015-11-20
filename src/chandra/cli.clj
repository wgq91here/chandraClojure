(ns chandra.cli
  (:require [clojure.tools.cli :refer [parse-opts]]
            [clojure.string :as str]
            [korma.db :as db]
            [korma.core :as dbcore]
            )
  (:use [chandra.model]
        [chandra.out])
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


(def ret-type
  {:error nil,
   :value nil})

(declare command merge-out command-help)

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
          (let [r (command p c)]
            (if (not (empty? r))
              (cprint r)))
          (print "# ") (flush)
          (recur (read-line) (conj ret i))))
      )
    )
  )

(defn command-help [p c]
  {:value "HELP!"})

(defn merge-out [v]
  (merge ret-type v))

(defn command [p c]
  ;  (let [
  (let [cm (str (first p))]
    ;    (print (str "COMMAND: " cm "\n"))
    (cond
      (empty? cm) {}
      (= cm "help") (merge-out (command-help p c))
      (= cm "model") (merge-out (command-model p c))
      :else (merge-out {:value "UNKOWN COMMAND!"}))
    )
  )
