package org.communitycookerfoundation.communitycookerfoundation.util;

import androidx.room.TypeConverter;

import java.util.List;

import static java.util.Arrays.asList;

public class TypeConverters {
    @TypeConverter
    public static List<String> fromString(String items){
        return asList(items.split(";"));
    }

    @TypeConverter
    public static String listToString(List<String> list){
        StringBuilder valToReturn = new StringBuilder();
        for(String item: list){
            valToReturn.append(item);
            valToReturn.append(";");
        }

        return valToReturn.toString();
    }
}
