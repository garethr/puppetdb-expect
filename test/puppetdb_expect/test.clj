(ns puppetdb-expect.test
  (:require [clj-puppetdb.core :as puppetdb]
            [expectations :refer [expect from-each]]
            [environ.core :refer [env]]))

(def client (puppetdb/connect (env :puppetdb-address)))

(def nodes (puppetdb/query client "/v3/nodes"))

(def average-resources-per-node
  (:Value (puppetdb/query client "/v3/metrics/mbean/com.puppetlabs.puppetdb.query.population:type=default,name=avg-resources-per-node")))

(defn installed? [node package]
  (= false (empty?
    (puppetdb/query client (str "/v3/nodes/" node "/resources/Package/" package)))))

(defn ubuntu? [value] (= "Ubuntu" value))

(defn installed-everywhere? [package]
  (= (count nodes)
    (count (puppetdb/query client (str "/v3/resources/Package/" package)))))

; check number of nodes
(expect 1 (count nodes))

; check all nodes have wget installed
(expect installed-everywhere? "wget")

; check specific node has package installed
(expect (installed? "master" "pe-puppetdb"))

(defn facts [fact]
  (map :value (puppetdb/query client (str "/v3/facts/" fact))))

; check all machines are running Ubuntu
(expect (every? ubuntu? (facts "operatingsystem")))

; keep a check on the number of resources
(expect (< average-resources-per-node 300))
