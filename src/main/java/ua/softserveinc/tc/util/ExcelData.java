package ua.softserveinc.tc.util;

import ua.softserveinc.tc.entity.User;

import java.util.List;
import java.util.Map;

public interface ExcelData<T> {
    int COLUMN_WIDTH = 5000;

    void setTableData(List<T> list);

    void setTableData(Map<User, Long> report);

    Map<String, List<String>> getTableData();

    List<String> getAdditionalFields();

    void addAdditionalFields(String field);

    String[] getHeaders();

    int getSize();

    default boolean hasAdditionalFields() {
        return false;
    }
}