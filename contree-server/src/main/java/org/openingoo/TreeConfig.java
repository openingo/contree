package org.openingoo;

import org.openingo.jdkits.http.RespData;
import org.springframework.context.annotation.Configuration;

/**
 * TreeConfig
 *
 * @author zhucongqi
 */
@Configuration
public class TreeConfig {

    public TreeConfig() {
        RespData.Config.SC_KEY = "code";
        RespData.Config.SM_KEY = "msg";
        RespData.Config.FAILURE_SC = 1;
        RespData.Config.SUCCESS_SC = 0;
        RespData.Config.SUCCESS_SM = "操作成功";
        RespData.Config.FRIENDLY_FAILURE_MESSAGE = null;
    }
}
