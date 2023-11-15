package cn.dbdj1201.restart2022.service;

import cn.dbdj1201.restart2022.vto.Translation;
import org.springframework.stereotype.Component;

/**
 * @Author: yz1201
 * @Date: 2023/11/10 14:08
 */
@Component
public interface TranslateService {

    public Translation translate();
}
