(ns get-current-weather.1
  (:require
   [reagent.core :as r]
   [promesa.core :as p]
   [clojure.string :as str]
   [blueprintjs.core :as bp-core]))

;; state

(def app-state (r/atom {}))

;; blueprint UI components

(defonce bp-card (r/adapt-react-class bp-core/Card))

(defonce bp-icon (r/adapt-react-class bp-core/Icon))

(defonce bp-callout (r/adapt-react-class bp-core/Callout))

(defonce bp-button (r/adapt-react-class bp-core/Button))

;; API

(def apikey "YOUR_API_KEY")

(defn error-handler [err]
  (.log js/console (str "Error: " (.-message err))))

(defn handler [temp state]
  (swap! state assoc :weather temp))

(defn get-weather-from-api [url state]
  (p/-> (js/fetch url)
        (p/then #(.json %))
        (p/then #(js->clj % :keywordize-keys true))
        (p/then #(handler % state))))

;; views

(defn stop-weather [state]
  [bp-button {:icon "ban-circle" :intent "danger" :on-click #(swap! state assoc :state nil)} "Stop weather"])

(defn main-view [block-uid state city]
  (let [url (str "https://api.openweathermap.org/data/2.5/weather?q=" city "&appid=" apikey "&units=metric")]
    (get-weather-from-api url state)
    (fn []
      (let [temp (->> @state
                      :weather
                      :main
                      :temp
                      js/Number
                      js/Math.floor)
            city (:name (:weather @state))
            min-temp (->> @state
                          :weather
                          :main
                          :temp_min
                          js/Number
                          js/Math.floor)
            max-temp (->> @state
                          :weather
                          :main
                          :temp_max
                          js/Number
                          js/Math.floor)
            desc (->> @state
                      :weather
                      :weather
                      first
                      :description)
            icon-url (str "http://openweathermap.org/img/wn/" (->> @state
                                                                   :weather
                                                                   :weather
                                                                   first
                                                                   :icon) "@2x.png")]
        [:div
         [bp-card {:elevation 4 :class "weather-container"}
          [:div.temp [bp-icon {:icon "temperature" :color "#5F6B7C" :size "24" :style {:display "inline-block" :padding-right "3%"}}]
          [:p (str "Current temperature in " city ": ") [:strong temp "°C"]]]
          [:div.min-max-container
           [:span.min [:span "Min: "] [:strong (str min-temp "°C")]]
           [:span.max [:span "Max: "] [:strong (str max-temp "°C")]]]
          [:div.weather-desc
           [:span "Weather: " [:strong desc]]
           [:img {:src icon-url}]]]
         [stop-weather state]]))))

(defn show-weather [block-uid state city]
  (let [caps-location (str/capitalize city)]
    [bp-button
     {:intent "success"
      :on-click #(swap! state assoc :state :running)}
     "Show Weather for " caps-location]))

(defn input-error []
  [bp-callout {:title "Error" :intent "danger"}
   "Add a city on the roam/render block after the block reference, like this: " [:code "{{[[roam/render]]: ((Fc_Df3sSB)) \"new york\"}}"]])

;; render function

(defn render [{:keys [block-uid]} [_ _ :as city]]
  (let [state (r/cursor app-state [block-uid])
        _ (when (nil? @state)
            (reset! state {:state nil}))]
    (if city
      (if (= :running (:state @state))
        [main-view block-uid state city]
        [show-weather block-uid state city])
      [input-error])))
