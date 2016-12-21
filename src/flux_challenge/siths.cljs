(ns flux-challenge.siths
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<! chan]]))

(def darth-sidious "http://localhost:3000/dark-jedis/3616")

(defn fetch-sith-lord [url]
  (if url
    (http/get url {:with-credentials? false})
    (let [null-sith-lord {:body {:null-sith-lord true :homeworld nil :apprentice {:url nil}}}
          response (chan)]
      (go []
          (>! response null-sith-lord))
      response)))

(defn init-list! [state]
  (go-loop [url darth-sidious
            start 0
            end 5]
    (when (< start end)
       (let [response (<! (fetch-sith-lord url))]
         (swap! state assoc :sith-lords (conj (:sith-lords @state) (:body response)))
         (recur (:url (:master (:body response))) (inc start) end)))))

(defn up! [state]
  (go []
      (let [response (<! (fetch-sith-lord (:url (:master (first (:sith-lords @state))))))]
        (swap! state assoc :sith-lords (take 5 (cons (:body response) (:sith-lords @state)))))))

(defn down! [state]
  (go []
      (let [response (<! (fetch-sith-lord (:url (:master (last (:sith-lords @state))))))]
        (swap! state assoc :sith-lords (rest (concat (:sith-lords @state) (:body response)))))))
