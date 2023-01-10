package com.ebc.ecard.application.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ListBodyDto<T> {
    List<T> data;

}
