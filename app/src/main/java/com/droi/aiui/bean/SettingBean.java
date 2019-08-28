package com.droi.aiui.bean;

import java.util.List;

/**
 * Created by hejianfeng on 2018/01/09.
 */

public class SettingBean extends BaseBean {

    /**
     * category : CAPPU.system_settings
     * intentType : custom
     * query : ������Ļ����
     * query_ws : ����/VO// ��Ļ/NN// ����/NN//
     * nlis : true
     * vendor : CAPPU
     * version : 2.0
     * semantic : [{"entrypoint":"ent","intent":"setting_intent","score":1,"slots":[{"begin":0,"end":2,"name":"operation","normValue":"����","va;;lue":"����"},{"begin":2,"end":6,"name":"type","normValue":"����","value":"��Ļ����"}]}]
     * state : null
     */

    private List<SemanticBean> semantic;

    public List<SemanticBean> getSemantic() {
        return semantic;
    }

    public void setSemantic(List<SemanticBean> semantic) {
        this.semantic = semantic;
    }

    public static class SemanticBean {
        /**
         * entrypoint : ent
         * intent : setting_intent
         * score : 1
         * slots : [{"begin":0,"end":2,"name":"operation","normValue":"����","va;;lue":"����"},{"begin":2,"end":6,"name":"type","normValue":"����","value":"��Ļ����"}]
         */

        private String intent;
        private List<SlotsBean> slots;

        public String getIntent() {
            return intent;
        }

        public void setIntent(String intent) {
            this.intent = intent;
        }

        public List<SlotsBean> getSlots() {
            return slots;
        }

        public void setSlots(List<SlotsBean> slots) {
            this.slots = slots;
        }

        public static class SlotsBean {
            /**
             * begin : 0
             * end : 2
             * name : operation
             * normValue : ����
             * va;;lue : ����
             * value : ��Ļ����
             */

            private String name;
            private String normValue;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNormValue() {
                return normValue;
            }

            public void setNormValue(String normValue) {
                this.normValue = normValue;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}