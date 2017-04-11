package com.bolanggu.bbl.output;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pabula.api.API;
import com.pabula.api.data.ReturnData;
import com.pabula.common.util.DateUtil;
import com.pabula.common.util.StrUtil;
import com.pabula.fw.exception.RuleException;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by 95155 on 2016/12/12.
 */
public class outputFinanceQuery {

    private static outputFinanceQuery bean;

    public static outputFinanceQuery newInstance() {
        if (null == bean) {
            synchronized (outputFinanceQuery.class) {
                if (null == bean) {
                    bean = new outputFinanceQuery();
                }
            }
        }
        return bean;
    }

    public SXSSFSheet GenerateExcelSheet(SXSSFWorkbook analyseBook, String parameter)
        throws RuleException {

        JSONObject paramJson = JSON.parseObject(parameter);

        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, Object> entry : paramJson.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }

        ReturnData outputDetail = new API().call("/member/balance/getAllBalanceList", map);
        //解析主要数据
        JSONArray jsonArray = JSONArray.parseArray(outputDetail.getData().toString());

        SXSSFSheet financeSheet = analyseBook.createSheet("资金明细表");
        financeSheet.setColumnWidth(0, 2000);
        financeSheet.setColumnWidth(1, 6000);
        financeSheet.setColumnWidth(2, 3000);
        financeSheet.setColumnWidth(3, 4000);
        financeSheet.setColumnWidth(4, 3000);
        financeSheet.setColumnWidth(5, 4000);
        financeSheet.setColumnWidth(6, 4000);
        financeSheet.setColumnWidth(7, 4000);
        financeSheet.setColumnWidth(8, 4000);
        financeSheet.setColumnWidth(9, 4000);
        financeSheet.setColumnWidth(10, 4000);
        financeSheet.setColumnWidth(11, 4000);
        financeSheet.setColumnWidth(12, 3000);
        financeSheet.setColumnWidth(13, 4000);
        financeSheet.setColumnWidth(14, 5000);
        financeSheet.setColumnWidth(15, 5000);

