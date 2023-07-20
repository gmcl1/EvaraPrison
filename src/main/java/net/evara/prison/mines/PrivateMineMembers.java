package net.evara.prison.mines;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class PrivateMineMembers {

    private final PrivateMine mine;
    private final Map<UUID, PrivateMineMember> members = new ConcurrentHashMap<>();
    private int maxMembers;

    public PrivateMineMembers(PrivateMine mine) {
        this.mine = mine;
        this.maxMembers = 3;
        this.addMember(mine.getMineOwner(), true);
    }

    public void messageAll(String msg) {
        members.values().forEach(member -> member.msg(msg));
    }

    public void increaseMaxMembers(int amount) {
        this.maxMembers += amount;
    }

    public void addMember(UUID uuid, boolean founder) {

        if (limitReached()) {
            return;
        }

        if (isMember(uuid)) {
            return;
        }

        PrivateMineMember member = new PrivateMineMember(mine, uuid, founder);
        members.put(uuid, member);

        this.messageAll("&a" + member.getUuid() + " &7has joined the mine!");
    }

    public void removeMember(UUID uuid) {

        if (!isMember(uuid)) {
            return;
        }

        members.remove(uuid);
    }

    public boolean isMember(UUID uuid) {
        return members.containsKey(uuid);
    }

    public boolean limitReached() {
        return members.size() >= maxMembers;
    }

    public List<PrivateMineMember> getBlockContributors() {
        return this.members.values()
                .stream()
                .sorted((o1, o2) -> Long.compare(o2.getBlocksMined(), o1.getBlocksMined()))
                .toList();
    }

    public List<UUID> getAllIDs(){
        return this.members.keySet().stream().toList();
    }

    public List<PrivateMineMember> getAllMembers(){
        return this.members.values().stream().toList();
    }

}
