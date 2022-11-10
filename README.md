# roamrender-weather
A roam/render component that returns a weather widget for any city.

## Demo

![](https://github.com/clarapastore/roamrender-weather/blob/main/demo-weather.gif)

## Usage

For the component to work, add the name of a city in the roam/render component after the block reference of the clojure code block, like this:

`{{[[roam/render]]: ((Fc_Df3sSB)) "amsterdam"}}`

This component also needs you to have an (OpenWeatherMap API key)[https://openweathermap.org/api]

## Notes

This component will show temperatures in metric by default. If you want to show temperatures in Fahrenheit, replace `&units=metric` with `&units=fahrenheit` on line 44 of the core.cljs file


