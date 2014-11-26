package com.ysten.local.bss.push.service;

import java.io.IOException;
import java.text.ParseException;

public interface IMailService {

    String shMailContent(String queryDate) throws ParseException;
    
    String c2MailContent(String queryDate, String title, String prefix, String webHttp) throws ParseException, IOException;
    
}
