package ua.softserveinc.tc.entity.pagination;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.Collections;
import java.util.List;

public class DataTableOutput<T> {

    @JsonView(View.class)
    private long draw;

    @JsonView(View.class)
    private long recordsTotal = 0L;

    @JsonView(View.class)
    private long recordsFiltered = 0L;

    @JsonView(View.class)
    private List<T> data = Collections.emptyList();

    @JsonView(View.class)
    private String error;

    public interface View {
    }

    public DataTableOutput() {
    }

    public DataTableOutput(long draw, long recordsTotal, long recordsFiltered, List<T> data) {
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.data = data;
    }

    public long getDraw() {
        return draw;
    }

    public void setDraw(long draw) {
        this.draw = draw;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "DataTableOutput{" +
                "draw=" + draw +
                ", recordsTotal=" + recordsTotal +
                ", recordsFiltered=" + recordsFiltered +
                ", data=" + data +
                ", error='" + error + '\'' +
                '}';
    }
}
