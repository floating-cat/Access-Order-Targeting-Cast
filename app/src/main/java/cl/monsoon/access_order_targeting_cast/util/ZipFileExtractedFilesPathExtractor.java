package cl.monsoon.access_order_targeting_cast.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Throwables;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class ZipFileExtractedFilesPathExtractor implements Supplier<Set<String>> {
    private final ZipInputStream zipInputStream;

    public ZipFileExtractedFilesPathExtractor(ZipInputStream zipInputStream) {
        this.zipInputStream = Preconditions.checkNotNull(zipInputStream);
    }

    @Override
    public Set<String> get() {
        try {
            ZipEntry zipEntry;
            Set<String> extractedFilePaths = new HashSet<>();
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (!zipEntry.isDirectory()) {
                    extractedFilePaths.add(zipEntry.getName());
                }
            }

            return extractedFilePaths;
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }
}
