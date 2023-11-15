package cn.dbdj1201.restart2022.vto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: yz1201
 * @Date: 2023/11/15 14:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Translation {

    /*
    {
    "from": "en",
    "to": "zh",
    "trans_result": [
        {
            "src": "apple",
            "dst": "苹果"
        }
    ]
}
     */
    @JSONField
    private String from;
    @JSONField
    private String to;
    @JSONField(name = "trans_result")
    private List<TranslationContent> translationContents;
}
