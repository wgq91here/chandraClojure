(ns chandra.table
  (:require [clojure.string :as str]
            [korma.db :as db]
            [korma.core :as dbcore]
            [chandra.sdb :as sdb]
            )
  (:gen-class)
  )

(declare command-help-table table-select table-insert table-update table-delete)

(defn commmand-help-table [p c]
  (let [a (str (nth p 2 ""))]
    (cond
      (= a "select") "Syntax: "
      (= a "insert") "Syntax: "
      (= a "update") "Syntax: "
      (= a "delete") "Syntax: "
      :else "Syntax: model [help select insert update delete]")
    ))

(defn get-args-table [p]
  (str (nth p 2 "")))

(defn check-args-table? [p]
  (let [tbname (get-args-table p)]
    (empty? tbname)
    )
  )

(def not-found-table
  "not found a valid table.")

(defn command-table [p c]
  ;  table [help [list {model args}]|[schema delete {model}]|[create update remove {model attrs}]
  ;
  ;  table help list
  ;  table select user
  ;  table insert user name "" ago 12
  ;  (prn p)
  (let [a (second p)]
    (cond
      (= a "help") {:value (commmand-help-table p c)}
      (= a "select") (if (check-args-table? p)
                       {:error not-found-table}
                       (table-select p c))
      (= a "insert") {:value (sdb/get-model (get-args-table p))}
      ;      (= a "create") (if (check-args-model? p)
      ;                       {:error not-found-model-args}
      ;                       (model-create p c))
      ;      (= a "list") (if (check-args-model? p)
      ;                     {:error not-found-model-args}
      ;                     (model-list p c))
      :else {:error "UNKOWN ACTION. use `table help`."}
      )
    )
  )

(defn table-select [p c]
  (let [tb (sdb/def-table (get-args-table p))]
    (if (nil? tb)
      {:error not-found-table}
      {:value (dbcore/select tb)})
    )
  )