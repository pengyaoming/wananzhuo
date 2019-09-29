package com.jxmfkj.www.myapplication.Entity;

public class MessageEvent {
    private boolean isErr;

    public MessageEvent(boolean isErr) {
        this.isErr = isErr;
    }

    public boolean isErr() {
        return isErr;
    }

    public void setErr(boolean err) {
        isErr = err;
    }
}
