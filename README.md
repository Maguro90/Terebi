# Terebi

Trial assessment for Brightcove

## Tech stack
- **UI**: Jetpack Compose
- **DI**: Koin
- **Threading**: Coroutines/Flow
- **API Calls**: Retrofit
- **JSON Serialization**: Moshi

## Architecture Layers 
- Data (Repositories, API calls and everything data related)
- Domain (Use cases)
- ViewModel
- UI

## Some notes
- The chosen arquitecture is MVVM.
- Time is displayed in the current device timezone.
- Decoupling has been done up to API (TVMaze) level. Since it is not owned by the author of the app, this lets us move to another API in the future if necessary.
- Although Unit tests were not included in this assessment, they should be very easy to implement. All dependencies are mockable and are injected in the constructor via Koin.
