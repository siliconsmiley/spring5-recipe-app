package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long id, MultipartFile file) {
        log.debug("received file id: " + String.valueOf(id));
        try {
            Recipe recipe = recipeRepository.findById(id).get();

            Byte[] bytes = new Byte[file.getBytes().length];
            int index = 0;
            for (byte nextByte : file.getBytes()) {
                bytes[index++] = nextByte;
            }

            recipe.setImage(bytes);
            recipeRepository.save(recipe);
        } catch (IOException e) {
            // TODO more graceful exception handling
            log.error("IO Exception", e);
            e.printStackTrace();
        }
    }
}
