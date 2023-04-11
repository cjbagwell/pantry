package com.example.pantry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.*;

import java.util.LinkedList;

public class IngredientTest {

    private Ingredient fixture;

    private String baseBarcode = "barcode";
    private String baseName = "ingredientName";
    private String baseImageUrl = "https://image.url/myImage.jpg";
    private int baseQuantity = 37;
    private double baseCarbs = 3.7;
    private double baseSugars = 7.3;
    private double baseFats = 69.42;
    private double baseProtein = 42.69;
    private double baseCalories = 370.073;
    private LinkedList<String> baseStores = new LinkedList<>();
    private String baseBrand = "BrandThatDoesNotSuck";


    @Before
    public void setUp() {
        fixture = new Ingredient(baseBarcode, baseName, baseImageUrl,
                baseQuantity, baseCarbs, baseSugars, baseFats,
                baseProtein, baseCalories, baseStores, baseBrand);
    }

    @After
    public void tearDown() {
        fixture = null;
    }

    @Test
    public void testAllArgConstructor() {
        assertTrue("barcode failed to set!", fixture.barcode.equals(baseBarcode));
        assertTrue("name failed to set!", fixture.name.equals(baseName));
        assertTrue("imageUrl failed to set!", fixture.imageUrl.equals(baseImageUrl));
        assertTrue("qtInPantry failed to set!", fixture.qtInPantry == baseQuantity);
        assertTrue("carbs failed to set!", fixture.carbs == baseCarbs);
        assertTrue("sugars failed to set!", fixture.sugars == baseSugars);
        assertTrue("fats failed to set!", fixture.fats == baseFats);
        assertTrue("protein failed to set!", fixture.protein == baseProtein);
        assertTrue("calories failed to set!", fixture.calories == baseCalories);
        assertTrue("stores failed to set!", fixture.stores.equals(baseStores));
        assertTrue("brand failed to set!", fixture.brand.equals(baseBrand));
    }

    @Test
    public void testToString() {
        String baseExpectedOutput = String.format("Ingredient{name='%s', qtInPantry=%d}", baseName, baseQuantity);
        assertEquals("toString did not return expected string.", fixture.toString(), baseExpectedOutput);

        String newName = "braindead";
        int newQuantity = (int)Math.random();
        fixture.name = newName;
        fixture.qtInPantry = newQuantity;
        String newExpectedOutput = String.format("Ingredient{name='%s', qtInPantry=%d}", newName, newQuantity);
        assertEquals("toString did not return expected string.", fixture.toString(), newExpectedOutput);
    }

    @Test
    public void loadImageIntoImageView() {
        // TODO
    }

    @Test
    public void getBarcode() {
        System.out.println("**--- Test Ingredient.getBarcode() executed ---**");

        assertTrue("Incorrect barcode from fixture.getBarcode()", fixture.getBarcode().equals(baseBarcode));

        String secondBarcode = "MyIngredientBarcode";
        fixture.barcode = secondBarcode;
        assertTrue("Incorrect barcode from fixture.getBarcode()", fixture.getBarcode().equals(secondBarcode));

        fixture.barcode = null;
        assertNull("Expected null return of fixture.getBarcode() was not null!", fixture.getBarcode());
    }

    @Test
    public void setBarcode() {
        System.out.println("**--- Test Ingredient.setBarcode() executed ---**");
        fixture.setBarcode(null);
        assertNull("fixture.barcode was not set to null!", fixture.barcode);

        String firstString = "CoolBarcode";
        fixture.setBarcode(firstString);
        assertFalse("Name was not still set to baseBarcode", fixture.barcode.equals(baseBarcode));
        assertTrue("Name was not correctly set by fixture.setBarcode(firstString)", fixture.barcode.equals(firstString));

        String secondString = "9789862410035,90000";
        fixture.setBarcode(secondString);
        assertFalse("Name was not still set to firstString", fixture.barcode.equals(firstString));
        assertTrue("Name was not correctly set by fixture.setBarcode(secondString)", fixture.barcode.equals(secondString));
    }

    @Test
    public void getName() {
        System.out.println("**--- Test Ingredient.getName() executed ---**");

        assertTrue("Incorrect name from fixture.getName()", fixture.getName().equals(baseName));

        String secondName = "MyIngredientName";
        fixture.name = secondName;
        assertTrue("Incorrect name from fixture.getName()", fixture.getName().equals(secondName));

        fixture.name = null;
        assertNull("Expected null return of fixture.getName() was not null!", fixture.getName());
    }

