(ns flux-challenge.core
  (:require [reagent.core :as r]
            [flux-challenge.component :as c]
            [flux-challenge.planet :as planet]
            [flux-challenge.siths :as siths]))

(enable-console-print!)

(def app-state (r/atom {:current-planet "Tattoine"
                        :sith-lords []}))

(planet/follow-planet! app-state)
(siths/init-list! app-state)

(defn app []
  (let [current-planet (:current-planet @app-state)
        sith-lords (:sith-lords @app-state)]
    [:div {:class "app-container"}
     [:div {:class "css-root"}
      (c/planet-monitor current-planet)
      [:section {:class "css-scrollable-list"}
       (c/siths-list sith-lords current-planet)
       (c/scroll-buttons (fn [_]
                           (siths/up! app-state))
                         (fn [_]
                           (siths/down! app-state)))]]]))

(r/render-component [app]
                    (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
