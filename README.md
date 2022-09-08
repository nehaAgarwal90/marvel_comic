# Marvel Comic Application 

MarvelComic is a demo application based on modern Android application tech-stacks and MVVM architecture.
Fetching data from the network and integrating persisted data in the database via repository pattern.

<img width="311" alt="Screenshot 2022-09-08 at 16 44 07" src="https://user-images.githubusercontent.com/113059078/189108426-c9489e65-3aa5-46e6-a927-a91e6ff4b3aa.png">


## Tech stack & Open-source libraries
- Kotlin based + Coroutines for asynchronous.
- JetPack
- ViewModel - UI related data holder, lifecycle aware.
- Room Persistence - construct a database using the abstract layer.
  Architecture
- MVVM Architecture (View - DataBinding - ViewModel - Model)
- Dagger Hilt - dependency injection.
- Retrofit2 & Gson - construct the REST APIs.
- OkHttp3 - implementing interceptor, logging and mocking web server.
- Glide - loading images.
- Material-Components

## Architecture
The application architecture is built based on Uncle Bob's Clean Architecture to enforce a proper separation of concerns, limiting boundaries and unidirectional data flow. For this reason, the project has been divided into 2 modules:

data: It contains the networking and entities, models as well as the repository implementations of the domain layer through dependency injection.

presentation: It contains the UI and view models (using MVVM) of the application. The view models receive in their constructor the domain use cases for fetching or updating the data. Note this module does not add a dependency on the data module.

app: It's the top-most module, its only job is to bring together the other modules and provide the Android's Application and Activity classes.



