(ns chandra.consoleout
  (:require [clojure.string :as str]
            [puget.printer :as puget-printer]
            )
  (:gen-class)
  )

(defn cprint
  ([value]
    (puget-printer/cprint value nil))
  ([value opts]
    (puget-printer/pprint value (assoc opts :print-color true)))
  )