    @Test
    public void setName() {
        System.out.println("**--- Test Ingredient.setName() executed ---**");
        fixture.setName(null);
        assertNull("fixture.name was not set to null!", fixture.name);

        String firstString = "Sour Patch Kids";
        fixture.setName(firstString);
        assertNotNull("fixture.name was null", fixture.name);
        assertTrue("Name was not correctly set by fixture.setName(firstString)", fixture.name.equals(firstString));

        String secondString = "Lettuce";
        fixture.setName(secondString);
        assertFalse("Name was not still set to firstString", fixture.name.equals(firstString));
        assertTrue("Name was not correctly set by fixture.setName(secondString)", fixture.name.equals(secondString));
    }

    @Test
    public void getImageUrl() {
        System.out.println("**--- Test Ingredient.getImageUrl() executed ---**");

        assertTrue("Incorrect imageUrl from fixture.getImageUrl()", fixture.getImageUrl().equals(baseImageUrl));

        String secondImageUrl = "https://www.tasteofhome.com/wp-content/uploads/2019/11/sugar-shutterstock_615908132.jpg";
        fixture.imageUrl = secondImageUrl;
        assertTrue("Incorrect imageUrl from fixture.getImageUrl()", fixture.getImageUrl().equals(secondImageUrl));

        fixture.imageUrl = null;
        assertNull("Expected null return of fixture.getImageUrl() was not null!", fixture.getImageUrl());
    }

    @Test
    public void setImageUrl() {
        System.out.println("**--- Test Ingredient.setImageUrl() executed ---**");
        fixture.setImageUrl(null);
        assertNull("fixture.imageUrl was not set to null!", fixture.imageUrl);

        String firstString = "https://www.tasteofhome.com/wp-content/uploads/2019/11/sugar-shutterstock_615908132.jpg";
        fixture.setImageUrl(firstString);
        assertNotNull("ImageUrl was null", fixture.imageUrl);
        assertTrue("ImageUrl was not correctly set by fixture.setImageUrl(firstString)", fixture.imageUrl.equals(firstString));

        String secondString = "https://nuts.com/images/rackcdn/ed910ae2d60f0d25bcb8-80550f96b5feb12604f4f720bfefb46d.ssl.cf1.rackcdn.com/5025_SourPatchKids_p-zuo5bhlq-zoom.jpg";
        fixture.setImageUrl(secondString);
        assertFalse("ImageUrl was not still set to firstString", fixture.imageUrl.equals(firstString));
        assertTrue("ImageUrl was not correctly set by fixture.setImageUrl(secondString)", fixture.imageUrl.equals(secondString));
    }

    @Test
    public void getCarbs() {
        System.out.println("**--- Test Ingredient.getCarbs() executed ---**");

        assertTrue("Inital value of getCarbs() was incorrect", fixture.getCarbs() == baseCarbs);

        double sourPatchCarbs = 27.0;
        fixture.carbs = sourPatchCarbs;
        assertTrue("getCarbs() did not return expected value: " + sourPatchCarbs, fixture.getCarbs() == sourPatchCarbs);

        double fakeFruitCarbs = -17.0;
        fixture.carbs = fakeFruitCarbs;
        assertTrue("getCarbs() did not return expected value: " + fakeFruitCarbs, fixture.getCarbs() == fakeFruitCarbs);

        double randomCarbs = Math.random();
        fixture.carbs = randomCarbs;
        assertTrue("getCarbs() did not return expected value: " + randomCarbs, fixture.getCarbs() == randomCarbs);
    }

    @Test
    public void setCarbs() {
        System.out.println("**--- Test Ingredient.setCarbs() executed ---**");

        double specialCarbs = -3.7;
        fixture.setCarbs(specialCarbs);
        assertTrue("fixture.setCarbs did not set to expected value", fixture.carbs == specialCarbs);

        double newCarbs = Math.random();
        fixture.setCarbs(newCarbs);
        assertTrue("fixture.setCarbs did not set to expected value", fixture.carbs == newCarbs);

        double zeroCarbs = 0;
        fixture.setCarbs(zeroCarbs);
        assertTrue("fixture.setCarbs did not set to expected value", fixture.carbs == zeroCarbs);
    }

