package com.example.onjeong.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SelfIntroductionAnswerGetDto {
    private Long selfIntroductionAnswerId;
    private String selfIntroductionAnswerContent;
}
