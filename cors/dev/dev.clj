;; dev mode in repl (can get prod mode by passing prod options to dev-init
(ns dev
  (:require [io.pedestal.service.http :as bootstrap]
            [cors.service :as service]
            [cors.server :as server]))

(def service (-> service/service
                 (merge  {:env :dev
                          ::bootstrap/join? false
                          ::bootstrap/routes #(deref #'service/routes)})
                 (bootstrap/default-interceptors)
                 (bootstrap/dev-interceptors)))

(defn start
  [& [opts]]
  (server/create-server (merge service opts))
  (bootstrap/start server/service-instance))

(defn stop
  []
  (bootstrap/stop server/service-instance))

(defn restart
  []
  (stop)
  (start))

