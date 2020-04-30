# BookIt

BookIt is an Android that that takes a search term and sends a list query to the Google Books API.

It uses the following components:

- Room to store the latest search results
- Retrofit2 to retrieve data from the Google Books API
- ViewModel to present the data to the user
- LiveData to facilitate updates to the data
- RecyclerView to provide efficient scrolling
- Glide for image loading
- Coroutines to properly scope method excecution such as I/O

It was written in Kotlin, as it was my goal to not default to Java which I have used in the past.

The To-do list includes:

- Infinite scrolling using PagedListAdapter
- Display errors to the user (even if that is just Toast)
- Make the UI look better in landscape



