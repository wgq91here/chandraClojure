(ns chandra.model
  (:require [clojure.string :as str]
            [korma.db :as db]
            [korma.core :as dbcore]
            [chandra.sdb :as sdb]
            )
  (:gen-class)
  )

(declare command-help-model model-list model-create)

(defn commmand-help-model [p c]
  (let [a (str (nth p 2 ""))]
    (cond
      (= a "list") "Syntax: model list modelname"
      (= a "create") "Syntax: model create modelname fields [`binding:upload ...]"
      :else "Syntax: model [help list create update delete remove]")
    ))

(def not-found-table
  "not found a valid table.")

(def not-found-model-args
  "not found a valid model name.")

(defn get-args-model [p]
  (str (nth p 2 "")))

(defn check-args-model? [p]
  (let [tbname (get-args-model p)]
    (empty? tbname)
    )
  )

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
      (= a "help") {:value (commmand-help-model p c)}
      (= a "show") {:value (sdb/get-model (get-args-model p))}
      (= a "create") (if (check-args-model? p)
                       {:error not-found-model-args}
                       (model-create p c))
      (= a "list") (if (check-args-model? p)
                     {:error not-found-model-args}
                     (model-list p c))
      :else {:error "UNKOWN ACTION. use `model help`."}
      )
    )
  )

(defn model-list [p c]
  (let [tb (sdb/def-table (get-args-model p))]
    (if (nil? tb)
      {:error not-found-table}
      {:value (dbcore/select tb)})
    )
  )

(defn model-create [p c]
  (let [tb (sdb/def-table (get-args-model p))]
    (if (nil? tb)
      {:error not-found-table}
      {:value (dbcore/select tb)})
    )
  )
