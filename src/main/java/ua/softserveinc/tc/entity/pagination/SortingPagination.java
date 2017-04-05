package ua.softserveinc.tc.entity.pagination;


import java.util.List;

public class SortingPagination {

    private List<Sorting> sortings;
    private List<Search> searches;
    private Pagination pagination;

    public class Sorting {
        private int direction;
        private String column;

        public Sorting() {
        }

        public int getDirection() {
            return direction;
        }

        public void setDirection(int direction) {
            this.direction = direction;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        @Override
        public String toString() {
            return "Sorting{" +
                    "direction=" + direction +
                    ", column='" + column + '\'' +
                    '}';
        }
    }

    public class Search {
        private String value;
        private String column;

        public Search() {
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        @Override
        public String toString() {
            return "Search{" +
                    "value='" + value + '\'' +
                    ", column='" + column + '\'' +
                    '}';
        }
    }

    public class Pagination {
        private int start;
        private int itemsPerPage;

        public Pagination() {
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getItemsPerPage() {
            return itemsPerPage;
        }

        public void setItemsPerPage(int itemsPerPage) {
            this.itemsPerPage = itemsPerPage;
        }

        @Override
        public String toString() {
            return "Pagination{" +
                    "start=" + start +
                    ", itemsPerPage=" + itemsPerPage +
                    '}';
        }
    }

    public List<Sorting> getSortings() {
        return sortings;
    }

    public void setSortings(List<Sorting> sortings) {
        this.sortings = sortings;
    }

    public List<Search> getSearches() {
        return searches;
    }

    public void setSearches(List<Search> searches) {
        this.searches = searches;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    @Override
    public String toString() {
        return "SortingPagination{" +
                "sortings=" + sortings +
                ", searches=" + searches +
                ", pagination=" + pagination +
                '}';
    }
}
