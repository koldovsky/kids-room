package ua.softserveinc.tc.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ExcelDocument extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        int rowIndex = 0;
        String fileName = (String) model.get("fileName");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");
        Sheet excelSheet = workbook.createSheet("Report");

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);
        style.setAlignment(CellStyle.ALIGN_CENTER);

        Map<String, List<String>> dataForExcel = (Map<String, List<String>>) model.get("data");
        String[] headers = dataForExcel.keySet().toArray(new String[]{""});
        int tableHeights = dataForExcel.get(headers[0]).size();
        if (model.containsKey("additionalFields")) {
            List<String> additionalFields = (List<String>) model.get("additionalFields");
            rowIndex = writeAdditionalFields(excelSheet, style, additionalFields,
                    rowIndex, headers.length);
        }

        createRows(rowIndex, tableHeights, excelSheet);
        setExcelHeader(excelSheet, style, headers, rowIndex++);
        for (int i = 0; i < headers.length; i++) {
            writeColumnToExcel(excelSheet, dataForExcel.get(headers[i]), i, rowIndex);
        }
    }

    private int writeAdditionalFields(Sheet excelSheet, CellStyle style,
                                      List<String> strings, int rowIndex, int size) {

        for (String item: strings) {
            excelSheet.createRow(rowIndex);
            CellRangeAddress cellRangeAddress = new CellRangeAddress(rowIndex, rowIndex, 0, size - 1);
            excelSheet.addMergedRegion(cellRangeAddress);
            excelSheet.getRow(rowIndex).createCell(0).setCellValue(item);
            excelSheet.getRow(rowIndex++).getCell(0).setCellStyle(style);
        }

        return rowIndex;
    }

    private void writeColumnToExcel(Sheet excelSheet, List<String> columnData, int columnIndex,
                                    int startRow) {
        excelSheet.setColumnWidth(columnIndex, 5000);
        for (int i = 0; i < columnData.size(); i++) {
            Cell cell = excelSheet.getRow(startRow + i).createCell(columnIndex);
            cell.setCellValue(columnData.get(i));
        }
    }

    private void createRows(int start, int size, Sheet excelSheet) {
        for (int i = start; i <= start + size; i++) {
            excelSheet.createRow(i);
        }
    }

    public void setExcelHeader(Sheet excelSheet, CellStyle styleHeader, String[] headersList, int rowIndex) {

        int cellCount = 0;
        for (String s : headersList) {
            Cell cell = excelSheet.getRow(rowIndex).createCell(cellCount);
            cell.setCellValue(s);
            cell.setCellStyle(styleHeader);
            cellCount++;
        };
    }
}
