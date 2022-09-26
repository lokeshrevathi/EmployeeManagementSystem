package com.ideas2it.ems.util;

public enum Role {
    SOFTWARE_DEVELOPER_TRAINEE,
    SOFTWARE_TESTER_TRAINEE,

    JUNIOR_SOFTWARE_DEVELOPER{
        public boolean isValidExperience(float experience) {
            if (1.0f <= experience && experience <= 4.0f) {
                return true;
            }
            return false;
        }
    },


    JUNIOR_SOFTWARE_TESTER{
        public boolean isValidExperience(float experience) {
            if (1.0f <= experience && experience <= 4.0f) {
                return true;
            }
            return false;
        }
    },


    SENIOR_SOFTWARE_DEVELOPER{
        public boolean isValidExperience(float experience) {
            if (4.1f < experience) {
                return true;
            }
            return false;
        }
    },


    SENIOR_SOFTWARE_TESTER{
        public boolean isValidExperience(float experience) {
            if (4.1f < experience) {
                return true;
            }
            return false;
        }
    };

    public boolean isValidExperience(float experience) {
        return true;
    }
}