        //字体预设置
        XSSFFont font = (XSSFFont) analyseBook.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short) 14);

        XSSFFont font2 = (XSSFFont) analyseBook.createFont();
        font2.setFontName("微软雅黑");
        font2.setFontHeightInPoints((short) 12);

        XSSFFont font3 = (XSSFFont) analyseBook.createFont();
        font3.setFontName("微软雅黑");
        font3.setFontHeightInPoints((short) 11);

        XSSFFont font4 = (XSSFFont) analyseBook.createFont();
        font4.setFontName("微软雅黑");
        font4.setFontHeightInPoints((short) 11);
        font4.setColor(XSSFFont.COLOR_RED);
        //合并大标题的单元格
        financeSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 15));

        //大标题样式
        CellStyle titleStyle = analyseBook.createCellStyle();

        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        titleStyle.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
        titleStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        titleStyle.setFont(font);

        //第二行样式
        CellStyle title2Style = analyseBook.createCellStyle();
        title2Style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        title2Style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        title2Style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        title2Style.setFont(font2);

        //内容的样式
        CellStyle cellStyle = analyseBook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        cellStyle.setFont(font3);

        //总计的样式
        CellStyle totalStyle = analyseBook.createCellStyle();
        totalStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        totalStyle.setFont(font4);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        NumberFormat NumFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);

        int rowIndex = 0;//行数
        //地区分析开始
        SXSSFRow analyseTitle = financeSheet.createRow(rowIndex++);
        analyseTitle.setHeightInPoints(25);
        SXSSFCell cellTitle1 = analyseTitle.createCell(0);
        cellTitle1.setCellStyle(titleStyle);
        cellTitle1.setCellValue("资金明细记录");

        SXSSFRow rowTitle = financeSheet.createRow(rowIndex++);
        rowTitle.setHeightInPoints(25);
        SXSSFCell cellNum = rowTitle.createCell(0);
        cellNum.setCellValue("序号");
        cellNum.setCellStyle(title2Style);
        SXSSFCell cellArea = rowTitle.createCell(1);
        cellArea.setCellValue("时间");
        cellArea.setCellStyle(title2Style);
        SXSSFCell cellName = rowTitle.createCell(2);
        cellName.setCellValue("客户姓名");
        cellName.setCellStyle(title2Style);
        SXSSFCell cellPhone = rowTitle.createCell(3);
        cellPhone.setCellValue("手机号");
        cellPhone.setCellStyle(title2Style);
        SXSSFCell cellReturn = rowTitle.createCell(4);
        cellReturn.setCellValue("变更事件");
        cellReturn.setCellStyle(title2Style);
        SXSSFCell cellOrderPrice = rowTitle.createCell(5);
        cellOrderPrice.setCellValue("交易卡号");
        cellOrderPrice.setCellStyle(title2Style);
        SXSSFCell cellBalBe = rowTitle.createCell(6);
        cellBalBe.setCellValue("交易前余额");
        cellBalBe.setCellStyle(title2Style);
        SXSSFCell cellBalAf = rowTitle.createCell(7);
        cellBalAf.setCellValue("交易后余额");
        cellBalAf.setCellStyle(title2Style);
        SXSSFCell cellCardType = rowTitle.createCell(8);
        cellCardType.setCellValue("交易卡类别");
        cellCardType.setCellStyle(title2Style);
        SXSSFCell cellCardShop = rowTitle.createCell(9);
        cellCardShop.setCellValue("交易卡开卡门店");
        cellCardShop.setCellStyle(title2Style);
        SXSSFCell cellReturnPrice = rowTitle.createCell(10);
        cellReturnPrice.setCellValue("交易操作门店");
        cellReturnPrice.setCellStyle(title2Style);
        SXSSFCell operator = rowTitle.createCell(11);
        operator.setCellValue("操作员");
        operator.setCellStyle(title2Style);
        SXSSFCell type = rowTitle.createCell(12);
        type.setCellValue("变更类型");
        type.setCellStyle(title2Style);
        SXSSFCell money = rowTitle.createCell(13);
        money.setCellValue("变更金额");
        money.setCellStyle(title2Style);
        SXSSFCell balance = rowTitle.createCell(14);
        balance.setCellValue("账户余额");
        balance.setCellStyle(title2Style);
        SXSSFCell intro = rowTitle.createCell(15);
        intro.setCellValue("变更说明");
        intro.setCellStyle(title2Style);
        int analyseIndex = 1;//序号

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            String changeType;

            if ("increase".equals(jsonObject.getString("FINANCE_LIST.CHANGE_TYPE"))) {
                changeType = "收入";
            } else {
                changeType = "支出";
            }

            String cardBalance = StrUtil.getNotNullStringValue(
                jsonObject.getString("FINANCE_LIST.EVENT_CARD_BALANCE"), "");
            String cardBalanceBefore = "";
            if (!"".equals(cardBalance)) {
                cardBalance = "￥" + cardBalance;
                cardBalanceBefore = StrUtil.getNotNullStringValue(
                    jsonObject.getString("FINANCE_LIST.BEFORE_CARD_BALANCE"), "");
                cardBalanceBefore = "￥" + cardBalanceBefore;
            }

            String Balance =
                StrUtil.getNotNullStringValue(jsonObject.getString("FINANCE_LIST.BALANCE"));
            if (!"".equals(Balance)) {
                Balance = "￥" + Balance;
            }

            String cardNo =
                StrUtil.getNotNullStringValue(jsonObject.getString("FINANCE_LIST.EVENT_CARD_NO"),
                    "");
            System.out.println(" cardNo  :  " + cardNo);
            if (cardNo.length() == 12 || cardNo.length() == 14) {
                cardNo = cardNo.substring(0, cardNo.length() - 4);
            }
            System.out.println(" cardNo  end  :  " + cardNo);
            SXSSFRow row = financeSheet.createRow(rowIndex++);
            row.setHeightInPoints(25);
            SXSSFCell cell0 = row.createCell(0);
            cell0.setCellValue(analyseIndex++);
            cell0.setCellStyle(cellStyle);
            SXSSFCell cell1 = row.createCell(1);
            cell1.setCellValue(jsonObject.getString("FINANCE_LIST.ADD_DATETIME").replace(".0", ""));
            cell1.setCellStyle(cellStyle);
            SXSSFCell cell2 = row.createCell(2);
            cell2.setCellValue(
                StrUtil.getNotNullStringValue(jsonObject.getString("FINANCE_LIST.TRUE_NAME"), ""));
            cell2.setCellStyle(cellStyle);
            SXSSFCell cell3 = row.createCell(3);
            cell3.setCellValue(jsonObject.getString("FINANCE_LIST.MEMBER_MOBILE"));
            cell3.setCellStyle(cellStyle);
            SXSSFCell cell4 = row.createCell(4);
            cell4.setCellValue(jsonObject.getString("FINANCE_LIST.EVENT"));
            cell4.setCellStyle(cellStyle);
            SXSSFCell cell5 = row.createCell(5);
            cell5.setCellValue(cardNo);
            cell5.setCellStyle(cellStyle);
            SXSSFCell cell6 = row.createCell(6);
            cell6.setCellValue(cardBalanceBefore);
            cell6.setCellStyle(cellStyle);
            SXSSFCell cell7 = row.createCell(7);
            cell7.setCellValue(cardBalance);
            cell7.setCellStyle(cellStyle);
            SXSSFCell cell8 = row.createCell(8);
            cell8.setCellValue(
                StrUtil.getNotNullStringValue(jsonObject.getString("FINANCE_LIST.EVENT_CARD_TYPE"),
                    ""));
            cell8.setCellStyle(cellStyle);
            SXSSFCell cell9 = row.createCell(9);
            cell9.setCellValue(
                StrUtil.getNotNullStringValue(jsonObject.getString("FINANCE_LIST.CARD_SHOP"), ""));
            cell9.setCellStyle(cellStyle);
            SXSSFCell cell10 = row.createCell(10);
            cell10.setCellValue(
                StrUtil.getNotNullStringValue(jsonObject.getString("FINANCE_LIST.SHOP"), ""));
            cell10.setCellStyle(cellStyle);
            SXSSFCell cell11 = row.createCell(11);
            cell11.setCellValue(
                StrUtil.getNotNullStringValue(jsonObject.getString("FINANCE_LIST.OPER_USER_ID"),
                    ""));
            cell11.setCellStyle(cellStyle);
            SXSSFCell cell12 = row.createCell(12);
            cell12.setCellValue(changeType);
            cell12.setCellStyle(cellStyle);
            SXSSFCell cell13 = row.createCell(13);
            cell13.setCellValue(
                NumFormat.format(jsonObject.getDouble("FINANCE_LIST.CHANGE_VALUE")));
            cell13.setCellStyle(cellStyle);
            SXSSFCell cell14 = row.createCell(14);
            cell14.setCellValue(Balance);
            cell14.setCellStyle(cellStyle);
            SXSSFCell cell15 = row.createCell(15);
            cell15.setCellValue(
                StrUtil.getNotNullStringValue(jsonObject.getString("FINANCE_LIST.EVENT_INTRO"),""));
            cell15.setCellStyle(cellStyle);
        }

        SXSSFRow rowM = financeSheet.createRow(rowIndex);
        rowM.setHeightInPoints(25);
        //        Cell cellMan = rowM.createCell(0);
        //        cellMan.setCellValue("操作人：");
        //        cellMan.setCellStyle(cellStyle);
        //        Cell cellManValue = rowM.createCell(1);
        //        cellManValue.setCellValue("");
        //        cellManValue.setCellStyle(cellStyle);
        rowM.createCell(0).setCellValue("");
        rowM.createCell(1).setCellValue("");
        rowM.createCell(2).setCellValue("");
        rowM.createCell(3).setCellValue("");
        rowM.createCell(4).setCellValue("");
        rowM.createCell(5).setCellValue("");
        rowM.createCell(6).setCellValue("");
        rowM.createCell(7).setCellValue("");
        rowM.createCell(8).setCellValue("");
        rowM.createCell(9).setCellValue("");
        rowM.createCell(10).setCellValue("");
        rowM.createCell(11).setCellValue("");
        rowM.createCell(12).setCellValue("");
        rowM.createCell(13).setCellValue("");
        SXSSFCell cellTime = rowM.createCell(14);
        cellTime.setCellValue("导出时间：");
        cellTime.setCellStyle(cellStyle);
        SXSSFCell cellTimeValue = rowM.createCell(15);
        cellTimeValue.setCellValue(dateFormat.format(DateUtil.getCurrTime()));
        cellTimeValue.setCellStyle(cellStyle);

        return financeSheet;
    }
}
