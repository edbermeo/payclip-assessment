package com.payclip.assessment.utilities;

import java.util.UUID;

public class UUIDUtil {
    public static boolean isValid(String uuid) {
        try {
            return UUID.fromString(uuid.trim()).toString().equals(uuid);
        } catch (IllegalArgumentException iae) {
            return false;
        }
    }
}
