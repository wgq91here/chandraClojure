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

(defn get-table-obj-name [table]
  (str "tb_" table))

(defn get-model [t]
  (let [st (get-table-obj-name t)
        f (find-var (symbol (str "chandra.core/" st)))]
    (if (nil? f)
      nil
      @f))
  )

(defn exist-model? [t]
  (let [f (get-model t)]
    (not (nil? f))
    )
  )

(defn exist-table? [t]
  (try
    (let [tm (get-model t)]
      (if (not (nil? tm))
        (do
          (dbcore/select tm (dbcore/limit 1))
          true)
        false))
    ;    (throw (RuntimeException.))
    (catch Exception e
      ;      (if (= nil (.getMessage e))
      ;        true
      false
      ;      ) ;(str "caught exception: " (.getMessage e))
      )
    )
  )

(defn undef-table [table]
  (let [t (get-table-obj-name table)
        st (symbol t)]
    (ns-unmap *ns* st))
  )

(defn def-table [table]
  (let [find-model (get-model table)
        t (get-table-obj-name table)
        st (symbol t)
        r (keyword table)]
    (if (nil? find-model)
      (let [x `(dbcore/defentity ~st
                 (dbcore/pk :id)
                 (dbcore/table ~r)
                 (dbcore/database ~sys-db))
            dmodel @(eval x)]
        (if (not (exist-table? table))
          (undef-table table) ; undef if this table not exist
          dmodel) ; return def model
        )
      find-model ; return existed def
      )
    )
  )


;(defn )
;(let [x @(dy-create-table-entity login_info (database db))] (select x))