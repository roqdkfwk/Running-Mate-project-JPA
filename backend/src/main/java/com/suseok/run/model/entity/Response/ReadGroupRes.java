package com.suseok.run.model.entity.Response;

import com.suseok.run.model.entity.Group;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReadGroupRes {

    private Long groupId;
    private String groupName;
    private Double pace;
    private Double frequency;
    private Double totalDistance;

    public ReadGroupRes(Group group) {
        this.groupId = group.getGroupId();
        this.groupName = group.getGroupName();
        this.pace = group.getPace();
        this.frequency = group.getFrequency();
        this.totalDistance = group.getTotalDistance();
    }
}
