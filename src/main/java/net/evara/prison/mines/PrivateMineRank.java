package net.evara.prison.mines;

import lombok.Getter;

@Getter
public enum PrivateMineRank {

    FOUNDER(),
    OFFICER(),
    MEMBER();

    private final int hierarchy;

    PrivateMineRank() {
        this.hierarchy = ordinal();
    }

    public boolean isHigherThan(PrivateMineRank rank) {
        return hierarchy > rank.hierarchy;
    }

    public boolean isLowerThan(PrivateMineRank rank) {
        return hierarchy < rank.hierarchy;
    }

    public boolean isEqualTo(PrivateMineRank rank) {
        return hierarchy == rank.hierarchy;
    }

    public static PrivateMineRank promote(PrivateMineRank rank) {
        if(rank.isEqualTo(FOUNDER)) {
            return FOUNDER;
        }
        if(rank.isEqualTo(OFFICER)) {
            return FOUNDER;
        }
        if(rank.isEqualTo(MEMBER)) {
            return OFFICER;
        }
        return MEMBER;
    }

}
