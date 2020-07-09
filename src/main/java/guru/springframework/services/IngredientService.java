package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(long recipeId, long ingredientId);
}
