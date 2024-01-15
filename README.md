## [PhoneBook App](https://github.com/LuisChore/android_firebase/tree/main/01PhonebookApp)

**Description**: App that fetches data from Firebase Real Time database and display them in a list (RecyclerView)
	
**Android Topics**:
* *Firebase*: Platform that offers tools and services for building and managing mobile and web applications.
* *Real-time Database*: No-SQL cloud hosted database that allows you to store and sync data in real time using a JSON-like data structure. It provides real time synchronization, any changes in the database are immediately reflected in all connected clients. 



**Firebase Realtime Database steps**

Firebase Console

1. Create a project in [firebase console](console.firebase.google.com)
2. Add  Firebase to an Android app
    1. Add config google services json file
    2. Add plugins and dependencies 
3. Create a Realtime Database
    1. Add the Realtime Database dependencies 

MainActivity.java

4. Get a FirebaseDatabase instance
5. Get a DatabaseReference 
6. Fetch the data from Firebase into RecyclerView
    * addValueEventListener 


**RecyclerView steps with DataBinding**

1. Add DataBinding Dependency (build.gradle.kts)
2. Create an AdapterView (RecyclerView) layout (activity_main.xml)
3. Create a custom layout for the items (item_card.xml)
4. Create  a custom Model Class to represent each of the items, a template for  the data we will pass, and make it BaseObservable for binding purposes
    * extends BaseObservable
    * Add @Bindable to the getters
    * Add notifyPropertyChanged(BR.att_name) to the setters
5. Modify the layout xml file to bind the Object Variable with the TextView Layout (activity_main.xml)

        <layout>
            <data>
                <variable>
    * Bind Textview tag ‘text’ attribute with the variable attribute object @{}

6. Create a Custom Adapter that extends RecyclerView.Adapter<CustomAdapter.ViewHolder> (MyAdapter.java).
    * Data source variable
    * Context variable
    * ViewHolder Class using DataBinding
        * Create a reference to DataBinding
    * Overridden Methods using DataBinding
        * onCreateViewHolder()
        * onBindViewHolder()
        * getItemCount()

MainActivity.java

7. Create a RecyclerView instance via Data Binding and a CustomAdapter instance
8. Define a Layout Manager for the RecyclerView object
9. Link the RecyclerView with the Adapter 
10. Add data to the SourceData
    * Notify changes to the RecyclerView via the Adapter



 **MainActivity Steps**

* Define 
    * Data Sources
        * Underlying data
    * Adapters
        * RecyclerView Adapter
    * Binding objects
         * MainActivityBinding




## [Journal App](https://github.com/LuisChore/android_firebase/tree/main/02JournalApp)

**Description**: App that displays a collection of Journals stored in the Firebase Firestore database. It uses Firebase Authentication for signing up. Logged users can upload a new Journal to the database, the Journals include: description, title and an image, the images are stored using Firebase Storage. 

**Android Topics**:

* *SHA1*: Cryptographic hash function, in the context of Android App signing, is used  to create a unique fingerprint of your app signing key.  
* *Firebase Authentication* : Firebase service that provides backend services, to authenticate users to your app. It supports authentication using passwords, phone numbers, Google signins, etc. 
* *Firebase Cloud Firestore*:  NoSQL cloud database, built on Google Cloud infrastructure, to store and sync data for client- and server-side development.
* *Firebase Storage*: Firebase service to store and serve user-generated content, such as photos or videos.
* *Glide Library*: Fast and efficient image loading library for Android
* *Menu Resource*: A menu resource defines an application menu—an options menu, context menu, or submenu—
* *ActivityResultLauncher*: Used to start an Activity and get a result from it. 


**Firebase Steps**

*Firebase Console*

1. Create a project in [firebase console](console.firebase.google.com)
2. Add Firebase to your Android app
    * Register App
      * SHA1
    * Add config google services json file
    * Add plugins and dependencies 

*Cloud Firestore Console*

1. Create a Database (test mode) 
2. Add dependency

*Authentication Console*

1. Get started
    * Add Sign-In method (Email/Password) 
2. Add dependency 

*Storage Console*

1. Get Started (test mode) 
2. Add dependency 

**Glide Steps**

1. Add dependency
2. Add the compiler using ‘annotationProcessor’
3. Call the method
```
Glide.with(context).load(imageUrl).fitCenter().into(imageView)

```

**Firebase Authentication Steps**

1. Listen for changes in the user authentication state

*SignUpActivity.java*

* Get an instance of FirebaseAuth class
* Create an AuthStateListener interface instance 
    * Implement onAuthStateChanged() method
    *  Get the current FirebaseUser to update  status
        ```
        firebaseUser = firebaseAuth.getCurrentUser()
        ```

2. Create a new User Account

