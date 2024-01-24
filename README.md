# Android Nimble Surveys App
An application allows users to browse a list of surveys.

## Application features
**Authentication:**
- Implement the login authentication screen.
- Implement the OAuth authentication including the storage of access tokens.
- Implement the automatic usage of refresh tokens to keep the user logged in using the OAuth API.

**Home Screen:**
- On the home screen, each survey card must display the following info:
  - Cover image (background)
  - Name (in bold)
  - Description
- There must be 2 actions:
  - Horizontal scroll through the surveys.
  - A “Take Survey” button should take the user to the survey detail screen. 
- The list of surveys must be fetched when opening the application.
- Show a loading animation when fetching the list of surveys.
- The navigation indicator list (bullets) must be dynamic and based on the API response.

## Technical in use
- App Architecture: Clean Architecture + MVVM.
- UI Toolkit: Jetpack Compose.
- Dependency Injection: Hilt.
- Asynchronous: Kotlin Coroutines + Flow.
- Network: Retrofit + OkHttp3.
- Storages: Datastore.
- JSON parser: Moshi.
- Paging: Paging 3.
- Image loading: Coil.
- Unit Testing: Mockk, Kotest, JUnit.
- Logger: Timber, Chucker, OkHttp logging interceptor.
- Crashlytics: Firebase Crashlytics.
- Security: Encrypted Datastore, ProGuard, protect API keys with CMake
- CI/CD: Bitrise.io auto-build when a push event was triggered.

## Need to improve
- The requirement request to support Android 5.0 (API 21) and Up. But I'm using `security-crypto-ktx`, which uses min SDK 23. I need more time to research and find the solution. 
- Implement animation when navigating from the Splash screen to the Login screen.
- Indicator list (bullets) isn't auto-scrolling when there are a lot of bullets to reach out of the width screen.
- Not yet implemented navigate to the Login screen in case the refresh token also expires.
- Apply DetectKt and Ktlint to format and analyze code.
- Apply Kover to measure the coverage for tests.
- Integrate code analytics and unit test running into the CI process when a PR event is triggered.

## Thank you
Thanks to GitHub repositories used in this project.
- Nimble Android Template: https://github.com/nimblehq/android-templates
- Encrypted Datastore: https://github.com/osipxd/encrypted-datastore
- Paging Compose sample: https://github.com/mohammadjoumani/paging_movie_app_jetpack_compose
- Android UI State Delegate: https://github.com/Ilinich/Android-UIState-Delegate

_In a short time, maybe there will be some issues or bugs in the project. So, I would be happy if you review this project, and hope to receive your feedback to improve it better._
