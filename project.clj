(defproject b-logic-basics "0.1.0"
  :description "Libs for easy business logic building."
  :url "https://github.com/davd33/b-logic-basics"
  :license {:name "GPL-3"
            :url "https://www.gnu.org/licenses/gpl.html"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [ring/ring-core "1.7.1"]
                 [ring/ring-mock "0.4.0"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-defaults "0.3.2"]
                 [compojure "1.5.0"]
                 [cheshire "5.8.1"]]
  :repl-options {:init-ns b-logic-basics.core})
