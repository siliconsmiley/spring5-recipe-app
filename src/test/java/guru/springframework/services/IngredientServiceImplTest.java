package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {
    public static final long RECIPE_ID = 1L;
    public static final long INGREDIENT_ID1 = 1L;
    public static final long INGREDIENT_ID2 = 2L;
    public static final long INGREDIENT_ID3 = 3L;

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    IngredientServiceImpl ingredientService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    // initialize converter
    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand, ingredientCommandToIngredient, unitOfMeasureRepository);
    }

    @Test
    public void findByRecipeIdAndIngredientIdHappyPath() throws Exception {
        // given
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(INGREDIENT_ID1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(INGREDIENT_ID2);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(INGREDIENT_ID3);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        // when
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        // then
        IngredientCommand ingredientCommand1 = ingredientService.findByRecipeIdAndIngredientId(RECIPE_ID, INGREDIENT_ID1);
        IngredientCommand ingredientCommand2 = ingredientService.findByRecipeIdAndIngredientId(RECIPE_ID, INGREDIENT_ID2);
        IngredientCommand ingredientCommand3 = ingredientService.findByRecipeIdAndIngredientId(RECIPE_ID, INGREDIENT_ID3);

        assertEquals(Long.valueOf(RECIPE_ID), ingredientCommand1.getRecipeId());
        assertEquals(Long.valueOf(INGREDIENT_ID1), ingredient1.getId());
        assertEquals(Long.valueOf(RECIPE_ID), ingredientCommand2.getRecipeId());
        assertEquals(Long.valueOf(INGREDIENT_ID2), ingredient2.getId());
        assertEquals(Long.valueOf(RECIPE_ID), ingredientCommand3.getRecipeId());
        assertEquals(Long.valueOf(INGREDIENT_ID3), ingredient3.getId());
        verify(recipeRepository, times(3)).findById(anyLong());
    }

    @Test
    public void testSaveRecipeCommand() throws Exception {
        // given
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        // when
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        // then
        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
}
