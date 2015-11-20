(ns chandra.tools)

(def current-path (System/getProperty "user.dir"))

(defn cli-parser [i]
  (map first (re-seq #"/\s*(\".+?\"|[^:\s])+((\s?:\s*(\".+?\"|[^\s])+)|)|(\".+?\"|[^\"\s])+" i)))

(def ret-type
  {:error nil, :value nil})