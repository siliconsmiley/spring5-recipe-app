package guru.springframework.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recipe recipe;

    @Lob
    private String recipeNotes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getRecipeNotes() {
        return recipeNotes;
    }

    public void setRecipeNotes(String notes) {
        this.recipeNotes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notes notes1 = (Notes) o;
        return Objects.equals(id, notes1.id) &&
                Objects.equals(recipeNotes, notes1.recipeNotes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recipeNotes);
    }

    @Override
    public String toString() {
        return "Notes{" +
                "id=" + id +
                ", notes='" + recipeNotes + '\'' +
                '}';
    }
}
