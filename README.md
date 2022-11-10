# roamrender-weather
A roam/render component that returns a weather widget for any city.

## Demo

![](https://github.com/clarapastore/roamrender-weather/blob/main/demo-weather.gif)

## Usage

For the component to work, add the name of a city in the roam/render component after the block reference of the clojure code block, like this:

`{{[[roam/render]]: ((Fc_Df3sSB)) "amsterdam"}}`

This component also needs you to have an [OpenWeatherMap API key](https://openweathermap.org/api) in order to get the weather data. Once you have the api key, replace `"YOUR_API_KEY"` with the key on line 24 of the core.cljs file.

## Notes

This component will show temperatures in metric by default. If you want to show temperatures in Fahrenheit, replace `&units=metric` with `&units=imperial` on line 44 of the core.cljs file.

Make sure to add the css code from the styles.css file into your roam graph, to render the layout of the widget correctly. Here's [a guide on adding css code into Roam](https://thinkstack.club/how-to-add-custom-css-in-roam/#:~:text=The%20simplest%20way%20to%20add,it%20to%20load%20the%20CSS.). 

## Component installation

For installing this roam/render component, refer to the instructions laid out in [this repository](https://github.com/clarapastore/youglish-roam-dutch/blob/main/README.md#installation) (the code is different, but the installation and usage instructions are the same).


