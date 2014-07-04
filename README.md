#Grobot -- the Groovy Robot

***

Grobot is a remote execution framework which draws inspiration from mcollective, Salt, func, and others. Grobot aims to fill in the space for a framework written in java with support for plugins written in groovy. 

Grobot consists of a server (the master) and agents that run on the remote target hosts (the bots). The master's role is to collect information from the bots, define jobs, schedule executions of those jobs, monitor and report on the status of the executions.
 
##DEVELOPER SETUP
###IDE
It's not strictly required but you'll probably want to install STS, SpringSource's Eclipse based IDE. Grobot makes extensive use of the Spring framework (IoC, WebMVC, ORM, JMS, and Integration) and is built with Gradle, all of which have excellent tooling support in STS.  Again, not required but highly recommended.

###Build
From the root of the project, you can build everything by issuing: 
`$ ./gradlew clean build` 

This will download all of the dependencies from Maven Central, install them, and compile and test each component.

##The Grobot REST API
/api/{version}/bots          GET        200     A list of all bots
/api/{version}/bots          POST       201     Attempt to create a new bot record
/api/{version}/bots/{id}     GET        200     Returns the details of a bot by id
/api/{version}/bots/{id}     PUT        201     Updates a specific bot details
/api/{version}/bots/{id}     DELETE     201     Updates a specific bot details
/api/{version}/groups        GET        200     A list of all groups
/api/{version}/groups/{id}   GET        200     Returns the details of a group by id


## LICENSE
Copyright 2012 Metabuild Software, LLC. (http://www.metabuild.org)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

