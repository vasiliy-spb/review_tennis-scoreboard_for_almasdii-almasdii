package TableTennis.dto;

import TableTennis.entity.MatchEntity;

import java.util.List;

public record PaginationData(
        List<MatchEntity> matchEntities,
        int numerOfPages,
        int pageSize,
        int pageNumber
)
{
}
