Test API deployed at : https://neko-test.herokuapp.com/suggestions

## Features

- Supports prefix search (autocomplete).
- Supports search in alternative names, therefore allowing search in languages other than English and French
- Supports search parameters such as excluding city by distance or populatio, turning on or off distance weighting factors.
- The API can ignore query strings shorter than a pre-defined length to improve overall performance, though this check should be done in the front-end.

## Query Paramters

* **Mandatory**

    `q = [String]` Partial or complete search term.
    
* **Optional**

    `latitude = [float]`
    
    `longitude = [float]`
    
    `language = ["FRE_ENG","OTHER"]` Specify the language of interest. Use OTHER to search in alternative names.
    
    `filterCityLargerThan = [integer]`
    
    `filterCitySmallerThan = [integer]` Exclude cities from result based on cities's population.
    
    `filterCityFartherThan = [float]`
    
    `filterCityCloserThan = [float]` Exclude cities from result based on city's distance from caller
    
    `filterPrefixMatch = ["true","false"]` Turn off or on prefix search.
    
    `useDistanceBonus = ["true","false"]` Decides whether consider city's distance from caller during calculation of scores.
    
    `usePopulationBonus = ["true","false"]` Decides whether consider city's population during calculation of scores.
    
## Example queries.

All caller locations are pointed to Toronto.

 - https://neko-test.herokuapp.com/suggestions?q=N&latitude=43.70011&longitude=-79.4163&filterCitySmallerThan=100000

    A user searching for "N". Only shows city with population larger than 100000

 - https://neko-test.herokuapp.com/suggestions?q=N&latitude=43.70011&longitude=-79.4163&filterCitySmallerThan=100000&useDistanceBonus=false

    The same search but don't care about how far the city is.

 - https://neko-test.herokuapp.com/suggestions?q=Mont&latitude=43.70011&longitude=-79.4163

    Same user search for "Mont", no particular search preference.

 - https://neko-test.herokuapp.com/suggestions?q=Mont&latitude=43.70011&longitude=-79.4163&filterCityFartherThan=600

    The same search but search area restricted to no farther than 600 km to Toronto.

 - https://neko-test.herokuapp.com/suggestions?q=%E3%83%A2%E3%83%B3&latitude=43.70011&longitude=-79.4163&language=OTHER

    User search with Japanese katakana 「モン」(mon)

 - https://neko-test.herokuapp.com/suggestions?q=%E9%AD%81%E5%8C%97&latitude=43.70011&longitude=-79.4163&language=OTHER

    User search with Simplified Chinese “魁北”(kui2 bei3)
    

## Performance

3600 requests/s on a single logical processor of Xeon X5670.

## TODO

- ~~Fix memory leak.~~ The memeory usage and GC is doing fine. The JVM needs to be configured to return unused heap to the system,though.
- Implement fuzzy search.
- Refactor the code for improved maintainability




# Coveo Backend Coding Challenge
(inspired by https://github.com/busbud/coding-challenge-backend-c)

## Requirements

Design an API endpoint that provides auto-complete suggestions for large cities.

- The endpoint is exposed at `/suggestions`
- The partial (or complete) search term is passed as a querystring parameter `q`
- The caller's location can optionally be supplied via querystring parameters `latitude` and `longitude` to help improve relative scores
- The endpoint returns a JSON response with an array of scored suggested matches
    - The suggestions are sorted by descending score
    - Each suggestion has a score between 0 and 1 (inclusive) indicating confidence in the suggestion (1 is most confident)
    - Each suggestion has a name which can be used to disambiguate between similarly named locations
    - Each suggestion has a latitude and longitude

## "The rules"

- *You can use the language and technology of your choosing.* It's OK to try something new (tell us if you do), but feel free to use something you're comfortable with. We don't care if you use something we don't; the goal here is not to validate your knowledge of a particular technology.
- End result should be deployed on a public Cloud (Heroku, AWS etc. all have free tiers you can use).

## Advices

- **Try to design and implement your solution as you would do for real production code**. Show us how you create clean, maintainable code that does awesome stuff. Build something that we'd be happy to contribute to. This is not a programming contest where dirty hacks win the game.
- Feel free to add more features! Really, we're curious about what you can think of. We'd expect the same if you worked with us.
- Documentation and maintainability is a plus.
- Don't you forget those unit tests.
- We don’t want to know if you can do exactly as asked (or everybody would have the same result). We want to know what **you** bring to the table when working on a project, what is your secret sauce. More features? Best solution? Thinking outside the box?

## Sample responses

These responses are meant to provide guidance. The exact values can vary based on the data source and scoring algorithm

**Near match**

    GET /suggestions?q=Londo&latitude=43.70011&longitude=-79.4163

```json
{
  "suggestions": [
    {
      "name": "London, ON, Canada",
      "latitude": "42.98339",
      "longitude": "-81.23304",
      "score": 0.9
    },
    {
      "name": "London, OH, USA",
      "latitude": "39.88645",
      "longitude": "-83.44825",
      "score": 0.5
    },
    {
      "name": "London, KY, USA",
      "latitude": "37.12898",
      "longitude": "-84.08326",
      "score": 0.5
    },
    {
      "name": "Londontowne, MD, USA",
      "latitude": "38.93345",
      "longitude": "-76.54941",
      "score": 0.3
    }
  ]
}
```

**No match**

    GET /suggestions?q=SomeRandomCityInTheMiddleOfNowhere

```json
{
  "suggestions": []
}
```

## References

- Geonames provides city lists Canada and the USA http://download.geonames.org/export/dump/readme.txt

## Getting Started

Begin by forking this repo and cloning your fork. GitHub has apps for [Mac](http://mac.github.com/) and
[Windows](http://windows.github.com/) that make this easier.
