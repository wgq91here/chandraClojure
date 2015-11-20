(ns chandra.out
  (:require [clojure.string :as str]
            [puget.printer :as puget-printer]
            )
  (:gen-class)
  )

(defn cprint
  ([value]
    (println (puget-printer/cprint-str value nil)))
  ([value opts]
    (println (puget-printer/pprint value (assoc opts :print-color true))))
  )
