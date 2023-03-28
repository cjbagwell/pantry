# Pantry Inventory Application for Android
## About
This is an android application which keeps track of ingredients that have a barcode in your pantry.  The application will scan ingredients into your pantry using the barcode on the product and looking up the product on the database https://world.openfoodfacts.org/.  You can then see your pantry by going to the "View Pantry" activity.

## Main Activity
The main activity for the application is simply a screen which allows the user to go into any of the other activities that are in the application.  The other activities that the application can enter are...
* Add Ingredient
* Remove Ingredient
* View Pantry
* Refill Pantry

![alt text](https://github.com/cjbagwell/pantry/blob/main/readme_resources/main_activity_image.png)

## Add Ingredient Activity
This activity allows the user to add ingredients into their pantry by scanning the products barcode.  Once a result is returned, a pop up will become visible asking how many of that ingredient the user wants to add to the pantry.
![alt text](https://github.com/cjbagwell/pantry/blob/main/readme_resources/add_ingredient_activity_image.png)
![alt text](https://github.com/cjbagwell/pantry/blob/main/readme_resources/add_ingredient_popup_image.png)

## View Pantry Activity
This activity implements a recycler view where every ingredient is displayed in a scrollable list.
![alt text](https://github.com/cjbagwell/pantry/blob/main/readme_resources/view_pantry_image.png)
