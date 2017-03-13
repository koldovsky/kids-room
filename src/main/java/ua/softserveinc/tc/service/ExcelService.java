package ua.softserveinc.tc.service;

import java.util.List;
import java.util.Map;

public interface ExcelService<T> {
    Map<String, List<String>> getDataFromDto(List<T> t);
}
