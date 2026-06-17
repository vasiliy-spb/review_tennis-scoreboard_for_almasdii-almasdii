package TableTennis.dto;

import java.util.List;

public record PaginationDto(List<MatchResponse> currentPageMatches, int numberOfPages /* Возможно более однозначным было бы название totalPages */, int currentPage, int pageSize) {
}
