package com.citizen.calculator2017.utils;

public interface UIsystemStates {
    public static final int BACKSPACE_KEY = 8;
    public static final int CHECK_MODE_ACTIVE = 1;
    public static final int CHECK_MODE_CORRECT_ACTIVE = 2;
    public static final int CHECK_MODE_CORRECT_EVALUATING = 3;
    public static final int CHECK_MODE_INACTIVE = 0;
    public static final int INPUT_TYPE_NO_OP = 0;
    public static final int INPUT_TYPE_OPERATION = 1;
    public static final int OPERATION_MODE_ACTIVE = 1;
    public static final int OPERATION_MODE_COMPLETE = 2;
    public static final int OPERATION_MODE_INACTIVE = 0;
    public static final int SYSTEM_STATE_UNKNOWN = -1;
}
