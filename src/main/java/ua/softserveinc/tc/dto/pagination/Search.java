package ua.softserveinc.tc.dto.pagination;

public class Search {
    private boolean regex;
    private String value;

    public Search() {
    }

    public Search(boolean regex, String value) {
        this.regex = regex;
        this.value = value;
    }

    public boolean isRegex() {
        return regex;
    }

    public void setRegex(boolean regex) {
        this.regex = regex;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Search{" +
                "regex=" + regex +
                ", value='" + value + '\'' +
                '}';
    }
}
