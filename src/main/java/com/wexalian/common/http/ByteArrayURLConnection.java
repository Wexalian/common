package com.wexalian.common.http;

import com.wexalian.nullability.annotations.Nonnull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.function.Supplier;

public class ByteArrayURLConnection extends URLConnection {
    private final Supplier<byte[]> byteSupplier;

    private ByteArrayURLConnection(URL url, Supplier<byte[]> byteSupplier) {
        super(url);
        this.byteSupplier = byteSupplier;
    }

    @Override
    public void connect() {}

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(byteSupplier.get());
    }

    @Nonnull
    public static ByteArrayURLConnection ofText(@Nonnull URL url, @Nonnull CharSequence text) {
        return new ByteArrayURLConnection(url, () -> text.toString().getBytes());
    }

    @Nonnull
    public static ByteArrayURLConnection ofText(@Nonnull URL url, @Nonnull Supplier<? extends CharSequence> textSupplier) {
        return new ByteArrayURLConnection(url, () -> textSupplier.get().toString().getBytes());
    }

    @Nonnull
    public static ByteArrayURLConnection ofBytes(@Nonnull URL url, @Nonnull byte[] data) {
        return new ByteArrayURLConnection(url, () -> data);
    }

    @Nonnull
    public static ByteArrayURLConnection ofBytes(@Nonnull URL url, @Nonnull Supplier<byte[]> dataSupplier) {
        return new ByteArrayURLConnection(url, dataSupplier);
    }
}
