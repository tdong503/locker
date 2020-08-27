package com.tw.locker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    private Integer id;
    private Integer bagId;
    private boolean isUsed;
}
