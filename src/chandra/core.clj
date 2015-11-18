(ns chandra.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [clojure.string :as str])
  (:use [chandra.cli])
  (:gen-class))

(defn -main [& args]
;  (print args)
;  (print (parse-opts args cli-options))
  (cli-start args)
  )


