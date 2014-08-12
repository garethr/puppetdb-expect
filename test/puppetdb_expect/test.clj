(ns puppetdb-expect.test
  (:require [clj-puppetdb.core :as puppetdb]
            [environ.core :refer [env]])
  (:use expectations))

(def client (puppetdb/connect (env :puppetdb-address)))

(def nodes (puppetdb/query client "/v3/nodes"))

; check number of nodes
(expect 2 (count nodes))

; check all nodes have wget installed
(expect (count nodes) (count
  (puppetdb/query client "/v3/resources/Package/wget")))

; moving on from just checking counts, this checks the value of all returned
; nodes that match the query
(expect "Linux" (from-each
  [node (get (puppetdb/query client "/v3/facts/kernel") :value)] node))
