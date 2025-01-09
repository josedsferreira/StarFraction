package dev.jf.starFraction.DTOs;

import java.time.LocalDateTime;

public record UpgradeStatusDTO(LocalDateTime upgradeFinishTime, String buildingBeingUpgraded) {

}
