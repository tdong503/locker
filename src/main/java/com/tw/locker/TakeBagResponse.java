package com.tw.locker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TakeBagResponse {
    private Boolean isSuccess;
    private String message;
    private Bag bag;
}
