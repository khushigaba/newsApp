
**NowNews App**

## Overview
The NowNews app integrates with **NewsAPI** to fetch and display the latest news articles. The application leverages Kotlin for Android development and follows modern Android architecture principles.

---

## **Backend API: NewsAPI**
- **Source:** [https://newsapi.org/](https://newsapi.org/)
- **Purpose:** Provides JSON data for trending news, headlines, and various categories such as business, sports, technology, and more.

---

## **API Endpoints Used**

1. **Trending News**  
   - **Endpoint:** `/v2/top-headlines`
   - **Parameters:**
     - **country:** `us` (or any other supported country code)
     - **apiKey:** `YOUR_API_KEY`
   - **Example Request:**  
     ```
     https://newsapi.org/v2/top-headlines?country=us&apiKey=YOUR_API_KEY
     ```

2. **Category-wise News**
   - **Endpoint:** `/v2/top-headlines`
   - **Parameters:**
     - **country:** `us`
     - **category:** `business | technology | sports | health | science`
     - **apiKey:** `YOUR_API_KEY`
   - **Example Request:**
     ```
     https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=YOUR_API_KEY
     ```

3. **Search News**
   - **Endpoint:** `/v2/everything`
   - **Parameters:**
     - **q:** `Keyword to search`
     - **apiKey:** `YOUR_API_KEY`
   - **Example Request:**
     ```
     https://newsapi.org/v2/everything?q=bitcoin&apiKey=YOUR_API_KEY
     ```

---


## **Technologies Used**

-   **Kotlin**: Primary language for Android development.
    
-   **Android Glide**: Image loading and caching library.
    
-   **OkHttp**: For making network API calls.
    
-   **Coroutines**: For background API calls and concurrency management.
    
-   **Jetpack Navigation**: Used for bottom navigation in the home screen.
    

---


## **Features Implemented**

1. **Trending News:**
   - Lists trending news articles in a RecyclerView.
   - Each list item includes an image, title, and publication date.

2. **News by Category Selection:**
   - Provides options for users to filter news by categories.

3. **Search News:**
   - Enables users to search for specific news articles by keywords.

---

## **Usage Instructions**

1. **Obtain API Key:**
   - Sign up at [NewsAPI](https://newsapi.org/) and get your API key.

2. **Integrate API Key:**
   - Store the key securely in **local.properties** or **BuildConfig**.

3. **Make API Calls:**
   - Ensure network connectivity and handle exceptions appropriately.

---

## **Contributing**
Contributions to enhance the NowNews app are welcome. Please follow standard coding conventions and document your changes.

---

## **License**
This project is licensed under the **MIT License**. Refer to the LICENSE file for more details.

