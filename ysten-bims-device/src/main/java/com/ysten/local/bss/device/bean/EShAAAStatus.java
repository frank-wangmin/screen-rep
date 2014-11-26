package com.ysten.local.bss.device.bean;

import com.ysten.utils.bean.IEnumDisplay;

public class EShAAAStatus {

    /**
     * 机顶盒同步接口，请求类型
     */
    public enum EWebTvStbSyncStatus implements IEnumDisplay {
        WEBTVSTBSYNC_REGISTER("注册"), WEBTVSTBSYNC_REMOVE_REGISTER("解注册"), WEBTVSTBSYNC_REMOVE("清除"), 
        WEBTVSTBSYNC_ADD("加装"), WEBTVSTBSYNC_PREPARE_OPEN("预开通");
      
        private String message;

        private EWebTvStbSyncStatus(String message) {
            this.message = message;
        }

        public String getDisplayName() {
            return this.message;
        }
    }
    
    /**
     * 是否预开通
     * @author John
     *
     */
    public enum EPrepareOpen implements IEnumDisplay {
        NOT_OPEN("未开通"), PREPARE_OPEN("预开通"), FORMAL_OPEN("正式开通");
      
        private String message;

        private EPrepareOpen(String message) {
            this.message = message;
        }

        public String getDisplayName() {
            return this.message;
        }
    }

}
