package com.bytehonor.sdk.jdbc.bytehonor.constant;

/**
 * 
 * @author lijianqiang
 *
 */
public enum QueryLogicEnum {

    AND("AND", "AND"),

    OR("OR", "OR"),

    ;

    private String key;

    private String name;

    private QueryLogicEnum(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public static QueryLogicEnum keyOf(String key) {
        if (key == null) {
            return AND;
        }
        key = key.toLowerCase();
        for (QueryLogicEnum item : QueryLogicEnum.values()) {
            if (item.getKey().equals(key)) {
                return item;
            }
        }
        return AND;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
