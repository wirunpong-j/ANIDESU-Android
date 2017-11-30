package com.bellkung.anidesu;

import com.bellkung.anidesu.utils.FormatCustomManager;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created by BellKunG on 30/11/2017 AD.
 */

public class FormatCustomManagerTest {

    @Test
    public void parseNullToNATextCompleted() {
        String text = FormatCustomManager.parseString(null);
        assertEquals(text, "N/A");
    }

    @Test
    public void parseNullToNATextFailed() {
        String text = FormatCustomManager.parseString("Bell");
        assertEquals(text, "Bell");
    }

    @Test
    public void parseIntToDateCompleted() {
        String text = FormatCustomManager.parseToDate(20171001);
        assertEquals(text, "01 Oct 2017");
    }

    @Test
    public void parseIntToDateFailed() {
        String text = FormatCustomManager.parseToDate(02);
        assertEquals(text, "N/A");
    }

    @Test
    public void parseStringToFirebaseDatetimeCompleted() {
        String text = FormatCustomManager.parseOnFirebaseDateTime("2017-10-07-22-33");
        assertEquals(text, "07 Oct 2017 - 22:33");
    }

    @Test
    public void parseStringToFirebaseDatetimeFailed() {
        String text = FormatCustomManager.parseOnFirebaseDateTime("2017-aasd");
        assertEquals(text, "N/A");
    }

}