    @Test
    public void getFats() {
        System.out.println("**--- Test Ingredient.getFats() executed ---**");

        assertTrue("Inital value of getFats() was incorrect", fixture.getFats() == baseFats);

        double sourPatchFats = 27.0;
        fixture.fats = sourPatchFats;
        assertTrue("getFats() did not return expected value: " + sourPatchFats, fixture.getFats() == sourPatchFats);

        double fakeFruitFats = -17.0;
        fixture.fats = fakeFruitFats;
        assertTrue("getFats() did not return expected value: " + fakeFruitFats, fixture.getFats() == fakeFruitFats);

        double randomFats = Math.random();
        fixture.fats = randomFats;
        assertTrue("getFats() did not return expected value: " + randomFats, fixture.getFats() == randomFats);
    }

    @Test
    public void setFats() {
        System.out.println("**--- Test Ingredient.setFats() executed ---**");

        double specialFats = -3.7;
        fixture.setFats(specialFats);
        assertTrue("fixture.setFats did not set to expected value", fixture.fats == specialFats);

        double newFats = Math.random();
        fixture.setFats(newFats);
        assertTrue("fixture.setFats did not set to expected value", fixture.fats == newFats);

        double zeroFats = 0;
        fixture.setFats(zeroFats);
        assertTrue("fixture.setFats did not set to expected value", fixture.fats == zeroFats);
    }

    @Test
    public void getProtein() {
        System.out.println("**--- Test Ingredient.getProtein() executed ---**");

        assertTrue("Inital value of getProtein() was incorrect", fixture.getProtein() == baseProtein);

        double sourPatchProtein = 27.0;
        fixture.protein = sourPatchProtein;
        assertTrue("getProtein() did not return expected value: " + sourPatchProtein, fixture.getProtein() == sourPatchProtein);

        double fakeFruitProtein = -17.0;
        fixture.protein = fakeFruitProtein;
        assertTrue("getProtein() did not return expected value: " + fakeFruitProtein, fixture.getProtein() == fakeFruitProtein);

        double randomProtein = Math.random();
        fixture.protein = randomProtein;
        assertTrue("getProtein() did not return expected value: " + randomProtein, fixture.getProtein() == randomProtein);
    }

    @Test
    public void setProtein() {
        System.out.println("**--- Test Ingredient.setProtein() executed ---**");

        double specialProtein = -3.7;
        fixture.setProtein(specialProtein);
        assertTrue("fixture.setProtein did not set to expected value", fixture.protein == specialProtein);

        double newProtein = Math.random();
        fixture.setProtein(newProtein);
        assertTrue("fixture.setProtein did not set to expected value", fixture.protein == newProtein);

        double zeroProtein = 0;
        fixture.setProtein(zeroProtein);
        assertTrue("fixture.setProtein did not set to expected value", fixture.protein == zeroProtein);
    }

    @Test
    public void getCalories() {
        System.out.println("**--- Test Ingredient.getCalories() executed ---**");

        assertTrue("Inital value of getCalories() was incorrect", fixture.getCalories() == baseCalories);

        double sourPatchCalories = 27.0;
        fixture.calories = sourPatchCalories;
        assertTrue("getCalories() did not return expected value: " + sourPatchCalories, fixture.getCalories() == sourPatchCalories);

        double fakeFruitCalories = -17.0;
        fixture.calories = fakeFruitCalories;
        assertTrue("getCalories() did not return expected value: " + fakeFruitCalories, fixture.getCalories() == fakeFruitCalories);

        double randomCalories = Math.random();
        fixture.calories = randomCalories;
        assertTrue("getCalories() did not return expected value: " + randomCalories, fixture.getCalories() == randomCalories);
    }

    @Test
    public void setCalories() {
        System.out.println("**--- Test Ingredient.setCalories() executed ---**");

        double specialCalories = -3.7;
        fixture.setCalories(specialCalories);
        assertTrue("fixture.setCalories did not set to expected value", fixture.calories == specialCalories);

        double newCalories = Math.random();
        fixture.setCalories(newCalories);
        assertTrue("fixture.setCalories did not set to expected value", fixture.calories == newCalories);

        double zeroCalories = 0;
        fixture.setCalories(zeroCalories);
        assertTrue("fixture.setCalories did not set to expected value", fixture.calories == zeroCalories);
    }

    @Test
    public void getStores() {
        // TODO
    }

    @Test
    public void setStores() {
        // TODO
    }

