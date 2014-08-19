(defproject query "0.1.0-SNAPSHOT"
  :description "Experiments writing tests against PuppetDB"
  :url "https://github.com/garethr/puppetdb-expect"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [
            [lein-expectations "0.0.7"]
            [lein-kibit "0.0.8"]
            [jonase/eastwood "0.1.4"]
            [lein-autoexpect "1.0"]]
  :dependencies [
                 [org.clojure/clojure "1.5.1"]
                 [expectations "2.0.9"]
                 [environ "0.5.0"]
                 [clj-puppetdb "0.1.1"]])
