package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>(2);

        Optional<UnitOfMeasure> eachUnitOfMeasureOption = validateUnitOfMeasure("Each");
        Optional<UnitOfMeasure> tablespoonUnitOfMeasureOptional = validateUnitOfMeasure("Tablespoon");
        Optional<UnitOfMeasure> teaspoonUnitOfMeasureOptional = validateUnitOfMeasure("Teaspoon");
        Optional<UnitOfMeasure> dashUnitOfMeasureOptional = validateUnitOfMeasure("Dash");
        Optional<UnitOfMeasure> pintUnitOfMeasureOptional = validateUnitOfMeasure("Pint");
        Optional<UnitOfMeasure> cupUnitOfMeasureOptional = validateUnitOfMeasure("Cup");

        UnitOfMeasure eachUnitOfMeasure = eachUnitOfMeasureOption.get();
        UnitOfMeasure tablespoonUnitOfMeasure = tablespoonUnitOfMeasureOptional.get();
        UnitOfMeasure teaspoonUnitOfMeasure = teaspoonUnitOfMeasureOptional.get();
        UnitOfMeasure dashUnitOfMeasure = dashUnitOfMeasureOptional.get();
        UnitOfMeasure pintUnitOfMeasure = pintUnitOfMeasureOptional.get();
        UnitOfMeasure cupUnitOfMeasure = cupUnitOfMeasureOptional.get();

        Category americanCategory = validateCategory("American").get();
        Category mexicanCategory = validateCategory("Mexican").get();

        //Yummy Guac
        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd");

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws");
        guacNotes.setRecipe(guacRecipe);
        guacRecipe.setNotes(guacNotes);

        guacRecipe.getIngredients().add(new Ingredient("ripe avocado", new BigDecimal(2), eachUnitOfMeasure, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredient("kosher salt", new BigDecimal(".5"), teaspoonUnitOfMeasure, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredient("fresh lime juice", new BigDecimal(2), tablespoonUnitOfMeasure, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredient("minced red onion", new BigDecimal(2), tablespoonUnitOfMeasure, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredient("serrano chiles, seeds removed, minced", new BigDecimal(2), eachUnitOfMeasure, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredient("cilantro", new BigDecimal(2), tablespoonUnitOfMeasure, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredient("freshly grated black pepper", new BigDecimal(1), dashUnitOfMeasure, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), eachUnitOfMeasure, guacRecipe));

        guacRecipe.getCategories().add(americanCategory);
        guacRecipe.getCategories().add(mexicanCategory);

        recipes.add(guacRecipe);

        //Yummy Tacos
        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Spicy Grilled Chicken Taco");
        tacosRecipe.setCookTime(9);
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setDifficulty(Difficulty.MODERATE);

        tacosRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm");

        Notes tacoNotes = new Notes();
        tacoNotes.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ");
        tacoNotes.setRecipe(tacosRecipe);
        tacosRecipe.setNotes(tacoNotes);


        tacosRecipe.getIngredients().add(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tablespoonUnitOfMeasure, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("Dried Oregano", new BigDecimal(1), teaspoonUnitOfMeasure, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("Dried Cumin", new BigDecimal(1), teaspoonUnitOfMeasure, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("Sugar", new BigDecimal(1), teaspoonUnitOfMeasure, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("Salt", new BigDecimal(".5"), teaspoonUnitOfMeasure, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), eachUnitOfMeasure, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("finely grated orange zestr", new BigDecimal(1), tablespoonUnitOfMeasure, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tablespoonUnitOfMeasure, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("Olive Oil", new BigDecimal(2), tablespoonUnitOfMeasure, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("boneless chicken thighs", new BigDecimal(4), tablespoonUnitOfMeasure, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("small corn tortillasr", new BigDecimal(8), eachUnitOfMeasure, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("packed baby arugula", new BigDecimal(3), cupUnitOfMeasure, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), eachUnitOfMeasure, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("radishes, thinly sliced", new BigDecimal(4), eachUnitOfMeasure, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), pintUnitOfMeasure, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), eachUnitOfMeasure, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), eachUnitOfMeasure, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cupUnitOfMeasure, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("lime, cut into wedges", new BigDecimal(4), eachUnitOfMeasure, tacosRecipe));

        tacosRecipe.getCategories().add(americanCategory);
        tacosRecipe.getCategories().add(mexicanCategory);

        recipes.add(tacosRecipe);

        return recipes;
    }

    /**
     * Validate unit of measure exists.
     *
     * @param description Unit of measure name or description
     * @return Optional unit of measure if it exists
     * @throws RuntimeException if unit of measure does not exist
     */
    private Optional<UnitOfMeasure> validateUnitOfMeasure(String description) throws RuntimeException {
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription(description);
        if (!unitOfMeasureOptional.isPresent()) {
            throw new RuntimeException(description + " unit of measure not found");
        }
        return unitOfMeasureOptional;
    }

    /**
     * Validate category exists
     *
     * @param description Category name or description
     * @return Optional category if it exists
     * @throws RuntimeException if category does not exist
     */
    private Optional<Category> validateCategory(String description) throws RuntimeException {
        Optional<Category> categoryOptional = categoryRepository.findByDescription(description);
        if (!categoryOptional.isPresent()) {
            throw new RuntimeException(description + " category not found");
        }
        return categoryOptional;
    }

}
