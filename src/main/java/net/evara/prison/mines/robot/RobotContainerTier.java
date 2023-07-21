package net.evara.prison.mines.robot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.lucko.helper.random.RandomSelector;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public enum RobotContainerTier {

    TIER_1("&dRobot Container &8(TIER 1)"),
    TIER_2("&dRobot Container &8(TIER 2)"),
    TIER_3("&dRobot Container &8(TIER 3)"),
    TIER_4("&dRobot Container &8(TIER 4)");

    private final String displayName;

    public RobotType getRandom() {
        RandomSelector<RobotType> selector = RandomSelector.weighted(getTiers());
        return selector.pick();
    }

    public List<RobotType> getTiers() {
        return Arrays.stream(RobotType.values())
                .filter(robotType -> robotType.getRetrievedFrom() == this)
                .collect(Collectors.toList());
    }

}
