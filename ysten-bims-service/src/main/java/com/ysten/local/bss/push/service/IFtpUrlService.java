package com.ysten.local.bss.push.service;

import java.util.List;
import java.util.Map;

public interface IFtpUrlService {
    
    List<Map<String, String>> findFtpUrl(Map<String, Object> param);
    
}
