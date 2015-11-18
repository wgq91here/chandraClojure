(ns chandra.sdb
  (:require [chandra.tools :refer [current-path]]
            [korma.db :as db]
            [korma.core :as dbcore])
  ;  (:use [korma.db]
  ;        [korma.core :exclude [update]]
  ;        [korma.core :rename {update kupdate}])
  (:gen-class)
  )

;(def sys-db (sqlite3 {:db (str current-path "/resources/db/chandra.db")}))

(def sys-db {:classname "org.xerial.sqlite-jdbc"
             :subprotocol "sqlite"
             :subname (str current-path "\\resources\\db\\chandra.db")})

(defmacro dy-create-table-entity [ent & body]
  (let [t (symbol (str "tb_" ent))
        real-table-name (keyword ent)]
    (prn "!!!! " t)
    `(dbcore/defentity ~t
       (dbcore/pk :id)
       (dbcore/table ~real-table-name)
       (dbcore/database ~sys-db)
       ~@body)
    ))

;(defn list-table [table & body]
;  (let [t (symbol (str "tb_" table))
;        real-table-name (symbol table)
;        x `(dy-create-table-entity ~t
;             (db/table ~real-table-name)
;             (db/database ~sys-db))
;        xx `(~@x)]
;    (prn xx)
;    (dbcore/select `x)))

(defn d-table [table]
  (let [t (symbol (str "tb_" table))
        r (keyword table)
        x `(dbcore/defentity ~t
             (dbcore/pk :id)
             (dbcore/table ~r)
             (dbcore/database ~sys-db))]
    (prn r)
    @(eval x)
    ))

;(defn )
;(let [x @(dy-create-table-entity login_info (database db))] (select x))