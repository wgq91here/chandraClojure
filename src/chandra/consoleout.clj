(ns chandra.consoleout
  (:require [clojure.string :as str]
            [puget.printer :as puget-printer]
            )
  (:gen-class)
  )

(defn cprint
  ([value]
    (print (puget-printer/cprint-str value nil)))
  ([value opts]
    (print (puget-printer/pprint value (assoc opts :print-color true))))
  )
