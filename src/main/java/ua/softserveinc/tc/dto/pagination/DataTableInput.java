package ua.softserveinc.tc.dto.pagination;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class DataTableInput implements Serializable {

    @NotNull
    @Min(0)
    private Integer draw = 1;

    @NotNull
    @Min(0)
    private Integer start = 0;

    @NotNull
    @Min(-1)
    private Integer length = 10;

    @NotNull
    private Search search = new Search();

    @NotEmpty
    private List<Order> order = new ArrayList<>();

    @NotEmpty
    private List<Column> columns = new ArrayList<>();

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "DataTableInput{" +
                "draw=" + draw +
                ", start=" + start +
                ", length=" + length +
                ", search=" + search +
                ", order=" + order +
                ", columns=" + columns +
                '}';
    }
}
