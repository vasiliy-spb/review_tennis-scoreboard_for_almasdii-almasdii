package TableTennis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PaginationDto<T> {
    private List<T> matches;
    private int numberOfPages;
}
