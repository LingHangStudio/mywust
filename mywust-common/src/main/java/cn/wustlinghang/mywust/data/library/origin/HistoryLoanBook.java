package cn.wustlinghang.mywust.data.library.origin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryLoanBook extends BaseLoanBook {
    private String returnDate;
}
