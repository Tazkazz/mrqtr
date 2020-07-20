# Mrqtr<sup>[*](http://www.decode.org/?q=Mrqtr) </sup> Music library application

## Prerequisites

* Java 11

## Launch

* Run `./gradlew bootRun` in project's root directory

## Properties

* iTunes.rootUrl - root URL of iTunes lookup endpoint
* iTunes.cache.ttlSeconds - Artist and album cache TTL (in seconds)
* iTunes.cache.maximumSize - Artist and album cache maximum size

## API

### GET `/v1/artists/search?keyword=Artist+name`

Search for artists with a given keyword.

Example response:

```
{
  "count": 5,
  "artists": [
    {
      "id": 3492,
      "name": "ABBA",
      "url": "https://music.apple.com/us/artist/abba/372976?uo=4",
      "genre": "Pop"
    },
    <...>
  ]
}
```

### POST `/v1/users/{userId}/favourite-artist`

Saves a user's favourite artist.

Request:

* artistId - favourite artist ID

### GET `/v1/users/{userId}/top-albums`

Find top 5 albums of user's favourite artist.

Example response:

```
{
  "count": 5,
  "albums": [
    {
      "id": 1440820126,
      "artistName": "ABBA",
      "name": "Arrival",
      "url": "https://music.apple.com/us/album/arrival/1440820126?uo=4",
      "artworkUrl": "https://is3-ssl.mzstatic.com/image/thumb/Music118/v4/4d/51/09/4d51098f-44c1-5a62-0cfe-28c3ea2db1bf/source/100x100bb.jpg",
      "price": 7.99,
      "currency": "USD",
      "tracks": 12,
      "releaseDate": "1976-10-11",
      "genre": "Pop"
    },
    <...>
  ]
}
```

Complete API documentation can be accessed via `/swagger-ui.html`