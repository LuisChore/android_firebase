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

