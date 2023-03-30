# Pantry Inventory Application for Android
## About
This is an android application which keeps track of ingredients that have a barcode in your pantry.  The application will scan ingredients into your pantry using the barcode on the product and looking up the product on the database https://world.openfoodfacts.org/.  You can then see your pantry by going to the "View Pantry" activity.

## Main Activity
The main activity for the application is simply a screen which allows the user to go into any of the other activities that are in the application.  The other activities that the application can enter are...
* Add Ingredient - Add an item to your pantry by scanning its barcode
* Remove Ingredient - Remove an item from your pantry by scanning its barcode
* View Pantry - View the items in your pantry using a recycler view

<img src="https://github.com/cjbagwell/pantry/blob/main/readme_resources/main_activity_image.png" width="150">

## Add Ingredient Activity
This activity allows the user to add ingredients into their pantry by scanning the products barcode.  Once the barcode has been scanned, a green checkmark will appear to let the user know the barcode was found.  Once a result is returned (either from the local SQLite database or the remote database), a pop up will become visible asking how many of that ingredient the user wants to add to the pantry.  If the item is not in the remote database, then a message will let the user know that the ingredient is not in the database.

<img src="https://github.com/cjbagwell/pantry/blob/main/readme_resources/add_ingredient_activity_image_successful_capture.png" width="150">   <img src="https://github.com/cjbagwell/pantry/blob/main/readme_resources/add_ingredient_popup_image.png" width="150">   <img src="https://github.com/cjbagwell/pantry/blob/main/readme_resources/add_ingredient_ingredient_not_found_image.png" width="150">

## Remove Ingredient Activity
This activity allows the user to remove ingredients from their pantry by scanning the products barcode.  

<img src="https://github.com/cjbagwell/pantry/blob/main/readme_resources/remove_ingredient_popup_image.png" width="150">

## View Pantry Activity
This activity implements a recycler view where every ingredient is displayed in a scrollable list.  All of the ingredients in the local SQLite database that have a quantity greater than or equal to 1 will be diplayed in the view.  If an entry in the view is clicked, then a pop up dialog will appear displaying information about the ingredient.

<img src="https://github.com/cjbagwell/pantry/blob/main/readme_resources/view_pantry_image.png" width="150">   <img src="https://github.com/cjbagwell/pantry/blob/main/readme_resources/view_pantry_popup_image.png" width="150">
