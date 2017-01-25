package com.citizen.calculator2017.utils;

import com.browndwarf.checkcalculator.BuildConfig;

public class InfoScreenData {
    String[] array;
    public String info;

    class holder {
        String body;
        String title;

        public holder(String Title, String body) {
            this.title = Title;
            this.body = body;
        }
    }

    public InfoScreenData() {
        this.array = new String[]{"Check feature:", "Citizen calculator has Check mode.User can check all the steps for the corresponding calculation by just pressing this button.", BuildConfig.FLAVOR, "Correct feature:", "When a user is in check mode, Pressing this button once will bring him to correct mode where he can correct the number entered for the corresponding step.", "To commit the correction user will have to press correct again.", BuildConfig.FLAVOR, "Mark Up feature:", "User can use this feature of Citizen calculator  to find out the mark up cost. To do so the user will have to enter final Price press MU and enter discount followed by either = or % buttons", BuildConfig.FLAVOR, "History:", "Whenever user clears the calculation it is automatically saved in history.", "The user can Load or Share any calculation eiter from history or from options screen.", BuildConfig.FLAVOR, BuildConfig.FLAVOR, "License Info:", BuildConfig.FLAVOR, BuildConfig.FLAVOR, "TinyDb,", "Copyright 2014 KC Ochibili", BuildConfig.FLAVOR, "Gson,", "Copyright (C) 2008 Google Inc.", BuildConfig.FLAVOR, BuildConfig.FLAVOR, "All of the above are Licensed under the Apache License, Version 2.0 (the \"License\");", "You may obtain a copy of the License at", BuildConfig.FLAVOR, "http://www.apache.org/licenses/LICENSE-2.0"};
        this.info = BuildConfig.FLAVOR;
        for (String str : this.array) {
            this.info += str + "\n";
        }
    }
}
