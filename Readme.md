# Pivotal Cloud Foundry (PCF) - CLI Hands On

## How to connect to PCF from CLI?
* Open your terminal and type `cf login -a https://api.run.pivotal.io`
```http request
API endpoint: https://api.run.pivotal.io

Email: <your-email>

Password: 
Authenticating...
OK

Targeted org techstack

Targeted space development

API endpoint:   https://api.run.pivotal.io (API version: 3.82.0)
User:           <entered-email-id>
Org:            techstack
Space:          development
```

## How to check where you have connected?
This cli command `cf t` or `cf target` will give you the information about your connected environment.
```http request
api endpoint:   https://api.run.pivotal.io
api version:    2.147.0
user:           <Your login email id>
org:            techstack
space:          development
```

## How to switch from Org or Space?
Before
```http request
cf t 
api endpoint:   https://api.run.pivotal.io
api version:    2.147.0
user:           <Your login email id>
org:            techstack
space:          development
```
After
```http request
cf t -o techstack -s testing | cf target -o techstack -s testing
api endpoint:   https://api.run.pivotal.io
api version:    2.147.0
user:           <Your login email id>
org:            techstack
space:          testing
```

## How to push apps to your org / space?
`cf push <app-name>`	==> if the `<app-name>` already exists globally on your PCF domain, it would throw an error. Hence use a unique `<app-name>` when you push.

OR

`cf push <app-name> --random-route` ==> In this way PCF would assign a random route for your app.  

If you do not specify any `<build-pack>`, it would throw an error. Because, PCF couldn’t find a right build pack for your app.  

`cf push <app-name> -p target/hello-world-rest-api.jar` ==> Here `-p` —> Path to app directory or to a zip file of the contents of the app directory.  

`cf push` ==> it would look for `manifest.yml` file at the root of your application folder structure.  


