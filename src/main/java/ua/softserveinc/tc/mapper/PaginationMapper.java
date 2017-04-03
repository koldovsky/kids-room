package ua.softserveinc.tc.mapper;

import org.springframework.stereotype.Component;
import ua.softserveinc.tc.entity.pagination.SortingPagination;
import ua.softserveinc.tc.util.JsonUtil;

@Component
public class PaginationMapper {
    public SortingPagination mapSortingPaginationFromJson(String json) {
        return JsonUtil.fromJson(json, SortingPagination.class);
    }
}
