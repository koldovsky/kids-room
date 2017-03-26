package ua.softserveinc.tc.entity.pagination;


import java.util.List;

public class SortingPagination {

    List<Sorting> sortings;
    List<Search> searches;
    Pagination pagination;

    class Sorting {
        String direction;
        String column;
    }

    class Search {
        String column;
        String value;
    }

    class Pagination {
        long currentPage;
        long itemsPerPage;
    }
}
