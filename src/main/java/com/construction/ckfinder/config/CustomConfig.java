package com.construction.ckfinder.config;

import com.cksource.ckfinder.config.Config;

public class CustomConfig extends Config {
    private boolean enabled = false;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
