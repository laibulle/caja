(ns petitplat.server.server
  (:require
   [org.httpkit.server :as http-kit]
   [taoensso.sente :as sente]
   [taoensso.sente.server-adapters.http-kit :as sente-httpkit]
   [compojure.core :refer :all]
   [compojure.route :as route]
   [ring.util.response :refer [response content-type]]))

(defonce channel-socket
  (sente/make-channel-socket! (sente-httpkit/get-sch-adapter) {}))

(defonce ch-recv (:ch-recv channel-socket))
(defonce connected-uids (:connected-uids channel-socket))

(defn event-msg-handler [event-msg]
  (let [{:keys [id uid client-id ?data event]} event-msg]
    (case id
      :chsk/ws-ping (println "Ping received" uid)
      :chsk/uidport-open (println "User connected" uid)
      :chsk/uidport-close (println "User disconnected" uid)
      :default (println "Unhandled event: " event-msg))))

(defn start-router! []
  (sente/start-chsk-router! ch-recv event-msg-handler))


(defroutes app
  [["/chsk" (:ajax-get-or-ws-handshake-fn channel-socket)]
   ["/chsk/send" (:ajax-post-fn channel-socket)]])

(defn start []
  (start-router!)
  (println "Starting server on port 8787...")
  (http-kit/run-server app {:port 8787}))


(comment
  (start))