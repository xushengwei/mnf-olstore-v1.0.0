package com.mshd.olstore.event;

public class RefreshTabCountEvent {
    public String status;
    public int tabCount;

    public RefreshTabCountEvent(String status ,int tabCount) {
        this.tabCount = tabCount;
        this.status=status;
    }
}
