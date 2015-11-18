(ns chandra.commandmodel
  (:require [clojure.string :as str]
            [korma.db :as db]
            [korma.core :as dbcore]
            [chandra.sdb :as sdb]
            [chandra.consoleout :refer :all]
            )
  ;  (:use )
  (:gen-class)
  )

(declare command-help-model model-list)

(defn commmand-help-model [p c]
  (let [a (str (nth p 2 ""))]
    (cond
      (= a "list") "list help"
      (= a "create") "create help"
      :else "list create update delete remove")
    ))

(defn command-model [p c]
  ;  (let [tb (str (second p))]
  ;    (println "MODEL: " tb)
  ;    (let [md (sdb/dy-create-table-entity tb)]
  ;      (println "MODEL ATTR: " md)
  ;      )
  ;    )
  ;  (prn (symbol (second p)))
  ;  model [help [list {model args}]|[schema delete {model}]|[create update remove {model attrs}]
  ;
  ;  model help list
  ;  model show [models]
  ;  model select user
  ;  model create user name:string ago:number
;  (prn p)
  (let [a (second p)]
    (cond
      (= a "help") (print (commmand-help-model p c))
      (= a "list") (cprint (model-list p c))
      :else (print "UNKOWN ACTION. use `model help`."))
    )
  )

(defn model-list [p c]
  (let [tbname (str (nth p 2 ""))]
    (if (empty? tbname)
      "not a valid table name."
      (let [tb (sdb/d-table tbname)
            x (dbcore/select tb)]
        x))
    ))