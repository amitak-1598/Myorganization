package com.myorganization.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.inject.Singleton;

@Singleton
public class AuditService {

	public static List<Map<String, Object>> findDifferences(Map<String, Object> map1, Map<String, Object> map2,String modifiedby,String tablename,Integer deleted) {
		
		               String excludedKey [] = {"id","createdat","createdby","createdAt","createdBy"};
		               List<String> excludedKeys = Arrays.asList(excludedKey);
        return map1.entrySet().stream()
                   .filter(entry ->
                        !excludedKeys.contains(entry.getKey()) &&  
                        map2.containsKey(entry.getKey()) &&        
                        areValuesDifferent(entry.getValue(), map2.get(entry.getKey()))) 
                .map(entry -> {
                    Map<String, Object> differenceMap = new HashMap<>();
                    differenceMap.put("fieldName", entry.getKey());
                    differenceMap.put("oldValue", entry.getValue());
                    differenceMap.put("newValue", map2.get(entry.getKey()));
                    differenceMap.put("parentTable",tablename);
                    differenceMap.put("deleted",deleted);
                    differenceMap.put("parentId",map2.get("id").toString());
                    differenceMap.put("updatedBy",modifiedby);
                    return differenceMap;
                })
                .collect(Collectors.toList());
    }

    private static boolean areValuesDifferent(Object value1, Object value2) {
        
        return (value1 == null && value2 != null) || (value1 != null && !value1.equals(value2));
    }

   
                            }

