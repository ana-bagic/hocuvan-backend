package fer.proinz.hocuvan.helpers;

import fer.proinz.hocuvan.domain.VisitorPreference;

import java.util.ArrayList;
import java.util.List;

public class CategoryDTO {
    Long categoryId;
    String categoryName;

    public CategoryDTO() {
    }

    public CategoryDTO(Long id, String name) {
        this.categoryId = id;
        this.categoryName = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long id) {
        this.categoryId = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String name) {
        this.categoryName = name;
    }

    public static List<CategoryDTO> fromVisitorPreferencesToCategories(List<VisitorPreference> visitorPreferences) {
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for(VisitorPreference vp : visitorPreferences){
            categoryDTOS.add(new CategoryDTO(vp.getCategoryId().getCategoryId(), vp.getCategoryId().getCategoryName()));
        }
        return categoryDTOS;
    }
}
