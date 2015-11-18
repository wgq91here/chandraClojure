(ns chandra.commandmodel
  (:require [clojure.string :as str]
            [korma.db :as db]
            [korma.core :as dbcore]
            [chandra.sdb :as sdb]
            [puget.printer :refer :all]
            )
  ;  (:use )
  (:gen-class)
  )

(declare command-help-model model-list)

(defn commmand-help-model [p]
  (let [action (str (second p))]
    (cond
      (= p "list") (do
                     (prn "list help"))
      (= p "create") (do
                       (prn "create help"))
      :else (do
              (prn "list create update delete remove")))
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
  (cprint p (count p))
  (let [a (second p)]
    (cond
      (= a "help") (prn (commmand-help-model p c))
      (= a "list") (prn (model-list p c))
      :else (prn "UNKOWN ACTION. use `model help`."))
    )
  )

(defn model-list [p c]
  (let [tbname (str (nth p 2 ""))]
    (if (empty? tbname)
      "not a valid table name."
      (let [tb (sdb/d-table tbname)
            x (dbcore/select tb)]
        (do
          (cprint tb)
          (cprint x)))
      )))