# Mind of the Valley
**A Mindvalley Technical Assessment**

![ic_loading](https://github.com/garethlye/MindOfTheValley/assets/24999634/52c9231d-3177-4de6-9d98-b28627b1de1d)



## Project Overview

Following based off the requirements of the assessment, this project encorporates mainly a single page with multiple scrollable views of which the data is obtained by the APIs provided.
This single page is called "Channels" within this application, which comprises of an Episodes section, a Channels section(with two different variants of views), and the category section.

The APIs provided were a JSON format hosted on pastebin.com, unfortunately pastebin is not an actual API provider but a text host so the response is just a plain string JSON file.
There are 3 APIs hosted on PasteBin that make up the entirety of data required on this single page.

The page has been created based off a figma design given in the assessment, it includes a ScrollView with multiple RecyclerViews with individual child views. Each recycler view has been
limited to a maximum of 6 childs based off the requirement.

The project has included some basic imports such as Retrofit, Gson, Glide and Espresso, of which most of it runs of AndroidX.
These were neccessary to make the codebase look cleaner, run more efficiently and easily maintainable and scalable.


## Notes about development
- An internet state check was implemented, to give better UX and functionality to the user if they were not connected to the internet.

- Although not requested, and not neccesary since there was only a single Activity required to achieve this task, i've added some BaseActivity and BaseViewModels to handle the loading state,
  empty state, no internet available state, content view state, as well as any other general purpose requirements each activity/viewmodel such as binding and layout inflation. I find this
  reduces code boilerplate quite significantly and makes the overall codebase much more maintainable. 

- Offline mode has been added and tested by saving the Data() object into Shared preferences, if the app launches and internet is not present, it will attempt to load the backup JSON.
  Cached Images will display along with the appropriate titles and text. IF no internet is present, and no offline backup detected, it will display an internet error screen where the user
  is able to press try again in an attempt to load the APIs again when the internet becomes available. If no internet persists, it will come back to the same no internet screen.

- The application is technically deployment-ready, i've tested it on a simulated Android 19 - 30 device, and my own person Android 32 device, but obviously it lacks production keystore keys
  to deploy to deploy to Google Play. Can be altered off-design if required.

- Tablet mode(or Landscape in general) is handled. The recycler views redraw itself into the updated orientation, the categories section were a tricky one as it was handled with a GridLayout
  as the layout manager to follow the figma design, but i've handled it in the activity itself to redraw itself. For the best experience I could redraw it manually by calculating pixel density
  but I chose not to attempt that as it would eat into some CPU cycles.

- Multi-resolution was also tested via Simulated devices, the UI scales appropriately.

- Efficiency wise, the images are set to cache itself the moment it's loaded which gives it a responsive scrolling and loading. I did notice in the URLs it was being downscaled by the server
  itself with "?transform=w_1080", the app could also override this transform in the URL to downscale further because 1080p images on such a small view is quite ram heavy as well unneccessary,
  but I decided not to go this far into handling image cache storage, just a point of potential improvement I noticed.

- I've added some loading animations, different state screens to give the best possible UX for this scope. There is one implementation i've added that may not be necessary, where I added a 2 second
  postDelayed before displaying the content, this is to prevent the loading view to essentially flicker if the API response is too quick. Although technically wasting time, as a user I much prefer
  to see seem-less transitions rather than weird flickers that may cause some unnecessary panic. 
- APIs, Interactors, Managers are dependency injected.

## Challenges
- I've not built an app from the ground up for awhile, so this was an interesting challenge but a refresher and brought back some memories.
- UI tests were not something i'm used to, in my previous experience I've mainly built an automation scripts with my QA team with Appium.
- Saving the JSON file for offline storage is also something I was worried about, as the JSON provided was massive and I was afraid that it would exceed a String's limit and cause some corruption.
  I did consider trimming it down to store specific classes or lists but I could not find a nice proper way of guarantying the same success with the String limit. It was down to either saving it as
  an external file or using SharedPreferences for the simplest implementation. I chose SharedPreferences in this case because after testing it, it seems to have managed to contain the large JSON
  object without exceeding the limit. If i had more time to redo this section, I would probably go with a different style of storing large objects like these.

## What I would add or improve?
- Definitely a different way of storing the JSON file into the device, one that makes more sense for potentially large files to ensure data integrity.
- A splash screen, it wasn't in the figma but it's something I always enjoyed seeing when opening an application that's loading
- Originally written with a variant of MVP called VIPER, but I have rewrote it to MVVM since it's the preferred structure.
- Was hoping to switch to an actual API endpoint rather than PasteBin, the API module would've looked more readable, I would be able to implement versioning to the endpoint, maybe experimented
  with GraphQL integrations as well depending on what the API endpoint is capable of.
- A customized MindValley logger, currently in this project I do not handle any real exceptions since the figma doesn't specifically say what should happen since it's only a single page,
  and there is no logging into logcat or analytics platforms like clevertap/firebase.
- Drawable Utilities, to be able to draw certain drawables directly on the app, mainly to reduce app size and ram usage by drawing certain images like borders and rounded backgrounds programatically,
  opted not to do this since the project complexity and assets are really small.

## Known Bugs / Issues
- Potential MultiDex issues with certain devices, it's a known issue with supporting old Android APIs that I have personally faced before with Samsung and Oppo devices, no good way to fix all of them
