(ns chandra.model
  (:require [clojure.string :as str]
            [korma.db :as db]
            [korma.core :as dbcore]
            [chandra.sdb :as sdb]
            )
  (:use [chandra.out]
        [chandra.tools])
  (:gen-class)
  )

(declare command-help-model model-list model-create)

(def not-found-model
  "not found a valid model name.")

(defn get-args-model [p]
  (str (nth p 2 "")))

(defn check-args-model? [p]
  (let [tbname (get-args-model p)]
    (empty? tbname)
    )
  )

(def model-command-options
  [["-n" "--notchange" "Not'do change to model"
    :default false]
   ["-p" "--preset name" "System's Preset DB"
    :default "none"]
   ["-f" "--field name1:type1" "Normal Fields"
    :default "id:pk"
    :parse-fn #(cli-parser %)]
   ["-b" "--binding name" "Special Fields"
    :default ""]
   ])

(defn commmand-help-model [p]
  (let [a (str (nth p 2 ""))]
    (cond
      (= a "list") "Syntax: model list modelname"
      (= a "create") "Syntax: model create modelname fields [`binding:upload ...]"
      :else "Syntax: model [help list create update delete remove]")
    ))


(defn command-model [c]
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

  (let [tc (parse-args c model-command-options)]
    tc
    )
  ;  (let [a (second p)]
  ;    (cond
  ;      (= a "help") {:value (commmand-help-model p c)}
  ;      (= a "show") {:value (sdb/get-model (get-args-model p))}
  ;      (= a "create") (if (check-args-model? p)
  ;                       {:error not-found-model}
  ;                       (model-create p c))
  ;      :else {:error "UNKOWN ACTION. use `model help`."}
  ;      )
  ;    )
  )

(defn model-create [p c]
  (let [tb (sdb/def-table (get-args-model p))]
    (if (nil? tb)
      {:error not-found-model}
      {:value (dbcore/select tb)})
    )
  )
