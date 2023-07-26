package com.invogue_fashionblog.dto.responses;

import com.invogue_fashionblog.dto.Choice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatGptResponse {

    private List<Choice> choices;

}
