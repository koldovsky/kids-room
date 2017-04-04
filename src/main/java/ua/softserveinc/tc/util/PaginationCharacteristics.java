package ua.softserveinc.tc.util;

import org.apache.commons.lang3.StringUtils;
import ua.softserveinc.tc.entity.pagination.SortingPagination.*;


import java.util.List;

public final class PaginationCharacteristics {
    public static long searchCount = 0;

    public static long definePage(long start, long paginationLength, long itemsLength) {
//        long pagesCount = (long) Math.ceil((double) itemsLength / paginationLength);
        long ret = start == 0 ? 0 : (long) Math.ceil((double)(start + 1)/paginationLength);
        System.out.println(ret);
        return ret;
//        return (long) Math.ceil((double)(start + 1)/paginationLength);
    }

//    public static boolean isSearched(List<Search> searches) {
//        return !searches.parallelStream()
//                .map(Search::getColumn)
//                .allMatch(StringUtils::isEmpty);
//    }
}
