package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }


    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(long recipeId, long ingredientId) {

        Optional<Recipe> recipesOptional = recipeRepository.findById(recipeId);

        if (!recipesOptional.isPresent()) {
            // TODO implement error handling
            log.error("recipe id not found for: " + recipeId);
        }

        Recipe recipe = recipesOptional.get();

        // TODO investigate Java streams
        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if (!ingredientCommandOptional.isPresent()) {
            // TODO implement error handling
            log.error("Ingredient id not found: " + ingredientId);
        }

        return ingredientCommandOptional.get();
    }

    @Override
    // transactional added to prevent lazy properties causing problems when conversions happen outside of scope i.e. tests
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
        if (!recipeOptional.isPresent()) {
            // TODO implement error handling
            log.error("recipe not found for id: " + command.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();
            // TODO research stream, lambda
            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            // update existing ingredient
            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                // find existing UOM, or throw exception if UOM not found
                // TODO fix this to handle UOM not found
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(command.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("OUM NOT FOUND")));

            } else {
                // add new ingredient
                recipe.addIngredient(ingredientCommandToIngredient.convert(command));
            }
            Recipe savedRecipe = recipeRepository.save(recipe);
            log.debug("saved ingredients for recipe id: " + savedRecipe.getId());

            // TODO check for failure
            return ingredientToIngredientCommand.convert(savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst().get());
        }

    }

}
