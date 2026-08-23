package com.fitness.aiService.service;

import com.fitness.aiService.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAIService {

    private final GeminiService geminiService;

    public String generateRecommendation(Activity activity) {
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiService.getAnswer(prompt);

        log.info("Response From AI: {}", aiResponse);

        return aiResponse;
    }

    private String createPromptForActivity(Activity activity) {
        return String.format("""
                Analyze the following fitness activity and provide detailed recommendations.

                Your response MUST follow this EXACT JSON format:

                {
                  "analysis": {
                    "overall": "Overall analysis here",
                    "pace": "Pace analysis here",
                    "heartRate": "Heart rate analysis here",
                    "caloriesBurned": "Calories burned analysis here"
                  },
                  "improvements": [
                    {
                      "area": "Area name",
                      "recommendation": "Detailed recommendation"
                    }
                  ],
                  "suggestions": [
                    {
                      "workout": "Workout name",
                      "description": "Detailed workout description"
                    }
                  ],
                  "safety": [
                    "Safety point 1",
                    "Safety point 2"
                  ]
                }

                Analyze this activity:

                Activity Type: %s
                Duration: %d minutes
                Calories Burned: %d
                Additional Metrics: %s

                Provide a detailed analysis focusing on:
                1. Overall performance
                2. Pace and heart rate
                3. Calories burned
                4. Areas for improvement
                5. Suggestions for the next workout
                6. Safety guidelines

                IMPORTANT:
                - Return ONLY valid JSON.
                - Do not include any markdown formatting.
                - Do not wrap the response in ```json or ``` code fences.
                - Follow the exact JSON structure shown above.
                - Do not add or remove any JSON fields.
                """,
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getAdditionalMetrics()
        );
    }
}