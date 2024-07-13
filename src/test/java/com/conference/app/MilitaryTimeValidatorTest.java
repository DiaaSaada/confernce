package com.conference.app;

import com.conference.app.validators.MilitaryTimeValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MilitaryTimeValidatorTest {

    @Test
    void testValidTimes() {

        String[] testTimes = {"1330", "0000", "2359"};
        for (String time : testTimes) {
            assertTrue(MilitaryTimeValidator.isValidMilitaryTime(time));

        }
    }

    @Test
    void testInvalidTimes() {
        String[] testTimes = {"2525", "1199", "2400", "0060", "2577", "", "A", " ", "9999", "0"};
        for (String time : testTimes) {
            assertFalse(MilitaryTimeValidator.isValidMilitaryTime(time));

        }
    }
}