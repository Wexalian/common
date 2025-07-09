package com.wexalian.common.gson.adapter.impl;

import com.wexalian.common.gson.TypeAdapterManager;
import com.wexalian.common.gson.adapter.IGsonTypeAdapter;

import java.time.Instant;

public class BuiltinTypeAdapterRegistry implements IGsonTypeAdapter.Registry {
    @Override
    public void register(TypeAdapterManager.Adapters adapters) {
        adapters.register(new DurationTypeAdapter());
        
        adapters.register(Instant.class, Instant::parse, Instant::toString);
    }
}
