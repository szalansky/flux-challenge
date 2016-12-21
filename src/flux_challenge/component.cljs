(ns flux-challenge.component)

(defn planet-monitor [planet]
  [:h1 {:class "css-planet-monitor"}
   (str "Obi-Wan currently on " (get planet "name"))])

(defn sith-lord [planet sith]
  (let [matching (= (:name planet) (:name (:homeworld sith)))]
    [:li {:class (str "css-slot"
                      (when matching " matching-planet"))}
     [:h3 (:name sith)]
     [:h6 (str "Homeworld: " (:name (:homeworld sith)))]]))

(defn scroll-buttons [up-fn down-fn]
  [:div {:class "css-scroll-buttons"}
   [:button {:class "css-button-up" :on-click up-fn}]
   [:button {:class "css-button-down" :on-click down-fn}]])

(defn siths-list [siths planet]
   [:ul {:class "css-slots"}
    (map (partial sith-lord planet) siths)])
