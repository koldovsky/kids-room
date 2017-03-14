package ua.softserveinc.tc.dto.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Column {

    @NotBlank
    private String data;

    private String name;

    @NotNull
    private Boolean searchable;

    @NotNull
    private Boolean orderable;

    @NotNull
    private Search search;

    public void setSearchValue(String searchValue) {
        this.search.setValue(searchValue);
    }

    @Override
    public String toString() {
        return "Column{" +
                "data='" + data + '\'' +
                ", name='" + name + '\'' +
                ", searchable=" + searchable +
                ", orderable=" + orderable +
                ", search=" + search +
                '}';
    }
}