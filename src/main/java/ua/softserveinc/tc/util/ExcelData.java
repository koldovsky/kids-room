package ua.softserveinc.tc.util;

import java.util.List;
import java.util.Map;

public interface ExcelData<T> {
    int COLUMN_WIDTH = 5000;

    void setTableData(List<T> list);

    Map<String, List<String>> getTableData();

    List<String> getAdditionalFields();

    void addAdditionalFields(String fields);

    String[] getHeaders();

    int getSize();

    default boolean hasAdditionalFields() {
        return false;
    }
}