*SignUpActivity.java*

* Get an instance of FirebaseAuth class
* Call method 
    ```
    firebaseAuth.createUserWithEmailAndPassword()
    ```
* Add Listener addOnCompleteListener 
    * Implement onComplete() method


3. Authentication SignIn / Signout 

*MainActivity.java*

* Get an instance of FirebaseAuth class
* Call method 
    ```
    firebaseAuth.signInWithEmailAndPassword
    ```
* Add Listener addOnSuccessListener 
    * Implement onSuccess() method
 
*JournalListActivity.java*

* Get an instance of FirebaseAuth class
* Get the current FirebaseUser to check status
    ```
    firebaseUser = firebaseAuth.getCurrentUser()
    ```
* Call the method 
    ```
    firebaseUser.signOut()
    ```

**Firebase Firestore Steps**

*JournalListActivity.java (steps)*

* Get a FirebaseFirestore instance 
* Get a CollectionReference from the firebaseFirestore instance
* Call collectionReference.get() method
* Add Listener addOnSuccessListener
    * Implement onSuccess() method
* Add addOnFailureListener
    * Implement onFailure() method 

*AddJournalActivity.java (Write)*

* Get a FirebaseFirestore instance 
* Get a CollectionReference from the firebaseFirestore instance
* Call collectionReference.add(modelClass) method
* Add Listener addOnSuccessListener
    * Implement onSuccess() method
* Add addOnFailureListener
    * Implement onFailure() method 


 **Firebase Storage steps**

*AddJournalActivity.java (Upload a File)*

* Get a StorageReference to the root location from a FirebaseStorage instance
    ```
    StorageReference storageReference =  FirebaseStorage.getIntance().getReference()
    ```

* Get a StorageReference (filePath) to a specific child using the storageReference object
    ```
    Storage Reference filePath = storageReference.child(“”).child(“”)...
    ```
* Upload a file using filePath.putFile(uriFile)

* Add Listener addOnSuccessListener
    * Implement onSuccess() method
* Add addOnFailureListener
    * Implement onFailure() method


*AddJournalActivity.java (Download a File)*

* Get a StorageReference to the root location from a FirebaseStorage instance
    ```
    StorageReference storageReference =  FirebaseStorage.getIntance().getReference()
    ```

* Get a StorageReference (filePath) to a specific child using the storageReference object
    ```
    Storage Reference filePath = storageReference.child(“”).child(“”)...
    ```
* Call the method filePath.getDownloadUrl()
* Add Listener addOnSuccessListener
    * Implement onSuccess() method
* Add addOnFailureListener
    * Implement onFailure() method


**Get a result from an activity steps**
 (Get an image from gallery) 

*AddJournalActivity.java*

1. Create an ActivityResultLauncher
    ```
    ActivityResultLauncher<String> mTakePhoto
    ```

2. Register for ActivityResult 
    ```
    mTakePhoto = registerForActivityResult(ActivityResultContract, ActivityResultCallBack)...
    ```
3. Launch an activity for result
    ```
    mTakePhoto.launch()
    ```


**Menu Steps**

* Create a menu layout (my_menu.xml)
    * Add list of items
* Override onCreateOptionsMenu (JournalListActivity.java)
    * Inflate the Menu Layout 
* Override onOptionsItemSelected (JournalListActivity.java)
    * Handle each menu item 
    
    
## [Chat App](https://github.com/LuisChore/android_firebase/tree/main/03ChatApp)

**Description**: Description: App that allows users to login anonymously using Firebase Authentication and enter into a Chat Group with different users. Every user can also create a new Chat Group, the database architecture is supported by Firebase Realtime Database, the app is developed using MVVM architecture. 


**Android Topics**:

* *Firebase Authentication (Anonymous):* It allows the user to use the app or access certain features without requiring them to sign up or log in with a traditional username and password. Instead it provides them with a temporary anonymous account that Firebase manages
* *Repository*: Class that gathers and manages multiple sources of data and provides an API used by ViewModel.
* *MutableLiveData<T>* : Class provided by Android Architecture components that is used to hold and observe data changes. It is mutable because you can change the data it holds over time.
* *Dialog:* Small window that prompts the user to make a decision or enter additional information.
![Screenshot_20240115-141516]()


 <img src="https://github.com/LuisChore/android_firebase/assets/30536589/665a3bd9-09d9-4a12-891f-55a89c9fc6bb" width="200" height="250">
 <img src="https://github.com/LuisChore/android_firebase/assets/30536589/46a6b2f2-d75a-4f4c-a390-510d02d96c56" width="200" height="250">
 <img src="https://github.com/LuisChore/android_firebase/assets/30536589/ecd95d71-7393-449e-a439-c7566eabb486" width="200" height="250">



