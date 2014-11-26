package com.ysten.local.bss.push.service;

import java.util.List;
import java.util.Map;

public interface ICdnTaskService {
    
    String[] inject(List<Map<String, Object>> params);

}
