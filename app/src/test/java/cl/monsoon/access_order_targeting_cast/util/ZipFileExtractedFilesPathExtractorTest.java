package cl.monsoon.access_order_targeting_cast.util;

import com.google.common.io.Closeables;
import com.google.common.truth.Truth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.InputStream;
import java.util.Set;
import java.util.zip.ZipInputStream;

@RunWith(JUnit4.class)
public final class ZipFileExtractedFilesPathExtractorTest {
    private ZipInputStream zipInputStreamWhichContainsThreeFiles;
    private ZipInputStream zipInputStreamWhichDoesNotContainsFiles;
    private ZipInputStream notAZipFileZipInputStream;

    @Before
    public void setUp() {
        zipInputStreamWhichContainsThreeFiles = getZipInputStreamByResourceFile(
                "zipFileWhichContainsThreeFiles.zip");
        zipInputStreamWhichDoesNotContainsFiles = getZipInputStreamByResourceFile(
                "zipFileWhichDoesNotContainFiles.zip");
        notAZipFileZipInputStream = getZipInputStreamByResourceFile("notAZipFile");
    }

    @Test
    public void getExtractedFilePathsIfZipFileNotEmpty() {
        ZipFileExtractedFilesPathExtractor supplier = new ZipFileExtractedFilesPathExtractor(
                zipInputStreamWhichContainsThreeFiles);

        Set<String> filePaths = supplier.get();

        Truth.assertThat(filePaths).containsExactly("file1", "file2", "path1/file3");
    }

    @Test
    public void getExtractedFilePathsIfZipFileIsEmpty() {
        ZipFileExtractedFilesPathExtractor supplier = new ZipFileExtractedFilesPathExtractor(
                zipInputStreamWhichDoesNotContainsFiles);

        Set<String> filePaths = supplier.get();

        Truth.assertThat(filePaths).isEmpty();
    }

    @Test(expected = NullPointerException.class)
    public void failToGetExtractedFilePathsIfZipInputStreamIsNull() {
        new ZipFileExtractedFilesPathExtractor(null);
    }

    @Test
    public void failToGetExtractedFilePathsIfIsNotAZipFile() {
        ZipFileExtractedFilesPathExtractor supplier = new ZipFileExtractedFilesPathExtractor(
                notAZipFileZipInputStream);

        Set<String> filePaths = supplier.get();

        Truth.assertThat(filePaths).isEmpty();
    }

    @After
    public void tearDown() {
        Closeables.closeQuietly(zipInputStreamWhichContainsThreeFiles);
        Closeables.closeQuietly(zipInputStreamWhichDoesNotContainsFiles);
        Closeables.closeQuietly(notAZipFileZipInputStream);
    }

    private ZipInputStream getZipInputStreamByResourceFile(String fileName) {
        InputStream zipFileInputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        return new ZipInputStream(zipFileInputStream);
    }
}
