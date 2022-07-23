package com.github.cyberryan1.paypluginexample.utils.yml;

public class YMLUtils {

    private static final DataUtils data = new DataUtils();

    public static DataUtils getData() { return data; }
    public static void saveData() { data.save(); }
}
