package com.artemzin.qualitymatters.developer_settings;

public class NoOpStrictModeNotifierProxy implements StrictModeNotifierProxy {
    @Override
    public void apply() {
        // no-op.
    }
}
