(ns puppetdb-expect.test
  (:require [clj-puppetdb.core :as puppetdb]
            [expectations :refer [expect from-each]]
            [environ.core :refer [env]]))


(def client (puppetdb/connect (env :puppetdb-address)))

(def nodes (puppetdb/query client "/v3/nodes"))

(defn installed? [node package]
  (= false (empty? (puppetdb/query client (str "/v3/nodes/" node "/resources/Package/" package)))))

; check number of nodes
(expect 2 (count nodes))

; check all nodes have wget installed
(expect (complement empty?) (from-each
  [node (puppetdb/query client "/v3/resources/Package/wget")] node))

; check specific node has package installed
(expect (installed? "master" "pe-puppetdb"))

(defn ubuntu? [value] (= "Ubuntu" value))

; check all machines are running Ubuntu
(expect empty? (remove ubuntu?
    (map :value (puppetdb/query client "/v3/facts/operatingsystem"))))

; moving on from just checking counts, this checks the value of all returned
; nodes that match the query
(expect "Linux" (from-each
  [node (puppetdb/query client "/v3/facts/kernel")] (node :value)))
