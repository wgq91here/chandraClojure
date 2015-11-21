(ns chandra.tools
  (:require [clojure.tools.cli :refer [parse-opts]]
            ))

(def current-path (System/getProperty "user.dir"))

(defn cli-parser [i]
  (map first (re-seq #"/\s*(\".+?\"|[^:\s])+((\s?:\s*(\".+?\"|[^\s])+)|)|(\".+?\"|[^\"\s])+" i)))

(defn cli-arg-parser [i]
  (map first (re-seq #"/\s*(\".+?\"|[^:\s])+((\s?:\s*(\".+?\"|[^\s])+)|)|(\".+?\"|[^\"\s])+" i)))

(def ret-type
  {:error nil, :value nil})

(defn merge-out [v]
  (merge ret-type v))

(defn parse-args [args options]
  (parse-opts (cli-parser args) options))