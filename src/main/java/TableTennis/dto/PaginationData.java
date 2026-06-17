package TableTennis.dto;

import TableTennis.entity.MatchEntity;

import java.util.List;

public record PaginationData(
        List<MatchEntity> matchEntities,
        int numerOfPages, // 1. Опечатка: numerOfPages —> numberOfPages 2. Возможно более однозначным было бы название totalPages
        int pageSize,
        int pageNumber
)
{
}
