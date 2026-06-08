package TableTennis.dto;

import java.util.List;

public record PaginationDto(List<MatchResponse> currentPageMatches, int numberOfPages, int currentPage, int pageSize) {
}
