package cn.dbdj1201.restart2022.controller;

import cn.dbdj1201.restart2022.entity.CommonResponse;
import cn.dbdj1201.restart2022.service.TranslateService;
import cn.dbdj1201.restart2022.vto.Translation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yz1201
 * @Date: 2023/11/10 11:22
 */
@Slf4j
@RestController
@RequestMapping("/translate")
public class TranslateController {

    @Autowired
    private TranslateService translateService;

    @GetMapping("/aphorisms")
    public CommonResponse getAphorisms() {
        Translation translate = this.translateService.translate();
        if (translate == null) {
            return CommonResponse.error("服务器故障");
        } else {
            return CommonResponse.ok().put("result", translate.getTranslationContents());
        }
    }
}
