package com.wexalian.common.gson.adapter.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.wexalian.common.gson.adapter.AbstractTypeAdapter;

import java.lang.reflect.Type;
import java.time.Duration;

public class DurationTypeAdapter extends AbstractTypeAdapter<Duration> {
    @Override
    public Type getType() {
        return Duration.class;
    }
    
    @Override
    protected Duration fromJson(JsonElement json) {
        String duration = json.getAsString();
        try {
            String[] durationParts = duration.split(":");
            int hours = Integer.parseInt(durationParts[0]);
            int minutes = Integer.parseInt(durationParts[1]);
            int seconds = Integer.parseInt(durationParts[2]);
            return Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds);
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException("Failed parsing '" + duration + "' as Duration, format is hh:mm:ss", e);
        }
    }
    
    @Override
    protected JsonElement toJson(Duration src) {
        String value = String.format("%02d:%02d:%02d", src.toHours(), src.toMinutesPart(), src.toSecondsPart());
        return new JsonPrimitive(value);
    }
}
