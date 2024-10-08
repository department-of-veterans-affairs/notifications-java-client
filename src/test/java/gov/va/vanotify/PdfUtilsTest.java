package gov.va.vanotify;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PdfUtilsTest
{
    @Test
    public void testIsBase64StringNotValidPdf() throws Exception
    {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("not_a_pdf.txt").getFile());

        byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));

        String base64encodedString = new String(encoded, US_ASCII);

        assertFalse(PdfUtils.isBase64StringPDF(base64encodedString));
    }


    @Test
    public void testIsBase64StringValidPdf() throws Exception
    {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("one_page_pdf.pdf").getFile());

        byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));

        String base64encodedString = new String(encoded, US_ASCII);

        assertTrue(PdfUtils.isBase64StringPDF(base64encodedString));
    }
}
