package com.bcstech.learning;

/**
 * @ClassdName ItemInfo
 * @Description 表达式单元
 * @Author zhangcq
 * @Date 2022/10/11
 */
public class ItemInfo {
    private ItemType type;          // 单一类型
    private String content;         // 单一内容
    private int nextIndex;          // 表达式的下一个位置。

    public ItemInfo() {
    }

    public ItemInfo( String content, ItemType type ) {
        this.content = content;
        this.type = type;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNextIndex() {
        return nextIndex;
    }

    public void setNextIndex(int nextIndex) {
        this.nextIndex = nextIndex;
    }

    @Override
    public String toString()
    {
        return content+":"+type;
    }
}
