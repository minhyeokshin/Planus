package com.teamflow.Planus.util;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class ColorMappingService {
    private final Map<String, String> userColorMap = new HashMap<>();
    private final Set<String> usedColors = new HashSet<>();

    public String assignColor(String userId) {
        if (userColorMap.containsKey(userId)) {
            return userColorMap.get(userId);
        }

        for (String color : colorPalette.COLORS) {
            if (!usedColors.contains(color)) {
                userColorMap.put(userId, color);
                usedColors.add(color);
                return color;
            }
        }

        // fallback: 중복 허용
        int idx = Math.floorMod(userId.hashCode(), colorPalette.COLORS.length);
        return colorPalette.COLORS[idx];
    }
}