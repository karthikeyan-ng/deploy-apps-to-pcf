# Pivotal Cloud Foundry (PCF) - CLI Hands On

* **How to connect to PCF from CLI?**  
Open your terminal and type `cf login -a https://api.run.pivotal.io`
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

* **How to check where you have connected?**  
This cli command `cf t` or `cf target` will give you the information about your connected environment.
```http request
api endpoint:   https://api.run.pivotal.io
api version:    2.147.0
user:           <Your login email id>
org:            techstack
space:          development
```

* **How to switch from Org or Space?**  
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

* **How to push apps to your org / space?**  
`cf push <app-name>`	==> if the `<app-name>` already exists globally on your PCF domain, it would throw an error. Hence use a unique `<app-name>` when you push.

OR

`cf push <app-name> --random-route` ==> In this way PCF would assign a random route for your app.  

If you do not specify any `<build-pack>`, it would throw an error. Because, PCF couldn’t find a right build pack for your app.  

`cf push <app-name> -p target/hello-world-rest-api.jar` ==> Here `-p` —> Path to app directory or to a zip file of the contents of the app directory.  

`cf push` ==> it would look for `manifest.yml` file at the root of your application folder structure.  

* **How to view application logs?**  
`cf logs APP_NAME` ==> it would show all the logs in your cf console.

* **How to view application info?**  
`cf apps` ==> it would display apps which is belongs to selected org and space as shown below.

```http request
name            requested state   instances   memory   disk   urls
hello-service   stopped           0/1         1G       1G     hello-service.cfapps.io
```

* **How to see the configured routes for your applications?**  
`cf routes` ==> It would display the routes for the apps belongs to the selected `org` and `space`.
You can point multiple routes to a single application.

```http request
space         host            domain      port   path   type   apps            service
development   hello-service   cfapps.io                        hello-service
```
* **How to map a new route to an existing app?**  
If you would like to add a new Route for an existing application, use below syntax  
Route is nothing but a new host URL  
`cf map-route APP_NAME  DOMAIN --hostname <your-new-unique-host-name>`  
 
* **How to List all the `spaces` for the current logged in `org`?**  
`cf spaces`  
```http request
name
development
```

* **How to List available `orgs` information? **  
`cf orgs`  
```http request
name
techstack
```

* **How to `start` and `stop` and `restart` the application?**  
`cf start APP_NAME` ==> To start  
`cf stop APP_NAME`  ==> To stop  
`cf restart APP_NAME` ==> To restart (stop and start)  

* **How to create a manifest file from the deployed application?**  
`cf create-app-manifest APP_NAME`  
The above command would look at the `app` which is deployed on the PCF and based on the app configuration, 
it would generate the `manifest.yml` file at your current path.  

* **List Droplets of an app**   
This command is in EXPERIMENTAL stage and may change without notice  
`cf v3-droplets APP_NAME`

* **List all the services which are attached to your application**  
`cf services`

     

 