    @Test
    public void getBrand() {
        System.out.println("**--- Test Ingredient.getBrand() executed ---**");

        assertTrue("Incorrect brand from fixture.getBrand()", fixture.getBrand().equals(baseBrand));

        String secondBrand = "Nabisco";
        fixture.brand = secondBrand;
        assertTrue("Incorrect brand from fixture.getBrand()", fixture.getBrand().equals(secondBrand));

        fixture.brand = null;
        assertNull("Expected null return of fixture.getBrand() was not null!", fixture.getBrand());
    }

    @Test
    public void setBrand() {
        System.out.println("**--- Test Ingredient.setBrand() executed ---**");
        fixture.setBrand(null);
        assertNull("fixture.brand was not set to null!", fixture.brand);

        String firstString = "Apple";
        fixture.setBrand(firstString);
        assertNotNull("fixture.brand was null", fixture.brand);
        assertTrue("Brand was not correctly set by fixture.setBrand(firstString)", fixture.brand.equals(firstString));

        String secondString = "CodersUnited";
        fixture.setBrand(secondString);
        assertFalse("Brand was not still set to firstString", fixture.brand.equals(firstString));
        assertTrue("Brand was not correctly set by fixture.setBrand(secondString)", fixture.brand.equals(secondString));
    }

    @Test
    public void getQtInPantry() {
        System.out.println("**--- Test Ingredient.getQtInPantry() executed ---**");

        assertTrue("Inital value of getQtInPantry() was incorrect", fixture.getQtInPantry() == baseQuantity);

        int sourPatchQtInPantry = 27;
        fixture.qtInPantry = sourPatchQtInPantry;
        assertTrue("getQtInPantry() did not return expected value: " + sourPatchQtInPantry, fixture.getQtInPantry() == sourPatchQtInPantry);

        int fakeFruitQtInPantry = -17;
        fixture.qtInPantry = fakeFruitQtInPantry;
        assertTrue("getQtInPantry() did not return expected value: " + fakeFruitQtInPantry, fixture.getQtInPantry() == fakeFruitQtInPantry);

        int randomQtInPantry = (int)Math.random();
        fixture.qtInPantry = randomQtInPantry;
        assertTrue("getQtInPantry() did not return expected value: " + randomQtInPantry, fixture.getQtInPantry() == randomQtInPantry);
    }

    @Test
    public void setQtInPantry() {
        System.out.println("**--- Test Ingredient.setQtInPantry() executed ---**");

        int specialQtInPantry = -37;
        fixture.setQtInPantry(specialQtInPantry);
        assertTrue("fixture.setQtInPantry did not set to expected value", fixture.qtInPantry == specialQtInPantry);

        int newQtInPantry = (int)Math.random();
        fixture.setQtInPantry(newQtInPantry);
        assertTrue("fixture.setQtInPantry did not set to expected value", fixture.qtInPantry == newQtInPantry);

        int zeroQtInPantry = 0;
        fixture.setQtInPantry(zeroQtInPantry);
        assertTrue("fixture.setQtInPantry did not set to expected value", fixture.qtInPantry == zeroQtInPantry);
    }

    @Test
    public void getSugars() {
        System.out.println("**--- Test Ingredient.getSugars() executed ---**");

        assertTrue("Inital value of getSugars() was incorrect", fixture.getSugars() == baseSugars);

        double sourPatchSugars = 27.0;
        fixture.sugars = sourPatchSugars;
        assertTrue("getSugars() did not return expected value: " + sourPatchSugars, fixture.getSugars() == sourPatchSugars);

        double fakeFruitSugars = -17.0;
        fixture.sugars = fakeFruitSugars;
        assertTrue("getSugars() did not return expected value: " + fakeFruitSugars, fixture.getSugars() == fakeFruitSugars);

        double randomSugars = Math.random();
        fixture.sugars = randomSugars;
        assertTrue("getSugars() did not return expected value: " + randomSugars, fixture.getSugars() == randomSugars);
    }

    @Test
    public void setSugars() {
        System.out.println("**--- Test Ingredient.setSugars() executed ---**");

        double specialSugars = -3.7;
        fixture.setSugars(specialSugars);
        assertTrue("fixture.setSugars did not set to expected value", fixture.sugars == specialSugars);

        double newSugars = Math.random();
        fixture.setSugars(newSugars);
        assertTrue("fixture.setSugars did not set to expected value", fixture.sugars == newSugars);

        double zeroSugars = 0;
        fixture.setSugars(zeroSugars);
        assertTrue("fixture.setSugars did not set to expected value", fixture.sugars == zeroSugars);
    }
}
