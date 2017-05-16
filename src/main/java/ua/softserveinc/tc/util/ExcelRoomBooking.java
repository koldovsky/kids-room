package ua.softserveinc.tc.util;

import org.springframework.stereotype.Component;
import ua.softserveinc.tc.constants.ExcelConstants;
import ua.softserveinc.tc.dto.RoomReportValuesDto;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.util.ExcelData;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ExcelRoomBooking implements ExcelData<RoomReportValuesDto> {
    private Map<String, List<String>> tableData;
    private List<String> additionalFields;

    @Override
    public void setTableData(List<RoomReportValuesDto> list) {
        tableData = new LinkedHashMap<>();
        additionalFields = new ArrayList<>();

        tableData.put(ExcelConstants.Headers.PARENT, list.stream().map(RoomReportValuesDto::getUser)
                .map(User::getFullName).collect(Collectors.toList()));
        tableData.put(ExcelConstants.Headers.EMAIL, list.stream().map(RoomReportValuesDto::getUser)
                .map(User::getEmail).collect(Collectors.toList()));
        tableData.put(ExcelConstants.Headers.ABONNEMENT, list.stream()
                .map(RoomReportValuesDto::getStringAbonnementHours)
                .collect(Collectors.toList()));
        tableData.put(ExcelConstants.Headers.BOOKING_TIME, list.stream()
                .map(RoomReportValuesDto::getStringAbonnementHours)
                .collect(Collectors.toList()));
        tableData.put(ExcelConstants.Headers.SUM, list.stream().map(RoomReportValuesDto::getStringSum)
                .collect(Collectors.toList()));
    }

    @Override
    public void addAdditionalFields(String field) {
        additionalFields.add(field);
    }

    @Override
    public Map<String, List<String>> getTableData() {
        return this.tableData;
    }

    @Override
    public List<String> getAdditionalFields() {
        return this.additionalFields;
    }

    @Override
    public String[] getHeaders() {
        return tableData.keySet().toArray(new String[]{""});
    }

    @Override
    public int getSize() {
        return tableData.get(getHeaders()[0]).size();
    }

    @Override
    public boolean hasAdditionalFields() {
        return !additionalFields.isEmpty();
    }
}
