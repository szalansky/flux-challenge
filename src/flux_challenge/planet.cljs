(ns flux-challenge.planet)

(def url "ws://localhost:4000")

(defn follow-planet! [state]
  (let [ws (js/WebSocket. url)]
    (aset ws "onmessage" (fn [m]
                           (let [json (.parse js/JSON (aget m "data"))]
                             (swap! state assoc :current-planet (js->clj json)))))))
