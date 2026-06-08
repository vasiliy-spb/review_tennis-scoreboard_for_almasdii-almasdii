package TableTennis.mapper;

import TableTennis.dto.MatchResponse;
import TableTennis.dto.PaginationData;
import TableTennis.dto.PaginationDto;
import TableTennis.entity.MatchEntity;

import java.util.List;


public class PaginationDtoMapper implements Mapper<PaginationDto, PaginationData>{
    private final MatchResponseMapper matchResponseMapper = new MatchResponseMapper();
    @Override
    public PaginationDto mapFrom(PaginationData data) {
        List<MatchResponse> list = data.matchEntities().stream().map(matchResponseMapper::mapFrom).toList();
        return new PaginationDto(list,data.numerOfPages(),data.pageSize(),data.pageNumber());
    }
}
