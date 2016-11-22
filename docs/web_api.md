# EstimoteServer web API
Part of an url wrapped in {} stands for variable. Inside {} variable name and its type are separated with :.
If a url variable declared with {} is wrapped in [] it is optional. No optional parameter can be excluded
if a latter parameter is wished to be defined.

API_URL: http://www.example.com/index.php/api

**Store beacon measurement values to database.**

	{API_URL}/beaconvalues/{device_id:UUID}/[{temperature:FLOAT}]/[{brightness:FLOAT}]/[{pressure:FLOAT}]

**Configure beacon device bound values.**

	{API_URL}/beaconconfig/{device_id:UUID}/{song_id:INT}/{background_id:INT}

**Get available songs**

	{API_URL}/songs

Returns a JSON array containing all songs available in this service.
The JSON is an array containing objects in following format:

```json
{
	"id":"1",
	"name":"Sandstorm",
	"artist":"Darude"
}
```

**Get available backgrounds**

	{API_URL}/backgrounds

Returns a JSON OBJECT containing all backgrounds available in this service.
The JSON is an object has two container objects, one for common information
about the backgrounds and one for background specific information. Common info
has the public path for the backgrounds folder, which is relative to application
root. Backgrounds is an array of JSON objects which hold background specific
information.

An example response:

```json

{
	"common": {
		"path": "/assets/backgrounds"
	},
	"backgrounds": [
		{
			"id": "1",
			"name": "jaa.jpg",
			"path": "/"
		}
	]
}
```
