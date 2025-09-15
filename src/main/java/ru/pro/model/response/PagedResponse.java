package ru.pro.model.response;

import com.fasterxml.jackson.annotation.JsonView;
import ru.pro.views.Views;

import java.util.List;

@JsonView(Views.UserSummary.class)
public record PagedResponse<T>(
        List<T> content,
        long totalElements,
        int totalPages,
        int number,
        int size
) {
}
